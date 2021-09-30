package br.com.finchsolucoes.xgracco.core.configuration;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzConfiguration implements Serializable {
    private static final Map<String, String> driverClasses = new HashMap<>();

    static {
        driverClasses.put("com.microsoft.sqlserver.jdbc.SQLServerDriver", "org.quartz.impl.jdbcjobstore.MSSQLDelegate");
        driverClasses.put("oracle.jdbc.driver.OracleDriver", "org.quartz.impl.jdbcjobstore.oracle.OracleDelegate");
        driverClasses.put("org.postgresql.Driver", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
        driverClasses.put("com.mysql.jdbc.Driver", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(Environment environment, DataSource dataSource, ApplicationContext applicationContext) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        schedulerFactoryBean.setSchedulerName("XGraccoScheduler");
        schedulerFactoryBean.setAutoStartup(Boolean.TRUE);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(Boolean.TRUE);
        schedulerFactoryBean.setOverwriteExistingJobs(Boolean.TRUE);
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setExposeSchedulerInRepository(Boolean.TRUE);
        schedulerFactoryBean.setApplicationContext(applicationContext);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");

        Properties properties = new Properties();
        properties.setProperty("org.quartz.scheduler.instanceName", "XGraccoScheduler");
        properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
        properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        properties.setProperty("org.quartz.jobStore.isClustered", "true");
        properties.setProperty("org.quartz.jobStore.useProperties", "false");
        properties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
        properties.setProperty("org.quartz.jobStore.driverDelegateClass", driverClasses.get(environment.getRequiredProperty("spring.datasource.driver-class-name")));
        schedulerFactoryBean.setQuartzProperties(properties);

        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }
}
