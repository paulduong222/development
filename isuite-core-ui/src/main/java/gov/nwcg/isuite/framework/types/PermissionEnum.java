/**
 * Represents permissions.
 */
package gov.nwcg.isuite.framework.types;

/**
 * Represents permissions.
 * @author doug
 *
 */
public enum PermissionEnum {
	VIEW, // view only
	EDIT, // edit (of course this includes viewing
	NAVIGATE, // used for navigation to a url
	EXECUTE;  // used for execution of a web service or service layer method

}
