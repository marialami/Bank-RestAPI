package com.rest.bank.controller.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    @NotNull
    private int document;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

}
