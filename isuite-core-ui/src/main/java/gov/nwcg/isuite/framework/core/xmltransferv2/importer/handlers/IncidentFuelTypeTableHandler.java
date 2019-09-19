package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IncidentFuelTypeTableHandler extends BaseTableHandler implements TableHandler {
	
	
	public IncidentFuelTypeTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		XmlField xf1=xmlTable.getXmlFieldBySqlName("INCIDENT_ID");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("FUEL_TYPE_ID");
		
		String iTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		String ftTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);
		
		if(StringUtility.hasValue(iTi)&& StringUtility.hasValue(ftTi)){
			String sqlCheck="select incident_id "+
						    "from isw_incident_fuel_type " +
						    "where incident_id = (select min(id) from isw_incident where transferable_identity='"+iTi+"') " +
						    "and fuel_type_id = (select min(id) from iswl_fuel_type where transferable_identity='"+ftTi+"') "; 
			
			Object rslt=super.executeQuery(sqlCheck);
			if(null == rslt){
				// create the record
				String sqlInsert="insert into isw_incident_fuel_type (incident_id, fuel_type_id) " +
								 "values (" +
								 "(select min(id) from isw_incident_fuel_type where transferable_identity='"+iTi+"')"+
								 ",(select min(id) from iswl_fuel_type where transferable_identity='"+ftTi+"')"+
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
