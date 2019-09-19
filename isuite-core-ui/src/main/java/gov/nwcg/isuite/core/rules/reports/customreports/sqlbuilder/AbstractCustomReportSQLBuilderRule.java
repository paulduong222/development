package gov.nwcg.isuite.core.rules.reports.customreports.sqlbuilder;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractCustomReportSQLBuilderRule extends AbstractRule {
	protected CustomReportVo reportVo;
	
	public AbstractCustomReportSQLBuilderRule(ApplicationContext ctx, String rname){
		context = ctx;
		ruleName = rname;
	}
	
	public void setObject(Object object, String objectName){
		if(objectName.equals(CustomReportSQLBuilderRuleFactory.ObjectTypeEnum.CUSTOM_REPORT_VO.name()))
			reportVo = (CustomReportVo)object;
	}

}
