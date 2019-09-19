package gov.nwcg.isuite.core.rules.dbmgmt.restoredb;

import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDbMgmtRestoreRule extends AbstractRule {
	protected String backupFilename;
	protected String targetDbName;
	protected String pwd;
	
	public AbstractDbMgmtRestoreRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DbMgmtRestoreRuleFactory.ObjectTypeEnum.BACKUP_FILENAME.name()))
			backupFilename = (String)object;
		if(objectName.equals(DbMgmtRestoreRuleFactory.ObjectTypeEnum.TARGET_DBNAME.name()))
			targetDbName = (String)object;
		if(objectName.equals(DbMgmtRestoreRuleFactory.ObjectTypeEnum.PWD.name()))
			pwd = (String)object;
	}
	
	
}
