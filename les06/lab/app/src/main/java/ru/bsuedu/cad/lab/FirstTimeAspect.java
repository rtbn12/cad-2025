package ru.bsuedu.cad.lab;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FirstTimeAspect {
    @Around("execution(* ru.bsuedu.cad.lab.CSVParser.parse(..))")
    public Object measureParseTime(ProceedingJoinPoint joinPoint) throws Throwable {


        long startTime = System.currentTimeMillis();


        System.out.println("Начинаю замер метода: " + joinPoint.getSignature().getName());


        Object result = joinPoint.proceed();


        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;


        System.out.println(" Метод " + joinPoint.getSignature().getName() +
                " выполнился за " + duration + " мс");

        return result;
    }
}
