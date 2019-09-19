package gov.nwcg.isuite.framework.core.xmltransfer.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;


public class IncidentCostRateTableHandler extends BaseTableHandler implements TableHandler {
	
	public IncidentCostRateTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		this.doSitePreProcess();
		return true;
	}

	private Object doSitePreProcess() throws Exception {
		
		// get xml values need to match with existing site group questions
		Object xmlCostRateCategoryValue = XmlTransferUtil.invokeGetMethod(xmlObject, "CostRateCategory");
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "TransferableIdentity");
		Object xmlIncTiValue = XmlTransferUtil.invokeGetMethod(xmlObject, "IncidentTransferableIdentity");
		
		// check if there is matching incident record
		String sql = "SELECT id " +
					 "FROM isw_inccost_rate " + 
					 "WHERE cost_rate_category = '" + (String)xmlCostRateCategoryValue + "' " +
					 "AND incident_id = (select id from isw_incident where transferable_identity = '" + (String)xmlIncTiValue + "' ) " ;

		Object rslt=super.executeQuery(sql);
		if(null != rslt) {
			Long id = TypeConverter.convertToLong(rslt);
			
			sql="UPDATE isw_inccost_rate " + 
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
