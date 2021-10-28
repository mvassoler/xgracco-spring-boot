package br.com.finchsolucoes.xgracco.core.configuration;

import br.com.finchsolucoes.xgracco.domain.dto.ErrorDetailsDTO;
import br.com.finchsolucoes.xgracco.resource.openapi.LinksModelOpenApi;
import br.com.finchsolucoes.xgracco.resource.openapi.PageableModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfiguration implements WebMvcConfigurer {

    private static final String ERRO_INTERNO_SERVIDOR = "Erro interno do Servidor";
    private static final String ERRO_NAO_ENCONTRADO = "Recurso não encontrado para um ID informado";
    private static final String ERRO_NAO_ACEITAVEL = "Recurso não possui representação que pode ser aceita pelo consumidor";
    private static final String ERRO_BAD_REQUEST = "Requisição inválida (erro do cliente)";
    private static final String ERRO_TIPO_MIDIA_NAO_SUPORTADO = "Requisição recusada porque o corpo está em um formato não suportado";
    private static final String ERRO_RECURSO_NAO_AUTORIZADO = "Recurso não liberado para acesso";
    private static final String ERRO_SEM_AUTORIZACAO = "Recurso não autorizado";

    @Bean
    public Docket apiDocket(){
        var typeResolver = new TypeResolver();
        return new Docket(DocumentationType.OAS_30)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("br.com.finchsolucoes.xgracco.resource"))
                    .paths(PathSelectors.any())
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(ErrorDetailsDTO.class))
                .directModelSubstitute(PagedModel.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, File.class, InputStream.class)
                .apiInfo(apiInfo())
                .tags(new Tag("Ações", "Recursos do cadastro de Ações"),
                      new Tag("Permissões", "Recursos do cadastro de Permissões"),
                      new Tag("Recursos", "Recursos de entrada da API"),
                      new Tag("Varas", "Recursos do cadastro de Varas"));
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("XGRACCO API")
                .description("API PARA PROCESSOS JURÍDICOS")
                .version("1.0")
                .contact(new Contact("Finch Soluções", "https://finchsolucoes.com.br", "comercial@finchsolucoes.com.br"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("index.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("index.html")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(comoString(HttpStatus.INTERNAL_SERVER_ERROR))
                        .description(ERRO_INTERNO_SERVIDOR)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.NOT_FOUND))
                        .description(ERRO_NAO_ENCONTRADO)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.BAD_REQUEST))
                        .description(ERRO_BAD_REQUEST)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.FORBIDDEN))
                        .description(ERRO_SEM_AUTORIZACAO)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.UNAUTHORIZED))
                        .description(ERRO_RECURSO_NAO_AUTORIZADO)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build()
        );
    }

    private List<Response> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(comoString(HttpStatus.INTERNAL_SERVER_ERROR))
                        .description(ERRO_INTERNO_SERVIDOR)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.NOT_ACCEPTABLE))
                        .description(ERRO_NAO_ACEITAVEL)
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.BAD_REQUEST))
                        .description(ERRO_BAD_REQUEST)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.NOT_FOUND))
                        .description(ERRO_NAO_ENCONTRADO)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.UNSUPPORTED_MEDIA_TYPE))
                        .description(ERRO_TIPO_MIDIA_NAO_SUPORTADO)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.FORBIDDEN))
                        .description(ERRO_SEM_AUTORIZACAO)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.UNAUTHORIZED))
                        .description(ERRO_RECURSO_NAO_AUTORIZADO)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build()
        );
    }

    private List<Response> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(comoString(HttpStatus.INTERNAL_SERVER_ERROR))
                        .description(ERRO_INTERNO_SERVIDOR)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.NOT_FOUND))
                        .description(ERRO_NAO_ENCONTRADO)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.UNAUTHORIZED))
                        .description(ERRO_RECURSO_NAO_AUTORIZADO)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.FORBIDDEN))
                        .description(ERRO_SEM_AUTORIZACAO)
                        .representation( MediaType.APPLICATION_JSON )
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(HttpStatus.NOT_ACCEPTABLE))
                        .description(ERRO_NAO_ACEITAVEL)
                        .build()
        );
    }

    private Consumer<RepresentationBuilder> problemBuilder() {
        return r -> r.model(m -> m.name("Problema")
                .referenceModel(
                        ref -> ref.key(
                                k -> k.qualifiedModelName(
                                        q -> q.name("Problema")
                                                .namespace("br.com.finchsolucoes.xgracco.domain.dto")
                                ))));
    }

    private String comoString(HttpStatus httpStatus) {
        return String.valueOf(httpStatus.value());
    }
}
