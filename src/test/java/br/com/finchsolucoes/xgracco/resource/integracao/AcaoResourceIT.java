package br.com.finchsolucoes.xgracco.resource.integracao;

import br.com.finchsolucoes.xgracco.configuracao.FunctionalBaseTest;
import br.com.finchsolucoes.xgracco.configuracao.PathConfig;
import br.com.finchsolucoes.xgracco.core.constants.ValidationConstants;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocale;
import br.com.finchsolucoes.xgracco.domain.dto.entities.AcaoDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.repository.AcaoRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = PathConfig.CLEAN, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AcaoResourceIT extends FunctionalBaseTest {

    //TODO - ACERTAR ESTA CLASSE APÓS CONFIGURAR O SPRING SECURITY

    @Autowired
    private AcaoRepository acaoRepository;

    @Autowired
    private MessageLocale messageLocale;

    /*
    @Autowired
    private DatabaseCleaner databaseCleaner;
    */

    private static final String ENDPOINT = "/api/acoes";
    private static List<Acao> ACOES;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        //databaseCleaner.clearTables();
        this.ACOES = this.popularBaseDados();
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    public void createNewAcaoValidate() throws Exception {
        String descricao = "Executando teste de integração";
        AcaoDTO acaoDTO = AcaoDTO.builder().descricao(descricao).build();
        String requestJson = objectMapper.writeValueAsString(acaoDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(descricao)));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    public void createNewAcaoWithDescricaoDuplicated() throws Exception {
        AcaoDTO acaoDTO = AcaoDTO.builder().descricao(this.ACOES.get(0).getDescricao()).build();
        String requestJson = objectMapper.writeValueAsString(acaoDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(messageLocale.validationMessageSource(ValidationConstants.ENTITY_IDENTIFICATION_ALREADY_EXIST))));
    }

    @Test
    //@WithMockUser
    public void createNewAcaoWithoutDescricao() throws Exception {
        AcaoDTO acaoDTO = AcaoDTO.builder().descricao(null).build();
        String requestJson = objectMapper.writeValueAsString(acaoDTO);
        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(messageLocale.beanMessageMessageSource("entity.description.required"))));
    }

    @Test
    //@WithMockUser
    public void createNewAcaoWithDescricaoMoreThanLimitCharacteres() throws Exception {
        String descricao = "Ultrapassou o limite de caractes6789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        AcaoDTO acaoDTO = AcaoDTO.builder().descricao(descricao).build();
        String requestJson = objectMapper.writeValueAsString(acaoDTO);
        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(
                        messageLocale.beanMessageMessageSource("entity.description.max.lenght").replace("{max}", "100"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void updateAcaoValidate() throws Exception {
        String descricao = "Alterando a descricao";
        String path = ENDPOINT + "/" + this.ACOES.get(0).getId().toString();
        AcaoDTO acaoDTO = AcaoDTO.builder().descricao(descricao).build();
        String requestJson = objectMapper.writeValueAsString(acaoDTO);
        AcaoDTO acaoResponsetDTO = AcaoDTO.builder()
                .id(this.ACOES.get(0).getId())
                .descricao(descricao)
                .praticas(new ArrayList<>())
                .instancias(new ArrayList<>())
                .build();
        String responseJson = objectMapper.writeValueAsString(acaoResponsetDTO);
        mvc.perform(put(path)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(responseJson)));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void updateAcaoWithDescricaoDuplicated() throws Exception {
        String path = ENDPOINT + "/" + this.ACOES.get(0).getId().toString();
        AcaoDTO acaoDTO = AcaoDTO.builder().descricao(this.ACOES.get(1).getDescricao()).build();
        String requestJson = objectMapper.writeValueAsString(acaoDTO);
        mvc.perform(put(path)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(messageLocale.validationMessageSource(ValidationConstants.ENTITY_IDENTIFICATION_ALREADY_EXIST))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void updateAcaoNotFound() throws Exception {
        Long id = Long.MAX_VALUE;
        String path = ENDPOINT + "/" + id.toString();
        String descricao = "Alterando a descricao";
        AcaoDTO acaoDTO = AcaoDTO.builder().descricao(descricao).build();
        String requestJson = objectMapper.writeValueAsString(acaoDTO);
        mvc.perform(put(path)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(
                        messageLocale.validationMessageSource(ValidationConstants.REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Acao"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void findAcaoByIdFound() throws Exception {
        String path = ENDPOINT + "/" + this.ACOES.get(0).getId().toString();
        AcaoDTO acaoResponseDTO = AcaoDTO.builder()
                .id(this.ACOES.get(0).getId())
                .descricao(this.ACOES.get(0).getDescricao())
                .praticas(new ArrayList<>())
                .instancias(new ArrayList<>())
                .build();
        String responseJson = objectMapper.writeValueAsString(acaoResponseDTO);
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(responseJson)));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void findByIdNotFound() throws Exception {
        Long id = Long.MAX_VALUE;
        String path = ENDPOINT + "/" + id.toString();
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(
                        messageLocale.validationMessageSource(ValidationConstants.REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Acao"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void deleteAcaoValidate() throws Exception {
        String path = ENDPOINT + "/" + this.ACOES.get(0).getId().toString();

        mvc.perform(delete(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"mensagem\":\"Registro excluído do sistema.\"")));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void deleteAcaoNotFound() throws Exception {
        Long id = Long.MAX_VALUE;
        String path = ENDPOINT + "/" + id.toString();

        mvc.perform(delete(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(
                        messageLocale.validationMessageSource(ValidationConstants.REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Acao"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"GROUP_CREATE"})
    public void searcheAllAcoes() throws Exception {
        String path = ENDPOINT + "?page=1&sortBy=descricao&sortDirection=ASC";
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"size\":10,\"totalElements\":14,\"totalPages\":2,\"number\":1")));

    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"GROUP_CREATE"})
    public void searcheAcaoByDescricao() throws Exception {
        String path = ENDPOINT + "?page=1&sortBy=descricao&sortDirection=ASC&descricao=" + this.ACOES.get(1).getDescricao();
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(this.ACOES.get(1).getDescricao())))
                .andExpect(content().string(containsString("size\":1,\"totalElements\":1,\"totalPages\":1,\"number\":1")));

    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"GROUP_CREATE"})
    public void searcheAcaoReturnEmpty() throws Exception {
        String path = ENDPOINT + "?page=1&sortBy=descricao&sortDirection=ASC&descricao=Acao não existente";
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("size\":0,\"totalElements\":0,\"totalPages\":0,\"number\":1")));
    }



    /**
     * Inicio dos métodos privado auxiliares para os testes
     * @return
     */
    private List<Acao> popularBaseDados(){
        List<Acao> acaos = new ArrayList<>();
        acaos.add(this.setNewAcao("Abrir processo"));
        acaos.add(this.setNewAcao("Fecha processo"));
        acaos.add(this.setNewAcao("Acao um"));
        acaos.add(this.setNewAcao("Acao dois"));
        acaos.add(this.setNewAcao("Acao tres"));
        acaos.add(this.setNewAcao("Acao quatro"));
        acaos.add(this.setNewAcao("Acao cinco"));
        acaos.add(this.setNewAcao("Acao seis"));
        acaos.add(this.setNewAcao("Acao sete"));
        acaos.add(this.setNewAcao("Acao oito"));
        acaos.add(this.setNewAcao("Acao nove"));
        acaos.add(this.setNewAcao("Acao dez"));
        acaos.add(this.setNewAcao("Acao onze"));
        acaos.add(this.setNewAcao("Acao doze"));
        return acaos;
    }

    private Acao setNewAcao(String descricao){
        return this.acaoRepository.save(Acao.builder().descricao(descricao).build());
    }
    /**
     * Fim
     */


}