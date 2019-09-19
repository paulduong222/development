package gov.nwcg.isuite.core.rules.datatransfer.getfile;

import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;

import org.springframework.context.ApplicationContext;

public class AbstractGetEnterpriseFileRule extends AbstractRule {
	protected DataTransferVo dataTransferVo;
	protected Boolean fromServlet=false;
	protected LoggingInterceptor logger=null;
	
	public AbstractGetEnterpriseFileRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(GetEnterpriseFileRuleFactory.ObjectTypeEnum.DATA_TRANSFER_VO.name()))
			dataTransferVo = (DataTransferVo)object;
		if(objectName.equals(GetEnterpriseFileRuleFactory.ObjectTypeEnum.LOGGER.name()))
			logger = (LoggingInterceptor)object;
	}
	
	
}
