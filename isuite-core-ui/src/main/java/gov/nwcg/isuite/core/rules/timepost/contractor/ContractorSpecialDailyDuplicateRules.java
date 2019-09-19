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
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

/*
 * Rules to determine if contractor time posting is a duplicate.
 */
public class ContractorSpecialDailyDuplicateRules extends AbstractContractorRule implements IRule {
	public static final String _RULE_NAME="CONTRACTOR_SPECIAL_DAILY_DUPLICATE";


	public ContractorSpecialDailyDuplicateRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{

			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;
			
					
			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				
				dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
				
				/*
				 * Check prompt result
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
				 * Rule check passed 
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
		 * Determine if time posting is Daily and 
		 * if any of the days are duplicates.
		 */
		if((postType.equals("SPECIAL") || postType.equals("BOTH")) 
				&& getSpecialUnitOfMeasure().equals("DAILY")){

			Date startDate=specialVo.getPostStartDate();
			Date stopDate=specialVo.getPostStopDate();

			if(null != startDate && null != stopDate) {
				/*
				 * Dan 1/16/2013 - no longer needed since sql was adjusted
				long diffDays=DateUtil.diffDays(startDate, stopDate);
				if(diffDays < 2)
					stopDate = startDate;
				*/
				
				tpqFilter=new TimePostQueryFilterImpl();
				if(LongUtility.hasValue(specialVo.getId()))
					tpqFilter.setAssignmentTimePostId(specialVo.getId());
				tpqFilter.setIncidentResourceId(incidentResourceEntity.getId());
				tpqFilter.setInvoiceOnly(false);
				tpqFilter.setStartDate(startDate);
				tpqFilter.setStopDate(stopDate);
				tpqFilter.setUnitOfMeasure("DAILY");
				tpqFilter.setPostType("SPECIAL");

				// Check for duplicate invoiced time postings
				tpqFilter.setInvoiceOnly(true);
				Collection<String> dupInvoicedDates = 
					tpDao.getDuplicateContractorPosts(tpqFilter);
				
				if(CollectionUtility.hasValue(dupInvoicedDates)){
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.time"
													,"validationerror"
													,"error.0137"
													,null));	
					return _FAIL;
				}
				
				// Check for duplicate time postings
				tpqFilter.setInvoiceOnly(false);
				Collection<String> dupDates = 
						tpDao.getDuplicateContractorPosts(tpqFilter);

				
				if(null != dupDates && dupDates.size() > 0){

					Collection<Long> dupIds = 
						tpDao.getDuplicateContractorPostsIds(tpqFilter);
					
					String duplicateDates = toDateDelimitedString(dupDates);

					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
					coaVo.setPromptVo(new PromptVo("text.time","action.0140a",new String[]{"Special","[ "+duplicateDates+" ]"},PromptVo._YES | PromptVo._NO | PromptVo._CANCEL));
					coaVo.getPromptVo().setYesLabel("POST ANYWAY");
					coaVo.getPromptVo().setNoLabel("OVERWRITE");
					coaVo.getPromptVo().setCancelLabel("CANCEL POST");
					coaVo.getPromptVo().setButtonWidth(150);
					coaVo.setStoredObject(LongUtility.convertToObjects(dupIds));
					coaVo.setStoredObject1(duplicateDates);
					dialogueVo.setCourseOfActionVo(coaVo);
									
					return _FAIL;
				}
			}
		}
		
		return _OK;
	}
	
	/**
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {


		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// add to processed
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

	private Collection<Date> getPostDates() throws Exception{
		Collection<Date> postDates = new ArrayList<Date>();
		
		if( (null != vo.getRefContractorRateVo()) 
				&& (StringUtility.hasValue(vo.getRefContractorRateVo().getUnitOfMeasure())) 
				&& (vo.getRefContractorRateVo().getUnitOfMeasure().equals("DAILY") ) ){
			
			Date startDate=vo.getPostStartDate();
			Date stopDate=vo.getPostStopDate();
			
			if(null != startDate && null != stopDate) {
				long diffDays=DateUtil.diffDays(startDate, stopDate);
				if(diffDays > 1){
					for(int i=0;i<diffDays;i++){
						if(i>0)
							postDates.add(DateUtil.addDaysToDate(startDate, i));
					}
				}else{
					postDates.add(startDate);
				}
			}
		}
		
		return postDates;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
