package gov.nwcg.isuite.core.rules.datatransfer.getfile;

import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;

import org.springframework.context.ApplicationContext;

public class GetEnterpriseFileRuleFactory {

	public enum ObjectTypeEnum {
		DATA_TRANSFER_VO
		,LOGGER
	}
	
	public enum RuleEnum {
		GET_FILE_LIST(10)
		,GET_FILE(30)
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
										,DataTransferVo dataTransferVo
										, LoggingInterceptor logger) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new GetFileListRules(ctx);
				break;
			case 30:
				ruleInstance = new GetFileRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(dataTransferVo, ObjectTypeEnum.DATA_TRANSFER_VO.name());
			ruleInstance.setObject(logger, ObjectTypeEnum.LOGGER.name());
		}
		
		return ruleInstance;
	}
	

	
}
