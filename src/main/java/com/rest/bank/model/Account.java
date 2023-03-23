package com.rest.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal balance;

    private Date creationDate;

    @Id
    private Long accountNum;
}
