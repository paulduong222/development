package gov.nwcg.isuite.core.rules.iap.savemasterfrequency;

import gov.nwcg.isuite.core.domain.IapMasterFrequency;
import gov.nwcg.isuite.core.persistence.IapMasterFrequencyDao;
import gov.nwcg.isuite.core.vo.IapMasterFrequencyVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class MasterFrequencySaveRuleFactory {
	
	public enum ObjectTypeEnum {
		MASTER_FREQUENCY_VO
		,MASTER_FREQUENCY_DAO
		,MASTER_FREQUENCY_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,VALIDATE_DATA(5)
		,CHECK_NOT_BLANK(10);
		
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
	 * @param vo
	 * @param entity
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapMasterFrequencyVo vo, IapMasterFrequency entity, IapMasterFrequencyDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				//ruleInstance = new CheckNotBlankRecordRules(ctx);
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
