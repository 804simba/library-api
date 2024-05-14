package com.simba.libraryapi.core.security;

import com.klosamart.klosamart.commons.constants.SystemConstants;
import com.klosamart.klosamart.core.config.security.jwt.DefaultJwtAuthenticationFilter;
import com.klosamart.klosamart.rest.service.KlosamartUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class CoreSecurityConfigurationAdapter {

    private final DefaultJwtAuthenticationFilter jwtAuthenticationFilter;

    private final DefaultAuthenticationEntryPoint authenticationEntryPoint;

    private final KlosamartUserService klosamartUserService;

    private final PasswordEncoder passwordEncoder;

    private final String[] allowedEndpoints = new String[]{"/v2/api-docs",
            "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**",
            "/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**",
            "/swagger-ui.html", "/", "/home", "/api/v1/users/**"};

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(klosamartUserService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        // TODO: test this, and see how it works
        // https://stackoverflow.com/questions/76313603/how-to-set-role-hierarchy-in-spring-security-6-1-0-spring-boot-3-1-0
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        String appHierarchyOrder = String.format("%s > %s > %s > %s > %s", SystemConstants.Roles.ROLE_ADMIN, SystemConstants.Roles.ROLE_VENDOR,
                SystemConstants.Roles.ROLE_CUSTOMER, SystemConstants.Roles.ROLE_AGENT, SystemConstants.Roles.ROLE_RIDER);
        hierarchy.setHierarchy(appHierarchyOrder);
        return hierarchy;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry.requestMatchers(allowedEndpoints).permitAll()
                                .anyRequest().authenticated()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(LogoutConfigurer::permitAll);
        return httpSecurity.build();
    }
}
