package gov.nwcg.isuite.core.rules.incidentgroup.deleteuser;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class IncidentGroupDeleteUserRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_GROUP_ID
		,USER_GRID_VOS
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, Long incidentGroupId, Collection<UserGridVo> userGridVos, IncidentGroupDao igDao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(userGridVos, ObjectTypeEnum.USER_GRID_VOS.name());
			ruleInstance.setObject(incidentGroupId, ObjectTypeEnum.INCIDENT_GROUP_ID.name());
			ruleInstance.setObject(igDao, ObjectTypeEnum.INCIDENT_GROUP_DAO.name());
		}
		
		return ruleInstance;
	}
	

	
}
