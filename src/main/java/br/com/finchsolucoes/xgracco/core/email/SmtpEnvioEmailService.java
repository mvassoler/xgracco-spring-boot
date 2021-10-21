package br.com.finchsolucoes.xgracco.core.email;

import br.com.finchsolucoes.xgracco.core.constants.ValidationConstants;
import br.com.finchsolucoes.xgracco.core.handler.exception.BadRequestException;
import br.com.finchsolucoes.xgracco.core.handler.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.core.handler.exception.FileAttachmentException;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocale;
import br.com.finchsolucoes.xgracco.domain.dto.output.RetornoMetodoDTO;
import br.com.finchsolucoes.xgracco.legacy.beans.parametros.ParametrosEmail;
import br.com.finchsolucoes.xgracco.legacy.mail.Attachment;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

	private static final String SUCESSO = "E-mail(s) enviado(s) com sucesso.";

	private final Environment environment;
	private final ParametrosEmailBO configuracoesBO;
	private final MessageLocale messageLocale;

	public SmtpEnvioEmailService(Environment environment, ParametrosEmailBO configuracoesBO, MessageLocale messageLocale) {
		this.environment = environment;
		this.configuracoesBO = configuracoesBO;
		this.messageLocale = messageLocale;
	}

	@Override
	public RetornoMetodoDTO sendMail(List<String> to, List<String> cc, List<String> cco, List<String> replyTo, String subject, String message, List<Attachment> anexos) {
		Long tamanhoTotalAnexos = Long.valueOf(Objects.requireNonNull(this.environment.getProperty("project.email.size.attachments")));
		RetornoMetodoDTO retorno = new RetornoMetodoDTO();
		try {
			ParametrosEmail config = configuracoesBO.get();
			if (Objects.isNull(config)) {
				throw new EntityNotFoundException(this.messageLocale.validationMessageSource(ValidationConstants.REGISTER_NOT_FOUND_CUSTOM).replace("table", "ParametrosBO"));
			}
			Properties props = this.setPropriedades(config);
			Session session = this.setSessao(config, props);
			MimeMessage msg =  this.setMimeMessage(session, to, cc, cco, replyTo, config, subject);
			BodyPart messageBodyPart = new MimeBodyPart();
			MimeMultipart multipart = new MimeMultipart("related");
			messageBodyPart.setContent(message, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);
			System.setProperty("mail.mime.base64.ignoreerrors", "true");
			if(this.getTamanhoTotalAnexos(multipart, anexos) > tamanhoTotalAnexos){
				retorno.setSucesso(false);
				retorno.setMensagem(this.messageLocale.validationMessageSource(ValidationConstants.ERRO_TAMANHO_ANEXO_EMAIL));
			}
			else{
				// COLOCA TODAS AS PROPRIEDADES JUNTAS
				msg.setContent(multipart);
				// OBJETO ENCARREGADO DE ENVIAR OS DADOS PARA O EMAIL
				Transport tr;
				// DEFINE SMTP PARA TRANSPORTE
				tr = session.getTransport("smtp");
				tr.connect(config.getServidorMail(), config.getPortaMail().intValue(), config.getUsuarioMail(), config.getSenhaMail());
				// SALVA AS CONFIGURACOES
				msg.saveChanges();
				// ENVIO DA MENSAGEM
				tr.sendMessage(msg, msg.getAllRecipients());
				tr.close();
				retorno.setMensagem(SUCESSO);
				retorno.setSucesso(true);
			}
		} catch (FileAttachmentException e) {
			retorno.setMensagem(this.messageLocale.validationMessageSource(ValidationConstants.ERRO_ANEXOS_EMAIL));
			retorno.setSucesso(false);
		}catch (MessagingException e) {
			retorno.setMensagem(this.messageLocale.validationMessageSource(ValidationConstants.ERRO_COMPRESSAO_ANEXO_EMAIL));
			retorno.setSucesso(false);
		} catch (Exception e) {
			retorno.setMensagem(this.messageLocale.validationMessageSource(ValidationConstants.ERRO_ENVIO_EMAIL));
			retorno.setSucesso(false);
		}
		return retorno;
	}

	private Properties setPropriedades(ParametrosEmail config){
		// MONTA AS CONFIGURACOES PARA O ENVIO DO E-MAIL
		Properties props = new Properties();
		// DEFINE COMO PROTOCOLO SMTP
		props.put("mail.transport.protocol", "smtp");
		// VERIFICA SE FOI MARCADO TIPO DE CRIPTOGRAFIA
		if (config.isTlsMail()) {
			props.put("mail.smtp.starttls.enable", "true");
		}
		// SERVIDOR SMTP
		props.put("mail.smtp.host", config.getServidorMail());
		// POSSUI AUTENTICACAO
		if (config.isAutenticacaoMail()) {
			// ATIVA AUTENTICACAO
			props.put("mail.smtp.auth", "true");
			// USUÁRIO PARA AUTENTICACAO
			props.put("mail.smtp.user", config.getUsuarioMail());
		} else {
			// DESATIVA AUTENTICACAO
			props.put("mail.smtp.auth", Boolean.FALSE);
		}
		// REMOVE O DEBUG DO LOG
		props.put("mail.debug", "false");
		// PORTA PARA ENVIO
		props.put("mail.smtp.port", config.getPortaMail());
		// VERIFICA SE FOI MARCADO TIPO DE CRIPTOGRAFIA
		if (config.isSslMail()) {
			// MESMA PORTA PARA SOCKET
			props.put("mail.smtp.socketFactory.port", config.getPortaMail());
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", Boolean.FALSE);
		}
		// POSSUI PROXY
		if (config.isProxyMail()) {
			props.setProperty("proxySet", "true");
			// ENDERECO SERVIDOR PROXY
			props.setProperty("socksProxyHost", config.getEnderecoProxyMail());
			// PORTA DO SERVIDOR PROXY
			props.setProperty("socksProxyPort", "" + config.getPortaProxyMail());
		}
		return props;
	}

	private Session setSessao(ParametrosEmail config, Properties props){
		Session session = null;
		// SE POSSUI AUTENTICACAO
		if (config.isAutenticacaoMail()) {
			// CRIA UM AUTENTICADOR QUE SERA USADO A SEGUIR
			SimpleAuth auth = new SimpleAuth(config.getUsuarioMail(), config.getSenhaMail());
			session = Session.getInstance(props, auth);
		} else {
			session = Session.getInstance(props);
		}
		// HABILITA O LOG DAS ACOES EXECUTADAS DURANTE O ENVIO DO EMAIL
		session.setDebug(false);
		return session;
	}

	private MimeMessage setMimeMessage(Session session, List<String> to, List<String> cc, List<String> cco, List<String> replyTo, ParametrosEmail config, String subject){
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setRecipients(Message.RecipientType.TO, this.getInternetAddresses(to));
			msg.setRecipients(Message.RecipientType.CC, this.getInternetAddresses(cc));
			msg.setRecipients(Message.RecipientType.BCC, this.getInternetAddresses(cco));
			msg.setRecipients(Message.RecipientType.BCC, this.getInternetAddresses(replyTo));
			msg.setFrom(new InternetAddress(config.getEmailRemetenteMail()));
			msg.setSubject(subject, "utf-8");
		}catch (AddressException ex){
			throw new BadRequestException(this.messageLocale.validationMessageSource(ValidationConstants.ERRO_ENDERECOS_EMAIL));
		} catch (MessagingException e) {
			throw new BadRequestException(this.messageLocale.validationMessageSource(ValidationConstants.ERRO_ASSUNTO_EMAIL));
		}
		return msg;
	}

	private InternetAddress[] getInternetAddresses(List<String> end) throws AddressException {
		InternetAddress[] enderecos = null;
		if (end != null && !end.isEmpty()) {
			enderecos = new InternetAddress[end.size()];
			for (int i = 0; i < end.size(); i++) {
				if (StringUtils.isNotBlank(end.get(i))) {
					enderecos[i] = new InternetAddress(end.get(i));
				}
			}
		}
		return  enderecos;
	}

	private static class SimpleAuth extends Authenticator {
		public String username = null;
		public String password = null;

		public SimpleAuth(String user, String pwd) {
			username = user;
			password = pwd;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	private Long getTamanhoTotalAnexos(MimeMultipart multipart, List<Attachment> anexos) throws MessagingException{
		Long tamanhoTotalAnexos = 0L;
		if (CollectionUtils.isNotEmpty(anexos)) {
			for (Attachment attachment : anexos) {
				multipart.addBodyPart(attachment.create());
				tamanhoTotalAnexos += attachment.getFile().length();
			}
		}
		return tamanhoTotalAnexos;
	}

}
