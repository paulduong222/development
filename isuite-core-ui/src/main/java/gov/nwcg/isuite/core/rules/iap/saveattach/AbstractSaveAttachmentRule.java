package gov.nwcg.isuite.core.rules.iap.saveattach;

import gov.nwcg.isuite.core.vo.IapAttachmentVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveAttachmentRule extends AbstractRule {
	protected IapAttachmentVo iapAttachmentVo;
	protected byte[] pdfByteArray;
	
	public AbstractSaveAttachmentRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveAttachmentRuleFactory.ObjectTypeEnum.IAP_ATTACHMENT_VO.name()))
			iapAttachmentVo = (IapAttachmentVo)object;
		if(objectName.equals(SaveAttachmentRuleFactory.ObjectTypeEnum.PDF_BYTE_ARRAY.name()))
			pdfByteArray = (byte[])object;
	}
}

