//package com.example.demo.security
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.web.SecurityFilterChain
//
//
//@Configuration
//@EnableWebSecurity
//open class SecurityConfig {
//    @Bean
//    @Throws(Exception::class)
//    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
//        http.authorizeHttpRequests().anyRequest().permitAll().and().cors().disable()
//
//        return http.build()
//    }
//}