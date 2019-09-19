package gov.nwcg.isuite.core.rules.referencedata.unitids.save;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSaveUnitIdRuleFactory {

	public enum ObjectTypeEnum {
		ORGANIZATION_VO,
		ORGANIZATION_ENTITY,
		ORGANIZATION_DAO
	}

	public enum RuleEnum {
		VALIDATE_DATA(5),
		CONTAINS_HYPHEN(7),
		CHECK_FOR_DUPLICATE_UNIT_ID(10),
		STANDARD_UNIT_ID_CHANGE_LOCAL_SETTING(15);
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
			OrganizationVo organizationVo,
			Organization organizationEntity,
			OrganizationDao organizationDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 7:
				ruleInstance = new ContainsHyphenRule(ctx);
				break;
			case 10:
				ruleInstance = new CheckForDuplicateUnitIdRules(ctx);
				break;
			case 15:
//				ruleInstance = new StandardUnitIdChangeLocalSettingRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(organizationVo, ObjectTypeEnum.ORGANIZATION_VO.name());
			ruleInstance.setObject(organizationEntity, ObjectTypeEnum.ORGANIZATION_ENTITY.name());
			ruleInstance.setObject(organizationDao, ObjectTypeEnum.ORGANIZATION_DAO.name());
		}

		return ruleInstance;
	}
}
