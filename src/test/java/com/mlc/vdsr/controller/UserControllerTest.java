package com.mlc.vdsr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.vdsr.dto.UserDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
     * Tests get user info.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void createUserTest() throws Exception {
        UserDTO userDTO = new UserDTO("artiom", "bozieac");

        MvcResult result = mvc.perform(post("/api/user").content(objectMapper.writeValueAsString(userDTO)).contentType(
                APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"User created successfully\"}"));
    }

    /**
     * Tests get all users
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void getAllUsersTest() throws Exception {
        assertTrue(this.createUser("artiom", "bozieac"));
        assertTrue(this.createUser("razvan", "smeu"));

        MvcResult result = mvc.perform(get("/api/user")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"firstName\":\"artiom\",\"lastName\":\"bozieac\"},{\"firstName\":\"razvan\",\"lastName\":\"smeu\"}]"));
    }

    /**
     * Tests get user by id.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void getUserById() throws Exception {
        assertTrue(this.createUser("artiom", "bozieac"));

        MvcResult result = mvc.perform(get("/api/user/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"firstName\":\"artiom\",\"lastName\":\"bozieac\"}"));
    }

    /**
     * Tests get user by non-existing id.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @Disabled
    void getUserByIdNonexisting() throws Exception {
        // TODO: Add exception handling and return ErrorDTO or something like that on exceptions. After that fix this text
        MvcResult result = mvc.perform(get("/api/user/1")).andExpect(status().isInternalServerError()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"firstName\":\"artiom\",\"lastName\":\"bozieac\"}"));
    }

    /**
     * Tests update user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void updateUserTest() throws Exception {
        assertTrue(this.createUser("artiom", "bozieac"));

        UserDTO userDTO = new UserDTO("razvan", "smeu");

        MvcResult result = mvc.perform(put("/api/user/1").content(objectMapper.writeValueAsString(userDTO)).contentType(
                APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"firstName\":\"razvan\",\"lastName\":\"smeu\"}"));
    }

    /**
     * Tests update non-existing user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @Disabled
    void updateNonexistingUserTest() throws Exception {
        // TODO: Add exception handling and return ErrorDTO or something like that on exceptions. After that fix this text
        UserDTO userDTO = new UserDTO("razvan", "smeu");

        MvcResult result = mvc.perform(put("/api/user/1").content(objectMapper.writeValueAsString(userDTO)).contentType(
                APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"firstName\":\"razvan\",\"lastName\":\"smeu\"}"));
    }

    /**
     * Tests delete user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void deleteUserTest() throws Exception {
        assertTrue(this.createUser("artiom", "bozieac"));

        MvcResult result = mvc.perform(delete("/api/user/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"User deleted successfully\"}"));
    }

    /**
     * Tests delete non-existing user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @Disabled
    void deleteNonexistingUserTest() throws Exception {
        // TODO: Add exception handling and return ErrorDTO or something like that on exceptions. After that fix this text
        MvcResult result = mvc.perform(delete("/api/user/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"User deleted successfully\"}"));
    }

    /**
     * Helper method that creates a user.
     *
     * @param firstName is the user's first name.
     * @param lastName is the user's last name.
     */
    private boolean createUser(String firstName, String lastName) throws Exception {
        UserDTO userDTO = new UserDTO(firstName, lastName);
        mvc.perform(post("/api/user").content(objectMapper.writeValueAsString(userDTO)).contentType(APPLICATION_JSON));

        return true;
    }
}
