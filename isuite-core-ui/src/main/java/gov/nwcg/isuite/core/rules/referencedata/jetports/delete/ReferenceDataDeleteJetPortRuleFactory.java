package gov.nwcg.isuite.core.rules.referencedata.jetports.delete;

import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.persistence.JetPortDao;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataDeleteJetPortRuleFactory {

	public enum ObjectTypeEnum {
		JET_PORT_VO,
		JET_PORT_ENTITY,
		JET_PORT_DAO
	}

	public enum RuleEnum {
		CHECK_IF_STANDARD_JET_PORT_CODE_IS_CURRENTLY_IN_USE(10)
		,CHECK_IF_NON_STANDARD_JET_PORT_CODE_IS_CURRENTLY_IN_USE(15)
		,PREVENT_STANDARD_JET_PORT_CODE_DELETION(20)
		;

		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}

	}

	public static IRule getInstance(RuleEnum rule, JetPortVo vo, JetPort entity,
			ApplicationContext ctx, JetPortDao jetPortDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new CheckIfStandardJetPortCodeIsCurrentlyInUseRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckIfNonStandardJetPortCodeIsCurrentlyInUseRules(ctx);
				break;
			case 20:
				//ruleInstance = new PreventStandardJetPortCodeDeletionRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.JET_PORT_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.JET_PORT_ENTITY.name());
			ruleInstance.setObject(jetPortDao, ObjectTypeEnum.JET_PORT_DAO.name());
		}

		return ruleInstance;
	}
}
