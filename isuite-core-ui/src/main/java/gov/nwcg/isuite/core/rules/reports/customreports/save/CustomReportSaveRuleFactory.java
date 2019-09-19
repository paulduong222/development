package gov.nwcg.isuite.core.rules.reports.customreports.save;

import gov.nwcg.isuite.core.persistence.CustomReportDao;
import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CustomReportSaveRuleFactory {

	public enum ObjectTypeEnum {
		CUSTOM_REPORT_FILTER,
		USER_VO,
		CUSTOM_REPORT_DAO
		}
	
	public enum RuleEnum {
		VALIDATE_DATA_RULES(10)
		,VALIDATE_OWNER_SAVE_RULE(20)
		,CHECK_FOR_DUPLICATE_NAME(30)
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
	 * @param userVo
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, 
			                        ApplicationContext ctx, 
			                        CustomReportVo vo,
			                        UserVo userVo,
			                        CustomReportDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()){
			case 10:
				ruleInstance=new ValidateDataRules(ctx, rule.name());
				break;
			case 20:
				ruleInstance=new ValidateOwnerSaveRules(ctx, rule.name());
				break;
			case 30:
				ruleInstance=new CheckDuplicateReportTitle(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.CUSTOM_REPORT_FILTER.name());
			ruleInstance.setObject(userVo, ObjectTypeEnum.USER_VO.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.CUSTOM_REPORT_DAO.name());
		}
		
		return ruleInstance;
	}
	

	
}
