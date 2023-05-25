package com.rest.bank.controller.dto;

import com.sun.istack.NotNull;
import lombok.*;


@Data
public class DepositDTO {

    @NotNull
    private int accountNumber;

    @NotNull
    private int depositAmount;
}

