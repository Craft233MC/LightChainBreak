package ink.neokoni.lightchainbreak.Configs.SQL;

import lombok.SneakyThrows;

public class MariadbAdapter extends SQLAdapter {
    public MariadbAdapter() {
        initSql();
        initTable();
    }

    @Override
    public String getDriverClass() {
        return "org.mariadb.jdbc.Driver";
    }

    @SneakyThrows
    @Override
    public void initTable() {
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS PlayerData(
                uuid UUID PRIMARY KEY,
                enabled BOOLEAN NOT NULL DEFAULT FALSE,
                displayCount BOOLEAN NOT NULL DEFAULT FALSE,
                sneakToEnable BOOLEAN NOT NULL DEFAULT FALSE,
                itemProtective BOOLEAN NOT NULL DEFAULT FALSE
            )
            """;
        getDataSource().getConnection().prepareStatement(createTableSql).execute();
    }
}
