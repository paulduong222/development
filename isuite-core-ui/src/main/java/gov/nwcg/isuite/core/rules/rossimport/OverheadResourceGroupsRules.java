package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessOverheadGroupVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class OverheadResourceGroupsRules extends AbstractRossImportRule implements IWizardRule{
	private static final String _RULE_NAME="OVERHEAD_RESOURCE_GROUPS";

	public OverheadResourceGroupsRules(ApplicationContext ctx){
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

				RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

				/*
				 * Check if all overhead resource groups have been resolved
				 */
				for(RossImportProcessOverheadGroupVo vo : ripVo.getOverheadResourceGroupWizardVo().getOverheadGroupVos()){
					if(!vo.getResolved()){
						dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						dialogueVo.getCourseOfActionVo().setMessageVo(new MessageVo("text.rossImport","info.generic",new String[]{"All Overhead Resource Groups must be resolved before continuing."},MessageTypeEnum.CRITICAL));
							
						return _FAIL;
					}
				}
					
				/*
				 * add rule to processedCoa's
				 */
				dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

				updateRossImportProcessVo(dialogueVo);
					
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
		if(this.buildOverheadResourceGroups(dialogueVo)){
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
	
	/**
	 * @param dialogueVo
	 * @throws Exception
	 */
	private void updateRossImportProcessVo(DialogueVo dialogueVo) throws Exception {
		
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		
		HashMap<Long,RossImportProcessResourceVo> ripResources = new HashMap<Long,RossImportProcessResourceVo>();
		ripResources = RossImportProcessResourceVo.toHashMapRossResReqId(ripVo.getRossImportProcessResourceVos());
			
		for(RossImportProcessOverheadGroupVo vo : ripVo.getOverheadResourceGroupWizardVo().getOverheadGroupVos()){
			
			// update the parent in ripVo
			RossImportProcessResourceVo parent = vo.getParentResourceVo();
			parent.setRossGroupAssignment("ROSTERED");
			
			/*
			 * update the resources in ripVo and set excludeFromRoster when
			 * the group has been marked as single
			 */
			if(vo.getSingleGroup().equals(Boolean.TRUE)){
				parent.setRossGroupAssignment("SINGLE");
				
				for(RossImportProcessResourceVo rvo : vo.getChildrenVos()){
					RossImportProcessResourceVo resVo = ripResources.get(rvo.getRossResReqId());
					
					if(null != resVo){
						
						resVo.setExcludeFromRoster(true);

						ripResources.put(rvo.getRossResReqId(), resVo);
						
					}else{
						//System.out.println("missing "+rvo.getRossResReqId() + " " + rvo.getResourceName());
					}
				}
			}
			ripResources.put(parent.getRossResReqId(), parent);

			
			((RossImportProcessVo)dialogueVo.getResultObject()).setRossImportProcessResourceVos(ripResources.values());
				
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
	private Boolean buildOverheadResourceGroups(DialogueVo dialogueVo) throws Exception{
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		//Long incidentId=ripVo.getMatchIncidentsWizardVo().getMatchingIncidentId();
		
		/*
		 * Build list of distinct overhead parents
		 */
		HashMap<Long,RossImportProcessResourceVo> overheadParentMap = new HashMap<Long,RossImportProcessResourceVo>();
		
		for(RossImportProcessResourceVo rvo : ripVo.getRossImportProcessResourceVos()){
			// is rvo in ripVo.eisuitevos
			boolean isAlreadyAdded = false;
			
			for( RossImportProcessEISuiteResourceVo ripevo : ripVo.getRossImportProcessEisuiteVos() ){
				if(LongUtility.hasValue(ripevo.getRossResReqId()) && LongUtility.hasValue(rvo.getRossResReqId()) ){
					if(ripevo.getRossResReqId().compareTo(rvo.getRossResReqId())==0){
						isAlreadyAdded=true;
						break;
					}
				}
			}
			
			if(isAlreadyAdded==false){
				if(StringUtility.hasValue(rvo.getRequestNumber()) && rvo.getRequestNumber().startsWith("O") && rvo.getRequestNumber().indexOf(".")<0){
					overheadParentMap.put(rvo.getRossResReqId(), rvo);
				}
			}
		}

		//System.out.println("Overhead parent count: "+ overheadParentMap.size());

		/*
		 * Build list of each parents children
		 */
		Collection<RossImportProcessResourceVo> vos = overheadParentMap.values();
		int i=0;
		for(RossImportProcessResourceVo vo : vos){
			if(vo.getRequestNumber().equals("O-9")){
				//System.out.println("");
				
			}
			RossImportProcessOverheadGroupVo ogvo = new RossImportProcessOverheadGroupVo();
			ogvo.setParentResourceVo(vo);
			
			for(RossImportProcessResourceVo rvo : ripVo.getRossImportProcessResourceVos()){
				if(rvo.getRequestNumber().equals("O-9.40")){
					//System.out.println("");
				}
				if(!rvo.getExcludeResource()){
					if(StringUtility.hasValue(rvo.getRequestNumber()) && rvo.getRequestNumber().startsWith(vo.getRequestNumber()+".")){
						if(rvo.getRequestNumber().indexOf(".")>0){
							ogvo.getChildrenVos().add(rvo);
						}
					}
				}
			}

			if(ogvo.getChildrenVos().size()>0){
				ripVo.getOverheadResourceGroupWizardVo().getParentNames().add(
						(StringUtility.hasValue(vo.getRequestNumber())) ? vo.getRequestNumber() : "" + " " + vo.getResourceName());
				ogvo.setIdx(i);
				ripVo.getOverheadResourceGroupWizardVo().getOverheadGroupVos().add(ogvo);
				i++;
			}
		}
		

		if(ripVo.getOverheadResourceGroupWizardVo().getOverheadGroupVos().size()>0){
			// update the rossimportprocessVo in dialogue.resultObject
			dialogueVo.setResultObject(ripVo);
			return true;
		}else
			return false;
	}
	
}
