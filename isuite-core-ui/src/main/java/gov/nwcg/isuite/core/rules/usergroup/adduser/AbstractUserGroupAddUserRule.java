package gov.nwcg.isuite.core.rules.usergroup.adduser;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.vo.UserGroupUserVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public abstract class AbstractUserGroupAddUserRule extends AbstractRule {
	protected UserGroup entity;
	protected Collection<UserGroupUserVo> vos;
	protected UserGroupDao ugDao;
	protected UserDao userDao;
	
	public AbstractUserGroupAddUserRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if (objectName == UserGroupAddUserRuleFactory.ObjectTypeEnum.USER_GROUP_USER_VOS.name()) {
//			vos = new ArrayList<UserVo>();
//			for(Object obj : (Collection) object) {
//				vos.add((UserVo)obj);
//			}
			vos = (Collection<UserGroupUserVo>) object;
		} else if (objectName == UserGroupAddUserRuleFactory.ObjectTypeEnum.USER_GROUP_DAO.name()) {
			ugDao = (UserGroupDao) object;
		} else if (objectName == UserGroupAddUserRuleFactory.ObjectTypeEnum.USER_DAO.name()) {
			userDao = (UserDao) object;
		} else if (objectName == UserGroupAddUserRuleFactory.ObjectTypeEnum.USER_GROUP.name()) {
			entity = (UserGroup) object;
		} 
	} 
}
