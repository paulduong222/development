package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class PropagateActualsOnlyRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="DISABLED"; //IncidentResourceSaveRuleFactory.RuleEnum.PROPAGATE_ACTUALS_ONLY.name();

	public PropagateActualsOnlyRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
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
		 * B.R.  10.0001
		 * Page: 13 e-ISuite Manage Costs-Daily Cost Use CAse.pdf
		 * 
		 * The system must not allow a user to identify Use Actuals Only 
		 * for Subordinate Resources. 
		 * If the user selects the Use Actuals Only option for the Primary Resource, 
		 * the system should then apply that same setting to all Subordinate Resources.
		 * 
		 * NOTE: requirement is a little ambiguous, it does not address what to do if
		 *       user does not select the use actuals only option. ?  do we apply that 
		 *       setting to all subordinate resources? 
		 */
		
		/*
		 * 1) determine if incident resource is parent (primary) and has no parent
		 * 2) if use actuals only is true, apply to all children
		 */
		if(null != super.irEntity){

			// is the use actual only option set to true
			if(null != super.vo.getCostDataVo() 
					&& BooleanUtility.isTrue(vo.getCostDataVo().getUseAccrualsOnly())){

				// is this resource a top level resource
				if(null != irEntity.getResource() 
						&& null == irEntity.getResource().getParentResource()){
					
					if(CollectionUtility.hasValue(irEntity.getResource().getChildResources())){
						CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
						courseOfActionVo.setCoaName(_RULE_NAME);
						courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
						courseOfActionVo.setIsComplete(true);
						dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
					}
				}
				
			}
		}
		
		return _OK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			// update children and set useactualsonly = true
			for(Resource child : super.irEntity.getResource().getChildResources() ){
				this.updateAllChildren(super.irDao,child);
			}
			
		}

	}

	private void updateAllChildren(IncidentResourceDao irdao,Resource parent) throws Exception {

		IncidentResource ir = (IncidentResource)parent.getIncidentResources().iterator().next();
		
		// update the parent
		irdao.updateCostDataUseActuals(ir.getId(), true);
		
		// update parent's children
		for(Resource child : parent.getChildResources() ){
			this.updateAllChildren(irdao,child);
		}
		
	}
}
