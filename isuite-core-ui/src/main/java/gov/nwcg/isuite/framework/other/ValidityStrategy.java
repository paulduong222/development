/**
 * Strategy to determine validity of entered data.
 * 
 */
package gov.nwcg.isuite.framework.other;

import gov.nwcg.isuite.framework.exceptions.ValidationException;

/**
 * Strategy to determine validity of entered data.
 * 
 * @author doug
 * 
 */
public interface ValidityStrategy {

	
	/**
	 * 
	 * Determine if the entered data is valid.
	 * <p>
	 * This does <b>not</b> compare against other possible instances of the data (i.e. uniqueness) but rather that the format of the data is correct (length, composition, etc.) 
	 * </p>
	 * @param data
	 * @throws ValidationException
	 */
	public void validate(String data)throws ValidationException;

}
