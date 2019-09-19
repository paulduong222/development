package gov.nwcg.isuite.core.rules.dbmgmt.savedb;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDbMgmtSaveRule extends AbstractRule {
	protected DbAvailVo vo;
	protected DbAvail entity;
	
	public AbstractDbMgmtSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DbMgmtSaveRuleFactory.ObjectTypeEnum.DBAVAIL_VO.name()))
			vo = (DbAvailVo)object;
		if(objectName.equals(DbMgmtSaveRuleFactory.ObjectTypeEnum.DBAVAIL_ENTITY.name()))
			entity = (DbAvail)object;
	}
	
	
}
