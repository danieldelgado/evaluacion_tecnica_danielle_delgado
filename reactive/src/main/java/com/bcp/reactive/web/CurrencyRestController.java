package com.bcp.reactive.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcp.reactive.service.CurrencyService;
import com.bcp.reactive.service.ExchangeRateService;
import com.bcp.reactive.servicedto.request.ExchangeRateAmountRequest;
import com.bcp.reactive.servicedto.request.UpdateExchangeRateRequest;
import com.bcp.reactive.servicedto.response.CurrencyResponse;
import com.bcp.reactive.servicedto.response.ExchangeRateResponse;
import com.bcp.reactive.webdto.response.BaseWebResponse;
import com.bcp.reactive.webdto.response.CurrencyWebResponse;
import com.bcp.reactive.webdto.response.ExchangeRateWebResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@RestController
@RequestMapping(value = "/api/currency")
public class CurrencyRestController {

	@Autowired
	private CurrencyService currencyService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Single<ResponseEntity<BaseWebResponse<List<CurrencyWebResponse>>>> getAll() {
		return currencyService.getAll().subscribeOn(Schedulers.io()).map(
				bookResponses -> ResponseEntity.ok(BaseWebResponse.successWithData(toWebResponseList(bookResponses))));
	}

	private List<CurrencyWebResponse> toWebResponseList(List<CurrencyResponse> bookResponseList) {
		return bookResponseList.stream().map(this::toWebResponse).collect(Collectors.toList());
	}

	private CurrencyWebResponse toWebResponse(CurrencyResponse bookResponse) {
		CurrencyWebResponse bookWebResponse = new CurrencyWebResponse();
		BeanUtils.copyProperties(bookResponse, bookWebResponse);
		return bookWebResponse;
	}

}
