package gov.nwcg.isuite.core.rules.financial.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractTimeInvoiceDeleteRule extends AbstractRule {
	
	protected TimeInvoice timeInvoice;
	protected TimeInvoiceDao timeInvoiceDao;
	
	public AbstractTimeInvoiceDeleteRule(ApplicationContext ctx){
		context=ctx;
	}
	
	public AbstractTimeInvoiceDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(TimeInvoiceDeleteRuleFactory.ObjectTypeEnum.TIME_INVOICE_ENTITY.name()))
			timeInvoice = (TimeInvoice)object;
		if(objectName.equals(TimeInvoiceDeleteRuleFactory.ObjectTypeEnum.TIME_INVOICE_DAO.name()))
			timeInvoiceDao = (TimeInvoiceDao)object;
	}

}
