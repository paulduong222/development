package gov.nwcg.isuite.core.rules.incidentgroup.delete;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractIncidentGroupDeleteRule extends AbstractRule {
	protected Long incidentGroupId;
	protected IncidentGroupDao incidentGroupDao;
	protected IncidentGroup incidentGroupEntity;
	
	public AbstractIncidentGroupDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/**
	 * @param object
	 * @param objectName
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentGroupDeleteRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_ID.name()))
			incidentGroupId = (Long)object;
		if(objectName.equals(IncidentGroupDeleteRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_DAO.name()))
			incidentGroupDao = (IncidentGroupDao)object;
		if(objectName.equals(IncidentGroupDeleteRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_ENTITY.name()))
			incidentGroupEntity = (IncidentGroup)object;
	}
	
}
