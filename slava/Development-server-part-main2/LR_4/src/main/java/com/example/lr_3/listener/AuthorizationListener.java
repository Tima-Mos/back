package com.example.lr_3.listener;

import com.example.lr_3.event.OnAuthorizationEvent;
import com.example.lr_3.service.VerificationService;
import com.example.lr_3.service.impl.UserCrudService;
import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationListener {
    private final VerificationService verificationService;
    private final UserCrudService userCrudService;
    public AuthorizationListener(UserCrudService userCrudService,
                                 VerificationService verificationService) {
        this.userCrudService = userCrudService;
        this.verificationService = verificationService;
    }
    @EventListener
    @Transactional
    public void onApplicationEvent(OnAuthorizationEvent event) {
        checkConfirmed(event);
    }
    private void checkConfirmed(OnAuthorizationEvent event){
        if(verificationService.shouldRemove(event.getAuthDto().getEmail())){
            userCrudService.removeByUsername(event.getAuthDto().getEmail());
        }
    }
}
