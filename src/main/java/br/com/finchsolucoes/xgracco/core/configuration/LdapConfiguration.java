//package br.com.finchsolucoes.xgracco.core.configuration;
//
//
//import br.com.finchsolucoes.xgracco.infra.security.EncryptionUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.ldap.core.LdapTemplate;
//import org.springframework.ldap.core.support.LdapContextSource;
//
//import java.io.Serializable;
//import java.util.Objects;
//
///**
// * Configurações do LDAP.
// */
//@Configuration
//public class LdapConfiguration implements Serializable {
//
//TODO ACERTAR ESSA CLASSE E VOLTAR A DEPENDENCIA NO POM.XML

//    @Autowired
//    private Environment environment;
//
//    @Bean
//    public LdapContextSource contextSource () {
//
//        LdapContextSource contextSource = new LdapContextSource();
//        String url = environment.getProperty("externo.url");
//
//        if (StringUtils.isEmpty(url)) {
//            return null;
//        }
//
//        contextSource.setUrl(url);
//        contextSource.setBase(environment.getProperty("externo.base"));
//
//        if (Boolean.parseBoolean(environment.getRequiredProperty("password.encrypt"))) {
//            EncryptionUtil.setKey();
//            contextSource.setUserDn(EncryptionUtil.decrypt(environment.getProperty("externo.username")));
//            contextSource.setPassword(EncryptionUtil.decrypt(environment.getProperty("externo.password")));
//        } else {
//            contextSource.setUserDn(environment.getProperty("externo.username"));
//            contextSource.setPassword(environment.getProperty("externo.password"));
//        }
//
//        return contextSource;
//    }
//
//    @Bean
//    public LdapTemplate ldapTemplate() {
//
//        LdapContextSource source = contextSource();
//
//        return Objects.nonNull(source) ? new LdapTemplate(contextSource()) : null;
//    }
//
//}
