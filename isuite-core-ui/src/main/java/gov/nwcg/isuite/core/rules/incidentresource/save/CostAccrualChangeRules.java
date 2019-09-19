package gov.nwcg.isuite.core.rules.incidentresource.save;

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

public class CostAccrualChangeRules extends AbstractIncidentResourceSaveRule implements IRule{
	public static final String _RULE_NAME=IncidentResourceSaveRuleFactory.RuleEnum.COST_ACCRUAL_CHANGE.name();

	public CostAccrualChangeRules(ApplicationContext ctx)
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
		 * Defect 4158 - Changing a resources Accrual code and locking should change unlocked 
		 * 				 Cost record accrual code to match the new code
		 * 
		 * 			When an accrual code is changed and locked for a resource, 
		 * 			on SAVE the accrual code should update to all unlocked cost records.
		 */
		if(null != super.irEntity && null != super.vo){
			// check if accrual code changed
			Long origAccrualId=(null != irEntity.getCostData().getAccrualCode() ? irEntity.getCostData().getAccrualCode().getId() : 0L);
			Long newAccrualId=(null != vo.getCostDataVo().getAccrualCodeVo() ? vo.getCostDataVo().getAccrualCodeVo().getId() : 0L);
			
			if(LongUtility.hasValue(newAccrualId)){
				if(newAccrualId.compareTo(origAccrualId)!=0){
					if(BooleanUtility.isTrue(vo.getCostDataVo().getAccrualLocked())){
						dialogueVo.getProcessedCourseOfActionVos()
							.add(super.buildAdditionalActionCoaVo(_RULE_NAME,true));
					}
				}
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
				int costRecordCount=super.irDao.getUnlockedCostRecordCount(irEntity.getId());
				if(costRecordCount > 0){
					Long newAccrualId=(null != vo.getCostDataVo().getAccrualCodeVo() ? vo.getCostDataVo().getAccrualCodeVo().getId() : 0L);
					String sql = "";
					if(irDao.isOracleDialect()==true){
						sql = "UPDATE ISW_INC_RES_DAILY_COST "+
						  "SET ACCRUAL_CODE_ID = " + newAccrualId + " " +
						  "WHERE INCIDENT_RESOURCE_ID = " + irEntity.getId() + " " +
						  "AND (IS_LOCKED IS NULL OR IS_LOCKED = 0 )";
					}else{
						sql = "UPDATE ISW_INC_RES_DAILY_COST "+
						  "SET ACCRUAL_CODE_ID = " + newAccrualId + " " +
						  "WHERE INCIDENT_RESOURCE_ID = " + irEntity.getId() + " " +
						  "AND (IS_LOCKED IS NULL OR IS_LOCKED = false )";
					}
					Collection<String> updateSqls = new ArrayList<String>();
					updateSqls.add(sql);
					irDao.persistSqls(updateSqls);
				}
			}
		}
		
	}

}
