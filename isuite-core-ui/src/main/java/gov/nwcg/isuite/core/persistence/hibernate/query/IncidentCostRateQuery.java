package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.filter.CostRateGridFilter;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IncidentCostRateQuery {


	/**
	 * Move to a Oracle db view in the future.
	 * 
	 * @return
	 */
	public static String getGridQuery(CostRateFilter filter){
		CostRateGridFilter subFilter = (CostRateGridFilter)filter.getSubFilter();
		
		StringBuffer sql = new StringBuffer()

		.append("SELECT k2.id as itemId ")
		.append(" , k2.code as itemCode ")
		.append(" , k2.description as itemDescription ")
		.append(" , RQ.CODE as requestCategory ")
		.append(" , fed.rate_amount as fedRate ")
		.append(" , state.rate_amount as stateRate ")
		.append(" , contr.rate_amount as contrRate ")
		.append(" , wm_concat(state_rate.rate) as customStateRates ")
		.append(" FROM iswl_request_category rq ")
		.append(" ,iswl_kind k2 ");
		if(LongUtility.hasValue(filter.getIncidentId()))
			sql.append(" left join (select icr.incident_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
		else
			sql.append(" left join (select icr.incident_group_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
		sql.append(" from isw_inccost_rate icr ")
		.append(" ,isw_inccost_rate_kind irk ")
		.append(" where irk.inccost_rate_id = icr.id  ")
		.append(" and cost_rate_category = 'CONTRACTOR' ");
		
		if(LongUtility.hasValue(filter.getIncidentId()))
			sql.append(" and incident_id = " + filter.getIncidentId() + " ");
		else
			sql.append(" and incident_group_id = " + filter.getIncidentGroupId() + " ");
				
		sql.append(" ) contr on contr.KIND_ID = K2.id ");
		if(LongUtility.hasValue(filter.getIncidentId()))
			sql.append(" inner join (select icr.incident_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
		else
			sql.append(" inner join (select icr.incident_group_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
		sql.append(" from isw_inccost_rate icr ")
		.append(" ,isw_inccost_rate_kind irk ")
		.append(" where irk.inccost_rate_id = icr.id  ")
		.append(" and cost_rate_category = 'FED' ");
		
		if(LongUtility.hasValue(filter.getIncidentId()))
			sql.append(" and incident_id = " + filter.getIncidentId() + " ");
		else
			sql.append(" and incident_group_id = " + filter.getIncidentGroupId() + " ");

		sql.append(" ) fed on fed.kind_id = k2.id ");
		if(LongUtility.hasValue(filter.getIncidentId()))
			sql.append(" inner join (select icr.incident_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
		else
			sql.append(" inner join (select icr.incident_group_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
		sql.append(" from isw_inccost_rate icr ")
		.append(" ,isw_inccost_rate_kind irk ")
		.append(" where irk.inccost_rate_id = icr.id ")
		.append(" and cost_rate_category = 'STATE_COOP' ");
		
		if(LongUtility.hasValue(filter.getIncidentId()))
			sql.append(" and incident_id = " + filter.getIncidentId() + " ");
		else
			sql.append(" and incident_group_id = " + filter.getIncidentGroupId() + " ");
		sql.append(" ) state on state.kind_id = k2.id ")
		.append(" ,(select (ag.agency_code || '=' || rate_amount) as rate, icrsk.kind_id ")
		.append(" from isw_inccost_rate_state_kind icrsk ")
		.append(" ,isw_inccost_rate_state icrs ")
		.append(" ,isw_inccost_rate icr ")
		.append(" ,iswl_agency ag ")
		.append(" where ");
		
		if(LongUtility.hasValue(filter.getIncidentId()))
			sql.append(" icr.incident_id = " + filter.getIncidentId() + " ");
		else
			sql.append(" icr.incident_group_id = " + filter.getIncidentGroupId() + " ");
		
		sql.append(" and icr.cost_rate_category = 'STATE_COOP_CUSTOM' ")
		.append(" and icrs.inccost_rate_id = icr.id ")
		.append(" and icrsk.inccost_rate_state_id = icrs.id ")
		.append(" and icrs.agency_id = ag.id ")
		.append(" and ag.is_state=1 ")      
		.append(" ) state_rate ")
		.append(" WHERE  ")
		.append(" rq.id = k2.request_category_id ")
		.append(" and state_rate.KIND_ID = k2.id ");
		
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
		sql.append(" group by k2.id, k2.code, k2.description, RQ.CODE, fed.rate_amount, state.rate_amount, contr.rate_amount ")
		.append(" order by itemId asc ");
		 
		return sql.toString();
	} 
	/**
	 * Move to a Postgres db view in the future.
	 * 
	 * @return
	 */
	public static String getGridQuery2(CostRateFilter filter){
		CostRateGridFilter subFilter = (CostRateGridFilter)filter.getSubFilter();
		
		StringBuffer sql = new StringBuffer()
			.append("SELECT k2.id as itemId ")
			.append(" ,k2.code as itemCode ")
			.append(" ,k2.description as itemDescription ")
			.append(" ,RQ.CODE as requestCategory ")
			.append(" ,fed.rate_amount as fedRate ")
			.append(" ,state.rate_amount as stateRate ")
			.append(" ,contr.rate_amount as contrRate ")
			.append(" ,array_to_string(array( ")	  
			.append(" select (ag.agency_code || '=' || rate_amount) as rate ")
        	.append(" from isw_inccost_rate_state_kind icrsk ")
        	.append(" ,isw_inccost_rate_state icrs ")
        	.append(" ,isw_inccost_rate icr ")
        	.append(" ,iswl_agency ag ")
        	.append(" where ");
        	
			if(LongUtility.hasValue(filter.getIncidentId()))
				sql.append(" icr.incident_id = " + filter.getIncidentId() + " ");
			else
				sql.append(" icr.incident_group_id = " + filter.getIncidentGroupId() + " ");
		
        	sql.append(" and icr.cost_rate_category = 'STATE_COOP_CUSTOM' ")
        	.append(" and icrs.inccost_rate_id = icr.id ")
        	.append(" and icrsk.inccost_rate_state_id = icrs.id ")
        	.append(" and icrs.agency_id = ag.id ")
        	.append(" and ag.is_state=true ")
        	.append(" and KIND_ID = k2.id), ',') as customStateRates ")
        	.append(" FROM iswl_request_category rq ")
			.append(" ,iswl_kind k2 ");
        	
			if(LongUtility.hasValue(filter.getIncidentId()))
				sql.append(" left join (select icr.incident_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
			else
				sql.append(" left join (select icr.incident_group_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
				
			sql.append(" from isw_inccost_rate icr ")
			.append(" ,isw_inccost_rate_kind irk ")
			.append(" where irk.inccost_rate_id = icr.id ") 
			.append(" and cost_rate_category = 'CONTRACTOR' ");
			
			if(LongUtility.hasValue(filter.getIncidentId()))
				sql.append( "and incident_id = " + filter.getIncidentId() + " ");
			else
				sql.append( "and incident_group_id = " + filter.getIncidentGroupId() + " ");

			sql.append(" ) contr on contr.KIND_ID = K2.id ");
			
			if(LongUtility.hasValue(filter.getIncidentId()))
				sql.append(" inner join (select icr.incident_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
			else
				sql.append(" inner join (select icr.incident_group_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
			sql.append(" from isw_inccost_rate icr ")
			.append(" ,isw_inccost_rate_kind irk ")
			.append(" where irk.inccost_rate_id = icr.id ") 
			.append(" and cost_rate_category = 'FED' ");
			
			if(LongUtility.hasValue(filter.getIncidentId()))
				sql.append( "and incident_id = " + filter.getIncidentId() + " ");
			else
				sql.append( "and incident_group_id = " + filter.getIncidentGroupId() + " ");
			sql.append(" ) fed on fed.kind_id = k2.id ");
			
			if(LongUtility.hasValue(filter.getIncidentId()))
				sql.append(" inner join (select icr.incident_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
			else
				sql.append(" inner join (select icr.incident_group_id, irk.kind_id, icr.cost_rate_category, irk.rate_amount ");
				
			sql.append(" from isw_inccost_rate icr ")
			.append(" ,isw_inccost_rate_kind irk ")
			.append(" where irk.inccost_rate_id = icr.id  ")
			.append(" and cost_rate_category = 'STATE_COOP' ");
			
			if(LongUtility.hasValue(filter.getIncidentId()))
				sql.append( "and incident_id = " + filter.getIncidentId() + " ");
			else
				sql.append( "and incident_group_id = " + filter.getIncidentGroupId() + " ");
			sql.append(" ) state on state.kind_id = k2.id ")
			.append(" WHERE ")
			.append(" rq.id = k2.request_category_id ");
			
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
			
			sql.append(" order by itemId asc ");

		return sql.toString();
	} 
}
