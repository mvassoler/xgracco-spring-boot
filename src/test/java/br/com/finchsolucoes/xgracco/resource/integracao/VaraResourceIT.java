package br.com.finchsolucoes.xgracco.resource.integracao;

import br.com.finchsolucoes.xgracco.configuracao.FunctionalBaseTest;
import br.com.finchsolucoes.xgracco.configuracao.PathConfig;
import br.com.finchsolucoes.xgracco.core.constants.ValidationConstants;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocale;
import br.com.finchsolucoes.xgracco.domain.dto.input.VaraDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.entity.Vara;
import br.com.finchsolucoes.xgracco.domain.repository.VaraRepository;
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
public class VaraResourceIT extends FunctionalBaseTest {

    @Autowired
    VaraRepository varaRepository;

    @Autowired
    private MessageLocale messageLocale;


    private static final String ENDPOINT = "/api/varas";
    private static List<Vara> VARAS;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        //databaseCleaner.clearTables();
        this.VARAS = this.popularBaseDados();
    }


    private List<Vara> popularBaseDados(){
        List<Vara> varas = new ArrayList<>();
        varas.add(this.setNewVara("Abrir vara"));
        varas.add(this.setNewVara("Fecha vara"));
        varas.add(this.setNewVara("Vara um"));
        varas.add(this.setNewVara("Vara dois"));
        varas.add(this.setNewVara("Vara tres"));
        varas.add(this.setNewVara("Vara quatro"));
        varas.add(this.setNewVara("Vara cinco"));
        varas.add(this.setNewVara("Vara seis"));
        varas.add(this.setNewVara("Vara sete"));
        varas.add(this.setNewVara("Vara oito"));
        varas.add(this.setNewVara("Vara nove"));
        varas.add(this.setNewVara("Vara dez"));
        varas.add(this.setNewVara("Vara onze"));
        varas.add(this.setNewVara("Vara doze"));
        return varas;
    }

    private Vara setNewVara(String descricao){
        return this.varaRepository.save(Vara.builder().descricao(descricao).build());
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    public void createNewVaraWithDescricaoDuplicated() throws Exception {
        VaraDTO varaDTO = VaraDTO.builder().descricao(this.VARAS.get(0).getDescricao()).build();
        String requestJson = objectMapper.writeValueAsString(varaDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(messageLocale.validationMessageSource(ValidationConstants.ENTITY_IDENTIFICATION_ALREADY_EXIST))));
    }
    @Test
    public void updateVaraValidate() throws Exception {
        String descricao = "Alterando a descricao";
        String path = ENDPOINT + "/" + this.VARAS.get(0).getId().toString();
        VaraDTO varaDTO = VaraDTO.builder().descricao(descricao).build();
        String requestJson = objectMapper.writeValueAsString(varaDTO);
        VaraDTO varaDTOresponse = VaraDTO.builder()
                .Id(this.VARAS.get(0).getId())
                .descricao(descricao)
                .instancias(new ArrayList<>())
                .tiposJustica(new ArrayList<>())
                .build();
        String responseJson = objectMapper.writeValueAsString(varaDTOresponse);
        mvc.perform(put(path)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(responseJson)));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    public void createNewVaraValidate() throws Exception {
        String descricao = "Executando teste de integração";
        VaraDTO varaDTO = VaraDTO.builder().descricao(descricao).build();
        String requestJson = objectMapper.writeValueAsString(varaDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(descricao)));
    }


    @Test
    //@WithMockUser
    public void createNewVaraWithoutDescricao() throws Exception {
        VaraDTO varaDTO = VaraDTO.builder().descricao(null).build();
        String requestJson = objectMapper.writeValueAsString(varaDTO);
        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(messageLocale.beanMessageMessageSource("entity.description.required"))));
    }

    @Test
    //@WithMockUser
    public void createNewVaraWithDescricaoMoreThanLimitCharacteres() throws Exception {
        String descricao = "Ultrapassou o limite de caractessssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
        VaraDTO acaoDTO = VaraDTO.builder().descricao(descricao).build();
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
    public void updateVaraWithDescricaoDuplicated() throws Exception {
        String path = ENDPOINT + "/" + this.VARAS.get(0).getId().toString();
        VaraDTO varaDTO = VaraDTO.builder().descricao(this.VARAS.get(1).getDescricao()).build();
        String requestJson = objectMapper.writeValueAsString(varaDTO);
        mvc.perform(put(path)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(messageLocale.validationMessageSource(ValidationConstants.ENTITY_IDENTIFICATION_ALREADY_EXIST))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void updateVaraNotFound() throws Exception {
        Long id = Long.MAX_VALUE;
        String path = ENDPOINT + "/" + id;
        String descricao = "Alterando a descricao";
        VaraDTO varaDTO = VaraDTO.builder().descricao(descricao).build();
        String requestJson = objectMapper.writeValueAsString(varaDTO);
        mvc.perform(put(path)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(
                        messageLocale.validationMessageSource(ValidationConstants.REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Vara"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void findVaraByIdFound() throws Exception {
        String path = ENDPOINT + "/" + this.VARAS.get(0).getId().toString();
        VaraDTO varaRespondeDTO = VaraDTO.builder()
                .Id(this.VARAS.get(0).getId())
                .descricao(this.VARAS.get(0).getDescricao())
                .tiposJustica(new ArrayList<>())
                .instancias(new ArrayList<>())
                .build();
        String responseJson = objectMapper.writeValueAsString(varaRespondeDTO);
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(responseJson)));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void findByIdNotFound() throws Exception {
        Long id = Long.MAX_VALUE;
        String path = ENDPOINT + "/" + id;
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(
                        messageLocale.validationMessageSource(ValidationConstants.REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Vara"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void deleteVaraValidate() throws Exception {
        String path = ENDPOINT + "/" + this.VARAS.get(0).getId().toString();

        mvc.perform(delete(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"mensagem\":\"Registro excluído do sistema.\"")));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    public void deleteVaraNotFound() throws Exception {
        Long id = Long.MAX_VALUE;
        String path = ENDPOINT + "/" + id;

        mvc.perform(delete(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(
                        messageLocale.validationMessageSource(ValidationConstants.REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Vara"))));
    }
    @Test
    //@WithMockUser (username = "admin", authorities = {"GROUP_CREATE"})
    public void searcheAllVaras() throws Exception {
        String path = ENDPOINT + "?page=1&sortBy=descricao&sortDirection=ASC";
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"size\":10,\"totalElements\":14,\"totalPages\":2,\"number\":1")));

    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"GROUP_CREATE"})
    public void searcheVaraByDescricao() throws Exception {
        String path = ENDPOINT + "?page=1&sortBy=descricao&sortDirection=ASC&descricao=" + this.VARAS.get(1).getDescricao();
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(this.VARAS.get(1).getDescricao())))
                .andExpect(content().string(containsString("size\":1,\"totalElements\":1,\"totalPages\":1,\"number\":1")));

    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"GROUP_CREATE"})
    public void searcheVaraReturnEmpty() throws Exception {
        String path = ENDPOINT + "?page=1&sortBy=descricao&sortDirection=ASC&descricao=Vara não existente";
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("size\":0,\"totalElements\":0,\"totalPages\":0,\"number\":1")));
    }


}
