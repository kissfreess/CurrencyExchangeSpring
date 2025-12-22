package mapper;

import dto.ExchangeRateRequest;
import dto.ExchangeRateResponse;
import lombok.experimental.UtilityClass;
import model.Currency;
import model.ExchangeRate;

import java.util.Optional;

@UtilityClass
public class ExchangeRateDtoMapper {

    public static ExchangeRateResponse convertEntityToResponse(ExchangeRate exchangeRate){
        return Optional.ofNullable(exchangeRate).map(ExchangeRateDtoMapper::buildResponse).orElse(null);
    }

    public static ExchangeRate convertRequestToEntity(ExchangeRateRequest request) {
        return Optional.ofNullable(request).map(ExchangeRateDtoMapper::buildEntity).orElse(null);
    }

    private static ExchangeRateResponse buildResponse(ExchangeRate exchangeRate){
        return new ExchangeRateResponse(
                exchangeRate.getId(),
                exchangeRate.getBaseCurrency().getCode(),
                exchangeRate.getTargetCurrency().getCode(),
                exchangeRate.getRate()
        );
    }

    private static ExchangeRate buildEntity(ExchangeRateRequest request){
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setRate(request.getRate());

        return exchangeRate;
    }
}
