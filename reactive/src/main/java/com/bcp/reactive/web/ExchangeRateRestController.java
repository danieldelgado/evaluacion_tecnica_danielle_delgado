package com.bcp.reactive.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcp.reactive.service.CurrencyService;
import com.bcp.reactive.service.ExchangeRateService;
import com.bcp.reactive.servicedto.request.ExchangeRateAmountRequest;
import com.bcp.reactive.servicedto.request.UpdateExchangeRateRequest;
import com.bcp.reactive.servicedto.response.ExchangeRateResponse;
import com.bcp.reactive.webdto.response.BaseWebResponse;
import com.bcp.reactive.webdto.response.ExchangeRateWebResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@RestController
@RequestMapping(value = "/api/exchange")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})

public class ExchangeRateRestController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
	private CurrencyService currencyService;

    @PostMapping(
            /*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE
    ) public Single<ResponseEntity<BaseWebResponse<ExchangeRateWebResponse>>> exchange(
        @RequestBody ExchangeRateAmountRequest req) {
        return exchangeRateService.exchange(req).subscribeOn(Schedulers.io())
                .map(bookResponses -> ResponseEntity.ok(BaseWebResponse.successWithData(toWebResponse(bookResponses))));     
    }
    
    
    

    @PatchMapping(
            /*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE
    ) public Single<ResponseEntity<BaseWebResponse<ExchangeRateWebResponse>>> updateExchange(
        @RequestBody UpdateExchangeRateRequest req) {
        return exchangeRateService.update(req).subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));     
    }
    
    @PostMapping(path = "/list",
            /*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE
    ) public Single<Object> exchangeUpdateList(
        @RequestBody List<UpdateExchangeRateRequest> req) {
    	    	
    	return exchangeRateService.exchangeUpdateList(req)
    			.map(bookResponses -> ResponseEntity.ok(BaseWebResponse.successWithData(toWebResponseList(bookResponses))));
    }
    
    
    
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse<List<ExchangeRateWebResponse>>>> getAll(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                                                      @RequestParam(value = "page", defaultValue = "0") int page) {
        return exchangeRateService.getAll(limit, page)
                .subscribeOn(Schedulers.io())
                .map(bookResponses -> ResponseEntity.ok(BaseWebResponse.successWithData(toWebResponseList(bookResponses))));
    }

    private List<ExchangeRateWebResponse> toWebResponseList(List<ExchangeRateResponse> bookResponseList) {
        return bookResponseList
                .stream()
                .map(this::toWebResponse)
                .collect(Collectors.toList());
    }

    private ExchangeRateWebResponse toWebResponse(ExchangeRateResponse bookResponse) {
    	ExchangeRateWebResponse bookWebResponse = new ExchangeRateWebResponse();
        BeanUtils.copyProperties(bookResponse, bookWebResponse);
        bookWebResponse.setExchange(currencyService.getCurrency(bookResponse.getExchangeCurrencyId()));
        bookWebResponse.setOrigin(currencyService.getCurrency(bookResponse.getOriginCurrencyId()));
        return bookWebResponse;
    }


}
