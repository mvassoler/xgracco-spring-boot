package br.com.finchsolucoes.xgracco.resource.unitarios;

import br.com.finchsolucoes.xgracco.configuracao.FunctionalBaseTest;
import br.com.finchsolucoes.xgracco.configuracao.PathConfig;
import br.com.finchsolucoes.xgracco.domain.dto.input.AcaoInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.service.AcaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Objects;

@Sql(value = PathConfig.CLEAN, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AcaoResourceTests extends FunctionalBaseTest {

    @Autowired
    private AcaoService acaoService;

    @Test
    public void insertAcaoValidate(){
        //cenario
        AcaoInDTO acao = AcaoInDTO.builder().descricao("Teste" + LocalDateTime.now().toString()).build();
        //acão
        AcaoOutDTO acaoOutDTO = this.acaoService.add(acao).getData();
        //validação
        Assertions.assertTrue(Objects.nonNull(acaoOutDTO.getId()));
    }

    @Test
    public void insertAcaoWithoutDescricao(){
        //cenario
        AcaoInDTO acao = AcaoInDTO.builder().descricao(null).build();
        //acão
        DataIntegrityViolationException erroEsperado =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
                    this.acaoService.add(acao);
                });
        //validação
        Assertions.assertTrue(Objects.nonNull(erroEsperado));
    }


}
