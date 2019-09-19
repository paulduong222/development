package gov.nwcg.isuite.core.persistence.hibernate.query;

public class ItemCodeQuery {
	
	public static String getInsertIncidentCostRateKind(Boolean isOracle, Long kindId, String reqCatCode, Long incidentId) {
		StringBuffer sql = new StringBuffer()
			.append("INSERT INTO ISW_INCCOST_RATE_KIND ")
			.append("(ID, KIND_ID, RATE_TYPE, RATE_AMOUNT, INCCOST_RATE_ID, IS_LOCKED) ")
			.append("SELECT ")
			.append((isOracle ? "SEQ_INCCOST_RATE_KIND.NEXTVAL" : "nextVal('SEQ_INCCOST_RATE_KIND')"))
			.append(", " + kindId)
			.append(", ")
			.append((reqCatCode.equalsIgnoreCase("O")) ? "'HOURLY'" : "'DAILY'")
			.append(", 0")
			.append(", ICR.ID")
			.append(", " + (isOracle ? "0 " : "FALSE " ))
			.append("FROM ISW_INCCOST_RATE ICR ")
			.append("WHERE ICR.INCIDENT_ID = " + incidentId)
			.append(" AND ICR.COST_RATE_CATEGORY IN ")
			.append("('FED','STATE_COOP','CONTRACTOR') ");
		
		return sql.toString();
	}
	
	public static String getInsertIncidentCostRateStateKind(Boolean isOracle, Long kindId, String reqCatCode, Long incidentId) {
		StringBuffer sql = new StringBuffer()
			.append("INSERT INTO ISW_INCCOST_RATE_STATE_KIND ")
			.append("(ID, INCCOST_RATE_STATE_ID, KIND_ID, RATE_TYPE, RATE_AMOUNT, IS_LOCKED) ")
			.append("SELECT ")
			.append((isOracle ? "SEQ_INCCOST_RATE_STATE_KIND.NEXTVAL" : "nextVal('SEQ_INCCOST_RATE_STATE_KIND')"))
			.append(", IRS.ID")
			.append(", " + kindId)
			.append(", ")
			.append((reqCatCode.equalsIgnoreCase("O")) ? "'HOURLY'" : "'DAILY'")
			.append(", 0")
			.append(", " + (isOracle ? "0 " : "FALSE " ))
			.append("FROM ISW_INCCOST_RATE_STATE IRS ")
			.append(", ISW_INCCOST_RATE IR ")
			.append("WHERE IRS.INCCOST_RATE_ID = IR.ID ")
			.append("AND IR.INCIDENT_ID = " + incidentId)
			.append(" AND IR.COST_RATE_CATEGORY = 'STATE_COOP_CUSTOM' ");
		
		return sql.toString();
	}
	

}
