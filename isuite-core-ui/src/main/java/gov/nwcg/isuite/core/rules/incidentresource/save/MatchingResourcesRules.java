package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.filter.ResourceDuplicateFilter;
import gov.nwcg.isuite.core.filter.impl.ResourceDuplicateFilterImpl;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.CustomPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class MatchingResourcesRules extends AbstractIncidentResourceSaveRule implements IRule {
	private static final String _RULE_NAME="CHECK_MATCHING_RESOURCE";

	public MatchingResourcesRules(ApplicationContext ctx){
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
			
			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				// empty the selectionRecords
				dialogueVo.getCourseOfActionVo().getCustomPromptVo().getSelectionItems().clear();
				
				// add to processed
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
				
				// determine result and handle what needs to happen on merging of data

				
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
			throw e;
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		if(!LongUtility.hasValue(vo.getId())){
			
			/*
			 * Determine if there are potential matches
			 */
			ResourceDuplicateFilter filter=new ResourceDuplicateFilterImpl();
			filter.setResourceId(vo.getResourceVo().getId());
			filter.setFirstName(vo.getResourceVo().getFirstName());
			filter.setLastName(vo.getResourceVo().getLastName());
			filter.setResourceName(vo.getResourceVo().getResourceName());
			if(null != vo.getResourceVo().getOrganizationVo())
				filter.setOrganizationId(vo.getResourceVo().getOrganizationVo().getId());
			else
				filter.setOrganizationId(null);
			
			ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");
			
			Collection<Resource> entities = resourceDao.getMatchingResources(filter);
			
			if( (null != entities) && (entities.size()>0)){
				Collection<ResourceVo> rvos=ResourceVo.getInstances(entities, true);
				Collection<ResourceVo> newrvos=new ArrayList<ResourceVo>();
				
				for(ResourceVo rvo : rvos){
					if(rvo.isPerson()){
						rvo.setResourceName(rvo.getLastName() + ", " + rvo.getFirstName());
					}
					newrvos.add(rvo);
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
				coaVo.setCustomPromptVo(new CustomPromptVo("CONFIRM_MATCHING_RESOURCES"
						, "text.incidentResources", "action.0180", newrvos));
				String[] params = {
						(vo.getResourceVo().getResourceName() != null && vo.getResourceVo().getResourceName() != "") 
								? vo.getResourceVo().getResourceName() 
								: vo.getResourceVo().getLastName() + ", " + vo.getResourceVo().getFirstName()
				};
				coaVo.getCustomPromptVo().setParameters(params);
				dialogueVo.setCourseOfActionVo(coaVo);

				return _FAIL;
			}
			
		}

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

	
}
