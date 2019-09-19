package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.xml.ross.ItemType;
import gov.nwcg.isuite.xml.ross.MetadataType;

import java.util.List;


public enum RossMetadataTypeEnum {
	INC_NUMBER("Inc Number", 0)
	,INC_NAME("Inc Name", 1)
	,INC_TYPE("Inc Type",2)
	,INC_STATE("Inc State",3)
	,INITIAL_DATE("Initial Date", 4)
	,INC_AGENCY_ABBREV("Inc Agency Abbrev",5)
	,INC_DISP_ORG_UNIT_CODE("Inc Disp Org Unit Code",6)
	,COMPLEX_FLAG("Complex Flag",7)
	,COMPLEX_MEMBER_FLAG("Complex Member Flag",8)
	,COMPLEX_INC_NAME("Complex Inc Name",9)
	,COMPLEX_INC_NUMBER("Complex Inc Number",10)
	,MERGED_INC_FLAG("Merged Inc Flag",11)
	,PREVIOUS_INC_NUMBER("Previous Inc Number",12)
	,TRANSFER_DATE("Transfer Date",13)
	,TRANSFER_DATE_GMT("Transfer Date GMT",14)
	,TRANSFER_DATE_TZ_CODE("Transfer Date TZ Code",15)
	,TRANSFER_FROM_ORG_NAME("Transfer From Org Name",16)
	,TRANSFER_FROM_ORG_UNIT_CODE("Transfer From Org Unit Code",17)
	,TRANSFER_TO_ORG_NAME("Transfer To Org Name",18)
	,TRANSFER_TO_ORG_UNIT_CODE("Transfer To Org Unit Code",19)
	,TRANSFERRED_FLAG("Transferred Flag",20)
	,RES_ID("Res ID",21)
	,REQ_ID("Req ID",22)
	,ROOT_REQ_FLAG("Root Req Flag",23)
	,REQ_NUMBER_PREFIX("Req Number Prefix",24)
	,REQ_NUMBER("Req Number",25)
	,RES_NAME("Res Name",26)
	,ASSIGNMENT_NAME("Assignment Name",27)
	,RES_PROV_AGENCY_ABBREV("Res Prov Agency Abbrev",28)
	,FILLED_CATALOG_ITEM_CODE("Filled Catalog Item Code",29)
	,FILLED_CATALOG_ITEM_NAME("Filled Catalog Item Name",30)
	,EMPLOYMENT_CLASS("Employment Class",31)
	,CATALOG_ITEM_CODE("Catalog Item Code",32)
	,CATALOG_ITEM_NAME("Catalog Item Name",33)
	,QUAL_STATUS("Qual Status",34)
	,RES_PROV_UNIT_CODE("Res Prov Unit Code",35)
	,JET_PORT("Jet Port",36)
	,MOB_ETD("Mob ETD",37)
	,MOB_ETD_TZ_CODE("Mob ETD TZ Code",38)
	,MOB_ETA("Mob ETA",39)
	,MOB_ETA_TZ_CODE("Mob ETA TZ Code",40)
	,MOB_ETE("Mob ETE",41)
	,DEMOB_ETD("Demob ETD",42)
	,DEMOB_ETD_TZ_CODE("Demob ETD TZ Code",43)
	,DEMOB_ETE("Demob ETE",44)
	,VENDOR_OWNED_FLAG("Vendor Owned Flag",45)
	,VENDOR_NAME("Vendor Name",46)
	,CONTRACT_TYPE("Contract Type",47)
	,CONTRACT_NUMBER("Contract Number",48)
	,REQ_CATALOG_NAME("Req Catalog Name",49)
	,REQ_CATEGORY_NAME("Req Category Name",50)
	,ROSS_INC_ID("Inc ID",51)
	,ROSS_LAST_NAME("Last Name",52)
	,ROSS_FIRST_NAME("First Name",53)
	,ROSS_MIDDLE_NAME("Middle Name",54)
	;
	
	private String columnName="";
	private int internalIdx=-1;
	
	RossMetadataTypeEnum(String name, int idx){
		this.columnName=name;
		this.internalIdx=idx;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @return the internalIdx
	 */
	public int getInternalIdx() {
		return internalIdx;
	}

	public static int getInternalIndex(MetadataType metadata, int orderIdx) {
		int counter= 0;

		List<ItemType> metadataItems = metadata.getItem();
		
		for(ItemType itemType : metadataItems){
			if(counter==orderIdx){
				for(RossMetadataTypeEnum rmdtEnum : RossMetadataTypeEnum.values()){
					if(itemType.getName().equals(rmdtEnum.getColumnName())){
						return rmdtEnum.getInternalIdx();
					}
				}
				
			}
			counter++;
		}
		
		return -1;
	}

}
