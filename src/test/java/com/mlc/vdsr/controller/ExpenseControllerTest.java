package com.mlc.vdsr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.vdsr.dto.EventDTO;
import com.mlc.vdsr.dto.ExpenseDTO;
import com.mlc.vdsr.utils.ExpenseCategory;
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
 * Test suite for {@link ExpenseController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ExpenseControllerTest {
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
     * Expense DTO.
     */
    private ExpenseDTO expenseDTO;


    /**
     * Setup before each test.
     */
    @BeforeEach
    void setup() {
        expenseDTO = new ExpenseDTO();
        expenseDTO.setDate(Instant.ofEpochSecond(1741102389));
        expenseDTO.setDescription("description");
        expenseDTO.setAmount(1.0);
        expenseDTO.setExpenseCategory(ExpenseCategory.IT);
    }

    /**
     * Tests get all expenses
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getAllExpensesTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/expense")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"description\":\"description\",\"expenseCategory\":\"IT\",\"amount\":1.0,\"date\":\"2025-03-04T15:33:09Z\"}]"));
    }

    /**
     * Tests get all expenses as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void getAllExpensesAsTLTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/expense")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"description\":\"description\",\"expenseCategory\":\"IT\",\"amount\":1.0,\"date\":\"2025-03-04T15:33:09Z\"}]"));
    }

    /**
     * Tests delete expense.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteExpenseTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/expense/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"expense_deleted_successfully\"}"));

        result = mvc.perform(get("/api/expense")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests delete non-existing expense.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteNonExistingEventTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/expense/999")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"status\":404,\"error\":\"expense_not_found\"}"));
    }

    /**
     * Tests delete expense as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void deleteExpenseAsTLTest() throws Exception {
        mvc.perform(delete("/api/expense/1")).andExpect(status().isForbidden()).andReturn();
    }

    /**
     * Tests delete expense as accountant.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"ACCOUNTING"})
    void deleteExpenseAsHRTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/expense/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"expense_deleted_successfully\"}"));

        result = mvc.perform(get("/api/expense")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests create invalid expense.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createInvalidExpenseTest() throws Exception {
        expenseDTO.setDescription("");

        MvcResult result = mvc.perform(post("/api/expense").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(expenseDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"description\",\"message\":\"description_is_required\"}"));
        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"description\",\"message\":\"description_length_invalid\"}"));
    }

    /**
     * Tests create expense.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createExpenseTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/expense").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(expenseDTO))).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"expense_created_successfully\"}"));

        result = mvc.perform(get("/api/expense")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"description\":\"description\",\"expenseCategory\":\"IT\",\"amount\":1.0,\"date\":\"2025-03-04T15:33:09Z\"},{\"id\":2,\"description\":\"description\",\"expenseCategory\":\"IT\",\"amount\":1.0,\"date\":\"2025-03-04T15:33:09Z\"}]"));
    }
}
