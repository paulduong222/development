package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

import org.springframework.context.ApplicationContext;

/*
 * Rules to determine if both (primary/special) time posting is acceptable according
 * to the 'Both' rules.
 */
public class ContractorBothValidationRules extends AbstractContractorRule implements IRule {
	public static final String _RULE_NAME="CONTRACTOR_BOTH_VALIDATION";

	public ContractorBothValidationRules(ApplicationContext ctx){
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
	
		/*
		 * When posting 'BOTH' Primary/Special.
		 * The rules are:
		 *      3. Special Start Date must be same as Primary Start Date
		 *      4. Special Daily cannot exceed 1 day when Primary uom is not DAILY
		 *      5. Special Stop Date must be same as Primary Stop Date when uom's are DAILY
		 *      
		 *      1. Primary Hourly cannot overlap midnight. This one not a rule.
		 *      
		 */
		
		if(super.postType.equals("BOTH")){
			
			//if(runCheckPrimaryHourly(dialogueVo)==_FAIL)
			//	return _FAIL;
	
			if(runCheckSpecialHourly(dialogueVo)==_FAIL)
				return _FAIL;
	
			if(runCheckSpecialStartDate(dialogueVo)==_FAIL)
				return _FAIL;
			
			if(runCheckSpecialDailyDays(dialogueVo)==_FAIL)
				return _FAIL;
	
			if(runCheckSpecialStopDate(dialogueVo)==_FAIL)
				return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * Verify that if the Primary Time posting is Hourly, 
	 * that it does not overlap midnight.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int runCheckPrimaryHourly(DialogueVo dialogueVo) throws Exception {
		
		if(getPrimaryUnitOfMeasure().equals("HOURLY")){
			Date startTime = 
				DateUtil.addMilitaryTimeToDate(
						vo.getPostStartDate()
						,vo.getPostStartTime());
			Date stopTime = 
				DateUtil.addMilitaryTimeToDate(
						vo.getPostStartDate()
						, vo.getPostStopTime());

			if(DateUtil.hasValue(startTime) && DateUtil.hasValue(stopTime)){
				if(stopTime.before(startTime)){
					/*
					 * This is a validation error, 
					 * this scenario is not allowed.
					 * 
					 * Build Error message 
					 */
					String errorMsg="Cannot save 'BOTH' time posting.  " +
									"When Primary Rate time posting is HOURLY, the start and stop times cannot overlap midnight." ;
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.time"
													, "error.800000"
													, new String[]{errorMsg}
													, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
							
					dialogueVo.setCourseOfActionVo(coaVo);
								
					return _FAIL;
				}
					
				
			}
		}

		return _OK;
	}
	
	/**
	 * Verify that if the Special Time posting is Hourly, 
	 * that it does not overlap midnight.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int runCheckSpecialHourly(DialogueVo dialogueVo) throws Exception {
		
		if(getSpecialUnitOfMeasure().equals("HOURLY")){
			Date startTime = 
				DateUtil.addMilitaryTimeToDate(
						specialVo.getPostStartDate()
						,specialVo.getPostStartTime());
			Date stopTime = 
				DateUtil.addMilitaryTimeToDate(
						specialVo.getPostStartDate()
						, specialVo.getPostStopTime());

			if(DateUtil.hasValue(startTime) && DateUtil.hasValue(stopTime)){
				if(stopTime.before(startTime)){
					/*
					 * This is a validation error, 
					 * this scenario is not allowed.
					 * 
					 * Build Error message 
					 */
					/* 6/22/2013
					String errorMsg="Cannot save 'BOTH' time posting.  " +
									"When Special Rate time posting is HOURLY, the start and stop times cannot overlap midnight." ;
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.time"
													, "error.800000"
													, new String[]{errorMsg}
													, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
							
					dialogueVo.setCourseOfActionVo(coaVo);
								
					return _FAIL;
					*/
				}
					
				
			}
		}

		return _OK;
	}

	/**
	 * Verify that if the Special Time posting start date
	 * is the same as the Primary start date.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int runCheckSpecialStartDate(DialogueVo dialogueVo) throws Exception {

		Date specialStartDate=specialVo.getPostStartDate();
		Date primaryStartDate=vo.getPostStartDate();
		
		if(!DateUtil.isSameDate(specialStartDate, primaryStartDate)){
					
			/*
			 * This is a validation error, 
			 * this scenario is not allowed.
			 * 
			 * Build Error message 
			 */
			String errorMsg="Cannot save 'BOTH' time posting.  " +
							"The Special Rate time posting start date must be the same as the Primary time posting start date.";
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.time"
											, "error.800000"
											, new String[]{errorMsg}
											, MessageTypeEnum.CRITICAL));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
							
			dialogueVo.setCourseOfActionVo(coaVo);
								
			return _FAIL;
					
		}
		
		return _OK;
	}
	
	/**
	 * Verify that if the Special Time posting is Daily
	 * and the Primary Time Posting is not Daily, 
	 * that the Special Time posting does not exceed 1 day.
	 * To check this, verify the special start date and stop dates match.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int runCheckSpecialDailyDays(DialogueVo dialogueVo) throws Exception {

		if(getSpecialUnitOfMeasure().equals("DAILY") &&
				!getPrimaryUnitOfMeasure().equals("DAILY")){
		
			Date startDate=specialVo.getPostStartDate();
			Date stopDate=specialVo.getPostStopDate();

			if(DateUtil.hasValue(startDate) && DateUtil.hasValue(stopDate)) {
				
				if(!DateUtil.isSameDate(startDate, stopDate)){
					
					/*
					 * This is a validation error, 
					 * this scenario is not allowed.
					 * 
					 * Build Error message 
					 */
					String errorMsg="Cannot save 'BOTH' time posting.  " +
									"When Special Rate time posting is DAILY and the Primary time posting is not DAILY, the Special Rate time posting cannot exceed 1 day." ;
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.time"
													, "error.800000"
													, new String[]{errorMsg}
													, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
							
					dialogueVo.setCourseOfActionVo(coaVo);
								
					return _FAIL;
					
				}
				
			}
			
		}
		
		return _OK;
	}

	/**
	 * Verify that if the Special Time posting uom is 'DAILY' that the stop date
	 * is the same as the Primary stop date when the primary uom is also 'DAILY'.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int runCheckSpecialStopDate(DialogueVo dialogueVo) throws Exception {

		if(getSpecialUnitOfMeasure().equals("DAILY") &&
				getPrimaryUnitOfMeasure().equals("DAILY")){
			
			Date specialStopDate=specialVo.getPostStopDate();
			Date primaryStopDate=vo.getPostStopDate();

			if(!DateUtil.isSameDate(specialStopDate, primaryStopDate)){
						
				/*
				 * This is a validation error, 
				 * this scenario is not allowed.
				 * 
				 * Build Error message 
				 */
				String errorMsg="Cannot save 'BOTH' time posting.  " +
								"When both the Special Rate and Primary time postings have unit of measures of 'DAILY', the Special Rate time posting stop date must be the same as the Primary time posting stop date.";
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.time"
												, "error.800000"
												, new String[]{errorMsg}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
								
				dialogueVo.setCourseOfActionVo(coaVo);
									
				return _FAIL;
						
			}
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
