package gov.nwcg.isuite.core.rules.iap.deleteairambulance;

import gov.nwcg.isuite.core.vo.IapMedicalAidVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteAirAmbulanceRule extends AbstractRule {
	protected IapMedicalAidVo iapMedicalAidVo;
	
	public AbstractDeleteAirAmbulanceRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteAirAmbulanceRuleFactory.ObjectTypeEnum.IAP_MEDICAL_AID_VO.name()))
			iapMedicalAidVo = (IapMedicalAidVo)object;
	}
}

