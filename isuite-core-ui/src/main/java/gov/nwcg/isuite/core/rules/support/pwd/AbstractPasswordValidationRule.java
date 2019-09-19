package gov.nwcg.isuite.core.rules.support.pwd;

import gov.nwcg.isuite.core.rules.user.importusers.ImportUserAccountsRuleFactory;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractPasswordValidationRule extends AbstractRule {
	protected String newPassword;
	protected String confirmPassword;
	protected final int MIN_PASSWORD_LENGTH = 12;
	
	public AbstractPasswordValidationRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.NEW_PASSWORD.name()))
			newPassword = (String)object;
		if(objectName.equals(ImportUserAccountsRuleFactory.ObjectTypeEnum.DEFAULT_PASSWORD.name()))
			newPassword = (String)object;
		if(objectName.equals(PasswordValidationRuleFactory.ObjectTypeEnum.CONFIRM_PASSWORD.name()))
			confirmPassword = (String)object;
	}

	
	
}
