package gov.nwcg.isuite.core.rules.incidentresourceother.save;

import gov.nwcg.isuite.core.persistence.IncidentResourceOtherDao;
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

public class CostAccrualChangeRules extends AbstractSaveIRORule implements IRule{
	public static final String _RULE_NAME=SaveIRORuleFactory.RuleEnum.COST_ACCRUAL_CHANGE.name();

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
		if(null != super.iroEntity && null != super.vo){
			IncidentResourceOtherDao iroDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
			
			Long origAccrualId=iroDao.getAccrualCodeId(super.iroEntity.getId());
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

			IncidentResourceOtherDao iroDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
			
			if(null != super.iroEntity){
				int costRecordCount=iroDao.getUnlockedCostRecordCount(iroEntity.getId());
				if(costRecordCount > 0){
					Long newAccrualId=(null != vo.getCostDataVo().getAccrualCodeVo() ? vo.getCostDataVo().getAccrualCodeVo().getId() : 0L);
					String sql = "";
					if(iroDao.isOracleDialect()==true){
						sql = "UPDATE ISW_INC_RES_DAILY_COST "+
						  "SET ACCRUAL_CODE_ID = " + newAccrualId + " " +
						  "WHERE INCIDENT_RESOURCE_OTHER_ID = " + iroEntity.getId() + " " +
						  "AND (IS_LOCKED IS NULL OR IS_LOCKED = 0 )";
					}else{
						sql = "UPDATE ISW_INC_RES_DAILY_COST "+
						  "SET ACCRUAL_CODE_ID = " + newAccrualId + " " +
						  "WHERE INCIDENT_RESOURCE_OTHER_ID = " + iroEntity.getId() + " " +
						  "AND (IS_LOCKED IS NULL OR IS_LOCKED = false )";
					}
					Collection<String> updateSqls = new ArrayList<String>();
					updateSqls.add(sql);
					iroDao.persistSqls(updateSqls);
				}
			}
		}
		
	}

}
