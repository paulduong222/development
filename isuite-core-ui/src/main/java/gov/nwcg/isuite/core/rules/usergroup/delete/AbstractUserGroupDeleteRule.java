package gov.nwcg.isuite.core.rules.usergroup.delete;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public abstract class AbstractUserGroupDeleteRule extends AbstractRule {
	protected UserGroup entity;
	protected UserGroupDao dao;
	
	public AbstractUserGroupDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if (objectName == UserGroupDeleteRuleFactory.ObjectTypeEnum.USER_GROUP_DAO.name()) {
			dao = (UserGroupDao) object;
		} else if (objectName == UserGroupDeleteRuleFactory.ObjectTypeEnum.USER_GROUP.name()) {
			entity = (UserGroup) object;
		} 
	} 
}
