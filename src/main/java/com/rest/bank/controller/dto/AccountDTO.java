package com.rest.bank.controller.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
public class AccountDTO {

    @NotNull
    private int accountNumber;

    @NotNull
    private int depositAmount;
}

