package gov.nwcg.isuite.framework.types;

/**
 * 
 * The categories available that provides a way of checking to see whether or not sections of a page are
 * viewable by the current user (based on their roles/authorities).
 * 
 */

public enum SecurityGroupEnum {
	COMMON_DATA(1),
	PLANS_DATA(2),
	EMPLOYMENT_DATA(3),
	HIRED_DATA(4),
	SOCIAL_SECURITY_NUMBER(5),
//	RESOURCE_ADDRESS(6),
	CONTRACTOR_DATA(6),
	CONTRACTOR_AGREEMENT(7),
	CONTRACTOR_RATE(8),
//	COST_RESOURCE_DATA(9),
	ROSTER(10),
	POST_TIME(11),
	TIME_ADJUSTMENTS(12),
	ADMINISTRATIVE_OFFICE_FOR_PAYMENT(13),
	DAILY_COSTS(14),
	ACRES_BURNED(15),
	COST_ANALYSIS_BENCHMARK_REPORT_SETUP(16),
	COST_DIVISIONS(17),
	COST_ACCRUAL_EXTRACT(18),
	COST_PROJECTIONS(19),
	COST_RATES(20),
	DEMOB_DATA(21),
	TENTATIVE_RELEASE_DATA(22),
	AIR_TRAVEL_DATA(23),
	ACTUAL_RELEASE_DATA(24),
	INJURY_ILLNESS_DATA(25),
//	RESOURCES(27),
	ADMINISTRATION(26),
	IAP(27);
	
	private int groupNumber = -1;
	private SecurityGroupEnum(int value) {
		groupNumber = value;
	}
	
	public int getGroupNumber() {
		return groupNumber;
	}
}
