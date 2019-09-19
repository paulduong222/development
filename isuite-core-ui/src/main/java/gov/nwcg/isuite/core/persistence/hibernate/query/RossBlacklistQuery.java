package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.util.Collection;


public class RossBlacklistQuery {

	/**
	 * @param isOracle
	 * @param rossXmlFileId
	 * @return
	 * @throws Exception
	 */
	public static String getRossIncDataInsertQuery(Boolean isOracle, Long rossXmlFileId) {
		StringBuffer sql = new StringBuffer()
			.append("INSERT INTO ISW_ROSS_INC_DATA_BLACKLIST (ID,RES_ID, ROSS_INC_ID) ")
			.append("SELECT " + (isOracle ? "SEQ_ISW_ROSS_INC_DATA_BL.nextVal" : "nextVal('SEQ_ISW_ROSS_INC_DATA_BL') " ) + ", RXFD.RES_ID, RXF.ROSS_INC_ID ")
			.append("FROM ISW_ROSS_XML_FILE_DATA RXFD , ISW_ROSS_XML_FILE RXF ")
			.append("WHERE RXFD.ROSS_XML_FILE_ID = RXF.ID " )
			.append("AND RXF.ID = " + rossXmlFileId + " ")
			.append("AND RXFD.RES_ID = :resid ")
			.append("AND RXFD.IS_ROSS_ASSIGNMENT = 1 " )
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
			.append("AND RXFD.RES_ID NOT IN ( SELECT RES_ID FROM ISW_ROSS_INC_DATA_BLACKLIST WHERE ROSS_INC_ID = RXF.ROSS_INC_ID) ");
		
		return sql.toString();
	}
	
	public static String getRossIncidentsQuery(Boolean isOracle) {
		StringBuffer sql = new StringBuffer()
			.append("")
			.append("SELECT I.id as id ")
            .append(",I.ROSS_INCIDENT_ID as rossIncidentId ")
            .append(", I.INCIDENT_NAME as incidentName ")
            .append(", 'US'||'-'||o.unit_code||'-'||i.nbr as incidentNumber ")
            .append(", CS.CS_ABBREVIATION as countrySubdivisionCode ")
            .append(", I.INCIDENT_START_DATE as incidentStartDt ")
            .append(", i.ross_xml_file_id as rossXmlFileId ")
            .append("FROM isw_incident i ")
            .append("   LEFT JOIN iswl_country_subdivision cs ON I.COUNTRY_SUBDIVISION_ID = cs.id ")
            .append("   LEFT JOIN isw_organization o on i.unit_id = o.id ")
            .append(" WHERE i.ross_incident_id is not null ")
            .append("and i.ross_incident_id != 'NaN' ")
            .append("and (select count(*) from isw_ross_inc_data_blacklist where ross_inc_id = i.ross_incident_id) > 0 ")
			.append("");
		
		return sql.toString();
	}
	
	public static String getRossIncDataResources(Boolean isOracle, String rossIncId) {
		StringBuffer sql = new StringBuffer()
			.append("")
			.append("select distinct(rxfd.res_id) as rossResourceId, RXFD.REQ_NUMBER as requestNumber, rxfd.res_name as resourceName, RXFD.INC_AGENCY_ABBREV as agency ")
			.append("from isw_ross_xml_file_data rxfd, isw_ross_inc_data_blacklist bdata, isw_ross_xml_file rxf ")
			.append("where rxfd.RES_ID = bdata.res_id ")
			.append("and bdata.ross_inc_id = '"+rossIncId+"' ")
			.append("and rxfd.ross_xml_file_id = rxf.id ")
			.append("and rxf.ross_inc_id = '" +rossIncId+"' ")
			.append("and (bdata.import_status is null or bdata.import_status not in ('IMPORTED','INPROCESS') )");
		
		return sql.toString();
	}

	public static String getRossIncExcludedResources(Boolean isOracle, String rossIncId) {
		StringBuffer sql = new StringBuffer()
			.append("")
			.append("select distinct(rxfd.req_id) as rossResReqId ")
			.append(", RXFD.REQ_NUMBER as requestNumber ")
			.append(", rxfd.res_name as resourceName ")
			.append(", rxfd.first_name as firstName ")
			.append(", rxfd.last_name as lastName ")
			.append(", rxfd.assignment_name as assignmentName ")
			.append(", RXFD.INC_AGENCY_ABBREV as agency ")
			.append("from isw_ross_xml_file_data rxfd, isw_ross_inc_data_blacklist bdata ")
			//.append(",isw_ross_xml_file rxf ")
			.append("where rxfd.REQ_ID = bdata.ross_res_req_id ")
			.append("and bdata.ross_inc_id = '"+rossIncId+"' ")
			//.append("and rxfd.ross_xml_file_id = rxf.id ")
			//.append("and rxf.ross_inc_id = '" +rossIncId+"' ")
			.append("and (bdata.import_status is null or bdata.import_status not in ('IMPORTED','INPROCESS') )");
		
		return sql.toString();
	}

	public static String getUpdateStatuses(Boolean isOracle, Long rxfId, String status) {
		StringBuffer sql = new StringBuffer()
			.append("")
			.append("UPDATE ISW_ROSS_XML_FILE_DATA ")
			.append("SET IMPORT_STATUS = '" + status + "' ")
			.append("WHERE ROSS_XML_FILE_ID = '" + rxfId + "' " )
			.append("AND RES_ID IN ( :ids ) ");
		
		return sql.toString();
	}

}
