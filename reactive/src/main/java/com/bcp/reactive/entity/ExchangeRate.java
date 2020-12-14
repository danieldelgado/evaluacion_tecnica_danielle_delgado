package com.bcp.reactive.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exchange_rate")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")    
    private Long id;

    @Column(name = "originCurrencyId")
    private Long originCurrencyId;


    @Column(name = "exchangeCurrencyId")
    private Long exchangeCurrencyId;

    @Column(name = "typeExchange")
    private Double typeExchange;
    
    
}
