package gov.nwcg.isuite.core.rules.datatransfer.exportdata;

import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ExportDataRuleFactory {

	public enum ObjectTypeEnum {
		DATA_TRANSFER_VO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		,CHECK_PASSWORD_FORMAT(15)
		,CHECK_SINGLE_INCIDENT(20)
		,CHECK_WRITE_FOLDER(30)
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx,DataTransferVo vo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckFormatRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckSingleIncidentRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckWriteFolderRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.DATA_TRANSFER_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
