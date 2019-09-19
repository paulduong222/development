package gov.nwcg.isuite.core.rules.referencedata.itemcodes.delete;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataDeleteItemCodeRuleFactory {

	public enum ObjectTypeEnum {
		KIND_VO,
		KIND_ENTITY,
		KIND_DAO
	}

	public enum RuleEnum {
		CHECK_IF_STANDARD_ITEM_CODE_IS_CURRENTLY_IN_USE(0),
		CHECK_IF_NON_STANDARD_ITEM_CODE_IS_CURRENTLY_IN_USE(5),
		PREVENT_STANDARD_ITEM_CODE_DELETION(10)
		;

		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}

	}

	public static IRule getInstance(RuleEnum rule, KindVo vo, Kind entity,
			ApplicationContext ctx, KindDao kindDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckIfStandardItemCodeIsCurrentlyInUseRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckIfNonStandardItemCodeIsCurrentlyInUseRules(ctx);
				break;
			case 10:
//				ruleInstance = new PreventStandardItemCodeDeletionRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.KIND_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.KIND_ENTITY.name());
			ruleInstance.setObject(kindDao, ObjectTypeEnum.KIND_DAO.name());
		}

		return ruleInstance;
	}
}
