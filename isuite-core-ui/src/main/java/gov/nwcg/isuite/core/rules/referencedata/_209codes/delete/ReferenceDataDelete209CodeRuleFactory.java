package gov.nwcg.isuite.core.rules.referencedata._209codes.delete;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.persistence.Sit209Dao;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataDelete209CodeRuleFactory {

	public enum ObjectTypeEnum {
		_209_CODE_VO,
		_209_CODE_ENTITY,
		_209_CODE_DAO
	}

	public enum RuleEnum {
		CHECK_209_CODE_CURRENTLY_IN_USE(0),
		PREVENT_STANDARD_209_CODE_DELETION(5)
		;

		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}

	}

	public static IRule getInstance(RuleEnum rule, Sit209Vo vo, Sit209 entity,
			ApplicationContext ctx, Sit209Dao sit209Dao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckIf209CodeIsCurrentlyInUseRules(ctx);
				break;
			case 5:
				//ruleInstance = new PreventStandard209CodeDeletionRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum._209_CODE_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum._209_CODE_ENTITY.name());
			ruleInstance.setObject(sit209Dao, ObjectTypeEnum._209_CODE_DAO.name());
		}

		return ruleInstance;
	}
}
