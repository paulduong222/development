package gov.nwcg.isuite.framework.core.xmltransfer.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;


public class IncidentCostRateKindTableHandler extends BaseTableHandler implements TableHandler {
	
	public IncidentCostRateKindTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		this.doSitePreProcess();
		return true;
	}

	private Object doSitePreProcess() throws Exception {
		
		// get xml values need to match with existing site group questions
		Object xmlKindTiValue = XmlTransferUtil.invokeGetMethod(xmlObject, "KindTransferableIdentity");
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "TransferableIdentity");
		Object xmlRateTypeValue=XmlTransferUtil.invokeGetMethod(xmlObject, "RateType");
		Object xmlIncCostRateTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "IncidentCostRateTransferableIdentity");
		
		// check if there is matching record
		String sql = "SELECT id " +
					 "FROM isw_inccost_rate_kind " + 
					 "WHERE kind_id = (" + "select id from iswl_kind where transferable_identity = '" + (String)xmlKindTiValue + "' ) " +
					 "AND rate_type = '" + (String)xmlRateTypeValue + "' " +
					 "AND inccost_rate_id = (select id from isw_inccost_rate where transferable_identity = '" + (String)xmlIncCostRateTiValue + "' ) ";

		Object rslt=super.executeQuery(sql);
		if(null != rslt) {
			Long id = TypeConverter.convertToLong(rslt);
			
			sql="UPDATE isw_inccost_rate_kind " + 
				"SET transferable_identity = '" + (String)xmlTiValue + "' " +
				"WHERE id = " + id + " ";
			
			dao.executeUpdate(sql);
		}
		
		return null;
	}

	public void doPostInsertProcesses() throws Exception {
	}

	public void doPostUpdateProcesses() throws Exception {

	}

}
