package repository;

import lombok.RequiredArgsConstructor;
import model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import queries.CurrencyQueries;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CurrencyRepository {

    private final JdbcTemplate jdbcTemplate;


    public List<Currency> findAll(){
        return jdbcTemplate.query(CurrencyQueries.FIND_ALL_SQL, new BeanPropertyRowMapper<>(Currency.class));
    }

    public Currency findById(Long id){
        return jdbcTemplate.queryForObject(CurrencyQueries.FIND_BY_ID_SQL, new BeanPropertyRowMapper<>(Currency.class), id);
    }

    public Currency findByCode(String code){
        return jdbcTemplate.queryForObject(CurrencyQueries.FIND_BY_CODE_SQL, new BeanPropertyRowMapper<>(Currency.class), code);
    }

    public Currency save(Currency currency){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CurrencyQueries.SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, currency.getCode());
            ps.setString(2, currency.getFullName());
            ps.setString(3, currency.getSign());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key != null){
            currency.setId(key.longValue());
        }

        return currency;
    }

    public Currency update(Currency currency, Long id){
        jdbcTemplate.update(CurrencyQueries.UPDATE_SQL, currency.getCode(), currency.getFullName(), currency.getSign(), id);

        return currency;
    }

    public boolean delete(Long id){
        return jdbcTemplate.update(CurrencyQueries.DELETE_SQL, id) > 0;
    }

}
