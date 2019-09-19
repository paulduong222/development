package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.rules.workarea.save.WorkAreaSaveRuleFactory;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class WorkAreaSaveRulesHandler extends AbstractRuleHandler {

	public WorkAreaSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(WorkAreaVo vo, WorkArea entity, DialogueVo dialogueVo) throws Exception {
		
		try{

			WorkAreaDao dao = (WorkAreaDao)context.getBean("workAreaDao");

			for(WorkAreaSaveRuleFactory.RuleEnum ruleEnum : WorkAreaSaveRuleFactory.RuleEnum.values()){
				IRule rule = WorkAreaSaveRuleFactory.getInstance(ruleEnum
																		, context
																		, vo
																		, entity
																		, dao);
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
			coaVo.setMessageVo(new MessageVo("text.time", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public int executeProcessedActions(WorkAreaVo vo, WorkArea entity, DialogueVo dialogueVo) throws Exception {

		try{
			
			WorkAreaDao dao = (WorkAreaDao)context.getBean("workAreaDao");

			for(WorkAreaSaveRuleFactory.RuleEnum ruleEnum : WorkAreaSaveRuleFactory.RuleEnum.values()){
				IRule rule = WorkAreaSaveRuleFactory.getInstance(ruleEnum
																		, context
																		, vo
																		, entity
																		, dao);
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.time", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
}
