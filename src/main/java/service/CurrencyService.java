package service;

import dto.CurrencyRequest;
import dto.CurrencyResponse;
import mapper.CurrencyDtoMapper;
import model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CurrencyRepository;

import java.util.List;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<CurrencyResponse> findAll(){
       return currencyRepository.findAll().stream().map(CurrencyDtoMapper::convertEntityToResponse).toList();
    }

    public CurrencyResponse findById(Long id){
        return CurrencyDtoMapper.convertEntityToResponse(currencyRepository.findById(id));
    }

    public CurrencyResponse findByCode(String code) {
        return CurrencyDtoMapper.convertEntityToResponse(currencyRepository.findByCode(code));
    }

    public CurrencyResponse save (CurrencyRequest currencyRequest) {

        Currency currency = CurrencyDtoMapper.convertRequestToEntity(currencyRequest);
        Currency savedCurrency = currencyRepository.save(currency);

        return  CurrencyDtoMapper.convertEntityToResponse(savedCurrency);
    }

    public CurrencyResponse update(CurrencyRequest currencyRequest, Long id) {
        Currency currencyExists = currencyRepository.findById(id);

        currencyExists.setCode(currencyRequest.getCode());
        currencyExists.setFullName(currencyRequest.getFullName());
        currencyExists.setSign(currencyRequest.getSign());

        currencyRepository.update(currencyExists, id);

        return CurrencyDtoMapper.convertEntityToResponse(currencyExists);
    }

    public boolean delete(Long id) {

        return currencyRepository.delete(id);
    }

}
