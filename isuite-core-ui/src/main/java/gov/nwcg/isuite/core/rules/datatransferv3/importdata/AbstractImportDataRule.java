package gov.nwcg.isuite.core.rules.datatransferv3.importdata;

import gov.nwcg.isuite.core.domain.xmlv3.DataTransferXml;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractImportDataRule extends AbstractRule {
	protected DataTransferXml dataTransferXml;
	protected String filePassword;
	protected Boolean fromServlet=false;
	
	public AbstractImportDataRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ImportDataRuleFactory.ObjectTypeEnum.DATA_TRANSFER_XML.name()))
			dataTransferXml = (DataTransferXml)object;
		if(objectName.equals(ImportDataRuleFactory.ObjectTypeEnum.FILE_PASSWORD.name()))
			filePassword = (String)object;
		if(objectName.equals(ImportDataRuleFactory.ObjectTypeEnum.FROM_SERVLET.name()))
			fromServlet = (Boolean)object;
	}
	
	
}
