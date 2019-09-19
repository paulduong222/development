package gov.nwcg.isuite.core.rules.incident.saveincident;

import java.util.Date;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckDuplicateNbrYearRules extends AbstractIncidentSaveRule implements IRule{
	public static final String _RULE_NAME=SaveIncidentRuleFactory.RuleEnum.CHECK_DUPLICATE_NBR_YEAR.name();

	public CheckDuplicateNbrYearRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
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
		 * B.R. 3.0001
		 * 
		 *  4.	The system must use the Incident Number and the four digit Year 
		 *      defined in the Incident Start Date to identify a unique Incident. 
		 *      The system must allow a user to add duplicate Incident Numbers to 
		 *      the e-ISuite System, as long as there are different years associated 
		 *      with the Incident Numbers. 
		 */
		
		//if ( !LongUtility.hasValue(vo.getId())) {

		Date incStartDate=null;
		if(DateTransferVo.hasDateString(vo.getIncStartDateTransferVo())){
			incStartDate=DateTransferVo.getDate(vo.getIncStartDateTransferVo());
		}
			Incident duplicateIncident = 
					incidentDao.getByIncNbrAndIncidentYear(vo, DateUtil.getYearAsInteger(incStartDate));

			if(duplicateIncident != null) {

				if(!LongUtility.hasValue(vo.getId()) || (duplicateIncident.getId().compareTo(vo.getId()) != 0) ){
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.incident"
													, ErrorEnum._0046_DUPLICATE_INCIDENT_FOR_YEAR.getErrorName()
													, null
													, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
					dialogueVo.setCourseOfActionVo(coaVo);
					
					return _FAIL;
				}
				
			}
		//}
	
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
