/**
 * Implementation of PasswordValidity on the site.
 */
package gov.nwcg.isuite.framework.other.impl;

import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.other.ValidityStrategy;
import gov.nwcg.isuite.framework.types.ErrorEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Implementation of PasswordValidity on the site.
 * 
 * @author doug
 * 
 */
public class SitePasswordValidityStrategy implements ValidityStrategy {

   /**
	 * Determine if a password is valid. Throws ValidationException if password
	 * can not be validated.
	 * <p>
	 * The criteria for a valid password are:
	 * <ul>
	 * <li>can not be null</li>
	 * <li>Passwords must be at least 8 characters long. </li>
	 * <li>Passwords must contain at least one lowercase letter. </li>
	 * <li>Passwords must contain at least one uppercase letter. </li>
	 * <li>Passwords must contain at least one number. </li>
	 * <li>Passwords must contain at least one special character. </li>
	 * <li>Allowed special characters: !#%&*^_ </li>
	 * </ul>
	 * </p>
	 * 
	 * @param data
	 *            password
	 * 
	 */

	private static final int MIN_LENGTH = 8;
	
	private static final Logger log = Logger.getLogger(SitePasswordValidityStrategy.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.ValidityStrategy#validate(java.lang.String)
	 */
	public void validate(String password) throws ValidationException {
		log.debug("Entering SitePasswordValidityStrategy : validate(password)");
		Collection<Enum<?>> errors = new ArrayList<Enum<?>>();

		if (password == null) {
		   throw new IllegalArgumentException("password is a required field.");
		}
		else {
		   if (password.length() < MIN_LENGTH) {
            log.debug("Password not the required minimum length.");
		      errors.add(ErrorEnum._0024_1_MISSING_LENGTH);
		   }
   		if (!Pattern.compile("[0-9]").matcher(password).find()) {
            log.debug("Password missing required number.");
   			errors.add(ErrorEnum._0024_4_MISSING_NUMERIC);
   		}
   
   		if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            log.debug("Password missing required capital letter.");
   			errors.add(ErrorEnum._0024_2_MISSING_UPPERCASE);
   		}
   
   		if (!Pattern.compile("[a-z]").matcher(password).find()) {
            log.debug("Password missing required lower-case letter.");
   			errors.add(ErrorEnum._0024_3_MISSING_LOWERCASE);
   		}
   
   		if (!Pattern.compile("[!, #, %, &, *, ^, _]").matcher(password).find()) {
            log.debug("Password missing required special character.");
   			errors.add(ErrorEnum._0024_5_MISSING_SPECIAL);
   		}
   
   		if (errors.size() > 0) {
   			throw new ValidationException("password could not be validated",
   					errors);
   		}
		}
	}
}
