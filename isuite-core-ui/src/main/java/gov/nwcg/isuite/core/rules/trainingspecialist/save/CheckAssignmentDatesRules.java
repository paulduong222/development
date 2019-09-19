package gov.nwcg.isuite.core.rules.trainingspecialist.save;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.persistence.ResourceTrainingDao;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.ResourceTrainingVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class CheckAssignmentDatesRules extends AbstractSaveResourceTrainingRule implements IRule {
	public static final String _RULE_NAME=ResourceTrainingSaveRuleFactory.RuleEnum.ASSIGNMENT_DATES.name();
	
	public CheckAssignmentDatesRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
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
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		// If a user has multiple trainee assignments, the system must check to see if
		// the assignment dates overlap.  
		
		if(null != super.resourceTrainingVo) {
			if(null != super.resourceTrainingVo.getIncidentResourceVo()) {
				
				ResourceTrainingDao rtDao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");
				Collection<ResourceTrainingVo> vos = new ArrayList<ResourceTrainingVo>();
				
				vos = rtDao.getResourceTrainings(super.resourceTrainingVo.getIncidentResourceVo().getId());
				
				Date resVoStartDate = DateTransferVo.getDate(super.resourceTrainingVo.getStartDateTransferVo());
				Date resVoEndDate = DateTransferVo.getDate(super.resourceTrainingVo.getEndDateTransferVo());
				Boolean err = false;
				
				for(ResourceTrainingVo vo : vos){
					if(super.resourceTrainingVo.getId() != vo.getId()){
						if(DateTransferVo.hasDateString(vo.getEndDateTransferVo())) {
							Date endDate = DateTransferVo.getDate(vo.getEndDateTransferVo());
							Date startDate = DateTransferVo.getDate(vo.getStartDateTransferVo());
						
							if(null != resVoStartDate) {
								if(resVoStartDate.after(startDate) && resVoStartDate.before(endDate)){
									err = true;
								}
							}
							
							if(null != resVoEndDate) {
								if(resVoEndDate.after(startDate) && resVoEndDate.before(endDate)){
									err = true;
								}
							}
							
							if(null != resVoStartDate && null != resVoEndDate) {
								if(resVoStartDate.before(startDate) && resVoEndDate.after(endDate)) {
									err  = true;
								}
							}
						}
					}
				}
				
				if(err) {
					String errMsg = "The assignment dates of this assignment overlap the assignment dates of another assignment.";
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.traineeAssignment"
													, "info.generic"
													, new String[]{errMsg}
													, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
					dialogueVo.setCourseOfActionVo(coaVo);
					return _FAIL;
				}
			}
		}
		
		return _OK;
	}
	

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}

	

}
