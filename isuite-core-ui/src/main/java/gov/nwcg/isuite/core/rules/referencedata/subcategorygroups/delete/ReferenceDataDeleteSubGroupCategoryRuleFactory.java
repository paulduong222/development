package gov.nwcg.isuite.core.rules.referencedata.subcategorygroups.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.SubGroupCategory;
import gov.nwcg.isuite.core.persistence.SubGroupCategoryDao;
import gov.nwcg.isuite.core.vo.SubGroupCategoryVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

public class ReferenceDataDeleteSubGroupCategoryRuleFactory {
	
	public enum ObjectTypeEnum {
		SUB_GROUP_CATEOGRY_VO,
		SUB_GROUP_CATEGORY_ENTITY,
		SUB_GROUP_CATEGORY_DAO
	}
	
	public enum RuleEnum {
		CHECK_IF_SUB_GROUP_CATEGORY_CODE_IS_CURRENTLY_IN_USE(5)
		,PREVENT_STANDARD_SUB_GROUP_CATEGORY_CODE_DELETION(10)
		;
		
		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, SubGroupCategoryVo vo, SubGroupCategory entity,
			ApplicationContext ctx, SubGroupCategoryDao subGroupCategoryDao) throws Exception {
		
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 5:
				ruleInstance = new CheckIfSubGroupCategoryIsCurrentlyInUseRules(ctx);
				break;
			case 10:
//				ruleInstance = new PreventStandardSubGroupCategoryDeletionRules(ctx);
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
