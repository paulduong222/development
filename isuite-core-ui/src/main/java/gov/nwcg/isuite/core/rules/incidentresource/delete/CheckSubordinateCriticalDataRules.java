package gov.nwcg.isuite.core.rules.incidentresource.delete;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckSubordinateCriticalDataRules extends AbstractIncidentResourceDeleteRule implements IRule {
	public static final String _RULE_NAME=IncidentResourceDeleteRuleFactory.RuleEnum.CHECK_SUBORDINATE_CRITICAL_DATA.name();

	public CheckSubordinateCriticalDataRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
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
				
			
		} catch(Exception e){
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
		 * C.R 146
		 * 
		 *  If either the primary resource or any of the resources rostered 
		 *  to that primary resource have critical data, 
		 *  the system must not delete either the primary resource 
		 *  or the subordinate resources. The system must display the following message:
		 *  
		 *  Message 0335 - There are resources in the roster that 
		 *  have critical data. You cannot delete the roster.
		 */
		TimePostDao timePostDao = (TimePostDao)context.getBean("timePostDao");
		TimeAssignAdjustDao taaDao = (TimeAssignAdjustDao)context.getBean("timeAssignAdjustDao");
		IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		
		Collection<Long> irChildIds = new ArrayList<Long>();
		
		for(Long irid : super.incidentResourceIds){
			IncidentResource irEntity =irDao.getById(irid, IncidentResourceImpl.class);
			Resource r = irEntity.getResource();
			if(r.getChildResources().size()>0)
				irChildIds.addAll(this.getChildIncidentResourceIds(r.getChildResources()));
		}
		
		if(CollectionUtility.hasValue(irChildIds)){
			// check if any children have time postings
			int cnt = timePostDao.getResourcesTimePostCount(irChildIds);
			
			// check if any children have adjustments
			int cnt2 = taaDao.getResourcesTimeAdjustmentCount(irChildIds);
			
			int totalCnt=cnt + cnt2;
			
			if(totalCnt > 0){
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incidentResources"
												, "error.0335.cr146" 
												, null
												, MessageTypeEnum.CRITICAL));

				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
		}
		
		
		return _OK;
	}
	
	private Collection<Long> getChildIncidentResourceIds(Collection<Resource> childs) throws Exception{
		Collection<Long> rtnIds = new ArrayList<Long>();

		for(Resource c : childs){
			if(c.getIncidentResources().size()>0){
				IncidentResource ir = (IncidentResource)c.getIncidentResources().iterator().next();
				if(null != ir){
					rtnIds.add(ir.getId());
				}
			}
			
			if(CollectionUtility.hasValue(c.getChildResources())){
				rtnIds.addAll(getChildIncidentResourceIds(c.getChildResources()));
			}
		}
		
		return rtnIds;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
