package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.rules.dbmgmt.savedb.DbMgmtSaveRuleFactory;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class DbManagementSaveRulesHandler extends AbstractRuleHandler {
	
	public DbManagementSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(DbAvail entity,DbAvailVo vo, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			for(DbMgmtSaveRuleFactory.RuleEnum ruleEnum : DbMgmtSaveRuleFactory.RuleEnum.values()){
				IRule rule = DbMgmtSaveRuleFactory.getInstance(ruleEnum, context, entity, vo);
				
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
			coaVo.setMessageVo(new MessageVo("text.database", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

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
	public void executeProcessedActions(DbAvail entity,DbAvailVo vo, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			
			for(DbMgmtSaveRuleFactory.RuleEnum ruleEnum : DbMgmtSaveRuleFactory.RuleEnum.values()){
				IRule rule = DbMgmtSaveRuleFactory.getInstance(ruleEnum, context, entity, vo);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
