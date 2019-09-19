package gov.nwcg.isuite.core.rules.incident.savecostdefaults;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractIncidentSaveCostDefaultsRule extends AbstractRule {
	protected Incident entity;
	protected IncidentDao incidentDao;
	
	public AbstractIncidentSaveCostDefaultsRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentSaveCostDefaultsRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			entity = (Incident)object;
		if(objectName.equals(IncidentSaveCostDefaultsRuleFactory.ObjectTypeEnum.INCIDENT_DAO.name()))
			incidentDao = (IncidentDao)object;
	}
	
}
