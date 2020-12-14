package com.bcp.reactive.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcp.reactive.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

	Optional<Currency> findByCurrency(String currency);


}
