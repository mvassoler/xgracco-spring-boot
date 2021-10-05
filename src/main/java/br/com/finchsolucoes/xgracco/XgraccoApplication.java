package br.com.finchsolucoes.xgracco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication(scanBasePackages = {"br.com.finchsolucoes"})
public class XgraccoApplication {

	public static void main(String[] args) {
		SpringApplication.run(XgraccoApplication.class, args);
	}

}
