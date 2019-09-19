package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.xml.SystemRoleType;


public class UserQuery {

	/*
	 *  Disables userImpls by setting enabled to false
	 */
	public static final String DISABLE_USERS="DISABLE_USERS";
	public static final String DISABLE_USERS_QUERY =
		"UPDATE UserImpl user " +
		"SET user.enabled = :enabled " +
		"WHERE user.id IN ( :ids )";

	/*
	 *  Enables UserImpls by setting enabled to true
	 */
	public static final String ENABLE_USERS="ENABLE_USERS";
	public static final String ENABLE_USERS_QUERY =
		"UPDATE UserImpl user " +
		"SET user.enabled = :enabled " +
		"WHERE user.id IN ( :ids )";

//	/*
//	 *  Deletes UserImpls by setting deleted_date
//	 */
//	public static final String DELETE_USERS="DELETE_USERS";
//	public static final String DELETE_USERS_QUERY =
//		"UPDATE UserImpl user " +
//		"SET user.deletedDate = :deletedDate " +
//		"WHERE user.id IN ( :ids )";
	
	/**
    * Delete the selected {@link User} objects.
    */
   public static final String DELETE_USERS="DELETE_USERS";
   public static final String DELETE_USERS_QUERY =
    "delete from UserImpl " +
    "where id in (:ids)";
   
   public static final String DELETE_SELECTED_USER_ID_FROM_ROLE_TABLE = 
      "DELETE_SELECTED_USER_ID_FROM_AUTHORITY_TABLE";
   public static final String DELETE_SELECTED_USER_ID_FROM_AUTHORITY_TABLE_QUERY = 
      "delete from UserAuthorityImpl " +
      "where userId in (:ids)";
   
   public static final String DELETE_SELECTED_USER_ID_FROM_USER_ORG=
      "DELETE_SELECTED_USER_ID_FROM_USER_ORG"; 
   public static final String DELETE_SELECTED_USER_ID_FROM_USER_ORG_SQL_QUERY = 
      "delete from isw_user_organization " +
      "where user_id in (:ids)";
   
   
   public static final String GET_PERMISSIONS_FOR_USER_SQL_QUERY = 
	   "SELECT DISTINCT(mp.permission_key) FROM " +
	   "ISW_SYSTEM_MODULE_PERM MP, " +
	   "ISW_SYSTEM_ROLE_PERM SRP, " +
	   "ISW_USER U, " +
	   "ISW_USER_ROLE UR " +
	   "WHERE UR.USER_ID = U.ID " +
	   "AND (" +
	   " SRP.ROLE_ID = UR.ROLE_ID " +
	   " OR " +
	   " SRP.ROLE_ID = (SELECT ID FROM ISW_SYSTEM_ROLE WHERE ROLE_NAME = '" + SystemRoleType.ROLE_SYSTEM.name() + "') " +
	   " ) " +
	   "AND MP.ID = SRP.MODULE_PERM_ID " +
	   "AND U.ID = :userid ";

   public static String getCreateWorkAreaUserAssocQuery1(Boolean isOracle) {
	   String pgSequence = "nextVal('SEQ_WORK_AREA_USER')";
	   String orcSequence="SEQ_WORK_AREA_USER.nextVal";
	   
	   StringBuffer sql = new StringBuffer();
	   sql.append("insert into isw_work_area_user (id, work_area_id, user_id, user_type) ")
	      .append("SELECT " + (isOracle ? orcSequence : pgSequence ) + ", w.ID, :userid , 'SYSTEM' ")
	      .append("FROM ISW_WORK_AREA w ")
	      .append("WHERE (w.STANDARD_ORGANIZATION_ID = :pdc ")
	      .append("AND(select count(work_area_id) ")
	      .append("from isw_work_area_user ")
	      .append("where work_area_id = w.id and user_id = :userid and user_type = 'SYSTEM') = 0 ")
	      .append(") ")
	      .append("or ")
	      .append("( ")
	      .append("w.id = 1 ")
	      .append("and ")
	      .append("(select count(work_area_id) ")
	      .append("from isw_work_area_user ")
	      .append("where work_area_id = 1 and user_id = :userid and user_type = 'SYSTEM') = 0 ")
	      .append(") ");
	   
	   return sql.toString();
   }
   
   public static String getCreateWorkAreaUserAssocQuery2(Boolean isOracle) {
	   String pgSequence = "nextVal('SEQ_WORK_AREA_USER')";
	   String orcSequence="SEQ_WORK_AREA_USER.nextVal";
	   
	   StringBuffer sql = new StringBuffer();
	   sql.append("insert into isw_work_area_user (id, work_area_id, user_id, user_type) ")
	      .append("SELECT " + (isOracle ? orcSequence : pgSequence ) + ", wau.work_area_ID, :userid , 'SYSTEM' ")
	      .append("FROM isw_work_area_user wau ")
	      .append("where wau.user_id = :userid ")
	      .append("and wau.work_area_id in ( ")
	      .append("select id from isw_work_area where standard_organization_id in ( ")
	      .append("select id from isw_organization where id in (select organization_id from isw_user_organization where user_id = :userid ) ")
	      .append("and is_dispatch_center = "+(isOracle ? 1 : true)+"")
	      .append(") ")
	      .append(") and ( ")
	      .append("select count(*) from isw_work_area_user ")
	      .append("where work_area_id = wau.work_area_id ")
	      .append("and user_id = wau.user_id ")
	      .append(")=0 ")
	      ;
   
	   return sql.toString();
   }   

   public static String getRemoveUserFromIncidentAccessList(Long userId) {
	   StringBuffer sql = new StringBuffer();

	   sql.append("DELETE FROM ISW_RESTRICTED_INCIDENT_USER ")
	   	  .append("WHERE USER_ID = " + userId + " ");
	   
       return sql.toString();
   }
   
   public static String getRemoveUserFromIncidentGroupAccessList(Long userId) {
	   StringBuffer sql = new StringBuffer();

	   sql.append("DELETE FROM ISW_INCIDENT_GROUP_USER ")
	   	  .append("WHERE USER_ID = " + userId + " ");
	   
       return sql.toString();
   }
   
}
