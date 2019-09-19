package gov.nwcg.isuite.core.rules.datatransfer.importdata;

import gov.nwcg.isuite.core.domain.xml.DataTransferXml;
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
		,CHECK_SITE_GROUP_MATCH(100)
		,CHECK_ENTERPRISE_GROUP_MATCH(130)
		,CHECK_ENTSITE_DUPLICATE_INCIDENT(140)
		,CHECK_SITE_INCIDENT_IS_TRANSITIONED(145)
		,CHECK_ENTERPRISE_GROUP_UNLOCKED(155)
		,CHECK_ENTERPRISE_INCIDENT_UNLOCKED(160)
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
			case 50:
				//ruleInstance = new CheckIncidentMatchRules(ctx);
				break;
			case 100:
				ruleInstance = new CheckSiteGroupMatchRules(ctx);
				break;
			case 130:
				ruleInstance = new CheckEnterpriseGroupMatchRules(ctx);
				break;
			case 140:
				ruleInstance = new CheckEntSiteDuplicateIncidentRules(ctx);
				break;
			case 145:
				ruleInstance = new CheckSiteIncidentIsTransitionedRules(ctx);
				break;
			case 155:
				ruleInstance = new CheckEnterpriseGroupUnlockedRules(ctx);
				break;
			case 160:
				ruleInstance = new CheckEnterpriseIncidentUnlockedRules(ctx);
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
