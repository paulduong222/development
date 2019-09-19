package gov.nwcg.isuite.core.rules.iap.savemasterfrequency;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.IapMasterFrequency;
import gov.nwcg.isuite.core.persistence.IapMasterFrequencyDao;
import gov.nwcg.isuite.core.vo.IapMasterFrequencyVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractMasterFrequencySaveRule extends AbstractRule {
	
	IapMasterFrequencyVo iapMasterFrequencyVo;
	IapMasterFrequency iapMasterFrequencyEntity;
	IapMasterFrequencyDao iapMasterFrequencyDao;
	
	public AbstractMasterFrequencySaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(MasterFrequencySaveRuleFactory.ObjectTypeEnum.MASTER_FREQUENCY_VO.name()))
			iapMasterFrequencyVo = (IapMasterFrequencyVo)object;
		if(objectName.equals(MasterFrequencySaveRuleFactory.ObjectTypeEnum.MASTER_FREQUENCY_ENTITY.name()))
			iapMasterFrequencyEntity = (IapMasterFrequency)object;
		if(objectName.equals(MasterFrequencySaveRuleFactory.ObjectTypeEnum.MASTER_FREQUENCY_DAO.name()))
			iapMasterFrequencyDao = (IapMasterFrequencyDao)object;
	}

}
