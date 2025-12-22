package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateConversionResponse {

    private String fromCurrency;
    private String toCurrency;
    private Double originalAmount;
    private Double convertedAmount;

}
