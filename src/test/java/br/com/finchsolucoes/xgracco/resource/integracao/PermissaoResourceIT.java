package br.com.finchsolucoes.xgracco.resource.integracao;

import br.com.finchsolucoes.xgracco.configuracao.FunctionalBaseTest;
import br.com.finchsolucoes.xgracco.configuracao.PathConfig;
import br.com.finchsolucoes.xgracco.core.constants.ValidationConstants;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocale;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Observação: os dados das permissão foram inseridos pelo Liquibase no momento da criação da base de teste
 * Não inserir no script data.sql a limpeza desta tabela, pois este procedimento quebrará estes testes
 */
@Sql(value = PathConfig.CLEAN, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PermissaoResourceIT extends FunctionalBaseTest {

    //TODO - ACERTAR ESTA CLASSE APÓS CONFIGURAR O SPRING SECURITY

    @Autowired
    private MessageLocale messageLocale;

    private static final String ENDPOINT = "/api/permissoes";

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        //databaseCleaner.clearTables();
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    public void findAllValidated() throws Exception {
        mvc.perform(get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("gestao-processos")))
                .andExpect(content().string(containsString("gestao-contratos")))
                .andExpect(content().string(containsString("monitoramento")));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    public void findByCodeValidated() throws Exception {
        mvc.perform(get(ENDPOINT + "/monitoramento")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("monitoramento")));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    public void findByCodeNotValidated() throws Exception {
        mvc.perform(get(ENDPOINT + "/teste")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(
                        this.messageLocale.validationMessageSource(ValidationConstants.REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Permissao"))));
    }
}
