/**
 * Strategy to determine validity of names.
 */
package gov.nwcg.isuite.framework.other.impl;

import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.other.ValidityStrategy;
import gov.nwcg.isuite.framework.types.ErrorEnum;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * Strategy to determine validity of names.
 * 
 * @author doug
 * 
 */
public class NameValidityStrategy implements ValidityStrategy {

	private static final Logger log = Logger.getLogger(NameValidityStrategy.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.ValidityStrategy#validate(java.lang.String)
	 */
	public void validate(String name) throws ValidationException {
		log.debug("Entering NameValidityStrategy : validate(name)");
		Collection<Enum<?>> errors = new ArrayList<Enum<?>>();
		if (name == null) {
			errors.add(ErrorEnum.INVALID_NAME);
		} else {
			if (name.length() == 0) {
				errors.add(ErrorEnum.INVALID_NAME);
			}
		}
		if (errors.size() != 0) {
			throw new ValidationException("name could not be validated", errors);
		}

	}

}
