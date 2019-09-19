package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.framework.util.LongUtility;

public class TrainingSpecialistQuery {
	
	public static String getAvailableFuelTypes(Long resourceTrainingId) {
		StringBuffer b = new StringBuffer();
		
		b.append("SELECT F.ID, ")
		.append("F.CODE, ")
		.append("F.DESCRIPTION ")
		.append("FROM ISWL_FUEL_TYPE F ")
		.append("AND NOT F.ID IN ")
		.append("(SELECT RF.FUEL_TYPE_ID FROM ISW_RSC_TRAINING_FUEL_TYPE RF ")
		.append(" WHERE RF.RESOURCE_TRAINING_ID = " + resourceTrainingId + ")" );
		
		return b.toString();
	}
	
	public static String getSelectedFuelTypes(Long resourceTrainingId) {
		StringBuffer b = new StringBuffer();
		
		b.append("SELECT F.ID, ")
		.append("F.CODE, ")
		.append("F.DESCRIPTION ")
		.append("FROM ISWL_FUEL_TYPE F ")
		.append("JOIN ISW_RSC_TRAINING_FUEL_TYPE RF ")
		.append("ON F.ID = RF.FUEL_TYPE_ID ")
		.append("WHERE RF.RESOURCE_TRAINING_ID = " + resourceTrainingId + ")" );
		
		return b.toString();
	}
	
	public static String getIncidentEvaluatorsGrid(Boolean isOracle, Long incidentResourceId, Long incidentId, Long incidentGroupId){
		StringBuffer sql = new StringBuffer()
		.append("SELECT IR.ID as id ")
		.append(", K.ID as itemCodeId ")
		.append(", K.CODE as itemCode ")
		.append(", K.DESCRIPTION as itemDescription ")
		.append(", R.ORGANIZATION_ID as organizationId ")
		.append(", A.ASSIGNMENT_STATUS as status ")
		.append(", A.REQUEST_NUMBER as requestNumber ")
		.append(", sortRequestNumber(A.REQUEST_NUMBER) as sortRequestNumberField ")
		.append(", r.last_name || ', ' || r.first_name ")
		.append("as resourceName ")
		.append("from isw_incident_resource ir ")
		.append(",isw_resource r ")
		.append(" left join iswl_agency ag on ag.id = r.agency_id ")
		.append(",isw_work_period wp ")
		.append(",isw_work_period_assignment wpa ")
		.append(",isw_assignment a ")
		.append(",iswl_kind k ")
		.append("where r.id = ir.resource_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and k.id = a.kind_id ")
		.append("and A.END_DATE is null ")
		.append("and R.IS_ENABLED = " + (isOracle ? 1 : true) + " ")
		.append("and R.IS_PERSON = " + (isOracle ? 1 : true) + " ")
		.append("and not ir.id = " + incidentResourceId + " ") ;
		
		if (LongUtility.hasValue(incidentGroupId)){
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ")");
		}else{
			sql.append("and ir.incident_id = " + incidentId + " ");
		}
		sql.append("order by sortRequestNumberField ");

