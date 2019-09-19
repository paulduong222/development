package gov.nwcg.isuite.core.rules.referencedata.adrates.save;

import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.core.persistence.RateClassRateDao;
import gov.nwcg.isuite.core.vo.RateClassRateVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSaveADRateRuleFactory {
	
	public enum ObjectTypeEnum {
		RATE_CLASS_RATE_VO,
		RATE_CLASS_RATE_ENTITY,
		RATE_CLASS_RATE_DAO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(5)
		,CHECK_FOR_VALID_YEAR(10)
		,CHECK_FOR_DUPLICATE_AD_RATE(15)
		;
		
		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, RateClassRateVo vo, RateClassRate entity,
			ApplicationContext ctx, RateClassRateDao rateClassRateDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckValidYearADRateRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckForDuplicateADRateRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.RATE_CLASS_RATE_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.RATE_CLASS_RATE_ENTITY.name());
			ruleInstance.setObject(rateClassRateDao, ObjectTypeEnum.RATE_CLASS_RATE_DAO.name());
		}

		return ruleInstance;
	}

}
