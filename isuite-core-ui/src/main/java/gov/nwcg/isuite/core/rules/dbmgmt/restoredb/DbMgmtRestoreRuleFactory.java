package gov.nwcg.isuite.core.rules.dbmgmt.restoredb;

import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DbMgmtRestoreRuleFactory {

	public enum ObjectTypeEnum {
		BACKUP_FILENAME
		,TARGET_DBNAME
		,PWD
	}
	
	public enum RuleEnum {
		VALIDATE_BACKUPFILE(10)
		,VALIDATE_TARGET_DATA(12)
		,CHECK_MAX_DB_COUNT(15)
		,CHECK_UNIQUE_TARGETDB(20)
		,CHECK_PWD_AND_REVISION(30)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	/**
	 * @param rule
	 * @param ctx
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx
										,String filename, String targetDbName, String pwd) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				//ruleInstance = new ValidateBackupFileRules(ctx);
				break;
			case 12:
				ruleInstance = new ValidateTargetDataRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckMaxDBCountRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckUniqueTargetRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckPasswordAndRevisionRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(filename, ObjectTypeEnum.BACKUP_FILENAME.name());
			ruleInstance.setObject(targetDbName, ObjectTypeEnum.TARGET_DBNAME.name());
			ruleInstance.setObject(pwd, ObjectTypeEnum.PWD.name());
		}
		
		return ruleInstance;
	}
	
}
