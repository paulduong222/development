package gov.nwcg.isuite.core.rules.dbmgmt.savedb;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DbMgmtSaveRuleFactory {

	public enum ObjectTypeEnum {
		DBAVAIL_VO
		,DBAVAIL_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		,CHECK_DUPLICATE_DB_NAME(20)
		,CHECK_MAX_DB_COUNT(30)
		,CHECK_ADDITIONAL_BACKUP_FOLDER(35)
		,CREATE_DEFAULT_USER(40)
		,CHECK_DATASOURCE_USAGE_RULES(50)
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, DbAvail entity
										,DbAvailVo vo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckDuplicateNameRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckMaxDBCountRules(ctx);
				break;
			case 35:
				ruleInstance = new CheckAdditionalBackupFolderRules(ctx);
				break;
			case 40:
				ruleInstance = new AddCreateUserRules(ctx);
				break;
			case 50:
				ruleInstance = new CheckDatasourceUsageRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.DBAVAIL_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.DBAVAIL_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
