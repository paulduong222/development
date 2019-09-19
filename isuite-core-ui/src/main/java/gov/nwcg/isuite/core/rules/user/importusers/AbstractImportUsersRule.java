package gov.nwcg.isuite.core.rules.user.importusers;

import gov.nwcg.isuite.core.persistence.UserImportFailureDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.xml.XmlHandler;

import org.springframework.context.ApplicationContext;

public class AbstractImportUsersRule extends AbstractRule {
	
	protected XmlHandler xmlHandler = new XmlHandler();
	protected byte[] xmlByteArray;
	protected UserImportFailureDao uifDao;
	protected String defaultPassword;
	protected String confirmDefaultPassword;
	
	public AbstractImportUsersRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ImportUserAccountsRuleFactory.ObjectTypeEnum.XML_HANDLER.name()))
			this.xmlHandler = (XmlHandler)object;
		if(objectName.equals(ImportUserAccountsRuleFactory.ObjectTypeEnum.XML_BYTE_ARRAY.name()))
			this.xmlByteArray = (byte[])object;
		if(objectName.equals(ImportUserAccountsRuleFactory.ObjectTypeEnum.UIF_DAO.name()))
			this.uifDao = (UserImportFailureDao)object;
		if(objectName.equals(ImportUserAccountsRuleFactory.ObjectTypeEnum.DEFAULT_PASSWORD.name()))
			this.defaultPassword = (String)object;
		if(objectName.equals(ImportUserAccountsRuleFactory.ObjectTypeEnum.CONFIRM_DEFAULT_PASSWORD.name()))
			this.confirmDefaultPassword = (String)object;
	}
	
	
}
