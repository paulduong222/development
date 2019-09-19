package gov.nwcg.isuite.core.rules.user.saveuser;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveUserRule extends AbstractRule {
	
	protected UserVo userVo;
	protected User userEntity;
	protected UserDao userDao;
	protected String dbName;
	
	public AbstractSaveUserRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveUserRuleFactory.ObjectTypeEnum.USER_VO.name()))
			userVo = (UserVo)object;
		if(objectName.equals(SaveUserRuleFactory.ObjectTypeEnum.USER_ENTITY.name()))
			userEntity = (User)object;
		if(objectName.equals(SaveUserRuleFactory.ObjectTypeEnum.USER_DAO.name()))
			userDao = (UserDao)object;
		if(objectName.equals(SaveUserRuleFactory.ObjectTypeEnum.DB_NAME.name()))
			dbName = (String)object;
	}
	
	
}
