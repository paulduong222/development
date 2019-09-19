package gov.nwcg.isuite.core.rules.referencedata.unitids.delete;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataDeleteUnitIdRuleFactory {
	
	public enum ObjectTypeEnum {
		ORGANIZATION_VO,
		ORGANIZATION_ENTITY,
		ORGANIZATION_DAO
	}

	public enum RuleEnum {
		CHECK_IF_STANDARD_UNIT_ID_IS_CURRENTLY_IN_USE(0),
		CHECK_IF_NON_STANDARD_UNIT_ID_IS_CURRENTLY_IN_USE(5),
		PREVENT_STANDARD_UNIT_ID_DELETION(10)
		;

		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}

	}

	public static IRule getInstance(RuleEnum rule, OrganizationVo vo, Organization entity,
			ApplicationContext ctx, OrganizationDao organizationDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckIfStandardUnitIdIsCurrentlyInUseRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckIfNonStandardUnitIdIsCurrentlyInUseRules(ctx);
				break;
			case 10:
//				ruleInstance = new PreventStandardUnitIdDeletionRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.ORGANIZATION_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.ORGANIZATION_ENTITY.name());
			ruleInstance.setObject(organizationDao, ObjectTypeEnum.ORGANIZATION_DAO.name());
		}

		return ruleInstance;
	}
}
