package gov.nwcg.isuite.core.rules.iap.saveambulance;

import gov.nwcg.isuite.core.domain.IapMedicalAid;
import gov.nwcg.isuite.core.vo.IapMedicalAidVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveAmbulanceRule extends AbstractRule {
	protected IapMedicalAidVo iapMedicalAidVo;
	protected IapMedicalAid entity;
	
	public AbstractSaveAmbulanceRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveAmbulanceRuleFactory.ObjectTypeEnum.IAP_MEDICAL_AID_VO.name()))
			iapMedicalAidVo = (IapMedicalAidVo)object;
		if(objectName.equals(SaveAmbulanceRuleFactory.ObjectTypeEnum.IAP_MEDICAL_AID_ENTITY.name()))
			entity = (IapMedicalAid)object;
	}
}

