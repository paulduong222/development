package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckParentGenCostsFlagRules extends AbstractCrewRule implements IRule{
	public static final String _RULE_NAME=TimePostCrewRuleFactory.RuleEnum.CHECK_PARENT_GEN_COST_FLAG.name();

	public CheckParentGenCostsFlagRules(ApplicationContext ctx)
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

		try{
			IncidentResourceDao irdao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			Long atId=super.entities.iterator().next().getId();
			IncidentResource ir = irdao.getByAssignmentTimeId(atId);
			if(null != ir && null != ir.getResource() && (null != ir.getResource().getParentResource() || LongUtility.hasValue(ir.getResource().getParentResourceId()))){
				Long parentResourceId=(null != ir.getResource().getParentResource() ? ir.getResource().getParentResource().getId() : ir.getResource().getParentResourceId() );
				IncidentResource parentIncResource=irdao.getByResourceId(parentResourceId);
				
				// system will only change the flag one time
				if(null != parentIncResource.getCostData() && BooleanUtility.isFalse(StringBooleanEnum.toBooleanValue(parentIncResource.getCostData().getGenerateCostsSys()))){
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaName(_RULE_NAME+"CHECKPARENT");
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
					courseOfActionVo.setIsComplete(true);
					courseOfActionVo.setStoredObject(parentIncResource.getId());
					dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
				}
				
				if(null != parentIncResource)
					irdao.flushAndEvict(parentIncResource);
			}
			irdao.flushAndEvict(ir);
		}catch(Exception e){
			
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME+"CHECKPARENT");

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			if(null != coaVo.getStoredObject()){
				Long parentIrId=(Long)coaVo.getStoredObject();
				
				try{
					IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
					IncidentResource parentIncResource=irDao.getById(parentIrId, IncidentResourceImpl.class);

					// moved to run cost area - meet with donna 3/26/2014 sacramento
					//parentIncResource.getCostData().setGenerateCosts(false);
					//parentIncResource.getCostData().setGenerateCostsSys(StringBooleanEnum.Y);
					//irDao.save(parentIncResource);
					//irDao.flushAndEvict(parentIncResource);
					
				}catch(Exception e){
					//smother
				}
				
			}
		}
		
	}

}
