package com.pece.agencia.api.utils;

import org.junit.jupiter.api.extension.ExtensionConfigurationException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Parameter;

public class TokenForParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Parameter parameter = parameterContext.getParameter();
        return parameter.isAnnotationPresent(TokenFor.class) && parameter.getType().equals(String.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        TokenFor annotation = parameterContext.getParameter().getAnnotation(TokenFor.class);
        if (annotation == null) {
            throw new ExtensionConfigurationException("@TokenFor annotation is missing");
        }
        ApplicationContext ctx = SpringExtension.getApplicationContext(extensionContext);
        LoginService loginService = ctx.getBean(LoginService.class);
        try {
            return loginService.login(annotation.username(), annotation.password());
        } catch (Exception e) {
            throw new ExtensionConfigurationException("Erro ao obter token JWT: " + e.getMessage(), e);
        }
    }
}

