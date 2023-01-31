package com.example.demo.security

import com.example.demo.security.filters.AuthenticationFilter
import com.example.demo.security.filters.CorsFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.cors.CorsUtils
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebSecurity
@CrossOrigin("http://localhost:3000")
open class SecurityConfig(private val authenticationFilter: AuthenticationFilter, private val corsFilter: CorsFilter) {
    @Bean
    @Throws(Exception::class)
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http.addFilterBefore(
            authenticationFilter, BasicAuthenticationFilter::class.java
        )
        http.addFilterBefore(
            corsFilter, BasicAuthenticationFilter::class.java
        )
        http.authorizeHttpRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .anyRequest().permitAll()

        return http.build()
    }


}