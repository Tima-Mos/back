package com.example.lr_3.event;

import com.example.lr_3.dto.RegDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnRegistrationEvent {
    private RegDto regDto;
    public OnRegistrationEvent(RegDto regDto){
        this.regDto = regDto;
    }
}
