package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class RscTrainingFuelTypeTableHandler extends BaseTableHandler implements TableHandler {
	
	
	public RscTrainingFuelTypeTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		XmlField xf1=xmlTable.getXmlFieldBySqlName("RESOURCE_TRAINING_ID");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("FUEL_TYPE_ID");
		
		String rtTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		String ftTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);
		
		if(StringUtility.hasValue(rtTi)&& StringUtility.hasValue(ftTi)){
			String sqlCheck="select resource_training_id "+
						    "from isw_rsc_training_fuel_type " +
						    "where resource_training_id = (select min(id) from isw_resource_training where transferable_identity='"+rtTi+"') " +
						    "and fuel_type_id = (select min(id) from iswl_fuel_type where transferable_identity='"+ftTi+"') "; 
			
			Object rslt=super.executeQuery(sqlCheck);
			if(null == rslt){
				// create the record
				String sqlInsert="insert into isw_rsc_training_fuel_type (resource_training_id, fuel_type_id) " +
								 "values (" +
								 "(select min(id) from isw_resource_training where transferable_identity='"+rtTi+"')"+
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
