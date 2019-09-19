package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;



/**
 * Pick list vo for AccountCode
 * 
 * @author dbudge
 */

public class AccountCodePicklistVo extends AbstractVo {
   private String accountCd;
   private String agencyCd;

   /**
    * 
    */
   public AccountCodePicklistVo() {}

   /**
    * @return the accountCd
    */
   public String getAccountCd() {
      return accountCd;
   }

   /**
    * @param accountCd
    *           the accountCd to set
    */
   public void setAccountCd(String accountCd) {
      this.accountCd = accountCd;
   }

   /**
    * @return the agencyCd
    */
   public String getAgencyCd() {
      return agencyCd;
   }

   /**
    * @param agencyCd
    *           the agencyCd to set
    */
   public void setAgencyCd(String agencyCd) {
      this.agencyCd = agencyCd;
   }

   public String toString() {

      final String newLine = System.getProperty("line.separator");
      StringBuffer string = new StringBuffer();
      String tab = "\t";

      string.append("<IncidentAccountCodePicklistVo>").append(newLine).append(tab).append("<agencyCd>")
      		.append(agencyCd).append("</agencyCd>").append(newLine)
      		.append("<accountCd>").append(accountCd).append("</accountCd>").append(newLine)
      		.append(super.toString()).append("</IncidentAccountCodePicklistVo>");

      return string.toString();
   }
}
