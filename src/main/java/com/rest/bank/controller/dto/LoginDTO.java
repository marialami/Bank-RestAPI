package com.rest.bank.controller.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginDTO {

    @NotNull
    private int document;

    @NotNull
    private String password;
}
