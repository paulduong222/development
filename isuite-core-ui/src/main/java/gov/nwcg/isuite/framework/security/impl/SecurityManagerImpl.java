 package gov.nwcg.isuite.framework.security.impl;

import gov.nwcg.isuite.core.service.IsuiteAuthorityService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.other.UserSession;
import gov.nwcg.isuite.framework.security.SecurityManager;
import gov.nwcg.isuite.framework.types.IsuiteAuthorityNameEnum;
import gov.nwcg.isuite.framework.types.SecurityGroupEnum;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

/**
 * This class will be used to control user access to individual groupings on web
 * pages. It will do this by storing the user authorities and authenticating
 * against them.
 * 
 * @author danderson, ncollette
 * 
 */
public abstract class SecurityManagerImpl implements SecurityManager {
   private static final EnumMap<SecurityGroupEnum, HashMap<String, Boolean>> viewable = new EnumMap<SecurityGroupEnum, HashMap<String, Boolean>>(
         SecurityGroupEnum.class);
   protected static final int MAX_LOGIN_ATTEMPTS = 5;
   protected IsuiteAuthorityService isuiteAuthorityService;
   private static final Logger LOG = Logger.getLogger(SecurityManagerImpl.class);
   private static final EnumMap<SecurityGroupEnum, HashMap<String, Boolean>> editable = new EnumMap<SecurityGroupEnum, HashMap<String, Boolean>>(
         SecurityGroupEnum.class);
   
   public SecurityManagerImpl() {
      populateViewableHashMap();
      populateEditableHashMap();
   }

   public final boolean isViewable(SecurityGroupEnum key) {
      return checkRole((HashMap<String, Boolean>) viewable.get(key));
   }

   public final boolean isEditable(SecurityGroupEnum key) {
      return checkRole((HashMap<String, Boolean>) editable.get(key));
   }
   
   private boolean checkRole(HashMap<String, Boolean> map) {
      UserSession details = getDetails();
      try {
         if (details != null) {
            for (String role : details.getPrimaryRoles()) {
               if (!role.equals("ACCEPTED_AUTHORITY")) {
                  if (map.get(role).booleanValue())
                  {
                     return true;
                  }
               }
            }
         }
      } catch (Exception e) {
         // Should only be the setup user.  Yes, it's not the best
         // practice to rely on exception handling to make the app work,
         // but efficiency is critical here, and the likely-hood of the
         // user being setup is so minimal, I thought adding a check to see
         // was a waste of time.  Also, I could go and manipulate the setup
         // user such that their roles are an empty array, but I couldn't be
         // bothered.  This is easier, and if the username is not setup, I'll
         // throw a new exception anyway.
         if (!details.getLoginName().equals("setup")) {
            LOG.error("It was assumed that this was not happening.", e);
         }
      }
      return false;
   }
   
   /*
    * Thread safe -- you'll note.
    */
   public UserSession getDetails() {
      UserSession result = null;
      Object obj = SecurityContextHolder.getContext()
         .getAuthentication().getDetails();
      if (obj != null && obj instanceof UserSession) {
         result = (UserSession) obj;
      }
      return result;
   }
   
   /**
    * Get the GrantedAuthorites out of the security context.
    * @return array of GrantedAuthorities, may be null
    */
   public GrantedAuthority[] getGrantedAuthorities() {
      try {
         return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
      } catch (NullPointerException npe) {
         //TODO: Nate or anyone.  I can consistently get this error to occur
         // if I login with a user, then logout by clicking the logout button.
         // Then try to log in with another valid username in the system, but enter
         // in the wrong password.
         return null;
      }
   }
   
