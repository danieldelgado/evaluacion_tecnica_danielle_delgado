package com.bcp.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bcp.reactive.entity.Currency;
import com.bcp.reactive.entity.ExchangeRate;
import com.bcp.reactive.repository.CurrencyRepository;
import com.bcp.reactive.repository.ExchangeRateRepository;
import com.bcp.reactive.service.UserService;

@SpringBootApplication
public class ReactiveApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveApplication.class, args);
	}

	private PasswordEncoder passwordEncoder;
	private UserService userService;

	@Autowired
	ExchangeRateRepository exchangeRateRepository;

	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	public ReactiveApplication(PasswordEncoder passwordEncoder, UserService userService) {
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}

	@Override
	public void run(String... strings) {
		System.out.println("passwordEncoder");
		System.out.println(passwordEncoder.encode("project_pass"));
		userService.createUser("usuario01@bcptest.com", "pass1234");

		Currency currency = new Currency();
		//currency.setId("1");
		currency.setCurrency("PEN");
		currency = currencyRepository.save(currency);

		Currency currency2 = new Currency();
		//currency2.setId("2");
		currency2.setCurrency("USD");
		currency2 = currencyRepository.save(currency2);

		Currency currency3 = new Currency();
		//currency3.setId("3");
		currency3.setCurrency("EUR");
		currency3 = currencyRepository.save(currency3);

		ExchangeRate op1 = new ExchangeRate();
		//op1.setId("1");
		op1.setOriginCurrencyId(currency.getId());
		op1.setExchangeCurrencyId(currency2.getId());
		op1.setTypeExchange(3.2);
		exchangeRateRepository.save(op1);
		ExchangeRate op2 = new ExchangeRate();
		//op2.setId("2");
		op2.setOriginCurrencyId(currency2.getId());
		op2.setExchangeCurrencyId(currency.getId());
		op2.setTypeExchange(3.1);
		exchangeRateRepository.save(op2);
		ExchangeRate op3 = new ExchangeRate();
		//op3.setId("3");
		op3.setOriginCurrencyId(currency.getId());
		op3.setExchangeCurrencyId(currency3.getId());
		op3.setTypeExchange(4.2);
		exchangeRateRepository.save(op3);
		ExchangeRate op4 = new ExchangeRate();
		//op4.setId("4");
		op4.setOriginCurrencyId(currency3.getId());
		op4.setExchangeCurrencyId(currency.getId());
		op4.setTypeExchange(4.1);
		exchangeRateRepository.save(op4);

	}
}
