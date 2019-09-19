package gov.nwcg.isuite.core.rules.datatransfer.exportdata;

import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractExportDataRule extends AbstractRule {
	protected DataTransferVo vo;
	
	public AbstractExportDataRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ExportDataRuleFactory.ObjectTypeEnum.DATA_TRANSFER_VO.name()))
			vo = (DataTransferVo)object;
	}
	
	
}
