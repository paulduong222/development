package gov.nwcg.isuite.core.rules.referencedata.unitids.delete;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteUnitIdRule extends AbstractRule {
	
	OrganizationVo organizationVo;
	Organization organizationEntity;
	OrganizationDao organizationDao;
	
	public AbstractDeleteUnitIdRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataDeleteUnitIdRuleFactory.ObjectTypeEnum.ORGANIZATION_VO.name()))
			organizationVo = (OrganizationVo)object;
		if(objectName.equals(ReferenceDataDeleteUnitIdRuleFactory.ObjectTypeEnum.ORGANIZATION_ENTITY.name()))
			organizationEntity = (Organization)object;
		if(objectName.equals(ReferenceDataDeleteUnitIdRuleFactory.ObjectTypeEnum.ORGANIZATION_DAO.name()))
			organizationDao = (OrganizationDao)object;
	}
	
	
}
