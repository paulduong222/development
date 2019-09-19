package gov.nwcg.isuite.core.rules.referencedata.jetports.save;

import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.persistence.JetPortDao;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSaveJetPortRuleFactory {

	public enum ObjectTypeEnum {
		JET_PORT_VO,
		JET_PORT_ENTITY,
		JET_PORT_DAO
	}

	public enum RuleEnum {
		VALIDATE_DATA(5)
		,CHECK_FOR_DUPLICATE_JET_PORT(10)
		,PREVENT_STANDARD_JET_PORT_CODE_CHANGE(20)
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
			JetPortVo jetPortVo,
			JetPort jetPortEntity,
			JetPortDao jetPortDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckForDuplicateJetPortRules(ctx);
				break;
			case 20:
//				ruleInstance = new PreventStandardJetPortCodeChangeRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(jetPortVo, ObjectTypeEnum.JET_PORT_VO.name());
			ruleInstance.setObject(jetPortEntity, ObjectTypeEnum.JET_PORT_ENTITY.name());
			ruleInstance.setObject(jetPortDao, ObjectTypeEnum.JET_PORT_DAO.name());
		}

		return ruleInstance;
	}
}
