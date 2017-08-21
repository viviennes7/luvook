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
	private static final String ELAPSED_UNIT = "(ms)";
	private static final String START_MESSAGE = "########## Start ::::: {} ##########";
	private static final String END_MESSAGE = "########## End ::::: {} / {}##########";
	private static final String PARAM_MESSAGE = "param ::::: ";
	private static final String RESULT_MESSAGE = "result ::::: {}";
	
	@Pointcut("execution(* com.ms.luvook..controller.*Controller.*(..))")
	public void controllers() {}
	
	@Pointcut("@annotation(com.ms.luvook.common.annotation.BasicLog)")
	public void basicLog(){}
	
	@Around("controllers() || basicLog()")
	public Object basic(ProceedingJoinPoint joinPoint) throws Throwable{
		
		long start = System.currentTimeMillis();
		log.info(START_MESSAGE, joinPoint.getSignature().toShortString() );
		logParameters(joinPoint);
		
		Object output = joinPoint.proceed();
		
		long end = System.currentTimeMillis();
		log.info(END_MESSAGE, joinPoint.getSignature().toShortString(), (end-start) + ELAPSED_UNIT );
		log.info(RESULT_MESSAGE, output);
		
		return output;
	}
	
	public void logParameters(JoinPoint joinPoint){
		String result = null;
		StringBuilder sb = new StringBuilder(PARAM_MESSAGE);
		
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String[] parameterNames = signature.getParameterNames();
		Object[] parameterValues = joinPoint.getArgs();

		for (int i = 0; i < parameterValues.length; i++) {
			sb.append(parameterNames[i]);
			sb.append(" : ");
			sb.append(parameterValues[i]);
			sb.append(" , ");
		}
		result = sb.toString();
		
		log.info(result);
	}
	
}
