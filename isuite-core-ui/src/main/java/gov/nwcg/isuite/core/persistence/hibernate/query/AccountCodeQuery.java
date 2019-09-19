package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.filter.IncidentAccountCodeFilter;
import gov.nwcg.isuite.framework.util.StringUtility;

public class AccountCodeQuery {

	/*
	 *  Finds accountCodeImpl by its known key (accountCode + agency)
	 */
	public static final String FIND_BY_KEY="FIND_BY_KEY";
	public static final String FIND_BY_KEY_QUERY =
		"SELECT ac FROM AccountCodeImpl ac " +
		"WHERE ac.accountCode = :accountcode " +
		"AND ac.agency.id = :agencyid ";

	public static String getAccountCodesByIncidentId(Long incidentId, IncidentAccountCodeFilter filter) throws Exception {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT iac.id as incidentAccountCodeId,")
		   .append("iac.incident_id as incidentId, ")
           .append("ac.account_code as accountCode,")
           .append("ag.AGENCY_CODE as agency,")
           .append("o.unit_code as regionUnit ")
           .append("FROM ISW_INCIDENT_ACCOUNT_CODE iac ")
           .append("left join ISW_ACCOUNT_CODE ac on iac.account_code_id = ac.id ")
           .append("left join ISWL_AGENCY ag on ac.agency_id = ag.id ")
           .append("left join isw_organization o on ac.region_unit_id = o.id ")
           .append("WHERE iac.incident_id = " + incidentId + " ")
           ;

		if(null != filter){
			if(StringUtility.hasValue(filter.getAccountCode()))
				sql.append("AND ac.account_code LIKE '%" + filter.getAccountCode() + "%' ");

			if(StringUtility.hasValue(filter.getAgencyCode()))
				sql.append("AND ag.agency_code LIKE '%" + filter.getAgencyCode() + "%' ");
			
			if(StringUtility.hasValue(filter.getRegionUnitCode()))
				sql.append("AND o.unit_code LIKE '%" + filter.getRegionUnitCode() + "%' ");
		}
		
		return sql.toString();
	}
}
