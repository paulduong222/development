package gov.nwcg.isuite.core.rules.dbmgmt.copydb;

import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDbMgmtCopyRule extends AbstractRule {
	protected DbAvailVo sourceVo;
	protected DbAvailVo targetVo;
	
	public AbstractDbMgmtCopyRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DbMgmtCopyRuleFactory.ObjectTypeEnum.SOURCE_VO.name()))
			sourceVo = (DbAvailVo)object;
		if(objectName.equals(DbMgmtCopyRuleFactory.ObjectTypeEnum.TARGET_VO.name()))
			targetVo = (DbAvailVo)object;
	}
	
	
}
