package gov.nwcg.isuite.core.rules.incident.restrincadduser;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class AbstractRestrIncAddUserRule extends AbstractRule{
	protected Incident entity;
	protected Collection<RestrictedIncidentUserVo> riuVos;
	
	public AbstractRestrIncAddUserRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/**
	 * @param object
	 * @param objectName
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(RestrictIncidentAddUserRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			entity = (Incident)object;
		if(objectName.equals(RestrictIncidentAddUserRuleFactory.ObjectTypeEnum.RESTRICTED_INCIDENT_USER_VO.name()))
			riuVos  = (Collection<RestrictedIncidentUserVo>)object;
	}
	
	
}
