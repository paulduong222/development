package gov.nwcg.isuite.core.rules.incidentgroup.addusergroupuser;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentGroupAddUserGroupUserRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_GROUP_ENTITY
		,USER_GROUP_ENTITY
		,INCIDENT_GROUP_DAO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IncidentGroup igEntity, UserGroup ugEntity, IncidentGroupDao igDao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(igDao, ObjectTypeEnum.INCIDENT_GROUP_DAO.name());
			ruleInstance.setObject(ugEntity, ObjectTypeEnum.USER_GROUP_ENTITY.name());
			ruleInstance.setObject(igEntity, ObjectTypeEnum.INCIDENT_GROUP_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
