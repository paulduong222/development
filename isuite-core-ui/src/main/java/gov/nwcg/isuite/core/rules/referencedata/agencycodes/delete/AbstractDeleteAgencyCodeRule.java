package gov.nwcg.isuite.core.rules.referencedata.agencycodes.delete;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.rules.referencedata.agencycodes.save.ReferenceDataSaveAgencyCodeRuleFactory;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteAgencyCodeRule extends AbstractRule {
	
	AgencyVo agencyVo;
	Agency agencyEntity;
	AgencyDao agencyDao;
	
	public AbstractDeleteAgencyCodeRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataSaveAgencyCodeRuleFactory.ObjectTypeEnum.AGENCY_VO.name()))
			agencyVo = (AgencyVo)object;
		if(objectName.equals(ReferenceDataSaveAgencyCodeRuleFactory.ObjectTypeEnum.AGENCY_ENTITY.name()))
			agencyEntity = (Agency)object;
		if(objectName.equals(ReferenceDataSaveAgencyCodeRuleFactory.ObjectTypeEnum.AGENCY_DAO.name()))
			agencyDao = (AgencyDao)object;
	}
	
	
}
