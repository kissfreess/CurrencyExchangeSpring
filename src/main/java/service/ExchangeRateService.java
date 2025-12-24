package service;

import dto.ExchangeRateConversionResponse;
import dto.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import mapper.ExchangeRateDtoMapper;
import model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ExchangeRateRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;


    public List<ExchangeRateResponse> findAll(){
        return exchangeRateRepository.findAll().stream().map(ExchangeRateDtoMapper::convertEntityToResponse).toList();
    }

    public ExchangeRateResponse findByCode(String baseCurrencyCode, String targetCurrencyCode){

        validateCurrencyCode(baseCurrencyCode);
        validateCurrencyCode(targetCurrencyCode);

        ExchangeRate exchangeRate = exchangeRateRepository.findByCode(baseCurrencyCode, targetCurrencyCode);

        return ExchangeRateDtoMapper.convertEntityToResponse(exchangeRate);
    }

    public ExchangeRateConversionResponse calculateExchange(String fromCurrencyCode, String toCurrencyCode, Double amount){

        validateCurrencyCode(fromCurrencyCode);
        validateCurrencyCode(toCurrencyCode);

        if (amount <= 0) {
            throw  new IllegalArgumentException("The amount must by a positive number");
        }

        ExchangeRate exchangeRate = exchangeRateRepository.findByCode(fromCurrencyCode, toCurrencyCode);
        Double convertedAmount =  amount * exchangeRate.getRate();

        return new ExchangeRateConversionResponse(
                fromCurrencyCode,
                toCurrencyCode,
                amount,
                convertedAmount
        );
    }

    private void validateCurrencyCode(String currencyCode) {
        if (currencyCode == null || currencyCode.length() !=3){
            throw new IllegalArgumentException("The currency code must be 3 characters long");
        }
        if (!currencyCode.matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("The currency code must contain only 3 letters");
        }
    }
}
