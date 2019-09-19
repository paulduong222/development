package gov.nwcg.isuite.core.rules.incident.unrestrictincident;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.rules.incident.restrictincident.RestrictIncidentRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractUnrestrictIncidentRule extends AbstractRule {
	protected IncidentVo vo=null;
	protected Incident incidentEntity=null;
	
	public AbstractUnrestrictIncidentRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(UnrestrictIncidentRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			incidentEntity = (Incident)object;
		if(objectName.equals(UnrestrictIncidentRuleFactory.ObjectTypeEnum.INCIDENT_VO.name()))
			vo = (IncidentVo)object;
	}
	
}
