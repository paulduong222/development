package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.framework.util.LongUtility;

public class ContractorQuery {
	
	public static final String DISABLE_CONTRACTORS="DISABLE_CONTRACTORS";
	public static final String DISABLE_CONTRACTORS_QUERY = 
		"UPDATE ContractorImpl contractor " +
		"SET contractor.enabled = :enabled " +
		"WHERE contractor.id IN ( :ids )";
	
	public static final String ENABLE_CONTRACTORS="ENABLE_CONTRACTORS";
	public static final String ENABLE_CONTRACTORS_QUERY =
		"UPDATE ContractorImpl contractor " +
		"SET contractor.enabled = :enabled " +
		"WHERE contractor.id IN ( :ids )";
	
	public static String getContractedResourcesWithOriginalInvoices(Long contractorId, Boolean isOracle) {
		StringBuffer b = new StringBuffer();
		
		b.append("SELECT DISTINCT CASE WHEN R.IS_PERSON = " + (isOracle ? 1 : Boolean.TRUE) + 
				" THEN R.LAST_NAME || ', ' || R.FIRST_NAME ELSE R.RESOURCE_NAME END AS resourceName ")
		.append("FROM ISW_RESOURCE R, ")
		.append("ISW_INCIDENT_RESOURCE IR, ")
		.append("ISW_WORK_PERIOD WP, ")
		.append("ISW_WORK_PERIOD_ASSIGNMENT WPA, ")
		.append("ISW_ASSIGNMENT A, ")
		.append("ISW_ASSIGNMENT_TIME AT, ")
		.append("ISW_ASSIGN_TIME_POST ATP, ")
		.append("ISW_ASSIGN_TIME_POST_INVOICE ATPI, ")
		.append("ISW_TIME_INVOICE TI ")
		.append("WHERE R.ID = IR.RESOURCE_ID ")
		.append("AND IR.ID = WP.INCIDENT_RESOURCE_ID ")
		.append("AND WP.ID = WPA.WORK_PERIOD_ID ")
		.append("AND WPA.ASSIGNMENT_ID = A.ID ")
		.append("AND A.ID = AT.ASSIGNMENT_ID ")
		.append("AND AT.ID = ATP.ASSIGNMENT_TIME_ID ")
		.append("AND ATP.ID = ATPI.ASSIGN_TIME_POST_ID ")
		.append("AND ATPI.TIME_INVOICE_ID = TI.ID ")
		.append("AND TI.CONTRACTOR_ID = " + contractorId + " ")
		.append("AND TI.IS_DRAFT = " + (isOracle ? 0 : Boolean.FALSE));
		
		return b.toString();
		
	}
	
	public static String getAssignmentTimePostingCountQuery() {
		String sql = 
			"SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST " +
			"WHERE REF_CONTRACTOR_ID = :id";
		return sql;
	}
	
	public static String REMOVE_CONTR_FROM_CONTR_PAYMENT_INFO_QUERY =
		"UPDATE ISW_CONTR_PAYMENT_INFO SET CONTRACTOR_ID = NULL WHERE CONTRACTOR_ID = :contractorId";
	
	public static String REMOVE_CONTR_AGREEMENT_FROM_CONTR_PAYMENT_INFO_QUERY =
		"UPDATE ISW_CONTR_PAYMENT_INFO SET CONTRACTOR_AGREEMENT_ID = NULL WHERE CONTRACTOR_AGREEMENT_ID IN (:agreementIds)";

	public static String getLightListQuery(Long incidentId, Long incidentGroupId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id as id ")
		  .append("       , name as name ")
		  .append("from isw_contractor ")
		  .append("where id in (");
		if(LongUtility.hasValue(incidentId)){
		   sb.append("select contractor_id from isw_incident_contractor where incident_id = " + incidentId + " ");
		}else if(LongUtility.hasValue(incidentGroupId)){
			   sb.append("select contractor_id from isw_incident_contractor where incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ");
		}
		sb.append(") ")
		  .append(" and deleted_date is null ")
		  .append("order by name asc ");
		return sb.toString();
	}
	
	public static String getAgreementsQuery(Long incidentId, Long incidentGroupId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id")
			.append(", agreement_number as agreementNumber ")
			.append(", contractor_id as contractorId ")
			.append("from isw_contractor_agreement where contractor_id in ( ");
			if(LongUtility.hasValue(incidentId)){
			   sb.append("select contractor_id from isw_incident_contractor where incident_id = " + incidentId + " ")
			   .append(" and deleted_date is null ");
			}else if(LongUtility.hasValue(incidentGroupId)){
				sb.append("select contractor_id from isw_incident_contractor where incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ")
				.append(" and deleted_date is null ");
			}
	  sb.append(") ");
		return sb.toString();
	}

}
