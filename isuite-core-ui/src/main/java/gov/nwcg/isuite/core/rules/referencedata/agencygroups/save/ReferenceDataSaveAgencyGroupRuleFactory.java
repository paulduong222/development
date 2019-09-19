package gov.nwcg.isuite.core.rules.referencedata.agencygroups.save;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.persistence.AgencyGroupDao;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSaveAgencyGroupRuleFactory {

	public enum ObjectTypeEnum {
		AGENCY_GROUP_VO,
		AGENCY_GROUP_ENTITY,
		AGENCY_GROUP_DAO
	}

	public enum RuleEnum {
		VALIDATE_DATA(5)
		,CHECK_FOR_DUPLICATE_AGENCY_GROUP(10),
		PREVENT_STANDARD_AGENCY_GROUP_CHANGE(15);
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
			AgencyGroupVo agencyGroupVo,
			AgencyGroup agencyGroupEntity,
			AgencyGroupDao agencyGroupDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckForDuplicateAgencyGroupRules(ctx);
				break;
			case 15:
//				ruleInstance = new PreventStandardAgencyGroupChangeRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(agencyGroupVo, ObjectTypeEnum.AGENCY_GROUP_VO.name());
			ruleInstance.setObject(agencyGroupEntity, ObjectTypeEnum.AGENCY_GROUP_ENTITY.name());
			ruleInstance.setObject(agencyGroupDao, ObjectTypeEnum.AGENCY_GROUP_DAO.name());
		}

		return ruleInstance;
	}
}
