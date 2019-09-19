package gov.nwcg.isuite.core.rules.rossimportend;

import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossImportVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class RossImportProcessEndRuleFactory {

	public enum ObjectTypeEnum {
		ROSS_XML_FILE_VO
		,ROSS_IMPORT_VO
		,ROSS_XML_FILE_ID
		,USER_VO
	}
	
	public enum RuleEnum {
		CREATE_INCIDENT(10)
		,CREATE_RESOURCES(20)
		,MATCH_RESOURCES(30)
		,CREATE_EXCLUDE_RESOURCES(40)
		,UPDATE_EXCLUDE_RESOURCES(50)
		,FINAL_(100)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
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

	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, RossImportVo rossImportVo, UserVo userVo, DialogueVo dialogueVo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new CreateIncidentRules(ctx);
				break;
			case 20:
				ruleInstance = new CreateResourcesRules(ctx);
				break;
			case 30:
				ruleInstance = new MatchResourcesRules(ctx);
				break;
			case 40:
				ruleInstance = new CreateExcludeResourcesRules(ctx);
				break;
			case 50:
				ruleInstance = new UpdateExcludeResourcesRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(rossImportVo, ObjectTypeEnum.ROSS_IMPORT_VO.name());
			ruleInstance.setObject(userVo, ObjectTypeEnum.USER_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
