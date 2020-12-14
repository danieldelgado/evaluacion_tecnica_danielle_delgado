package com.bcp.reactive.webdto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeRateWebResponse {
    private String id;
    private String origin;
    private String exchange;
    private Double typeExchange;
    private Double exchangeAmount;
	private Boolean isNew;
}
