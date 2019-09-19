package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
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
		XmlField xfCrc=xmlTable.getXmlFieldBySqlName("COST_RATE_CATEGORY");
		XmlField xfIncTi=xmlTable.getXmlFieldBySqlName("INCIDENT_ID");
		XmlField xfIncGrpTi=xmlTable.getXmlFieldBySqlName("INCIDENT_GROUP_ID");
		
		Object xmlCostRateCategoryValue = XmlTransferUtil.invokeGetMethod(xmlObject, xfCrc.name);
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "TI");
		Object xmlIncTiValue = XmlTransferUtil.invokeGetMethod(xmlObject, xfIncTi.name);
		Object xmlIncGrpTiValue = XmlTransferUtil.invokeGetMethod(xmlObject, xfIncGrpTi.name);

		String incidentTi=(String)xmlIncTiValue;
		String incidentGroupTi=(String)xmlIncGrpTiValue;
		
		if(StringUtility.hasValue(incidentTi)){
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
		}
		
		if(StringUtility.hasValue(incidentGroupTi)){
			// check if there is matching incident group record
			String sql = "SELECT id " +
						 "FROM isw_inccost_rate " + 
						 "WHERE cost_rate_category = '" + (String)xmlCostRateCategoryValue + "' " +
						 "AND incident_group_id = (select id from isw_incident_group where transferable_identity = '" + (String)xmlIncGrpTiValue + "' ) " ;

			Object rslt=super.executeQuery(sql);
			if(null != rslt) {
				Long id = TypeConverter.convertToLong(rslt);
				
				sql="UPDATE isw_inccost_rate " + 
					"SET transferable_identity = '" + (String)xmlTiValue + "' " +
					"WHERE id = " + id + " ";
				
				dao.executeUpdate(sql);
			}
		}

		return null;
	}

	public void doPostInsertProcesses() throws Exception {
	}

	public void doPostUpdateProcesses() throws Exception {

	}

}
