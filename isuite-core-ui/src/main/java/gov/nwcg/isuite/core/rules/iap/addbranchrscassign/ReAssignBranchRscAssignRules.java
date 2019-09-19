package gov.nwcg.isuite.core.rules.iap.addbranchrscassign;

import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.CustomPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

public class ReAssignBranchRscAssignRules extends AbstractAddBranchRscAssignRule implements IRule {
	public static final String _RULE_NAME=AddBranchRscAssignRuleFactory.RuleEnum.REASSIGN_RESOURCE.name();
	
	public ReAssignBranchRscAssignRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			
			/*
			 * if rule check completed,
			 * return
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
				 * Run rule check
				 */
				if(runRuleCheck(dialogueVo)==_FAIL)
					return _FAIL;
				
				/*
				 * Rule check passed, mark as completed
				 */
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(_RULE_NAME,true));
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws PersistenceException 
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws PersistenceException {

		List<IapBranchRscAssignVo> alreadyAssignedResources = null;
		List<IapBranchRscAssignVo> uniqueResources = null;
		
		// 1. Send alreadyAssignedResources back to pop up window 
		// 2. Save alreadyAssignedResources and uniqueResources for trip back
		// If user selects:
		// YES: Save All, i.e., resourcesBeingAdded
		// NO: Save uniqueResources
		// CANCEL: Abort
		
		List<Long> alreadyAssignedResourceIDs = 
			iapBranchRscAssignDao.getResourcesAlreadyAssigned(resourcesBeingAdded, iapPlanId);
		
		if(alreadyAssignedResourceIDs != null && alreadyAssignedResourceIDs.size()>0) {
			alreadyAssignedResources = new ArrayList<IapBranchRscAssignVo>();
			uniqueResources = new ArrayList<IapBranchRscAssignVo>();
			
			boolean isReAssigned = false;
			for(IapBranchRscAssignVo v : resourcesBeingAdded){
				isReAssigned = false;
				for(Long alreadyAssignedResourceID: alreadyAssignedResourceIDs){
					if(alreadyAssignedResourceID.equals(v.getResourceId())){
						isReAssigned = true;
						alreadyAssignedResources.add(v);
						break;
					} 
				}
				if(!isReAssigned){
					uniqueResources.add(v);
				}
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
			coaVo.setCustomPromptVo(new CustomPromptVo("REASSIGN_ADDRESOURCEASSIGN",null,null,alreadyAssignedResources));
			
			coaVo.setStoredObject(resourcesBeingAdded);
			if(uniqueResources.size()==0) {
				uniqueResources = null; // The "NO" button on the custom prompt need only be displayed if uniqueResources 
										// exist in the list of resources the user wants to add.
			}
			coaVo.setStoredObject1(uniqueResources); 
			
			dialogueVo.setCourseOfActionVo(coaVo);
			return _FAIL;
		}
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {

		CourseOfActionVo coa = dialogueVo.getCourseOfActionVo();
		
		// check prompt result
		if(getCustomPromptResult(dialogueVo) == PromptVo._YES) { // ADD ALL - INCLUDING RESOURCES ALREADY ASSIGNED
			// COA already contains AllResources list in its storedObject property.
			coa.setStoredObject1(null);
		} else if(getCustomPromptResult(dialogueVo) == PromptVo._NO) { // DON'T ADD RESOURCES THAT HAVE ALREADY BEEN ASSIGNED
			List<IapBranchRscAssignVo> uniqueResources = (List<IapBranchRscAssignVo>)coa.getStoredObject1();
			coa.setStoredObject(uniqueResources);
			coa.setStoredObject1(null);
		} else{ //CANCEL
			// ABORT. DON'T ADD ANY RESOURCES.
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"No resources were added."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);

			return _FAIL;
		}
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
	}
}
