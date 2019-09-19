package gov.nwcg.isuite.core.rules.incident.restrincadduserfromusergroup;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractRestrIncAddUserGroupUserRule extends AbstractRule{
	protected Incident incidentEntity;
	protected UserGroup userGroupEntity;

	public AbstractRestrIncAddUserGroupUserRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		
	}
	
}
