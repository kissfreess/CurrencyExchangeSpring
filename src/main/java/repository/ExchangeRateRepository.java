package repository;

import model.Currency;
import model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import queries.ExchangeRateQueries;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ExchangeRateRepository {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public ExchangeRateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ExchangeRate> findAll(){

        return jdbcTemplate.query(ExchangeRateQueries.FIND_ALL_SQL, new ExchangeRateMapper());
    }

    public ExchangeRate findByCode(String baseCode, String targetCode){
        return jdbcTemplate.queryForObject(ExchangeRateQueries.FIND_BY_CODE_SQL, new ExchangeRateMapper(), baseCode, targetCode);
    }

    public ExchangeRate save(ExchangeRate exchangeRate){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(ExchangeRateQueries.SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, exchangeRate.getBaseCurrency().getId());
            ps.setLong(2, exchangeRate.getTargetCurrency().getId());
            ps.setDouble(3, exchangeRate.getRate());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null){
            exchangeRate.setId(key.longValue());
        }

        return exchangeRate;
    }

    public ExchangeRate update(ExchangeRate exchangeRate, Long id){
        jdbcTemplate.update(ExchangeRateQueries.UPDATE_SQL,
                exchangeRate.getBaseCurrency().getId(),
                exchangeRate.getTargetCurrency().getId(),
                exchangeRate.getRate(),
                id);

        exchangeRate.setId(id);

        return exchangeRate;
    }

    public boolean delete(Long id){
        return jdbcTemplate.update(ExchangeRateQueries.DELETE_SQL, id) > 0;
    }
}
