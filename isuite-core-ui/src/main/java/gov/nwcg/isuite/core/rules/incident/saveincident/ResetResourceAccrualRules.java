package gov.nwcg.isuite.core.rules.incident.saveincident;

import gov.nwcg.isuite.core.cost.utilities.DefaultAccrualCodeHandler;
import gov.nwcg.isuite.core.domain.AccrualCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.AccrualCodeImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.ResourceDataForAccrualVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ResetResourceAccrualRules extends AbstractIncidentSaveRule implements IRule{
	public static final String _RULE_NAME=SaveIncidentRuleFactory.RuleEnum.RESET_RES_ACCRUALS.name();

	public ResetResourceAccrualRules(ApplicationContext ctx)
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
			if(runRuleCheck(dialogueVo)==_FAIL){
				dialogueVo.getProcessedCourseOfActionVos().add(super.buildAdditionalActionCoaVo(_RULE_NAME,true));
				return _OK;
			}
				
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		

		/*
		 * If the user changes the incident jurisdiction,
		 * reset accrual code for all resources
		 */
		if( null != super.incidentEntity) {
			Long incId = incidentEntity.getId();
			Long agencyId = incidentEntity.getAgencyId();
			
			if(null != super.vo 
					&& null != super.vo.getAgencyVo()){
				Long newAgencyId = super.vo.getAgencyVo().getId();
				
				if(agencyId.compareTo(newAgencyId) != 0){
					// jurisdiction changed
					dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildAdditionalActionCoaVo(_RULE_NAME,true));
					
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
			if( null != super.incidentEntity) {
				Long incId = incidentEntity.getId();
				
				// get all resources for this incident
				IncidentResourceDao irdao = (IncidentResourceDao)context.getBean("incidentResourceDao");
				
				Collection<ResourceDataForAccrualVo> resourceData = irdao.getResourceDataForAccrual(incId);
				
				
				if(CollectionUtility.hasValue(resourceData)){
					
					Collection<String> updateSqls = new ArrayList<String>();
					
					for(ResourceDataForAccrualVo rd : resourceData){
						
						if(LongUtility.hasValue(rd.getCostDataId())
								&& BooleanUtility.isFalse(rd.getIsAccrualLocked())){
							
							DefaultAccrualCodeHandler acHandler = new DefaultAccrualCodeHandler(super.context);
							acHandler.incidentAgency=(StringUtility.hasValue(rd.getIncidentAgency()) ? rd.getIncidentAgency() : "");
							acHandler.incidentState=(StringUtility.hasValue(rd.getIncidentState()) ? rd.getIncidentState() : "");
							acHandler.resourceAgency=(StringUtility.hasValue(rd.getResourceAgencyCode()) ? rd.getResourceAgencyCode() : "");
							acHandler.resourceAgencyIsState=rd.getIsResourceAgencyState();
							acHandler.resourceEmployment=(StringUtility.hasValue(rd.getResourceEmploymentType()) ? rd.getResourceEmploymentType() : "");
							acHandler.resourceItemCode=(StringUtility.hasValue(rd.getResourceItemCode()) ? rd.getResourceItemCode() : "");
							acHandler.resourceItemCodeCategory=(StringUtility.hasValue(rd.getResourceItemCodeCategory()) ? rd.getResourceItemCodeCategory() : "");
							acHandler.resourcePaymentAgency=(StringUtility.hasValue(rd.getResourcePaymentAgency()) ? rd.getResourcePaymentAgency() : "");
							acHandler.resourceUnitState="";
							if(StringUtility.hasValue(rd.getResourceUnitCode())){
								acHandler.resourceUnitState=rd.getResourceUnitCode().substring(0,2);
							}

							Long acId = acHandler.getDefaultAccrualCodeIdVoForResource();
							
							if(LongUtility.hasValue(acId)){
								String sql = "UPDATE ISW_COST_DATA SET ACCRUAL_CODE_ID = " + acId + " " +
											" where id = " + rd.getCostDataId();
								
								updateSqls.add(sql);
							
								if(rd.getResourceCostCount().intValue()>0){
									if(irdao.isOracleDialect()==true){
										sql = "UPDATE ISW_INC_RES_DAILY_COST "+
										  "SET ACCRUAL_CODE_ID = " + acId + " " +
										  "WHERE INCIDENT_RESOURCE_ID = " + rd.getIncidentResourceId() + " " +
										  "AND (IS_LOCKED IS NULL OR IS_LOCKED = 0 )";
									}else{
										sql = "UPDATE ISW_INC_RES_DAILY_COST "+
										  "SET ACCRUAL_CODE_ID = " + acId + " " +
										  "WHERE INCIDENT_RESOURCE_ID = " + rd.getIncidentResourceId() + " " +
										  "AND (IS_LOCKED IS NULL OR IS_LOCKED = false )";
									}
									updateSqls.add(sql);
								}
							}
						}
						
					}

					try{
						if(CollectionUtility.hasValue(updateSqls))
							irdao.persistSqls(updateSqls);
					}catch(Exception e){
						//System.out.println(e.getMessage());
						throw e;
					}
				}

				
			}
			
		}
		
	}

}
