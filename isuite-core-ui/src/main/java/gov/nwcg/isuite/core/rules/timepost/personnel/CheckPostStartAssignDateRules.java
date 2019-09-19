package gov.nwcg.isuite.core.rules.timepost.personnel;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckPostStartAssignDateRules extends AbstractPersonnelRule implements IRule{
	public static final String _RULE_NAME=TimePostPersonnelRuleFactory.RuleEnum.CHECK_POST_START_ASSIGN_DATE.name();

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
		/*
		 * If timepost date is before resource assign date (cost module),
		 * set resource assign date to time post date
		 */
		IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		IncidentResource irEntity = irDao.getByAssignmentTimeId(vo.getAssignmentTimeId());
		if(null != irEntity){
			Date assignDate=irEntity.getCostData().getAssignDate();
			if(null!=assignDate)
				assignDate=DateUtil.addMilitaryTimeToDate(assignDate, "2359");
			Date postDate=vo.getPostStartDate();
			if(null!=postDate)
				postDate=DateUtil.addMilitaryTimeToDate(postDate, "2359");
			else
				return;
				
			if(null==assignDate && null != postDate){
				irEntity.getCostData().setAssignDate(postDate);
				irDao.save(irEntity);
				irDao.flushAndEvict(irEntity);
			}else if(null != assignDate && null != postDate){
				if(postDate.before(assignDate)){
					irEntity.getCostData().setAssignDate(postDate);
					irDao.save(irEntity);
					irDao.flushAndEvict(irEntity);
					//dialogueVo.setResultObjectAlternate4(postDate);
				}
				
				// if this resource is a subordinate and compare with parent assigndate
				if(LongUtility.hasValue(irEntity.getResource().getParentResourceId())){
					Resource parent=irEntity.getResource().getParentResource();
					if(null != parent){
						IncidentResource irParent = parent.getIncidentResources().iterator().next();
						Date parentAssignDate=irParent.getCostData().getAssignDate();
						if(null != parentAssignDate){
							parentAssignDate=DateUtil.addMilitaryTimeToDate(parentAssignDate, "2359");
							if(postDate.before(parentAssignDate)){
								irParent.getCostData().setAssignDate(postDate);
								irDao.save(irParent);
								irDao.flushAndEvict(irParent);
							}
						}
					}
				}
			}
		}
		
	}

}
