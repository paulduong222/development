package gov.nwcg.isuite.core.rules.financialexport.export;

import gov.nwcg.isuite.core.domain.FinancialExport;
import gov.nwcg.isuite.core.persistence.FinancialExportDao;
import gov.nwcg.isuite.core.vo.FinancialExportVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class FinancialExportExportRuleFactory {
	public enum ObjectTypeEnum{
		FINANCIAL_EXPORT_VO
		,FINANCIAL_EXPORT_DAO
		,FINANCIAL_EXPORT_ENTITY
	}

	public enum RuleEnum{
		CHECK_PERMISSIONS(0)
		,VALIDATE_DATA(5)
		,VALIDATE_EXPORT_DATE(10)
		,CONFIRM_EXPORT(15);
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, FinancialExportVo vo, FinancialExport entity, FinancialExportDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new ValidateExportDateRules(ctx);
				break;
			case 15:
			//	ruleInstance = new ConfirmExportRules(ctx);
				break;
			
		}
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.FINANCIAL_EXPORT_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.FINANCIAL_EXPORT_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.FINANCIAL_EXPORT_DAO.name());
		}
		
		return ruleInstance;
	}
}

