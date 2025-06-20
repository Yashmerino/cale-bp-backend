package com.mlc.vdsr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.vdsr.dto.EventDTO;
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
 * Test suite for {@link EventController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class EventControllerTest {
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
     * Event DTO.
     */
    private EventDTO eventDTO;


    /**
     * Setup before each test.
     */
    @BeforeEach
    void setup() {
        eventDTO = new EventDTO();
        eventDTO.setTitle("Event");
        eventDTO.setDate(Instant.now());
        eventDTO.setIsImportant(true);
    }

    /**
     * Tests get all events
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void getAllEventsTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/event")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Event\""));
    }

    /**
     * Tests get all events as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void getAllEventsAsTLTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/event")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Event\""));
    }

    /**
     * Tests delete event.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteEventTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/event/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"event_deleted_successfully\"}"));

        result = mvc.perform(get("/api/event")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests delete non-existing event.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void deleteNonExistingEventTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/event/999")).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"status\":404,\"error\":\"event_not_found\"}"));
    }

    /**
     * Tests delete event as team leader.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"TL"})
    void deleteEventAsTLTest() throws Exception {
        mvc.perform(delete("/api/event/1")).andExpect(status().isForbidden()).andReturn();
    }

    /**
     * Tests delete event as HR.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"HR"})
    void deleteEventAsHRTest() throws Exception {
        MvcResult result = mvc.perform(delete("/api/event/1")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"event_deleted_successfully\"}"));

        result = mvc.perform(get("/api/event")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[]"));
    }

    /**
     * Tests create invalid event.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createInvalidEventTest() throws Exception {
        eventDTO.setTitle("");

        MvcResult result = mvc.perform(post("/api/event").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(eventDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"title\",\"message\":\"title_is_required\"}"));
        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"title\",\"message\":\"title_length_invalid\"}"));
    }

    /**
     * Tests create event.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void createEventTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/event").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(eventDTO))).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"status\":200,\"message\":\"event_created_successfully\"}"));

        result = mvc.perform(get("/api/event")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"title\":\"Event\",\"date\":\""));
        assertTrue(result.getResponse().getContentAsString().contains(",{\"id\":2,\"title\":\"Event\",\"date\":\""));
        assertTrue(result.getResponse().getContentAsString().contains("\"isImportant\":false"));
        assertTrue(result.getResponse().getContentAsString().contains("\"isImportant\":true"));
    }
}
