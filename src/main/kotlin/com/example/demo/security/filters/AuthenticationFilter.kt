package com.example.demo.security.filters

import com.example.demo.services.JWTService
import com.example.demo.services.UserService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationFilter(private val jwtService: JWTService, private val userService: UserService): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (!request.requestURI.contains("login") && !request.requestURI.contains("signup")) {
            try {
                if (!jwtService.JWTisValid(request.getHeader("Authentication").replace("Bearer ", ""))) {
                    response.status = 401
                    return
                }
                else {
                    val username = jwtService.getSubject(
                        request.getHeader("Authentication").removePrefix("Bearer "))
                    if (!userService.userWithUsernameExists(username)) {
                        response.status = 401
                        return
                    }
                    request
                        .setAttribute("username",
                            username
                        )
                }
            }
            catch (e: Exception) {
                response.status = 401
                return
            }
        }
        if (request.requestURI.contains("login/google")) {
            try {
                val token = (request.getHeader("Authentication").removePrefix("Bearer "))
                if (jwtService.googleJWTIsValid(token)) {
                    request.setAttribute("token", token)
                }
                else {
                    response.status = 401
                    return
                }
            } catch (e: Exception) {
                response.status = 401
                return
            }
        }
        filterChain.doFilter(request, response)
        return
    }

}