package com.aspects;

import com.annotations.SanitizeHtml;
import com.dto.SanitizeType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Aspect
@Component
public class SanitizeHtmlAspect {

    @Before("execution(* *(.., @com.annotations.SanitizeHtml (*), ..))")
    public void beforeRequest(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Parameter[] parameters = method.getParameters();
        Optional<Parameter> requestParameter = Arrays.stream(parameters).filter(parameter -> parameter.isAnnotationPresent(SanitizeHtml.class)).findAny();

        if(requestParameter.isPresent()){
            Parameter param = requestParameter.get();
            Optional<Object> requestObject = Arrays.stream(joinPoint.getArgs()).filter(arg ->
                    arg.getClass().equals(param.getType())
            ).findFirst();

            requestObject.ifPresent(this::sanitizeHtml);
        }
    }

    private void sanitizeHtml(Object request) {
        Field[] fields = request.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getSimpleName().equals("SanitizeHtml")) {
                    SanitizeHtml sanitizeHtmlAnnotation = (SanitizeHtml) annotation;
                    SanitizeType sanitizeType = sanitizeHtmlAnnotation.cleanType();

                    field.setAccessible(true);
                    Object value = null;
                    try {
                        value = field.get(request);
                        if (value instanceof String) {
                            String stringValue = (String) value;
                            String sanitizedValue = Jsoup.clean(stringValue, getSafeList(sanitizeType));
                            field.set(request, sanitizedValue);

                        }else if(value instanceof Collection){
                            Collection collection = (Collection) value;
                            collection.forEach(this::sanitizeHtml);
                        }else{
                            sanitizeHtml(value);
                        }
                    } catch (IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    private Safelist getSafeList(SanitizeType cleanType) {
        switch (cleanType) {
            case NONE:
                return Safelist.none();
            case BASIC:
                return Safelist.basic();
            case SIMPLE_TEXT:
                return Safelist.simpleText();
            case BASIC_WITH_IMAGES:
                return Safelist.basicWithImages();
            case RELAXED:
                return Safelist.relaxed();
            default:
                return Safelist.none();
        }
    }
}
