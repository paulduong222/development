package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.Collection;

/**
 * Domain object representing failed import data.
 * 
 * @author bsteiner
 */
public interface UserImportFailure extends Persistable {

   /**
    * Name used to login to the system.
    * 
    * @return name used to login to the system, never null, nor empty
    * @see #setLoginName(String)
    * @see #setCountryName(String)
    */
   public String getLoginName();

   /**
    * Name used to login to the system.
    * 
    * @param name
    *            name used to login to the system, can not be null, nor empty
    * @see #getLoginName()
    * @see #getCountryName()
    */
   public void setLoginName(String name);

   /**
    * Password used to log into the system.
    * 
    * @return password used to log into the system, will not be null
    * @see #setPassword(String)
    */
   public String getPassword();
   
   /**
    * Password used to log into the system.
    * 
    * @param password
    *            password used to log into the system, can not be null
    *            NOTE: SHOULD ONLY BE USED FOR HIBERNATE PURPOSES.  ALL OTHER
    *                  CODE SHOULD BE CALLING SetEncryptedPassword!!!!
    *
    * @throws ValidationException 
    * @throws Exception 
    * @see #getPassword()
    */
   public void setPassword(String password) throws ValidationException, Exception;

   /**
    * Accessor for first name of the user.
    * 
    * @return first name of user, may be null
    * @see #setFirstName(String)
    */
   public String getFirstName();

   /**
    * Setter for first name of the user.
    * 
    * @param firstName
    *            first name of the user, may be null
    * @see #getFirstName()
    */
   public void setFirstName(String firstName);

   /**
    * Accessor for last name of the user.
    * 
    * @return last name of user, will NOT be null, nor empty
    * @see #setLastName(String)
    */
   public String getLastName();

   /**
    * Setter for last name of the user.
    * 
    * @param lastName
    *            last name of the user, will NOT be null, nor empty
    * @see #getLastName()
    */
   public void setLastName(String lastName);

   /**
    * Get the User's home or base unit string.
    * @return a populated unit string.
    */
   public String getHomeUnitCode();
   
   /**
    * Set the User's home or base unit string.
    * @param unit a populated unit string.
    */
   public void setHomeUnitCode(String unit);
   
   /**
    * 
    * @return The {@link User} object's PDC Unit Code.
    */
   public String getPdcUnitCode();
   
   /**
    * 
    * @param pdcUnitCode The PDC Unit Code to set for this {@link User} object.
    */
   public void setPdcUnitCode(String pdcUnitCode);
   
   /**
    * Get the reason for user import failure.
    * @return a string indicating failure reason.
    */
   public String getFailureReason();

   /**
    * Set the reason for user import failure.
    * @param failureReason a string indicating failure reason.
    */
   public void setFailureReason(String failureReason);
   
   /**
    * Set the roles associated to the import failure user.
    * 
    * @param roles Collection<SystemRole> objects.
    */
   public void setRoles(Collection<SystemRole> roles);
   
   /**
    * Retreive the roles associated to this import failure user.
    * 
    * @return Collection<SystemRole> objects.
    */
   public Collection<SystemRole> getRoles();

}
