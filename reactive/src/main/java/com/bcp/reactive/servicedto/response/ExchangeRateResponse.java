package com.bcp.reactive.servicedto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponse {
	private String id;
	private Long originCurrencyId;
	private Long exchangeCurrencyId;
	private Double typeExchange;
	private Double exchangeAmount;
	private Boolean isNew;
}
