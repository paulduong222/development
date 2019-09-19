package gov.nwcg.isuite.core.rules.contractor.save;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ContractorSaveRuleFactory {

	public enum ObjectTypeEnum {
		CONTRACTOR_VO
		,CONTRACTOR_ENTITY
		,CONTRACTOR_DAO
		,ORIGINAL_CONTRACTOR_VO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,VALIDATE_DATA(3)
		,CHECK_UNIQUE_NAME_INCIDENT(5)
		,CHECK_UNIQUE_NAME_WORK_AREA(10)
		,NAME_HISTORY(15)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	/**
	 * @param rule
	 * @param ctx
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx
										,ContractorVo vo, ContractorVo originalVo,Contractor entity, ContractorDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 3:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckUniqueNameIncidentRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckUniqueNameWorkAreaRules(ctx);
				break;
			case 15:
				ruleInstance = new ContractorNameHistoryRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.CONTRACTOR_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.CONTRACTOR_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.CONTRACTOR_DAO.name());
			ruleInstance.setObject(originalVo, ObjectTypeEnum.ORIGINAL_CONTRACTOR_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
