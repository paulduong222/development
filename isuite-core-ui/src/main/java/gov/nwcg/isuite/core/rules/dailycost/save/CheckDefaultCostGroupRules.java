package gov.nwcg.isuite.core.rules.dailycost.save;

import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceOtherDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import org.springframework.context.ApplicationContext;

public class CheckDefaultCostGroupRules extends AbstractSaveDailyCostRule implements IRule {
	public static final String _RULE_NAME=SaveDailyCostRuleFactory.RuleEnum.CHECK_DEFAULT_COST_GROUP.name();

	public CheckDefaultCostGroupRules(ApplicationContext ctx) {
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
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		/*
		 * Once a Cost Group is assigned to one or more Resource Cost records, 
		 * the system must assign that same Cost Group to all future Daily Cost Records. 
		 * In essence, the Cost Group will be treated as a default until a user changes 
		 * it to a different Cost Group.
		 */
		
		// check incidentResource
		if(null != irEntity){
			if(null != irEntity.getCostDataId() ){
				//&& null == irEntity.getCostData().getDefaultCostGroup()){
				if(null != super.irdcVo.getCostGroupVo()){
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaName(_RULE_NAME);
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
					courseOfActionVo.setIsComplete(true);
					courseOfActionVo.setStoredObject(irdcVo.getCostGroupVo().getId());
					dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
						
				}
			}
		}
		
		// check incidentResourceOther
		if(null != iroEntity){
			if(null != iroEntity.getCostDataId() ){
				if(null != super.irdcVo.getCostGroupVo()){
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaName(_RULE_NAME);
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
					courseOfActionVo.setIsComplete(true);
					courseOfActionVo.setStoredObject(irdcVo.getCostGroupVo().getId());
					dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
						
				}
			}
		}

		return _OK;
		
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			if(null != coaVo.getStoredObject()){
				
				IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
				//IncidentResourceOtherDao iroDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
				
				Long costGroupId=0L;
				try{
					costGroupId=TypeConverter.convertToLong(coaVo.getStoredObject());
				}catch(Exception e){}
				
				if(LongUtility.hasValue(costGroupId))	
					if(null != irEntity){
						irDao.updateDefaultCostGroup(irEntity.getCostDataId(),costGroupId);
					}

					if(null != iroEntity){
						irDao.updateDefaultCostGroup(iroEntity.getCostDataId(),costGroupId);
					}
			}
		}

	}

}
