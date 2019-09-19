package gov.nwcg.isuite.core.rules.adminoffice.save;

import gov.nwcg.isuite.core.persistence.AdminOfficeDao;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractAdminOfficeSaveRule extends AbstractRule {
	protected AdminOfficeVo vo;
	protected AdminOfficeDao dao;
	
	public AbstractAdminOfficeSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(AdminOfficeSaveRuleFactory.ObjectTypeEnum.ADMIN_OFFICE_VO.name()))
			vo = (AdminOfficeVo)object;
		if(objectName.equals(AdminOfficeSaveRuleFactory.ObjectTypeEnum.ADMIN_OFFICE_DAO.name()))
			dao = (AdminOfficeDao)object;
	}
	
	
}
