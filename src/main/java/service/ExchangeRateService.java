package service;

import dto.ExchangeRateResponse;
import mapper.ExchangeRateDtoMapper;
import model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ExchangeRateRepository;

import java.util.List;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public List<ExchangeRateResponse> findAll(){
        return exchangeRateRepository.findAll().stream().map(ExchangeRateDtoMapper::convertEntityToResponse).toList();
    }

    public ExchangeRateResponse findByCode(String baseCurrencyCode, String targetCurrencyCode){
        ExchangeRate exchangeRate = exchangeRateRepository.findByCode(baseCurrencyCode, targetCurrencyCode);
        return ExchangeRateDtoMapper.convertEntityToResponse(exchangeRate);
    }

    public Double calculateExchange(String fromCurrencyCode, String toCurrencyCode, Double amount){
        ExchangeRate exchangeRate = exchangeRateRepository.findByCode(fromCurrencyCode, toCurrencyCode);
        return amount * exchangeRate.getRate();
    }
}
