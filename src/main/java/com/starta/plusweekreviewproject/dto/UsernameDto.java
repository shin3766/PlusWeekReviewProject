package com.starta.plusweekreviewproject.dto;

import com.starta.plusweekreviewproject.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameDto {
    private String username;

    public UsernameDto(User user) {
        this.username = user.getUsername();
    }
}