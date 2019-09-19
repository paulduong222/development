package gov.nwcg.isuite.core.rules.referencedata.agencygroups.delete;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.persistence.AgencyGroupDao;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteAgencyGroupRule extends AbstractRule {
	
	AgencyGroupVo agencyGroupVo;
	AgencyGroup agencyGroupEntity;
	AgencyGroupDao agencyGroupDao;
	
	public AbstractDeleteAgencyGroupRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataDeleteAgencyGroupRuleFactory.ObjectTypeEnum.AGENCY_GROUP_VO.name()))
			agencyGroupVo = (AgencyGroupVo)object;
		if(objectName.equals(ReferenceDataDeleteAgencyGroupRuleFactory.ObjectTypeEnum.AGENCY_GROUP_ENTITY.name()))
			agencyGroupEntity = (AgencyGroup)object;
		if(objectName.equals(ReferenceDataDeleteAgencyGroupRuleFactory.ObjectTypeEnum.AGENCY_GROUP_DAO.name()))
			agencyGroupDao = (AgencyGroupDao)object;
	}
	
	
}
