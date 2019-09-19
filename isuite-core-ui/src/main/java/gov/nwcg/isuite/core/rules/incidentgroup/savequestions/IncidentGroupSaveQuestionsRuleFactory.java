package gov.nwcg.isuite.core.rules.incidentgroup.savequestions;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGroupQuestionVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class IncidentGroupSaveQuestionsRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_GROUP_ENTITY
		,INCIDENT_GROUP_QUESTION_VOS
		,INCIDENT_GROUP_DAO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_QUESTION_MARK(1)
		,CHECK_UNIQUE_QUESTION(5)
		,SYNC_QUESTIONS(20)
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
	 * @param entity
	 * @param vos
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, 
	                                ApplicationContext ctx, 
	                                IncidentGroup entity,
	                                Collection<IncidentGroupQuestionVo> vos,
	                                IncidentGroupDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx, rule.name());
				break;
			case 1:
				//ruleInstance = new CheckQuestionMarkRules(ctx, rule.name());
				break;
			case 5:
				ruleInstance = new CheckUniqueQuestionRules(ctx, rule.name());
				break;
			case 20:
				ruleInstance = new SyncQuestionRules(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.INCIDENT_GROUP_ENTITY.name());
			ruleInstance.setObject(vos, ObjectTypeEnum.INCIDENT_GROUP_QUESTION_VOS.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.INCIDENT_GROUP_DAO.name());
		}
		
		return ruleInstance;
	}
	
	
}
