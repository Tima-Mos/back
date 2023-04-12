package com.example.lr_3.filter;

import com.example.lr_3.service.UserService;
import com.example.lr_3.service.VerificationService;
import com.example.lr_3.service.impl.UserCrudService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserVerificationFilter extends OncePerRequestFilter {
    private final VerificationService verificationService;
    private final UserCrudService userCrudService;
    @Autowired
    public UserVerificationFilter(VerificationService verificationService, UserService userCrudService) {
        this.verificationService = verificationService;
        this.userCrudService = userCrudService;
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().equals("/auth/reg/mail/confirm");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("auth = " + authentication);
        boolean shouldRemoveUser = false;
        String username = null;
        if(authentication != null){
            username = authentication.getName();
            if(!verificationService.isConfirmed(username)){
                if(!verificationService.isNotExpired(username)){
                    shouldRemoveUser = true;
                }
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "UNCONFIRMED ACCOUNT");
            }
        }
        filterChain.doFilter(request, response);
        if(shouldRemoveUser){
            userCrudService.removeByUsername(username);
        }
    }
}
