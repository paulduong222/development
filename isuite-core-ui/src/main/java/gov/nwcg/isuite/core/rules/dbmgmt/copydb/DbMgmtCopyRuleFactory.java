package gov.nwcg.isuite.core.rules.dbmgmt.copydb;

import gov.nwcg.isuite.core.rules.dbmgmt.copydb.CheckAdditionalBackupFolderRules;
import gov.nwcg.isuite.core.rules.dbmgmt.copydb.CheckMaxDBCountRules;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DbMgmtCopyRuleFactory {

	public enum ObjectTypeEnum {
		SOURCE_VO
		,TARGET_VO
	}
	
	public enum RuleEnum {
		VALIDATE_SOURCE_DATA(10)
		,VALIDATE_TARGET_DATA(20)
		,CHECK_DUPLICATE_DB_NAME(30)
		,CHECK_ADDITIONAL_BACKUP_FOLDER(35)
		,CHECK_MAX_DB_COUNT(40)
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
										,DbAvailVo sourceVo, DbAvailVo targetVo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateSourceDataRules(ctx);
				break;
			case 20:
				ruleInstance = new ValidateTargetDataRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckDuplicateNameRules(ctx);
				break;
			case 35:
				ruleInstance = new CheckAdditionalBackupFolderRules(ctx);
				break;
			case 40:
				ruleInstance = new CheckMaxDBCountRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(sourceVo, ObjectTypeEnum.SOURCE_VO.name());
			ruleInstance.setObject(targetVo, ObjectTypeEnum.TARGET_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
