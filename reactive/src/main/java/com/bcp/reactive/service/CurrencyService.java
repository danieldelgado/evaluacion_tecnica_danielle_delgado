package com.bcp.reactive.service;

import java.util.List;

import com.bcp.reactive.servicedto.response.CurrencyResponse;

import io.reactivex.Single;

public interface CurrencyService {

	Single<List<CurrencyResponse>> getAll();

	String getCurrency(Long exchangeCurrencyId);

}