   public Authentication getPrincipal() {
      return SecurityContextHolder.getContext().getAuthentication();
   }
//
   /**
    * This method populates the viewable map. It is VERY critical for
    * maintenance purposes that this stays in alphebetical order!
    */
   private final void populateViewableHashMap() {
      HashMap<String, Boolean> value = new HashMap<String, Boolean>();
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.ACRES_BURNED, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", true);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", true);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.ACTUAL_RELEASE_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", true);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", true);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.ADMINISTRATION, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.ADMINISTRATIVE_OFFICE_FOR_PAYMENT, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", true);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.AIR_TRAVEL_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", true);
      value.put("ROLE_CHECKIN", true);
      value.put("ROLE_COMMUNICATIONS", true);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", true);
      value.put("ROLE_DEMOB", true);
      value.put("ROLE_IAP", true);
      value.put("ROLE_INJURY_ILLNESS", true);
      value.put("ROLE_RESOURCES", true);
      value.put("ROLE_SUPPLY_CLERK", true);
      value.put("ROLE_SUPPLY_SUPERVISOR", true);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", true);
      viewable.put(SecurityGroupEnum.COMMON_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.CONTRACTOR_AGREEMENT, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.CONTRACTOR_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.CONTRACTOR_RATE, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.COST_ACCRUAL_EXTRACT, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.COST_ANALYSIS_BENCHMARK_REPORT_SETUP, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.COST_DIVISIONS, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.COST_PROJECTIONS, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.COST_RATES, value);
   
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.DAILY_COSTS, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", true);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.DEMOB_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.EMPLOYMENT_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.HIRED_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", true);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", true);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", true);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.IAP, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", true);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.INJURY_ILLNESS_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", true);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.PLANS_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);   
      viewable.put(SecurityGroupEnum.POST_TIME, value);
      
//    value = new HashMap<String, Boolean>();
//    value.put("ROLE_ANONYMOUS", false);
//    value.put("ROLE_UNKNOWN", false);
//    value.put("ROLE_NITCUNKNOWN", false);
//    value.put("ROLE_DISABLED", false);
//    value.put("ROLE_ADMINISTRATOR", false);
//    value.put("ROLE_CHECKIN", 2), true);
//    value.put("ROLE_COMMUNICATIONS", false);
//    value.put("ROLE_COST", false);
//    value.put("ROLE_DBADMIN", false);
//    value.put("ROLE_DEMOB", 6), true);
//    value.put("ROLE_IAP", false);
//    value.put("ROLE_INJURY_ILLNESS", false);
//    value.put("ROLE_RESOURCES", 9), true);
//    value.put("ROLE_SUPPLY_CLERK", false);
//    value.put("ROLE_SUPPLY_SUPERVISOR", false);
//    value.put("ROLE_TIME", false);
//    value.put("ROLE_WORK_SPACE", false);
//    viewable.put(SecurityGroupEnum.RESOURCE_ADDRESS, value);
//    
//    value = new HashMap<String, Boolean>();
//    value.put("ROLE_ANONYMOUS", false);
//    value.put("ROLE_UNKNOWN", false);
//    value.put("ROLE_NITCUNKNOWN", false);
//    value.put("ROLE_DISABLED", false);
//    value.put("ROLE_ADMINISTRATOR", 1), true);
//    value.put("ROLE_CHECKIN", 2), true);
//    value.put("ROLE_COMMUNICATIONS", false);
//    value.put("ROLE_COST", 4), true);
//    value.put("ROLE_DBADMIN", 5), true);
//    value.put("ROLE_DEMOB", 6), true);
//    value.put("ROLE_IAP", false);
//    value.put("ROLE_INJURY_ILLNESS", false);
//    value.put("ROLE_RESOURCES", 9), true);
//    value.put("ROLE_SUPPLY_CLERK", false);
//    value.put("ROLE_SUPPLY_SUPERVISOR", false);
//    value.put("ROLE_TIME", 12), true);
//    value.put("ROLE_WORK_SPACE", false);
//    viewable.put(SecurityGroupEnum.RESOURCES, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", true);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", true);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.ROSTER, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.SOCIAL_SECURITY_NUMBER, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", true);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.TENTATIVE_RELEASE_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      viewable.put(SecurityGroupEnum.TIME_ADJUSTMENTS, value);
   }

   /**
    * This method populates the editable map. It is VERY critical for
    * maintenance purposes that this stays in alphabetical order!
    */
   private final void populateEditableHashMap() {
      HashMap<String, Boolean> value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.ACRES_BURNED, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", true);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.ACTUAL_RELEASE_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", true);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", true);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.ADMINISTRATION, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.ADMINISTRATIVE_OFFICE_FOR_PAYMENT, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", true);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.AIR_TRAVEL_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", true);
      value.put("ROLE_CHECKIN", true);
      value.put("ROLE_COMMUNICATIONS", true);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", true);
      value.put("ROLE_DEMOB", true);
      value.put("ROLE_IAP", true);
      value.put("ROLE_INJURY_ILLNESS", true);
      value.put("ROLE_RESOURCES", true);
      value.put("ROLE_SUPPLY_CLERK", true);
      value.put("ROLE_SUPPLY_SUPERVISOR", true);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", true);
      editable.put(SecurityGroupEnum.COMMON_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.CONTRACTOR_AGREEMENT, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", true);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", true);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.CONTRACTOR_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.CONTRACTOR_RATE, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.COST_ACCRUAL_EXTRACT, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.COST_ANALYSIS_BENCHMARK_REPORT_SETUP, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.COST_DIVISIONS, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.COST_PROJECTIONS, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.COST_RATES, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", true);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.DAILY_COSTS, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", true);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.DEMOB_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.EMPLOYMENT_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.HIRED_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", true);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", true);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", true);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.IAP, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", true);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.INJURY_ILLNESS_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", true);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", true);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.PLANS_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.POST_TIME, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", true);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", true);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.ROSTER, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.SOCIAL_SECURITY_NUMBER, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", true);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", false);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.TENTATIVE_RELEASE_DATA, value);
      
      value = new HashMap<String, Boolean>();
      value.put("ROLE_ANONYMOUS", false);
      value.put("ROLE_UNKNOWN", false);
      value.put("ROLE_NITCUNKNOWN", false);
      value.put("ROLE_DISABLED", false);
      value.put("ROLE_ADMINISTRATOR", false);
      value.put("ROLE_CHECKIN", false);
      value.put("ROLE_COMMUNICATIONS", false);
      value.put("ROLE_COST", false);
      value.put("ROLE_DBADMIN", false);
      value.put("ROLE_DEMOB", false);
      value.put("ROLE_IAP", false);
      value.put("ROLE_INJURY_ILLNESS", false);
      value.put("ROLE_RESOURCES", false);
      value.put("ROLE_SUPPLY_CLERK", false);
      value.put("ROLE_SUPPLY_SUPERVISOR", false);
      value.put("ROLE_TIME", true);
      value.put("ROLE_WORK_SPACE", false);
      editable.put(SecurityGroupEnum.TIME_ADJUSTMENTS, value);
   }

   
   
   public IsuiteAuthorityService getIsuiteAuthorityService() {
      return isuiteAuthorityService;
   }

   /*
    * Thread safe -- you'll note.
    */
