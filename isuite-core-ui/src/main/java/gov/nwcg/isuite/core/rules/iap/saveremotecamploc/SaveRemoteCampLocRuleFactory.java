package gov.nwcg.isuite.core.rules.iap.saveremotecamploc;

import gov.nwcg.isuite.core.domain.IapRemoteCampLocations;
import gov.nwcg.isuite.core.vo.IapRemoteCampLocationsVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveRemoteCampLocRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_REMOTE_CAMP_LOCATIONS_VO
		,IAP_REMOTE_CAMP_LOCATIONS_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapRemoteCampLocationsVo vo, IapRemoteCampLocations entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateRemoteCampLocDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_REMOTE_CAMP_LOCATIONS_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_REMOTE_CAMP_LOCATIONS_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

