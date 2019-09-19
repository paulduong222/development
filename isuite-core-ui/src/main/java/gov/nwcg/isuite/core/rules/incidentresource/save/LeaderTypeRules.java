package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentResourceFilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class LeaderTypeRules extends AbstractIncidentResourceSaveRule implements IRule{
	public static final String _RULE_NAME="CHECK_LEADER_TYPE_CHANGE";

	public LeaderTypeRules(ApplicationContext ctx)
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
			

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
			}else{
				/*
				 * Run Rule Check
				 */
				if(runRuleCheck(dialogueVo) == _FAIL)
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		if(null != vo.getResourceVo().getLeaderType() 
				&& vo.getResourceVo().getLeaderType().intValue() < 99){

			ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");
			//Resource parentResource = resourceDao.getById(vo.getResourceVo().getParentResourceId(), ResourceImpl.class);

			Resource resource = 
				resourceDao.getResourceLeader(vo.getResourceVo().getParentResourceId()
											, Integer.valueOf(vo.getResourceVo().getLeaderType()));

			if(null != resource){
				Long voId = (null != vo.getResourceVo().getId() ? vo.getResourceVo().getId() : 0L);
						
				// check for matching id's
				if(resource.getId().compareTo(voId)==0){
					// ok
				}else{
					/*
					 * leaderType designation within the roster set
					 * is currently being changed
					 */
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
							
					String msgId="";
							
					if(vo.getResourceVo().getLeaderType().compareTo(new Integer(1)) == 0){
						// primary
						msgId="action.0085_p";
					}
							
					if(vo.getResourceVo().getLeaderType().compareTo(new Integer(2)) == 0){
						// secondary
						msgId="action.0085_s";
					}
							
					if(StringUtility.hasValue(msgId)){
						coaVo.setPromptVo(new PromptVo("text.incidentResources",msgId,null,PromptVo._YES | PromptVo._NO));
						
						dialogueVo.setCourseOfActionVo(coaVo);

						return _FAIL;
					}
				}
			}
					
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {

		dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
		dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
		/*
		 * Determine if we need clean up who's the leader(s)?
		 */
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa){
			if(getPromptResult(dialogueVo) == PromptVo._YES) {
			
				ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");
				
				Resource resource = resourceDao.getById(vo.getResourceVo().getId(), ResourceImpl.class);
				if(null != resource){
					
					Collection<Resource> prevLeaders= null;
					
					if(null != resource.getLeaderType() && resource.getLeaderType().intValue() > 0){
		
						if(resource.getLeaderType().intValue() == 1){
							// get previous old leader and build gridvo collection
							prevLeaders = resourceDao.getPreviousLeader(resource.getParentResourceId(), new Integer(1), resource.getId());	
							
							// update any existing primary's as 99
							if(null != resource.getParentResourceId() && resource.getParentResourceId() > 0)
								resourceDao.removeParentResourceLeader(resource.getParentResourceId(), new Integer(1), resource.getId());
						}else if(resource.getLeaderType().intValue() == 2){
							// get previous old secondary
							prevLeaders = resourceDao.getPreviousLeader(resource.getParentResourceId(), new Integer(2), resource.getId());	
							
							// update any existing secondary's as 99
							if(null != resource.getParentResourceId() && resource.getParentResourceId() > 0)
								resourceDao.removeParentResourceLeader(resource.getParentResourceId(), new Integer(2), resource.getId());
						}
						resourceDao.flushAndEvict(resource);
						
						if(CollectionUtility.hasValue(prevLeaders)){
							Collection<IncidentResourceGridVo> vos  = new ArrayList<IncidentResourceGridVo>();
							
							IncidentResourceFilter irFilter = new IncidentResourceFilterImpl();
							IncidentResourceDao incidentResourceDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
							
							for(Resource r : prevLeaders){
								IncidentResource ir = (IncidentResource)r.getIncidentResources().iterator().next();
								
								irFilter.setIncidentResourceId(ir.getId());
										
								Collection<IncidentResourceGridVo> irgVos = incidentResourceDao.getGrid2(irFilter, null);
								IncidentResourceGridVo irgVo = null;
								if(CollectionUtility.hasValue(irgVos)){
									irgVo = irgVos.iterator().next();
									vos.add(irgVo);
								}
							}

							if(CollectionUtility.hasValue(vos))
								dialogueVo.setResultObjectAlternate3(vos);
						}
					}
				}
			} else if (getPromptResult(dialogueVo) == PromptVo._NO) {
				ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");
				
				Resource resource = resourceDao.getById(vo.getResourceVo().getId(), ResourceImpl.class);
				if(null != resource){
					resource.setLeader(false);
					resource.setLeaderType(99);
					
					// save the entity
					resourceDao.save(resource);
					
					//flush
					resourceDao.flushAndEvict(resource);
				}
			}
		}
		
	}
	
}
