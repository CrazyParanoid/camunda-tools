package io.github.camunda.tools.delegate;

import io.github.camunda.tools.process.ProcessVariable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Delegate {
    String beanName();

    String key();

    ProcessVariable[] variables() default {};
}
