package gov.nwcg.isuite.core.rules.incidentgroup.savequestions;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.rules.incident.savequestions.IncidentSaveQuestionsRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentGroupQuestionVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class AbstractIncidentGroupSaveQuestionsRule extends AbstractRule {
	protected IncidentGroup entity;
	protected ArrayList<IncidentGroupQuestionVo> vos;
	protected IncidentGroupDao incidentGroupDao;
	
	public AbstractIncidentGroupSaveQuestionsRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentGroupSaveQuestionsRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_ENTITY.name()))
			entity = (IncidentGroup)object;
		if(objectName.equals(IncidentGroupSaveQuestionsRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_QUESTION_VOS.name()))
			vos = (ArrayList<IncidentGroupQuestionVo>)object;
		if(objectName.equals(IncidentSaveQuestionsRuleFactory.ObjectTypeEnum.INCIDENT_DAO.name()))
			incidentGroupDao = (IncidentGroupDao)object;
	}
	
}
