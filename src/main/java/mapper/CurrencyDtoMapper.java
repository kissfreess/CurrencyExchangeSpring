package mapper;

import dto.CurrencyRequest;
import dto.CurrencyResponse;
import lombok.experimental.UtilityClass;
import model.Currency;

import java.util.Optional;

@UtilityClass
public class CurrencyDtoMapper {

    public static CurrencyResponse convertEntityToResponse(Currency currency) {
        return Optional.ofNullable(currency).map(CurrencyDtoMapper::buildResponse).orElse(null);
    }

    public static Currency convertRequestToEntity(CurrencyRequest request) {
        return Optional.ofNullable(request).map(CurrencyDtoMapper::buildEntity).orElse(null);
    }

    private static CurrencyResponse buildResponse(Currency currency){
        return new CurrencyResponse(
                currency.getId(),
                currency.getCode(),
                currency.getFullName(),
                currency.getSign()
        );
    }

    private static Currency buildEntity(CurrencyRequest request){
        return new Currency(
                request.getCode(),
                request.getFullName(),
                request.getSign()
        );
    }
}
