package gov.nwcg.isuite.core.rules.referencedata.categorygroups.delete;

import gov.nwcg.isuite.core.domain.GroupCategory;
import gov.nwcg.isuite.core.persistence.GroupCategoryDao;
import gov.nwcg.isuite.core.vo.GroupCategoryVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataDeleteCategoryGroupRuleFactory {

	public enum ObjectTypeEnum {
		GROUP_CATEGORY_VO,
		GROUP_CATEGORY_ENTITY,
		GROUP_CATEGORY_DAO
	}

	public enum RuleEnum {
		CHECK_IF_CATEGORY_GROUP_IS_CURRENTLY_IN_USE(0),
		PREVENT_STANDARD_CATEGORY_GROUP_DELETION(5)
		;

		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}

	}

	public static IRule getInstance(RuleEnum rule, GroupCategoryVo vo, GroupCategory entity,
			ApplicationContext ctx, GroupCategoryDao groupCategoryDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckIfCategoryGroupIsCurrentlyInUseRules(ctx);
				break;
			case 5:
				//ruleInstance = new PreventStandardCategoryGroupDeletionRules(ctx);
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
