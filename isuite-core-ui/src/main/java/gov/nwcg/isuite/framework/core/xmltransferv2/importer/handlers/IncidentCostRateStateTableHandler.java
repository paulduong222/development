package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;


public class IncidentCostRateStateTableHandler extends BaseTableHandler implements TableHandler {
	
	public IncidentCostRateStateTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		this.doSitePreProcess();
		return true;
	}

	private Object doSitePreProcess() throws Exception {
		
		// get xml values need to match with existing site group questions
		XmlField xfAgencyTi=xmlTable.getXmlFieldBySqlName("AGENCY_ID");
		XmlField xfIcr=xmlTable.getXmlFieldBySqlName("INCCOST_RATE_ID");
		
		Object xmlAgencyTiValue = XmlTransferUtil.invokeGetMethod(xmlObject, xfAgencyTi.name);
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "TI");
		Object xmlIncCostRateTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, xfIcr.name);
		
		// check if there is matching record
		String sql = "SELECT id " +
					 "FROM isw_inccost_rate_state " + 
					 "WHERE agency_id = (" + "select id from iswl_agency where transferable_identity = '" + (String)xmlAgencyTiValue + "' ) " +
					 "AND inccost_rate_id = (select id from isw_inccost_rate where transferable_identity = '" + (String)xmlIncCostRateTiValue + "' ) ";

		Object rslt=super.executeQuery(sql);
		if(null != rslt) {
			Long id = TypeConverter.convertToLong(rslt);
			
			sql="UPDATE isw_inccost_rate_state " + 
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
