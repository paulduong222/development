package gov.nwcg.isuite.core.rules.iap.savesetting;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractSettingSaveRule extends AbstractRule {
	
	IncidentVo incidentVo;
	Incident incidentEntity;
	IncidentDao incidentDao;
	
	public AbstractSettingSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SettingSaveRuleFactory.ObjectTypeEnum.SETTING_VO.name()))
			incidentVo = (IncidentVo)object;
		if(objectName.equals(SettingSaveRuleFactory.ObjectTypeEnum.SETTING_ENTITY.name()))
			incidentEntity = (Incident)object;
		if(objectName.equals(SettingSaveRuleFactory.ObjectTypeEnum.SETTING_DAO.name()))
			incidentDao = (IncidentDao)object;
	}
}

