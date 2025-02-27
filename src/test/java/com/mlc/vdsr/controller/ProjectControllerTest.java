package com.mlc.vdsr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.vdsr.dto.ProjectDTO;
import org.junit.jupiter.api.BeforeEach;
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
 * Test suite for {@link ProjectController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ProjectControllerTest {
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
     * Project DTO.
     */
    private ProjectDTO projectDTO;


    /**
     * Setup before each test.
     */
    @BeforeEach
    void setup() {
        projectDTO = new ProjectDTO();
        projectDTO.setTitle("Project");
    }

    /**
     * Tests get all projects
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getAllProjectsTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/project")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Project\""));
    }

    /**
     * Tests get all projects as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void getAllProjectsAsTLTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/project")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Project\""));
    }

    /**
     * Tests delete project.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteProjectTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/project/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"project_deleted_successfully\"}"));
    }

    /**
     * Tests delete non-existing project.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteNonExistingProjectTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/project/999")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"status\":404,\"error\":\"project_not_found\"}"));
    }

    /**
     * Tests delete project as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void deleteProjectAsTLTest() throws Exception {
        mvc.perform(delete("/api/project/1")).andExpect(status().isForbidden()).andReturn();
    }

    /**
     * Tests create invalid project.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createInvalidProjectTest() throws Exception {
        projectDTO.setTitle("");

        MvcResult result = mvc.perform(post("/api/project").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(projectDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"title\",\"message\":\"title_is_required\"}"));
        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"title\",\"message\":\"title_length_invalid\"}"));
    }

    /**
     * Tests create project.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createProjectTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/project").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(projectDTO))).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"project_created_successfully\"}"));
    }
}
