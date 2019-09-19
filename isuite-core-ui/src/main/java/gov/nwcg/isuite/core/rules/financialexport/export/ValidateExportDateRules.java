package gov.nwcg.isuite.core.rules.financialexport.export;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import org.springframework.context.ApplicationContext;

public class ValidateExportDateRules extends AbstractFinancialExportExportRule
		implements IRule {
	public static final String _RULE_NAME=FinancialExportExportRuleFactory.RuleEnum.VALIDATE_EXPORT_DATE.name();


	public ValidateExportDateRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;


			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;


			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));

		}catch(Exception e){
			throw new ServiceException(e);
		}

		return _OK;
	}
	/**
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*A.	12.0001 - Financial Export
				Use Case ID: 12.0001	Goal: Generate a Financial Export
				Actors: Data Steward	Category: Enterprise and Site
		
				Business Requirements
		
				Financial Export export
				
				2. If the user attempts to create a Financial Export file for 
				   an Incident/Incident Group on the same date on which it was 
				   already created, the system must not allow the file to be 
				   created and must display a message. [0259]
		*/
		if(null!=financialExportVo){
			if(null!=financialExportVo.getLastExportDate()){
				if(DateUtil.isSameDate(financialExportVo.getLastExportDate(), financialExportVo.getExportDate())){    			
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.financialExport","info.0259",
								new String[]{DateUtil.toDateString(financialExportVo.getExportDate()),DateUtil.toDateString(DateUtil.addDays(financialExportVo.getExportDate(),1))},
								MessageTypeEnum.CRITICAL));
					courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
				
					dialogueVo.setCourseOfActionVo(courseOfActionVo);
				
					return _FAIL;
				}		
			}
		}
		return _OK;
		
	}
}

