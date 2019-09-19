package gov.nwcg.isuite.core.rules.iap.deletemasterfrequency;

import gov.nwcg.isuite.core.domain.IapMasterFrequency;
import gov.nwcg.isuite.core.persistence.IapMasterFrequencyDao;
import gov.nwcg.isuite.core.vo.IapMasterFrequencyVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class MasterFrequencyDeleteRuleFactory {
	
	public enum ObjectTypeEnum {
		MASTER_FREQUENCY_VO
		,MASTER_FREQUENCY_DAO
		,MASTER_FREQUENCY_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0);
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapMasterFrequencyVo vo, IapMasterFrequency entity, IapMasterFrequencyDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.MASTER_FREQUENCY_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.MASTER_FREQUENCY_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.MASTER_FREQUENCY_DAO.name());
		}
		
		return ruleInstance;
	}

}
