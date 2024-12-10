package com.mlc.vdsr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.vdsr.dto.auth.LoginDTO;
import com.mlc.vdsr.dto.auth.RegisterDTO;
import com.mlc.vdsr.utils.RoleEnum;
import jakarta.transaction.Transactional;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests {@link AuthController} endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("test")
class AuthControllerTest {

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
     * Register DTO.
     */
    private RegisterDTO registerDTO;

    /**
     * Login DTO.
     */
    private LoginDTO loginDTO;

    @BeforeEach
    void setup() {
        registerDTO = new RegisterDTO();
        registerDTO.setRoles(new HashSet<>(Set.of(RoleEnum.HR)));
        registerDTO.setFirstName("test123");
        registerDTO.setLastName("test123");
        registerDTO.setUsername("test123");
        registerDTO.setPassword("test123");
        registerDTO.setEmail("test@test.test");

        loginDTO = new LoginDTO();
        loginDTO.setUsername("test123");
        loginDTO.setPassword("test123");
    }

    /**
     * Tests that /register endpoint works as expected.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void registerSuccessfulTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("{\"status\":200,\"message\":\"user_registered_successfully\"}"));
    }

    /**
     * Tests /register with existing user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void registerExistingUserTest() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isOk());

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isConflict()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\",\"status\":409,\"error\":\"username_already_taken\"}"));
    }

    /**
     * Test if an email has no sign, is invalid and is not provided.
     */
    @ParameterizedTest
    @ValueSource(strings = {"nosign_email", "@invalid.mail", ""})
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void registerNoSignEmailTest() throws Exception {
        registerDTO.setEmail("nosign_email");

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"fieldErrors\":[{\"field\":\"email\",\"message\":\"email_is_invalid\"}]}"));
    }

    /**
     * Tests /register without username.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void registerNoUsername() throws Exception {
        registerDTO.setUsername("");

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"username\",\"message\":\"username_is_required\"}"));
        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"username\",\"message\":\"username_length_invalid\"}"));
    }

    /**
     * Tests /register without body.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void registerNoRequestBodyTest() throws Exception {
        mvc.perform(post("/api/auth/register")).andExpect(status().isBadRequest()).andReturn();
    }

    /**
     * Tests /register without password.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void registerNoPasswordTest() throws Exception {
        registerDTO.setPassword("");

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"password\",\"message\":\"password_length_invalid\"}"));
        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"password\",\"message\":\"password_is_required\"}"));
    }

    /**
     * Tests that /login endpoint works as expected.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void loginSuccessfulTest() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isOk());

        MvcResult result = mvc.perform(post("/api/auth/login").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Bearer "));
    }

    /**
     * Tests /login with a non-existing username.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void usernameDoesntExistTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/auth/login").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(",\"status\":404,\"error\":\"username_not_found\"}"));
    }

    /**
     * Tests /login without username.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void loginNoUsernameTest() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isOk());

        loginDTO.setUsername("");

        MvcResult result = mvc.perform(post("/api/auth/login").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"fieldErrors\":[{\"field\":\"username\",\"message\":\"username_is_required\"}]}"));
    }

    /**
     * Tests /login without password.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void loginNoPasswordTest() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isOk());

        loginDTO.setPassword("");

        MvcResult result = mvc.perform(post("/api/auth/login").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"fieldErrors\":[{\"field\":\"password\",\"message\":\"password_is_required\"}]}"));
    }

    /**
     * Tests /register with a username that is too long
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void registerUsernameTooLongTest() throws Exception {
        registerDTO.setUsername(RandomString.make(512));

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"fieldErrors\":[{\"field\":\"username\",\"message\":\"username_length_invalid\"}]}"));
    }

    /**
     * Tests /register with a username that is too short
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "owner", authorities = {"OWNER"})
    void registerUsernameTooShortTest() throws Exception {
        registerDTO.setUsername("baa");

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"username\",\"message\":\"username_length_invalid\"}"));
    }

    /**
     * Tests that only owner can register users.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void registerWithoutRoleTest() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isUnauthorized()).andReturn();
    }
}