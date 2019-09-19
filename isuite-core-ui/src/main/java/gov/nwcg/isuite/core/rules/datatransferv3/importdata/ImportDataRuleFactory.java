package gov.nwcg.isuite.core.rules.datatransferv3.importdata;

import gov.nwcg.isuite.core.domain.xmlv3.DataTransferXml;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ImportDataRuleFactory {

	public enum ObjectTypeEnum {
		FILE_PASSWORD
		,DATA_TRANSFER_XML
		,FROM_SERVLET
	}
	
	public enum RuleEnum {
		VALIDATE_FILE_PASSWORD(10)
		,CHECK_REVISION_LEVEL(15)
		,CHECK_INCIDENT_MATCH(50)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	/**
	 * @param rule
	 * @param ctx
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx,String password, DataTransferXml dataTransferXml, Boolean fromServlet) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateFilePasswordRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckRevisionLevelRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(password, ObjectTypeEnum.FILE_PASSWORD.name());
			ruleInstance.setObject(dataTransferXml, ObjectTypeEnum.DATA_TRANSFER_XML.name());
			ruleInstance.setObject(fromServlet, ObjectTypeEnum.FROM_SERVLET.name());
		}
		
		return ruleInstance;
	}
	

	
}
