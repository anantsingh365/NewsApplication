package com.anant.newApp.Aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class PresentationControllerLogging {

    Logger logger = Logger.getAnonymousLogger();

    @Before("execution(* com.anant.newApp.controller.NewsPresentation.topHeadLines(..))")
    public void beforeAddAccountAdvice() {
        logger.log(Level.INFO,"This is Advice executing before Presentation controller Method");
    }
}
