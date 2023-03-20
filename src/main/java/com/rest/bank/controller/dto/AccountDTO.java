package com.rest.bank.controller.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
public class AccountDTO {

    @NotNull
    private Long accountNumber;

    @NotNull
    private BigDecimal depositAmount;
}

