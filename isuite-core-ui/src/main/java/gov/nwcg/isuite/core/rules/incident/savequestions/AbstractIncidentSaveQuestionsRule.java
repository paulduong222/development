package gov.nwcg.isuite.core.rules.incident.savequestions;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class AbstractIncidentSaveQuestionsRule extends AbstractRule {
	protected Incident entity;
	protected Collection<IncidentQuestionVo> vos;
	protected IncidentDao incidentDao;
	
	public AbstractIncidentSaveQuestionsRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentSaveQuestionsRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			entity = (Incident)object;
		if(objectName.equals(IncidentSaveQuestionsRuleFactory.ObjectTypeEnum.INCIDENT_QUESTION_VOS.name()))
			vos = (Collection<IncidentQuestionVo>)object;
		if(objectName.equals(IncidentSaveQuestionsRuleFactory.ObjectTypeEnum.INCIDENT_DAO.name()))
			incidentDao = (IncidentDao)object;
	}
	
}
