package com.mlc.vdsr.security;

import com.mlc.vdsr.utils.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Security configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Endpoints for Swagger UI.
     */
    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    /**
     * Endpoints for Actuator.
     */
    private static final String[] ACTUATOR_WHITELIST = {
            "/actuator/**",
            "/actuator/health",
            "/actuator/info"
    };

    /**
     * Regex for all the endpoints related to authentication/authorization.
     */
    private static final String AUTH_ALL_ENDPOINTS = "/api/auth/**";

    /**
     * Regex for all the endpoints related to users.
     */
    private static final String USERS_ALL_ENDPOINTS = "/api/user/**";

    /**
     * Regex for all the endpoints related to events.
     */
    private static final String EVENTS_ALL_ENDPOINTS = "/api/event/**";

    /**
     * Regex for all the endpoints related to recruitments.
     */
    private static final String RECRUITMENTS_ALL_ENDPOINTS = "/api/recruitment/**";

    /**
     * Regex for all the endpoints related to projects.
     */
    private static final String PROJECTS_ALL_ENDPOINTS = "/api/project/**";

    /**
     * Regex for all the endpoints related to payroll.
     */
    private static final String PAYROLL_ALL_ENDPOINTS = "/api/payroll/**";

    /**
     * Regex for all the endpoints related to employee records.
     */
    private static final String EMPLOYEE_RECORD_ALL_ENDPOINTS = "/api/employeeRecord/**";

    /**
     * Regex for all the endpoints related to invoices.
     */
    private static final String INVOICE_ALL_ENDPOINTS = "/api/invoice/**";

    /**
     * Regex for all the endpoints related to budgets.
     */
    private static final String BUDGET_ALL_ENDPOINTS = "/api/budget/**";

    /**
     * Regex for all the endpoints related to expenses.
     */
    private static final String EXPENSE_ALL_ENDPOINTS = "/api/expense/**";

    /**
     * Jwt Auth Entry Point to handle exceptions.
     */
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    /**
     * JWT Token generator.
     */
    private final JwtProvider tokenGenerator;

    /**
     * Custom user details service.
     */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Array that contains the names of all roles
     */
    private final String[] ALL_ROLES = Stream.of(RoleEnum.values())
            .map(Enum::name)
            .toArray(String[]::new);

    /**
     * Constructor.
     *
     * @param jwtAuthEntryPoint        is the auth entry point.
     * @param tokenGenerator           is the token generator.
     * @param customUserDetailsService is the service that deals with user's details.
     */
    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint, JwtProvider tokenGenerator, CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.tokenGenerator = tokenGenerator;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Security filter.
     *
     * @param http is the Http Security config object.
     * @return Security Filter Chain.
     * @throws Exception if certain settings couldn't be changed.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> corsConfigurationSource())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(HttpMethod.GET, USERS_ALL_ENDPOINTS).hasAnyAuthority(ALL_ROLES);
                    request.requestMatchers(HttpMethod.GET, EVENTS_ALL_ENDPOINTS).hasAnyAuthority(ALL_ROLES);
                    request.requestMatchers(HttpMethod.GET, RECRUITMENTS_ALL_ENDPOINTS).hasAnyAuthority(ALL_ROLES);
                    request.requestMatchers(HttpMethod.GET, PROJECTS_ALL_ENDPOINTS).hasAnyAuthority(ALL_ROLES);
                    request.requestMatchers(HttpMethod.GET, PAYROLL_ALL_ENDPOINTS).hasAnyAuthority(ALL_ROLES);
                    request.requestMatchers(HttpMethod.GET, EMPLOYEE_RECORD_ALL_ENDPOINTS).hasAnyAuthority(ALL_ROLES);
                    request.requestMatchers(HttpMethod.GET, INVOICE_ALL_ENDPOINTS).hasAnyAuthority(ALL_ROLES);
                    request.requestMatchers(HttpMethod.GET, BUDGET_ALL_ENDPOINTS).hasAnyAuthority(ALL_ROLES);
                    request.requestMatchers(HttpMethod.GET, EXPENSE_ALL_ENDPOINTS).hasAnyAuthority(ALL_ROLES);
                    request.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
                    request.requestMatchers(HttpMethod.POST, "/api/auth/register").hasAnyAuthority(RoleEnum.OWNER.name());
                    request.requestMatchers(HttpMethod.DELETE, USERS_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name());
                    request.requestMatchers(HttpMethod.POST, EVENTS_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.HR.name(), RoleEnum.HR_LEADER.name());
                    request.requestMatchers(HttpMethod.DELETE, EVENTS_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.HR.name(), RoleEnum.HR_LEADER.name());
                    request.requestMatchers(HttpMethod.POST, RECRUITMENTS_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.HR.name(), RoleEnum.HR_LEADER.name());
                    request.requestMatchers(HttpMethod.DELETE, RECRUITMENTS_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.HR.name(), RoleEnum.HR_LEADER.name());
                    request.requestMatchers(HttpMethod.POST, PROJECTS_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name());
                    request.requestMatchers(HttpMethod.DELETE, PROJECTS_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name());
                    request.requestMatchers(HttpMethod.POST, PAYROLL_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.HR.name(), RoleEnum.HR_LEADER.name());
                    request.requestMatchers(HttpMethod.DELETE, PAYROLL_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.HR.name(), RoleEnum.HR_LEADER.name());
                    request.requestMatchers(HttpMethod.POST, EMPLOYEE_RECORD_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.HR.name(), RoleEnum.HR_LEADER.name());
                    request.requestMatchers(HttpMethod.DELETE, EMPLOYEE_RECORD_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.HR.name(), RoleEnum.HR_LEADER.name());
                    request.requestMatchers(HttpMethod.POST, INVOICE_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.ACCOUNTING.name(), RoleEnum.ACCOUNTING_LEADER.name());
                    request.requestMatchers(HttpMethod.DELETE, INVOICE_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.ACCOUNTING.name(), RoleEnum.ACCOUNTING_LEADER.name());
                    request.requestMatchers(HttpMethod.POST, BUDGET_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.ACCOUNTING.name(), RoleEnum.ACCOUNTING_LEADER.name());
                    request.requestMatchers(HttpMethod.DELETE, BUDGET_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.ACCOUNTING.name(), RoleEnum.ACCOUNTING_LEADER.name());
                    request.requestMatchers(HttpMethod.POST, EXPENSE_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.ACCOUNTING.name(), RoleEnum.ACCOUNTING_LEADER.name());
                    request.requestMatchers(HttpMethod.DELETE, EXPENSE_ALL_ENDPOINTS).hasAnyAuthority(RoleEnum.OWNER.name(), RoleEnum.ACCOUNTING.name(), RoleEnum.ACCOUNTING_LEADER.name());
                    request.requestMatchers(SWAGGER_WHITELIST).permitAll();
                    request.requestMatchers(ACTUATOR_WHITELIST).permitAll();
                    request.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Configure the CORS policy.
     *
     * @return <code>CorsConfigurationSource</code>
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setExposedHeaders(Arrays.asList("X-Auth-Token", "Authorization", "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * Gives the authentication manager.
     *
     * @param authenticationConfiguration is the auth configuration.
     * @return authentication manager.
     * @throws Exception if manager couldn't be obtained.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Generates an BCrypt password encoder.
     *
     * @return an BCrypt password encoder.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    public JwtAuthFilter jwtAuthenticationFilter() {
        return new JwtAuthFilter(tokenGenerator, customUserDetailsService);
    }
}