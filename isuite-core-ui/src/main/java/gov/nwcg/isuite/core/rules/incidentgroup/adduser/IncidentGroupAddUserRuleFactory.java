package gov.nwcg.isuite.core.rules.incidentgroup.adduser;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentGroupAddUserRuleFactory {

	public enum ObjectTypeEnum {
		ORIGINAL_IG_VO
		,NEW_IG_VO
		,INCIDENT_GROUP_DAO
	}
	
	public enum RuleEnum {
		IG_USER_CHECK_PERMISSIONS(0)
		,IG_USER_UNIQUE_USERS(5)
		,IG_CHECK_USERS_ARE_NON_PRIV_USERS(10)
		,IG_CHECK_USERS_NOT_ALREADY_ADDED(15)
		,IG_MINIMUM_ONE_USER(20)
		,IG_ADD_USERS(25)
		,IG_REMOVE_USERS(30)
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
	 * @param incidentGroupId
	 * @param userGridVos
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, 
			                        ApplicationContext ctx, 
			                        IncidentGroupVo newVo, 
			                        IncidentGroupVo originalVo,  
			                        IncidentGroupDao igDao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx, rule.name());
				break;
			case 5:
				ruleInstance = new UniqueUsersRules(ctx, rule.name());
				break;
			case 10:
				ruleInstance = new CheckUsersAreNonPrivUsersRules(ctx, rule.name());
				break;
			case 15:
				ruleInstance = new CheckUsersNotAlreadyAddedRules(ctx, rule.name());
				break;
			case 20:
				ruleInstance = new MinimumOneUserRules(ctx, rule.name());
				break;
			case 25:
				ruleInstance = new AddUsersRules(ctx, rule.name());
				break;
			case 30:
				ruleInstance = new RemoveUsersRules(ctx, rule.name());
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(newVo, ObjectTypeEnum.NEW_IG_VO.name());
			ruleInstance.setObject(originalVo, ObjectTypeEnum.ORIGINAL_IG_VO.name());
			ruleInstance.setObject(igDao, ObjectTypeEnum.INCIDENT_GROUP_DAO.name());
		}
		
		return ruleInstance;
	}
	

	
}
