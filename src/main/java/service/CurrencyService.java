package service;

import dto.CurrencyRequest;
import dto.CurrencyResponse;
import exception.CurrencyNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.CurrencyDtoMapper;
import model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CurrencyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    private final CurrencyRepository currencyRepository;


    public List<CurrencyResponse> findAll(){
        logger.info("Get all currencies");
        List<CurrencyResponse> currencies = currencyRepository.findAll().stream().map(CurrencyDtoMapper::convertEntityToResponse).toList();
        logger.debug("Founded {} currencies", currencies.size());

        return currencies;
    }

    public CurrencyResponse findById(Long id){
        logger.info("Get currency by ID", id);
        try {
            CurrencyResponse currencyResponse = CurrencyDtoMapper.convertEntityToResponse(currencyRepository.findById(id));
            logger.debug("Currency found: {}", currencyResponse.getCode());
            return currencyResponse;
        } catch (Exception e) {
            logger.warn("Currency with ID: {} not found", id);
            throw new CurrencyNotFoundException("Currency with id:" + id + " not found");

        }
    }

    public CurrencyResponse findByCode(String code) {
        logger.info("Get currency by Code", code);
        try {
            CurrencyResponse currencyResponse = CurrencyDtoMapper.convertEntityToResponse(currencyRepository.findByCode(code));
            logger.debug("Currency found: {}", currencyResponse.getCode());
            return currencyResponse;
        } catch (Exception e) {
            logger.warn("Currency with Code {} not found", code);
            throw new CurrencyNotFoundException("Currency with code:" + code + " not found");
        }
    }

    public CurrencyResponse save (CurrencyRequest currencyRequest) {
        logger.info("Save a new currency with code:{}", currencyRequest.getCode());
        Currency currency = CurrencyDtoMapper.convertRequestToEntity(currencyRequest);
        Currency savedCurrency = currencyRepository.save(currency);

        logger.info("Currency saved with ID:{}", savedCurrency.getId());
        return  CurrencyDtoMapper.convertEntityToResponse(savedCurrency);
    }

    public CurrencyResponse update(CurrencyRequest currencyRequest, Long id) {
        logger.info("Update currency with ID:{}", id);
        Currency currencyExists = currencyRepository.findById(id);

        currencyExists.setCode(currencyRequest.getCode());
        currencyExists.setFullName(currencyRequest.getFullName());
        currencyExists.setSign(currencyRequest.getSign());

        currencyRepository.update(currencyExists, id);

        return CurrencyDtoMapper.convertEntityToResponse(currencyExists);
    }

    public boolean delete(Long id) {
        logger.info("Delete currency with ID:{}", id);

        return currencyRepository.delete(id);
    }

}
