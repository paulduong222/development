package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlTable;

public class TableHandlerRuleFactory {

	private enum TableRuleEnum {
		IswIncidentGroup("isw_incident_group",IncidentGroupTableHandler.class,true,true,true)
		,IswIncidentGroupIncident("isw_incident_group_incident",IncidentGroupIncidentTableHandler.class,true,false,false)
		,IswIncident("isw_incident",IncidentTableHandler.class,false,true,true)
		, IswIncidentContractor("isw_incident_contractor", IncidentContractorTableHandler.class, true,false,false)
		, IswIapPositionItemCode("isw_iap_position_item_code", IapPositionItemCodeTableHandler.class, true,false,false)
		, IswQuestion("isw_question", QuestionTableHandler.class, true,false,false)
		, IswIncidentQuestion("isw_incident_question", IncidentQuestionTableHandler.class, true,false,false)
		, IswIncidentGroupQuestion("isw_incident_group_question", IncidentGroupQuestionTableHandler.class, true,false,false)
		, IswIncidentGroupPrefs("isw_incident_group_prefs", IncidentGroupPrefsTableHandler.class, true,false,false)
		, IswIncidentCostRate("isw_inccost_rate", IncidentCostRateTableHandler.class, true,false,false)
		, IswIncidentCostRateKind("isw_inccost_rate_kind", IncidentCostRateKindTableHandler.class, true,false,false)
		, IswIncidentCostRateState("isw_inccost_rate_state", IncidentCostRateStateTableHandler.class, true,false,false)
		, IswIncidentCostRateStateKind("isw_inccost_rate_state_kind", IncidentCostRateStateKindTableHandler.class, true,false,false)
		, IswIncidentCostRateOvhd("isw_inccost_rate_ovhd", IncidentCostRateOvhdTableHandler.class, true,false,false)
		, IswIapForm202("isw_iap_form_202", IapForm202TableHandler.class, false,true,true)
		, IswIapForm205("isw_iap_form_205", IapForm205TableHandler.class, false,true,true)
		, IswIapBranch("isw_iap_branch", IapBranchTableHandler.class, false,true,true)
		, IswWorkPeriodAssignment("isw_work_period_assignment", WorkPeriodAssignmentTableHandler.class, true,false,false)
		, IswAssignmentTimePostInvoice("isw_assign_time_post_invoice", AssignmentTimePostInvoiceTableHandler.class, true,false,false)
		, IswTimeAssignAdjustInvoice("isw_time_assign_adj_invoice", TimeAssignAdjustInvoiceTableHandler.class, true,false,false)
		, IswResourceInvoice("isw_resource_invoice", ResourceInvoiceTableHandler.class, true,false,false)
		, IswContrPayinfoRate("isw_contr_payinfo_rate", ContrPayInfoRateTableHandler.class, true,false,false)
		, IswCostAccrualExtract("isw_cost_accrual_extract", CostAccrualExtractTableHandler.class, false,true,false)
		, IswTrainingSetFuelType("isw_training_set_fuel_type", TrainingSetFuelTypeTableHandler.class, true,false,false)
		, IswRscTrainingFuelType("isw_rsc_training_fuel_type", RscTrainingFuelTypeTableHandler.class, true,false,false)
		, IswIncidentFuelType("isw_incident_fuel_type", IncidentFuelTypeTableHandler.class, false,true,false)
		, IswlPriorityProgram("iswl_priority_program", PriorityProgramTableHandler.class, false,true,false)
		;
		
		private String tableName="";
		private Class handler;
		private boolean postInsertRule=false;
		private boolean postUpdateRule=false;
		private boolean preProcessRule=false;
		
		TableRuleEnum(String tablename,Class cls
							, boolean preProcessRle
							, boolean postInsertRle
							, boolean postUpdateRle){
			this.tableName=tablename;
			this.handler=cls;
			this.preProcessRule=preProcessRle;
			this.postInsertRule=postInsertRle;
			this.postUpdateRule=postUpdateRle;
		}

		public boolean hasPreProcessRule(){
			return this.preProcessRule;
		}

		public boolean hasPostInsertRule(){
			return this.postInsertRule;
		}
		
		public boolean hasPostUpdateRule(){
			return this.postUpdateRule;
		}
		
	}	
	
	public TableHandlerRuleFactory(){
		
	}

	private TableRuleEnum getByName(String tableName){
		for(TableRuleEnum tre : TableRuleEnum.values()){
			if(tre.tableName.equals(tableName)){
				return tre;
			}
		}
		
		return null;
	}
	
	public Boolean hasPostInsertRule(XmlTable xt){
		TableRuleEnum tre =this.getByName(xt.tableName);
		if(null != tre) 
			return tre.hasPostInsertRule();
		
		return false;
	}

	public Boolean hasPostUpdateRule(XmlTable xt){
		TableRuleEnum tre =this.getByName(xt.tableName);
		if(null != tre) 
			return tre.hasPostUpdateRule();
		
		return false;
	}

	public Boolean hasPreProcessRule(XmlTable xt){
		TableRuleEnum tre =this.getByName(xt.tableName);
		if(null != tre) {
			return tre.hasPreProcessRule();
		}
		
		return false;
	}
	
	/**
	 * @param dao
	 * @param xmlObject
	 * @param xt
	 * @return
	 * @throws Exception
	 */
	public TableHandler getTableHandler(DataTransferDao dao, Object xmlObject, XmlTable xt, DialogueVo dvo) throws Exception {
		TableHandler tableHandler=null;
		
		TableRuleEnum tre =this.getByName(xt.tableName);
		if(null != tre){
			tableHandler=(TableHandler)tre.handler.newInstance();
			tableHandler.setDao(dao);
			tableHandler.setXmlObject(xmlObject);
			tableHandler.setXmlTable(xt);
			tableHandler.setDialogueVo(dvo);
		}
		
		return tableHandler;
	}
	
}
