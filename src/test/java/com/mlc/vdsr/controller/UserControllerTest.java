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

import java.util.Date;

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

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"firstName\":\"owner\",\"lastName\":\"owner\",\"username\":\"owner\",\"email\":\"owner@test.com\",\"dateOfBirth\""));
        assertTrue(result.getResponse().getContentAsString().contains("{\"id\":2,\"firstName\":\"artiom\",\"lastName\":\"bozieac\",\"username\":\"artiombozieac\",\"email\":\"test@test.com\",\"dateOfBirth\":\""));
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

        assertTrue(result.getResponse().getContentAsString().contains("{\"id\":1,\"firstName\":\"owner\",\"lastName\":\"owner\",\"username\":\"owner\",\"email\":\"owner@test.com\",\"dateOfBirth\":\""));
    }

    /**
     * Tests get user by non-existing id.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getUserByNonExistingId() throws Exception {
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
        UserDTO userDTO = new UserDTO(1L, "razvan", "smeu", "username2", "test2@mail.com", new Date());

        MvcResult result = mvc.perform(put("/api/user/1").content(objectMapper.writeValueAsString(userDTO)).contentType(
                APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"id\":1,\"firstName\":\"razvan\",\"lastName\":\"smeu\",\"username\":\"owner\",\"email\":\"test2@mail.com\",\"dateOfBirth\":\""));
    }

    /**
     * Tests update non-existing user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void updateNonexistingUserTest() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "razvan", "smeu", "username", "test@mail.com", new Date());

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

        mvc.perform(get("/api/user/1")).andExpect(status().isNotFound()).andReturn();
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

    /**
     * Tests assign project to user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void assignProjectTest() throws Exception {
        MvcResult result = mvc.perform(put("/api/user/1/assign?projectId=1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"project_assigned_successfully\"}"));

        result = mvc.perform(get("/api/user/1/projects")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Project\"}]"));
    }

    /**
     * Tests unassign project to user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void unassignProjectTest() throws Exception {
        MvcResult result = mvc.perform(put("/api/user/1/unassign?projectId=1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"project_unassigned_successfully\"}"));

        result = mvc.perform(get("/api/user/1/projects")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests get user's projects.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getUserProjectsTest() throws Exception {
        mvc.perform(put("/api/user/1/assign?projectId=1")).andExpect(status().isOk()).andReturn();

        MvcResult result = mvc.perform(get("/api/user/1/projects")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Project\"}]"));
    }

    /**
     * Tests assign non-existing project to user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void assignNonExistingProjectTest() throws Exception {
        MvcResult result = mvc.perform(put("/api/user/1/assign?projectId=999")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(",\"status\":404,\"error\":\"project_not_found\"}"));
    }

    /**
     * Tests unassign non-existing project to user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void unassignNonExistingProjectTest() throws Exception {
        MvcResult result = mvc.perform(put("/api/user/1/unassign?projectId=999")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(",\"status\":404,\"error\":\"project_not_found\"}"));
    }

    /**
     * Tests assign project to non-existing user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void assignProjectToNonExistingUser() throws Exception {
        MvcResult result = mvc.perform(put("/api/user/999/assign?projectId=1")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(",\"status\":404,\"error\":\"user_not_found\"}"));
    }

    /**
     * Tests unassign project to non-existing user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void unassignProjectToNonExistingUser() throws Exception {
        MvcResult result = mvc.perform(put("/api/user/999/unassign?projectId=1")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(",\"status\":404,\"error\":\"user_not_found\"}"));
    }

    /**
     * Tests get user info.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getUserInfoSuccessful() throws Exception {
        MvcResult result = mvc.perform(get("/api/user/1/info")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"id\":1,\"firstName\":\"owner\",\"lastName\":\"owner\",\"email\":\"owner@test.com\",\"roles\":[{\"id\":7,\"name\":\"OWNER\"}],"));
    }

    /**
     * Tests get user info non existing user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getUserInfoNonExisting() throws Exception {
        MvcResult result = mvc.perform(get("/api/user/9999999999/info")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(",\"status\":404,\"error\":\"user_not_found\"}"));
    }

    /**
     * Tests get user info for another user that user doesn't have access to.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getUserInfoForAnotherUser() throws Exception {
        MvcResult result = mvc.perform(get("/api/user/2/info")).andExpect(status().isForbidden()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(",\"status\":403,\"error\":\"access_denied\"}"));
    }
}
