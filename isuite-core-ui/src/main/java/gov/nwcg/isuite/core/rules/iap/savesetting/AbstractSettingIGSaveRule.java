package gov.nwcg.isuite.core.rules.iap.savesetting;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractSettingIGSaveRule extends AbstractRule {
	
	IncidentGroupVo incidentGroupVo;
	IncidentGroup incidentGroupEntity;
	IncidentGroupDao incidentGroupDao;
	
	public AbstractSettingIGSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SettingIGSaveRuleFactory.ObjectTypeEnum.SETTING_VO.name()))
			incidentGroupVo = (IncidentGroupVo)object;
		if(objectName.equals(SettingIGSaveRuleFactory.ObjectTypeEnum.SETTING_ENTITY.name()))
			incidentGroupEntity = (IncidentGroup)object;
		if(objectName.equals(SettingIGSaveRuleFactory.ObjectTypeEnum.SETTING_DAO.name()))
			incidentGroupDao = (IncidentGroupDao)object;
	}
}


