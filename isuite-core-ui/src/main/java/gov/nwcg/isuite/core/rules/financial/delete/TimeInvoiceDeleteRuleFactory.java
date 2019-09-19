package gov.nwcg.isuite.core.rules.financial.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

public class TimeInvoiceDeleteRuleFactory {
	
	public enum ObjectTypeEnum {
		TIME_INVOICE_ENTITY,
		TIME_INVOICE_DAO
	}
	
	public enum RuleEnum {
		CHECK_IF_INCLUDED_IN_FINANCIAL_EXPORT(5)
		;
		
		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, TimeInvoice entity,  
			ApplicationContext ctx, TimeInvoiceDao timeInvoiceDao) throws Exception {
		
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
		case 5:
			ruleInstance = new CheckInvoiceIncludedInFinancialExportRules(ctx);
			break;
		}
		
		if(null != ruleInstance) {
			ruleInstance.setObject(entity, ObjectTypeEnum.TIME_INVOICE_ENTITY.name());
			ruleInstance.setObject(timeInvoiceDao, ObjectTypeEnum.TIME_INVOICE_DAO.name());
		}
		
		return ruleInstance;
	}

}
