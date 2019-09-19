package gov.nwcg.isuite.core.rules.referencedata.agencycodes.save;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSaveAgencyCodeRuleFactory {

	public enum ObjectTypeEnum {
		AGENCY_VO,
		AGENCY_ENTITY,
		AGENCY_DAO
	}

	public enum RuleEnum {
		VALIDATE_DATA(5)
		,CHECK_FOR_DUPLICATE_AGENCY_CODE(10)
		,PREVENT_STANDARD_AGENCY_CODE_CHANGE(20)
		;

		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}

	}

	public static IRule getInstance(RuleEnum rule, 
			ApplicationContext ctx,
			AgencyVo agencyVo,
			Agency agencyEntity,
			AgencyDao agencyDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckForDuplicateAgencyCodeRules(ctx);
				break;
			case 20:
//				ruleInstance = new PreventStandardAgencyCodeChangeRules(ctx);
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
