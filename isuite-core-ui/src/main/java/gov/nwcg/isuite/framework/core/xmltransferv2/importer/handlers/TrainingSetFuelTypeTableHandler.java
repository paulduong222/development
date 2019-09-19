package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class TrainingSetFuelTypeTableHandler extends BaseTableHandler implements TableHandler {
	
	
	public TrainingSetFuelTypeTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		XmlField xf1=xmlTable.getXmlFieldBySqlName("TRAINING_SETTINGS_ID");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("FUEL_TYPE_ID");
		
		String tsTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		String ftTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);
		
		if(StringUtility.hasValue(tsTi)&& StringUtility.hasValue(ftTi)){
			String sqlCheck="select training_settings_id "+
						    "from isw_training_set_fuel_type " +
						    "where training_settings_id = (select min(id) from isw_training_settings where transferable_identity='"+tsTi+"') " +
						    "and fuel_type_id = (select min(id) from iswl_fuel_type where transferable_identity='"+ftTi+"') "; 
			
			Object rslt=super.executeQuery(sqlCheck);
			if(null == rslt){
				// create the record
				String sqlInsert="insert into isw_training_set_fuel_type (training_settings_id, fuel_type_id) " +
								 "values (" +
								 "(select min(id) from isw_training_settings where transferable_identity='"+tsTi+"')"+
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
