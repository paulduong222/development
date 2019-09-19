package gov.nwcg.isuite.core.rules.common.sitemanaged;

import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSiteManagedRule extends AbstractRule {
	protected Long id=0L;
	protected String idType="";
	
	protected String lockedMessage="This Incident/Incident Group has been transitioned to Site and is locked for editing.";
	
	public AbstractSiteManagedRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SiteManagedRuleFactory.ObjectTypeEnum.ID.name()))
			id = (Long)object;
		if(objectName.equals(SiteManagedRuleFactory.ObjectTypeEnum.ID_TYPE.name()))
			idType = (String)object;
	}

	
	
}
