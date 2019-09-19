package gov.nwcg.isuite.core.rules.iap.deleteoperationalperiod;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.core.domain.impl.IncidentShiftImpl;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;


public class CheckIfOperationalPeriodIsCurrentlyInUseRules extends AbstractOperationalPeriodDeleteRule implements IRule {

	public static final String _RULE_NAME=OperationalPeriodDeleteRuleFactory.RuleEnum.CHECK_IF_OPERATIONAL_PERIOD_IS_CURRENTLY_IN_USE.name();
	
	public CheckIfOperationalPeriodIsCurrentlyInUseRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
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
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * Cannot delete an operational period if it is in use in
		 * IAP or Cost.
		 */
		
		IncidentShift incidentShift = super.incidentShiftDao.getById(super.incidentShiftVo.getId(), IncidentShiftImpl.class);
		
		if(CollectionUtility.hasValue(incidentShift.getCostGroups())) {
			//TODO add check for iap form table when schema/mapping completed
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.operationalPeriod",
							"info.generic",
							new String[]{"The Operational Period is in use and cannot be deleted."},
							MessageTypeEnum.CRITICAL));
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.	setCourseOfActionVo(courseOfActionVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}
	
}
