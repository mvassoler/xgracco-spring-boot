package br.com.finchsolucoes.xgracco.core.configuration;

import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * Post-processor usado para utilizar o {@link JacksonServletModelAttributeResolver}.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class JacksonHandlerMappingPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String arg1) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String arg1) throws BeansException {
        if (bean instanceof RequestMappingHandlerAdapter) {
            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
            List<HandlerMethodArgumentResolver> resolvers = adapter.getCustomArgumentResolvers();
            if (resolvers == null) {
                resolvers = Lists.newArrayList();
            }
            resolvers.add(new JacksonServletModelAttributeResolver(false));
            adapter.setCustomArgumentResolvers(resolvers);
        }

        return bean;
    }
}