package com.rest.bank.feign;

import com.rest.bank.controller.dto.DepositDTO;
import com.rest.bank.controller.dto.TransferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "transaction-service",url = "http://localhost:8081")
public interface TransactionFeign {

    @PutMapping(path = "/deposit")
    String depositFunds(@RequestBody DepositDTO depositDTO);

    @PutMapping(path = "/transfer")
    String transfer(@RequestBody TransferDTO transferDTO) throws Exception;


}