package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CostAccrualChangeRules2 extends AbstractIncidentResourceSaveRule implements IRule{
	public static final String _RULE_NAME=IncidentResourceSaveRuleFactory.RuleEnum.COST_ACCRUAL_CHANGE2.name();

	public CostAccrualChangeRules2(ApplicationContext ctx)
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
	
		/*
		 * Defect 4547 - 
			Related to defect 4158.  
			I change an accrual code, click the lock accrual code box and 
			then save.  This correctly changes the accrual code for all daily records, 
			as corrected in defect 4158.  
 
			However, if I now go back and uncheck the lock button and save, 
			the accrual code for the record is correctly adjusted back to the 
			system generated accrual code, but the daily records continue to show 
			the accrual code that previously existed when the record was locked.
		 */
		
		if(null != super.irEntity && null != super.vo){
			// check if locked accrual code changed to unlocked
			Boolean originalLocked = irEntity.getCostData().getAccrualLocked();
			Boolean currentLocked=vo.getCostDataVo().getAccrualLocked();

			if(BooleanUtility.isTrue(originalLocked)
					&& BooleanUtility.isFalse(currentLocked)){
				dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildAdditionalActionCoaVo(_RULE_NAME,true));
			}
			
		}
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
			
			if(null != super.irEntity){
				
				IncidentResource updatedEntity = super.irDao.getById(irEntity.getId(), IncidentResourceImpl.class);
				
				int costRecordCount=super.irDao.getUnlockedCostRecordCount(irEntity.getId());
				if(costRecordCount > 0){
					Long newAccrualId=(null != updatedEntity.getCostData().getAccrualCode() ? updatedEntity.getCostData().getAccrualCode().getId() : 0L);
					String sql = "";
					if(irDao.isOracleDialect()==true){
						sql = "UPDATE ISW_INC_RES_DAILY_COST "+
						  "SET ACCRUAL_CODE_ID = " + newAccrualId + " " +
						  "WHERE INCIDENT_RESOURCE_ID = " + irEntity.getId() + " " +
						  "AND (IS_LOCKED IS NULL OR IS_LOCKED = 0 ) ";
					}else{
						sql = "UPDATE ISW_INC_RES_DAILY_COST "+
						  "SET ACCRUAL_CODE_ID = " + newAccrualId + " " +
						  "WHERE INCIDENT_RESOURCE_ID = " + irEntity.getId() + " " +
						  "AND (IS_LOCKED IS NULL OR IS_LOCKED = false ) ";
					}
					Collection<String> updateSqls = new ArrayList<String>();
					updateSqls.add(sql);
					irDao.persistSqls(updateSqls);
				}
			}
		}
		
	}

}
