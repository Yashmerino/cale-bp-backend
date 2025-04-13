package com.mlc.vdsr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.vdsr.dto.PayrollDTO;
import com.mlc.vdsr.enums.PayrollStatus;
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

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test suite for {@link PayrollController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class PayrollControllerTest {
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
     * Payroll DTO.
     */
    private PayrollDTO payrollDTO;


    /**
     * Setup before each test.
     */
    @BeforeEach
    void setup() {
        payrollDTO = new PayrollDTO();
        payrollDTO.setSalary(100.0);
        payrollDTO.setPaidDate(Instant.ofEpochSecond(1741102389));
        payrollDTO.setEmployeeId(1L);
        payrollDTO.setStatus(PayrollStatus.UNPAID);
    }

    /**
     * Tests get all payrolls.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getAllPayrollsTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/payroll")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"salary\":100.0,\"paidDate\":\"2025-03-04T15:33:09Z\",\"employeeId\":1,\"status\":\"UNPAID\",\"userName\":\"artiom bozieac\",\"department\":\"IT\",\"position\":\"Developer\"}]"));
    }

    /**
     * Tests get all payrolls as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void getAllPayrollsAsTLTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/payroll")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"salary\":100.0,\"paidDate\":\"2025-03-04T15:33:09Z\",\"employeeId\":1,\"status\":\"UNPAID\",\"userName\":\"artiom bozieac\",\"department\":\"IT\",\"position\":\"Developer\"}]"));
    }

    /**
     * Tests delete payroll.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deletePayrollTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/payroll/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"payroll_deleted_successfully\"}"));

        result = mvc.perform(get("/api/payroll")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests delete non-existing payroll.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteNonExistingPayrollTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/payroll/999")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"status\":404,\"error\":\"payroll_not_found\"}"));
    }

    /**
     * Tests delete payroll as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void deletePayrollAsTLTest() throws Exception {
        mvc.perform(delete("/api/payroll/1")).andExpect(status().isForbidden()).andReturn();
    }

    /**
     * Tests create invalid payroll.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createInvalidPayrollTest() throws Exception {
        payrollDTO.setSalary(null);

        MvcResult result = mvc.perform(post("/api/payroll").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(payrollDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"salary\",\"message\":\"salary_is_required\"}"));
    }

    /**
     * Tests create payroll.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createPayrollTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/payroll").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(payrollDTO))).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"payroll_created_successfully\"}"));

        result = mvc.perform(get("/api/payroll")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"salary\":100.0,\"paidDate\":\"2025-03-04T15:33:09Z\",\"employeeId\":1,\"status\":\"UNPAID\",\"userName\":\"artiom bozieac\",\"department\":\"IT\",\"position\":\"Developer\"},{\"id\":2,\"salary\":100.0,\"paidDate\":\"2025-03-04T15:33:09Z\",\"employeeId\":1,\"status\":\"UNPAID\",\"userName\":\"artiom bozieac\",\"department\":\"IT\",\"position\":\"Developer\"}]"));
    }

    /**
     * Tests update payroll.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void updatePayrollTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/payroll").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(payrollDTO))).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"payroll_created_successfully\"}"));

        result = mvc.perform(get("/api/payroll")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"salary\":100.0,\"paidDate\":\"2025-03-04T15:33:09Z\",\"employeeId\":1,\"status\":\"UNPAID\",\"userName\":\"artiom bozieac\",\"department\":\"IT\",\"position\":\"Developer\"},{\"id\":2,\"salary\":100.0,\"paidDate\":\"2025-03-04T15:33:09Z\",\"employeeId\":1,\"status\":\"UNPAID\",\"userName\":\"artiom bozieac\",\"department\":\"IT\",\"position\":\"Developer\"}]"));

        payrollDTO.setStatus(PayrollStatus.PAID);
        result = mvc.perform(post("/api/payroll/1").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(payrollDTO))).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"payroll_updated_successfully\"}"));

        result = mvc.perform(get("/api/payroll")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"salary\":100.0,\"paidDate\":\"2025-03-04T15:33:09Z\",\"employeeId\":1,\"status\":\"PAID\",\"userName\":\"artiom bozieac\",\"department\":\"IT\",\"position\":\"Developer\"},{\"id\":2,\"salary\":100.0,\"paidDate\":\"2025-03-04T15:33:09Z\",\"employeeId\":1,\"status\":\"UNPAID\",\"userName\":\"artiom bozieac\",\"department\":\"IT\",\"position\":\"Developer\"}]"));

    }
}
