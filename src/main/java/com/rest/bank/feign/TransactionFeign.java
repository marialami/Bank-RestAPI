package com.rest.bank.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "transaction-service",url = "http://localhost:8081")
public class TransactionFeign {
}
