package com.example.lr_3.event;

import com.example.lr_3.dto.AuthDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnAuthorizationEvent {
    private AuthDto authDto;
    public OnAuthorizationEvent(AuthDto authDto){
        this.authDto = authDto;
    }
}