// private Principal getPrincipal() {
//    Principal result = null;
//    Object obj = SecurityContextHolder.getContext().getAuthentication()
//          .getPrincipal();
//    if (obj != null && obj instanceof Principal) {
//       result = (Principal) obj;
//    }
//    return result;
// }
   /*
    * Package scoped for testing purposes.
    */
    Map getViewable() {
      return (Map)viewable;
    }
    
   /*
    * Package scoped for testing purposes.
    */
    Map getEditable() {
      return (Map)editable;
    }

   /**
    * It's assumed that if they're going back to the acceptSecurityAgreement page, 
    * then they need to re-login, no attempt will be made to preserve past 
    * authorities, therefore it is possible for someone to login, then go to the 
    * acceptSecurityAgreement page again and lose the authorities they just logged in
    * with, causing them to have to re-login.  If they're doing that they deserve
    * the extra login.
    *  (non-Javadoc)
    * @see gov.nwcg.isuite.service.admin.SecurityManager#acceptSecurityAgreement(boolean)
    */
   public void acceptSecurityAgreement(boolean accept) throws ServiceException {
      LOG.debug("**Entering SecurityManagerImpl.acceptSecurityAgreement(accept)");
      
      SecurityContext securityContext = SecurityContextHolder.getContext();
      Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
      IsuiteAuthorityService authService = getIsuiteAuthorityService();
      if (authService == null) {
         throw new ServiceException(
             new IllegalStateException("IsuiteAuthorityService is null"));
      }
      authorities.add(authService.getBy(accept 
               ? IsuiteAuthorityNameEnum.ACCEPTED_AUTHORITY
               : IsuiteAuthorityNameEnum.DECLINED_AUTHORITY));
//      if (accept) { 
//         authorities.add(authService.getBy(IsuiteAuthorityNameEnum.ACCEPTED_AUTHORITY));
//      } else {
//         authorities.add(authService.getBy(IsuiteAuthorityNameEnum.DECLINED_AUTHORITY));
//      }
      LOG.debug("authorities: " + authorities.toString());
      GrantedAuthority[] theArray = new GrantedAuthority[authorities.size()];
      /*
      InitialAuthenticationToken principal = new InitialAuthenticationToken(
               "Unauthenticated", "User", authorities.toArray(theArray));
      LOG.debug("accepted/rejected authority principal into the session.");
      securityContext.setAuthentication(principal);
      */
   }
}