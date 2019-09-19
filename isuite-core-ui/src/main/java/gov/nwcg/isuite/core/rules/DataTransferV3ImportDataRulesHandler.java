package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.xmlv3.DataTransferXml;
import gov.nwcg.isuite.core.rules.datatransferv3.importdata.ImportDataRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class DataTransferV3ImportDataRulesHandler extends AbstractRuleHandler {
	
	public DataTransferV3ImportDataRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(DataTransferXml dataTransferXml, String filePassword, Boolean fromServlet, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			if(fromServlet==true){
				return _OK;
			}
			
			for(ImportDataRuleFactory.RuleEnum ruleEnum : ImportDataRuleFactory.RuleEnum.values()){
				IRule rule = ImportDataRuleFactory.getInstance(ruleEnum, context, filePassword, dataTransferXml, fromServlet);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.dataTransfer", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(DataTransferXml dataTransferXml, String filePassword, Boolean fromServlet,DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			
			for(ImportDataRuleFactory.RuleEnum ruleEnum : ImportDataRuleFactory.RuleEnum.values()){
				IRule rule = ImportDataRuleFactory.getInstance(ruleEnum, context, filePassword,dataTransferXml,fromServlet);
			
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
