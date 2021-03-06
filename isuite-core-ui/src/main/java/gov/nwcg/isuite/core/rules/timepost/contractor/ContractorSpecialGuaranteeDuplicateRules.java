package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

/*
 * Rules to determine if special time posting is a duplicate
 * of a guarantee time posting for the same date and uom.
 */
public class ContractorSpecialGuaranteeDuplicateRules extends AbstractContractorRule implements IRule {
	public static final String _RULE_NAME="CONTRACTOR_SPECIAL_GUARANTEE_DUPLICATE";

	public ContractorSpecialGuaranteeDuplicateRules(ApplicationContext ctx){
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
		 * The rule is:
		 *   When posting a special time posting,
		 *   we need to make sure there are no other guarantee time postings 
		 *   on the same date(s) and same unit of measure
		 *   and same accounting code.
		 *   
		 */
		if(postType.equals("SPECIAL") || postType.equals("BOTH")){ 
			
			/*
			 * Determine if the time posting has a date that
			 * is a duplicate of an existing guarantee time posting
			 * with the same unit of measure.
			 */
			Date startDate=specialVo.getPostStartDate();
			Date stopDate=specialVo.getPostStopDate();

			if(null == stopDate)
				stopDate=startDate;
		
			/*
			 * Query for any duplicate time post dates against guarantee dates/uom/acctcode.
			 * Note: Pass in "GUARANTEE" as part of the query, 
			 *       we want to exclude all others (primarys and specials).
			 */
			tpqFilter=new TimePostQueryFilterImpl();
			tpqFilter.setIncidentResourceId(incidentResourceEntity.getId());
			tpqFilter.setInvoiceOnly(false);
			tpqFilter.setStartDate(startDate);
			tpqFilter.setStopDate(stopDate);
			tpqFilter.setUnitOfMeasure(super.getSpecialUnitOfMeasure()); 
			tpqFilter.setAccountingCode(super.getSpecialAccountingCode());
			tpqFilter.setPostType("GUARANTEE");
			
			Collection<String> dupDates = 
				tpDao.getDuplicateContractorPosts(tpqFilter);
			
			/*
			 * Check query results.
			 */
			if(CollectionUtility.hasValue(dupDates)){
				
				String duplicateDates = toDateDelimitedString(dupDates);
				
				/*
				 * Build Error message 
				 */
				String errorMsg="Cannot save time posting.  " +
								"The followng dates (" + duplicateDates + ") are duplicates of an existing guaranteed time posting " + 
								"with the same date(s) and unit of measure.";
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
