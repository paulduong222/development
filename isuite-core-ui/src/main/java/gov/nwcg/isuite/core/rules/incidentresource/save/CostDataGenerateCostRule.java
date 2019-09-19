package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
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
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CostDataGenerateCostRule extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME=IncidentResourceSaveRuleFactory.RuleEnum.COST_DATA_GENERATE_COSTS.name();
	
	public CostDataGenerateCostRule(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try {
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
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
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));
				
		} catch(Exception e) {
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
		
		/*
		 * If resource is generate cost flag is off, check the rules and verify that the 
		 * flag can be turned off
		 */

		/*
		 * only need to check if we already have a resource id
		 */
		if(null != vo.getResourceVo() && LongUtility.hasValue(vo.getResourceVo().getId())){
			
			ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");
			Resource resource = resourceDao.getById(vo.getResourceVo().getId(), ResourceImpl.class);
			super.tpDao = (TimePostDao)context.getBean("timePostDao");

			/*
			 * if generate costs flag is false
			 */
			if(null != vo.getCostDataVo() && BooleanUtility.isFalse(vo.getCostDataVo().getGenerateCosts())) {
				// check if resource has time postings
				Collection<AssignmentTimePost> timeposts=tpDao.getByIncidentResourceId(vo.getId());
				if(CollectionUtility.hasValue(timeposts)){
					String msg="You cannot set the Generate Costs checkbox to unchecked since this Resource has time postings.  "+
								"You must re-check the Generate Costs checkbox before saving this Resource.";
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.incidentResources"
													, "info.generic"
													, new String[]{msg}
													, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
									
					dialogueVo.setCourseOfActionVo(coaVo);
					return _FAIL;
				}
			}
			
			/*
			 * does the resource have children?
			 */
			if(null != resource.getChildResources() && resource.getChildResources().size() > 0){
				if(null != vo.getCostDataVo() && BooleanUtility.isFalse(vo.getCostDataVo().getGenerateCosts())) {

					
					int childTimePostCount=0;
					this.loadChildTimePostCount(resource, childTimePostCount);
					
					if(childTimePostCount > 0){
						String msg="You cannot set the Generate Costs checkbox to unchecked since this Resource has subordinates with time postings.";
						
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_MSG_FINISHED);
						coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						coaVo.setMessageVo(new MessageVo("text.incidentResources"
														, "info.generic"
														, new String[]{msg}
														, MessageTypeEnum.CRITICAL));
						coaVo.setIsDialogueEnding(Boolean.TRUE);
										
						dialogueVo.setCourseOfActionVo(coaVo);
						return _FAIL;
					}
				}
				
			}
			resourceDao.flushAndEvict(resource);

		}	
		
		return _OK;
	}

	private void loadChildTimePostCount(Resource parent, int cnt) {
		try{
			Long irid = parent.getIncidentResources().iterator().next().getId();
			
			int count = tpDao.getResourceTimePostCount(irid);
			
			cnt+=count;
			
			if(null != parent.getChildResources() && parent.getChildResources().size() > 0){
				for(Resource child : parent.getChildResources()){
					this.loadChildTimePostCount(child, cnt);
				}
			}
			
		}catch(Exception e){
			//smother
		}
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}
}
