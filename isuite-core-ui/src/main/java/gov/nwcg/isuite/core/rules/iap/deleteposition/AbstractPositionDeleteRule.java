package gov.nwcg.isuite.core.rules.iap.deleteposition;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractPositionDeleteRule extends AbstractRule {
	
	public AbstractPositionDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		
	}

}
