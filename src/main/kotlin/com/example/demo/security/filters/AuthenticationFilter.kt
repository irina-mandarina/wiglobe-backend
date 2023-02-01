package com.example.demo.security.filters

import com.example.demo.services.JWTService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationFilter(private val jwtService: JWTService): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (!request.requestURI.contains("login") && !request.requestURI.contains("signup")) {
            println(request.requestURI)
            println( request.getHeader("Authentication"))
            try {
                println("!!!!!!!!!!" + request.getHeader("Authentication").replace("Bearer ", ""))
                if (!jwtService.JWTisValid(request.getHeader("Authentication").replace("Bearer ", ""))) {
                    response.status = 401
                    return
                }
                else {
                    request
                        .setAttribute("username",
                            jwtService.getSubject(
                                request.getHeader("Authentication").removePrefix("Bearer "))
                        )
                }
            }
            catch (e: Exception) {
                response.status = 401
                return
            }
        }
        filterChain.doFilter(request, response)
        return
    }

}