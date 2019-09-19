package gov.nwcg.isuite.core.persistence.hibernate.query;

public class IncidentGroupPrefsQuery {

	public static enum CheckOutPrefsEnum {
		LOGISTICS1("LOGISTICS", "Supply Unit")
		,LOGISTICS2("LOGISTICS", "Communications Unit")
		,LOGISTICS3("LOGISTICS", "Facilities Unit")
		,LOGISTICS4("LOGISTICS", "Ground Support Unit")
		,PLANNING1("PLANNING", "Documentation Unit")
		,PLANNING2("PLANNING", "Demob Unit")
		,FINANCE1("FINANCE", "Time Unit")
		,OTHER_LABEL1("OTHER_LABEL","Security Unit")
		;

		public String sectionName = "";
		public String fieldLabel = "";
		
		CheckOutPrefsEnum(String sectName, String fldLabel){
			this.sectionName = sectName;
			this.fieldLabel=fldLabel;
		}
	}

	public static String getUpdateIncidentQuery(Long groupId,CheckOutPrefsEnum cop ) {
		StringBuffer sql = new StringBuffer();

		sql.append("update isw_incident_prefs ip ")
	       .append("set position=(select ip2.position from isw_incident_group_prefs ip2 where ip2.incident_group_id = "+groupId+" and ip2.section_name=ip.section_name and ip2.field_label=ip.field_label) ")
	       .append(",is_selected=(select ip2.is_selected from isw_incident_group_prefs ip2 where ip2.incident_group_id = "+groupId+" and ip2.section_name=ip.section_name and ip2.field_label=ip.field_label) ")
	       .append("where incident_id in ( select incident_id from isw_incident_group_incident where incident_group_id = "+groupId+") ")
	       .append("and section_name='"+cop.sectionName+"' ")
	       .append("and field_label='"+cop.fieldLabel+"' ");
		
		return sql.toString();
	}

	public static String getUpdateIncidentGroupQuery(Long incidentId, Long groupId,CheckOutPrefsEnum cop ) {
		StringBuffer sql = new StringBuffer();

		sql.append("update isw_incident_group_prefs ip ")
	       .append("set position=(select ip2.position from isw_incident_prefs ip2 where ip2.incident_id = "+incidentId+" and ip2.section_name=ip.section_name and ip2.field_label=ip.field_label) ")
	       .append(",is_selected=(select ip2.is_selected from isw_incident_prefs ip2 where ip2.incident_id = "+incidentId+" and ip2.section_name=ip.section_name and ip2.field_label=ip.field_label) ")
	       .append("where incident_group_id = " + groupId + " ")
	       .append("and section_name='"+cop.sectionName+"' ")
	       .append("and field_label='"+cop.fieldLabel+"' ");
		
		return sql.toString();
	}

	public static String getUpdateOtherFieldQuery(Long ipId, Long ipGrpId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("update isw_incident_prefs ip ")
	       .append("set position=(select ip2.position from isw_incident_group_prefs ip2 where ip2.id = "+ipGrpId+" ) ")
	       .append(" ,is_selected=(select ip2.is_selected from isw_incident_group_prefs ip2 where ip2.id = "+ipGrpId+" ) ")
	       .append(" ,field_label = (select ip2.field_label from isw_incident_group_prefs ip2 where ip2.id = "+ipGrpId+" ) ")
	       .append("where id = " + ipId + " ");

		return sql.toString();
	}

	public static String getUpdateOtherFieldGroupQuery(Long ipId, Long ipGrpId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("update isw_incident_group_prefs ip ")
	       .append("set position=(select ip2.position from isw_incident_prefs ip2 where ip2.id = "+ipId+" ) ")
	       .append(" ,is_selected=(select ip2.is_selected from isw_incident_prefs ip2 where ip2.id = "+ipId+" ) ")
	       .append(" ,field_label = (select ip2.field_label from isw_incident_prefs ip2 where ip2.id = "+ipId+" ) ")
	       .append("where id = " + ipGrpId + " ");

		return sql.toString();
	}

}
