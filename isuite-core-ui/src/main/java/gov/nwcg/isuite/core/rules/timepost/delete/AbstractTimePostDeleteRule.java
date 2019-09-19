package gov.nwcg.isuite.core.rules.timepost.delete;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractTimePostDeleteRule extends AbstractRule {
	protected TimePostDao tpDao=null;
	protected AssignmentTimePost entity=null;
	
	public AbstractTimePostDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(TimePostDeleteRuleFactory.ObjectTypeEnum.TIME_POST_DAO.name()))
			tpDao = (TimePostDao)object;
		if(objectName.equals(TimePostDeleteRuleFactory.ObjectTypeEnum.ASSIGNMENT_TIME_POST_ENTITY.name()))
			entity = (AssignmentTimePost)object;
	}
	
	
}
