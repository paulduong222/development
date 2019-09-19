package gov.nwcg.isuite.core.rules.referencedata.adrates.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.core.persistence.RateClassRateDao;
import gov.nwcg.isuite.core.vo.RateClassRateVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractDeleteADRateRule extends AbstractRule {
	
	RateClassRateVo rateClassRateVo;
	RateClassRate rateClassRateEntity;
	RateClassRateDao rateClassRateDao;
	
	public AbstractDeleteADRateRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataDeleteADRateRuleFactory.ObjectTypeEnum.RATE_CLASS_RATE_VO.name()))
			rateClassRateVo = (RateClassRateVo)object;
		if(objectName.equals(ReferenceDataDeleteADRateRuleFactory.ObjectTypeEnum.RATE_CLASS_RATE_ENTITY.name()))
			rateClassRateEntity = (RateClassRate)object;
		if(objectName.equals(ReferenceDataDeleteADRateRuleFactory.ObjectTypeEnum.RATE_CLASS_RATE_DAO.name()))
			rateClassRateDao = (RateClassRateDao)object;
	}
	
}
