package gov.nwcg.isuite.core.rules.incidentgroup.addusergroupuser;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractAddUserGroupUserIGRule extends AbstractRule {
	protected IncidentGroup entity;
	protected UserGroup userGroupEntity;
	protected IncidentGroupDao incidentGroupDao;
	
	public AbstractAddUserGroupUserIGRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/**
	 * @param object
	 * @param objectName
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentGroupAddUserGroupUserRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_ENTITY.name()))
			entity = (IncidentGroup)object;
		if(objectName.equals(IncidentGroupAddUserGroupUserRuleFactory.ObjectTypeEnum.USER_GROUP_ENTITY.name()))
			userGroupEntity = (UserGroup)object;
		if(objectName.equals(IncidentGroupAddUserGroupUserRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_DAO.name()))
			incidentGroupDao = (IncidentGroupDao)object;
	}
	
}
