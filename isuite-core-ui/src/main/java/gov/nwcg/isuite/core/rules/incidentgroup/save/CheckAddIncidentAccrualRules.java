package gov.nwcg.isuite.core.rules.incidentgroup.save;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.CostAccrualExtractDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckAddIncidentAccrualRules extends AbstractIncidentGroupSaveRule implements IRule{
	private Collection<Long> incidentIds = new ArrayList<Long>();
	
	public CheckAddIncidentAccrualRules(ApplicationContext ctx, String rname)
	{
		super(ctx,rname);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _FAIL) 
				return _FAIL;
			
			dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(ruleName, true));
			
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
		
		if(hasNewIncidents()){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(super.ruleName+"SYNCGROUPACCRUAL");
			coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			coaVo.setStoredObject(this.incidentIds);
			dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
		}
		
		return _OK;
	}

	
	private Boolean hasNewIncidents() {
		if(null != entity){
			for(IncidentVo ivo : newVo.getIncidentVos()){
				boolean bFound = false;
				
				for(Incident i : entity.getIncidents()){
					if(ivo.getId().compareTo(i.getId())==0)
						bFound=true;
				}
				
				if(bFound==false){
					this.incidentIds.add(ivo.getId());
				}
			}
		}else{
			for(IncidentVo ivo : newVo.getIncidentVos()){
				this.incidentIds.add(ivo.getId());
			}
		}

		if(CollectionUtility.hasValue(incidentIds))
			return true;
		else
			return false;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName+"SYNCGROUPACCRUAL");
		if(coaVo != null && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			Collection<Long> newIncidentIds = (Collection<Long>)coaVo.getStoredObject();
			Long incidentGroupId = super.entity.getId();
		
			// defect 3524
			/*
			 *  When the user adds an Incident to an Incident Group that has extracts, 
			 *  those extracts should display in the grid. 
			 *  If two incidents have extracts that were created on the same date, 
			 *  the totals for those extracts should be combined into one line for that date 
			 *  (even if they were finalized). 
			 *  Donna indicated that ASC should already have the finalized extracts, and even if they are combined and they didn't have the finalized extracts, it shouldn't matter, because everything is based on Accounting Codes.)
			 */
			
			
			// update all finalized accruals in the newList of incidents
			// isw_cost_accrual_extract.IS_ADDED_TO_GROUP
			for(Long i : newIncidentIds){
				CostAccrualExtractDao caeDao = (CostAccrualExtractDao)context.getBean("costAccrualExtractDao");
				caeDao.updateAllFinAccrualsAsSingle(i);
			}
		}

	}

}
	
