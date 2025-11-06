package com.pece.agencia.api.controller.v1;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

@ControllerAdvice
public class ZalandoControllerAdvice implements ProblemHandling, SecurityAdviceTrait {
}
