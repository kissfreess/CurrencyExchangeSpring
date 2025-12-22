package queries;

public class ExchangeRateQueries {

    public static final String FIND_ALL_SQL = """
        SELECT exchangerates.id, 
               base.id as baseId, base.code as baseCode, base.fullname as baseFullname, base.sign as baseSign,
                target.id as targetId, target.code as targetCode, target.fullname as targetFullname, target.sign as targetSign,
                exchangerates.rate
                FROM exchangerates
                JOIN currencies as base ON exchangerates.basecurrencyid = base.id
                JOIN currencies as target ON exchangerates.targetcurrencyid = target.id
                """;

    public static final String FIND_BY_CODE_SQL = FIND_ALL_SQL + "WHERE base.code = ? AND target.code = ?";

    public static final String SAVE_SQL = "INSERT INTO exchangerates(basecurrencyid, targetcurrencyid, rate) VALUES (?, ?, ?)";

    public static final String UPDATE_SQL = "UPDATE exchangerates SET basecurrencyid = ?, targetcurrencyid = ?, rate = ? WHERE  id = ?";

    public static final String DELETE_SQL = "DELETE FROM exchangerates WHERE id = ?";



}
