package com.annotations;

import com.dto.SanitizeType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface SanitizeHtml {

    SanitizeType cleanType() default SanitizeType.NONE;
}
