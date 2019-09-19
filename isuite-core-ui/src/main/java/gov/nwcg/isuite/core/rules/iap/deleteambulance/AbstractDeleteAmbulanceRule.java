package gov.nwcg.isuite.core.rules.iap.deleteambulance;

import gov.nwcg.isuite.core.vo.IapMedicalAidVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteAmbulanceRule extends AbstractRule {
	protected IapMedicalAidVo iapMedicalAidVo;
	
	public AbstractDeleteAmbulanceRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteAmbulanceRuleFactory.ObjectTypeEnum.IAP_MEDICAL_AID_VO.name()))
			iapMedicalAidVo = (IapMedicalAidVo)object;
	}
}

