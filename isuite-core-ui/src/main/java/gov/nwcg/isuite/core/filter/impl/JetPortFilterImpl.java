package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.JetPortFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;

import java.util.ArrayList;
import java.util.Collection;
/**
 * @author mgreen
 */

public class JetPortFilterImpl extends FilterImpl implements JetPortFilter {
   
   private static final long serialVersionUID = 8186799437774167785L;
   private String code;
   private String description;
   private Boolean standard = null;
   private String countrySubdivision;

   /**
    * Default constructor
    */
   public JetPortFilterImpl() {
      super();
      this.reset();
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.JetPortFilter#setCode(java.lang.String)
    */
   public void setCode(String code) {
      this.code = code;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.JetPortFilter#getCode()
    */
   public String getCode() {
      return this.code;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.JetPortFilter#setDescription(java.lang.String)
    */
   public void setDescription(String description) {
      this.description = description;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.JetPortFilter#getDescription()
    */
   public String getDescription() {
      return this.description;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.JetPortFilter#isStandard()
    */
   public Boolean isStandard() {
      return this.standard;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.JetPortFilter#getStandard()
    */
   public int getStandard() {
      if (standard) {
         return 1;
      } else if (!standard){
         return 0;
      } else {
         return -1;
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.JetPortFilter#setStandard(int)
    */
   public void setStandard(int standard) {
      switch ( standard ) {
         case 0:  this.standard = false;
                  break;
         case 1:  this.standard = true;
                  break;
         default: this.standard = null;
      }
   }       
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.JetPortFilter#getCountrySubdivision()
    */
   public String getCountrySubdivision() {
      return countrySubdivision;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.JetPortFilter#setCountrySubdivision(java.lang.String)
    */
   public void setCountrySubdivision(String countrySubdivision) {
      this.countrySubdivision = countrySubdivision;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Filter#reset()
    */
   public void reset() {
      this.code = "";
      this.description = "";
      this.standard = null;
      this.countrySubdivision = null;
   }
   
   /**
    * Constructs a <code>String</code> with all attributes in xml format.
    *
    * @return a <code>String</code> representation of this object.
    */
   @Override
   public String toString() {
       final String nl = System.getProperty("line.separator");
       
       StringBuffer retValue = new StringBuffer();
       String tab = "\t";
       
       retValue.append("<JetPortFilterImpl>").append(nl)
         .append(tab).append(super.toString()).append(nl)
          .append(tab).append("<code>").append(this.code).append("</code>").append(nl)   
          .append(tab).append("<description>").append(this.description).append("</description>").append(nl)   
          .append(tab).append("<standard>").append(this.standard.toString()).append("</standard>").append(nl)   
          .append(tab).append("<countrySubdivision>").append(this.countrySubdivision).append("</countrySubdivision>").append(nl)   
          .append("</JetPortFilterImpl>");
           
       return retValue.toString();
   }

   /**
    * Adds Criteria information to queries based upon incoming filter
    * @param filter the filter information to add to FilterCriteria object
    * @return criteria Collection of FilterCriteria objects
    * @throws Exception
    */
   public static Collection<FilterCriteria> getFilterCriteria(JetPortFilter filter) throws Exception {
      Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
      
      // TYPE_EQUALS 
      if (null != filter.isStandard()) {
         criteria.add( filter.isStandard() ? new FilterCriteria("this.standard",Boolean.TRUE,FilterCriteria.TYPE_EQUAL) : new FilterCriteria("this.standard",Boolean.FALSE,FilterCriteria.TYPE_EQUAL) );
      }
      
      // TYPE_ILIKE
      criteria.add( null != filter.getCode() && !filter.getCode().isEmpty() ? new FilterCriteria("this.code",filter.getCode(),FilterCriteria.TYPE_ILIKE) : null);
      criteria.add( null != filter.getDescription() && !filter.getDescription().isEmpty() ? new FilterCriteria("this.description",filter.getDescription(),FilterCriteria.TYPE_ILIKE) : null);      
      criteria.add( null != filter.getCountrySubdivision() && !filter.getCountrySubdivision().isEmpty() ? new FilterCriteria("this.countrySubdivision.abbreviation",filter.getCountrySubdivision(),FilterCriteria.TYPE_ILIKE) : null);      
      
      return criteria;
   }
}
