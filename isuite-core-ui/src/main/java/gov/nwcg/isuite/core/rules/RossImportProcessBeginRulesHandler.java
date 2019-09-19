package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.rossimportbegin.RossImportProcessBeginRuleFactory;
import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossImportVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class RossImportProcessBeginRulesHandler extends AbstractRuleHandler {

	public RossImportProcessBeginRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(RossXmlFileVo vo, UserVo userVo, Collection<Long> reimportReqIds,DialogueVo dialogueVo) throws Exception {
		
		try{

			RossImportVo rossImportVo = new RossImportVo();

			// store in roa4
			dialogueVo.setResultObjectAlternate4(rossImportVo);
			
			for(RossImportProcessBeginRuleFactory.RuleEnum ruleEnum : RossImportProcessBeginRuleFactory.RuleEnum.values()){
				IRule rule = RossImportProcessBeginRuleFactory.getInstance(ruleEnum, context, vo,userVo, reimportReqIds,dialogueVo);
				
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
