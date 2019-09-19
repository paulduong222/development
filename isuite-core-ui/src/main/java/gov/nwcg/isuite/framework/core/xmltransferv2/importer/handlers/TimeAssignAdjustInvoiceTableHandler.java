package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class TimeAssignAdjustInvoiceTableHandler extends BaseTableHandler implements TableHandler {
	
	
	public TimeAssignAdjustInvoiceTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		XmlField xf1=xmlTable.getXmlFieldBySqlName("TIME_POST_ADJUST_ID");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("TIME_INVOICE_ID");
		
		String adjTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		String invTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);
		
		if(StringUtility.hasValue(adjTi)&& StringUtility.hasValue(invTi)){
			String sqlCheck="select time_post_adjust_id "+
						    "from isw_time_assign_adj_invoice " +
						    "where time_post_adjust_id = (select min(id) from isw_assign_time_post where transferable_identity='"+adjTi+"') " +
						    "and time_invoice_id = (select min(id) from isw_time_invoice where transferable_identity='"+invTi+"') "; 
			
			Object rslt=super.executeQuery(sqlCheck);
			if(null == rslt){
				// create the record
				String sqlInsert="insert into isw_time_assign_adj_invoice (time_post_adjust_id, time_invoice_id) " +
								 "values (" +
								 "(select min(id) from isw_time_assign_adjust where transferable_identity='"+adjTi+"')"+
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
