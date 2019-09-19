package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

/*
 * Rules to determine if contractor 'MILEAGE' time posting is a duplicate
 * of another 'MILEAGE' time posting.
 */
public class ContractorPrimaryMileageDuplicateRules extends AbstractContractorRule implements IRule {
	public static final String _RULE_NAME="CONTRACTOR_PRIMARY_MILEAGE_DUPLICATE";

	public ContractorPrimaryMileageDuplicateRules(ApplicationContext ctx){
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

			
			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				/*
				 * Check result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
			
			}else{

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
			}
			
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
		 * Rule is:
		 *   Prompt the user for course of action when the
		 *   following apply:
		 *   
		 *   1. Time posting is a primary time posting
		 *   2. The Unit of Measure is MILEAGE
		 *   3. The time posting dates are duplicates
		 *      of existing 'MILEAGE' time postings for the resource
		 */
		if((postType.equals("PRIMARY") || postType.equals("BOTH")) 
				&& getPrimaryUnitOfMeasure().equals("MILEAGE")){
			
			Date startDate=vo.getPostStartDate();
			Date stopDate=startDate;
			
			if(null != startDate && null != stopDate) {
				/*
				 * Query for any duplicate Primary MILEAGE posting dates.
				 * Note: Pass in "PRIMARY" as part of the query, 
				 *       we want to exclude all others (specials and guarantees).
				 */
				tpqFilter=new TimePostQueryFilterImpl();
				tpqFilter.setAssignmentTimePostId(vo.getId());
				tpqFilter.setIncidentResourceId(incidentResourceEntity.getId());
				tpqFilter.setInvoiceOnly(false);
				tpqFilter.setStartDate(startDate);
				tpqFilter.setStopDate(stopDate);
				tpqFilter.setUnitOfMeasure("MILEAGE");
				tpqFilter.setPostType("PRIMARY");
				//tpqFilter.setAccountingCode(super.getPrimaryAccountingCode());

				// Check for duplicate invoiced time postings
				tpqFilter.setInvoiceOnly(true);
				Collection<String> dupInvoicedDates = 
					tpDao.getDuplicateDailyPosts(tpqFilter);
				
				if(CollectionUtility.hasValue(dupInvoicedDates)){
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.time"
													,"validationerror"
													,"error.0137"
													,null));	
					return _FAIL;
				}

				// Check for duplicates
				tpqFilter.setInvoiceOnly(false);
				Collection<String> dupDates = 
					tpDao.getDuplicateDailyPosts(tpqFilter);

				/*
				 * Check query results.
				 */
				if(CollectionUtility.hasValue(dupDates)){
					Collection<Long> dupIds = tpDao.getDuplicateContractorPostsIds(tpqFilter);
					
					String duplicateDates = toDateDelimitedString(dupDates);
					
					/*
					 * Build Prompt on how to handle the duplicates.
					 */
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
					coaVo.setPromptVo(new PromptVo("text.time","action.0140a",new String[]{"Primary 'MILEAGE'", "[ "+duplicateDates+" ]"},PromptVo._YES | PromptVo._NO | PromptVo._CANCEL));
					coaVo.getPromptVo().setYesLabel("POST ANYWAY");
					coaVo.getPromptVo().setNoLabel("OVERWRITE");
					coaVo.getPromptVo().setCancelLabel("CANCEL POST");
					coaVo.getPromptVo().setButtonWidth(150);
					coaVo.setStoredObject(LongUtility.convertToObjects(dupIds));
					dialogueVo.setCourseOfActionVo(coaVo);
								
					return _FAIL;
				
				}
			}
		}

		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {
		
		dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);

		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			// continue;
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			
			// continue as additional action needed (need overwrite records)
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		
		}else if(getPromptResult(dialogueVo) == PromptVo._CANCEL){
			// cannot continue if prompt was cancel post
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.time", "text.abortProcess" , new String[]{"post time"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
					
			dialogueVo.setCourseOfActionVo(coaVo);
					
			return _FAIL;
		}

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
