package gov.nwcg.isuite.core.rules.usergroup.save;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.rules.usergroup.delete.UserGroupDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.UserGroupVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public abstract class AbstractUserGroupSaveRule extends AbstractRule {
	protected UserGroup entity;
	protected UserGroupVo vo;
	protected UserGroupDao dao;
	
	public AbstractUserGroupSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if (objectName == UserGroupSaveRuleFactory.ObjectTypeEnum.USER_GROUP.name()) {
			entity = (UserGroup) object;
		} else if(objectName == UserGroupSaveRuleFactory.ObjectTypeEnum.USER_GROUP_VO.name()) {
			vo = (UserGroupVo) object;
		} else if (objectName == UserGroupSaveRuleFactory.ObjectTypeEnum.USER_GROUP_DAO.name()) {
			dao = (UserGroupDao) object;
		} 
	}
}
