/**
 * Used to generate site users.
 */
package gov.nwcg.isuite.framework.other;

import gov.nwcg.isuite.core.domain.User;

import javax.xml.bind.ValidationException;

/**
 * Used to generate site users.
 * <p>This factory checks the validity of passwords before creating SiteUsers </p>
 * @author doug
 *
 */
public interface SiteUserFactory {

	/**
	 * Create a siteUser based on valid input parameters.
	 * @param loginName name used to login to the system
	 * @param password password used to login to the system
	 * @param firstName first name of the user
	 * @param lastName last name of the user
	 * @return SiteUser if the input data is valid, IllegalArgumentException otherwise
	 * @throws Exception 
	 * @throws ValidationException 
	 */
	User createUser(String loginName, String password, String firstName, String lastName) throws ValidationException, Exception;
}
