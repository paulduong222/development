package gov.nwcg.isuite.core.rules.dbmgmt.backupdb;

import gov.nwcg.isuite.core.rules.dbmgmt.backupdb.CheckAdditionalBackupFolderRules;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DbMgmtBackupRuleFactory {

	public enum ObjectTypeEnum {
		DBAVAIL_VO
		,DEST_FILENAME
		,ALT_PATH
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		,CHECK_ADDITIONAL_BACKUP_FOLDER(20)
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
										,DbAvailVo vo, String destFilename, String altDest) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckAdditionalBackupFolderRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.DBAVAIL_VO.name());
			ruleInstance.setObject(destFilename, ObjectTypeEnum.DEST_FILENAME.name());
			ruleInstance.setObject(altDest, ObjectTypeEnum.ALT_PATH.name());
		}
		
		return ruleInstance;
	}
	

	
}
