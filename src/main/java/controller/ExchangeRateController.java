package controller;

import dto.ExchangeRateConversionResponse;
import dto.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.ExchangeRateService;

import java.util.List;

@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public ResponseEntity<List<ExchangeRateResponse>> getAllExchangeRates(){
        List<ExchangeRateResponse> exchangeRates = exchangeRateService.findAll();
        return ResponseEntity.ok(exchangeRates);
    }

    @GetMapping("/pair")
    public ResponseEntity<ExchangeRateResponse> getExchangeRateByCode(@RequestParam String baseCurrency, @RequestParam String targetCurrency){
        validateCurrencyCode(baseCurrency);
        validateCurrencyCode(targetCurrency);

        ExchangeRateResponse exchangeRateResponse = exchangeRateService.findByCode(baseCurrency.toUpperCase(), targetCurrency.toUpperCase());

        return ResponseEntity.ok(exchangeRateResponse);
    }

    @GetMapping("/convert")
    public ResponseEntity<ExchangeRateConversionResponse> convertEntity(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam Double amount
    ) {
        validateCurrencyCode(from);
        validateCurrencyCode(to);

        if (amount <= 0) {
            throw  new IllegalArgumentException("The amount must by a positive number");
        }

        Double convertedAmount = exchangeRateService.calculateExchange(from, to, amount);

        ExchangeRateConversionResponse response = new ExchangeRateConversionResponse(
                from.toUpperCase(),
                to.toUpperCase(),
                amount,
                convertedAmount
        );

        return ResponseEntity.ok(response);
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
