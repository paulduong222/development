package gov.nwcg.isuite.core.rules.user.disableusers;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.rules.user.saveuser.SaveUserRuleFactory;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class AbstractDisableUserRule extends AbstractRule {

	protected Collection<UserVo> userVos;
	protected Collection<User> userEntities;
	protected UserDao userDao;
	
	public AbstractDisableUserRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DisableUsersRuleFactory.ObjectTypeEnum.USER_VOS.name()))
			userVos = (Collection<UserVo>)object;
		if(objectName.equals(DisableUsersRuleFactory.ObjectTypeEnum.USER_ENTITIES.name()))
			userEntities = (Collection<User>)object;
		if(objectName.equals(SaveUserRuleFactory.ObjectTypeEnum.USER_DAO.name()))
			userDao = (UserDao)object;
	}
}
