package gov.nwcg.isuite.core.rules.adminoffice.delete;

import gov.nwcg.isuite.core.persistence.AdminOfficeDao;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractAdminOfficeDeleteRule extends AbstractRule {
	protected AdminOfficeVo vo;
	protected AdminOfficeDao dao;
	
	public AbstractAdminOfficeDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(AdminOfficeDeleteRuleFactory.ObjectTypeEnum.ADMIN_OFFICE_VO.name()))
			vo = (AdminOfficeVo)object;
		if(objectName.equals(AdminOfficeDeleteRuleFactory.ObjectTypeEnum.ADMIN_OFFICE_DAO.name()))
			dao = (AdminOfficeDao)object;
	}
	
	
}
