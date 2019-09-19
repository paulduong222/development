package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;

import org.springframework.context.ApplicationContext;

public class RossImportProcessRuleFactory {

	public enum ObjectTypeEnum {
		ROSS_XML_FILE_VO
		,ROSS_IMPORT_PROCESS_VO
		,ROSS_XML_FILE_ID
		,ACTION
		,USER_VO
	}
	
	/*
	 * NOTE: Always keep GET_ROSS_IMPORT_PROCESS as the first one in the list.
	 */
	public enum RuleEnum {
		GET_ROSS_IMPORT_PROCESS(0,"")
		,MATCH_INCIDENT_PROCESS(1,"DEST_MATCH_INCIDENT")
		,DATA_ERRORS(2,"DEST_RESOLVE_DATA_CONFLICTS")
		,MATCH_RESOURCES_RQ_NBR_AND_NAME(3,"DEST_MATCH_REQ_NBR_RES_NAME")
		,MATCH_RESOURCES_RQ_NBR(4,"DEST_MATCH_REQ_NBR")
		,MATCH_RESOURCES_RES_NAME_ITEM_NAME(5,"DEST_MATCH_RES_NAME_ITEM_NAME")
		,UNMATCHED_RESOURCES_NO_REQ_NUM(6,"DEST_UNMATCHED_RESOURCES_NO_REQ_NUM")
		,UNMATCHED_RESOURCES(7,"DEST_UNMATCHED_RESOURCES")
		,VIEW_ROSS_RESOURCES_EXCLUDE(8,"DEST_VIEW_ROSS_RESOURCES_EXCLUDE")
		,OVERHEAD_RESOURCE_GROUPS(9,"DEST_OVERHEAD_RESOURCE_GROUPS")
		,REVIEW_ROSTERED_RESOURCES(10,"DEST_REVIEW_ROSTERED_RESOURCES")
		,FINAL_CONFIRMATION(11,"DEST_FINAL_CONFIRMATION")
		;
		
		private int ruleIdx=-1;
		private String destination="";
		
		RuleEnum(int idx,String dest){
			ruleIdx=idx;
			destination=dest;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}

		public String getDestination(){
			return destination;
		}

	}	

	public static RuleEnum getRuleEnum(int ruleIdx) {
		return null;
	}

	public static int getRuleIndexByName(String ruleName) {
		for(RuleEnum ruleEnum : RuleEnum.values()){
			if(ruleEnum.name().equals(ruleName)){
				return ruleEnum.getRuleIdx();
			}
		}
		return -1;
	}

	public static String getRuleDestinationByName(String ruleName) {
		for(RuleEnum ruleEnum : RuleEnum.values()){
			if(ruleEnum.name().equals(ruleName)){
				return ruleEnum.getDestination();
			}
		}
		return "";
	}
	
	public static IWizardRule getInstanceByName(String name, ApplicationContext ctx, RossXmlFileVo vo) throws Exception {
		IWizardRule ruleInstance = null;

		int idx = getRuleIndexByName(name);

		switch(idx)
		{
			case 0:
				ruleInstance=new GetRossImportProcessRules(ctx);
				break;
			case 1:
				ruleInstance = new MatchIncidentRules(ctx);
				break;
			case 2:
				ruleInstance = new DataErrorRules(ctx);
				break;
			case 3:
				ruleInstance = new MatchResourcesByReqNbrAndNameRules(ctx);
				break;
			case 4:
				ruleInstance = new MatchResourcesByReqNbrRules(ctx);
				break;
			case 5:
				ruleInstance = new MatchResourcesByNameAndKindRules(ctx);
				break;
			case 6:
				ruleInstance = new UnmatchedResourcesNoReqNumRules(ctx);
				break;
			case 7:
				ruleInstance = new UnmatchedResourcesRules(ctx);
				break;
			case 8:
				ruleInstance = new ViewRossResourcesExcludeRules(ctx);
				break;
			case 9:
				ruleInstance = new OverheadResourceGroupsRules(ctx);
				break;
			case 10:
				ruleInstance = new ReviewRosteredResourcesRules(ctx);
				break;
			case 11:
				ruleInstance = new FinalConfirmationRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.ROSS_XML_FILE_VO.name());
		}
		
		return ruleInstance;
	}

	public static IWizardRule getInstance(RuleEnum rule, ApplicationContext ctx, RossXmlFileVo vo, UserVo userVo) throws Exception {
		IWizardRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance=new GetRossImportProcessRules(ctx);
				break;
			case 1:
				ruleInstance = new MatchIncidentRules(ctx);
				break;
			case 2:
				ruleInstance = new DataErrorRules(ctx);
				break;
			case 3:
				ruleInstance = new MatchResourcesByReqNbrAndNameRules(ctx);
				break;
			case 4:
				ruleInstance = new MatchResourcesByReqNbrRules(ctx);
				break;
			case 5:
				ruleInstance = new MatchResourcesByNameAndKindRules(ctx);
				break;
			case 6:
				ruleInstance = new UnmatchedResourcesNoReqNumRules(ctx);
				break;
			case 7:
				ruleInstance = new UnmatchedResourcesRules(ctx);
				break;
			case 8:
				ruleInstance = new ViewRossResourcesExcludeRules(ctx);
				break;
			case 9:
				ruleInstance = new OverheadResourceGroupsRules(ctx);
				break;
			case 10:
				ruleInstance = new ReviewRosteredResourcesRules(ctx);
				break;
			case 11:
				ruleInstance = new FinalConfirmationRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			System.out.println(ruleInstance.getRuleName());
			ruleInstance.setObject(vo, ObjectTypeEnum.ROSS_XML_FILE_VO.name());
			ruleInstance.setObject(userVo, ObjectTypeEnum.USER_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
