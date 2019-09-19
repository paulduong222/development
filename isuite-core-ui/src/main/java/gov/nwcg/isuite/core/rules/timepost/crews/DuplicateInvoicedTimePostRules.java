package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class DuplicateInvoicedTimePostRules extends AbstractCrewRule implements IRule {
	public static final String _RULE_NAME="DUPLICATE_INVOICED_TIME_POST";

	public DuplicateInvoicedTimePostRules(ApplicationContext ctx){
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
		 * Determine if the new start and stop times overlap 
		 * an existing time posting time period for the crew
		 * subordinates the time post is being saved against.
		 */
		Collection<String> failedNames = new ArrayList<String>();
		
		for(AssignmentTime atEntity : super.entities){
			if(null != atEntity){
				WorkPeriod wp = atEntity.getAssignment().getWorkPeriods().iterator().next();
				Resource resource = wp.getIncidentResource().getResource();

				Long assignmentTimeId = atEntity.getId();

				// get last invoice date for this crew member
				Date lastInvoiceDate = tpDao.getLastInvoiceDate(assignmentTimeId);
				
				if(DateUtil.hasValue(lastInvoiceDate)){
					lastInvoiceDate=DateUtil.addMilitaryTimeToDate(lastInvoiceDate, "2359");
					Date postDate=DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(),"2359");

					if(DateUtil.isSameDate(lastInvoiceDate, postDate))
						failedNames.add(resource.getFirstName()+ " " + resource.getLastName());
					else{
						if(postDate.before(lastInvoiceDate))
							failedNames.add(resource.getFirstName()+ " " + resource.getLastName());
					}

					
				}

			}

		}

		if(CollectionUtility.hasValue(failedNames)){
			String msg="The posting date cannot be prior to or the same as a date that was included in an original invoice for the selected crew member(s) [";
			int i=0;
			for(String s : failedNames){
				if(i==0)
					msg=msg+s;
				else
					msg=","+msg+s;
				i++;
			}
			msg=msg+"].  Please enter another posting date.";
			
			dialogueVo.setCourseOfActionVo(
					super.buildErrorCoaVo("text.time"
										  ,"validationerror"
										  ,"error.800000"
										  , new String[]{msg}));	
			return _FAIL;
			
		}
		
		/*
		if(CollectionUtility.hasValue(failedNames)){
			String names=CollectionUtility.toCommaDelimitedString(failedNames);
			
			dialogueVo.setCourseOfActionVo(
					super.buildErrorCoaVo("text.time"
											,"validationerror"
											,"error.0137a"
											,new String[]{names}));	
								
			return _FAIL;
		}
		*/
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
