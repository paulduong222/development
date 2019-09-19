package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.UserGroupFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

import java.util.Date;

/**
 * Implementation of the UserGroupFilter.
 * 
 * @author bsteiner
 */
public class UserGroupFilterImpl extends FilterImpl implements UserGroupFilter {

   private static final long serialVersionUID = -1740374416257823349L;
   private String groupName = "";
   private Date createdDate;
   private Boolean deletable=false;
   private String deletableString;
   private String crypticDateFilterCode;
   
   /**
    * Default constructor
    */
   public UserGroupFilterImpl() {
      // details in here should match the reset() method.
      super();
      this.reset();
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupFilter#getGroupName()
    */
   public String getGroupName() {
      return this.groupName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupFilter#setGroupName(java.lang.String)
    */
   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupFilter#getCreatedDate()
    */
   public Date getCreatedDate() {
      return this.createdDate;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupFilter#setCreatedDate(java.util.Date)
    */
   public void setCreatedDate(Date date) {
      this.createdDate = date;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.impl.FilterImpl#reset()
    */
   public final void reset() {
      this.groupName = "";
      this.createdDate = null;
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
        
        retValue.append("<UserGroupFilterImpl>").append(nl)
    	    .append(tab).append(super.toString()).append(nl)
           .append(tab).append("<groupName>").append(this.groupName).append("</groupName>").append(nl)   
           .append(tab).append("<createdDate>").append(this.createdDate).append("</createdDate>").append(nl)   
           .append("</UserGroupFilterImpl>");
            
        return retValue.toString();
    }

    /**
     * Returns the deletable.
     *
     * @return 
     *		the deletable to return
     */
    public Boolean getDeletable() {
    	return deletable;
    }

    /**
     * Sets the deletable.
     *
     * @param deletable 
     *			the deletable to set
     */
    public void setDeletable(Boolean deletable) {
    	this.deletable = deletable;
    }

   /**
    * @return the deletableString
    */
   public String getDeletableString() {
      return deletableString;
   }

   /**
    * @param deletableString the deletableString to set
    */
   public void setDeletableString(String deletableString) {
      this.deletableString = deletableString;
      this.setDeletable(super.determineDeletableValue(this.deletableString));
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.UserGroupFilter#getCrypticDateFilterCode()
    */
   @Override
   public String getCrypticDateFilterCode() {
	   return this.crypticDateFilterCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.UserGroupFilter#setCrypticDateFilterCode(java.lang.String)
    */
   @Override
   public void setCrypticDateFilterCode(String crypticDateFilterCode) {
	   this.crypticDateFilterCode = crypticDateFilterCode;
   }

}