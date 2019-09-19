package gov.nwcg.isuite.core.rules.incidentgroup.deletequestion;


import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractIncidentGroupDeleteQuestionRule extends AbstractRule {
	protected IncidentGroupQuestion entity;

	public AbstractIncidentGroupDeleteQuestionRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentGroupDeleteQuestionRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_QUESTION_ENTITY.name()))
			entity = (IncidentGroupQuestion)object;
	}
	
}
