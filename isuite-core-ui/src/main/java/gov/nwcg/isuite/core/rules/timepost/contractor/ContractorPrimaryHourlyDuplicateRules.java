package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
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

public class ContractorPrimaryHourlyDuplicateRules extends AbstractContractorRule implements IRule {
	public static final String _RULE_NAME="CONTRACTOR_PRIMARY_HOURLY_DUPLICATE";


	public ContractorPrimaryHourlyDuplicateRules(ApplicationContext ctx){
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
		 *   2. The Unit of Measure is HOURLY
		 *   3. The time posting dates/times are duplicates
		 *      of existing 'HOURLY' time postings for the resource
		 */
		if(isExclusivePrimaryContractorPost() 
				&& getPrimaryUnitOfMeasure().equals("HOURLY")){
			
			Date startTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
			Date stopTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStopTime());
			
			if(null != startTime && null != stopTime) {
				/*
				 * Query for any duplicate Primary Hourly posting dates.
				 * Note: Pass in "PRIMARY" as part of the query, 
				 *       we want to exclude all others (specials and guarantees).
				 */
				tpqFilter=new TimePostQueryFilterImpl();
				tpqFilter.setAssignmentTimePostId(vo.getId());
				tpqFilter.setIncidentResourceId(incidentResourceEntity.getId());
				tpqFilter.setInvoiceOnly(false);
				tpqFilter.setIncludeTime(true);
				tpqFilter.setStartDate(startTime);
				tpqFilter.setStopDate(stopTime);
				tpqFilter.setUnitOfMeasure("HOURLY");
				tpqFilter.setPostType("PRIMARY");
				
				Collection<String> dupDates = new ArrayList<String>();
				Collection<Long> dupIds= new ArrayList<Long>();
				Collection<Long> firstDupIds= new ArrayList<Long>();
				Collection<Long> secondDupIds= new ArrayList<Long>();
				String dupTimes="";
				
				if(stopTime.before(startTime)){
					/*
					 * The time posting is split into 2
					 * Query for any duplicate Primary Hourly posting dates for the first date
					 */
					
					// Check first First Date
					stopTime=DateUtil.addMilitaryTimeToDate(stopTime, "2359");
					tpqFilter.setStopDate(stopTime);

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
					
					dupDates = tpDao.getDuplicateContractorPosts(tpqFilter);
					firstDupIds= tpDao.getDuplicateContractorPostsIds(tpqFilter);
					
					for(Long lngId : firstDupIds){
						AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimeImpl.class);
						tpDao.flushAndEvict(atp);
						
						String stoptime="";
						
						if(DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM).equals("23:59")){
							stoptime="2400";
						}else
							stoptime=DateUtil.toDateString(atp.getPostStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM);
						
						if(StringUtility.hasValue(dupTimes)){
							dupTimes=dupTimes+"\n"+DateUtil.toDateString(atp.getPostStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)
								+ " to " + stoptime;
						}else{
							dupTimes="Time postings:\n"+DateUtil.toDateString(atp.getPostStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)
								+ " to " + stoptime;
						}
						dupIds.add(lngId);
					}

					// Check Second Date
					startTime = 
						DateUtil.addMilitaryTimeToDate(
								vo.getPostStartDate()
								,"0000");
					stopTime = 
						DateUtil.addMilitaryTimeToDate(
								vo.getPostStartDate()
								, vo.getPostStopTime());
					
					startTime=DateUtil.addDays(startTime, 1);
					stopTime=DateUtil.addDays(stopTime, 1);
					tpqFilter.setStartDate(startTime);
					tpqFilter.setStopDate(stopTime);

					// if editing a posting, and this id not in ids, need to add it as the first id so the remaining 
					// timeposts that are supposed to be getting updated will actually get deleted since this time posting
					// is replacing them
					if(LongUtility.hasValue(super.vo.getId())){
						Collection<Long> newids = new ArrayList<Long>();
						newids.add(vo.getId());
						for(Long id : firstDupIds){
							Boolean bAdd=false;
							for(Long nid : newids){
								if(nid.compareTo(id)!=0)
									bAdd=true;
							}
							if(bAdd==true)
								newids.add(id);
						}
						firstDupIds=newids;
					}
					
					// Check for duplicate invoiced time postings
					tpqFilter.setInvoiceOnly(true);
					dupInvoicedDates = 
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
					dupDates = tpDao.getDuplicateContractorPosts(tpqFilter);

					secondDupIds= tpDao.getDuplicateContractorPostsIds(tpqFilter);
					
					for(Long lngId : secondDupIds){
						AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimeImpl.class);
						tpDao.flushAndEvict(atp);
						if(StringUtility.hasValue(dupTimes)){
							dupTimes=dupTimes+"\n"+DateUtil.toDateString(atp.getPostStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)
								+ " to " + DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM);
						}else{
							dupTimes="Time postings:\n"+DateUtil.toDateString(atp.getPostStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)
								+ " to " + DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM);
						}
						dupIds.add(lngId);
					}
					
				}else{
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
					dupDates = tpDao.getDuplicateContractorPosts(tpqFilter);

					dupIds= tpDao.getDuplicateContractorPostsIds(tpqFilter);
					
					dupTimes="";
					
					for(Long lngId : dupIds){
						AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimeImpl.class);
						tpDao.flushAndEvict(atp);
						if(StringUtility.hasValue(dupTimes)){
							dupTimes=dupTimes+"\n"+DateUtil.toDateString(atp.getPostStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)
								+ " to " + DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM);
						}else{
							dupTimes="Time postings:\n"+DateUtil.toDateString(atp.getPostStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)
								+ " to " + DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM);
						}
					}

					// if editing a posting, and this id not in ids, need to add it as the first id so the remaining 
					// timeposts that are supposed to be getting updated will actually get deleted since this time posting
					// is replacing them
					if(LongUtility.hasValue(super.vo.getId())){
						Collection<Long> newids = new ArrayList<Long>();
						newids.add(vo.getId());
						for(Long id : dupIds){
							Boolean bAdd=false;
							for(Long nid : newids){
								if(nid.compareTo(id)!=0)
									bAdd=true;
							}
							if(bAdd==true)
								newids.add(id);
						}
						dupIds=newids;
					}
					
				}

					
				
				/*
				 * Check query results.
				 */
				if(CollectionUtility.hasValue(dupDates)){
					String duplicateDates = toDateDelimitedString(dupDates);
					
					/*
					 * Build Prompt on how to handle the duplicates.
					 */
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
					coaVo.setPromptVo(new PromptVo("text.time","action.0140b2",new String[]{"Primary 'HOURLY'", "[ "+duplicateDates+" ]", dupTimes},PromptVo._YES | PromptVo._NO | PromptVo._CANCEL));
					coaVo.getPromptVo().setYesLabel("POST ANYWAY");
					coaVo.getPromptVo().setNoLabel("OVERWRITE");
					coaVo.getPromptVo().setCancelLabel("CANCEL POST");
					coaVo.getPromptVo().setButtonWidth(150);
					coaVo.setStoredObject(dupIds);
					coaVo.setStoredObject2(firstDupIds);
					coaVo.setStoredObject3(secondDupIds);
					
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
			
		// add to processed
		dialogueVo.getCourseOfActionVo().setIsComplete(true);
		dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// continue;
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			
			// continue as additional action needed (need overwrite records)
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else{
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.time", "text.abortProcess" , new String[]{"post time"}, MessageTypeEnum.INFO));
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
