package com.example.lr_3.filter;

import com.example.lr_3.security.UserDetailsServiceImpl;
import com.example.lr_3.service.JwtTokenService;
import com.example.lr_3.service.TokenParserFromRequest;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component

public class JwtRequestFilter extends OncePerRequestFilter {
    private final TokenParserFromRequest tokenParserFromRequest;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;



    @Autowired
    public JwtRequestFilter(TokenParserFromRequest tokenParserFromRequest,
                            JwtTokenService jwtTokenService, UserDetailsServiceImpl userDetailsService) {
        this.tokenParserFromRequest = tokenParserFromRequest;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        String jwtToken = tryGetToken(request);
        try {
            tryAuthenticate(request,jwtToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
            System.out.println("Refreshing token");
            tryAuthenticate(request,
                    jwtTokenService
                            .refreshToken(jwtToken)
                            .getValue());
        }
        chain.doFilter(request, response);
    }
    private void tryAuthenticate(HttpServletRequest request, String jwtToken){
        UUID userId = jwtTokenService.getIdFromToken(jwtToken);
        if (SecurityContextHolder.getContext().getAuthentication() == null
                && jwtTokenService.tokenExists(jwtToken)) {
            if (jwtTokenService.validateToken(jwtToken, userId)) {
                authenticate(
                        userDetailsService.loadUserById(userId),
                        request);
                System.out.println("in filter: " + SecurityContextHolder.getContext().getAuthentication());
            }
        }
    }
    private String tryGetToken(HttpServletRequest request){
        String jwtToken;
        try {
            jwtToken = tokenParserFromRequest.parse(request);
        }
        catch (IllegalArgumentException e){
            jwtToken = null;
        }
        return jwtToken;
    }
    private void authenticate(UserDetails user, HttpServletRequest request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
        usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        System.out.println(request.getRequestURI());
        return request.getRequestURI().equals("/auth/reg");
    }
}
