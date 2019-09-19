package gov.nwcg.isuite.core.rules.user.resolvefailedimports;

import gov.nwcg.isuite.core.domain.UserImportFailure;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserImportFailureDao;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractFailedImportsRule extends AbstractRule {
	
	protected UserVo validUserVo;
	protected UserImportFailureDao uifDao;
	protected UserDao userDao;
	protected UserImportFailure uifEntity;
	
	public AbstractFailedImportsRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ResolveFailedImportRulesFactory.ObjectTypeEnum.VALID_USER_VO.name()))
			this.validUserVo = (UserVo)object;
		if(objectName.equals(ResolveFailedImportRulesFactory.ObjectTypeEnum.UIF_DAO.name()))
			this.uifDao = (UserImportFailureDao)object;
		if(objectName.equals(ResolveFailedImportRulesFactory.ObjectTypeEnum.USER_DAO.name()))
			this.userDao = (UserDao)object;
		if(objectName.equals(ResolveFailedImportRulesFactory.ObjectTypeEnum.UIF_ENTITY.name()))
			this.uifEntity = (UserImportFailure)object;
	}
	
}
