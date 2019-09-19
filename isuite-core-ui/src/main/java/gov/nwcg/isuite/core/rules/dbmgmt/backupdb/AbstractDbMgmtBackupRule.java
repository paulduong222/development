package gov.nwcg.isuite.core.rules.dbmgmt.backupdb;

import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDbMgmtBackupRule extends AbstractRule {
	protected DbAvailVo vo;
	protected String destFilename;
	protected String altDestination;
	
	public AbstractDbMgmtBackupRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DbMgmtBackupRuleFactory.ObjectTypeEnum.DBAVAIL_VO.name()))
			vo = (DbAvailVo)object;
		if(objectName.equals(DbMgmtBackupRuleFactory.ObjectTypeEnum.DEST_FILENAME.name()))
			destFilename = (String)object;
		if(objectName.equals(DbMgmtBackupRuleFactory.ObjectTypeEnum.ALT_PATH.name()))
			altDestination = (String)object;
	}
	
	
}
