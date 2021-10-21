package br.com.finchsolucoes.xgracco.resource.unitarios;

import br.com.finchsolucoes.xgracco.configuracao.FunctionalBaseTest;
import br.com.finchsolucoes.xgracco.configuracao.PathConfig;
import br.com.finchsolucoes.xgracco.core.email.SmtpEnvioEmailService;
import br.com.finchsolucoes.xgracco.domain.dto.output.RetornoMetodoDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

@Sql(value = {PathConfig.CLEAN, PathConfig.DATA}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SmtpEnvioEmailTests extends FunctionalBaseTest {

    @Autowired
    private SmtpEnvioEmailService emailService;

    private static final String TITULO = "TESTE DO SERVIÇO DO ENVIO DO EMAIL";

    private static List<String> DESTINATARIOS;
    private static List<String> COPIADOS;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        this.DESTINATARIOS = this.setDestinatarios();
        this.COPIADOS = this.setCopiados();
    }

    @Test
    public void sendEmailValidated() throws Exception {
        String mensagem = this.montarMensagemLink();
        RetornoMetodoDTO retorno =this.emailService.sendMail(this.DESTINATARIOS, this.COPIADOS, new ArrayList<>(), new ArrayList<>(), TITULO, mensagem, new ArrayList<>());
        Assertions.assertTrue(retorno.isSucesso());
    }

    @Test
    public void sendEmailNotValidated() throws Exception {
        String mensagem = this.montarMensagemLink();
        RetornoMetodoDTO retorno =this.emailService.sendMail(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), TITULO, mensagem, new ArrayList<>());
        Assertions.assertFalse(retorno.isSucesso());
    }

    @Test
    public void sendEmailAddressNotValidated() throws Exception {
        String mensagem = this.montarMensagemLink();
        this.DESTINATARIOS.clear();
        this.DESTINATARIOS.add("testegmail.com");
        RetornoMetodoDTO retorno =this.emailService.sendMail(this.DESTINATARIOS, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), TITULO, mensagem, new ArrayList<>());
        Assertions.assertFalse(retorno.isSucesso());
    }

    private String montarMensagemLink() {
        StringBuilder bodyEmail = new StringBuilder();
        bodyEmail.append("<h2>TESTE ENVIO E-MAIL</h2>");
        bodyEmail.append("<p>E-mail enviado com o objetivo de validar o serviço de envio.</p>");
        bodyEmail.append("<p>Desconsiderar este e-mail. Atenciosamente, </p>");
        bodyEmail.append("<h2>Equipe X-Gracco.</h2>");
        return bodyEmail.toString();
    }

    private List<String> setDestinatarios(){
        List<String> destinatarios = new ArrayList<>();
        destinatarios.add("marcosvassoler@finchsolucoes.com.br");
        return destinatarios;
    }

    private List<String> setCopiados(){
        List<String> copiados = new ArrayList<>();
        copiados.add("marcosvassoler@finchsolucoes.com.br");
        return copiados;
    }

}
