//package br.com.finchsolucoes.xgracco.core.configuration;
//
//import br.com.finchsolucoes.xgracco.infra.security.EncryptionUtil;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.beans.PropertyVetoException;
//import java.io.Serializable;
//
///**
// * Configurações do DataSource.
// *
// * @author Rodolpho Couto
// * @since 2.1
// */
//@Configuration
//@EnableTransactionManagement
//public class DataSourceConfiguration implements Serializable {
//
//    private Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);
//
//    @Autowired
//    private Environment environment;
//
//    /**
//     * Configura o DataSource da aplicação.
//     *
//     * @return
//     * @throws PropertyVetoException
//     */
//    @Bean(destroyMethod = "close")
//    public DataSource dataSource() throws PropertyVetoException {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(environment.getRequiredProperty("jdbc.url"));
//        config.setDriverClassName(environment.getRequiredProperty("jdbc.driverClass"));
//
//        if(Boolean.valueOf(environment.getRequiredProperty("password.encrypt"))){
//            EncryptionUtil.setKey();
//            config.setUsername(EncryptionUtil.decrypt(environment.getRequiredProperty("jdbc.username").trim()));
//            config.setPassword(EncryptionUtil.decrypt(environment.getRequiredProperty("jdbc.password").trim()));
//        }else{
//            config.setUsername(environment.getRequiredProperty("jdbc.username"));
//            config.setPassword(environment.getRequiredProperty("jdbc.password"));
//        }
//
//        if (environment.containsProperty("hikari.poolName")) {
//            String poolName = environment.getRequiredProperty("hikari.poolName");
//            if (poolName != null && !"".equals(poolName)) {
//                config.setPoolName(poolName);
//            }
//        }
//        if (environment.containsProperty("hikari.maximumPoolSize")) {
//            String maximumPoolSize = environment.getRequiredProperty("hikari.maximumPoolSize");
//            if (maximumPoolSize != null && !"".equals(maximumPoolSize)) {
//                try {
//                    config.setMaximumPoolSize(Integer.valueOf(maximumPoolSize));
//                } catch (Exception e) {
//                    logger.info("Falha ao setar maximumPoolSize do hikari: " + maximumPoolSize);
//                }
//            }
//        }
//        if(environment.containsProperty("hikari.minimumIdle")) {
//            String minimumIdle = environment.getRequiredProperty("hikari.minimumIdle");
//            if (minimumIdle != null && !"".equals(minimumIdle)) {
//                try {
//                    config.setMinimumIdle(Integer.valueOf(minimumIdle));
//                } catch (Exception e) {
//                    logger.info("Falha ao setar minimumIdle do hikari: " + minimumIdle);
//                }
//            }
//        }
//        if(environment.containsProperty("hikari.useServerPrepStmts")) {
//            String useServerPrepStmts = environment.getRequiredProperty("hikari.useServerPrepStmts");
//            if (useServerPrepStmts != null && !"".equals(useServerPrepStmts)) {
//                config.addDataSourceProperty("useServerPrepStmts", useServerPrepStmts);
//            }
//        }
//        if(environment.containsProperty("hikari.transactionIsolation")) {
//            String transactionIsolation = environment.getRequiredProperty("hikari.transactionIsolation");
//            if (transactionIsolation != null && !"".equals(transactionIsolation)) {
//                config.setTransactionIsolation(transactionIsolation);
//            } else {
//                config.setTransactionIsolation("TRANSACTION_READ_UNCOMMITTED");
//            }
//        }else {
//            config.setTransactionIsolation("TRANSACTION_READ_UNCOMMITTED");
//        }
//
//        config.addDataSourceProperty("cachePrepStmts", environment.getRequiredProperty("hikari.cachePrepStmts"));
//        config.addDataSourceProperty("prepStmtCacheSize", environment.getRequiredProperty("hikari.prepStmtCacheSize"));
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", environment.getRequiredProperty("hikari.prepStmtCacheSqlLimit"));
//
//        HikariDataSource ds = new HikariDataSource(config);
//        return ds;
//    }
//}
