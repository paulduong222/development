package gov.nwcg.isuite.core.rules.mbadmin.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractSaveMessageRule extends AbstractRule {
	
	public AbstractSaveMessageRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		
	}


}
