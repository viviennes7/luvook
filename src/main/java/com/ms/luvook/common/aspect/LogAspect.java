package com.ms.luvook.common.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAspect {
	final static String ELAPSED_UNIT = "(ms)";
	
//	@Pointcut("execution(* com.ms.luvook..controller.*Controller.*(..))")
//	public void controllers() {}
//	
//	@Pointcut("@annotation(com.ms.luvook.common.annotation.BasicLog)")
//	public void basicLog(){}
//	
//	@Around("controllers() || basicLog()")
//	public Object basic(ProceedingJoinPoint joinPoint) throws Throwable{
//		
//		long start = System.currentTimeMillis();
//		log.info("########## Start ::::: {} ##########", joinPoint.getSignature().toShortString() );
//		
//		logParameters(joinPoint);
//		
//		Object output = joinPoint.proceed();
//		
//		long end = System.currentTimeMillis();
//		log.info("########## End ::::: {} / {}##########", joinPoint.getSignature().toShortString(), (end-start) + ELAPSED_UNIT );
//		
//		log.info("result ::::: {}", output);
//		
//		return output;
//	}
//	
//	public void logParameters(JoinPoint joinPoint){
//		String result = null;
//		StringBuilder sb = new StringBuilder("param ::::: ");
//		
//		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//		String[] parameterNames = signature.getParameterNames();
//		Object[] parameterValues = joinPoint.getArgs();
//
//		for (int i = 0; i < parameterValues.length; i++) {
//			sb.append(parameterNames[i]);
//			sb.append(" : ");
//			sb.append(parameterValues[i]);
//			sb.append(" , ");
//		}
//		result = sb.toString();
//		
//		log.info(result);
//	}
	
}
