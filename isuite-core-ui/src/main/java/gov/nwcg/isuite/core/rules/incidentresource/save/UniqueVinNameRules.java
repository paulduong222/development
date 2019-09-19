package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.ContractorPaymentInfo;
import gov.nwcg.isuite.core.persistence.ContractorPaymentInfoDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class UniqueVinNameRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="CHECK_UNIQUE_VIN_NAME";


	public UniqueVinNameRules(ApplicationContext ctx)
	{
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
			
			return _OK;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		String vinName="";
		Long assignmentTimeId=0L;
		
		// if user is timeuser, then validate vin
		if(super.hasRole("ROLE_TIME")){
			/*
			 * Only need to check if employment type is contractor
			 */
			if(null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()){

				assignmentTimeId=vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getId();
				
				if(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() == EmploymentTypeEnum.CONTRACTOR
						&&
				   null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo()){		
					vinName=vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getVinName();
				}
			}

			if(StringUtility.hasValue(vinName)){
				ContractorPaymentInfoDao cpiDao = (ContractorPaymentInfoDao)context.getBean("contractorPaymentInfoDao");
				
				/*
				Collection<ContractorPaymentInfo> list = cpiDao.getByVinName(vinName.toUpperCase());
				
				if(null != list && list.size()>0){
					// check assignmentTimeId
					ContractorPaymentInfo cpi = list.iterator().next();
					
					if(null != assignmentTimeId && assignmentTimeId > 0){
						if(cpi.getAssignmentTimeId().compareTo(assignmentTimeId) != 0){
							
							dialogueVo.setCourseOfActionVo(
									super.buildErrorCoaVo("text.incidentResources"
														  ,"validationerror"
														  ,"error.900013"
														  ,null));	
							
							return _FAIL;
						}
					}else{
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_MSG_FINISHED);
						coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						coaVo.setMessageVo(new MessageVo("text.incidentResources", "error.900013" , null, MessageTypeEnum.CRITICAL));

						dialogueVo.setCourseOfActionVo(coaVo);
						
						return _FAIL;
					}
				}
				*/
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
