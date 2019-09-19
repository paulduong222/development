package gov.nwcg.isuite.core.rules.dbmgmt.deletedb;

import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDbMgmtDeleteRule extends AbstractRule {
	protected DbAvailVo vo;
	protected String destFilename;
	
	public AbstractDbMgmtDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DbMgmtDeleteRuleFactory.ObjectTypeEnum.DBAVAIL_VO.name()))
			vo = (DbAvailVo)object;
		if(objectName.equals(DbMgmtDeleteRuleFactory.ObjectTypeEnum.DEST_FILENAME.name()))
			destFilename = (String)object;
	}
	
	
}
