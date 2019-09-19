package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;


/**
 * This is a vo explicitly created for the selection of organizations from a picklist.
 * 
 * It should have very minimal data and is read-only.
 * 
 * @author doug
 * 
 */
public class OrganizationPicklistVo extends AbstractVo {
   private String orgName;
   private String unitCd;

   //default constructor
   public OrganizationPicklistVo(){}
   
   public OrganizationPicklistVo(Long id, String name, String unitCode) {
      setId(id);
      setOrgName(name);
      setUnitCd(unitCode);
   }
   
   public OrganizationPicklistVo(Organization org) {
      if (org != null) {
         setId(org.getId());
         setOrgName(org.getName());
         setUnitCd(org.getUnitCode());
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Organization#getName()
    */
   public String getOrgName() {
      return orgName;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#setName(java.lang.String)
    */
   public void setOrgName(String name) {
      if (name == null || name.length() == 0) {
         throw new IllegalArgumentException("name can not be null or empty");
      }
      this.orgName = name;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#getUnitCode()
    */
   public String getUnitCd() {
      return this.unitCd;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#setUnitCode(java.lang.String)
    */
   public void setUnitCd(String unitCode) {
      this.unitCd = unitCode;
   }
   
   /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    @Override
    public String toString()
    {
        final String nl = System.getProperty("line.separator");
        
        StringBuffer retValue = new StringBuffer();
        String tab = "\t";
        
        retValue.append("<OrganizationPicklistVo>").append(nl)
            .append(tab).append("<orgName>").append(this.orgName).append("</orgName>").append(nl)
            .append(tab).append("<unitCd>").append(this.unitCd).append("</unitCd>").append(nl)
            .append(super.toString())
            .append("</OrganizationPicklistVo>").append(nl);
            
        return retValue.toString();
    }
}
