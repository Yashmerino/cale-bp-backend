package com.mlc.vdsr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.vdsr.dto.EmployeeRecordDTO;
import com.mlc.vdsr.utils.Department;
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
 * Test suite for {@link com.mlc.vdsr.entity.EmployeeRecord}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class EmployeeRecordControllerTest {
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
     * Employee Record DTO.
     */
    private EmployeeRecordDTO employeeRecordDTO;

    /**
     * Setup before each test.
     */
    @BeforeEach
    void setup() {
        employeeRecordDTO = new EmployeeRecordDTO();
        employeeRecordDTO.setDepartment(Department.IT);
        employeeRecordDTO.setPosition("Developer");
        employeeRecordDTO.setUserId(1L);
    }

    /**
     * Tests get all employee records.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getAllEmployeeRecordsTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/employeeRecord")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"department\":\"IT\",\"position\":\"Developer\",\"userId\":2}]"));
    }

    /**
     * Tests get all employee records as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void getAllEmployeeRecordsAsTLTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/employeeRecord")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"department\":\"IT\",\"position\":\"Developer\",\"userId\":2}]"));
    }

    /**
     * Tests delete employee record.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteEmployeeRecordTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/employeeRecord/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"employee_record_deleted_successfully\"}"));

        result = mvc.perform(get("/api/employeeRecord")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests delete non-existing event.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteNonExistingEmployeeRecordTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/employeeRecord/999")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"status\":404,\"error\":\"employee_record_not_found\"}"));
    }

    /**
     * Tests delete event as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void deleteEventAsTLTest() throws Exception {
        mvc.perform(delete("/api/employeeRecord/1")).andExpect(status().isForbidden()).andReturn();
    }

    /**
     * Tests delete event as HR.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"HR"})
    void deleteEventAsHRTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/employeeRecord/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"employee_record_deleted_successfully\"}"));

        result = mvc.perform(get("/api/employeeRecord")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests create invalid employee record.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createInvalidEmployeeRecordTest() throws Exception {
        employeeRecordDTO.setPosition("");

        MvcResult result = mvc.perform(post("/api/employeeRecord").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeRecordDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"position\",\"message\":\"position_is_required\"}"));
        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"position\",\"message\":\"position_length_invalid\"}"));
    }

    /**
     * Tests create employee record.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createEmployeeRecordTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/employeeRecord").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeRecordDTO))).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"employee_record_created_successfully\"}"));

        result = mvc.perform(get("/api/employeeRecord")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"department\":\"IT\",\"position\":\"Developer\",\"userId\":2},{\"id\":2,\"department\":\"IT\",\"position\":\"Developer\",\"userId\":1}]"));
    }

    /**
     * Tests update employee record.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void updateEmployeeRecordTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/employeeRecord/1").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeRecordDTO))).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"employee_record_updated_successfully\"}"));

        result = mvc.perform(get("/api/employeeRecord")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"department\":\"IT\",\"position\":\"Developer\",\"userId\":1}]"));
    }

    /**
     * Tests update employee record.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void updateEmployeeRecordWithInvalidDataTest() throws Exception {
        employeeRecordDTO.setDepartment(null);

        MvcResult result = mvc.perform(post("/api/employeeRecord/1").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeRecordDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"fieldErrors\":[{\"field\":\"department\",\"message\":\"department_is_required\"}]}"));
    }
}
