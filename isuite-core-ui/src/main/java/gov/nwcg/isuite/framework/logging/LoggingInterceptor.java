package gov.nwcg.isuite.framework.logging;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

public class LoggingInterceptor implements MethodInterceptor {
	
	private final Logger log = Logger.getLogger(getClass());

	/* (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		
		if(log.isDebugEnabled()){
			log.debug("Beginning method: " +
					  methodInvocation.getMethod().getDeclaringClass() + "::" +
					  methodInvocation.getMethod().getName());
			
			log.debug("Method parameters: " +
					  methodInvocation.getArguments().length);
			
			for(Object object : methodInvocation.getArguments()) {
				log.debug(LoggingInterceptor.createToString(object));
			}
					 
		}
		
		long startTime = System.currentTimeMillis();
		
		try{
			Object returnValue = methodInvocation.proceed();
			
			if(log.isDebugEnabled()){
				log.debug("Return value of method: " + LoggingInterceptor.createToString(returnValue));
			}
			return returnValue;
		}finally{
			if(log.isDebugEnabled()) {
				log.debug("Ending method: " +
						  methodInvocation.getMethod().getDeclaringClass() +
						  "::" + methodInvocation.getMethod().getName());
				log.debug("Method invocation time: " +
						  (System.currentTimeMillis() - startTime) + " milli seconds.");
			}
		}
	}

	/**
	 * Convenience method for logging custom messages in between the
	 * aop intercepted logging.
	 * 
	 * @param message
	 * 			the message to log
	 * @param level
	 * 			the log level
	 */
	public void addLog(String message, Level level) {
		switch(level.toInt()){
			case Level.TRACE_INT :
				log.trace(message);
				break;
			case Level.DEBUG_INT:
				log.debug(message);
				break;
			case Level.INFO_INT:
				log.info(message);
				break;
			case Level.WARN_INT:
				log.warn(message);
				break;
			case Level.ERROR_INT:
				log.error(message);
				break;
			case Level.FATAL_INT:
				log.fatal(message);
				break;
		}
	}
	
	private static String createToString(Object object) {
		StringBuilder builder = new StringBuilder();
		
		if(object != null) {
			builder.append(object.getClass().getName());
			builder.append(" { ");
			
			Method[] methods = object.getClass().getMethods();
		
			for(Method method : methods) {
				
				if(method.getName().startsWith("get") && method.getParameterTypes().length == 0){
					
					String propertyNameWithoutFirstCharacter = method.getName().substring(4);
					String propertyNameFirstCharacter = method.getName().substring(3,4).toLowerCase();
					String propertyName = propertyNameFirstCharacter + propertyNameWithoutFirstCharacter;
					
					Object propertyValue;
					
					try{
						propertyValue=method.invoke(object);
					}catch(Exception e){
						propertyValue=e.getMessage();
					}
		
					builder.append(" (")
						   .append(propertyName)
						   .append(": ")
						   .append(propertyValue)
						   .append(") ");
				}
			}
			
			builder.append("}");
		}else{
			return "null";
		}
		
		return builder.toString();
	}
	
}
