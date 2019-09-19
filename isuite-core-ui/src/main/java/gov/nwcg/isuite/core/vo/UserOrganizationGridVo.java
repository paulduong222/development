package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

/**
 * 
 * @author dbudge
 */
public class UserOrganizationGridVo extends AbstractVo {

//   private Long dispatchCenterId;
   private String dispatchCenterCode;
   private String dispatchCenterName;
   private Boolean dispatchCenter;
   
   public UserOrganizationGridVo() {}

   /**
    * @return the dispatchCenterCode
    */
   public String getDispatchCenterCode() {
      return dispatchCenterCode;
   }

   /**
    * @param dispatchCenterCode the dispatchCenterCode to set
    */
   public void setDispatchCenterCode(String dispatchCenterCode) {
      this.dispatchCenterCode = dispatchCenterCode;
   }

   /**
    * @return the dispatchCenterName
    */
   public String getDispatchCenterName() {
      return dispatchCenterName;
   }

   /**
    * @param dispatchCenterName the dispatchCenterName to set
    */
   public void setDispatchCenterName(String dispatchCenterName) {
      this.dispatchCenterName = dispatchCenterName;
   }

//   /**
//    * @return the dispatchCenterId
//    */
//   public Long getDispatchCenterId() {
//      return dispatchCenterId;
//   }
//
//   /**
//    * @param dispatchCenterId the dispatchCenterId to set
//    */
//   public void setDispatchCenterId(Long dispatchCenterId) {
//      this.dispatchCenterId = dispatchCenterId;
//   }

   /**
    * @return the dispatchCenter
    */
   public Boolean getDispatchCenter() {
      return dispatchCenter;
   }

   /**
    * @param dispatchCenter the dispatchCenter to set
    */
   public void setDispatchCenter(Boolean dispatchCenter) {
      this.dispatchCenter = dispatchCenter;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.AbstractVo#toString()
    */
   @Override
   public String toString() {
      final String nl = System.getProperty("line.separator");

      StringBuffer retValue = new StringBuffer();
      String tab = "\t";

      retValue.append("<UserOrganizationsGridVo>").append(nl)
      .append(tab).append(super.toString()).append(nl)
      .append(tab).append("<dispatchCenterCode>").append(this.dispatchCenterCode).append("</dispatchCenterCode>").append(nl)
      .append(tab).append("dispatchCenterName").append(this.dispatchCenterName).append("</dispatchCenterName>").append(nl)
      .append("</UserOrganizationsGridVo>");

      return retValue.toString();
   }
}
