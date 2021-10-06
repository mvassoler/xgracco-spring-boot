package br.com.finchsolucoes.xgracco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.com.finchsolucoes"})
public class XgraccoApplication {

	public static void main(String[] args) {
		SpringApplication.run(XgraccoApplication.class, args);
	}

}
