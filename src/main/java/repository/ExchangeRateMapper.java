package repository;


import model.Currency;
import model.ExchangeRate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRateMapper implements RowMapper<ExchangeRate> {
    @Override
    public ExchangeRate mapRow(ResultSet rs, int rowNum) throws SQLException {

        Currency baseCurrency = new Currency(
                rs.getLong("baseId"),
                rs.getString("baseCode"),
                rs.getString("baseFullname"),
                rs.getString("baseSign")
        );

        Currency targetCurrency = new Currency(
                rs.getLong("targetId"),
                rs.getString("targetCode"),
                rs.getString("targetFullname"),
                rs.getString("targetSign")
        );

        ExchangeRate exchangeRate = new ExchangeRate();

        exchangeRate.setId(rs.getLong("id"));
        exchangeRate.setBaseCurrency(baseCurrency);
        exchangeRate.setTargetCurrency(targetCurrency);
        exchangeRate.setRate(rs.getDouble("rate"));

        return exchangeRate;
    }
}
