package com.rest.bank.controller.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    @NotNull
    private int documentId;

    @NotNull
    private String name;
}
