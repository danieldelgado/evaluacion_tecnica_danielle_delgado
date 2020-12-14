package com.bcp.reactive.servicedto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateAmountRequest {
    
    private Double amount;
    private Long originCurrencyId;
    private Long exchangeCurrencyId;
    
    

}
