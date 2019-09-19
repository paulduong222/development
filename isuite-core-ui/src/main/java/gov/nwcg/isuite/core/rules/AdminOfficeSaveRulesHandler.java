package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.persistence.AdminOfficeDao;
import gov.nwcg.isuite.core.rules.adminoffice.save.AdminOfficeSaveRuleFactory;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class AdminOfficeSaveRulesHandler extends AbstractRuleHandler {
	
	public AdminOfficeSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(AdminOfficeVo vo, DialogueVo dialogueVo) throws Exception {
		
		try{

			AdminOfficeDao dao = (AdminOfficeDao)context.getBean("adminOfficeDao");
			
			for(AdminOfficeSaveRuleFactory.RuleEnum ruleEnum : AdminOfficeSaveRuleFactory.RuleEnum.values()){
				IRule rule = AdminOfficeSaveRuleFactory.getInstance(ruleEnum, context, vo, dao);
				
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
			coaVo.setMessageVo(new MessageVo("text.adminOffice", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

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
	public void executeProcessedActions(AdminOfficeVo vo, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			AdminOfficeDao dao = (AdminOfficeDao)context.getBean("adminOfficeDao");
			
			for(AdminOfficeSaveRuleFactory.RuleEnum ruleEnum : AdminOfficeSaveRuleFactory.RuleEnum.values()){
				IRule rule = AdminOfficeSaveRuleFactory.getInstance(ruleEnum, context, vo, dao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
