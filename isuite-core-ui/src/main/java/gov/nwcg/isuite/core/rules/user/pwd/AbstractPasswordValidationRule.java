package gov.nwcg.isuite.core.rules.user.pwd;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.rules.user.importusers.ImportUserAccountsRuleFactory;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractPasswordValidationRule extends AbstractRule {
	protected String currentPassword;
	protected String newPassword;
	protected String confirmPassword;
	protected UserVo userVo;
	protected User userEntity;
	protected final int MIN_PASSWORD_LENGTH = 12;
	
	public AbstractPasswordValidationRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.CURRENT_PASSWORD.name()))
			currentPassword = (String)object;
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.NEW_PASSWORD.name()))
			newPassword = (String)object;
		if(objectName.equals(ImportUserAccountsRuleFactory.ObjectTypeEnum.DEFAULT_PASSWORD.name()))
			newPassword = (String)object;
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.CONFIRM_PASSWORD.name()))
			confirmPassword = (String)object;
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.USER_VO.name()))
			userVo = (UserVo)object;
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.USER_ENTITY.name()))
			userEntity = (User)object;
	}

	
	
}
