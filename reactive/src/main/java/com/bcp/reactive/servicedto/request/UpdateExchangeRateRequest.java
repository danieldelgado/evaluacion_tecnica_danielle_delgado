package com.bcp.reactive.servicedto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExchangeRateRequest {
    private Long id;
    private String origin;
    private String exchange;
    private Long originCurrencyId;
    private Long exchangeCurrencyId;
    private Double typeExchange;
    private Boolean isNew;
}
