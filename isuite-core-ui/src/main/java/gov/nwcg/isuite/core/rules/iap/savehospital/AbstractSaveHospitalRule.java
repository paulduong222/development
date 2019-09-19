package gov.nwcg.isuite.core.rules.iap.savehospital;

import gov.nwcg.isuite.core.domain.IapHospital;
import gov.nwcg.isuite.core.vo.IapHospitalVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveHospitalRule extends AbstractRule {
	protected IapHospitalVo iapHospitalVo;
	protected IapHospital entity;
	
	public AbstractSaveHospitalRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveHospitalRuleFactory.ObjectTypeEnum.IAP_HOSPITAL_VO.name()))
			iapHospitalVo = (IapHospitalVo)object;
		if(objectName.equals(SaveHospitalRuleFactory.ObjectTypeEnum.IAP_HOSPITAL_ENTITY.name()))
			entity = (IapHospital)object;
	}
}

