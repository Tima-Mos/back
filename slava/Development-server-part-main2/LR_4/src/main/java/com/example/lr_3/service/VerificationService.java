package com.example.lr_3.service;

import com.example.lr_3.entity.User;
import com.example.lr_3.entity.VerificationToken;
import com.example.lr_3.service.impl.IVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class VerificationService implements IVerificationService {
    private final VerificationTokenCrudService verificationTokenCrudService;
    private final UserService userService;
    @Autowired
    public VerificationService(VerificationTokenCrudService verificationTokenCrudService, UserService userService) {
        this.verificationTokenCrudService = verificationTokenCrudService;
        this.userService = userService;
    }
    @Override
    public void confirm(String token) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        VerificationToken verificationToken = verificationTokenCrudService.getByUserId(user.getId());
        if(!token.equals(verificationToken.getToken())){
            throw new IllegalArgumentException("Incorrect token for user: "+ user.getEmail());
        }
        verificationToken.setConfirmed(true);
        verificationTokenCrudService.merge(verificationToken);
    }
    @Override
    public void createVerificationToken(UUID userId) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(createRandomTokenFromUUID());
        verificationToken.setConfirmed(false);
        User user = userService.getById(userId);
        verificationToken.setUser(user);
        verificationTokenCrudService.create(verificationToken);
    }
    @Override
    public boolean shouldRemove(UUID id) {
        return !isNotExpired(verificationTokenCrudService.getById(id).getUser().getEmail()) &&
                !isConfirmed(verificationTokenCrudService.getById(id).getUser().getId());
    }
    public boolean shouldRemove(String username){
        return shouldRemove(verificationTokenCrudService.getByUserId(userService.getByUsername(username).getId()).getId());
    }
    public String getVerificationTokenValueByUserId(UUID userId){
        return verificationTokenCrudService.getByUserId(userId).getToken();
    }
    public boolean isConfirmed(UUID userId){
        return verificationTokenCrudService.getByUserId(userId).isConfirmed();
    }
    public boolean isConfirmed(String username){
        return isConfirmed(userService.getByUsername(username).getId());
    }
    public boolean isNotExpired(String username) {
        VerificationToken verificationToken = verificationTokenCrudService
                .getByUserId(
                        userService.getByUsername(username).getId()
                );
        return !verificationToken.getExpiryDate().before(new Date());
    }
    private String createRandomTokenFromUUID(){
        return UUID.randomUUID().toString();
    }
}
