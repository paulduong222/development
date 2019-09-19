package gov.nwcg.isuite.framework.security;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * This class provides functionality needed to check 
 * whether or not the user is within the 3 day window 
 * when their password may expire.
 * @author ncollette
 *
 */
public class SecurityUtil {
   
   private static final Logger LOG = Logger.getLogger(SecurityUtil.class);
   
   /**
    * As I understand things, they have 3 days of password expiration warning.
    * 
    * @param calendar
    * @return true or false based on whether or not their password change date is
    * within the next 3 days of todays date.
    */
   public static boolean isPasswordAboutToExpire(Calendar theUsersPasswordExpirationDate) {
      //TODO: theUsersPasswordExpirationDate comes in null.  Why? DBudge
      if(theUsersPasswordExpirationDate == null) {
         theUsersPasswordExpirationDate = new GregorianCalendar();
         theUsersPasswordExpirationDate.add(Calendar.DAY_OF_YEAR, 100);
      }
      Calendar threeDaysFromNow = new GregorianCalendar();
      threeDaysFromNow.add(Calendar.DAY_OF_YEAR, 3);
      if (LOG.isDebugEnabled()) {
         LOG.debug("expiration date: " + theUsersPasswordExpirationDate.getTime());
         LOG.debug("3 days from now: " + threeDaysFromNow.getTime());
      }
      if (theUsersPasswordExpirationDate.compareTo(threeDaysFromNow) < 0) {
         LOG.debug("User password will expire on " + theUsersPasswordExpirationDate.getTime().toString());
         return true;
      }
      return false;
   }
}
