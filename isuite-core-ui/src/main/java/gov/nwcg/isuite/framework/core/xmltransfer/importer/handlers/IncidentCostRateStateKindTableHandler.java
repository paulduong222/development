package gov.nwcg.isuite.framework.core.xmltransfer.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;


public class IncidentCostRateStateKindTableHandler extends BaseTableHandler implements TableHandler {
	
	public IncidentCostRateStateKindTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		this.doSitePreProcess();
		return true;
	}

	private Object doSitePreProcess() throws Exception {
		
		// get xml values need to match with existing site group questions
		Object xmlKindTiValue = XmlTransferUtil.invokeGetMethod(xmlObject, "KindTransferableIdentity");
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "TransferableIdentity");
		Object xmlIncCostRateStateTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "IncidentCostRateStateTransferableIdentity");
		
		// check if there is matching record
		String sql = "SELECT id " +
					 "FROM isw_inccost_rate_state_kind " + 
					 "WHERE kind_id = (" + "select id from iswl_kind where transferable_identity = '" + (String)xmlKindTiValue + "' ) " +
					 "AND inccost_rate_state_id = (select id from isw_inccost_rate_state where transferable_identity = '" + (String)xmlIncCostRateStateTiValue + "' ) ";

		Object rslt=super.executeQuery(sql);
		if(null != rslt) {
			Long id = TypeConverter.convertToLong(rslt);
			
			sql="UPDATE isw_inccost_rate_state_kind " + 
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
