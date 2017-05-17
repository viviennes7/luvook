package com.ms.luvook.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAspect {
	
	final static String ELAPSED_UNIT = "(ms)";
	
	@Pointcut("execution(* com.ms.luvook..controller.*Controller.*(..))")
	public void controllers() {}
	
	@Around("controllers()")
	public Object basic(ProceedingJoinPoint joinPoint) throws Throwable{
		
		
		long start = System.currentTimeMillis();
		log.info("########## Start ::::: {} ##########", joinPoint.getSignature().toShortString() );
		
		logParameters(joinPoint);
		
		Object output = joinPoint.proceed();
		
		long end = System.currentTimeMillis();
		log.info("########## End ::::: {} / {}##########", joinPoint.getSignature().toShortString(), (end-start) + ELAPSED_UNIT );
		
		logResult(output);
		
		return output;
	}
	
	public void logParameters(JoinPoint joinPoint){
		String result = "param ::::: "; 
		
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String[] parameterNames = signature.getParameterNames();
		Object[] parameterValues = joinPoint.getArgs();

		for (int i = 0; i < parameterValues.length; i++) {
			result += parameterNames[i] + " : ";
			result += parameterValues[i] + " , ";
		}
		
		log.info(result);
	}
	
	public void logResult(Object output){
		log.info("result ::::: {}", output);
	}
}
