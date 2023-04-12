package com.example.lr_3.service.impl;

import java.util.UUID;

public interface IVerificationService {
    void confirm(String token);
    void createVerificationToken(UUID userId);
    boolean shouldRemove(UUID id);
}
