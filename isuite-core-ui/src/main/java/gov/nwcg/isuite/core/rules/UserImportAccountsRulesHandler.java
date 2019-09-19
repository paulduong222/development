package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserImportFailureDao;
import gov.nwcg.isuite.core.rules.user.importusers.ImportUserAccountsRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.xml.XmlHandler;

import org.springframework.context.ApplicationContext;

public class UserImportAccountsRulesHandler extends AbstractRuleHandler {
	
	public UserImportAccountsRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(
			byte[] xmlByteArray, 
			String defaultPassword,
			String confirmDefaultPassword,
			DialogueVo dialogueVo,
			UserImportFailureDao uifDao,
			UserDao userDao) throws Exception {
		
		try{
			XmlHandler xmlHandler = new XmlHandler();
			for(ImportUserAccountsRuleFactory.RuleEnum ruleEnum : ImportUserAccountsRuleFactory.RuleEnum.values()){
				IRule rule = ImportUserAccountsRuleFactory.getInstance(
						ruleEnum, context, defaultPassword, confirmDefaultPassword,
						xmlByteArray,
						uifDao,
						xmlHandler);
				
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
			coaVo.setMessageVo(
					new MessageVo(
							"text.userAccounts", 
							"error.900000" , 
							new String[]{e.getMessage()}, 
							MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(
			byte[] xmlByteArray, 
			String defaultPassword,
			String confirmDefaultPassword,
			DialogueVo dialogueVo,
			UserImportFailureDao uifDao,
			UserDao userDao) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ImportUserAccountsRuleFactory.RuleEnum ruleEnum : ImportUserAccountsRuleFactory.RuleEnum.values()){
				IRule rule = ImportUserAccountsRuleFactory.getInstance(
						ruleEnum, 
						context,
						defaultPassword,
						confirmDefaultPassword,
						xmlByteArray,
						uifDao,
						null);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