	return sql.toString();
	}
	
	public static String getTraineeTotal(Long incidentId, Long incidentGroupId, Boolean isOracle){
		StringBuffer sql = new StringBuffer()
		.append("select count(distinct ir.id) ")
		.append("from isw_incident_resource ir, ")
		.append("isw_resource_training rt ");
		//.append("where ir.id = rt.incident_resource_id ");
		
		if (LongUtility.hasValue(incidentGroupId)){
			sql.append("where ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ")");
		}else{
			sql.append("where ir.incident_id = " + incidentId + " ");
		};
		
		sql.append("and ")
		   .append("(")
		   .append("   (")
		   .append("     ir.id = rt.incident_resource_id ")
		   .append("   ) ")
		   .append("   OR " )
		   .append("   (")
		   .append("     ir.id in (")
		   .append("       select wp.incident_resource_id ")
		   .append("       from isw_work_period wp ")
		   .append("            , isw_work_period_assignment wpa ")
		   .append("            , isw_assignment a ")
		   .append("       where wp.id = wpa.work_period_id ")
		   .append("       and wpa.assignment_id = a.id ")
		   .append("       and a.is_training = " + (isOracle ? 1 : true) + " ")
		   .append("     ) ")
		   .append("   )" )
		   .append(") ");
		return sql.toString();
	}
	
	public static String getPriorityTrainees(Long incidentId, Long incidentGroupId){
		StringBuffer sql = new StringBuffer()
		//.append("select count(distinct ir.id) ")
		.append("select count(distinct rt.id) ")
		.append("from isw_incident_resource ir, ")
		.append("isw_resource_training rt ")
		.append("where rt.incident_resource_id = ir.id ")
		.append("and rt.is_fs_priority_trainee = 'Y' ");
		
		if (LongUtility.hasValue(incidentGroupId)){
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ")");
		}else{
			sql.append("and ir.incident_id = " + incidentId + " ");
		}
		
		return sql.toString();
	}
	
	public static String getContactResourcesGrid(Boolean isOracle, Long incidentId, Long incidentGroupId){
		StringBuffer sql = new StringBuffer()
		.append("SELECT IR.ID as incidentResourceId ")
		.append(", IR.ACTIVE as active ")
		.append(", K.CODE as itemCode ")
		.append(", K.DESCRIPTION as itemDescription ")
		.append(", O.UNIT_CODE as unitId ")
		.append(", O.ORGANIZATION_NAME as unitDescription ")
		.append(", A.ASSIGNMENT_STATUS as status ")
		.append(", A.REQUEST_NUMBER as requestNumber ")
//		.append(", sortRequestNumber(A.REQUEST_NUMBER) as sortRequestNumberField ")
		.append(", r.last_name || ', ' || r.first_name ")
		.append("as resourceName ")
		.append("from isw_incident_resource ir ")
		.append(",isw_resource r ")
		.append(" left join iswl_agency ag on ag.id = r.agency_id ")
		.append(" left join isw_organization o on r.organization_id = o.id ")
		.append(",isw_work_period wp ")
		.append(",isw_work_period_assignment wpa ")
		.append(",isw_assignment a ")
		.append(",iswl_kind k ")
		.append("where r.id = ir.resource_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and k.id = a.kind_id ")
		.append("and A.END_DATE is null ")
		.append("and R.IS_ENABLED = " + (isOracle ? 1 : true) + " ")
		.append("and R.IS_PERSON = " + (isOracle ? 1 : true) + " ")
		.append("and IR.ID NOT IN (select incident_resource_id from isw_training_contact)");
		
		if (LongUtility.hasValue(incidentGroupId)){
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ")");
		}else{
			sql.append("and ir.incident_id = " + incidentId + " ");
		}
//		sql.append("order by sortRequestNumberField ");
		sql.append("order by sortRequestNumber(A.REQUEST_NUMBER) ");

		return sql.toString();
	}
	
	public static String getTrainingContactGrid(Boolean isOracle, Long incidentId, Long incidentGroupId){
		StringBuffer sql = new StringBuffer()
		.append("SELECT IR.ID as incidentResourceId ")
		.append(", IR.ACTIVE as active ")
		.append(", K.CODE as itemCode ")
		.append(", K.DESCRIPTION as itemDescription ")
		.append(", O.UNIT_CODE as unitId ")
		.append(", O.ORGANIZATION_NAME as unitDescription ")
		.append(", A.ASSIGNMENT_STATUS as status ")
		.append(", A.REQUEST_NUMBER as requestNumber ")
//		.append(", sortRequestNumber(A.REQUEST_NUMBER) as sortRequestNumberField ")
		.append(", r.last_name || ', ' || r.first_name ")
		.append("as resourceName ")
		.append("from isw_training_contact tc ")
		.append(",isw_incident_resource ir ")
		.append(",isw_resource r ")
		.append(" left join iswl_agency ag on ag.id = r.agency_id ")
		.append(" left join isw_organization o on r.organization_id = o.id ")
		.append(",isw_work_period wp ")
		.append(",isw_work_period_assignment wpa ")
		.append(",isw_assignment a ")
		.append(",iswl_kind k ")
		.append("where tc.incident_resource_id = ir.id ")
		.append("and r.id = ir.resource_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and k.id = a.kind_id ")
		.append("and A.END_DATE is null ")
		.append("and R.IS_ENABLED = " + (isOracle ? 1 : true) + " ")
		.append("and R.IS_PERSON = " + (isOracle ? 1 : true) + " ");
		
		if (LongUtility.hasValue(incidentGroupId)){
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ")");
		}else{
			sql.append("and ir.incident_id = " + incidentId + " ");
		}
//		sql.append("order by sortRequestNumberField ");
		sql.append("order by sortRequestNumber(A.REQUEST_NUMBER) ");

		return sql.toString();
	}
	
	
}
