package gov.nwcg.isuite.core.rules.incidentgroup.saveqskinds;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public abstract class AbstractIncidentGroupSaveQSKindsRule extends AbstractRule {
	protected IncidentGroup entity;
	protected IncidentGroupDao dao;
	protected Collection<KindVo> kindVos;
	
	public AbstractIncidentGroupSaveQSKindsRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if (objectName == IncidentGroupSaveQSKindsRuleFactory.ObjectTypeEnum.INCIDENT_GROUP.name()) {
			entity = (IncidentGroup) object;
		} else if (objectName == IncidentGroupSaveQSKindsRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_DAO.name()) {
			dao = (IncidentGroupDao) object;
		} else if (objectName == IncidentGroupSaveQSKindsRuleFactory.ObjectTypeEnum.QS_KINDS_VOS.name()) {
			kindVos = (Collection<KindVo>) object;
		} 
	}
}
