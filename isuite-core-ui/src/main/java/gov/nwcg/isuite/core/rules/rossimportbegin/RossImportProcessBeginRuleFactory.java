package gov.nwcg.isuite.core.rules.rossimportbegin;

import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class RossImportProcessBeginRuleFactory {

	public enum ObjectTypeEnum {
		ROSS_XML_FILE_VO
		,ROSS_IMPORT_PROCESS_VO
		,ROSS_XML_FILE_ID
		,REIMPORT_REQ_IDS
		,USER_VO
	}
	
	public enum RuleEnum {
		CHECK_MATCH_INCIDENT(10)
		,CHECK_INCIDENT_LOCKED(13)
		,CHECK_USER_ACCESS(15)
		,LOAD_ROSS_RESOURCES(20)
		,LOAD_ROSS_RESOURCES_REIMPORT(25)
		,CHECK_CONFLICTS(30)
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

	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, RossXmlFileVo vo, UserVo userVo, Collection<Long> reimportReqIds, DialogueVo dialogueVo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance=new CheckMatchIncidentRules(ctx);
				break;
			case 13:
				ruleInstance=new CheckIncidentLockedRules(ctx);
				break;
			case 15:
				ruleInstance=new CheckUserAccessRules(ctx);
				break;
			case 20:
				ruleInstance=new LoadRossResourcesRules(ctx);
				break;
			case 25:
				ruleInstance=new LoadRossResourcesReimportRules(ctx);
				break;
			case 30:
				ruleInstance=new CheckConflictsRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.ROSS_XML_FILE_VO.name());
			ruleInstance.setObject(userVo, ObjectTypeEnum.USER_VO.name());
			ruleInstance.setObject(reimportReqIds, ObjectTypeEnum.REIMPORT_REQ_IDS.name());
		}
		
		return ruleInstance;
	}
	

	
}
