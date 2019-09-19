package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.RequestNumberUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class CheckRequestNumberFormatRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="CHECK_REQUEST_NUMBER_FORMAT";

	public CheckRequestNumberFormatRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try {

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
				
		} catch(Exception e){
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
		AssignmentVo avo = null;
		
		if(null != vo.getWorkPeriodVo()
				&&
				  null != vo.getWorkPeriodVo().getCurrentAssignmentVo()){
			avo=vo.getWorkPeriodVo().getCurrentAssignmentVo();
		}
		
		if(null != avo){
			
			/*
			 * Request Number validation
			 */
			if(null != avo.getRequestNumber()){
				
				if(avo.getRequestNumber().startsWith("-") || avo.getRequestNumber().startsWith(" -")){
					// must be A C E O S
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.incidentResources"
												  ,"validationerror"
												  ,"error.8004"
												  ,null));	
					return _FAIL;
				}
				
				RequestNumberUtil.formatRequestNumber(avo);
				
				String rqChar = RequestNumberUtil.parseRequestNumberChar(avo.getRequestNumber());
				
				/*
				 * if rqChar has a value, verify it is A C E O or S
				 */
				if(StringUtility.hasValue(rqChar)){
					if(rqChar.length() > 1 || !rqChar.toUpperCase().matches("[ACEOS]")){
						// must be A C E O S
						dialogueVo.setCourseOfActionVo(
								super.buildErrorCoaVo("text.incidentResources"
													  ,"validationerror"
													  ,"error.8004"
													  ,null));	
						return _FAIL;
					}
				}
				
				
				String rqValue = RequestNumberUtil.parseRequestNumberValue(avo.getRequestNumber());

				/*
				 * If rqValue has a value, verify it only contains
				 * numbers and/or .
				 */
				if(StringUtility.hasValue(rqValue)){
					if(!rqValue.matches("([0-9?\\.]*)")){
						dialogueVo.setCourseOfActionVo(
								super.buildErrorCoaVo("text.incidentResources"
													  ,"validationerror"
													  ,"error.8005"
													  ,null));	
						
						return _FAIL;
					}
				}
				
				/*
				 * verify we either have both or none at all
				 */
				if( (StringUtility.hasValue(rqChar) && !StringUtility.hasValue(rqValue))
						||
					(!StringUtility.hasValue(rqChar) && StringUtility.hasValue(rqValue) )){
				
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.incidentResources"
												  ,"validationerror"
												  ,"error.8005"
												  ,new String[]{""}));	
					return _FAIL;
				}
				
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
