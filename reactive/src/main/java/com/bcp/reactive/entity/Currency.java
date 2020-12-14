package com.bcp.reactive.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "currency")
    private String currency;
    
    
}
