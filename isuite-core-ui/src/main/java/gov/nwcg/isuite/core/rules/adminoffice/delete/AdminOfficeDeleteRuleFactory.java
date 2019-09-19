package gov.nwcg.isuite.core.rules.adminoffice.delete;

import gov.nwcg.isuite.core.persistence.AdminOfficeDao;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class AdminOfficeDeleteRuleFactory {

	public enum ObjectTypeEnum {
		ADMIN_OFFICE_VO
		,ADMIN_OFFICE_DAO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_IS_STANDARD(3)
		,CHECK_NOT_USED_IN_INVOICE(5)		
		,PROMPT_DELETE_ADMIN_OFFICE(6)
		,REMOVE_FROM_AGREEMENTS(10)
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
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx
									,AdminOfficeVo vo, AdminOfficeDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 3:
				ruleInstance = new CheckDeleteStandardRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckNotUsedInInvoiceRules(ctx);
				break;
			case 6:
				ruleInstance = new PromptDeleteAdminOfficeRules(ctx);
				break;
			case 10:
				ruleInstance = new RemoveFromAgreementsRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.ADMIN_OFFICE_VO.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.ADMIN_OFFICE_DAO.name());
		}
		
		return ruleInstance;
	}
	

	
}
