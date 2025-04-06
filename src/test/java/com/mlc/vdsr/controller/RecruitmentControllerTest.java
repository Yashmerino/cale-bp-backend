package com.mlc.vdsr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.vdsr.dto.RecruitmentDTO;
import com.mlc.vdsr.enums.Availability;
import com.mlc.vdsr.enums.Department;
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
 * Test suite for {@link RecruitmentController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class RecruitmentControllerTest {
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
     * Recruitment DTO.
     */
    private RecruitmentDTO recruitmentDTO;


    /**
     * Setup before each test.
     */
    @BeforeEach
    void setup() {
        recruitmentDTO = new RecruitmentDTO();
        recruitmentDTO.setId(1L);
        recruitmentDTO.setTitle("Recruitment");
        recruitmentDTO.setDepartment(Department.IT);
        recruitmentDTO.setAvailability(Availability.IMMEDIATE);
        recruitmentDTO.setOpen(true);
    }

    /**
     * Tests get all recruitments
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getAllRecruitmentsTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/recruitment")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Recruitment\",\"department\":\"IT\",\"availability\":\"IMMEDIATE\",\"open\":true}]"));
    }

    /**
     * Tests get all recruitments as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void getAllRecruitmentsAsTLTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/recruitment")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Recruitment\",\"department\":\"IT\",\"availability\":\"IMMEDIATE\",\"open\":true}]"));
    }

    /**
     * Tests delete recruitment.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteEventTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/recruitment/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"recruitment_deleted_successfully\"}"));

        result = mvc.perform(get("/api/recruitment")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests delete non-existing recruitment.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteNonExistingRecruitmentTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/recruitment/999")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"status\":404,\"error\":\"recruitment_not_found\"}"));
    }

    /**
     * Tests delete recruitment as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void deleteRecruitmentAsTLTest() throws Exception {
        mvc.perform(delete("/api/recruitment/1")).andExpect(status().isForbidden()).andReturn();
    }

    /**
     * Tests delete recruitment as HR.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"HR"})
    void deleteRecruitmentAsHRTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/recruitment/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"recruitment_deleted_successfully\"}"));

        result = mvc.perform(get("/api/recruitment")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests create invalid recruitment.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createInvalidRecruitmentTest() throws Exception {
        recruitmentDTO.setTitle("");

        MvcResult result = mvc.perform(post("/api/recruitment").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(recruitmentDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"title\",\"message\":\"title_is_required\"}"));
        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"title\",\"message\":\"title_length_invalid\"}"));
    }

    /**
     * Tests create recruitment.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createRecruitmentTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/recruitment").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(recruitmentDTO))).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"recruitment_created_successfully\"}"));

        result = mvc.perform(get("/api/recruitment")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Recruitment\",\"department\":\"IT\",\"availability\":\"IMMEDIATE\",\"open\":true},{\"id\":2,\"title\":\"Recruitment\",\"department\":\"IT\",\"availability\":\"IMMEDIATE\",\"open\":true}]"));
    }

    /**
     * Tests close recruitment.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void closeRecruitmentTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/recruitment/1/close").contentType(
                APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"recruitment_closed_successfully\"}"));

        result = mvc.perform(get("/api/recruitment")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Recruitment\",\"department\":\"IT\",\"availability\":\"IMMEDIATE\",\"open\":false}]"));
    }

    /**
     * Tests open recruitment.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void openRecruitmentTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/recruitment/1/open").contentType(
                APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"recruitment_opened_successfully\"}"));

        result = mvc.perform(get("/api/recruitment")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Recruitment\",\"department\":\"IT\",\"availability\":\"IMMEDIATE\",\"open\":true}]"));
    }
}
