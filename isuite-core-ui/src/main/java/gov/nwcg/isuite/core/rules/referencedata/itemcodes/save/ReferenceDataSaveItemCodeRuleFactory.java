package gov.nwcg.isuite.core.rules.referencedata.itemcodes.save;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSaveItemCodeRuleFactory {

	public enum ObjectTypeEnum {
		KIND_VO,
		KIND_ENTITY,
		KIND_DAO
	}

	public enum RuleEnum {
		VALIDATE_DATA(5),
		CHECK_FOR_DUPLICATE_ITEM_CODE(10),
		PREVENT_STANDARD_ITEM_CODE_AND_ITEM_NAME_CHANGE(15)
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
			KindVo kindVo,
			Kind kindEntity,
			KindDao kindDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckForDuplicateItemCodeRules(ctx);
				break;
			case 15:
//				ruleInstance = new PreventStandardItemCodeAndItemNameChangeRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(kindVo, ObjectTypeEnum.KIND_VO.name());
			ruleInstance.setObject(kindEntity, ObjectTypeEnum.KIND_ENTITY.name());
			ruleInstance.setObject(kindDao, ObjectTypeEnum.KIND_DAO.name());
		}

		return ruleInstance;
	}
}
