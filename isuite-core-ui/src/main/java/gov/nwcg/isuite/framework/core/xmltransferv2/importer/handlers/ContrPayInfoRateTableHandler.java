package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class ContrPayInfoRateTableHandler extends BaseTableHandler implements TableHandler {
	
	
	public ContrPayInfoRateTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		XmlField xf1=xmlTable.getXmlFieldBySqlName("CONTRACTOR_PAY_INFO_ID");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("CONTRACTOR_RATE_ID");
		
		String cpiTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		String crTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);
		
		if(StringUtility.hasValue(cpiTi)&& StringUtility.hasValue(crTi)){
			String sqlCheck="select contractor_pay_info_id "+
						    "from isw_contr_payinfo_rate " +
						    "where contractor_pay_info_id = (select id from isw_contr_payment_info where transferable_identity='"+cpiTi+"') " +
						    "and contractor_rate_id = (select id from isw_contractor_rate where transferable_identity='"+crTi+"') "; 
			
			Object rslt=super.executeQuery(sqlCheck);
			if(null == rslt){
				// create the record
				String sqlInsert="insert into isw_contr_payinfo_rate (contractor_pay_info_id, contractor_rate_id) " +
								 "values (" +
								 "(select id from isw_contr_payment_info where transferable_identity='"+cpiTi+"')"+
								 ",(select id from isw_contractor_rate where transferable_identity='"+crTi+"')"+
								 ")";
				dao.executeUpdate(sqlInsert);
			}

		}
		return false;
	}

	public void doPostInsertProcesses() throws Exception {
		
	}
	
	public void doPostUpdateProcesses() throws Exception {
		
	}
}
