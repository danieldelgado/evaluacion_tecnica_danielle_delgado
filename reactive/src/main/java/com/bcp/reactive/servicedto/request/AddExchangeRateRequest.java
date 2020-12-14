package com.bcp.reactive.servicedto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddExchangeRateRequest {
    private String name;
    private String origin;
    private String exchange;
    private Double typeExchange;
}
