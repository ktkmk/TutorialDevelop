package com.techacademy.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest2 {

    private final WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    public UserControllerTest2(WebApplicationContext context) {
        this.webApplicationContext = context;
    }

    @BeforeEach
    void beforeEach() {
        // Spring Securityを有効にする
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Userリスト取得")
    @WithMockUser
    void testGetList() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/list")) // URLにアクセス
            .andExpect(status().isOk()) // ステータスを確認
            .andExpect(model().attributeExists("userlist")) // Modelにuserlistが含まれていることを確認
            .andExpect(model().hasNoErrors()) // Modelにエラーが無いことを確認
            .andExpect(view().name("user/list")) // viewの確認
            .andReturn();

        // Modelからuserlistを取得
        List<User> userList = (List<User>) result.getModelAndView().getModel().get("userlist");

        // 件数が3件であることを確認
        assertEquals(3, userList.size());

        // userListから1件ずつ取り出し、idとnameを検証
        assertEquals(1, userList.get(0).getId());
        assertEquals("キラメキ太郎", userList.get(0).getName());

        assertEquals(2, userList.get(1).getId());
        assertEquals("キラメキ次郎", userList.get(1).getName());

        assertEquals(3, userList.get(2).getId());
        assertEquals("キラメキ花子", userList.get(2).getName());
    }
}
