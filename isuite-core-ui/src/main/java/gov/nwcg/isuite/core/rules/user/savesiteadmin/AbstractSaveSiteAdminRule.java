package gov.nwcg.isuite.core.rules.user.savesiteadmin;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveSiteAdminRule extends AbstractRule {
	
	UserVo userVo;
	User userEntity;
	UserDao userDao;
	
	public AbstractSaveSiteAdminRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveSiteAdminRuleFactory.ObjectTypeEnum.USER_VO.name()))
			userVo = (UserVo)object;
		if(objectName.equals(SaveSiteAdminRuleFactory.ObjectTypeEnum.USER_ENTITY.name()))
			userEntity = (User)object;
		if(objectName.equals(SaveSiteAdminRuleFactory.ObjectTypeEnum.USER_DAO.name()))
			userDao = (UserDao)object;
	}
	
	
}
