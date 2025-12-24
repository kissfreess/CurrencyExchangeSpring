package controller;

import dto.CurrencyRequest;
import dto.CurrencyResponse;
import exception.CurrencyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private final CurrencyService currencyService;


    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getAllCurrencies(){
        logger.info("GET /currencies - get all currencies");

        try {
            List<CurrencyResponse> currencies = currencyService.findAll();
            logger.debug("Founded {} currencies", currencies.size());
            return ResponseEntity.ok(currencies);
        } catch (Exception e) {
            logger.error("Error getting list of currencies");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponse> getCurrencyById(@PathVariable Long id){
        logger.info("GET /curriencies/{} - get currency by ID", id);
        try {
            CurrencyResponse currency = currencyService.findById(id);
            logger.debug("Currency found: {}", currency.getCode());
            return ResponseEntity.ok(currency);
        } catch (Exception e) {
            logger.warn("Currency with ID: {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CurrencyResponse> getCurrencyByCode(@PathVariable String code){
        logger.info("GET /currencies/{} - get currency by Code", code);
        try {
            CurrencyResponse currency = currencyService.findByCode(code);
            logger.debug("Currency found: {}", currency.getCode());
            return ResponseEntity.ok(currency);
        } catch (Exception e) {
            logger.warn("Currency with Code {} not found", code);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<CurrencyResponse> createCurrency(@RequestBody CurrencyRequest currencyRequest){
        logger.info("Create new currency:{}", currencyRequest);
        try {
            CurrencyResponse currency = currencyService.save(currencyRequest);
            logger.debug("Currency created:{}", currency);
            return ResponseEntity.status(HttpStatus.CREATED).body(currency);
        } catch (Exception e) {
            logger.error("Currency {} didn't create", currencyRequest);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponse> updateCurrency(@PathVariable Long id, @RequestBody CurrencyRequest currencyRequest){
        logger.info("Update currency with ID:{}", id);
        try {
            CurrencyResponse updateCurrency = currencyService.update(currencyRequest, id);
            logger.debug("Currency updated: {}", updateCurrency);
            return ResponseEntity.ok(updateCurrency);
        } catch (Exception e) {
            logger.error("Currency {} didn't update", currencyRequest);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CurrencyResponse> deleteCurrency(@PathVariable Long id){
        logger.info("Delete currency with ID:{}", id);
        boolean deleted = currencyService.delete(id);
        if (deleted){
            logger.debug("Currency with ID {} deleted", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.error("Currency with ID{} didn't delete", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
