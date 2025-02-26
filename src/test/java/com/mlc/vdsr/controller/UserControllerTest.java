package com.mlc.vdsr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.vdsr.dto.UserDTO;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test suite for {@link UserController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class UserControllerTest {
    /**
     * MVC Mock.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Object mapper.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Tests get all users
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getAllUsersTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/user")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"firstName\":\"owner\",\"lastName\":\"owner\",\"username\":\"owner\",\"email\":\"owner@test.com\"},{\"id\":2,\"firstName\":\"artiom\",\"lastName\":\"bozieac\",\"username\":\"artiombozieac\",\"email\":\"test@test.com\"}]"));
    }

    /**
     * Tests get user by id.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getUserById() throws Exception {
        MvcResult result = mvc.perform(get("/api/user/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"id\":1,\"firstName\":\"owner\",\"lastName\":\"owner\",\"username\":\"owner\",\"email\":\"owner@test.com\"}"));
    }

    /**
     * Tests get user by non-existing id.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getUserByIdNonexisting() throws Exception {
        MvcResult result = mvc.perform(get("/api/user/99999")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"status\":404,\"error\":\"user_not_found\""));
    }

    /**
     * Tests update user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void updateUserTest() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "razvan", "smeu", "username2", "test2@mail.com");

        MvcResult result = mvc.perform(put("/api/user/1").content(objectMapper.writeValueAsString(userDTO)).contentType(
                APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"id\":1,\"firstName\":\"razvan\",\"lastName\":\"smeu\",\"username\":\"owner\",\"email\":\"test2@mail.com\"}"));
    }

    /**
     * Tests update non-existing user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void updateNonexistingUserTest() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "razvan", "smeu", "username", "test@mail.com");

        MvcResult result = mvc.perform(put("/api/user/9999").content(objectMapper.writeValueAsString(userDTO)).contentType(
                APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"status\":404,\"error\":\"user_not_found\""));
    }

    /**
     * Tests delete user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteUserTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/user/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"user_deleted_successfully\"}"));
    }

    /**
     * Tests delete non-existing user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteNonexistingUserTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/user/99")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"status\":404,\"error\":\"user_not_found\""));
    }
}
