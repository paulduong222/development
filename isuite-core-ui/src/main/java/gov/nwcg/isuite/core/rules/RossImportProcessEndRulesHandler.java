package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.rossimportend.RossImportProcessEndRuleFactory;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossImportVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class RossImportProcessEndRulesHandler extends AbstractRuleHandler {

	public RossImportProcessEndRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(RossImportVo vo, UserVo userVo, DialogueVo dialogueVo) throws Exception {
		
		try{

			RossImportVo rossImportVo = new RossImportVo();

			// store in roa4
			dialogueVo.setResultObjectAlternate4(rossImportVo);
			
			for(RossImportProcessEndRuleFactory.RuleEnum ruleEnum : RossImportProcessEndRuleFactory.RuleEnum.values()){
				IRule rule = RossImportProcessEndRuleFactory.getInstance(ruleEnum, context, vo,userVo,dialogueVo);
				
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
							"ROSS Import", 
							"error.900000" , 
							new String[]{e.getMessage()}, 
							MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

}
