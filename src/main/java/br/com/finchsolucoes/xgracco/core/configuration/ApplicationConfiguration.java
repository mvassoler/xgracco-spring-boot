package br.com.finchsolucoes.xgracco.core.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackages = "br.com.finchsolucoes.xgracco")
//@PropertySource(value = "file:${jetty.base}/resources/xgracco/xgracco.properties")
@PropertySource(value = "classpath:application.properties")
@Import({
        LiquibaseConfiguration.class, QuartzConfiguration.class, RestTemplateConfiguration.class
})
@EnableTransactionManagement
public class ApplicationConfiguration {
}
