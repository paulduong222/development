package gov.nwcg.isuite.core.rules.incidentgroup.addincidentscheck;

import java.util.Collection;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractAddIncidentsCheckRule extends AbstractRule {
	protected IncidentGroupVo incidentGroupVo=null;
	protected IncidentGroupDao incidentGroupDao=null;
	protected Collection<IncidentGridVo> incGridVos=null;
	
	public AbstractAddIncidentsCheckRule(ApplicationContext ctx){
		context=ctx;
	}

	public void setObject(Object object, String objectName) {
		if(objectName.equals(AddIncidentsCheckRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_DAO.name()))
			incidentGroupDao = (IncidentGroupDao)object;
		if(objectName.equals(AddIncidentsCheckRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_VO.name()))
			incidentGroupVo = (IncidentGroupVo)object;
		if(objectName.equals(AddIncidentsCheckRuleFactory.ObjectTypeEnum.INCIDENT_GRID_VOS.name()))
			incGridVos = (Collection<IncidentGridVo>)object;
	}

}
