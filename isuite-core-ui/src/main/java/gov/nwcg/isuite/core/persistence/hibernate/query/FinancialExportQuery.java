package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.util.Collection;

public class FinancialExportQuery {
	
	public static String getAccountCodeSummaryTypes(Long extractId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select distinct (account_code || fiscal_year) as accountingCode, ")
		.append("(select sum(total_amount) from isw_cost_accrual_ext_rsc r where r.account_code = rsc.account_code and r.fiscal_year = rsc.fiscal_year and r.cost_accrual_extract_id = rsc.cost_accrual_extract_id) as subTotalAmount, ")
		.append("(select sum(total_amount - change_amount) from isw_cost_accrual_ext_rsc r where r.account_code = rsc.account_code and r.fiscal_year = rsc.fiscal_year and r.cost_accrual_extract_id = rsc.cost_accrual_extract_id) as subPrevAmount, ")
		.append("(select sum(change_amount) from isw_cost_accrual_ext_rsc r where r.account_code = rsc.account_code and r.fiscal_year = rsc.fiscal_year and r.cost_accrual_extract_id = rsc.cost_accrual_extract_id) as subChangeAmount ")
		.append("from isw_cost_accrual_ext_rsc rsc ")
		.append("where cost_accrual_extract_id = " + extractId + " ")
		.append("order by accountingCode")
		;
		
		return sql.toString();
	}
	
	public static String getRCLineSummaryTypes(Long extractId, String accountCodeYr) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select distinct rc_line_number as rcLineNumber, ")
		.append("ac.description as rcLineDescription, ")
		.append("(select sum(total_amount) from isw_cost_accrual_ext_rsc r where r.account_code = rsc.account_code and r.fiscal_year = rsc.fiscal_year and r.cost_accrual_extract_id = rsc.cost_accrual_extract_id and r.cost_accrual_code = rsc.cost_accrual_code) as rcLineTotalAmount, ")
		.append("(select sum(total_amount - change_amount) from isw_cost_accrual_ext_rsc r where r.account_code = rsc.account_code and r.fiscal_year = rsc.fiscal_year and r.cost_accrual_extract_id = rsc.cost_accrual_extract_id and r.cost_accrual_code = rsc.cost_accrual_code) as rcLinePrevAmount, ")
		.append("(select sum(change_amount) from isw_cost_accrual_ext_rsc r where r.account_code = rsc.account_code and r.fiscal_year = rsc.fiscal_year and r.cost_accrual_extract_id = rsc.cost_accrual_extract_id and r.cost_accrual_code = rsc.cost_accrual_code) as rcLineChangeAmount, ")
		.append("ac.code as accrualCode ")
		.append("from iswl_accrual_code ac, ")
		.append("isw_cost_accrual_ext_rsc rsc ")
		.append("where RSC.COST_ACCRUAL_CODE = AC.CODE ")
		.append("and rsc.cost_accrual_extract_id = " + extractId + " ")
		.append("and (account_code || fiscal_year) = '" + accountCodeYr + "' ")
		.append("order by rc_line_number")
		;
		
		return sql.toString();
	}
	
	public static String getAccrualDetailTypes(Long extractId, String accountCodeYr, String accrualCode,  Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ")
		.append("case ")
		.append("when r.is_person = " + (isOracle ? 1 : true) + " then r.last_name || ', ' || r.first_name ")
		.append("else ")
		.append("r.resource_name ")
		.append("end as name, ")
		.append("a.request_number as requestNumber, ")
		.append("k.code as kindCode, ")
		.append("case ")
		.append("when rsc.draw_down = 'Y' then 'ADDRAW' ")
		.append("else ")
		.append("case ")
		.append("when o.unit_code = 'UNK' then o.unit_code ")
		.append("else ")
		.append("substr(o.unit_code, 0, 2) || substr(o.unit_code, 4) ")
		.append("end ")
		.append("end as HomeUnitCode, ")
		.append("rsc.total_amount as totalAmount, ")
		.append("(rsc.total_amount - rsc.change_amount) as prevAmount, ")
		.append("rsc.change_amount as changeAmount ")
		.append("from isw_cost_accrual_ext_rsc rsc, ")
		.append("isw_incident_resource ir, ")
		.append("isw_resource r, ")
		.append("isw_organization o, ")
		.append("isw_work_period wp, ")
		.append("isw_work_period_assignment wpa, ")
		.append("isw_assignment a, ")
		.append("iswl_kind k ")
		.append("where rsc.incident_resource_id = ir.id ")
		.append("and ir.resource_id = r.id  ")
		.append("and r.organization_id = o.id ")
		.append("and ir.id = wp.incident_resource_id ")
		.append("and wp.id = wpa.work_period_id ")
		.append("and wpa.assignment_id = a.id ")
		.append("and a.kind_id = k.id ")
		.append("and a.end_date is null ")
		.append("and rsc.cost_accrual_extract_id = " + extractId + " ")
		.append("and cost_accrual_code = '" + accrualCode + "' ")
		.append("and (account_code || fiscal_year) = '" + accountCodeYr + "' ")
		.append("order by name")
		;
		
		return sql.toString();
	}
	
	public static String getUpdateExportedAccrualExtracts(Collection<Long> incidentIds) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("update isw_cost_accrual_extract ")
		.append("set is_exported = 'Y' ")
		.append("where ")
		.append("incident_id in ( :incidentIds ) ")
		.append("and is_exported = 'N'" )
		.append("and finalized_date is not null ")
		;
		
		return sql.toString();
		
	}
	
	public static String getFinancialExportResourceData(Long timeInvoiceId, Boolean isOracle){
		StringBuffer sql = new StringBuffer();

		sql.append("select a.request_number as requestNumber, ")
		.append(" case ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name")
		.append(" else r.last_name || ', ' || r.first_name")
		.append(" end")
		.append(" as resourceName, ")
		.append(" at.employment_Type as employmentType ")
		.append(" from isw_assignment a ")
        .append(" , isw_work_period_assignment wpa ")
        .append(" , isw_work_period wp ")
        .append(" , isw_incident_resource ir ")
        .append(" , isw_resource r ")
        .append(" , isw_assignment_time at ")
        .append(" , isw_time_invoice ti ")
        .append(" , isw_resource_invoice ri ")
        .append(" where ti.id = " + timeInvoiceId )
        .append(" and ri.time_invoice_id = ti.id ")
        .append(" and r.id = ri.resource_id ")
        .append(" and ir.resource_id = r.id ")
        .append(" and wp.incident_resource_id = ir.id ")
		.append(" and wp.incident_resource_id = ir.id ")
		.append(" and wpa.work_period_id = wp.id ")
		.append(" and a.id = wpa.assignment_id " )
		.append(" and at.assignment_id = a.id ")
		;
		
		return sql.toString();
	}
	
	

}
