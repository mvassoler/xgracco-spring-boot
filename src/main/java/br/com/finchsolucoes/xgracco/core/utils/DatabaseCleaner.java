package br.com.finchsolucoes.xgracco.core.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe implementada com base no curso Especialista Spring Rest adpatada para o projeto e Liquibase
 * O objetivo é limpar o banco de dados de testes, menos as tabelas de log do Liquibase
 * @author Marcos Vassoler - Tarefa T01-1495
 * No momento não é possível utilizar esta classe devido a problemas de desabilitar as foreign key para o banco postgres.
 */
@Component
public class DatabaseCleaner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataSource dataSource;

    private Connection connection;

    public void clearTables() {
        try (Connection connection = dataSource.getConnection()) {
            this.connection = connection;

            checkTestDatabase();
            tryToClearTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.connection = null;
        }
    }

    private void checkTestDatabase() throws SQLException {
        String catalog = connection.getCatalog();

        if (catalog == null || !catalog.endsWith("tst")) {
            throw new RuntimeException(
                    "Cannot clear database tables because '" + catalog + "' is not a test database (suffix 'test' not found).");
        }
    }

    private void tryToClearTables() throws SQLException {
        List<String> tableNames = getTableNames();
        clear(tableNames);
    }

    private List<String> getTableNames() throws SQLException {
        List<String> tableNames = new ArrayList<>();

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getTables(connection.getCatalog(), null, null, new String[] { "TABLE" });

        while (rs.next()) {
            tableNames.add(rs.getString("TABLE_NAME"));
        }

        tableNames.remove("databasechangelog");
        tableNames.remove("databasechangeloglock");

        return tableNames;
    }

    private void clear(List<String> tableNames) throws SQLException {
        Statement statement = buildSqlStatement(tableNames);

        logger.debug("Executing SQL");
        statement.executeBatch();
    }

    private Statement buildSqlStatement(List<String> tableNames) throws SQLException {
        Statement statement = connection.createStatement();

        //statement.addBatch(sql("SET FOREIGN_KEY_CHECKS = 0"));
        //addTruncateSatements(tableNames, statement);
        //statement.addBatch(sql("SET FOREIGN_KEY_CHECKS = 1"));

        statement.addBatch(sql("SET session_replication_role = 'replica' "));
        addTruncateSatements(tableNames, statement);
        statement.addBatch(sql("SET session_replication_role = 'origin' "));

        return statement;
    }

    private void addTruncateSatements(List<String> tableNames, Statement statement) {
        tableNames.forEach(tableName -> {
            try {
                statement.addBatch(sql("TRUNCATE TABLE " + tableName));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String sql(String sql) {
        logger.debug("Adding SQL: {}", sql);
        return sql;
    }

}