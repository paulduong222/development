/**
 * 
 */
package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.KindGroupFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation of the KindGroupFilter
 * 
 * @author gdyer
 *
 */
public class KindGroupFilterImpl extends FilterImpl implements KindGroupFilter {
   
   private static final long serialVersionUID = 2473239164777963444L;
   private String code;
   private String description;
   private Boolean standard = null;
   private Boolean deletable=false;
   
   /**
    * Default constructor
    */
   public KindGroupFilterImpl() {
      super();
      this.reset();
   }
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.KindGroupFilter#getCode()
    */
   public String getCode() {
      return code;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.KindGroupFilter#setCode(java.lang.String)
    */
   public void setCode(String code) {
      this.code = code;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.KindGroupFilter#getDescription()
    */
   public String getDescription() {
      return description;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.KindGroupFilter#setDescription(java.lang.String)
    */
   public void setDescription(String description) {
      this.description = description;
   }

   public void setStandard(Boolean val){
	   this.standard=val;
   }
   
   public Boolean getStandard(){
	   return standard;
   }
   
   public void setDeletable(Boolean val){
	   this.deletable=val;
   }
   
   public Boolean getDeletable(){
	   return deletable;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.filter.impl.FilterImpl#reset()
    */
   public final void reset() {
      this.code = "";
      this.description = "";
      this.standard = null;

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
       
       retValue.append("<KindGroupFilterImpl>").append(nl)
         .append(tab).append(super.toString()).append(nl)
          .append(tab).append("<code>").append(this.code).append("</code>").append(nl)   
          .append(tab).append("<description>").append(this.description).append("</description>").append(nl)   
          .append(tab).append("<standard>").append(this.standard.toString()).append("</standard>").append(nl)   
          .append("</kindGroupFilterImpl>");
           
       return retValue.toString();
   }

   /**
    * Adds Criteria information to queries based upon incoming filter
    * @param filter the filter information to add to FilterCriteria object
    * @return criteria Collection of FilterCriteria objects
    * @throws Exception
    */
   public static Collection<FilterCriteria> getFilterCriteria(KindGroupFilter filter) throws Exception {
      Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
      
      // TYPE_EQUALS 
      if (null != filter.getDeletable() && (filter.getDeletable()) ) {
         criteria.add( new FilterCriteria("this.standard",Boolean.FALSE,FilterCriteria.TYPE_EQUAL));
      }
      
      // TYPE_ILIKE
      criteria.add( null != filter.getCode() && !filter.getCode().isEmpty() ? new FilterCriteria("this.code",filter.getCode(),FilterCriteria.TYPE_ILIKE) : null);
      criteria.add( null != filter.getDescription() && !filter.getDescription().isEmpty() ? new FilterCriteria("this.description",filter.getDescription(),FilterCriteria.TYPE_ILIKE) : null);      
      
      return criteria;
   }

}
