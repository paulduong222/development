package gov.nwcg.isuite.core.persistence.hibernate.query;

public class DataTransferQuery {
	/*
	   select dt.id
		       , filename
		       , incident_id
		       , null
		       , dt.created_date
		       , stored_filepath
		from isw_data_transfer dt
		       left join isw_incident i on dt.incident_id = i.id
		where incident_id is not null
		and dt.id = (select max(id) from isw_data_transfer where incident_id = dt.incident_id)
		and i.incident_end_date is null
		union
		select dt2.id
			       , filename
			       , null
			       , incident_group_id
			       , dt2.created_date
			       , stored_filepath
		from isw_data_transfer dt2
		      left join isw_incident_group ig on dt2.incident_group_id = ig.id
		where incident_group_id is not null
		and dt2.id = (select max(id) from isw_data_transfer where incident_group_id = dt2.incident_group_id)
		order by filename asc
	 */
	public static String getFileListQuery()  {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select dt.id as id")
	       .append("	    , filename as filename ")
	       .append("		, incident_id as incidentId")
	       .append("		, null as incidentGroupId")
	       .append("		, dt.created_date as createdDate")
	       .append("		, stored_filepath as storedFilepath ")
	       .append("from isw_data_transfer dt ")
	       .append("	left join isw_incident i on dt.incident_id = i.id ")
	       .append("where incident_id is not null ")
	       .append("and dt.id = (select max(id) from isw_data_transfer where incident_id = dt.incident_id) ")
	       .append("and i.incident_end_date is null ")
	       .append("union ")
	       .append("select dt2.id as id")
		   .append("   		, filename as filename")
		   .append("   		, null as incidentId")
		   .append("    	, incident_group_id as incidentGroupId ")
		   .append("    	, dt2.created_date as createdDate")
		   .append("    	, stored_filepath as storedFilepath ")
		   .append("from isw_data_transfer dt2 ")
	       .append("	left join isw_incident_group ig on dt2.incident_group_id = ig.id ")
	       .append("where incident_group_id is not null ")
	       .append("and dt2.id = (select max(id) from isw_data_transfer where incident_group_id = dt2.incident_group_id) ")
	       .append("order by filename asc");
		
		return sql.toString();
	}
}
