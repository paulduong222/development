package gov.nwcg.isuite.core.rules.dailycost.runmanualcost;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class ValidateNoAgencyNoPostingsRules extends AbstractRunManualDailyCostRule implements IRule {
	public static final String _RULE_NAME=RunManualDailyCostRuleFactory.RuleEnum.VALIDATE_NO_AGENCY_NO_POSTINGS.name();

	public ValidateNoAgencyNoPostingsRules(ApplicationContext ctx) {
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
			String agency=(StringUtility.hasValue(super.costResourceDataVo.getAgencyCode())?super.costResourceDataVo.getAgencyCode() : "");
			if(!StringUtility.hasValue(agency)){
				
				
				/*
					When the user selects a Resource that does not have an Agency defined and 
					Resource has no time postings, the system must display a message to the user 
					indicating that the user must select an Agency when generating Cost records 
					for a Resource.
					 
					Show Error Message:
					 
					<Resource Name> does not have an agency defined. 
					This can result in no cost records being generated for this resource. 
					Please define an agency for the resource before generating cost records.
				 */
				
				if(!IntegerUtility.hasValue(super.costResourceDataVo.getTimePostCount())
						&& !IntegerUtility.hasValue(super.costResourceDataVo.getAdjustmentCount())){
				
					ResourceDao resDao = (ResourceDao)context.getBean("resourceDao");
					Resource r = resDao.getById(super.costResourceDataVo.getResourceId(), ResourceImpl.class);
					resDao.flushAndEvict(r);
					
					String name="";
					if(BooleanUtility.isFalse(r.isPerson())){
						name=r.getResourceName();
					}else{
						name=r.getFirstName() + " " + r.getLastName();
					}
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.dailyCost"
													, "info.0380.a"
													, new String[]{name}
													, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
					coaVo.setStoredObject3("Missing Agency");
									
					dialogueVo.setCourseOfActionVo(coaVo);
					return _FAIL;
				}
			}			
		}

		// check for IncidentResourceOTher?
		if(null != this.iroEntity){
			if(null==this.iroEntity.getResourceOther().getAgency()){
				String errorMsg="Cannot run cost for Resource, Resource must have an Agency assigned.";
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost"
												, "info.generic"
												, new String[]{errorMsg}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
								
				/*
				 * this rule no longer applies, the system should still generate actuals
				 */
				//dialogueVo.setCourseOfActionVo(coaVo);
				//return _FAIL;
			}
			
		}
		
		return _OK;
		
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
