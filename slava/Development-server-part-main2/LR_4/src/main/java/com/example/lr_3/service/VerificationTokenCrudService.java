package com.example.lr_3.service;

import com.example.lr_3.dao.VerificationTokenRepository;
import com.example.lr_3.entity.VerificationToken;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class VerificationTokenCrudService {
    private final VerificationTokenRepository verificationTokenRepository;
    @Autowired
    public VerificationTokenCrudService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }
    public VerificationToken getByUserId(UUID id){
        return verificationTokenRepository
                .findVerificationTokenByUserId(id)
                .orElseThrow(
                        ()-> new EntityExistsException(
                                String.format("Verification token with user id: %s doesn't exists", id)));
    }
    public VerificationToken create(VerificationToken verificationToken){
        if( verificationToken.getId() != null
                && verificationTokenRepository.existsById(verificationToken.getId())){
            throw new EntityExistsException(
                    "Verification token with id:'"
                            + verificationToken.getId()
                            + "' already exists");
        }
        verificationToken.setExpiryDate(expirationDate());
        return verificationTokenRepository.save(verificationToken);
    }
    private Date expirationDate(){
        return new Date(new Date().getTime() + 24*60*60*1000);
    }
    public VerificationToken merge(VerificationToken verificationToken){
        return verificationTokenRepository.save(verificationToken);
    }
    public VerificationToken getById(UUID id){
        return verificationTokenRepository.findById(id).orElseThrow(()->{
            throw new EntityExistsException(String.format("Token with id: %s doesn't exists", id));
        });
    }
    public Date getExpirationDate(UUID id){
        return getById(id).getExpiryDate();
    }
}
