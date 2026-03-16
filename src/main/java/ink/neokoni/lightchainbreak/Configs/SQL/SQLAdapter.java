package ink.neokoni.lightchainbreak.Configs.SQL;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ink.neokoni.lightchainbreak.Configs.Config;
import ink.neokoni.lightchainbreak.Configs.Datas.PlayerDataInfo;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAdapter {
    private HikariDataSource dataSource;
    public SQLAdapter() {
        dataSource = initSql();
        initTable();
    }

    public HikariDataSource initSql() {
        HikariConfig hikariConfig = new HikariConfig();
        Config.DataStorageInfo databaseInfo = Config.getConfig().getDataStorageInfo();
        StringBuilder baseUrlBuilder = new StringBuilder();
        baseUrlBuilder.append("jdbc:")
            .append(databaseInfo.type().toLowerCase())
            .append("://")
            .append(databaseInfo.host())
            .append(":")
            .append(databaseInfo.port())
            .append("/")
            .append(databaseInfo.datebase());
        if (databaseInfo.args()!=null && !databaseInfo.args().isEmpty()) {
            if (databaseInfo.args().toLowerCase()!="null") {
                baseUrlBuilder.append("?").append(databaseInfo.args());
            }
        }
        String baseUrl = baseUrlBuilder.toString();
        hikariConfig.setJdbcUrl(baseUrl);
        hikariConfig.setDriverClassName(getDriverClass());
        hikariConfig.setUsername(databaseInfo.username());
        hikariConfig.setPassword(databaseInfo.password());
        return new HikariDataSource(hikariConfig);
    }

    public String getDriverClass() {
        return "com.mysql.cj.jdbc.Driver";
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public void initTable() {
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS PlayerData(
                uuid VARCHAR(36) PRIMARY KEY,
                enabled BOOLEAN NOT NULL DEFAULT FALSE,
                displayCount BOOLEAN NOT NULL DEFAULT FALSE,
                sneakToEnable BOOLEAN NOT NULL DEFAULT FALSE,
                itemProtective BOOLEAN NOT NULL DEFAULT FALSE
            )
            """;
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement(createTableSql).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerDataInfo getPlayerData(Player player) {
        String uuid = player.getUniqueId().toString();
        String lookupSql = """
                SELECT * FROM PlayerData WHERE uuid=?
                """;
        try (Connection connection = getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(lookupSql);
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new PlayerDataInfo(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getBoolean("enabled"),
                        resultSet.getBoolean("displayCount"),
                        resultSet.getBoolean("sneakToEnable"),
                        resultSet.getBoolean("itemProtective")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @SneakyThrows
    public void savePlayerData(Player player, PlayerDataInfo playerData) {
        String uuid = player.getUniqueId().toString();
        String lookupSql = """
                INSERT INTO PlayerData (uuid, enabled, displayCount, sneakToEnable, itemProtective) VALUES (?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE enabled=?, displayCount=?, sneakToEnable=?, itemProtective=?;
                """;
        try (Connection connection = getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(lookupSql);
            statement.setString(1, uuid);
            statement.setBoolean(2, playerData.isEnabled());
            statement.setBoolean(3, playerData.isDisplayCount());
            statement.setBoolean(4, playerData.isSneakToEnable());
            statement.setBoolean(5, playerData.isItemProtective());

            statement.setBoolean(6, playerData.isEnabled());
            statement.setBoolean(7, playerData.isDisplayCount());
            statement.setBoolean(8, playerData.isSneakToEnable());
            statement.setBoolean(9, playerData.isItemProtective());
            statement.execute();
        }
    }

    public void close() {
        getDataSource().close();
    }
}