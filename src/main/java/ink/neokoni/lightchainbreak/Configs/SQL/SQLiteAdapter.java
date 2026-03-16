package ink.neokoni.lightchainbreak.Configs.SQL;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ink.neokoni.lightchainbreak.Utils.FileUtils;

public class SQLiteAdapter extends SQLAdapter{
    public SQLiteAdapter() {
        initSql();
        initTable();
    }
    @Override
    public HikariDataSource initSql() {
        HikariConfig hikariConfig = new HikariConfig();
        StringBuilder baseUrlBuilder = new StringBuilder();
        baseUrlBuilder.append("jdbc:sqlite:")
                .append(FileUtils.getFile("PlayerData.db").getAbsoluteFile());
        String baseUrl = baseUrlBuilder.toString();
        hikariConfig.setJdbcUrl(baseUrl);
        hikariConfig.setDriverClassName(getDriverClass());
        return new HikariDataSource(hikariConfig);
    }

    @Override
    public String getDriverClass() {
        return "org.sqlite.JDBC";
    }
}
