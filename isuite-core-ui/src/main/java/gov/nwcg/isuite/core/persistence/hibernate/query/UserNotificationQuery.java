package gov.nwcg.isuite.core.persistence.hibernate.query;


public class UserNotificationQuery {

	/*
	 *  Update the user's messages readflag to true.
	 */
	public static final String UPDATE_READFLAG="UPDATE_READFLAG";
	public static final String UPDATE_READFLAG_QUERY =
		"UPDATE UserNotificationImpl model " +
		"SET model.readFlag = :flag, " +
		"model.readDate = :readdate " +
		"WHERE model.userId = :userid ";
}
