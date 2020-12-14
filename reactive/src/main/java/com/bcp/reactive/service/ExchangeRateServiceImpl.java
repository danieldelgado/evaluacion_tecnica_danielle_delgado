package com.bcp.reactive.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bcp.reactive.entity.Currency;
import com.bcp.reactive.entity.ExchangeRate;
import com.bcp.reactive.repository.CurrencyRepository;
import com.bcp.reactive.repository.ExchangeRateRepository;
import com.bcp.reactive.servicedto.request.ExchangeRateAmountRequest;
import com.bcp.reactive.servicedto.request.UpdateExchangeRateRequest;
import com.bcp.reactive.servicedto.response.ExchangeRateResponse;

import io.reactivex.Completable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

	@Autowired
	ExchangeRateRepository exchangeRateRepository;

	@Autowired
	CurrencyRepository currencyRepository;

	@Override
	public Single<List<ExchangeRateResponse>> getAll(int limit, int page) {
		return findAllInRepository(limit, page).map(this::toResponseList);
	}

	private Single<List<ExchangeRate>> findAllInRepository(int limit, int page) {
		return Single.create(singleSubscriber -> {
			List<ExchangeRate> books = exchangeRateRepository.findAll(PageRequest.of(page, limit)).getContent();
			singleSubscriber.onSuccess(books);
		});
	}

	private List<ExchangeRateResponse> toResponseList(List<ExchangeRate> bookList) {
		return bookList.stream().map(this::toResponse).collect(Collectors.toList());
	}

	private ExchangeRateResponse toResponse(ExchangeRate book) {
		ExchangeRateResponse response = new ExchangeRateResponse();
		BeanUtils.copyProperties(book, response);
		return response;
	}

	@Override
	public Completable update(UpdateExchangeRateRequest req) {
		return Completable.create(completableSubscriber -> {
			Optional<ExchangeRate> optionalBook = exchangeRateRepository.findById(req.getId());
			if (!optionalBook.isPresent())
				completableSubscriber.onError(new EntityNotFoundException());
			else {
				ExchangeRate book = optionalBook.get();
				book.setTypeExchange(req.getTypeExchange());
				exchangeRateRepository.save(book);
				completableSubscriber.onComplete();
			}
		});
	}

	@Override
	public Single<ExchangeRateResponse> exchange(ExchangeRateAmountRequest req) {
		log.info("exchange : {} to {} ", req.getOriginCurrencyId(), req.getExchangeCurrencyId());
		return Single.create(singleSubscriber -> {
			ExchangeRate exchangeRate = exchangeRateRepository.findByOriginCurrencyIdAndExchangeCurrencyId(
					req.getOriginCurrencyId(), req.getExchangeCurrencyId());
			if (null == exchangeRate) {
				singleSubscriber.onError(new EntityNotFoundException());
			}
			double result = exchangeRate.getTypeExchange() * req.getAmount();

			ExchangeRateResponse r = toResponse(exchangeRate);
			r.setExchangeAmount(result);
			singleSubscriber.onSuccess(r);
		});
	}

	@Override
	public Single<List<ExchangeRateResponse>> exchangeUpdateList(List<UpdateExchangeRateRequest> reqs) {
		return Single.create(sink -> {
			sink.onSuccess(reqs.stream().map(req -> {
				log.info("update : {} to {} ", req.getOrigin(), req.getExchange());
				boolean isNew = false;
				ExchangeRate exchangeRate = new ExchangeRate();
				Long currencyOriginId = null;
				Optional<Currency> currencyOrigin = currencyRepository.findByCurrency(req.getOrigin());
				if (currencyOrigin.isPresent()) {
					currencyOriginId = currencyOrigin.get().getId();
				}
				Long currencyExchangeId = null;
				Optional<Currency> currencyExchange = currencyRepository.findByCurrency(req.getExchange());
				if (currencyExchange.isPresent()) {
					currencyExchangeId = currencyExchange.get().getId();
				}

				if (null == currencyOriginId) {
					isNew = true;
					Currency currency = new Currency();
					currency.setCurrency(req.getOrigin());
					currency = currencyRepository.save(currency);
					currencyOriginId = currency.getId();
				}

				if (null == currencyExchangeId) {
					isNew = true;
					Currency currency = new Currency();
					currency.setCurrency(req.getExchange());
					currency = currencyRepository.save(currency);
					currencyExchangeId = currency.getId();
				}

				if (isNew) {
					exchangeRate.setOriginCurrencyId(currencyOriginId);
					exchangeRate.setExchangeCurrencyId(currencyExchangeId);
					exchangeRate.setTypeExchange(req.getTypeExchange());
					req.setIsNew(true);
					log.info("new : {} to {} ", req.getOrigin(), req.getExchange());
				} else {
					log.info("update : {} to {} ", currencyOriginId, currencyExchangeId);
					exchangeRate = exchangeRateRepository.findByOriginCurrencyIdAndExchangeCurrencyId(currencyOriginId,
							currencyExchangeId);
					if (null == exchangeRate) {
						exchangeRate = new ExchangeRate();
						exchangeRate.setOriginCurrencyId(currencyOriginId);
						exchangeRate.setExchangeCurrencyId(currencyExchangeId);
						exchangeRate.setTypeExchange(req.getTypeExchange());
						req.setIsNew(true);
						log.info("new : {} to {} ", req.getOrigin(), req.getExchange());
					} else {
						exchangeRate.setTypeExchange(req.getTypeExchange());
						req.setIsNew(false);
						log.info("update : {} to {} ", req.getOrigin(), req.getExchange());
					}
				}
				req.setExchangeCurrencyId(currencyExchangeId);
				req.setOriginCurrencyId(currencyOriginId);
				exchangeRateRepository.save(exchangeRate);
				return req;
			}).collect(Collectors.toList()));
		}).map(obj -> {
			return ((List<UpdateExchangeRateRequest>) obj).stream().map(s -> {
				ExchangeRateResponse x = new ExchangeRateResponse();
				x.setIsNew(s.getIsNew());
				x.setOriginCurrencyId(s.getOriginCurrencyId());
				x.setExchangeCurrencyId(s.getExchangeCurrencyId());
				x.setTypeExchange(s.getTypeExchange());
				return x;
			}).collect(Collectors.toList());
		});
	}

}
