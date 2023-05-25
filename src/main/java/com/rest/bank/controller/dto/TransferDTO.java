package com.rest.bank.controller.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferDTO {

    @NotNull
    private int origin;

    @NotNull
    private int destination;

    @NotNull
    private int amount;
}
