package gov.nwcg.isuite.core.rules.iap.savesetting;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

public class SettingSaveRuleFactory {
	
	public enum ObjectTypeEnum {
		SETTING_VO
		,SETTING_DAO
		,SETTING_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,VALIDATE_DATA(5);
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IncidentVo vo, Incident entity, IncidentDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.SETTING_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.SETTING_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.SETTING_DAO.name());
		}
		
		return ruleInstance;
	}

}

