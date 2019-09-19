package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.filter.ResourcePersonFilter;

public class ResourceQuery {
	
	/*
	 *  Deletes resourceImpls by setting deleted_date value and enabled to false
	 */
	public static final String DELETE_RESOURCES="DELETE_RESOURCES";
	public static final String DELETE_RESOURCES_QUERY =
		"UPDATE ResourceImpl r " +
		"SET r.enabled = :enabled " +
		", r.deletedDate = :deletedDate " +
		", leader_type = 99 "+
		"WHERE r.id IN ( :ids )";

	public static final String getDeleteResourceQuery() {
		StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM ISW_INCIDENT_RESOURCE WHERE resource_id in ( :ids ) ");
		
		return sql.toString();
	}
	
	/*
	 *  Deletes resourceImpls by setting deleted_date value and enabled to false
	 *  and sets parentResourceId to null
	 */
	public static final String DELETE_INCIDENT_RESOURCES_REMOVE_PARENT_ASSOC="DELETE_INCIDENT_RESOURCES_REMOVE_PARENT_ASSOC";
	public static final String DELETE_INCIDENT_RESOURCES_REMOVE_PARENT_ASSOC_QUERY =
		"UPDATE ResourceImpl r " +
		"SET r.enabled = :enabled " +
		", r.parentResourceId = NULL " +
		", r.component = :component " +
		", r.deletedDate = :deletedDate " +
		", leader_type = 99 " +
		"WHERE r.id IN ( :ids )";
		
	/*
	 *  Deletes resourceImpls by setting deleted_date value and enabled to false
	 *  and sets parentResourceId to null
	 */
	public static final String DELETE_WORK_AREA_RESOURCES_REMOVE_PARENT_ASSOC="DELETE_WORK_AREA_RESOURCES_REMOVE_PARENT_ASSOC";
	public static final String DELETE_WORK_AREA_RESOURCES_REMOVE_PARENT_ASSOC_QUERY =
		"UPDATE ResourceImpl r " +
		"SET r.enabled = :enabled " +
		", r.parentResourceId = NULL " +
		", r.component = :component " +
		", r.deletedDate = :deletedDate " +
		"WHERE r.permanentResourceId IN ( :ids )";
	
	/*
	 *  Disables resourceImpls by setting enabled to false
	 */
	public static final String DISABLE_RESOURCES="DISABLE_RESOURCES";
	public static final String DISABLE_RESOURCES_QUERY =
		"UPDATE ResourceImpl r " +
		"SET r.enabled = :enabled " +
		"WHERE r.id IN ( :ids )";

	/*
	 *  Disables resourceImpls by setting enabled to false
	 *  and sets parentResourceId to null
	 */
	public static final String DISABLE_RESOURCES_REMOVE_PARENT_ASSOC="DISABLE_RESOURCES_REMOVE_PARENT_ASSOC";
	public static final String DISABLE_RESOURCES_REMOVE_PARENT_ASSOC_QUERY =
		"UPDATE ResourceImpl r " +
		"SET r.enabled = :enabled " +
		", r.parentResourceId = NULL "+
		", r.component = :component " +
		"WHERE r.permanentResourceId IN ( :ids )";
	
	/*
	 *  
	 */
	public static final String REMOVE_INCIDENT_RESOURCES_ROSTERED_CHILDREN ="REMOVE_INCIDENT_RESOURCES_ROSTERED_CHILDREN";
	public static final String REMOVE_INCIDENT_RESOURCES_ROSTERED_CHILDREN_QUERY = 
		"UPDATE ResourceImpl r " + 
		"SET r.parentResourceId = NULL " +
		", r.leaderType = 99 " +
		", r.component = :component " +
		//", RESOURCE_CLASSIFICATION = 'S' " +
		"WHERE r.parentResourceId IN ( :ids )";
	
	/*
	 *  
	 */
	public static final String REMOVE_WORK_AREA_RESOURCES_ROSTERED_CHILDREN ="REMOVE_WORK_AREA_RESOURCES_ROSTERED_CHILDREN";
	public static final String REMOVE_WORK_AREA_RESOURCES_ROSTERED_CHILDREN_QUERY = 
		"UPDATE ResourceImpl r " + 
		"SET r.parentResourceId = NULL " +
		", r.leaderType = 99 " +
		", r.component = :component " +
		//", RESOURCE_CLASSIFICATION = 'S' " +
		"WHERE r.parentResourceId IN " +
		"(SELECT r2.id FROM ResourceImpl r2 WHERE r2.permanentResourceId IN ( :ids ))";

	/*
	 *  Enables resourceImpls by setting enabled to true
	 */
	public static final String ENABLE_RESOURCES="ENABLE_RESOURCES";
	public static final String ENABLE_RESOURCES_QUERY =
		"UPDATE ResourceImpl r " +
		"SET r.enabled = :enabled " +
		"WHERE r.permanentResourceId IN ( :ids )";

	public static String getPersonResourcesSQLQuery(Boolean isOracle, ResourcePersonFilter filter) {
	   StringBuffer sql = new StringBuffer();
	   sql.append("select ")
      .append("distinct r.id as resourceId, ")
      .append("o.id as organizationId, ")
      .append("o.unit_code as unitCode, ")
      .append("r.first_name as firstName, ") 
      .append("r.last_name as lastName, ")
      .append("r.phone as workPhone, ")
      .append("r.phone as cellPhone, ")
      .append("r.email as email ") 
      .append("from isw_resource r, isw_organization o ")
      .append("where r.is_person = " + (isOracle ? "1 " : "true "))
      .append("and r.is_enabled = " + (isOracle ? "1 " : "true "))
      .append("and r.permanent_resource_id is not null ")
      .append("and r.organization_id = o.id ");
      if(filter != null) {
         if(filter.getFirstName() != null && filter.getFirstName().trim().length() > 0) {
            sql.append("and r.first_name like '" + filter.getFirstName().toUpperCase().trim() + "%' ");
         }
         if(filter.getLastName() != null && filter.getLastName().trim().length() > 0) {
            sql.append("and r.last_name like '" + filter.getLastName().toUpperCase().trim() + "%' ");
         }
         if(filter.getUnitCode() != null && filter.getUnitCode().trim().length() > 0) {
            sql.append("and o.unit_code like '" + filter.getUnitCode().toUpperCase().trim() + "%' ");
         }
      }
      sql.append("order by r.last_name");
	   return sql.toString();
   }
}
