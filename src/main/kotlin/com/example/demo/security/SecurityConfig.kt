package com.example.demo.security

import com.example.demo.security.filters.AuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.bind.annotation.CrossOrigin


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@CrossOrigin("http://localhost:3000")
open class SecurityConfig(private val authenticationFilter: AuthenticationFilter) {
    @Bean
    @Throws(Exception::class)
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http.addFilterBefore(
            authenticationFilter, BasicAuthenticationFilter::class.java
        )
        http
            .authorizeHttpRequests()
            .anyRequest().permitAll()
            .and().cors()
        return http.build()
    }


}