package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.ResourcePersonFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class ResourcePersonFilterImpl extends FilterImpl implements ResourcePersonFilter {

   private static final long serialVersionUID = -3427631656190450190L;
  
   private String firstName;
   private String lastName;
   private String unitCode;
   
   public ResourcePersonFilterImpl() {
      
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.ResourcePersonFilter#getFirstName()
    */
   public String getFirstName() {
      return firstName;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.ResourcePersonFilter#setFirstName(java.lang.String)
    */
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.ResourcePersonFilter#getLastName()
    */
   public String getLastName() {
      return lastName;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.ResourcePersonFilter#setLastName(java.lang.String)
    */
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.ResourcePersonFilter#getUnitCode()
    */
   public String getUnitCode() {
      return unitCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.ResourcePersonFilter#setUnitCode(java.lang.String)
    */
   public void setUnitCode(String unitCode) {
      this.unitCode = unitCode;
   }
}
