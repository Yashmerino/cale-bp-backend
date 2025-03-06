package com.mlc.vdsr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.vdsr.dto.BudgetDTO;
import com.mlc.vdsr.utils.BudgetStatus;
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
 * Test suite for {@link BudgetController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class BudgetControllerTest {
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
     * Budget DTO.
     */
    private BudgetDTO budgetDTO;


    /**
     * Setup before each test.
     */
    @BeforeEach
    void setup() {
        budgetDTO = new BudgetDTO();
        budgetDTO.setStatus(BudgetStatus.PENDING);
        budgetDTO.setAmount(100.0);
        budgetDTO.setName("Budget");
        budgetDTO.setDepartment(Department.IT);
    }

    /**
     * Tests get all budgets
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getAllBudgetsTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/budget")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"department\":\"IT\",\"name\":\"Budget\",\"amount\":100.0,\"status\":\"PENDING\"}]"));
    }

    /**
     * Tests get all budgets as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void getAllBudgetsAsTLTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/budget")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"department\":\"IT\",\"name\":\"Budget\",\"amount\":100.0,\"status\":\"PENDING\"}]"));
    }

    /**
     * Tests delete budget.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteBudgetTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/budget/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"budget_deleted_successfully\"}"));

        result = mvc.perform(get("/api/budget")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests delete non-existing budget.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteNonExistingBudgetTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/budget/999")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"status\":404,\"error\":\"budget_not_found\"}"));
    }

    /**
     * Tests delete budget as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void deleteBudgetAsTLTest() throws Exception {
        mvc.perform(delete("/api/budget/1")).andExpect(status().isForbidden()).andReturn();
    }

    /**
     * Tests delete budget as Accountant.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"ACCOUNTING"})
    void deleteBudgetAsAccountantTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/budget/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"budget_deleted_successfully\"}"));

        result = mvc.perform(get("/api/budget")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests create invalid budget.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createInvalidInvoiceTest() throws Exception {
        budgetDTO.setName("");

        MvcResult result = mvc.perform(post("/api/budget").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(budgetDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"name\",\"message\":\"name_is_required\"}"));
    }

    /**
     * Tests create budget.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createBudgetTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/budget").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(budgetDTO))).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"budget_created_successfully\"}"));

        result = mvc.perform(get("/api/budget")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"department\":\"IT\",\"name\":\"Budget\",\"amount\":100.0,\"status\":\"PENDING\"},{\"id\":2,\"department\":\"IT\",\"name\":\"Budget\",\"amount\":100.0,\"status\":\"PENDING\"}]"));
    }

    /**
     * Tests update budget status.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void updateBudgetStatusTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/budget/1/update?status=PENDING").contentType(
                APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"budget_status_updated_successfully\"}"));

        result = mvc.perform(get("/api/budget")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"department\":\"IT\",\"name\":\"Budget\",\"amount\":100.0,\"status\":\"PENDING\"}]"));
    }
}
