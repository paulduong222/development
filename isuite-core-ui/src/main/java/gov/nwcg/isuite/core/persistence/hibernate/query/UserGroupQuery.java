package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.domain.impl.UserGroupUserImpl;
import gov.nwcg.isuite.xml.SystemRoleType;



public class UserGroupQuery {

	
	
	/*
	 *  Tests to see if an entered Group Name is Unique to the System
	 */
	public static final String IS_GROUP_NAME_UNIQUE_TO_SYSTEM="IS_GROUP_NAME_UNIQUE_TO_SYSTEM";
	public static final String IS_GROUP_NAME_UNIQUE_TO_SYSTEM_QUERY =
		"SELECT count(*) from UserGroupImpl ug " +
		"WHERE ug.groupName = :groupName"; 
	
	/*
	 *  Tests to see if an entered Group Name is Unique to the User Adding/Editing User Group
	 */
	public static final String IS_GROUP_NAME_UNIQUE_TO_USER="IS_GROUP_NAME_UNIQUE_TO_USER";
	public static final String IS_GROUP_NAME_UNIQUE_TO_USER_QUERY =
		"SELECT count(*) from UserGroupImpl ug " +
		"WHERE ug.groupName = :groupName " +
		"AND ug.groupOwnerId = :groupOwnerId";

	/*
	 *  Removes All UserGroupUserAuthority records associated with a specified UserGroupUserId
	 */
	public static final String DELETE_ALL_WITH_USER_GROUP_USER_ID="DELETE_ALL_WITH_USER_GROUP_USER_ID";
	public static final String DELETE_ALL_WITH_USER_GROUP_USER_ID_QUERY =
		"DELETE from UserGroupUserAuthorityImpl ugua " +
		"WHERE ugua.userGroupUserId = :userGroupUserId";

	/**
	 * Delete {@link UserGroupUserImpl} objects that contain the given ids.
	 */
	public static final String DELETE_USER_GROUP_USERS_BY_ID = "DELETE_USER_GROUP_USERS_BY_ID";
	public static final String DELETE_USER_GROUP_USERS_BY_ID_QUERY = 
	   "delete from UserGroupUserImpl " +
	   "where userId in (:ids)";
	
	//TODO:  Modify query to exclude privileged users. -dbudge
	public static final String GET_AVAILABLE_USERS_SQL_QUERY = 
		"SELECT U.ID AS id,  " +
        "U.LOGIN_NAME AS loginName,  " +
        "U.FIRST_NAME AS firstName,  " +
        "U.LAST_NAME AS lastName,  " +
        "HUORG.UNIT_CODE as homeUnitCode,  " +
        "DCORG.UNIT_CODE as pdcUnitCode  " +
        "FROM ISW_USER U  " +
        "	LEFT OUTER JOIN ISW_ORGANIZATION HUORG ON U.HOME_UNIT_ID = HUORG.ID " +
        "	LEFT OUTER JOIN ISW_ORGANIZATION DCORG ON U.PRIMARY_DISP_CTR_ID = DCORG.ID " +
        "WHERE U.ID NOT IN ( " +
        " 		SELECT UR.USER_ID FROM ISW_USER_ROLE UR WHERE ROLE_ID IN ( " +
        " 				SELECT ID FROM ISW_SYSTEM_ROLE WHERE ROLE_NAME IN ('" + SystemRoleType.ROLE_ACCOUNT_MANAGER.name() + "') " +
        "		) " +
        ") " +
		"AND U.DELETED_DATE IS NULL ";
		
	public static String getAvailableUsersSqlQueryString(Boolean isOracle){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT U.ID AS id,  ")
        .append("U.LOGIN_NAME AS loginName,  ")
        .append("U.FIRST_NAME AS firstName,  ")
        .append("U.LAST_NAME AS lastName,  ")
        .append("HUORG.UNIT_CODE as homeUnitCode,  ")
        .append("DCORG.UNIT_CODE as pdcUnitCode  ")
        .append("FROM ISW_USER U  ")
        .append("	LEFT OUTER JOIN ISW_ORGANIZATION HUORG ON U.HOME_UNIT_ID = HUORG.ID ")
        .append("	LEFT OUTER JOIN ISW_ORGANIZATION DCORG ON U.PRIMARY_DISP_CTR_ID = DCORG.ID ")
        .append("WHERE U.ID IN ( ")
        .append(" 		SELECT UR.USER_ID FROM ISW_USER_ROLE UR WHERE ROLE_ID NOT IN ( ") 
        .append(" 				SELECT ID FROM ISW_SYSTEM_ROLE WHERE IS_PRIVILEGED_ROLE = " + (isOracle==true ? 1 : true) + " ") 
        .append("		) ") 
        .append(") " )
		.append("AND U.DELETED_DATE IS NULL ");
		
		return sb.toString();
		
	}
}
