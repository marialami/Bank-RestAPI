package com.rest.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "`ACCOUNT`")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`user`")
    private User user;
    private int money;

    private Date date_created;

    private String type;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
}
