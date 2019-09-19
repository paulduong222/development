package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.dbmgmt.restoredb.DbMgmtRestoreRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class DbManagementRestoreRulesHandler extends AbstractRuleHandler {
	
	public DbManagementRestoreRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param filename
	 * @param targetDbName
	 * @param pwd
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(String filename, String targetDbName, String pwd, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			for(DbMgmtRestoreRuleFactory.RuleEnum ruleEnum : DbMgmtRestoreRuleFactory.RuleEnum.values()){
				IRule rule = DbMgmtRestoreRuleFactory.getInstance(ruleEnum, context, filename, targetDbName, pwd);
				
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
			coaVo.setMessageVo(new MessageVo("text.dbmgmt", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * @param filename
	 * @param targetDbName
	 * @param pwd
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(String filename, String targetDbName, String pwd, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			
			for(DbMgmtRestoreRuleFactory.RuleEnum ruleEnum : DbMgmtRestoreRuleFactory.RuleEnum.values()){
				IRule rule = DbMgmtRestoreRuleFactory.getInstance(ruleEnum, context, filename, targetDbName, pwd);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
