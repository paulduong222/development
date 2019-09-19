package gov.nwcg.isuite.core.rules.iap.saveattach;

import gov.nwcg.isuite.core.vo.IapAttachmentVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveAttachmentRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_ATTACHMENT_VO
		,PDF_BYTE_ARRAY
	}
	
	public enum RuleEnum {
		VALIDATE_ATTACHMENT_DATA(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapAttachmentVo vo,byte[] pdfByteArray) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateAttachDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_ATTACHMENT_VO.name());
			ruleInstance.setObject(pdfByteArray, ObjectTypeEnum.PDF_BYTE_ARRAY.name());
		}
		
		return ruleInstance;
	}

}

