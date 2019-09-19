package gov.nwcg.isuite.core.rules.financialexport.export;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.FinancialExport;
import gov.nwcg.isuite.core.persistence.FinancialExportDao;
import gov.nwcg.isuite.core.vo.FinancialExportVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractFinancialExportExportRule extends AbstractRule {
	protected FinancialExportVo financialExportVo=null;
	protected FinancialExportDao feDao=null;
	protected FinancialExport entity = null;
	
	public AbstractFinancialExportExportRule(ApplicationContext ctx, String rname){
		context = ctx;
		ruleName = rname;
	}
	
	public void setObject(Object object, String objectName){
		if(objectName.equals(FinancialExportExportRuleFactory.ObjectTypeEnum.FINANCIAL_EXPORT_VO.name()))
			financialExportVo = (FinancialExportVo)object;
		if(objectName.equals(FinancialExportExportRuleFactory.ObjectTypeEnum.FINANCIAL_EXPORT_DAO.name()))
			feDao = (FinancialExportDao)object; 
		if(objectName.equals(FinancialExportExportRuleFactory.ObjectTypeEnum.FINANCIAL_EXPORT_ENTITY.name()))
			entity = (FinancialExport)object; 
	}

}
