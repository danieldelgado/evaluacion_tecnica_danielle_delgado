package com.bcp.reactive.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcp.reactive.entity.Currency;
import com.bcp.reactive.repository.CurrencyRepository;
import com.bcp.reactive.servicedto.response.CurrencyResponse;

import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	CurrencyRepository currencyRepository;

	@Override
	public Single<List<CurrencyResponse>> getAll() {
		return findAllInRepository().map(this::toResponseList);
	}

	private Single<List<Currency>> findAllInRepository() {
		return Single.create(singleSubscriber -> {
			List<Currency> books = currencyRepository.findAll();
			singleSubscriber.onSuccess(books);
		});
	}

	private List<CurrencyResponse> toResponseList(List<Currency> bookList) {
		return bookList.stream().map(this::toResponse).collect(Collectors.toList());
	}

	private CurrencyResponse toResponse(Currency book) {
		CurrencyResponse response = new CurrencyResponse();
		BeanUtils.copyProperties(book, response);
		return response;
	}

	@Override
	public String getCurrency(Long id) {
		if (null != id) {
			Optional<Currency> currency = currencyRepository.findById(id);
			if (currency.isPresent()) {
				return currency.get().getCurrency();
			}
		}
		return null;
	}

}
