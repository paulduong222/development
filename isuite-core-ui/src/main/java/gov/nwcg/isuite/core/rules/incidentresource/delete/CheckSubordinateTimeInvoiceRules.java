package gov.nwcg.isuite.core.rules.incidentresource.delete;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class CheckSubordinateTimeInvoiceRules extends AbstractIncidentResourceDeleteRule implements IRule {
	public static final String _RULE_NAME="CHECK_SUBORDINATE_TIME_INVOICES";

	public CheckSubordinateTimeInvoiceRules(ApplicationContext ctx){
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
		 * B.R. 5.0004
		 * 
		 * 1.	The system must allow a user to delete a Resource that does not have 
		 *      critical data associated with it, regardless of how the critical data 
		 *      was acquired 
		 *      (e.g., from another User, during a prior now-completed assignment, 
		 *      or a system action). 
		 *      Critical data is defined as data that is part of the financial or historical record. 
		 *      (e.g., Time Postings, Invoices, Injury/Illness Recordings, etc.)
		 *           
		 */
		
		/*
		 * Check for subordinate invoices
		 */
		IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		TimePostDao timePostDao = (TimePostDao)context.getBean("timePostDao");

		Collection<Long> irChildIds = new ArrayList<Long>();
		
		for(Long irid : super.incidentResourceIds){
			IncidentResource irEntity =irDao.getById(irid, IncidentResourceImpl.class);
			Resource r = irEntity.getResource();
			if(r.getChildResources().size()>0)
				irChildIds.addAll(this.getChildIncidentResourceIds(r.getChildResources()));
			irDao.flushAndEvict(r);
			irDao.flushAndEvict(irEntity);
		}
		
		if(CollectionUtility.hasValue(irChildIds)){
			// check if any children have time invoices
			int cnt = timePostDao.getResourcesInvoicedTimePostCount(irChildIds);
			
			if(cnt > 0){
				String msg = "You cannot delete resource(s) that have subordinates with time invoices.";
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incidentResources"
												, "info.generic"
												, new String[]{msg}
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
