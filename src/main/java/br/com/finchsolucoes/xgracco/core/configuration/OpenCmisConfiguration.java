//package br.com.finchsolucoes.xgracco.core.configuration;
//
//import br.com.finchsolucoes.xgracco.infra.persistence.cmis.OpenCmisSession;
//import br.com.finchsolucoes.xgracco.infra.security.EncryptionUtil;
//import br.com.finchsolucoes.xgracco.legacy.alfresco.CmisSession;
//import org.apache.chemistry.opencmis.client.api.Session;
//import org.apache.chemistry.opencmis.client.api.SessionFactory;
//import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
//import org.apache.chemistry.opencmis.commons.SessionParameter;
//import org.apache.chemistry.opencmis.commons.enums.BindingType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import java.beans.PropertyVetoException;
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Configurações do Apache OpenCMIS.
// *
// * @author Rodolpho Couto
// * @since 2.1
// */
//@Configuration
//public class OpenCmisConfiguration implements Serializable {
//
//    private static final Logger logger = LoggerFactory.getLogger(OpenCmisConfiguration.class);
//
//    @Autowired
//    private Environment environment;
//
//    /**
//     * Configura a Session do OpenCMIS.
//     *
//     * @return
//     * @throws PropertyVetoException
//     */
//    @Bean
//    public Session session() {
//        final Map<String, String> params = new HashMap<>();
//        params.put(SessionParameter.ATOMPUB_URL, environment.getRequiredProperty("alfresco.host"));
//        params.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
//        params.put(SessionParameter.AUTH_HTTP_BASIC, "true");
//        params.put(SessionParameter.USER, environment.getRequiredProperty("alfresco.user"));
//        params.put(SessionParameter.PASSWORD, environment.getRequiredProperty("alfresco.password"));
//
//        if(Boolean.valueOf(environment.getRequiredProperty("password.encrypt"))){
//            EncryptionUtil.setKey();;
//            params.put(SessionParameter.USER, EncryptionUtil.decrypt(environment.getRequiredProperty("alfresco.user").trim()));
//            params.put(SessionParameter.PASSWORD, EncryptionUtil.decrypt(environment.getRequiredProperty("alfresco.password").trim()));
//        }else{
//            params.put(SessionParameter.USER, environment.getRequiredProperty("alfresco.user"));
//            params.put(SessionParameter.PASSWORD, environment.getRequiredProperty("alfresco.password"));
//        }
//
//        final SessionFactory factory = SessionFactoryImpl.newInstance();
//        final Session session = factory.getRepositories(params).get(0).createSession();
//
//        logger.info("Apache OpenCMIS inicializado com sucesso!");
//        return session;
//    }
//
//    /**
//     * Configura o OpenCmisSession (wrapper).
//     *
//     * @return
//     */
//    @Bean
//    public OpenCmisSession cmisManager() {
//        return new OpenCmisSession(environment.getRequiredProperty("alfresco.path"));
//    }
//
//    /**
//     * Configura o CmisSession (wrapper).
//     *
//     * @return
//     */
//    @Bean
//    public CmisSession cmisSession() {
//        return new CmisSession(environment.getRequiredProperty("alfresco.path"));
//    }
//}
