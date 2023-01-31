package com.example.demo.security.filters

import com.example.demo.services.SessionService
import com.google.common.io.CharStreams
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.NullPointerException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationFilter(private val sessionService: SessionService): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (!request.requestURI.contains("login") && !request.requestURI.contains("signup")) {

            println( request.getHeader("Authentication"))
            try {
                if (!sessionService.JWTisValid(request.getHeader("Authentication").removePrefix("Bearer "))) {
                    response.status = 401
                    return
                }
            }
            catch (e: NullPointerException) {
                response.status = 401
                return
            }
        }
        filterChain.doFilter(request, response)
    }

}