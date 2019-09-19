package gov.nwcg.isuite.core.rules.common.password;

import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractPasswordValidationRule extends AbstractRule {
	protected Boolean currentRequired;   // Is the currentPassword required for processing Rule validation
	
	protected String currentPassword;    // UI form submitted current password
	protected String newPassword;        // UI form submitted new password
	protected String confirmNewPassword; // UI form submitted confirm password
	
	protected Long userId;                // user Id from database
	protected String userPassword;        // user Password from database

	protected final int MIN_PASSWORD_LENGTH = 12;
	
	public AbstractPasswordValidationRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.CURRENT_REQUIRED.name()))
			currentRequired = (Boolean)object;
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.CURRENT_PASSWORD.name()))
			currentPassword = (String)object;
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.NEW_PASSWORD.name()))
			newPassword = (String)object;
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.CONFIRM_NEW_PASSWORD.name()))
			confirmNewPassword = (String)object;
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.USER_ID.name()))
			userId = (Long)object;
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.USER_PASSWORD.name()))
			userPassword = (String)object;
	}

	
	
}
