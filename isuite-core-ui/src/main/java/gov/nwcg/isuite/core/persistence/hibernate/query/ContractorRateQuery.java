package gov.nwcg.isuite.core.persistence.hibernate.query;

public class ContractorRateQuery {
	
	public static String getAssignmentTimePostingCountQuery() {
		String sql = 
			"SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST " +
			"WHERE REF_CONTRACTOR_RATE_ID = :id";
		return sql;
	}

}
