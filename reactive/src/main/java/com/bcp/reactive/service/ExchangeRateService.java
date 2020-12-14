package com.bcp.reactive.service;

import java.util.List;

import com.bcp.reactive.servicedto.request.AddExchangeRateRequest;
import com.bcp.reactive.servicedto.request.ExchangeRateAmountRequest;
import com.bcp.reactive.servicedto.request.UpdateExchangeRateRequest;
import com.bcp.reactive.servicedto.response.ExchangeRateResponse;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ExchangeRateService {

    Single<List<ExchangeRateResponse>> getAll(int limit, int page);
    
    Completable update(UpdateExchangeRateRequest req);

    Single<List<ExchangeRateResponse>> exchangeUpdateList(List<UpdateExchangeRateRequest> reqs);
    
	Single<ExchangeRateResponse> exchange(ExchangeRateAmountRequest req);

	
}
