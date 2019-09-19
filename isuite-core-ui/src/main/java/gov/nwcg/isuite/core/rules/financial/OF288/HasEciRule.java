package gov.nwcg.isuite.core.rules.financial.OF288;

import gov.nwcg.isuite.core.rules.adminoffice.save.AdminOfficeSaveRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.MissingDataVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.CustomPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class HasEciRule extends AbstractInvoiceGenerationRule implements IRule {
	public static final String _RULE_NAME=OF288InvoiceGeneratorRuleFactory.RuleEnum.AD_HAS_ECI.name();

	public HasEciRule(ApplicationContext ctx, String rname)
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
		
		Collection<MissingDataVo> missingDataVos = new ArrayList<MissingDataVo>();

		if(BooleanUtility.isFalse(super.filter.getPrintOriginalInvoice()))
			return _OK;
		
		for(IncidentResourceVo irvo : super.irs){
			if(irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo() != null){
				EmploymentTypeEnum empType = irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType();
				if(null != empType){
					if(empType==EmploymentTypeEnum.AD){
						if(!StringUtility.hasValue(irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getAdPaymentInfoVo().getEci())){
							MissingDataVo missingDataVo = new MissingDataVo();
							missingDataVo.setDataType("ECI");
							missingDataVo.setIncidentResourceId(irvo.getId());
							missingDataVo.setRequestNumber(irvo.getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber());
							String resourceName = "";
							if (irvo.getResourceVo().getResourceName() != null) {
								resourceName = irvo.getResourceVo().getResourceName();
							} else {
								resourceName = irvo.getResourceVo().getFirstName() + " " + irvo.getResourceVo().getLastName();
							}
							missingDataVo.setResourceName(resourceName);
							missingDataVos.add(missingDataVo);
						}
					}
				}
			}
		}

		if(CollectionUtility.hasValue(missingDataVos)){
		    CourseOfActionVo coa = new CourseOfActionVo();
		    coa.setCoaName(_RULE_NAME);
		    coa.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
			coa.setCustomPromptVo(new CustomPromptVo("MISSINGECI","text.time" ,"action.0142",missingDataVos));
			coa.setStoredObject(missingDataVos);
	    
		    dialogueVo.setCourseOfActionVo(coa);
			
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
