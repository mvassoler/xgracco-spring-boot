package br.com.finchsolucoes.xgracco.core.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.Serializable;

/**
 * Configuração do Thymeleaf e templateEngine
 *
 * @author Paulo Marçon
 * @since 2.1
 */
@Configuration
public class ThymeleafConfiguration implements Serializable {

    private static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

    @Bean
    public TemplateEngine emailTemplateEngine(ApplicationContext applicationContext) {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver(applicationContext));
        return templateEngine;
    }

    private ITemplateResolver htmlTemplateResolver(ApplicationContext applicationContext) {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        return resolver;
    }
}
