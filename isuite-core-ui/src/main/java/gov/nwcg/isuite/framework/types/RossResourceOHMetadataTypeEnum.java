package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.xml.ross.ItemType;
import gov.nwcg.isuite.xml.ross.MetadataType;

import java.util.List;


public enum RossResourceOHMetadataTypeEnum {
	RES_ID("Res ID", 0)
	,RES_NAME("Res Name", 1)
	,LAST_NAME("Last Name", 2)
	,FIRST_NAME("First Name", 3)
	,MIDDLE_NAME("Middle Name", 4)
	,RES_GACC_ORG_UNIT_CODE("Res GACC Org Unit Code",5)
	,RES_DISP_ORG_UNIT_CODE("Res Disp Org Unit Code",6)
	,RES_PROV_UNIT_CODE("Res Prov Unit Code", 7)
	,REMOVED_FLAG("Removed Flag",8)
	,VENDOR_OWNED_FLAG("Vendow Owned Flag",9)
	;
	
	private String columnName="";
	private int internalIdx=-1;
	
	RossResourceOHMetadataTypeEnum(String name, int idx){
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
				for(RossResourceOHMetadataTypeEnum rmdtEnum : RossResourceOHMetadataTypeEnum.values()){
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
