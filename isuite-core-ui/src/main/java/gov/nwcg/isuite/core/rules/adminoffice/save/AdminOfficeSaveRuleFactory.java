package gov.nwcg.isuite.core.rules.adminoffice.save;

import gov.nwcg.isuite.core.persistence.AdminOfficeDao;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class AdminOfficeSaveRuleFactory {

	public enum ObjectTypeEnum {
		ADMIN_OFFICE_VO
		,ADMIN_OFFICE_DAO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,VALIDATE_DATA(3)
		,CHECK_IS_STANDARD(4)
		,CHECK_UNIQUE_NAME(5)
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
										,AdminOfficeVo vo, AdminOfficeDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 3:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 4:
				ruleInstance = new CheckEditStandardRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckUniqueNameRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.ADMIN_OFFICE_VO.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.ADMIN_OFFICE_DAO.name());
		}
		
		return ruleInstance;
	}
	

	
}
