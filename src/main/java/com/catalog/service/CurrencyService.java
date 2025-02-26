package com.catalog.service;

import com.catalog.model.money.Currency;
import com.catalog.model.money.CurrencyType;
import com.catalog.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency getCurrency(String currencyType){
        return getCurrency(CurrencyType.valueOf(currencyType));
    }

    public Currency getCurrency(CurrencyType currencyType){
        return currencyRepository.findByType(currencyType)
                .orElseThrow(() -> new IllegalArgumentException("Currency not found"));
    }
}
