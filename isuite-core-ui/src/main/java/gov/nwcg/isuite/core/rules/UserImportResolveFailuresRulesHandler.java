package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.UserImportFailure;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserImportFailureDao;
import gov.nwcg.isuite.core.rules.user.resolvefailedimports.ResolveFailedImportRulesFactory;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class UserImportResolveFailuresRulesHandler extends AbstractRuleHandler {
	
	public UserImportResolveFailuresRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(DialogueVo dialogueVo, UserVo validUserVo, UserImportFailureDao uifDao, 
			UserDao userDao, UserImportFailure uifEntity) throws Exception {
		
		try{
			for(ResolveFailedImportRulesFactory.RuleEnum ruleEnum : ResolveFailedImportRulesFactory.RuleEnum.values()){
				IRule rule = ResolveFailedImportRulesFactory.getInstance(
						ruleEnum, context, validUserVo, uifDao, userDao, uifEntity);
				
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

	public void executeProcessedActions(DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ResolveFailedImportRulesFactory.RuleEnum ruleEnum : ResolveFailedImportRulesFactory.RuleEnum.values()){
				IRule rule = ResolveFailedImportRulesFactory.getInstance(ruleEnum, context, null, null, null, null);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
