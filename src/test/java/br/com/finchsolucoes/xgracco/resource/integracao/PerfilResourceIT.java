package br.com.finchsolucoes.xgracco.resource.integracao;

import br.com.finchsolucoes.xgracco.configuracao.FunctionalBaseTest;
import br.com.finchsolucoes.xgracco.configuracao.PathConfig;
import br.com.finchsolucoes.xgracco.core.constants.ValidationConstants;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocale;
import br.com.finchsolucoes.xgracco.domain.dto.input.IdDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.PerfilInDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Perfil;
import br.com.finchsolucoes.xgracco.domain.entity.Permissao;
import br.com.finchsolucoes.xgracco.domain.repository.PerfilRepository;
import br.com.finchsolucoes.xgracco.domain.repository.PermissaoRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static br.com.finchsolucoes.xgracco.core.constants.ValidationConstants.REGISTER_NOT_FOUND_CUSTOM;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = PathConfig.CLEAN, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PerfilResourceIT extends FunctionalBaseTest {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private MessageLocale messageLocale;

    private static final String ENDPOINT = "/api/perfis";
    private static final String TEXT_SIZE = "123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780123456780";
    private static final String CONSTANTE = "{table}";
    private static List<Permissao> PERMISSOES;
    private static Perfil PERFIL;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        //databaseCleaner.clearTables();
        this.PERMISSOES = this.getListPermissoes();
        this.PERFIL = this.setNewPerfil();
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void createPerfilValidate() throws Exception {
        String descricao = "Teste de integração do Perfil";
        String nome = "Escritório";
        List<IdDTO> permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(4).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(5).getId()).build());

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(nome, descricao, permissoes);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(descricao)));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void createPerfilNameDuplicated() throws Exception {
        String descricao = "Teste de integração do Perfil";
        String nome = "Administrador";
        List<IdDTO> permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(nome, descricao, permissoes);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(messageLocale.validationMessageSource(ValidationConstants.ENTITY_NAME_ALREADY_EXIST))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void createPerfilPermissaoNotFound() throws Exception {
        String descricao = "Teste de integração do Perfil";
        String nome = "Teste permissao não valida";
        List<IdDTO> permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());
        permissoes.add(IdDTO.builder().id(1000000l).build());

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(nome, descricao, permissoes);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Permissao"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void createPerfilWithouthDescricao() throws Exception {
       // String descricao = "Teste de integração do Perfil";
        String nome = "Escritório";
        List<IdDTO> permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(4).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(5).getId()).build());

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(nome, null, permissoes);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(this.messageLocale.beanMessageMessageSource("entity.description.required"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void createPerfilWithDescricaoSizeNotValidated() throws Exception {
        String descricao = this.TEXT_SIZE;
        String nome = "Escritório";
        List<IdDTO> permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(4).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(5).getId()).build());

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(nome, descricao, permissoes);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(
                        this.messageLocale.beanMessageMessageSource("entity.description.max.lenght").replace("{max}", "200"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void createPerfilWithouthNome() throws Exception {
        String descricao = "Teste de integração do Perfil";
        //String nome = "Escritório";
        List<IdDTO> permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(4).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(5).getId()).build());

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(null, descricao, permissoes);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(this.messageLocale.beanMessageMessageSource("entity.nome.required"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void createPerfilWithNomeSizeNotValidated() throws Exception {
        String descricao = "Teste de integração do Perfil";
        String nome = this.TEXT_SIZE;
        List<IdDTO> permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(4).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(5).getId()).build());

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(nome, descricao, permissoes);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(
                        this.messageLocale.beanMessageMessageSource("entity.nome.max.lenght").replace("{max}", "100"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void createPerfilWithouthPermissoes() throws Exception {
        String descricao = "Teste de integração do Perfil";
        String nome = "Escritório";

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(null, descricao, null);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(this.messageLocale.beanMessageMessageSource("entity.permissoes.required"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void updatePerfilValidate() throws Exception {
        String path = this.ENDPOINT + "/" + this.PERFIL.getId().toString();
        String descricao = "Alterando a descrição do perfil da administração";
        String nome = "Administração";
        List<IdDTO> permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(5).getId()).build());

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(nome, descricao, permissoes);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(put(path)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(descricao)))
                .andExpect(content().string(containsString(this.PERMISSOES.get(3).getCodigo())))
                .andExpect(content().string(containsString(this.PERMISSOES.get(5).getCodigo())));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void updatePerfilNomeDuplicated() throws Exception {
        String descricao = "Teste de integração do Perfil";
        String nome = "Escritório";
        List<IdDTO> permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(4).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(5).getId()).build());

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(nome, descricao, permissoes);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(post(ENDPOINT)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String path = this.ENDPOINT + "/" + this.PERFIL.getId().toString();
        descricao = "Alterando a descrição do perfil da administração";
        nome = "Escritório";
        permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(5).getId()).build());

        PerfilInDTO perfilInDTOOther = this.setNewPerfilEntradaDTO(nome, descricao, permissoes);
        requestJson = objectMapper.writeValueAsString(perfilInDTOOther);

        mvc.perform(put(path)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(messageLocale.validationMessageSource(ValidationConstants.ENTITY_NAME_ALREADY_EXIST))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void updatePerfilNotFound() throws Exception {
        String path = this.ENDPOINT + "/" + Integer.MAX_VALUE;
        String descricao = "Alterando a descrição do perfil da administração";
        String nome = "Administração";
        List<IdDTO> permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(5).getId()).build());

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(nome, descricao, permissoes);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(put(path)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Perfil"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"}) - Retorna após implementar o spring security
    void updatePerfilPermissaoNotFound() throws Exception {
        String path = this.ENDPOINT + "/" + this.PERFIL.getId().toString();
        String descricao = "Alterando a descrição do perfil da administração";
        String nome = "Administração";
        List<IdDTO> permissoes = new ArrayList<>();
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(3).getId()).build());
        permissoes.add(IdDTO.builder().id(this.PERMISSOES.get(5).getId()).build());
        permissoes.add(IdDTO.builder().id(Long.valueOf(Integer.MAX_VALUE)).build());

        PerfilInDTO perfilInDTO = this.setNewPerfilEntradaDTO(nome, descricao, permissoes);
        String requestJson = objectMapper.writeValueAsString(perfilInDTO);

        mvc.perform(put(path)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Permissao"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    void findPerfilByIdFound() throws Exception {
        String path = ENDPOINT + "/" + this.PERFIL.getId().toString();
        String responseJson = "\"id\":" +this.PERFIL.getId().toString() + ",\"nome\":\"Administrador\",\"descrição\":\"Administrador do Sistema\",\"permissões\":";
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(responseJson)));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    void findByIdNotFound() throws Exception {
        Long id = Long.MAX_VALUE;
        String path = ENDPOINT + "/" + id.toString();
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(
                        messageLocale.validationMessageSource(ValidationConstants.REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Perfil"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    void deletePerfilValidate() throws Exception {
        String path = ENDPOINT + "/" + this.PERFIL.getId().toString();

        mvc.perform(delete(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"mensagem\":\"Registro excluído do sistema.\"")));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"SUPER"})
    void deletePerfilNotFound() throws Exception {
        Long id = Long.MAX_VALUE;
        String path = ENDPOINT + "/" + id.toString();

        mvc.perform(delete(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(
                        messageLocale.validationMessageSource(ValidationConstants.REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Perfil"))));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"GROUP_CREATE"})
    void searcheAllPerfis() throws Exception {
        this.setListPerfis();
        String path = ENDPOINT + "?page=1&sortBy=nome&sortDirection=ASC";
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"size\":5,\"totalElements\":5,\"totalPages\":1,\"number\":1")));
    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"GROUP_CREATE"})
    void searchePerfilByNome() throws Exception {
        this.setListPerfis();
        String path = ENDPOINT + "?page=1&sortBy=nome&sortDirection=ASC&nome=Teste Two";
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Descrição teste two")))
                .andExpect(content().string(containsString("size\":1,\"totalElements\":1,\"totalPages\":1,\"number\":1")));

    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"GROUP_CREATE"})
    void searchePerfilByDescricao() throws Exception {
        this.setListPerfis();
        String path = ENDPOINT + "?page=1&sortBy=nome&sortDirection=ASC&descricao=Descrição teste two";
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Descrição teste two")))
                .andExpect(content().string(containsString("size\":1,\"totalElements\":1,\"totalPages\":1,\"number\":1")));

    }

    @Test
    //@WithMockUser (username = "admin", authorities = {"GROUP_CREATE"})
    void searcheAcaoReturnEmpty() throws Exception {
        String path = ENDPOINT + "?page=1&sortBy=nome&sortDirection=ASC&descricao=Perfil não existente";
        mvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("size\":0,\"totalElements\":0,\"totalPages\":0,\"number\":1")));    }


    /**
     * Inicio dos métodos privado auxiliares para os testes
     */
    private Perfil setNewPerfil(){
        return this.perfilRepository.save(Perfil.builder()
                .nome("Administrador")
                .descricao("Administrador do Sistema")
                .permissoes(this.PERMISSOES)
                .build()
        );
    }

    private PerfilInDTO setNewPerfilEntradaDTO(String nome, String descricao, List<IdDTO> permissoes){
        return PerfilInDTO.builder()
                .nome(nome)
                .descricao(descricao)
                .permissoes(permissoes)
                .build();
    }

    private Perfil setNewPerfilEntrada(String nome, String descricao, List<Permissao> permissoes){
        return Perfil.builder()
                .nome(nome)
                .descricao(descricao)
                .permissoes(permissoes)
                .build();
    }

    private void setListPerfis(){
        this.perfilRepository.save(this.setNewPerfilEntrada("Teste One", "Descrição teste one", this.PERMISSOES));
        this.perfilRepository.save(this.setNewPerfilEntrada("Teste Two", "Descrição teste two", this.PERMISSOES));
        this.perfilRepository.save(this.setNewPerfilEntrada("Teste Three", "Descrição teste three", this.PERMISSOES));
        this.perfilRepository.save(this.setNewPerfilEntrada("Teste Four", "Descrição teste four", this.PERMISSOES));
    }

    private List<Permissao> getListPermissoes(){
        List<Permissao> permissoes = new ArrayList<>();
        permissoes.add(this.permissaoRepository.findByCodigo("gestao-processos:cadastros:geral:bancos:duplicar").get());
        permissoes.add(this.permissaoRepository.findByCodigo("gestao-processos:cadastros:geral:bancos:editar").get());
        permissoes.add(this.permissaoRepository.findByCodigo("gestao-processos:cadastros:geral:bancos:excluir").get());
        permissoes.add(this.permissaoRepository.findByCodigo("gestao-processos:cadastros:geral:escritorios:editar").get());
        permissoes.add(this.permissaoRepository.findByCodigo("gestao-processos:cadastros:geral:escritorios:excluir").get());
        permissoes.add(this.permissaoRepository.findByCodigo("gestao-processos:cadastros:geral:escritorios:incluir").get());
        return  permissoes;
    }

    /**
     * Fim
     */
}
