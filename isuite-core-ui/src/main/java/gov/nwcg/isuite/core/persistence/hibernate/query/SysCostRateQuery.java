package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.filter.CostRateGridFilter;
import gov.nwcg.isuite.framework.util.StringUtility;

public class SysCostRateQuery {

	
	/**
	 * Move to a db view in the future.
	 * 
	 * @return
	 */
	public static String getGridQuery(Boolean isOracle, CostRateFilter filter){
		CostRateGridFilter subFilter = (CostRateGridFilter)filter.getSubFilter();
		
		StringBuffer sql = new StringBuffer()
			.append("select distinct(itemId) ")
			.append(",itemCode ")
			.append(",itemDescription ")
			.append(",requestCategory ")
			.append(",fedRate ")
			.append(",stateRate ")
			.append(",contrRate ")
			.append(",customStateRates ")
			.append("FROM ") 
			.append("( ")
			.append("  SELECT distinct(k2.id) as itemId ")
            .append("   ,k2.code as itemCode ")
            .append("  ,k2.description as itemDescription ")
            .append("  ,RQ.CODE as requestCategory ")
            .append("  , srkfed.rate_amount as fedRate ")
            .append("  , srkstate.rate_amount as stateRate ")
            .append("  ,srkcontr.rate_amount as contrRate ");
			if(isOracle)
				sql.append("  ,(select  getSysCostRateStateKindRates(k2.id) from dual) as customStateRates ");
			else
				sql.append("  ,(select  getSysCostRateStateKindRates(k2.id)) as customStateRates ");
         sql.append("  FROM iswl_kind k2 ")
            .append("      ,iswl_request_category rq ")
            .append("      ,isw_syscost_rate scrfed ")
            .append("      ,isw_syscost_rate scrstate ")
            .append("      ,isw_syscost_rate scrcontr ")
            .append("      ,isw_syscost_rate_kind srkfed ")
            .append("      ,isw_syscost_rate_kind srkstate ")
            .append("      ,isw_syscost_rate_kind srkcontr ")
            .append("   WHERE  ")
            .append("    rq.id = k2.request_category_id ")
            .append("    and scrfed.cost_rate_category = 'FED' ")
            .append("    and scrstate.cost_rate_category = 'STATE_COOP' ")
            .append("    and scrcontr.cost_rate_category = 'CONTRACTOR' ")
            .append("    and srkfed.kind_id = k2.id ")
            .append("    and srkstate.kind_id = k2.id ")
            .append("    and srkcontr.kind_id = k2.id ")
            .append("    AND srkfed.syscost_rate_id = scrfed.id ")
            .append("    AND srkstate.syscost_rate_id = scrstate.id ")
            .append("    AND srkcontr.syscost_rate_id = scrcontr.id ");
			if(isOracle == true) {
				sql.append(")  ");
			} else {
				//PostgreSQL sub queries require an alias.
				sql.append(") as sq ");
			}
		
		sql.append("	WHERE (1 = 1) ");
		if(null != filter){//If filter is not null, subFilter should not be null
			if(StringUtility.hasValue(filter.getRequestCategory())){
				if(filter.getRequestCategory().equalsIgnoreCase("OTHER")){
					sql.append("AND requestCategory not in ('C','O','E') ");
					if(StringUtility.hasValue(subFilter.getRequestCategory())) {
						sql.append("AND requestCategory = '"+subFilter.getRequestCategory().toUpperCase()+"' ");
					}
				} else {
					sql.append("AND requestCategory = '"+filter.getRequestCategory().toUpperCase()+"' ");
				}

				if(null != subFilter){
					if(StringUtility.hasValue(subFilter.getItemCode())) {
						sql.append("AND itemCode like '"+subFilter.getItemCode().toUpperCase()+"%' ");
					}
					if(StringUtility.hasValue(subFilter.getItemDescription())) {
						sql.append("AND itemDescription like '"+subFilter.getItemDescription().toUpperCase()+"%' ");
					}
					if(StringUtility.hasValue(subFilter.getFedRate())) {
						sql.append("AND fedRate like '"+subFilter.getFedRate().toUpperCase()+"%' ");
					}
					if(StringUtility.hasValue(subFilter.getStateRate())) {
						sql.append("AND stateRate like '"+subFilter.getStateRate().toUpperCase()+"%' ");
					}
					if(StringUtility.hasValue(subFilter.getContractorRate())) {
						sql.append("AND contrRate like '"+subFilter.getContractorRate().toUpperCase()+"%' ");
					}
					
				}
			} else {

				if(null != subFilter){
					if(StringUtility.hasValue(subFilter.getRequestCategory())) {
						sql.append("AND requestCategory = '"+subFilter.getRequestCategory().toUpperCase()+"' ");
					}
					if(StringUtility.hasValue(subFilter.getItemCode())) {
						sql.append("AND itemCode like '"+subFilter.getItemCode().toUpperCase()+"%' ");
					}
					if(StringUtility.hasValue(subFilter.getItemDescription())) {
						sql.append("AND itemDescription like '"+subFilter.getItemDescription().toUpperCase()+"%' ");
					}
					if(StringUtility.hasValue(subFilter.getFedRate())) {
						sql.append("AND fedRate like '"+subFilter.getFedRate().toUpperCase()+"%' ");
					}
					if(StringUtility.hasValue(subFilter.getStateRate())) {
						sql.append("AND stateRate like '"+subFilter.getStateRate().toUpperCase()+"%' ");
					}
					if(StringUtility.hasValue(subFilter.getContractorRate())) {
						sql.append("AND contrRate like '"+subFilter.getContractorRate().toUpperCase()+"%' ");
					}
				}
			}
		}
		
        sql.append("group by itemId, itemCode,itemDescription,requestCategory,fedRate,stateRate,contrRate,customStateRates ")
        	//.append( (!isOracle ? ",customStateRates " : "")  )
           .append("order by itemId asc ");
		
		return sql.toString();
	} 
}
