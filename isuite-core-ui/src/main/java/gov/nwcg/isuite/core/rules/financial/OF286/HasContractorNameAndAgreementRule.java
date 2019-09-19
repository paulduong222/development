package gov.nwcg.isuite.core.rules.financial.OF286;

import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class HasContractorNameAndAgreementRule extends AbstractInvoiceGenerationRule implements IRule {

	public HasContractorNameAndAgreementRule(ApplicationContext ctx, String rname)
	{
		super(ctx, rname);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
					
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _FAIL) {
				return _FAIL;
			} 	
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(ruleName,true));
		
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
		Long nameId = executeHasName(irs);
		Long agId = executeHasAgreement(irs);
        
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		Boolean hasError=false;
        String resourceName = getResourceName(agId, irs);
        String requestNumber = getRequestNumber(agId, irs);
        if(!StringUtility.hasValue(requestNumber))
        	requestNumber="";
		
		if(nameId!=null && agId!=null) {
          hasError=true;
          //getNoNameOrAgreementMessage(dialogueVo, resourceName, (StringUtility.hasValue(requestNumber)?requestNumber:""));
          //return _FAIL;
          
        } else if(agId!=null) {
            hasError=true;
          //getNoAgreementMessage(dialogueVo, resourceName, (StringUtility.hasValue(requestNumber)?requestNumber:""));
          //return _FAIL;
        }
		
		if(hasError==true){
			String msg="A Contractor Name and an Agreement Number have not been defined for "+requestNumber+" " + resourceName + ". You must define a Contractor Name and an Agreement Number before generating the OF-286 Invoice.";
			ErrorObject error2 = 
				new ErrorObject("error.800000"
									,msg);
			errorObjects.add(error2);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ValidationError");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);

			coaVo.setErrorObjectVos(errorObjects);
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

  /**
   * Return Resource Id of Incident Resource w/o Contractor name
   * @param irs
   * @return
   */
	private Long executeHasName(Collection<IncidentResourceVo> irs) {
		Long value = null;
		for (IncidentResourceVo ir : irs) {
			for (AssignmentVo a : ir.getWorkPeriodVo().getAssignmentVos()) {
				if (a.getAssignmentTimeVo() != null 
						&& a.getAssignmentTimeVo().getContractorPaymentInfoVo() != null
						&& a.getAssignmentTimeVo().getContractorPaymentInfoVo().getContractorVo() != null) {

					String name = a.getAssignmentTimeVo().getContractorPaymentInfoVo().getContractorVo().getName();
					if (name == null || name == "") {
						value = ir.getResourceVo().getId();
						return value;
					}
				} else {
					value = ir.getResourceVo().getId();
					return value;
				}
			}
		}
		return value;
  	}
  
	private Long executeHasAgreement(Collection<IncidentResourceVo> irs) {
		Long value = null;
		for (IncidentResourceVo ir : irs) {
			for (AssignmentVo a : ir.getWorkPeriodVo().getAssignmentVos()) {
				if (a.getAssignmentTimeVo() != null && a.getAssignmentTimeVo().getContractorPaymentInfoVo() != null
						&& a.getAssignmentTimeVo().getContractorPaymentInfoVo().getContractorVo() != null) {

					String nbr = a.getAssignmentTimeVo().getContractorPaymentInfoVo().getContractorAgreementVo()
							.getAgreementNumber();
					if (nbr == null && nbr == "") {
						value = ir.getResourceVo().getId();
						return value;
					}
				} else {
					value = ir.getResourceVo().getId();
					return value;
				}
			}
		}
		return value;
	}

	private void getNoNameOrAgreementMessage(DialogueVo dialogueVo, String resourceName, String requestNumber) {
		MessageVo messageVo = new MessageVo();
		messageVo.setMessageType(MessageTypeEnum.CRITICAL);
		messageVo.setMessageProperty("action.0153");
		messageVo.setParameters(new String[] { requestNumber, resourceName });
		messageVo.setTitleProperty("text.timeReports");

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName(ruleName);
		coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		coa.setIsDialogueEnding(true);
		coa.setMessageVo(messageVo);
		dialogueVo.setCourseOfActionVo(coa);
	}

	private void getNoAgreementMessage(DialogueVo dialogueVo, String resourceName, String requestNumber) {
		MessageVo messageVo = new MessageVo();
		messageVo.setMessageType(MessageTypeEnum.CRITICAL);
		messageVo.setMessageProperty("action.0154");
		messageVo.setParameters(new String[] { requestNumber, resourceName });
		messageVo.setTitleProperty("text.timeReports");

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName(ruleName);
		coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		coa.setIsDialogueEnding(true);
		coa.setMessageVo(messageVo);
		dialogueVo.setCourseOfActionVo(coa);
	}
}
