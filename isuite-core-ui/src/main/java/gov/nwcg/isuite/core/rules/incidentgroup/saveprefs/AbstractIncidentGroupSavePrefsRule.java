package gov.nwcg.isuite.core.rules.incidentgroup.saveprefs;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGroupPrefsVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public abstract class AbstractIncidentGroupSavePrefsRule extends AbstractRule {
	protected IncidentGroup entity;
	protected IncidentGroupDao dao;
	protected Collection<IncidentGroupPrefsVo> prefVos;
	
	public AbstractIncidentGroupSavePrefsRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if (objectName == IncidentGroupSavePrefsRuleFactory.ObjectTypeEnum.INCIDENT_GROUP.name()) {
			entity = (IncidentGroup) object;
		} else if (objectName == IncidentGroupSavePrefsRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_DAO.name()) {
			dao = (IncidentGroupDao) object;
		} else if (objectName == IncidentGroupSavePrefsRuleFactory.ObjectTypeEnum.PREFS_VOS.name()) {
			prefVos = (Collection<IncidentGroupPrefsVo>) object;
		} 
	}
}
