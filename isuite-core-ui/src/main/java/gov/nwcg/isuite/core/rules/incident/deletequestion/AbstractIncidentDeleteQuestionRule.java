package gov.nwcg.isuite.core.rules.incident.deletequestion;


import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractIncidentDeleteQuestionRule extends AbstractRule {
	protected IncidentQuestion entity;

	public AbstractIncidentDeleteQuestionRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentDeleteQuestionRuleFactory.ObjectTypeEnum.INCIDENT_QUESTION_ENTITY.name()))
			entity = (IncidentQuestion)object;
	}
	
}
