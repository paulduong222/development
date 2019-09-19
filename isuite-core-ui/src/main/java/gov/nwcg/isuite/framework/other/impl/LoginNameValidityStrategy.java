/**
 * Implementation of the strategy to validate loginName contents.
 * 
 */
package gov.nwcg.isuite.framework.other.impl;

import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.other.ValidityStrategy;
import gov.nwcg.isuite.framework.types.ErrorEnum;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * Implementation of the strategy to validate loginName contents.
 * @author doug
 * 
 */
public class LoginNameValidityStrategy implements ValidityStrategy {

	/**
	 * Determine if the loginName data is valid.
	 * <p>
	 * This does <b>not</b> check for existence of other loginNames that match
	 * this login name, but rather if the content of the login name meets the
	 * standards.
	 * </p>
	 * <p>The criteria for validity are:
	 * <ul>
	 * <li>  </li>
	 * </ul>
	 * </p>
	 * 
	 * @param data login name to check
	 * @return true if the loginName meets the criteria
	 */
	
	private static final Logger log = Logger.getLogger(LoginNameValidityStrategy.class);

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.ValidityStrategy#validate(java.lang.String)
	 */
	public void validate(String loginName) throws ValidationException {
		log.debug("Entering LoginNameValidityStrategy : validate(loginName)");
		Collection<Enum<?>> errors = new ArrayList<Enum<?>>();
		if(loginName == null){
			errors.add(ErrorEnum.INVALID_LOGIN);
		}else{
			if(loginName.length()==0){
				errors.add(ErrorEnum.INVALID_LOGIN);
			}
		}
		if (errors.size()!=0){
			throw new ValidationException("loginName could not be validated", errors);
		}
		
	}
	
}
