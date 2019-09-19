package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.cost.utilities.DefaultAccrualCodeHandler;
import gov.nwcg.isuite.core.domain.AccrualCode;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.AccrualCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.persistence.AssignmentDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.rules.incidentresource.save.propagate.AbstractPropagateRule;
import gov.nwcg.isuite.core.rules.incidentresource.save.propagate.AssignmentStatusFieldRule;
import gov.nwcg.isuite.core.rules.incidentresource.save.propagate.IncidentResourcePropagationHandler;
import gov.nwcg.isuite.core.rules.incidentresource.save.propagate.PropagateRuleFactory;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.CustomPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class PropagateFieldPromptRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="CHECK_PROPAGATE_FIELD_PROMPT";
	private Boolean skipAddToDialogue=false;
	
	public PropagateFieldPromptRules(ApplicationContext ctx)
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
				if(skipAddToDialogue==false){
					dialogueVo.getProcessedCourseOfActionVos()
						.add(super.buildNoActionCoaVo(_RULE_NAME,true));
				}
				
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
		 * only need if propagate parent and children
		 */
		CourseOfActionVo propCoaVo=dialogueVo.getCourseOfActionByName("CHECK_PROPAGATE_PARENT_DATA");
		if(null != propCoaVo && propCoaVo.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
			
			// get the parent resource
			ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");
			Resource resource = resourceDao.getById(vo.getResourceVo().getId(), ResourceImpl.class);
			IncidentResource irParent = irDao.getById(resource.getIncidentResources().iterator().next().getId(), IncidentResourceImpl.class);

			Boolean isStrikeTeam = super.isParentStrikeTeam(irParent);
			
				ResourceData origParentResourceData = new ResourceData(resource);
				ResourceData newParentResourceData = new ResourceData(super.vo);
				
				Collection<ResourceData> childrenResourceData = new ArrayList<ResourceData>();
				populateChildrenResourceData(resource,childrenResourceData);
	
				Collection<PropagateFieldPromptVo> propagateFieldPromptVos = new ArrayList<PropagateFieldPromptVo>();

				GlobalCacheVo cacheVo = super.getGlobalCacheVo();
				Long unknownUnitId=-1L;
				for(OrganizationVo v : cacheVo.getOrganizationVos()){
					if(v.getUnitCode().equalsIgnoreCase("UNKNOWN")){
						unknownUnitId=v.getId();
						break;
					}
				}
				
				// determine which fields need propagation prompt
				for(PropagateRuleFactory.RuleEnum ruleEnum : PropagateRuleFactory.RuleEnum.values()){
					AbstractPropagateRule rule = PropagateRuleFactory.getInstance(ruleEnum
																				   , isStrikeTeam
																				   , origParentResourceData
																				   , newParentResourceData
																				   , childrenResourceData
																				   , propagateFieldPromptVos
																				   ,unknownUnitId);
					
					if(null != rule){
						PropagateFieldPromptVo vo = rule.checkRules();
						if(null != vo)
							propagateFieldPromptVos.add(vo);
					}
				}
				
				if(CollectionUtility.hasValue(propagateFieldPromptVos)){
					Boolean hasOnePrompt=false;
					
					for(PropagateFieldPromptVo v : propagateFieldPromptVos){
						if(BooleanUtility.isTrue(v.getPromptUserToPropagate())){
							hasOnePrompt=true;
							break;
						}
					}
					
					if(hasOnePrompt==true){
						// show propagation prompt
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
						coaVo.setCustomPromptVo(new CustomPromptVo("PROPAGATEWINDOW","text.incidentResources" ,"action.0139crew",propagateFieldPromptVos));
						coaVo.setStoredObject(propagateFieldPromptVos);
						dialogueVo.setCourseOfActionVo(coaVo);
						
						return _FAIL;
					}else{
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
						coaVo.setCustomPromptVo(new CustomPromptVo("PROPAGATEWINDOW","text.incidentResources" ,"action.0139crew",propagateFieldPromptVos));
						coaVo.setStoredObject(propagateFieldPromptVos);
						
						dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
						this.skipAddToDialogue=true;
						return _OK;
					}
				}
		}
		
			
		return _OK;
	}
		
	private void populateChildrenResourceData(Resource parentResource,Collection<ResourceData> childrenResourceData) {
		if(CollectionUtility.hasValue(parentResource.getChildResources())){
			for(Resource child : parentResource.getChildResources()) {
				childrenResourceData.add(new ResourceData(child));
				if(CollectionUtility.hasValue(child.getChildResources())){
					populateChildrenResourceData(child,childrenResourceData);
				}
				
			}
		}
	}
	
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {

		dialogueVo.getCourseOfActionVo().setIsComplete(true);
		
		// add to processed
		dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
		dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

		return _OK;
	}

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
				
				Collection<PropagateFieldPromptVo> propagateFieldPromptVos
						=(Collection<PropagateFieldPromptVo>)coa.getStoredObject();
				
				/*
				for(PropagateFieldPromptVo vo : propagateFieldPromptVos){
					System.out.println("FIELD: " + vo.getFieldName() + " " + 
									   "AUTO: " + vo.getFieldAutoPropagate() + " " +
									   "PROP: " + vo.getPromptUserToPropagateResult());
				}
				*/
				
				// propagate to children
				//WorkPeriodDao wpDao = (WorkPeriodDao)context.getBean("workPeriodDao");
				//AssignmentDao aDao = (AssignmentDao)context.getBean("assignmentDao");
				ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");

				// get the parent resource
				Resource resource = resourceDao.getById(vo.getResourceVo().getId(), ResourceImpl.class);
				IncidentResource irParent = irDao.getById(resource.getIncidentResources().iterator().next().getId(), IncidentResourceImpl.class);
				
				this.propagateChildrenTree(irParent,resource,propagateFieldPromptVos);
				
			}
		}
		
	}
	
	private void propagateChildrenTree(IncidentResource topLevelIRParent, Resource child,Collection<PropagateFieldPromptVo> propagateFieldPromptVos) throws Exception {
		ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");

		GlobalCacheVo cacheVo = super.getGlobalCacheVo();
		Long unknownUnitId=-1L;
		for(OrganizationVo v : cacheVo.getOrganizationVos()){
			if(v.getUnitCode().equalsIgnoreCase("UNKNOWN")){
				unknownUnitId=v.getId();
				break;
			}
		}
		
		IncidentResourcePropagationHandler propHandler = new IncidentResourcePropagationHandler();
		propHandler.propagateFieldPromptVos=propagateFieldPromptVos;
		propHandler.unknownUnitId=unknownUnitId;
		
		// process all children for the current parent
		Collection<Long> ids = new ArrayList<Long>();
		for(Resource subResource : child.getChildResources()){
			ids.add(subResource.getId());
		}

		Collection<String> updateCostRecordsSqls = new ArrayList<String>();
		
		for(Long id : ids){
			Resource subResource = resourceDao.getById(id, ResourceImpl.class);
				
			if(null != subResource.getIncidentResources()){
				IncidentResource irChild = subResource.getIncidentResources().iterator().next();

				propHandler.propagateParentChanges(origResourceData,topLevelIRParent.getResource(), irChild.getResource());

				IncidentResourceVo irvo = IncidentResourceVo.getInstance(irChild, true);
				// set default accrual code
				DefaultAccrualCodeHandler acHandler = new DefaultAccrualCodeHandler(super.context);
				if(null != irvo.getCostDataVo() && BooleanUtility.isFalse(irvo.getCostDataVo().getAccrualLocked()))
					acHandler.setDefaultAccrualCodeVo(irvo);

				if(null != irvo.getCostDataVo() 
						&& null != irvo.getCostDataVo().getAccrualCodeVo()
						&& null != irChild.getCostData()
						&& BooleanUtility.isFalse(irChild.getCostData().getAccrualLocked()) ){
					AccrualCode ac = new AccrualCodeImpl();
					ac.setId(irvo.getCostDataVo().getAccrualCodeVo().getId());
					irChild.getCostData().setAccrualCode(ac);

					int costCount = super.irDao.getUnlockedCostRecordCount(irvo.getId());
					if(costCount > 0){
						String sql="";
						if(irDao.isOracleDialect()==true){
							sql = "UPDATE ISW_INC_RES_DAILY_COST "+
							  "SET ACCRUAL_CODE_ID = " + ac.getId() + " " +
							  "WHERE INCIDENT_RESOURCE_ID = " + irvo.getId() + " " +
							  "AND (IS_LOCKED IS NULL OR IS_LOCKED = 0 )";
							updateCostRecordsSqls.add(sql);
						}else{
							sql = "UPDATE ISW_INC_RES_DAILY_COST "+
							  "SET ACCRUAL_CODE_ID = " + ac.getId() + " " +
							  "WHERE INCIDENT_RESOURCE_ID = " + irvo.getId() + " " +
							  "AND (IS_LOCKED IS NULL OR IS_LOCKED = false )";
							updateCostRecordsSqls.add(sql);
						}
					}
				}
				
				resourceDao.save(irChild.getResource());
				resourceDao.flushAndEvict(irChild);
				irChild = irDao.getById(irChild.getId(), IncidentResourceImpl.class);

				/*
				 * Propagate Status
				 * 
				 * Defect #3270 CR126
				 * Only propagate StrikeTeam status if child status is not Demobed
				 */
				if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(AssignmentStatusFieldRule.FIELD_NAME, propagateFieldPromptVos))){
					PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(AssignmentStatusFieldRule.FIELD_NAME, propagateFieldPromptVos);
					if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
						
						if(BooleanUtility.isTrue(vo.getIsStrikeTeam())){
							// if child status is not D or R
							if(!this.isChildStatusDemob(irChild) && !this.isChildStatusReassigned(irChild)){
								AssignmentDao adao = (AssignmentDao)context.getBean("assignmentDao");
								adao.updateStatus(irChild.getResource().getParentResourceId(), super.vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode());
							}
						}else{
							// if child status is F
							//if(this.isChildStatusFilled(irChild)){
							if(!this.isChildStatusDemob(irChild) && !this.isChildStatusReassigned(irChild)){
								AssignmentDao adao = (AssignmentDao)context.getBean("assignmentDao");
								adao.updateStatus(irChild.getResource().getParentResourceId(), super.vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode());
							}
						}
					}else{
						if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
							// if child status is not D or R
							if(!this.isChildStatusDemob(irChild) && !this.isChildStatusReassigned(irChild)){
								AssignmentDao adao = (AssignmentDao)context.getBean("assignmentDao");
								adao.updateStatus(irChild.getResource().getParentResourceId(), super.vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode());
							}
						}
					}
				}
				/* dan 5/16/2013
				if(BooleanUtility.isTrue(this.isParentStrikeTeam(topLevelIRParent))){
					if(BooleanUtility.isFalse(this.isChildStatusDemob(irChild))){
						AssignmentDao adao = (AssignmentDao)context.getBean("assignmentDao");
						adao.updateStatus(irChild.getResource().getParentResourceId(), vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode());
					}
				}
				*/
				
				// if child has children, update those also
				if(CollectionUtility.hasValue(irChild.getResource().getChildResources())){
					for(Resource child2 : child.getChildResources()){
						this.propagateChildrenTree(topLevelIRParent,child2,propagateFieldPromptVos);
					}
				}
			}
		}

		if(CollectionUtility.hasValue(updateCostRecordsSqls)){
			try{
				irDao.persistSqls(updateCostRecordsSqls);
			}catch(Exception ee){
				// smother for now
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

	private Boolean isChildStatusFilled(IncidentResource childIR){
		
		// Establish if child's status is Demob
		for(Assignment a : childIR.getWorkPeriod().getAssignments()){
			if(a.getEndDate()==null){
				if(null != a.getAssignmentStatus() 
						&& a.getAssignmentStatus()==AssignmentStatusTypeEnum.F)
					return true;
				break;
			}
		}
		
		return false;
		
	}

	private Boolean isChildStatusReassigned(IncidentResource childIR){
		
		// Establish if child's status is Reassigned
		for(Assignment a : childIR.getWorkPeriod().getAssignments()){
			if(a.getEndDate()==null){
				if(null != a.getAssignmentStatus() 
						&& a.getAssignmentStatus()==AssignmentStatusTypeEnum.R)
					return true;
				break;
			}
		}
		
		return false;
		
	}
	
}
