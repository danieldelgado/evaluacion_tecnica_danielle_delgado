package com.bcp.reactive.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcp.reactive.entity.ExchangeRate;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

	ExchangeRate findByOriginCurrencyIdAndExchangeCurrencyId(Long originCurrencyId, Long exchangeCurrencyId);

}
