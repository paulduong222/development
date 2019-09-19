package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
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
		XmlField xf1=xmlTable.getXmlFieldBySqlName("SECTION_NAME");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("TRANSFERABLE_IDENTITY");
		XmlField xf3=xmlTable.getXmlFieldBySqlName("FIELD_LABEL");
		XmlField xf4=xmlTable.getXmlFieldBySqlName("POSITION");
		
		Object xmlSectionNameValue = XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);
		Object xmlFieldLabelValue = XmlTransferUtil.invokeGetMethod(xmlObject, xf3.name);
		Object xmlPositionValue = XmlTransferUtil.invokeGetMethod(xmlObject, xf4.name);
		
		if(null == xmlFieldLabelValue)
			xmlFieldLabelValue="";
		
		String section=(String)(xmlSectionNameValue);
		String field=(String)xmlFieldLabelValue;
		
		if(section.equalsIgnoreCase("OTHER_LABEL") && !field.equalsIgnoreCase("SECURITY UNIT")){
			super.isOtherLabelField=true;
		}

		if(super.isOtherLabelField==false){
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
		}else{
			// check if there is matching prefs record
			String sql="";
			if(super.otherLabelFieldCount==1){
				sql = "SELECT min(id) " +
				 "FROM isw_incident_group_prefs " + 
				 "WHERE section_name = '" + (String)xmlSectionNameValue + "' " +
				 "AND upper(field_label) != 'SECURITY UNIT' " +
				 "AND position = " + xmlPositionValue + " ";
			}else{
				sql = "SELECT max(id) " +
				 "FROM isw_incident_group_prefs " + 
				 "WHERE section_name = '" + (String)xmlSectionNameValue + "' " +
				 "AND upper(field_label) != 'SECURITY UNIT' " + 
				 "AND position = " + xmlPositionValue + " ";
			}
	
			Object rslt=super.executeQuery(sql);
			if(null != rslt) {
				Long id = TypeConverter.convertToLong(rslt);
				
				sql="UPDATE isw_incident_group_prefs " + 
					"SET transferable_identity = '" + (String)xmlTiValue + "' " +
					", field_label='"+(String)xmlFieldLabelValue+"' "+
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
