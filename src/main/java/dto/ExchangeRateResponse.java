package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateResponse {

    private Long id;
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private double rate;
}
