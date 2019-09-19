package gov.nwcg.isuite.core.rules.dbmgmt.deletedb;

import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DbMgmtDeleteRuleFactory {

	public enum ObjectTypeEnum {
		DBAVAIL_VO
		,DEST_FILENAME
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		,CHECK_SAME_SESSION_DB(20)
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
										,DbAvailVo vo, String destFilename) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckSameSessionDbRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.DBAVAIL_VO.name());
			ruleInstance.setObject(destFilename, ObjectTypeEnum.DEST_FILENAME.name());
		}
		
		return ruleInstance;
	}
	

	
}
