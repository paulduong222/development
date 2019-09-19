package gov.nwcg.isuite.core.rules.iap.saveposition;

import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.core.persistence.IapPositionItemCodeDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckMax204PositionsRules extends AbstractPositionSaveRule implements IRule {
	public static final String _RULE_NAME=PositionSaveRuleFactory.RuleEnum.CHECK_MAX_204_POSITIONS.name();

	public CheckMax204PositionsRules(ApplicationContext ctx) {
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
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception{
		
		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;
		
		/*
		 * If adding a new 204 position, verify position name is unique to incident/group
		 */
		if(StringUtility.hasValue(super.iapPositionVo.getForm()) && iapPositionVo.getForm().equals("204")){
			if(!LongUtility.hasValue(super.iapPositionVo.getId())){
				IapPositionItemCodeDao dao = (IapPositionItemCodeDao)context.getBean("iapPositionItemCodeDao");
				Long incidentId=(null != super.iapPositionVo.getIncidentVo() ? super.iapPositionVo.getIncidentVo().getId() : 0L);
				Long incidentGroupId=(null != super.iapPositionVo.getIncidentGroupVo() ? super.iapPositionVo.getIncidentGroupVo().getId() : 0L);
				int cnt=dao.get204PositionCount(incidentId,incidentGroupId);
				
				if(cnt >= 6){
					error = new ErrorObject("error.800000","Only 6 positions are allowed for the 204 position settings.");
					errorObjects.add(error);
				}
			}
		}
		
		if(CollectionUtility.hasValue(errorObjects)){
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ValidationError");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);

			coaVo.setErrorObjectVos(errorObjects);
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
		
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
