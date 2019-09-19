package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessReviewRosterVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

public class ReviewRosteredResourcesRules extends AbstractRossImportRule implements IWizardRule{
	private static final String _RULE_NAME="REVIEW_ROSTERED_RESOURCES";

	public ReviewRosteredResourcesRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IWizardRule#syncData(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int syncData(DialogueVo dialogueVo) throws Exception {
		// create a navigate courseofaction
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		if(null != coaVo){
			coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
			coaVo.setNavigateVo(
					buildNavigateVo(
							RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
			dialogueVo.setCourseOfActionVo(coaVo);
		}else{
			return _FAIL;
		}

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			/*
			 * if rule check has been completed, return
			 */
			if( (isCourseOfActionComplete(dialogueVo, _RULE_NAME))
					|| super.isCourseOfActionNoAction(dialogueVo, _RULE_NAME))
				return _OK;
				
			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				/*
				 * add rule to processedCoa's
				 */
				dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

				return _OK;
			}
			
			if(buildWizardData(dialogueVo)==_FAIL)
				return _FAIL;
					
		}catch(Exception e){
			throw e;
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int buildWizardData(DialogueVo dialogueVo) throws Exception {
	
		/*
		 * Is there one already in the processedCoa's but not complete?
		 */
		CourseOfActionVo cvo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		if(null != cvo){
			cvo.setCoaName(_RULE_NAME);
			cvo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
			cvo.setNavigateVo(
					buildNavigateVo(
							RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
			dialogueVo.setCourseOfActionVo(cvo);
			return _FAIL;
		}
		
		/*
		 * Determine if there any overhead resources from the ross file
		 */
		//RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		if(this.buildReviewRosteredResources(dialogueVo)){
			// create a navigate courseofaction
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
			coaVo.setNavigateVo(
					buildNavigateVo(
							RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
			dialogueVo.setCourseOfActionVo(coaVo);
			return _FAIL;
		}

		// skip the rule, 
		dialogueVo.getProcessedCourseOfActionVos().add(
				super.buildNoActionCoaVo(_RULE_NAME, false));
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
	private Boolean buildReviewRosteredResources(DialogueVo dialogueVo) throws Exception{
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		//RossImportProcessDao ripDao = (RossImportProcessDao)super.context.getBean("rossImportProcessDao");

		int i =0;
		
		/*
		 * Determine the roster tree
		 */
		ripVo.getReviewRosteredResourcesWizardVo().setRosterVos(new ArrayList<RossImportProcessReviewRosterVo>());

		HashMap<Long,RossImportProcessResourceVo> parentMap = new HashMap<Long,RossImportProcessResourceVo>();

		for(RossImportProcessResourceVo vo :ripVo.getRossImportProcessResourceVos()){
			if(!vo.getExcludeResource()){
				if(StringUtility.hasValue(vo.getRequestNumber()) && !vo.getRequestNumber().startsWith("O") && vo.getRequestNumber().indexOf(".")<0){
					parentMap.put(vo.getRossResReqId(), vo);
				}
				
			}
		}
		
		
		/*
		 * Build list of each parents children
		 */
		Collection<RossImportProcessResourceVo> vos = parentMap.values();
		i=0;
		for(RossImportProcessResourceVo vo : vos){
			RossImportProcessReviewRosterVo ripRevRosVo = new RossImportProcessReviewRosterVo();
			ripRevRosVo.setParentResourceVo(vo);
			
			for(RossImportProcessResourceVo rvo : ripVo.getRossImportProcessResourceVos()){
				if(!rvo.getExcludeResource()){
					if(StringUtility.hasValue(rvo.getRequestNumber()) && !rvo.getRequestNumber().equals(vo.getRequestNumber()) && rvo.getRequestNumber().startsWith(vo.getRequestNumber()+".")){
						if(rvo.getRequestNumber().indexOf(".") > 0) {
							ripRevRosVo.getChildrenVos().add(rvo);
						}
					}
				}
			}

			if(ripRevRosVo.getChildrenVos().size()>0){
				ripVo.getReviewRosteredResourcesWizardVo().getParentNames().add((StringUtility.hasValue(vo.getRequestNumber()) ? vo.getRequestNumber() : "") + " " + vo.getResourceName());
				ripRevRosVo.setIdx(i);
				ripVo.getReviewRosteredResourcesWizardVo().getRosterVos().add(ripRevRosVo);
				i++;
			}
		}
		
		if(ripVo.getReviewRosteredResourcesWizardVo().getRosterVos().size()>0){
			// update the rossimportprocessVo in dialogue.resultObject
			dialogueVo.setResultObject(ripVo);
			return true;
		}else
			return false;
	}
	
}
