package gov.nwcg.isuite.core.rules.incidentresource.delete;

import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class AbstractIncidentResourceDeleteRule extends AbstractRule {
	protected Collection<Long> incidentResourceIds=null;
	protected IncidentResourceGridVo gridVo=null;
	
	public AbstractIncidentResourceDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentResourceDeleteRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_IDS.name()))
			incidentResourceIds = (Collection<Long>)object;
		if(objectName.equals(IncidentResourceDeleteRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_GRID_VO.name()))
			gridVo = (IncidentResourceGridVo)object;
	}

	
}
