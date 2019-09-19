package gov.nwcg.isuite.core.rules.referencedata.agencycodes.delete;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataDeleteAgencyCodeRuleFactory {

	public enum ObjectTypeEnum {
		AGENCY_VO,
		AGENCY_ENTITY,
		AGENCY_DAO
	}

	public enum RuleEnum {
		CHECK_IF_STANDARD_AGENCY_CODE_IS_CURRENTLY_IN_USE(10),
		CHECK_IF_NON_STANDARD_AGENGY_CODE_IS_CURRENTLY_IN_USE(15),
		PREVENT_STANDARD_AGENCY_CODE_DELETION(20)
		;

		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}

	}

	public static IRule getInstance(
			RuleEnum rule, 
			ApplicationContext ctx,
			AgencyVo agencyVo,
			Agency agencyEntity,
			AgencyDao agencyDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new CheckIfStandardAgencyCodeIsCurrentlyInUseRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckIfNonStandardAgencyCodeIsCurrentlyInUseRules(ctx);
				break;
			case 20:
				//ruleInstance = new PreventStandardAgencyCodeDeletionRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(agencyVo, ObjectTypeEnum.AGENCY_VO.name());
			ruleInstance.setObject(agencyEntity, ObjectTypeEnum.AGENCY_ENTITY.name());
			ruleInstance.setObject(agencyDao, ObjectTypeEnum.AGENCY_DAO.name());
		}

		return ruleInstance;
	}
}
