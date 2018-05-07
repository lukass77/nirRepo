package com.nir.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: NirB
 * Date: 3/30/13
 * Time: 2:06 AM
 */
@Component
@Aspect
public class LoggerAspect {

//   @Before("execution(* com.nir.aop.MyService.*(..))")
//   @Before("within(com.nir.aop.MyServiceImpl)")
   @Before("@within(com.nir.aop.NeedFilter)")
    public void doLog(JoinPoint jp){
       Signature signature1 = jp.getSignature();
       String signature = signature1.getName();
       String targetName = jp.getTarget().getClass().getName();
       String thisName = jp.getThis().getClass().getName();

       System.out.println("Before aspect method 123 "+ "method "+signature+",Target class : "+targetName+",ThisClass "+thisName+" ,Kind -"+jp.getKind());
    }

   // @After("execution(* com.nir.aop.MyService.*(..))")
    public void doLogAfter(){
        System.out.println("After aspect method 123");
    }

//    @AfterReturning(pointcut="execution(* com.nir.aop.MyService.*(..))", returning = "result")
    @AfterReturning(pointcut = "@within(com.nir.aop.NeedFilter)", returning = "result")
    public List doLogAfterR(Object result){
        List<String> mm = (List<String>) result;
        mm.remove(0);
        System.out.println(mm);
        return mm;

    }
}

