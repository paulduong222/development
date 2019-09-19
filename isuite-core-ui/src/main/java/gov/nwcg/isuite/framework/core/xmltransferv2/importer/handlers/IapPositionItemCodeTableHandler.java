package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;


public class IapPositionItemCodeTableHandler extends BaseTableHandler implements TableHandler {
	
	public IapPositionItemCodeTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		if(super.runMode.equals("SITE")){
			XmlField xf = xmlTable.getXmlFieldBySqlName("INCIDENT_GROUP_ID");
			
			// only have pre processing rules if we there is an incidentGroupId
			Object xmlIncGroupTiValue = XmlTransferUtil.invokeGetMethod(xmlObject, xf.name);
			if(null != xmlIncGroupTiValue){
				//Long groupId=TypeConverter.convertToLong(xmlIncGroupIdValue);
				//if(LongUtility.hasValue(groupId))
				//	this.doSitePreProcessIG();
				String ti=String.valueOf(xmlIncGroupTiValue);
				if(StringUtility.hasValue(ti)){
					this.doSitePreProcessIG();
				}
			}
		}
		
		return true;
	}

	private Object doSitePreProcessIG() throws Exception {
		/*
		 * For site mode, since there is always only 1 incident group for site,
		 * we need to try and match the iap position item code records for the site group.
		 */

		// get xml values need to match with existing site group iap position item codes
		XmlField xf1=xmlTable.getXmlFieldBySqlName("FORM");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("POSITION");
		XmlField xf3=xmlTable.getXmlFieldBySqlName("KIND_ID");
		XmlField xf4=xmlTable.getXmlFieldBySqlName("SECTION");
		
		Object xmlFormValue = XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		Object xmlPositionValue = XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "TI");
		Object xmlKindTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, xf3.name);
		Object xmlSectionValue=XmlTransferUtil.invokeGetMethod(xmlObject, xf4.name);
		
		// first check if the xmlTiValue already exists in the db?
		String sql="SELECT id FROM isw_iap_position_item_code WHERE transferable_identity = '"+(String)xmlTiValue+"' ";
		Object rslt = super.executeQuery(sql);
		if(null != rslt){
			// the record is already there
			return null;
		}else{
			// its not in the db yet
			// try and find a matching record in xml with one in the db?
			sql="SELECT id FROM iswl_kind where transferable_identity  = '" + (String)xmlKindTiValue + "' ";
			rslt=super.executeQuery(sql);
			if(null != rslt){
				Long kindId=TypeConverter.convertToLong(rslt);
				
				sql="SELECT id " +
				    "FROM isw_iap_position_item_code " +
				    "WHERE kind_id = " + kindId + " " +
				    "AND section = '" + (String)xmlSectionValue + "' "+
				    "AND position = '" + (String)xmlPositionValue + "' " +
				    "AND form = '" + (String)xmlFormValue + "' " +
				    "AND incident_group_id = " + 1 + " ";

				rslt=super.executeQuery(sql);
				if(null != rslt){
					Long id = TypeConverter.convertToLong(rslt);
					
					sql="UPDATE isw_iap_position_item_code " +
						"SET transferable_identity = '" + (String)xmlTiValue + "' " +
						"WHERE id = " + id + " " ;
					
					dao.executeUpdate(sql);
				}
			}
		}

		
		return null;
	}

	public void doPostInsertProcesses() throws Exception {
	}

	public void doPostUpdateProcesses() throws Exception {

	}

}
