/**
 * 
 */
package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.Sit209Filter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation of the Sit209Filter
 */
public class Sit209FilterImpl extends FilterImpl implements Sit209Filter {
   
   private static final long serialVersionUID = 2058332018432052207L;
   private String code;
   private String description;
   private Boolean standard = null;
   private Boolean deletable=false;
   private String incident;
   
   /**
    * Default constructor
    */
   public Sit209FilterImpl() {
      super();
      this.reset();
   }
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.Sit209Filter#getCode()
    */
   public String getCode() {
      return code;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.Sit209Filter#setCode(java.lang.String)
    */
   public void setCode(String code) {
      this.code = code;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.Sit209Filter#getDescription()
    */
   public String getDescription() {
      return description;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.Sit209Filter#setDescription(java.lang.String)
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
   
   /**
	 * @param incident the incident to set
	 */
	public void setIncident(String incident) {
		this.incident = incident;
	}
	
	/**
	 * @return the incident
	 */
	public String getIncident() {
		return incident;
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
       
       retValue.append("<Sit209FilterImpl>").append(nl)
         .append(tab).append(super.toString()).append(nl)
          .append(tab).append("<code>").append(this.code).append("</code>").append(nl)   
          .append(tab).append("<description>").append(this.description).append("</description>").append(nl)   
          .append(tab).append("<standard>").append(this.standard.toString()).append("</standard>").append(nl)   
          .append("</sit209FilterImpl>");
           
       return retValue.toString();
   }

   /**
    * Adds Criteria information to queries based upon incoming filter
    * @param filter the filter information to add to FilterCriteria object
    * @return criteria Collection of FilterCriteria objects
    * @throws Exception
    */
   public static Collection<FilterCriteria> getFilterCriteria(Sit209Filter filter) throws Exception {
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
