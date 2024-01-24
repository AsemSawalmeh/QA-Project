package com.example.currency_converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyConverterApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(CurrencyConverterApplication.class, args);
	}


}
