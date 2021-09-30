package br.com.finchsolucoes.xgracco.core.configuration;


import br.com.finchsolucoes.xgracco.core.configuration.annotation.QueryParam;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

/**
 * Resolve o nome dos atributos considerando a anotação {@link JsonProperty} do Jackson.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */

public class JacksonServletModelAttributeResolver extends ServletModelAttributeMethodProcessor {
    /**
     * Class constructor.
     *
     * @param annotationNotRequired if "true", non-simple method arguments and
     *                              return values are considered model attributes with or without a
     *                              {@code @ModelAttribute} annotation
     */
    //TODO - Acertar a classe com.google.common depreciada
    public JacksonServletModelAttributeResolver(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

//    private ConcurrentMap<Class<?>, Map<String, String>> definitionsCache = Maps.newConcurrentMap();
//
//    public JacksonServletModelAttributeResolver(boolean annotationNotRequired) {
//        super(annotationNotRequired);
//    }
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.hasParameterAnnotation(QueryParam.class);
//    }
//
//    @Override
//    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
//        ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
//        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
//        bind(servletRequest, servletBinder);
//    }
//
//    @SuppressWarnings("unchecked")
//    public void bind(ServletRequest request, ServletRequestDataBinder binder) {
//        Map<String, ?> propertyValues = parsePropertyValues(request, binder);
//        MutablePropertyValues mpvs = new MutablePropertyValues(propertyValues);
//        MultipartRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartRequest.class);
//        if (multipartRequest != null) {
//            bindMultipart(multipartRequest.getMultiFileMap(), mpvs);
//        }
//
//        String attr = HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
//        mpvs.addPropertyValues((Map<String, String>) request.getAttribute(attr));
//        binder.bind(mpvs);
//    }
//
//    private Map<String, ?> parsePropertyValues(ServletRequest request, ServletRequestDataBinder binder) {
//        Map<String, Object> params = Maps.newTreeMap();
//        Objects.requireNonNull(request, "Request must not be null");
//        Enumeration<?> paramNames = request.getParameterNames();
//        Map<String, String> parameterMappings = getParameterMappings(binder);
//        while (paramNames != null && paramNames.hasMoreElements()) {
//            String paramName = (String) paramNames.nextElement();
//            String[] values = request.getParameterValues(paramName);
//
//            String fieldName = parameterMappings.get(paramName);
//            if (fieldName == null) {
//                fieldName = paramName;
//            }
//
//            if (values == null || values.length == 0) {
//            } else if (values.length > 1) {
//                params.put(fieldName, values);
//            } else {
//                params.put(fieldName, values[0]);
//            }
//        }
//
//        return params;
//    }
//
//    private Map<String, String> getParameterMappings(ServletRequestDataBinder binder) {
//        Class<?> targetClass = binder.getTarget().getClass();
//        Map<String, String> map = definitionsCache.get(targetClass);
//        if (map == null) {
//            Field[] fields = targetClass.getDeclaredFields();
//            map = Maps.newHashMapWithExpectedSize(fields.length);
//            for (Field field : fields) {
//                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
//                if (annotation != null && !annotation.value().isEmpty()) {
//                    map.put(annotation.value(), field.getName());
//                }
//            }
//            definitionsCache.putIfAbsent(targetClass, map);
//            return map;
//        } else {
//            return map;
//        }
//    }
//
//    protected void bindMultipart(Map<String, List<MultipartFile>> multipartFiles, MutablePropertyValues mpvs) {
//        for (Map.Entry<String, List<MultipartFile>> entry : multipartFiles.entrySet()) {
//            String key = entry.getKey();
//            List<MultipartFile> values = entry.getValue();
//            if (values.size() == 1) {
//                MultipartFile value = values.get(0);
//                if (!value.isEmpty()) {
//                    mpvs.add(key, value);
//                }
//            } else {
//                mpvs.add(key, values);
//            }
//        }
//    }
}