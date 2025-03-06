package com.mlc.vdsr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.vdsr.dto.EventDTO;
import com.mlc.vdsr.dto.InvoiceDTO;
import com.mlc.vdsr.utils.InvoiceStatus;
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
 * Test suite for {@link InvoiceController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class InvoiceControllerTest {
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
     * Invoice DTO.
     */
    private InvoiceDTO invoiceDTO;


    /**
     * Setup before each test.
     */
    @BeforeEach
    void setup() {
        invoiceDTO = new InvoiceDTO();
        invoiceDTO.setClient("Client");
        invoiceDTO.setDueDate(Instant.now());
        invoiceDTO.setAmount(10.0);
        invoiceDTO.setStatus(InvoiceStatus.PENDING);
    }

    /**
     * Tests get all invoices
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getAllInvoicesTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/invoice")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"client\":\"Client\",\"amount\":100.0,\"dueDate\":\"2025-03-04T15:33:09Z\",\"status\":\"PAID\"}]"));
    }

    /**
     * Tests get all invoices as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void getAllInvoicesAsTLTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/invoice")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"client\":\"Client\",\"amount\":100.0,\"dueDate\":\"2025-03-04T15:33:09Z\",\"status\":\"PAID\"}]"));
    }

    /**
     * Tests delete invoice.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteInvoiceTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/invoice/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"invoice_deleted_successfully\"}"));

        result = mvc.perform(get("/api/invoice")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests delete non-existing invoice.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteNonExistingInvoiceTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/invoice/999")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"status\":404,\"error\":\"invoice_not_found\"}"));
    }

    /**
     * Tests delete invoice as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void deleteInvoiceAsTLTest() throws Exception {
        mvc.perform(delete("/api/invoice/1")).andExpect(status().isForbidden()).andReturn();
    }

    /**
     * Tests delete invoice as Accountant.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"ACCOUNTING"})
    void deleteInvoiceAsAccountantTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/invoice/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"invoice_deleted_successfully\"}"));

        result = mvc.perform(get("/api/invoice")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests create invalid invoice.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createInvalidInvoiceTest() throws Exception {
        invoiceDTO.setClient("");

        MvcResult result = mvc.perform(post("/api/invoice").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(invoiceDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"client\",\"message\":\"client_is_required\"}"));
    }

    /**
     * Tests create invoice.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createInvoiceTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/invoice").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(invoiceDTO))).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"invoice_created_successfully\"}"));

        result = mvc.perform(get("/api/invoice")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"client\":\"Client\",\"amount\":100.0,\"dueDate\":\"2025-03-04T15:33:09Z\",\"status\":\"PAID\"},"));
        assertTrue(result.getResponse().getContentAsString().contains("{\"id\":2,\"client\":\"Client\",\"amount\":10.0,\"dueDate\":"));
        assertTrue(result.getResponse().getContentAsString().contains("\"status\":\"PENDING\""));
    }

    /**
     * Tests update invoice status.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void updateInvoiceStatusTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/invoice/1/update?status=PENDING").contentType(
                APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"invoice_status_updated_successfully\"}"));

        result = mvc.perform(get("/api/invoice")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"client\":\"Client\",\"amount\":100.0,\"dueDate\":\"2025-03-04T15:33:09Z\",\"status\":\"PENDING\"}]"));
    }
}
