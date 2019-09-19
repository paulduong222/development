package gov.nwcg.isuite.core.rules.referencedata.agencygroups.save;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.persistence.AgencyGroupDao;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveAgencyGroupRule extends AbstractRule {
	
	AgencyGroupVo agencyGroupVo;
	AgencyGroup agencyGroupEntity;
	AgencyGroupDao agencyGroupDao;
	
	public AbstractSaveAgencyGroupRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataSaveAgencyGroupRuleFactory.ObjectTypeEnum.AGENCY_GROUP_VO.name()))
			agencyGroupVo = (AgencyGroupVo)object;
		if(objectName.equals(ReferenceDataSaveAgencyGroupRuleFactory.ObjectTypeEnum.AGENCY_GROUP_ENTITY.name()))
			agencyGroupEntity = (AgencyGroup)object;
		if(objectName.equals(ReferenceDataSaveAgencyGroupRuleFactory.ObjectTypeEnum.AGENCY_GROUP_DAO.name()))
			agencyGroupDao = (AgencyGroupDao)object;
	}
	
	
}
