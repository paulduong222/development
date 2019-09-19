package gov.nwcg.isuite.core.rules.incident.savequestions;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class IncidentSaveQuestionsRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_ENTITY
		,INCIDENT_QUESTION_VOS
		,INCIDENT_DAO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
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
	                                Incident entity,
	                                Collection<IncidentQuestionVo> vos,
	                                IncidentDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckUniqueQuestionRules(ctx);
				break;
			case 20:
				ruleInstance = new SyncQuestionRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.INCIDENT_ENTITY.name());
			ruleInstance.setObject(vos, ObjectTypeEnum.INCIDENT_QUESTION_VOS.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.INCIDENT_DAO.name());
		}
		
		return ruleInstance;
	}
	
	
}
