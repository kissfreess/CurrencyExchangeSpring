package controller;

import dto.ExchangeRateConversionResponse;
import dto.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.ExchangeRateService;

import java.util.List;

@RestController
@RequestMapping("/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateController.class);
    private final ExchangeRateService exchangeRateService;


    @GetMapping
    public ResponseEntity<List<ExchangeRateResponse>> getAllExchangeRates(){
        logger.info("GET /exchange-rates - get all exchange-rates");
        List<ExchangeRateResponse> exchangeRates = exchangeRateService.findAll();
        logger.debug("Founded {} exchange rates", exchangeRates.size());

        return ResponseEntity.ok(exchangeRates);
    }

    @GetMapping("/pair")
    public ResponseEntity<ExchangeRateResponse> getExchangeRateByCode(@RequestParam String baseCurrency, @RequestParam String targetCurrency){
        logger.info("GET /pair - get exchange rate for a  pair of codes");
        ExchangeRateResponse exchangeRateResponse = exchangeRateService.findByCode(baseCurrency.toUpperCase(), targetCurrency.toUpperCase());
        logger.debug("Founded exchange rate:{}", exchangeRateResponse.getRate());
        return ResponseEntity.ok(exchangeRateResponse);
    }

    @GetMapping("/convert")
    public ResponseEntity<ExchangeRateConversionResponse> convertEntity(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam Double amount
    ) {
        logger.info("GET /convert - get convert entity");
        ExchangeRateConversionResponse exchangeRateResponse = exchangeRateService.calculateExchange(from, to, amount);
        logger.debug("Got convert entity");

        return ResponseEntity.ok(exchangeRateResponse);
    }

}
