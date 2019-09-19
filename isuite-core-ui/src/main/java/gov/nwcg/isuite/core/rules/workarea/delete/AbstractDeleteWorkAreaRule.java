package gov.nwcg.isuite.core.rules.workarea.delete;

import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteWorkAreaRule extends AbstractRule {
	protected Long workAreaId;
	
	public AbstractDeleteWorkAreaRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(WorkAreaDeleteRuleFactory.ObjectTypeEnum.WORK_AREA_ID.name()))
			workAreaId = (Long)object;
	}

	
	
}
