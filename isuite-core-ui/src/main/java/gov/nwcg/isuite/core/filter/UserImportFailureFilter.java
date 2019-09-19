package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

/**
 * @author bsteiner
 *
 */
public interface UserImportFailureFilter extends Filter {
   public void setFirstName(String firstName);
   public void setLastName(String lastName);
   public void setLoginName(String loginName);
   public void setUnitCode(String homeUnit);
   public void setFailureReason(String failureReason);
   public String getFirstName();
   public String getLastName();
   public String getLoginName();
   public String getUnitCode();
   public String getFailureReason();
}
