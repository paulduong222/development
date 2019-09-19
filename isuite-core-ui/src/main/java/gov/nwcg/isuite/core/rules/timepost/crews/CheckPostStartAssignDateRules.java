package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckPostStartAssignDateRules extends AbstractCrewRule implements IRule{
	public static final String _RULE_NAME=TimePostCrewRuleFactory.RuleEnum.CHECK_POST_START_ASSIGN_DATE.name();

	public CheckPostStartAssignDateRules(ApplicationContext ctx)
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
				
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// set crew assign date if applicable
		Date postDate = super.vo.getPostStartDate();
		if(null != postDate){
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			
			postDate=DateUtil.addMilitaryTimeToDate(postDate, "2359");

			if(CollectionUtility.hasValue(super.entities)){
				for(AssignmentTime at : super.entities){
					// look up parent ir
					IncidentResource child=irDao.getByAssignmentTimeId(at.getId());
					if(null != child){
						if(LongUtility.hasValue(child.getResource().getParentResourceId())){
							Resource parent=child.getResource().getParentResource();
							
							//update parent
							if(null != parent){
								IncidentResource irParent = parent.getIncidentResources().iterator().next();
								Date parentAssignDate=irParent.getCostData().getAssignDate();

								if(null==parentAssignDate){
									irParent.getCostData().setAssignDate(postDate);
									
									//dan 1/28/2014 - also set parent's use actuals only = false since child has actual
									irParent.getCostData().setUseAccrualsOnly(false);
									
									irDao.save(irParent);
									irDao.flushAndEvict(irParent);
								}
								
								if(null != parentAssignDate){
									parentAssignDate=DateUtil.addMilitaryTimeToDate(parentAssignDate, "2359");
									if(postDate.before(parentAssignDate)){
										irParent.getCostData().setAssignDate(postDate);
										irDao.save(irParent);
										irDao.flushAndEvict(irParent);
									}
								}
							}
							
							// update child
							Date assignDate=child.getCostData().getAssignDate();
							if(null != assignDate){
								assignDate=DateUtil.addMilitaryTimeToDate(assignDate, "2359");
							}
							
							if(null==assignDate && null != postDate){
								child.getCostData().setAssignDate(postDate);
								irDao.save(child);
								irDao.flushAndEvict(child);
							}else if(null != assignDate && null != postDate){
								if(postDate.before(assignDate)){
									child.getCostData().setAssignDate(postDate);
									irDao.save(child);
									irDao.flushAndEvict(child);
								}
							}
						}
						
					}
					
				}
				
			} // end collectionUtil.hasValue(entities)
		}
		
	}

}
