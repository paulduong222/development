package gov.nwcg.isuite.framework.core.xmltransfer.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;


public class IncidentGroupPrefsTableHandler extends BaseTableHandler implements TableHandler {
	
	public IncidentGroupPrefsTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		if(super.runMode.equals("SITE")){
			this.doSitePreProcess();
		}
		
		return true;
	}

	private Object doSitePreProcess() throws Exception {
		/*
		 * For site mode, try and match with existing inc group prefs in the database.
		 */
		
		// get xml values need to match with existing site group questions
		Object xmlSectionNameValue = XmlTransferUtil.invokeGetMethod(xmlObject, "SectionName");
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "TransferableIdentity");
		Object xmlFieldLabelValue = XmlTransferUtil.invokeGetMethod(xmlObject, "FieldLabel");
		if(null == xmlFieldLabelValue)
			xmlFieldLabelValue="";
		
		// check if there is matching prefs record
		String sql = "SELECT id " +
					 "FROM isw_incident_group_prefs " + 
					 "WHERE section_name = '" + (String)xmlSectionNameValue + "' " +
					 "AND field_label = '" + (String)xmlFieldLabelValue + "' " ;

		Object rslt=super.executeQuery(sql);
		if(null != rslt) {
			Long id = TypeConverter.convertToLong(rslt);
			
			sql="UPDATE isw_incident_group_prefs " + 
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
