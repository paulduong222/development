package gov.nwcg.isuite.core.rules.referencedata.categorygroups.save;

import gov.nwcg.isuite.core.domain.GroupCategory;
import gov.nwcg.isuite.core.persistence.GroupCategoryDao;
import gov.nwcg.isuite.core.vo.GroupCategoryVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSaveCategoryGroupRuleFactory {

	public enum ObjectTypeEnum {
		GROUP_CATEGORY_VO,
		GROUP_CATEGORY_ENTITY,
		GROUP_CATEGORY_DAO
	}

	public enum RuleEnum {
		VALIDATE_DATA(5)
		,CHECK_FOR_DUPLICATE_CATEGORY_GROUP(10),
		PREVENT_STANDARD_CATEGORY_GROUP_CHANGE(15)
		;

		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}

	}

	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, GroupCategoryVo vo, GroupCategory entity,
			GroupCategoryDao groupCategoryDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckForDuplicateCategoryGroupRules(ctx);
				break;
			case 15:
//				ruleInstance = new PreventStandardCategoryGroupChangeRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.GROUP_CATEGORY_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.GROUP_CATEGORY_ENTITY.name());
			ruleInstance.setObject(groupCategoryDao, ObjectTypeEnum.GROUP_CATEGORY_DAO.name());
		}

		return ruleInstance;
	}
}
