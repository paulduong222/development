package gov.nwcg.isuite.framework.core.xmltransfer.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;


public class IapPositionItemCodeTableHandler extends BaseTableHandler implements TableHandler {
	
	public IapPositionItemCodeTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		if(super.runMode.equals("SITE")){
			// only have pre processing rules if we there is an incidentGroupId
			Object xmlIncGroupIdValue = XmlTransferUtil.invokeGetMethod(xmlObject, "IncidentGroupId");
			if(null != xmlIncGroupIdValue){
				Long groupId=TypeConverter.convertToLong(xmlIncGroupIdValue);
				if(LongUtility.hasValue(groupId))
					this.doSitePreProcessIG();
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
		Object xmlFormValue = XmlTransferUtil.invokeGetMethod(xmlObject, "Form");
		Object xmlPositionValue = XmlTransferUtil.invokeGetMethod(xmlObject, "Position");
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "TransferableIdentity");
		Object xmlKindTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "KindTransferableIdentity");
		Object xmlSectionValue=XmlTransferUtil.invokeGetMethod(xmlObject, "Section");
		
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
