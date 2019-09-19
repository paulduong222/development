package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;

public class CostAccrualExtractQuery {

	public static String getCostAccrualExtract(Long incidentId, Long incidentGroupId, Date date, boolean isOracleDialect) throws Exception {
		StringBuffer sql = new StringBuffer();
		return sql.toString();
	}

	/*
	public static String getCreateCostAccrualExtractResourcesQuery(Long extractId, Long incidentId, String extractDateString, String fiscalDate, String fiscalYear, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("insert into isw_cost_accrual_ext_rsc(" +
				"id, cost_accrual_extract_id, incident_resource_id" +
				", total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, draw_down) ")
		   .append("select ")
		   .append(" " + (isOracle ? "SEQ_COST_ACCRUAL_EXT_RSC.nextVal" : "nextVal('SEQ_COST_ACCRUAL_EXT_RSC') " ) + " ")
		   .append(" , " + extractId + " ")
		   .append(", tbl1.incidentResourceId ")
		   .append(", tbl1.totalCost ")
		   .append(", tbl1.changeAmount ")
		   .append(", tbl1.costAccrualCode ")
		   .append(", tbl1.accountCode ")
		   .append(", " + fiscalYear )
		   .append(", 'N' ")
		   .append(" from ")
		   .append(" (select " )
		   .append(" distinct(ir.id) as incidentResourceId ")
		   .append(",  ( ") 
		   .append("     case ")
		   .append("		when (select count(id) from isw_inc_res_daily_cost ")
		   .append("              where incident_resource_id = ir.id ")
		   .append("              and incident_account_code_id = iiac.id ")
		   .append("              and accrual_code_id = ac.id ")
		   .append("              and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("              and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("              ) > 0 then ( ")
		   .append("                select sum(total_cost_amount) ")
		   .append("                from isw_inc_res_daily_cost ")
		   .append("			    where incident_resource_id = ir.id ")
		   .append("                and incident_account_code_id = iiac.id ")
		   .append("                and accrual_code_id = ac.id ")
		   .append("                and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("                and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("				) ")
		   .append("        else 0 ")
		   .append("        end ")
		   .append("   ) as totalCost ")
		   .append(",  ( ") 
		   .append("     case ")
		   .append("		when (select count(id) from isw_inc_res_daily_cost ")
		   .append("              where incident_resource_id = ir.id ")
		   .append("              and incident_account_code_id = iiac.id ")
		   .append("              and accrual_code_id = ac.id ")
		   .append("              and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("              and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("              ) > 0 then ( ")
		   .append("                select sum(total_cost_amount) ")
		   .append("                from isw_inc_res_daily_cost ")
		   .append("			    where incident_resource_id = ir.id ")
		   .append("                and incident_account_code_id = iiac.id ")
		   .append("                and accrual_code_id = ac.id ")
		   .append("                and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("                and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("				) ")
		   .append("        else 0 ")
		   .append("        end ")
		   .append("   ) as changeAmount ")
		   .append(", ac.code as costAccrualCode ")
		   .append(", iac.account_code as accountCode ")
		   .append("from isw_incident_resource ir ")
		   .append("       left join isw_cost_data cd on ir.cost_data_id = cd.id ")
		   //.append("       left join iswl_accrual_code ac on cd.accrual_code_id = ac.id ")
		   .append("     , isw_resource r ")
		   .append("     , isw_inc_res_daily_cost dc ")
		   .append("       		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")
		   .append("     , isw_incident_account_code iiac ")
		   .append("     , isw_account_code iac ")
		   .append("where ir.incident_id = " + incidentId + " ")
		   .append("and ir.resource_id = r.id ")
		   .append("and ir.id = dc.incident_resource_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   //.append("and ir.cost_data_id in (select id from isw_cost_data where accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ) ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")
		   .append(" and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append(" and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append(" )tbl1");
			//.append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}
	*/

