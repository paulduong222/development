package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class AssignmentTimePostInvoiceTableHandler extends BaseTableHandler implements TableHandler {
	
	
	public AssignmentTimePostInvoiceTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		XmlField xf1=xmlTable.getXmlFieldBySqlName("ASSIGN_TIME_POST_ID");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("TIME_INVOICE_ID");
		
		String atpTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		String invTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);
		
		if(StringUtility.hasValue(atpTi)&& StringUtility.hasValue(invTi)){
			String sqlCheck="select assign_time_post_id "+
						    "from isw_assign_time_post_invoice " +
						    "where assign_time_post_id = (select min(id) from isw_assign_time_post where transferable_identity='"+atpTi+"') " +
						    "and time_invoice_id = (select min(id) from isw_time_invoice where transferable_identity='"+invTi+"') "; 
			
			Object rslt=super.executeQuery(sqlCheck);
			if(null == rslt){
				// create the record
				String sqlInsert="insert into isw_assign_time_post_invoice (assign_time_post_id, time_invoice_id) " +
								 "values (" +
								 "(select min(id) from isw_assign_time_post where transferable_identity='"+atpTi+"')"+
								 ",(select min(id) from isw_time_invoice where transferable_identity='"+invTi+"')"+
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
