package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckChildGenCostsFlagRules extends AbstractCrewRule implements IRule{
	public static final String _RULE_NAME=TimePostCrewRuleFactory.RuleEnum.CHECK_CHILD_GEN_COST_FLAG.name();

	public CheckChildGenCostsFlagRules(ApplicationContext ctx)
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
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaName(_RULE_NAME+"CHECKCHILD");
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
					courseOfActionVo.setIsComplete(true);
					dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
					
		}catch(Exception e){
			
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME+"CHECKCHILD");

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			
			Long parentResourceId = 0L;
			
			if(CollectionUtility.hasValue(super.entities)){
				for(AssignmentTime at : super.entities){
					IncidentResource ir = irDao.getByAssignmentTimeId(at.getId());
					
					if(null != ir && null != ir.getResource() && null!=ir.getResource().getParentResource()){
						parentResourceId = ir.getResource().getParentResource().getId();
						break;
					}
					/*	
					
					if(null != ir && null != ir.getResource() && (null != ir.getResource().getParentResource() || LongUtility.hasValue(ir.getResource().getParentResourceId()))){
						// system will only change the flag one time
						if(null != ir.getCostData() && BooleanUtility.isFalse(StringBooleanEnum.toBooleanValue(ir.getCostData().getGenerateCostsSys()))){
							// turn on child gen costs since child has time posting
							ir.getCostData().setGenerateCosts(true);
							ir.getCostData().setGenerateCostsSys(StringBooleanEnum.Y);
							irDao.save(ir);
							irDao.flushAndEvict(ir.getCostData());
							irDao.flushAndEvict(ir);
						}
					}
					irDao.flushAndEvict(ir.getCostData());
					irDao.flushAndEvict(ir);
					*/
					
				}
				
				if(LongUtility.hasValue(parentResourceId)){
					// turn on all subordinates since this child has time postings
					// 3/26/2014 - this change is to turn on all subordinates generate costs flag = true
					// when at least one subordinate has actuals.  - meeting with Donna in sacramento
					//irDao.updateChildGenCostsTrue(parentResourceId);
					
				}
			}
		}
		
	}

}
