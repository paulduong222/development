package gov.nwcg.isuite.core.rules.iap.deletehospital;

import gov.nwcg.isuite.core.vo.IapHospitalVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteHospitalRule extends AbstractRule {
	protected IapHospitalVo iapHospitalVo;
	
	public AbstractDeleteHospitalRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteHospitalRuleFactory.ObjectTypeEnum.IAP_HOSPITAL_VO.name()))
			iapHospitalVo = (IapHospitalVo)object;
	}
}

