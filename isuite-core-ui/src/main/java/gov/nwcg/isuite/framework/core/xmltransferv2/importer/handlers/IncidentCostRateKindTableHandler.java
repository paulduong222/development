package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
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
		XmlField xfKindTi=xmlTable.getXmlFieldBySqlName("KIND_ID");
		XmlField xfRt=xmlTable.getXmlFieldBySqlName("RATE_TYPE");
		XmlField xfIcr=xmlTable.getXmlFieldBySqlName("INCCOST_RATE_ID");
		
		Object xmlKindTiValue = XmlTransferUtil.invokeGetMethod(xmlObject, xfKindTi.name);
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "TI");
		Object xmlRateTypeValue=XmlTransferUtil.invokeGetMethod(xmlObject, xfRt.name);
		Object xmlIncCostRateTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, xfIcr.name);
		
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
