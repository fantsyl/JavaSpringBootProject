package com.cathy.interviewproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity
@Table
@Getter
@Setter
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String currency;
    @Column(name = "currency_name_cn")
    private String currency_name_cn;
    @Column(name = "exchange_rate")
    private Float exchange_rate;
    @Column(name = "updated_date")
    private Date updated_date;

    public Currency(String currency) {
        this.currency = currency;
    }

}
