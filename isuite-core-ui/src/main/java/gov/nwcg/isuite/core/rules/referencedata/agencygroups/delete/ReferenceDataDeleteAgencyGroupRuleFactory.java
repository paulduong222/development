package gov.nwcg.isuite.core.rules.referencedata.agencygroups.delete;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.persistence.AgencyGroupDao;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataDeleteAgencyGroupRuleFactory {

	public enum ObjectTypeEnum {
		AGENCY_GROUP_VO,
		AGENCY_GROUP_ENTITY,
		AGENCY_GROUP_DAO
	}

	public enum RuleEnum {
		CHECK_IF_AGENCY_GROUP_IS_CURRENTLY_IN_USE(0),
		PREVENT_STANDARD_AGENCY_GROUP_DELETION(5)
		;

		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}

	}

	public static IRule getInstance(RuleEnum rule, AgencyGroupVo vo, AgencyGroup entity,
			ApplicationContext ctx, AgencyGroupDao agencyGroupDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckIfAgencyGroupIsCurrentlyInUseRules(ctx);
				break;
			case 5:
				//ruleInstance = new PreventStandardAgencyGroupDeletionRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.AGENCY_GROUP_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.AGENCY_GROUP_ENTITY.name());
			ruleInstance.setObject(agencyGroupDao, ObjectTypeEnum.AGENCY_GROUP_DAO.name());
		}

		return ruleInstance;
	}
}
