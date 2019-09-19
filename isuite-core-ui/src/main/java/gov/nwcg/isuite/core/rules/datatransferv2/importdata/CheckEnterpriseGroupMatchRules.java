package gov.nwcg.isuite.core.rules.datatransferv2.importdata;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGroupGridVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckEnterpriseGroupMatchRules extends AbstractImportDataRule implements IRule {
	public static final String _RULE_NAME = ImportDataRuleFactory.RuleEnum.CHECK_ENTERPRISE_GROUP_MATCH.name();

	public CheckEnterpriseGroupMatchRules(ApplicationContext ctx) {
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
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				// add to processed
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				return checkPromptResult(dialogueVo);
				
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
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {

			CourseOfActionVo coaVo = dialogueVo.getCourseOfActionVo();
			String groupName="";
			
			if(null != coaVo.getStoredObject2())
				groupName=(String)coaVo.getStoredObject2();
			
			// check if incident group name is unique?
			if(null != coaVo.getStoredObject()
					&& StringUtility.hasValue(groupName)){
				
				Collection<IncidentGroupGridVo> vos = (Collection<IncidentGroupGridVo>)coaVo.getStoredObject();
				for(IncidentGroupGridVo vo : vos){
					if(vo.getGroupName().equalsIgnoreCase(groupName)){
						// error?, must be unique name
						coaVo.setStoredObject1("An Incident Group already exists in Enterprise with the same name.  "+
												" Please enter a unique Incident Group Name.");
						return _FAIL;
					}
				}
			}else{
				// no name was inputted?
			}
			
			// continue
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.NOACTION);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * When importing into Enterprise, we want to check if there is ane existing 
		 * Incident Group already in the database that matches the 
		 * one in the incoming file.
		 */
		CourseOfActionVo coaVo = new CourseOfActionVo();
		
		if(super.getRunMode().equals("ENTERPRISE")){
			IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			if(CollectionUtility.hasValue(super.dataTransferXml.getDataTransferIncGroups())){
				String incomingTi=super.dataTransferXml.getDataTransferIncGroups().iterator().next().getTI();
				if(StringUtility.hasValue(incomingTi)){
					Collection<IncidentGroupGridVo> gridVos=igDao.getGroupsForDataTransfer();
					Long matchingGroupId=0L;

					coaVo.setStoredObject(gridVos);
					
					// try and find a matching ig
					for(IncidentGroupGridVo vo : gridVos){
						if(StringUtility.hasValue(vo.getTransferableIdentity())){
							if(vo.getTransferableIdentity().equals(incomingTi)){
								matchingGroupId=vo.getId();
								break;
							}
						}
					}
					
					if(!LongUtility.hasValue(matchingGroupId)){
						// need to prompt the user to
						// enter a name for a new group
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
						coaVo.setPromptVo(new PromptVo("text.dataTransfer"
											,"info.9918"
											,new String[]{}
											,PromptVo._OK | PromptVo._CANCEL));
						
						dialogueVo.setCourseOfActionVo(coaVo);
					
						return _FAIL;
						
					}else{
						// nothing else to do, rule check is complete
						dialogueVo.getProcessedCourseOfActionVos()
							.add(super.buildNoActionCoaVo(_RULE_NAME,true));
					}
				}
			}
		}

		return _OK;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
