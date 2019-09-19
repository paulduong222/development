package gov.nwcg.isuite.core.rules.iap.deleteoperationalperiod;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.core.persistence.IncidentShiftDao;
import gov.nwcg.isuite.core.vo.IncidentShiftVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractOperationalPeriodDeleteRule extends AbstractRule {
	
	IncidentShiftVo incidentShiftVo;
	IncidentShift incidentShiftEntity;
	IncidentShiftDao incidentShiftDao;
	
	public AbstractOperationalPeriodDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(OperationalPeriodDeleteRuleFactory.ObjectTypeEnum.OPERATIONAL_PERIOD_VO.name()))
			incidentShiftVo = (IncidentShiftVo)object;
		if(objectName.equals(OperationalPeriodDeleteRuleFactory.ObjectTypeEnum.OPERATIONAL_PERIOD_ENTITY.name()))
			incidentShiftEntity = (IncidentShift)object;
		if(objectName.equals(OperationalPeriodDeleteRuleFactory.ObjectTypeEnum.OPERATIONAL_PERIOD_DAO.name()))
			incidentShiftDao = (IncidentShiftDao)object;
	}
}
