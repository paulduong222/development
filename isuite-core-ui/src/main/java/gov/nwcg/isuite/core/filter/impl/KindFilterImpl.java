package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.KindFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author mgreen
 */

@SuppressWarnings("serial")
public class KindFilterImpl extends FilterImpl implements KindFilter {
   
   private String code;
   private String description;
   private Boolean standard;
   private String rcCode;

   public KindFilterImpl() {
      super();
   }

   /**
    * @param code the kind code
    */
   public void setCode(String code) {
      this.code = code;
   }
   
   /**
    * @return the kind code
    */
   public String getCode() {
      return this.code;
   }

   /**
    * @param description the kind code description
    */
   public void setDescription(String description) {
      this.description = description;
   }
   
   /**
    * @return the kind code description
    */
   public String getDescription() {
      return this.description;
   }
   
   /**
    * @return the standard
    */
   public Boolean isStandard() {
      return this.standard;
   }

   /**
    * @param isStandard the isStandard to set
    */
   public void setStandard(Boolean isStandard) {
      this.standard = isStandard;
   }       
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Filter#reset()
    */
   public void reset() {
      this.code = null;
      this.description = null;
      this.standard = null;
      this.rcCode = null;
   } 
   
   /**
    * @param filter
    * @return
    * @throws Exception
    */
   public static Collection<FilterCriteria> getFilterCriteria(KindFilter filter) throws Exception {
      Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

      // TYPE_EQUALS
      criteria.add( null != filter.isStandard() && filter.isStandard() ? new FilterCriteria("this.standard",Boolean.TRUE,FilterCriteria.TYPE_EQUAL) : null);
      
      // TYPE_ILIKE
      criteria.add( null != filter.getCode() && !filter.getCode().isEmpty() ? new FilterCriteria("this.code",filter.getCode(),FilterCriteria.TYPE_ILIKE) : null);
      criteria.add( null != filter.getDescription() && !filter.getDescription().isEmpty() ? new FilterCriteria("this.description",filter.getDescription(),FilterCriteria.TYPE_ILIKE) : null);
      
      return criteria;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.KindFilter#getRcCode()
    */
   public String getRcCode() {
      return rcCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.KindFilter#setRcCode(java.lang.String)
    */
   public void setRcCode(String rcCode) {
      this.rcCode = rcCode;
   }
}
