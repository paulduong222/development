package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.persistence.AssignmentDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.rules.IncidentResourceRosterPropagationHandler;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class PropagateParentDataRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="CHECK_PROPAGATE_PARENT_DATA";

	public PropagateParentDataRules(ApplicationContext ctx)
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

			
			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
			}else{
				
				/*
				 * Run Rule Check
				 */
				if(runRuleCheck(dialogueVo) == _FAIL)
					return _FAIL;
				

				/*
				 * Rule check passed, mark as completed
				 */
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(_RULE_NAME,true));
				
			}
			
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
	
		/*
		 * only need to check if we already have a resource id
		 */
		if(null != vo.getResourceVo() && LongUtility.hasValue(vo.getResourceVo().getId())){
			
			/*
			 * does the resource have children?
			 */
			ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");
			Resource resource = resourceDao.getById(vo.getResourceVo().getId(), ResourceImpl.class);
			
			if(null != resource.getChildResources() && resource.getChildResources().size() > 0){
				/*
				 * Prompt user
				 */
				/*
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				coaVo.setPromptVo(new PromptVo(
						"text.incidentResources"
						,"text.promptPropagateParentChanges"
						,null
						,PromptVo._YES | PromptVo._NO | PromptVo._CANCEL));
				coaVo.getPromptVo().setButtonWidth(150);
				coaVo.getPromptVo().setYesLabel("Save and Propagate");
				coaVo.getPromptVo().setNoLabel("Save Primary Only");
				
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
				*/
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
				coaVo.setIsComplete(true);
				dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
				
				return _OK;
				
			}
		}
			
		return _OK;
	}
		
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {

		dialogueVo.getCourseOfActionVo().setIsComplete(true);
		
		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// add to processed
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		}else{
			
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		}

		return _OK;
	}

/*	 (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
		 * Determine if we need to propagate parent data to children?
		 
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa){
			if(coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
				// propagate to children
				//WorkPeriodDao wpDao = (WorkPeriodDao)context.getBean("workPeriodDao");
				//AssignmentDao aDao = (AssignmentDao)context.getBean("assignmentDao");
				ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");
				
				// get the parent resource
				Resource resource = resourceDao.getById(vo.getResourceVo().getId(), ResourceImpl.class);
				IncidentResource irParent = irDao.getById(vo.getId(), IncidentResourceImpl.class);
				
				Collection<Long> ids = new ArrayList<Long>();
				for(Resource subResource : resource.getChildResources()){
					ids.add(subResource.getId());
				}
				for(Long id : ids){
					Resource subResource = resourceDao.getById(id, ResourceImpl.class);
						
					if(null != subResource.getIncidentResources()){
						IncidentResource irChild = subResource.getIncidentResources().iterator().next();
							
						IncidentResourceRosterPropagationHandler.propagateParentChanges(origResourceData,irParent.getResource(), irChild.getResource());

						
						 * if parent is strike team and status is demob,
						 * then remove children from parent
						 
						if( irParent.getResource().getResourceClassification()==ResourceClassificationEnum.ST
								||
							irParent.getResource().getResourceClassification()==ResourceClassificationEnum.TF){
						
							if(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode().equals("D")){
								irChild.getResource().setParentResource(null);
								irChild.getResource().setComponent(false);
								irChild.getResource().setLeaderType(new Integer(99));
							}
						}
							
						resourceDao.save(irChild.getResource());
						resourceDao.flushAndEvict(irChild);
						irChild = irDao.getById(irChild.getId(), IncidentResourceImpl.class);

						AssignmentDao adao = (AssignmentDao)context.getBean("assignmentDao");
						adao.updateStatus(irParent.getResourceId(), vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode());

					}
				}
				
			}
		}
		
	}
*/
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		/*
		 * Determine if we need to propagate parent data to children?
		 */
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa){
			if(coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
				// propagate to children
				//WorkPeriodDao wpDao = (WorkPeriodDao)context.getBean("workPeriodDao");
				//AssignmentDao aDao = (AssignmentDao)context.getBean("assignmentDao");
				ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");

				// get the parent resource
				Resource resource = resourceDao.getById(vo.getResourceVo().getId(), ResourceImpl.class);
				IncidentResource irParent = irDao.getById(resource.getIncidentResources().iterator().next().getId(), IncidentResourceImpl.class);
				
				//dan-05/14/2013 this.propagateChildrenTree(irParent,resource);
				
			}
		}
		
	}
	
	private void propagateChildrenTree(IncidentResource topLevelIRParent, Resource child) throws Exception {
		ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");
		
		// process all children for the current parent
		Collection<Long> ids = new ArrayList<Long>();
		for(Resource subResource : child.getChildResources()){
			ids.add(subResource.getId());
		}
		for(Long id : ids){
			Resource subResource = resourceDao.getById(id, ResourceImpl.class);
				
			if(null != subResource.getIncidentResources()){
				IncidentResource irChild = subResource.getIncidentResources().iterator().next();
					
				IncidentResourceRosterPropagationHandler.propagateParentChanges(origResourceData,topLevelIRParent.getResource(), irChild.getResource());

					/*
					 * if parent is strike team and status is demob,
					 * then remove children from parent
					 */
					/*
					 * Commenting out below per
					 * Defect # 3277 CR 147 - Primary Resource Demobed
					 * 
					if( irParent.getResource().getResourceClassification()==ResourceClassificationEnum.ST
							||
						irParent.getResource().getResourceClassification()==ResourceClassificationEnum.TF){
					
						if(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode().equals("D")){
							irChild.getResource().setParentResource(null);
							irChild.getResource().setComponent(false);
							irChild.getResource().setLeaderType(new Integer(99));
						}
					}
					 * 
					 */
					
				resourceDao.save(irChild.getResource());
				resourceDao.flushAndEvict(irChild);
				irChild = irDao.getById(irChild.getId(), IncidentResourceImpl.class);

				/*
				 * Propagate Status
				 * 
				 * Defect #3270 CR126
				 * Only propagate StrikeTeam status if child status is not Demobed
				 */
				if(BooleanUtility.isTrue(this.isParentStrikeTeam(topLevelIRParent))){
					if(BooleanUtility.isFalse(this.isChildStatusDemob(irChild))){
						AssignmentDao adao = (AssignmentDao)context.getBean("assignmentDao");
						adao.updateStatus(irChild.getResource().getParentResourceId(), vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode());
					}
				}
				
				// if child has children, update those also
				if(CollectionUtility.hasValue(irChild.getResource().getChildResources())){
					for(Resource child2 : child.getChildResources()){
						this.propagateChildrenTree(topLevelIRParent,child2);
					}
				}
			}
		}
	
	}
	
	private Boolean isChildStatusDemob(IncidentResource childIR){
		Boolean isChildDemob=false;
		
		// Establish if child's status is Demob
		for(Assignment a : childIR.getWorkPeriod().getAssignments()){
			if(a.getEndDate()==null){
				if(null != a.getAssignmentStatus() 
						&& a.getAssignmentStatus()==AssignmentStatusTypeEnum.D)
					isChildDemob=true;
				break;
			}
		}
		
		return isChildDemob;
		
		
	}
	
}
