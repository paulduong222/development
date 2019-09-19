package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class ResourcePersonVo extends AbstractVo implements PersistableVo {

   private Long resourceId;
   private Long organizationId;
   private String firstName;
   private String lastName;
   private String unitCode;
   private String workPhone;
   private String cellPhone;
   private String email;
   
   public ResourcePersonVo() {
      
   }

   /**
    * @return the resourceId
    */
   public Long getResourceId() {
      return resourceId;
   }

   /**
    * @param resourceId the resourceId to set
    */
   public void setResourceId(Long resourceId) {
      this.resourceId = resourceId;
   }

   /**
    * @return the organizationId
    */
   public Long getOrganizationId() {
      return organizationId;
   }

   /**
    * @param organizationId the organizationId to set
    */
   public void setOrganizationId(Long organizationId) {
      this.organizationId = organizationId;
   }

   /**
    * @return the firstName
    */
   public String getFirstName() {
      return firstName;
   }

   /**
    * @param firstName the firstName to set
    */
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /**
    * @return the lastName
    */
   public String getLastName() {
      return lastName;
   }

   /**
    * @param lastName the lastName to set
    */
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   /**
    * @return the unitCode
    */
   public String getUnitCode() {
      return unitCode;
   }

   /**
    * @param unitCode the unitCode to set
    */
   public void setUnitCode(String unitCode) {
      this.unitCode = unitCode;
   }

   /**
    * @return the workPhone
    */
   public String getWorkPhone() {
      return workPhone;
   }

   /**
    * @param workPhone the workPhone to set
    */
   public void setWorkPhone(String workPhone) {
      this.workPhone = workPhone;
   }

   /**
    * @return the cellPhone
    */
   public String getCellPhone() {
      return cellPhone;
   }

   /**
    * @param cellPhone the cellPhone to set
    */
   public void setCellPhone(String cellPhone) {
      this.cellPhone = cellPhone;
   }

   /**
    * @return the email
    */
   public String getEmail() {
      return email;
   }

   /**
    * @param email the email to set
    */
   public void setEmail(String email) {
      this.email = email;
   }

}
