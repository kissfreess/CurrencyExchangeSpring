package controller;

import dto.CurrencyRequest;
import dto.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getAllCurrencies(){
        List<CurrencyResponse> currencies = currencyService.findAll();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponse> getCurrencyById(@PathVariable Long id){
        try {
            CurrencyResponse currency = currencyService.findById(id);
            return ResponseEntity.ok(currency);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CurrencyResponse> getCurrencyByCode(@PathVariable String code){
        try {
            CurrencyResponse currency = currencyService.findByCode(code);
            return ResponseEntity.ok(currency);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<CurrencyResponse> createCurrency(@RequestBody CurrencyRequest currencyRequest){
        try {
            CurrencyResponse currency = currencyService.save(currencyRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(currency);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponse> updateCurrency(@PathVariable Long id, @RequestBody CurrencyRequest currencyRequest){
        try {
            CurrencyResponse updateCurrency = currencyService.update(currencyRequest, id);
            return ResponseEntity.ok(updateCurrency);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CurrencyResponse> deleteCurrency(@PathVariable Long id){
        boolean deleted = currencyService.delete(id);

        if (deleted){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
