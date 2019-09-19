package gov.nwcg.isuite.core.rules.referencedata.subcategorygroups.save;

import gov.nwcg.isuite.core.domain.SubGroupCategory;
import gov.nwcg.isuite.core.persistence.SubGroupCategoryDao;
import gov.nwcg.isuite.core.vo.SubGroupCategoryVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSaveSubGroupCategoryRuleFactory {
	
	public enum ObjectTypeEnum {
		SUB_GROUP_CATEOGRY_VO,
		SUB_GROUP_CATEGORY_ENTITY,
		SUB_GROUP_CATEGORY_DAO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(5)
		,CHECK_FOR_DUPLICATE_SUB_GROUP_CATEGORY(10)
		,PREVENT_STANDARD_SUB_GROUP_CATEGORY_CHANGE(20)
		;
		
		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, SubGroupCategoryVo vo, 
			SubGroupCategory entity, SubGroupCategoryDao subGroupCategoryDao) throws Exception {
		
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckForDuplicateSubGroupCategoryRules(ctx);
				break;
			case 20:
//				ruleInstance = new PreventStandardSubGroupCategoryChangeRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, 	ObjectTypeEnum.SUB_GROUP_CATEOGRY_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.SUB_GROUP_CATEGORY_ENTITY.name());
			ruleInstance.setObject(subGroupCategoryDao, ObjectTypeEnum.SUB_GROUP_CATEGORY_DAO.name());
		}
		
		return ruleInstance;
	}

}
