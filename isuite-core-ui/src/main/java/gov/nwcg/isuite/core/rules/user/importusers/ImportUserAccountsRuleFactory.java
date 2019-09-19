package gov.nwcg.isuite.core.rules.user.importusers;

import gov.nwcg.isuite.core.persistence.UserImportFailureDao;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.xml.XmlHandler;

import org.springframework.context.ApplicationContext;

public class ImportUserAccountsRuleFactory {

	public enum ObjectTypeEnum {
		XML_HANDLER,
		XML_BYTE_ARRAY,
		UIF_DAO,
		DEFAULT_PASSWORD,
		CONFIRM_DEFAULT_PASSWORD
	}
	
	public enum RuleEnum {
		VALIDATE_IMPORT_USER_FORM_DATA_RULE(5),
		CHECK_PASSWORD_AND_CONFIRM_PASSWORD_RULE(10),
		CHECK_PASSWORD_FORMAT_RULE(15),
		SET_XML_SCHEMA_AND_XSD_PATH_RULE(20),
		UNMARSHAL_DATA_RULE(25)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	public static IRule getInstance(RuleEnum rule, 
	                                ApplicationContext ctx,
	                                String defaultPassword,
	                                String confirmDefaultPassword,
	                                byte[] xmlByteArray,
	                                UserImportFailureDao uifDao,
	                                XmlHandler xmlHandler) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 5:
				ruleInstance = new ValidateImportUserFormDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckPasswordAndConfirmPasswordMatchRules(ctx);
			    break;
			case 15:
				ruleInstance = new CheckFormatRules(ctx);
				break;
			case 20:
				ruleInstance = new SetXMLSchemaAndXSDPathRules(ctx);
				break;
			case 25:
				ruleInstance = new UnmarshalDataRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(xmlByteArray, ObjectTypeEnum.XML_BYTE_ARRAY.name());
			ruleInstance.setObject(uifDao, ObjectTypeEnum.UIF_DAO.name());
			ruleInstance.setObject(defaultPassword, ObjectTypeEnum.DEFAULT_PASSWORD.name());
			ruleInstance.setObject(confirmDefaultPassword, ObjectTypeEnum.CONFIRM_DEFAULT_PASSWORD.name());
			ruleInstance.setObject(xmlHandler, ObjectTypeEnum.XML_HANDLER.name());
		}
		
		return ruleInstance;
	}
	
}
