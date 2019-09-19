package gov.nwcg.isuite.core.rules.incidentgroup.removeincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractIncidentGroupRemoveIncidentRule extends AbstractRule {
	protected IncidentGroupDao incidentGroupDao=null;
	protected IncidentGroup incidentGroupEntity=null;
	protected Incident incidentEntity=null;
	
	public AbstractIncidentGroupRemoveIncidentRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentGroupRemoveIncidentRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_ENTITY.name()))
			incidentGroupEntity = (IncidentGroup)object;
		if(objectName.equals(IncidentGroupRemoveIncidentRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_DAO.name()))
			incidentGroupDao = (IncidentGroupDao)object;
		if(objectName.equals(IncidentGroupRemoveIncidentRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			incidentEntity = (Incident)object;
	}
	
	
}
