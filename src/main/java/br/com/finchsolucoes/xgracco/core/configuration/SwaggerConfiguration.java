//package br.com.finchsolucoes.xgracco.core.configuration;
//
//import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.builders.RequestParameterBuilder;
//import springfox.documentation.service.RequestParameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
//public class SwaggerConfiguration {
//
//    @Bean
//    public Docket forumApi() {
//        RequestParameterBuilder tokenBuilder = new RequestParameterBuilder();
//        List<RequestParameter> parameterList = new ArrayList<>();
//
//        tokenBuilder.name("Authorization")
//                .description("Header para token JWT")
//                .name("Authorization")
//                .required(false).build();
//        parameterList.add(tokenBuilder.build());
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("br.com.finchsolucoes.xgracco"))
//                .paths(PathSelectors.ant("/**"))
//                .build()
//                .ignoredParameterTypes(Usuario.class)
//                .globalRequestParameters(parameterList);
//    }
//}