	public static String getCreateCostAccrualExtResCountPrevYearQuery(Long extractId, Long incidentId, String extractDateString, String fiscalDate,String fiscalYear,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("select count(ir.id) ")
		   .append("from isw_incident_resource ir ")
		   .append("		left join isw_cost_data cd on ir.cost_data_id = cd.id ")      
		   .append("     , isw_resource r ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where ir.incident_id = " + incidentId + " ")
		   .append("and ir.resource_id = r.id ")
		   .append("and ir.id = dc.incident_resource_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+fiscalDate+"','MM/DD/YYYY') ");
		   //.append(" group by ir.id, iiac.id, ac.code, iac.account_code ");
		
		return sql.toString();
	}

	// resource_other
	public static String getCreateCostAccrualExtResOtCountPrevYearQuery(Long extractId, Long incidentId, String extractDateString, String fiscalDate,String fiscalYear,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("select count(iro.id) ")
		   .append("from isw_incident_resource_other iro ")
		   .append("		left join isw_cost_data cd on iro.cost_data_id = cd.id ")      
		   .append("     , isw_resource_other ro ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where iro.incident_id = " + incidentId + " ")
		   .append("and iro.resource_other_id = ro.id ")
		   .append("and iro.id = dc.incident_resource_other_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+fiscalDate+"','MM/DD/YYYY') ");
		   //.append(" group by ir.id, iiac.id, ac.code, iac.account_code ");
		
		return sql.toString();
	}
	
	public static String getCreateCostAccrualExtractResourcesCountQuery(Long extractId, Long incidentId, String extractDateString, String fiscalDate,String fiscalYear,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("select count(ir.id) ")
		   .append("from isw_incident_resource ir ")
		   .append("		left join isw_cost_data cd on ir.cost_data_id = cd.id ")      
		   .append("     , isw_resource r ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where ir.incident_id = " + incidentId + " ")
		   .append("and ir.resource_id = r.id ")
		   .append("and ir.id = dc.incident_resource_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ");
		   //.append(" group by ir.id, iiac.id, ac.code, iac.account_code ");
		
		return sql.toString();
	}

	// OTHER RESOURCES
	public static String getCreateCostAccrualExtractOTResourcesCountQuery(Long extractId, Long incidentId, String extractDateString, String fiscalDate,String fiscalYear,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("select count(iro.id) ")
		   .append("from isw_incident_resource_other iro ")
		   .append("		left join isw_cost_data cd on iro.cost_data_id = cd.id ")      
		   .append("     , isw_resource_other ro ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where iro.incident_id = " + incidentId + " ")
		   .append("and iro.resource_other_id = ro.id ")
		   .append("and iro.id = dc.incident_resource_other_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ");
		   //.append(" group by ir.id, iiac.id, ac.code, iac.account_code ");
		
		return sql.toString();
	}

	public static String getCreateCostAccrualExtResPrevYearQuery(Long extractId, Long incidentId, String extractDateString, String fiscalDate,String fiscalYear,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		int prevYear=0;
		if(StringUtility.hasValue(fiscalYear)){
			prevYear=( (Integer.parseInt(fiscalYear)) - 1);
		}
		sql.append("insert into isw_cost_accrual_ext_rsc(" +
				"id, cost_accrual_extract_id, incident_resource_id" +
				", total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, draw_down, account_code_id ) ")
		   .append("select ")
		   .append(" " + (isOracle ? "get_accrual_ext_rsc_seq()" : "nextVal('SEQ_COST_ACCRUAL_EXT_RSC') " ) + " ")
		   .append(" , " + extractId + " ")
		   .append(", ir.id as incidentResourceId ")
		   .append(", sum(dc.primary_total_amount) as totalCost ")
		   .append(", sum(dc.primary_total_amount) as changeAmount ")
		   .append(", ac.code as costAccrualCode ")
		   .append(", iiac.accrual_account_code as accountCode ")
		   .append(", " + prevYear)
		   .append(", 'N' ")
		   .append(", iiac.id ")
		   .append("from isw_incident_resource ir ")
		   .append("		left join isw_cost_data cd on ir.cost_data_id = cd.id ")      
		   .append("     , isw_resource r ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where ir.incident_id = " + incidentId + " ")
		   .append("and ir.resource_id = r.id ")
		   .append("and ir.id = dc.incident_resource_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append(" group by ir.id, iiac.id, ac.code, iiac.accrual_account_code ");
			//.append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}

	// OTHER RESOURCES
	public static String getCreateCostAccrualExtResOTPrevYearQuery(Long extractId, Long incidentId, String extractDateString, String fiscalDate,String fiscalYear,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		int prevYear=0;
		if(StringUtility.hasValue(fiscalYear)){
			prevYear=( (Integer.parseInt(fiscalYear)) - 1);
		}
		sql.append("insert into isw_cost_accrual_ext_rsc(" +
				"id, cost_accrual_extract_id, incident_resource_other_id" +
				", total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, draw_down, account_code_id ) ")
		   .append("select ")
		   .append(" " + (isOracle ? "get_accrual_ext_rsc_seq()" : "nextVal('SEQ_COST_ACCRUAL_EXT_RSC') " ) + " ")
		   .append(" , " + extractId + " ")
		   .append(", iro.id as incidentResourceOtherId ")
		   .append(", sum(dc.primary_total_amount) as totalCost ")
		   .append(", sum(dc.primary_total_amount) as changeAmount ")
		   .append(", ac.code as costAccrualCode ")
		   .append(", iiac.accrual_account_code as accountCode ")
		   .append(", " + prevYear)
		   .append(", 'N' ")
		   .append(", iiac.id ")
		   .append("from isw_incident_resource_other iro ")
		   .append("		left join isw_cost_data cd on iro.cost_data_id = cd.id ")      
		   .append("     , isw_resource_other ro ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where iro.incident_id = " + incidentId + " ")
		   .append("and iro.resource_other_id = ro.id ")
		   .append("and iro.id = dc.incident_resource_other_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append(" group by iro.id, iiac.id, ac.code, iiac.accrual_account_code ");
			//.append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}

	public static String getCreateCostAccrualExtractResourcesQuery(Long extractId, Long incidentId, String extractDateString, String fiscalDate,String fiscalYear,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("insert into isw_cost_accrual_ext_rsc(" +
				"id, cost_accrual_extract_id, incident_resource_id" +
				", total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, draw_down, account_code_id ) ")
		   .append("select ")
		   .append(" " + (isOracle ? "get_accrual_ext_rsc_seq()" : "nextVal('SEQ_COST_ACCRUAL_EXT_RSC') " ) + " ")
		   .append(" , " + extractId + " ")
		   .append(", ir.id as incidentResourceId ")
		   .append(", sum(dc.primary_total_amount) as totalCost ")
		   .append(", sum(dc.primary_total_amount) as changeAmount ")
		   .append(", ac.code as costAccrualCode ")
		   // djp 09/21/2017 use next one below .append(", iiac.accrual_account_code as accountCode ")
		   .append(", case when (iiac.accrual_account_code is not null and iiac.accrual_account_code != '') ")
		   .append("   then iiac.accrual_account_code else iac.account_code end as accountCode ")
		   .append(", " + fiscalYear)
		   .append(", 'N' ")
		   .append(", iiac.id ")
		   .append("from isw_incident_resource ir ")
		   .append("		left join isw_cost_data cd on ir.cost_data_id = cd.id ")      
		   .append("     , isw_resource r ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where ir.incident_id = " + incidentId + " ")
		   .append("and ir.resource_id = r.id ")
		   .append("and ir.id = dc.incident_resource_id ")
		   .append("and dc.incident_resource_other_id is null ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   // djp 09/21/2017 use one below .append("and iiac.account_code_id = iac.id ")
		   .append("and iac.id = iiac.account_code_id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append(" group by ir.id, iiac.id, ac.code, iiac.accrual_account_code, iac.account_code ");
			//.append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}
	
	public static String getCreateCostAccrualExtractResourcesOTQuery(Long extractId, Long incidentId, String extractDateString, String fiscalDate,String fiscalYear,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("insert into isw_cost_accrual_ext_rsc(" +
				"id, cost_accrual_extract_id, incident_resource_other_id" +
				", total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, draw_down, account_code_id ) ")
		   .append("select ")
		   .append(" " + (isOracle ? "get_accrual_ext_rsc_seq()" : "nextVal('SEQ_COST_ACCRUAL_EXT_RSC') " ) + " ")
		   .append(" , " + extractId + " ")
		   .append(", iro.id as incidentResourceOtherId ")
		   .append(", sum(dc.primary_total_amount) as totalCost ")
		   .append(", sum(dc.primary_total_amount) as changeAmount ")
		   .append(", ac.code as costAccrualCode ")
		   // djp 09/21/2017 use next one below .append(", iiac.accrual_account_code as accountCode ")
		   .append(", case when (iiac.accrual_account_code is not null and iiac.accrual_account_code != '') ")
		   .append("   then iiac.accrual_account_code else iac.account_code end as accountCode ")
		   .append(", " + fiscalYear)
		   .append(", 'N' ")
		   .append(", iiac.id ")
		   .append("from isw_incident_resource_other iro ")
		   .append("		left join isw_cost_data cd on iro.cost_data_id = cd.id ")      
		   .append("     , isw_resource_other ro ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where iro.incident_id = " + incidentId + " ")
		   .append("and iro.resource_other_id = ro.id ")
		   .append("and iro.id = dc.incident_resource_other_id ")
		   .append("and dc.incident_resource_id is null ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   // djp 09/21/2017 use one below .append("and iiac.account_code_id = iac.id ")
		   .append("and iac.id = iiac.account_code_id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append(" group by iro.id, iiac.id, ac.code, iiac.accrual_account_code, iac.account_code ");
			//.append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}

	/*
	public static String getCreateCostAccrualExtractResourcesIGQuery(Long extractId, Long incidentGroupId, String extractDateString, String fiscalDate,String fiscalYear,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("insert into isw_cost_accrual_ext_rsc(" +
				"id, cost_accrual_extract_id, incident_resource_id" +
				", total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, draw_down) ")
		   .append("select ")
		   .append(" " + (isOracle ? "SEQ_COST_ACCRUAL_EXT_RSC.nextVal" : "nextVal('SEQ_COST_ACCRUAL_EXT_RSC') " ) + " ")
		   .append(" , " + extractId + " ")
		   .append(", tbl1.incidentResourceId ")
		   .append(", tbl1.totalCost ")
		   .append(", tbl1.changeAmount ")
		   .append(", tbl1.costAccrualCode ")
		   .append(", tbl1.accountCode ")
		   .append(", " + fiscalYear)
		   .append(", 'N' ")
		   .append(" from ")
		   .append(" (select " )
		   .append(" distinct(ir.id) as incidentResourceId ")
		   .append(",  ( ") 
		   .append("     case ")
		   .append("		when (select count(id) from isw_inc_res_daily_cost ")
		   .append("              where incident_resource_id = ir.id ")
		   .append("              and incident_account_code_id = iiac.id ")
		   .append("              and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("              and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("              ) > 0 then ( ")
		   .append("                select sum(total_cost_amount) ")
		   .append("                from isw_inc_res_daily_cost ")
		   .append("			    where incident_resource_id = ir.id ")
		   .append("                and incident_account_code_id = iiac.id ")
		   .append("                and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("                and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("				) ")
		   .append("        else 0 ")
		   .append("        end ")
		   .append("   ) as totalCost ")
		   .append(",  ( ") 
		   .append("     case ")
		   .append("		when (select count(id) from isw_inc_res_daily_cost ")
		   .append("              where incident_resource_id = ir.id ")
		   .append("              and incident_account_code_id = iiac.id ")
		   .append("              and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("              and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("              ) > 0 then ( ")
		   .append("                select sum(total_cost_amount) ")
		   .append("                from isw_inc_res_daily_cost ")
		   .append("			    where incident_resource_id = ir.id ")
		   .append("                and incident_account_code_id = iiac.id ")
		   .append("                and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("                and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("				) ")
		   .append("        else 0 ")
		   .append("        end ")
		   .append("   ) as changeAmount ")
		   .append(", ac.code as costAccrualCode ")
		   .append(", iac.account_code as accountCode ")
		   .append("from isw_incident_resource ir ")
		   .append("       left join isw_cost_data cd on ir.cost_data_id = cd.id ")
		   //.append("       left join iswl_accrual_code ac on cd.accrual_code_id = ac.id ")
		   .append("     , isw_resource r ")
		   .append("     , isw_inc_res_daily_cost dc ")
		   .append("       		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")
		   .append("     , isw_incident_account_code iiac ")
		   .append("     , isw_account_code iac ")
		   .append("where ir.incident_id in ( select incident_id from isw_incident_group_incident where incident_group_id =  " + incidentGroupId + ") ")
		   .append("and ir.resource_id = r.id ")
		   .append("and ir.id = dc.incident_resource_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   //.append("and ir.cost_data_id in (select id from isw_cost_data where accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ) ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")
		   .append(" and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append(" and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append(" )tbl1");
			//.append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}
	*/

	public static String getCreateCostAccrualExtResPrevYearIGCountQuery(Long extractId, Long incidentGroupId, String extractDateString, String fiscalDate,String fiscalYear,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("select count(ir.id) ")
		   .append("from isw_incident_resource ir ")
		   .append("		left join isw_cost_data cd on ir.cost_data_id = cd.id ")      
		   .append("     , isw_resource r ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where ir.incident_id in ( ") 
		   .append("	select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + " ")
		   .append(") ") 
		   .append("and ir.resource_id = r.id ")
		   .append("and ir.id = dc.incident_resource_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+fiscalDate+"','MM/DD/YYYY') ");
		   //.append(" group by ir.id, iiac.id, ac.code, iac.account_code ");
			//.append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}
	
	public static String getCreateCostAccrualExtractResourcesIGCountQuery(Long extractId, Long incidentGroupId, String extractDateString, String fiscalDate,String fiscalYear,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("select count(ir.id) ")
		   .append("from isw_incident_resource ir ")
		   .append("		left join isw_cost_data cd on ir.cost_data_id = cd.id ")      
		   .append("     , isw_resource r ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where ir.incident_id in ( ") 
		   .append("	select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + " ")
		   .append(") ") 
		   .append("and ir.resource_id = r.id ")
		   .append("and ir.id = dc.incident_resource_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ");
		   //.append(" group by ir.id, iiac.id, ac.code, iac.account_code ");
			//.append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}

	public static String getCreateCostAccrualExtResPrevYearIGQuery(Long extractId, Long incidentGroupId, String extractDateString, String fiscalDate,String fiscalYear, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		int prevYear=0;
		if(StringUtility.hasValue(fiscalYear)){
			prevYear=( (Integer.parseInt(fiscalYear)) - 1);
		}
		
		sql.append("insert into isw_cost_accrual_ext_rsc(" +
				"id, cost_accrual_extract_id, incident_resource_id" +
				", total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, draw_down, account_code_id ) ")
		   .append("select ")
		   .append(" " + (isOracle ? "get_accrual_ext_rsc_seq()" : "nextVal('SEQ_COST_ACCRUAL_EXT_RSC') " ) + " ")
		   .append(" , " + extractId + " ")
		   .append(", ir.id as incidentResourceId ")
		   .append(", sum(dc.primary_total_amount) as totalCost ")
		   .append(", sum(dc.primary_total_amount) as changeAmount ")
		   .append(", ac.code as costAccrualCode ")
		   //.append(", case when iiac.accrual_account_code != '' then iiac.accrual_account_code else iac.account_code end as accountCode ")
		   .append(", iiac.accrual_account_code ")
		   .append(", " + prevYear)
		   .append(", 'N' ")
		   .append(", iiac.id ")
		   .append("from isw_incident_resource ir ")
		   .append("		left join isw_cost_data cd on ir.cost_data_id = cd.id ")      
		   .append("     , isw_resource r ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where ir.incident_id in ( ") 
		   .append("	select incident_id from isw_incident_group_incident where incident_group_id =  " + incidentGroupId+" ")
		   .append(") ") 
		   .append("and ir.resource_id = r.id ")
		   .append("and ir.id = dc.incident_resource_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append(" group by ir.id, iiac.id, ac.code, iiac.accrual_account_code ");
			//.append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}
	
	public static String getCreateCostAccrualExtractResourcesIGQuery(Long extractId, Long incidentGroupId, String extractDateString, String fiscalDate,String fiscalYear, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("insert into isw_cost_accrual_ext_rsc(" +
				"id, cost_accrual_extract_id, incident_resource_id" +
				", total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, draw_down, account_code_id ) ")
		   .append("select ")
		   .append(" " + (isOracle ? "get_accrual_ext_rsc_seq()" : "nextVal('SEQ_COST_ACCRUAL_EXT_RSC') " ) + " ")
		   .append(" , " + extractId + " ")
		   .append(", ir.id as incidentResourceId ")
		   .append(", sum(dc.primary_total_amount) as totalCost ")
		   .append(", sum(dc.primary_total_amount) as changeAmount ")
		   .append(", ac.code as costAccrualCode ")
		   //.append(", case when iiac.accrual_account_code != '' then iiac.accrual_account_code else iac.account_code end as accountCode ")
		   .append(", iiac.accrual_account_code ")
		   .append(", " + fiscalYear)
		   .append(", 'N' ")
		   .append(", iiac.id ")
		   .append("from isw_incident_resource ir ")
		   .append("		left join isw_cost_data cd on ir.cost_data_id = cd.id ")      
		   .append("     , isw_resource r ")      
		   .append("	, isw_inc_res_daily_cost dc ")        		
		   .append("		left join iswl_accrual_code ac on dc.accrual_code_id = ac.id ")      
		   .append("	, isw_incident_account_code iiac ")      
		   .append("	, isw_account_code iac ") 
		   .append("where ir.incident_id in ( ") 
		   .append("	select incident_id from isw_incident_group_incident where incident_group_id =  " + incidentGroupId+" ")
		   .append(") ") 
		   .append("and ir.resource_id = r.id ")
		   .append("and ir.id = dc.incident_resource_id ")
		   .append("and iiac.id = dc.incident_account_code_id ")
		   .append("and iiac.account_code_id = iac.id ")
		   .append("and dc.accrual_code_id is not null ")
		   .append("and dc.accrual_code_id != (select id from iswl_accrual_code where code='EXCL' ) ")  
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("and to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append(" group by ir.id, iiac.id, ac.code, iiac.accrual_account_code ");
			//.append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}

	public static String getUpdateChangeAmountQuery(Long extractId, Long incidentId, String extractDateString, String fiscalDate, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
		
		sql.append("update isw_cost_accrual_ext_rsc caeres ")
			.append("set "+(isOracle ? "caeres.change_amount ": "change_amount ")+" = ")
			.append("  (select ")
			.append("     case ")
			.append("            when ")
			.append("                ( ")
			.append("                   select count(id) from isw_cost_accrual_extract ")
			.append("                   where incident_id =  " + incidentId + " ")
			.append("                   and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
			.append("                ) > 0 then  ")
			.append("                    ( ")
			.append("                    select caer.total_amount - caer2.total_amount ")
			.append("                    from isw_cost_accrual_ext_rsc caer2 ")
			.append("                    where caer2.incident_resource_id = caer.incident_resource_id ")
			.append("                    and caer2.account_code = caer.account_code ")
			.append("                    and caer2.account_code_id = caer.account_code_id ")
			.append("                    and caer2.draw_down = caer.draw_down ")
			.append("                    and caer2.cost_accrual_code = caer.cost_accrual_code ")
			.append("                    and caer2.fiscal_year = caer.fiscal_year ")
			.append("                    and caer2.cost_accrual_extract_id = ")
			.append("                        ( ")
			.append("                            select id from isw_cost_accrual_extract ")
			.append("                            where incident_id = " + incidentId + " ")
			.append("                            and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
//		    .append("                   		 and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+fiscalDate+"','MM/DD/YYYY') ")
			.append("                            and extract_date = ( ")
			.append("                                select max(extract_date) from isw_cost_accrual_extract ")
			.append("                                where incident_id = " + incidentId + " ")
			.append("                                and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
//		    .append("                   		     and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+fiscalDate+"','MM/DD/YYYY') ")
			.append("                            ) ")
			.append("                        ) ")
			.append("                    ) ")
			.append("            else caer.total_amount  ")
			.append("        end as change_amount ")
			.append("        from isw_cost_accrual_ext_rsc caer ")
			.append("        where caer.cost_accrual_extract_id = "+extractId + " ")
			.append("        and caer.incident_resource_id = caeres.incident_resource_id ")
//			.append("        and caer.fiscal_year = '"+extractDateString.substring((extractDateString.length()-2), extractDateString.length())+"' ")
			.append("        and caer.draw_down = caeres.draw_down ")
			.append("        and caer.cost_accrual_code = caeres.cost_accrual_code ")
			.append("        and caer.account_code = caeres.account_code ")
			.append("        and caer.account_code_id = caeres.account_code_id ")
			.append("    ) ")
			.append("where caeres.cost_accrual_extract_id = " + extractId + " ")
			.append(" and caeres.incident_resource_id in ")
			.append("      (select incident_resource_id ")
			.append("       from isw_cost_accrual_ext_rsc icaer  ")
			.append("       where icaer.incident_resource_id = caeres.incident_resource_id ")
			.append("       and icaer.account_code = caeres.account_code ")
			.append("       and icaer.draw_down = caeres.draw_down ")
			.append("       and icaer.cost_accrual_code = caeres.cost_accrual_code ")
			.append("       and icaer.account_code_id = caeres.account_code_id ")
			.append("       and icaer.cost_accrual_extract_id = ")
			.append("            ( ")
			.append("             select id from isw_cost_accrual_extract ")
			.append("             where incident_id = " + incidentId + " ")
			.append("             and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
		    .append("             and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
			.append("             and extract_date = ( ")
			.append("                  select max(extract_date) from isw_cost_accrual_extract ")
			.append("                  where incident_id = " + incidentId + " ")
		    .append("                  and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
			.append("                  and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
			.append("                                ) ")
			.append("            ) ")
			.append("       ) ")
			;
		
		return sql.toString();
		
	}

	public static String getUpdateChangeAmountQuery2(Long extractId, Long incidentId, String extractDateString, String fiscalDate, Boolean isOracle){
		StringBuffer sql = new StringBuffer();

		sql.append("update isw_cost_accrual_ext_rsc caeres ")
		.append("set "+(isOracle ? "caeres.change_amount ": "change_amount ")+" = ")
		.append("  (select ")
		.append("     case ")
		.append("            when ")
		.append("                ( ")
		.append("                   select count(id) from isw_cost_accrual_extract ")
		.append("                   where incident_id =  " + incidentId + " ")
		.append("                   and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
		.append("                ) > 0 then  ")
		.append("                    ( ")
		.append("                    select case ")
		.append("                       when ")
		.append("                           (c1.incident_resource_id = caeres.incident_resource_id ")
		.append("                              and c1.account_code = caeres.account_code ")
		.append("                              and c1.account_code_id = caeres.account_code_id ")
		.append("                              and c1.draw_down = caeres.draw_down ")
		.append("                              and c1.cost_accrual_code = caeres.cost_accrual_code ")
		.append("                              and c1.fiscal_year = caeres.fiscal_year) then (caeres.total_amount - c1.total_amount) ")
		.append("                       else caeres.total_amount ")
		.append("                       end ") 
		.append("                    from isw_cost_accrual_ext_rsc c1 ")
		.append("                    where c1.cost_accrual_extract_id = ")
		.append("                        ( ")
		.append("                            select id from isw_cost_accrual_extract ")
		.append("                            where incident_id = " + incidentId + " ")
		.append("                            and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
		.append("                            and finalized = 'Y'" + " ")
		.append("                            and extract_date = ( ")
		.append("                                select max(extract_date) from isw_cost_accrual_extract ")
		.append("                                where incident_id = " + incidentId + " ")
		.append("                                and finalized = 'Y'" + " ")
		.append("                                and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
		.append("                            ) ")
		.append("                        ) ")
		.append("                    ) ")
		.append("            else caeres.total_amount  ")
		.append("        end as change_amount ")
		.append("        from isw_cost_accrual_extract caer ")
		.append("        where caer.id = "+extractId + " ")
		.append("    ) ")
		.append("where caeres.cost_accrual_extract_id = " + extractId + " ");
		/*
		.append(" and caeres.incident_resource_id in ")
		.append("      (select incident_resource_id ")
		.append("       from isw_cost_accrual_ext_rsc icaer  ")
		.append("       where icaer.incident_resource_id = caeres.incident_resource_id ")
		.append("       and icaer.account_code = caeres.account_code ")
		.append("       and icaer.draw_down = caeres.draw_down ")
		.append("       and icaer.cost_accrual_code = caeres.cost_accrual_code ")
		.append("       and icaer.account_code_id = caeres.account_code_id ")
		.append("       and icaer.cost_accrual_extract_id = ")
		.append("            ( ")
		.append("             select id from isw_cost_accrual_extract ")
		.append("             where incident_id = " + incidentId + " ")
		.append("             and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
	    .append("             and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		.append("             and extract_date = ( ")
		.append("                  select max(extract_date) from isw_cost_accrual_extract ")
		.append("                  where incident_id = " + incidentId + " ")
	    .append("                  and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		.append("                  and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
		.append("                                ) ")
		.append("            ) ")
		.append("       ) ")
		;
		*/
		return sql.toString();
	}

	public static String getUpdateChangeAmountQuery3(Long extractId, Long previousExtractId, String extractDateString, String fiscalDate, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
		sql.append("")
		.append("update isw_cost_accrual_ext_rsc c ");
		if(isOracle)
			sql.append("set c.change_amount = ( ");
		else
			sql.append("set change_amount = ( ");
		sql.append("	select (c.total_amount - p.total_amount) as change_amount ")
		.append("	from isw_cost_accrual_ext_rsc p ")
		.append("	where p.cost_accrual_extract_id = " + previousExtractId + " ")
		.append("	and p.incident_resource_id=c.incident_resource_id ")
		.append("	and p.incident_resource_other_id is null ")
		.append("	and p.account_code = c.account_code ")
		.append("	and p.account_code_id = c.account_code_id ")
		.append("	and p.draw_down = c.draw_down ")
		.append("	and p.cost_accrual_code=c.cost_accrual_code ")
		.append("	and p.fiscal_year = c.fiscal_year ")
		// djp 10/12/2017 adding min check in case there are duplicates with matching values
		/*
		.append("   and p.id = (")
		.append("      select min(p2.id) ")
		.append("	   from isw_cost_accrual_ext_rsc p2 ")
		.append("	   where p2.cost_accrual_extract_id = " + previousExtractId + " ")
		.append("	   and p2.incident_resource_id=c.incident_resource_id ")
		.append("	   and p2.account_code = c.account_code ")
		.append("	   and p2.account_code_id = c.account_code_id ")
		.append("	   and p2.draw_down = c.draw_down ")
		.append("	   and p2.cost_accrual_code=c.cost_accrual_code ")
		.append("	   and p2.fiscal_year = c.fiscal_year ")
		.append("   ) ")
		*/
		.append(") ")
		.append("where c.cost_accrual_extract_id = " + extractId + " ")
		.append("and c.id in ( ")
		.append("	select c2.id ")
		.append("	from isw_cost_accrual_ext_rsc c2, isw_cost_accrual_ext_rsc p2 ")
		.append("	where c2.cost_accrual_extract_id = " + extractId + " ")
		.append("	and p2.cost_accrual_extract_id = " + previousExtractId + " ")
		.append("	and c2.incident_resource_id=p2.incident_resource_id ")
		.append("	and c2.incident_resource_other_id is null ")
		.append("	and c2.account_code = p2.account_code ")
		.append("	and c2.account_code_id = p2.account_code_id ")
		.append("	and c2.draw_down = p2.draw_down ") 
		.append("	and c2.cost_accrual_code=p2.cost_accrual_code ")
		.append("	and c2.fiscal_year = p2.fiscal_year ")
		.append(") "); 
		return sql.toString();
	}
	
	public static String getUpdateChangeAmountQuery3OT(Long extractId, Long previousExtractId, String extractDateString, String fiscalDate, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
		sql.append("")
		.append("update isw_cost_accrual_ext_rsc c ");
		if(isOracle)
			sql.append("set c.change_amount = ( ");
		else
			sql.append("set change_amount = ( ");
		sql.append("	select (c.total_amount - p.total_amount) as change_amount ")
		.append("	from isw_cost_accrual_ext_rsc p ")
		.append("	where p.cost_accrual_extract_id = " + previousExtractId + " ")
		.append("   and p.incident_resource_id is null ")
		.append("	and p.incident_resource_other_id=c.incident_resource_other_id ")
		.append("	and p.account_code = c.account_code ")
		.append("	and p.account_code_id = c.account_code_id ")
		.append("	and p.draw_down = c.draw_down ")
		.append("	and p.cost_accrual_code=c.cost_accrual_code ")
		.append("	and p.fiscal_year = c.fiscal_year ")
		// djp 10/12/2017 adding min check in case there are duplicates with matching values
		/*
		.append("   and p.id = (")
		.append("      select min(p2.id) ")
		.append("	   from isw_cost_accrual_ext_rsc p2 ")
		.append("	   where p2.cost_accrual_extract_id = " + previousExtractId + " ")
		.append("	   and p2.incident_resource_id=c.incident_resource_id ")
		.append("	   and p2.account_code = c.account_code ")
		.append("	   and p2.account_code_id = c.account_code_id ")
		.append("	   and p2.draw_down = c.draw_down ")
		.append("	   and p2.cost_accrual_code=c.cost_accrual_code ")
		.append("	   and p2.fiscal_year = c.fiscal_year ")
		.append("   ) ")
		*/
		.append(") ")
		.append("where c.cost_accrual_extract_id = " + extractId + " ")
		.append("and c.id in ( ")
		.append("	select c2.id ")
		.append("	from isw_cost_accrual_ext_rsc c2, isw_cost_accrual_ext_rsc p2 ")
		.append("	where c2.cost_accrual_extract_id = " + extractId + " ")
		.append("	and p2.cost_accrual_extract_id = " + previousExtractId + " ")
		.append("   and c2.incident_resource_id is null ")
		.append("	and c2.incident_resource_other_id=p2.incident_resource_other_id ")
		.append("	and c2.account_code = p2.account_code ")
		.append("	and c2.account_code_id = p2.account_code_id ")
		.append("	and c2.draw_down = p2.draw_down ") 
		.append("	and c2.cost_accrual_code=p2.cost_accrual_code ")
		.append("	and c2.fiscal_year = p2.fiscal_year ")
		.append(") "); 
		return sql.toString();
	}

	public static String getUpdateChangeAmountIGQuery(Long extractId, Long incidentGroupId, String extractDateString, String fiscalDate, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
		
		sql.append("update isw_cost_accrual_ext_rsc caeres ")
			.append("set "+(isOracle ? "caeres.change_amount ": "change_amount ")+" = ")
			.append("  (select ")
			.append("     case ")
			.append("            when ")
			.append("                ( ")
			.append("                   select count(id) from isw_cost_accrual_extract ")
			.append("                   where incident_group_id =  " + incidentGroupId + " ")
			.append("                   and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
			.append("                ) > 0 then  ")
			.append("                    ( ")
			.append("                    select caer.total_amount - caer2.total_amount ")
			.append("                    from isw_cost_accrual_ext_rsc caer2 ")
			.append("                    where caer2.incident_resource_id = caer.incident_resource_id ")
			.append("                    and caer2.account_code = caer.account_code ")
			.append("                    and caer2.account_code_id = caer.account_code_id ")
			.append("                    and caer2.draw_down = caer.draw_down ")
			.append("                    and caer2.cost_accrual_code = caer.cost_accrual_code ")
			.append("                    and caer2.cost_accrual_extract_id = ")
			.append("                        ( ")
			.append("                            select id from isw_cost_accrual_extract ")
			.append("                            where incident_group_id = " + incidentGroupId + " ")
			.append("                            and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
			.append("                            and extract_date = ( ")
			.append("                                select max(extract_date) from isw_cost_accrual_extract ")
			.append("                                where incident_group_id = " + incidentGroupId + " ")
			.append("                                and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
		    .append("                   		 	 and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
			.append("                            ) ")
			.append("                        ) ")
			.append("                    ) ")
			.append("            else caer.total_amount  ")
			.append("        end as change_amount ")
			.append("        from isw_cost_accrual_ext_rsc caer ")
			.append("        where caer.cost_accrual_extract_id = "+extractId + " ")
			.append("        and caer.incident_resource_id = caeres.incident_resource_id ")
			.append("        and caer.draw_down = caeres.draw_down ")
			.append("        and caer.cost_accrual_code = caeres.cost_accrual_code ")
			.append("        and caer.account_code = caeres.account_code ")
			.append("        and caer.account_code_id = caeres.account_code_id ")
			.append("    ) ")
			.append("where caeres.cost_accrual_extract_id = " + extractId + " ")
			.append(" and caeres.incident_resource_id in ")
			.append("      (select incident_resource_id ")
			.append("       from isw_cost_accrual_ext_rsc icaer  ")
			.append("       where icaer.incident_resource_id = caeres.incident_resource_id ")
			.append("       and icaer.account_code = caeres.account_code ")
			.append("       and icaer.account_code_id = caeres.account_code_id ")
			.append("       and icaer.draw_down = caeres.draw_down ")
			.append("       and icaer.cost_accrual_code = caeres.cost_accrual_code ")
			.append("       and icaer.cost_accrual_extract_id = ")
			.append("            ( ")
			.append("             select id from isw_cost_accrual_extract ")
			.append("             where incident_group_id = " + incidentGroupId + " ")
		    .append("             and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
			.append("             and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
			.append("             and extract_date = ( ")
			.append("                  select max(extract_date) from isw_cost_accrual_extract ")
			.append("                  where incident_group_id = " + incidentGroupId + " ")
		    .append("                  and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
			.append("                  and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
			.append("                                ) ")
			.append("            ) ")
			.append("       ) ")
			;
		
		return sql.toString();
		
	}

	public static String getUpdateChangeAmountIGQuery2(Long extractId, Long incidentGroupId, String extractDateString, String fiscalDate, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
		
		sql.append("update isw_cost_accrual_ext_rsc caeres ")
		.append("set "+(isOracle ? "caeres.change_amount ": "change_amount ")+" = ")
		.append("  (select ")
		.append("     case ")
		.append("            when ")
		.append("                ( ")
		.append("                   select count(id) from isw_cost_accrual_extract ")
		.append("                   where incident_group_id =  " + incidentGroupId + " ")
		.append("                   and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
		.append("                ) > 0 then  ")
		.append("                    ( ")
		.append("                    select case ")
		.append("                       when ")
		.append("                           (c1.incident_resource_id = caeres.incident_resource_id ")
		.append("                              and c1.account_code = caeres.account_code ")
		.append("                              and c1.account_code_id = caeres.account_code_id ")
		.append("                              and c1.draw_down = caeres.draw_down ")
		.append("                              and c1.cost_accrual_code = caeres.cost_accrual_code ")
		.append("                              and c1.fiscal_year = caeres.fiscal_year) then (caeres.total_amount - c1.total_amount) ")
		.append("                       else caeres.total_amount ")
		.append("                       end ") 
		.append("                    from isw_cost_accrual_ext_rsc c1 ")
		.append("                    where c1.cost_accrual_extract_id = ")
		.append("                        ( ")
		.append("                            select id from isw_cost_accrual_extract ")
		.append("                            where incident_group_id = " + incidentGroupId + " ")
		.append("                            and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
		.append("                            and extract_date = ( ")
		.append("                                select max(extract_date) from isw_cost_accrual_extract ")
		.append("                                where incident_group_id = " + incidentGroupId + " ")
		.append("                                and to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+extractDateString+"','MM/DD/YYYY') ")
		.append("                            ) ")
		.append("                        ) ")
		.append("                    ) ")
		.append("            else caeres.total_amount  ")
		.append("        end as change_amount ")
		.append("        from isw_cost_accrual_extract caer ")
		.append("        where caer.id = "+extractId + " ")
		.append("    ) ")
		.append("where caeres.cost_accrual_extract_id = " + extractId + " ");

		return sql.toString();
	}

	public static String getUpdateChangeAmountIGQuery3(Long extractId, Long previousExtractId, String extractDateString, String fiscalDate, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
		sql.append("")
		.append("update isw_cost_accrual_ext_rsc c ");
		if(isOracle)
			sql.append("set c.change_amount = ( ");
		else
			sql.append("set change_amount = ( ");
		sql.append("	select (c.total_amount - p.total_amount) as change_amount ")
		.append("	from isw_cost_accrual_ext_rsc p ")
		.append("	where p.cost_accrual_extract_id = " + previousExtractId + " ")
		.append("	and p.incident_resource_id=c.incident_resource_id ")
		.append("	and p.incident_resource_other_id=c.incident_resource_other_id ")
		.append("	and p.account_code = c.account_code ")
		.append("	and p.account_code_id = c.account_code_id ")
		.append("	and p.draw_down = c.draw_down ")
		.append("	and p.cost_accrual_code=c.cost_accrual_code ")
		.append("	and p.fiscal_year = c.fiscal_year ")
		.append(") ")
		.append("where c.cost_accrual_extract_id = " + extractId + " ")
		.append("and c.id in ( ")
		.append("	select c2.id ")
		.append("	from isw_cost_accrual_ext_rsc c2, isw_cost_accrual_ext_rsc p2 ")
		.append("	where c2.cost_accrual_extract_id = " + extractId + " ")
		.append("	and p2.cost_accrual_extract_id = " + previousExtractId + " ")
		.append("	and c2.incident_resource_id=p2.incident_resource_id ")
		.append("	and c2.incident_resource_other_id=p2.incident_resource_other_id ")
		.append("	and c2.account_code = p2.account_code ")
		.append("	and c2.account_code_id = p2.account_code_id ")
		.append("	and c2.draw_down = p2.draw_down ") 
		.append("	and c2.cost_accrual_code=p2.cost_accrual_code ")
		.append("	and c2.fiscal_year = p2.fiscal_year ")
		.append(") "); 

		return sql.toString();
	}
	
	public static String getCreateADDrawDownQuery(Long extractId, Long incidentId, String extractDateString, String fiscalDate, String fiscalYear, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("insert into isw_cost_accrual_ext_rsc(" +
				"id, cost_accrual_extract_id, incident_resource_id" +
				", total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, draw_down,account_code_id) ")
		   .append("select ")
		   .append(" " + (isOracle ? "get_accrual_ext_rsc_seq()" : "nextVal('SEQ_COST_ACCRUAL_EXT_RSC') " ) + " ")
		   .append(" , " + extractId + " ")
		   .append(", tbl1.incidentResourceId ")
		   .append(", tbl1.totalAmount ")
		   .append(", tbl1.changeAmount ")
		   .append(", tbl1.costAccrualCode ")
		   .append(", tbl1.accountCode ")
		   .append(", " + fiscalYear)
		   .append(", 'Y' ")
		   .append(", tbl1.accountCodeId ")
		   .append(" from ")
		   .append("     (select " )
		   .append("     distinct(ir.id) as incidentResourceId ")
		   .append(",    ( ") 
		   .append("     select -sum(atp2.invoiced_amount) ")
		  .append("	      from ")
		   .append("          isw_time_invoice ti2 ")
		   .append("          ,isw_assign_time_post_invoice atpi2 ")
		   .append("          ,isw_assign_time_post atp2 ")
		   .append("          ,isw_assignment_time at2 ")
		   .append("          ,isw_assignment a2 ")
		   .append("          ,isw_work_period_assignment wpa2 ")
		   .append("          ,isw_work_period wp2 ")
		   .append("          ,isw_incident_resource ir2 ")
		   .append("          ,isw_cost_data cd2 ")
		   .append("          ,iswl_accrual_code ac2 ")
		   .append("          ,isw_incident_account_code iac2 ")
		   .append("          where ti2.id = atpi2.time_invoice_id ")
		   .append("          and atpi2.assign_time_post_id = atp2.id ")
		   .append("          and atp2.assignment_time_id = at2.id ")
		   .append("          and at2.assignment_id = a2.id ")
		   .append("	      and a2.id = wpa2.assignment_id ")
		   .append("          and wpa2.work_period_id = wp2.id ")
		   .append("          and wp2.incident_resource_id = ir2.id ")
		   .append("          and ir2.cost_data_id = cd2.id ")
		   .append("          and cd2.accrual_code_id = ac2.id ")
		   .append("          and ac2.code = 'AD' ")
		   .append("          and ti2.export_date is not null ")
		   .append("          and to_date(to_char(ti2.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("          and to_date(to_char(ti2.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("          and ir2.id = ir.id ")
		   .append("          and atp2.incident_account_code_id = iac2.id ")
		   .append("	      and iac2.id = iac.id) ")
		   .append("     as totalAmount ")
		   .append(",    ( ") 
		   .append("     select -sum(atp2.invoiced_amount) ")
		   .append("	      from ")
		   .append("          isw_time_invoice ti2 ")
		   .append("          ,isw_assign_time_post_invoice atpi2 ")
		   .append("          ,isw_assign_time_post atp2 ")
		   .append("          ,isw_assignment_time at2 ")
		   .append("          ,isw_assignment a2 ")
		   .append("          ,isw_work_period_assignment wpa2 ")
		   .append("          ,isw_work_period wp2 ")
		   .append("          ,isw_incident_resource ir2 ")
		   .append("          ,isw_cost_data cd2 ")
		   .append("          ,iswl_accrual_code ac2 ")
		   .append("          ,isw_incident_account_code iac2 ")
		   .append("          where ti2.id = atpi2.time_invoice_id ")
		   .append("          and atpi2.assign_time_post_id = atp2.id ")
		   .append("          and atp2.assignment_time_id = at2.id ")
		   .append("          and at2.assignment_id = a2.id ")
		   .append("	      and a2.id = wpa2.assignment_id ")
		   .append("          and wpa2.work_period_id = wp2.id ")
		   .append("          and wp2.incident_resource_id = ir2.id ")
		   .append("          and ir2.cost_data_id = cd2.id ")
		   .append("          and cd2.accrual_code_id = ac2.id ")
		   .append("          and ac2.code = 'AD' ")
		   .append("          and ti2.export_date is not null ")
		   .append("          and to_date(to_char(ti2.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("          and to_date(to_char(ti2.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("          and ir2.id = ir.id ")
		   .append("          and atp2.incident_account_code_id = iac2.id ")
		   .append("	      and iac2.id = iac.id) ")
		   .append("     as changeAmount ")
		   .append("     , ac.code as costAccrualCode ")
		   .append("     , case when (iac.accrual_account_code is not null and iac.accrual_account_code != '') then iac.accrual_account_code else acc.account_code end as accountCode ")
		   .append("     , iac.id as accountCodeId ")
		   .append("     from ")
		   .append("     isw_time_invoice ti ")
		   .append("     , isw_assign_time_post_invoice atpi ")
		   .append("     , isw_assign_time_post atp ")
		   .append("     , isw_assignment_time at ")
		   .append("     , isw_assignment a ")
		   .append("     , isw_work_period_assignment wpa ")
		   .append("     , isw_work_period wp ")
		   .append("     , isw_incident_resource ir ")
		   .append("     , isw_cost_data cd ")
		   .append("     , iswl_accrual_code ac ")
		   .append("     , isw_incident_account_code iac ")
		   .append("     , isw_account_code acc ")
		   .append("     where ")
		   .append("     ti.id = atpi.time_invoice_id ")
		   .append("     and atpi.assign_time_post_id = atp.id ")
		   .append("     and atp.assignment_time_id = at.id ")
		   .append("     and at.assignment_id = a.id ")
		   .append("     and a.id = wpa.assignment_id ")
		   .append("     and wpa.work_period_id = wp.id ")
		   .append("     and wp.incident_resource_id = ir.id ")
		   .append("     and ir.cost_data_id = cd.id ")
		   .append("     and cd.accrual_code_id = ac.id ")
		   .append("     and ac.code = 'AD' ")
		   .append("     and atp.incident_account_code_id = iac.id ")
		   .append("     and iac.account_code_id = acc.id ")
		   .append("     and ir.incident_id = " + incidentId + " ")
		   .append("     and ti.export_date is not null ")
		   .append("     and to_date(to_char(ti.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("     and to_date(to_char(ti.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append(" )tbl1");
		
		return sql.toString();
	}
	
	public static String getCreateADDrawDownIGQuery(Long extractId, Long incidentGroupId, String extractDateString, String fiscalDate, String fiscalYear, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("insert into isw_cost_accrual_ext_rsc(" +
				"id, cost_accrual_extract_id, incident_resource_id" +
				", total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, draw_down, account_code_id) ")
		   .append("select ")
		   .append(" " + (isOracle ? "get_accrual_ext_rsc_seq()" : "nextVal('SEQ_COST_ACCRUAL_EXT_RSC') " ) + " ")
		   .append(" , " + extractId + " ")
		   .append(", tbl1.incidentResourceId ")
		   .append(", tbl1.totalAmount ")
		   .append(", tbl1.changeAmount ")
		   .append(", tbl1.costAccrualCode ")
		   .append(", tbl1.accountCode ")
		   .append(", " + fiscalYear)
		   .append(", 'Y' ")
		   .append(", tbl1.accountCodeId ")
		   .append(" from ")
		   .append("     (select " )
		   .append("     distinct(ir.id) as incidentResourceId ")
		   .append(",    ( ") 
		   .append("     select -sum(atp2.invoiced_amount) ")
		   .append("	      from ")
		   .append("          isw_time_invoice ti2 ")
		   .append("          ,isw_assign_time_post_invoice atpi2 ")
		   .append("          ,isw_assign_time_post atp2 ")
		   .append("          ,isw_assignment_time at2 ")
		   .append("          ,isw_assignment a2 ")
		   .append("          ,isw_work_period_assignment wpa2 ")
		   .append("          ,isw_work_period wp2 ")
		   .append("          ,isw_incident_resource ir2 ")
		   .append("          ,isw_cost_data cd2 ")
		   .append("          ,iswl_accrual_code ac2 ")
		   .append("          ,isw_incident_account_code iac2 ")
		   .append("          where ti2.id = atpi2.time_invoice_id ")
		   .append("          and atpi2.assign_time_post_id = atp2.id ")
		   .append("          and atp2.assignment_time_id = at2.id ")
		   .append("          and at2.assignment_id = a2.id ")
		   .append("	      and a2.id = wpa2.assignment_id ")
		   .append("          and wpa2.work_period_id = wp2.id ")
		   .append("          and wp2.incident_resource_id = ir2.id ")
		   .append("          and ir2.cost_data_id = cd2.id ")
		   .append("          and cd2.accrual_code_id = ac2.id ")
		   .append("          and ac2.code = 'AD' ")
		   .append("          and ti2.export_date is not null ")
		   .append("          and to_date(to_char(ti2.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("          and to_date(to_char(ti2.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("          and ir2.id = ir.id ")
		   .append("          and atp2.incident_account_code_id = iac2.id ")
		   .append("	      and iac2.id = iac.id) ")
		   .append("     as totalAmount ")
		   .append(",    ( ") 
		   .append("     select -sum(atp2.invoiced_amount) ")
		   .append("	      from ")
		   .append("          isw_time_invoice ti2 ")
		   .append("          ,isw_assign_time_post_invoice atpi2 ")
		   .append("          ,isw_assign_time_post atp2 ")
		   .append("          ,isw_assignment_time at2 ")
		   .append("          ,isw_assignment a2 ")
		   .append("          ,isw_work_period_assignment wpa2 ")
		   .append("          ,isw_work_period wp2 ")
		   .append("          ,isw_incident_resource ir2 ")
		   .append("          ,isw_cost_data cd2 ")
		   .append("          ,iswl_accrual_code ac2 ")
		   .append("          ,isw_incident_account_code iac2 ")
		   .append("          where ti2.id = atpi2.time_invoice_id ")
		   .append("          and atpi2.assign_time_post_id = atp2.id ")
		   .append("          and atp2.assignment_time_id = at2.id ")
		   .append("          and at2.assignment_id = a2.id ")
		   .append("	      and a2.id = wpa2.assignment_id ")
		   .append("          and wpa2.work_period_id = wp2.id ")
		   .append("          and wp2.incident_resource_id = ir2.id ")
		   .append("          and ir2.cost_data_id = cd2.id ")
		   .append("          and cd2.accrual_code_id = ac2.id ")
		   .append("          and ac2.code = 'AD' ")
		   .append("          and ti2.export_date is not null ")
		   .append("          and to_date(to_char(ti2.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("          and to_date(to_char(ti2.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append("          and ir2.id = ir.id ")
		   .append("          and atp2.incident_account_code_id = iac2.id ")
		   .append("	      and iac2.id = iac.id) ")
		   .append("     as changeAmount ")
		   .append("     , ac.code as costAccrualCode ")
		   .append("     , case when iac.accrual_account_code != '' then iac.accrual_account_code else acc.account_code end as accountCode ")
		   .append("     , iac.id as accountCodeId ")
		   .append("     from ")
		   .append("     isw_time_invoice ti ")
		   .append("     , isw_assign_time_post_invoice atpi ")
		   .append("     , isw_assign_time_post atp ")
		   .append("     , isw_assignment_time at ")
		   .append("     , isw_assignment a ")
		   .append("     , isw_work_period_assignment wpa ")
		   .append("     , isw_work_period wp ")
		   .append("     , isw_incident_resource ir ")
		   .append("     , isw_cost_data cd ")
		   .append("     , iswl_accrual_code ac ")
		   .append("     , isw_incident_account_code iac ")
		   .append("     , isw_account_code acc ")
		   .append("     where ")
		   .append("     ti.id = atpi.time_invoice_id ")
		   .append("     and atpi.assign_time_post_id = atp.id ")
		   .append("     and atp.assignment_time_id = at.id ")
		   .append("     and at.assignment_id = a.id ")
		   .append("     and a.id = wpa.assignment_id ")
		   .append("     and wpa.work_period_id = wp.id ")
		   .append("     and wp.incident_resource_id = ir.id ")
		   .append("     and ir.cost_data_id = cd.id ")
		   .append("     and cd.accrual_code_id = ac.id ")
		   .append("     and ac.code = 'AD' ")
		   .append("     and atp.incident_account_code_id = iac.id ")
		   .append("     and iac.account_code_id = acc.id ")
		   .append("     and ir.incident_id in ( select incident_id from isw_incident_group_incident where incident_group_id =  " + incidentGroupId + ") ")
		   .append("     and ti.export_date is not null ")
		   .append("     and to_date(to_char(ti.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+extractDateString+"','MM/DD/YYYY') ")
		   .append("     and to_date(to_char(ti.export_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+fiscalDate+"','MM/DD/YYYY') ")
		   .append(" )tbl1");
		
		return sql.toString();
	}

	public static String getTotalAmountByExtractIdQuery(Long extractId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select sum(caer.total_amount) as totalAmount ")
		   .append("from isw_cost_accrual_ext_rsc caer ")
		   .append("where caer.cost_accrual_extract_id = " + extractId + " ");
		
//		sql.append("select sum(caer.total_amount) as totalAmount ")
//		   .append("from isw_cost_accrual_ext_rsc caer ")
//		   .append("     , isw_incident_resource ir ")
//		   .append("     , isw_resource r ")
//		   .append("where caer.cost_accrual_extract_id = " + extractId + " ")
//		   .append("and ir.id = caer.incident_resource_id ")
//		   .append("and r.id = ir.resource_id ")
//		   .append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}
	
	public static String getTotalChangeAmountByExtractIdQuery(Long extractId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select sum(caer.change_amount) as changeAmount ")
		   .append("from isw_cost_accrual_ext_rsc caer ")
		   .append("where caer.cost_accrual_extract_id = " + extractId + " ");
		
//		sql.append("select sum(caer.change_amount) as changeAmount ")
//		   .append("from isw_cost_accrual_ext_rsc caer ")
//		   .append("     , isw_incident_resource ir ")
//		   .append("     , isw_resource r ")
//		   .append("where caer.cost_accrual_extract_id = " + extractId + " ")
//		   .append("and ir.id = caer.incident_resource_id ")
//		   .append("and r.id = ir.resource_id ")
//		   .append("and r.parent_resource_id is null ");
		
		return sql.toString();
	}
	
	public static String getIncidentGroupNumbersQuery(Long incidentGroupId, Date extractDate) {
		StringBuffer sql = new StringBuffer();
		sql.append("")
			.append("select cost_accrual_code as costAccrualCode")
			.append(", sum(total_amount) as totalAmount")
			.append(", sum(change_amount) as changeAmount")
			.append(", account_code as accountCode")
			.append(", account_code_id as accountCodeId ")
			.append(", incident_resource_id as incidentResourceId ")
			.append(", draw_down as drawDown ")
			.append(", fiscal_year as fiscalYear ")
			.append("from isw_cost_accrual_ext_rsc ")
			.append("where cost_accrual_extract_id in ( ")
			.append("	select id ")
			.append("	from isw_cost_accrual_extract ")
			.append("	where incident_id in ( ")
			.append("		select incident_id ")
			.append("		from isw_incident_group_incident ")
			.append("		where incident_group_id = " + incidentGroupId + " ")
			.append("	) ")
			.append("	and to_char(extract_date,'MM/DD/YYYY') = '"+DateUtil.toDateString(extractDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"' ")
			.append(") " )
			.append("group by account_code, cost_accrual_code,account_code_id, incident_resource_id, draw_down, fiscal_year ");
		
		return sql.toString();
	}
}
