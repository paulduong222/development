package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.xml.ross.ItemType;
import gov.nwcg.isuite.xml.ross.MetadataType;

import java.util.List;


public enum RossResourceACEMetadataTypeEnum {
	RES_ID("Res ID", 0)
	,RES_NAME("Res Name", 1)
	,RES_GACC_ORG_UNIT_CODE("Res GACC Org Unit Code",2)
	,RES_DISP_ORG_UNIT_CODE("Res Disp Org Unit Code",3)
	,RES_PROV_UNIT_CODE("Res Prov Unit Code", 4)
	,CATALOG_ITEM_NAME("Catalog Item Name",5)
	,REMOVED_FLAG("Removed Flag",6)
	,VENDOR_OWNED_FLAG("Vendow Owned Flag",7)
	;
	
	private String columnName="";
	private int internalIdx=-1;
	
	RossResourceACEMetadataTypeEnum(String name, int idx){
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
				for(RossResourceACEMetadataTypeEnum rmdtEnum : RossResourceACEMetadataTypeEnum.values()){
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
