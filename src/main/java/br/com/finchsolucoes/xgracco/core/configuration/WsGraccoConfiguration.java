//package br.com.finchsolucoes.xgracco.core.configuration;
//
//import br.com.finchsolucoes.xgracco.infra.ws.boomerang.BoomerangStub;
//import br.com.finchsolucoes.xgracco.infra.ws.dig.DigStub;
//import br.com.finchsolucoes.xgracco.infra.ws.gracco.GraccoStub;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import java.io.Serializable;
//
///**
// * Configurações dos Web Services.
// *
// * @author Renan Gigliotti
// * @since 2.1
// */
//@Configuration
//public class WsGraccoConfiguration implements Serializable {
//
//    @Bean
//    public BoomerangStub boomerangWS(Environment environment) {
//        return new BoomerangStub(environment.getProperty("gracco.url"));
//    }
//
//    @Bean
//    public DigStub digWS(Environment environment) {
//        return new DigStub(environment.getProperty("gracco.url"));
//    }
//
//    @Bean
//    public GraccoStub graccoWS(Environment environment) {
//        return new GraccoStub(environment.getProperty("gracco.url"));
//    }
//}
