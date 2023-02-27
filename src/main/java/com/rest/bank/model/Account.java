package com.rest.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    private int documentId;

    private String name;

    private BigDecimal balance;

    private Date creationDate;

    @Id
    private Long accountNum;
}
