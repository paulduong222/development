package gov.nwcg.isuite.framework.other.impl;

import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.other.ValidityStrategy;

/**
 * This validity strategy was created purely so that I could create
 * essentially an empty user.  This "empty" user was needed for vo
 * to do transliteration.
 * 
 * @author ncollette
 *
 */
public class EmptyStringsValidtyStrategy implements ValidityStrategy {

   public void validate(String data) throws ValidationException {
      if (data != "") {
         throw new ValidationException("Only empty strings are allowed as input!!");
      }
   }
}
