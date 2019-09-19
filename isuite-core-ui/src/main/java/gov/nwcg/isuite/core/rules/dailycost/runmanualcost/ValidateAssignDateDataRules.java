package gov.nwcg.isuite.core.rules.dailycost.runmanualcost;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

import org.springframework.context.ApplicationContext;

public class ValidateAssignDateDataRules extends AbstractRunManualDailyCostRule implements IRule {
	public static final String _RULE_NAME=RunManualDailyCostRuleFactory.RuleEnum.VALIDATE_ASSIGN_DATE.name();

	public ValidateAssignDateDataRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
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
			if(isCourseOfActionComplete(dialogueVo, ruleName))
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
				.add(super.buildNoActionCoaVo(ruleName,true));
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception{
		// check for incidentResource?
		if(null != super.costResourceDataVo){
			if(!DateUtil.hasValue(super.costResourceDataVo.getAssignDate())){
				
				ResourceDao resDao = (ResourceDao)context.getBean("resourceDao");
				Resource r = resDao.getById(super.costResourceDataVo.getResourceId(), ResourceImpl.class);
				resDao.flushAndEvict(r);
				
				String name="";
				if(BooleanUtility.isFalse(r.isPerson())){
					name=r.getResourceName();
				}else{
					name=r.getFirstName() + " " + r.getLastName();
				}
				
				String errorMsg2="No Cost records were generated for " + name + ", because of the following reasons:  No assign date is defined for the resource.";
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost"
												, "info.generic"
												, new String[]{errorMsg2}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setStoredObject3("Missing Check-In/Assign Date");
								
				dialogueVo.setCourseOfActionVo(coaVo);
				return _FAIL;
			}
			
		}
		
		// check for incidentResourceOther?
		if(null != this.iroEntity){
			if(null==this.iroEntity.getCostData() || 
					!DateUtil.hasValue(iroEntity.getCostData().getAssignDate())){
				
				String errorMsg="Cannot run cost for Resource, Resource must have an Assign date.";
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost"
												, "info.generic"
												, new String[]{errorMsg}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
								
				dialogueVo.setCourseOfActionVo(coaVo);
				return _FAIL;
			}
			
		}

		return _OK;
		
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
