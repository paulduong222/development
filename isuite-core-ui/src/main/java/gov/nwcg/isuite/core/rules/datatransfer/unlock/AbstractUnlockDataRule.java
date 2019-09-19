package gov.nwcg.isuite.core.rules.datatransfer.unlock;

import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractUnlockDataRule extends AbstractRule {
	protected DataTransferVo dataTransferVo;
	
	public AbstractUnlockDataRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(UnlockDataRuleFactory.ObjectTypeEnum.DATA_TRANSFER_VO.name()))
			dataTransferVo = (DataTransferVo)object;
	}
	
	
}
