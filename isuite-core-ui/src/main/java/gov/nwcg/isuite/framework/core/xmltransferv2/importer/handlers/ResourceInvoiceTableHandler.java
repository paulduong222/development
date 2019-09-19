package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class ResourceInvoiceTableHandler extends BaseTableHandler implements TableHandler {
	
	
	public ResourceInvoiceTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		XmlField xf1=xmlTable.getXmlFieldBySqlName("RESOURCE_ID");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("TIME_INVOICE_ID");
		
		String rTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		String iTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);
		
		if(StringUtility.hasValue(rTi)&& StringUtility.hasValue(iTi)){
			boolean bproceed=false;
			String resIdSql="select id from isw_resource where transferable_identity='"+rTi+"'";
			Object rslt1=super.executeQuery(resIdSql);
			if(null != rslt1){
				String tiIdSql="select id from isw_time_invoice where transferable_identity='"+iTi+"'";
				Object rslt2=super.executeQuery(tiIdSql);
				if(null != rslt2){
					bproceed=true;
				}
			}
			

			if(bproceed==true){
				String sqlCheck="select resource_id "+
							    "from isw_resource_invoice " +
							    "where resource_id = (select min(id) from isw_resource where transferable_identity='"+rTi+"') " +
							    "and time_invoice_id = (select min(id) from isw_time_invoice where transferable_identity='"+iTi+"') "; 
				
				Object rslt=super.executeQuery(sqlCheck);
				if(null == rslt){
					// create the record
					String sqlInsert="insert into isw_resource_invoice (resource_id, time_invoice_id) " +
									 "values (" +
									 "(select min(id) from isw_resource where transferable_identity='"+rTi+"')"+
									 ",(select min(id) from isw_time_invoice where transferable_identity='"+iTi+"')"+
									 ")";
					dao.executeUpdate(sqlInsert);
				}
			}

		}
		return false;
	}

	public void doPostInsertProcesses() throws Exception {
		
	}
	
	public void doPostUpdateProcesses() throws Exception {
		
	}
}
