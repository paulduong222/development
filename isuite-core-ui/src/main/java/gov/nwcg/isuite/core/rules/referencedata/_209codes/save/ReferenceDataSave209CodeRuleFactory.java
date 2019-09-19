package gov.nwcg.isuite.core.rules.referencedata._209codes.save;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.persistence.Sit209Dao;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSave209CodeRuleFactory {

	public enum ObjectTypeEnum {
		SIT209_VO,
		SIT209_ENTITY,
		SIT209_DAO
	}

	public enum RuleEnum {
		VALIDATE_DATA(5)
		,CHECK_FOR_DUPLICATE_209_CODE(10)
		,PREVENT_STANDARD_209_CODE_CHANGE(20)
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
			Sit209Vo vo,
			Sit209 entity,
			Sit209Dao dao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckForDuplicate209CodeRules(ctx);
				break;
			case 20:
//				ruleInstance = new PreventStandard209CodeChangeRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.SIT209_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.SIT209_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.SIT209_DAO.name());
		}

		return ruleInstance;
	}
}
