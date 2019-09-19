package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.RossResourceACEMetadataTypeEnum;
import gov.nwcg.isuite.framework.types.RossResourceInternalMetadata;
import gov.nwcg.isuite.framework.types.RossResourceOHMetadataTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;
import gov.nwcg.isuite.xml.ross.MetadataType;
import gov.nwcg.isuite.xml.ross.Row;
import gov.nwcg.isuite.xml.ross.Row.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;


public class RossResourceXmlFileDataVo extends AbstractVo {
	// xml row data fields
	private Long resId;
	private String resourceName;
	private String unitCode;
	private String pdcCode;
	private String gaccUnitCode;
	private String resProvUnitCode;
	private String catalogItemName;
	private String updateDate;
	private Boolean removedFlag;
	private Boolean vendorOwnedFlag;
	private String lastName="";
	private String firstName="";
	private String middleName="";
	
	// other internal supplimental data
	private Boolean person=false;
	private String resourceName2="";
	
	public RossResourceXmlFileDataVo(){
		
	}

	private static RossResourceXmlFileDataVo getInstanceACE(MetadataType metadata,Row row) throws Exception {
		RossResourceXmlFileDataVo vo = new RossResourceXmlFileDataVo();
		
		/*
		 * We map the values using our internal rossdatatypeenum indexes, instead of
		 * relying on the order of the fields in the row set.
		 */
		List<Value> rowValues = row.getValue();
		
		int counter=0;
		
		for(Value v : rowValues){

			int internalIndex = RossResourceACEMetadataTypeEnum.getInternalIndex(metadata,counter);
			
			String val = ((null != v && null != v.getContent()) ? v.getContent() : null);
			
			if(null != val)val=val.toUpperCase();

			vo.setPerson(false);
			
			switch(internalIndex)
			{
				case RossResourceInternalMetadata.ACE_RES_ID:
					vo.setResId(TypeConverter.convertToLong(val));
					break;
				case RossResourceInternalMetadata.ACE_RES_NAME:
					vo.setResourceName(val);
					break;
				case RossResourceInternalMetadata.ACE_RES_GACC_ORG_UNIT_CODE:
					vo.setGaccUnitCode(val);
					break;
				case RossResourceInternalMetadata.ACE_RES_DISP_ORG_UNIT_CODE:
					vo.setPdcCode(val);
					break;
				case RossResourceInternalMetadata.ACE_RES_PROV_UNIT_CODE:
					vo.setUnitCode(val);
					vo.setResProvUnitCode(val);
					break;
				case RossResourceInternalMetadata.ACE_CATALOG_ITEM_NAME:
					vo.setCatalogItemName(val);
					break;
				case RossResourceInternalMetadata.ACE_REMOVED_FLAG:
					if(StringUtility.hasValue(val) && val.equalsIgnoreCase("NO"))
						vo.setRemovedFlag(false);
					else
						vo.setRemovedFlag(true);
					break;
				case RossResourceInternalMetadata.ACE_VENDOR_OWNED_FLAG:
					if(StringUtility.hasValue(val) && val.equalsIgnoreCase("NO"))
						vo.setVendorOwnedFlag(false);
					else
						vo.setVendorOwnedFlag(true);
					break;
			}
			
			counter++;
		}

		return vo;
	}

	private static RossResourceXmlFileDataVo getInstanceOH(MetadataType metadata,Row row) throws Exception {
		RossResourceXmlFileDataVo vo = new RossResourceXmlFileDataVo();
		
		/*
		 * We map the values using our internal rossdatatypeenum indexes, instead of
		 * relying on the order of the fields in the row set.
		 */
		List<Value> rowValues = row.getValue();
		
		int counter=0;
		
		for(Value v : rowValues){

			int internalIndex = RossResourceOHMetadataTypeEnum.getInternalIndex(metadata,counter);
			
			String val = ((null != v && null != v.getContent()) ? v.getContent() : null);
			
			if(null != val)val=val.toUpperCase();

			vo.setPerson(true);
			
			switch(internalIndex)
			{
				case RossResourceInternalMetadata.OH_RES_ID:
					vo.setResId(TypeConverter.convertToLong(val));
					break;
				case RossResourceInternalMetadata.OH_RES_NAME:
					vo.setResourceName(val);
					break;
				case RossResourceInternalMetadata.OH_RES_LAST_NAME:
					vo.setLastName(val);
					break;
				case RossResourceInternalMetadata.OH_RES_FIRST_NAME:
					vo.setFirstName(val);
					break;
				case RossResourceInternalMetadata.OH_RES_MIDDLE_NAME:
					vo.setMiddleName(val);
					break;
				case RossResourceInternalMetadata.OH_RES_GACC_ORG_UNIT_CODE:
					vo.setGaccUnitCode(val);
					break;
				case RossResourceInternalMetadata.OH_RES_DISP_ORG_UNIT_CODE:
					vo.setPdcCode(val);
					break;
				case RossResourceInternalMetadata.OH_RES_PROV_UNIT_CODE:
					vo.setUnitCode(val);
					vo.setResProvUnitCode(val);
					break;
				case RossResourceInternalMetadata.OH_REMOVED_FLAG:
					if(StringUtility.hasValue(val) && val.equalsIgnoreCase("NO"))
						vo.setRemovedFlag(false);
					else
						vo.setRemovedFlag(true);
					break;
				case RossResourceInternalMetadata.OH_VENDOR_OWNED_FLAG:
					if(StringUtility.hasValue(val) && val.equalsIgnoreCase("NO"))
						vo.setVendorOwnedFlag(false);
					else
						vo.setVendorOwnedFlag(true);
					break;
			}
			
			counter++;
		}

		return vo;
	}
	
	/**
	 * @param metadata
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	public static Collection<RossResourceXmlFileDataVo> getInstances(Boolean isACEFile,MetadataType metadata, List<Row> rows) throws Exception {
		Collection<RossResourceXmlFileDataVo> vos = new ArrayList<RossResourceXmlFileDataVo>();
		
		if(isACEFile){
			for(Row row : rows){
				vos.add(getInstanceACE(metadata,row));
			}
		}else{
			for(Row row : rows){
				vos.add(getInstanceOH(metadata,row));
			}
		}
		
		return vos;
	}

	/**
	 * @return the resId
	 */
	public Long getResId() {
		return resId;
	}

	/**
	 * @param resId the resId to set
	 */
	public void setResId(Long resId) {
		this.resId = resId;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * @return the pdcCode
	 */
	public String getPdcCode() {
		return pdcCode;
	}

	/**
	 * @param pdcCode the pdcCode to set
	 */
	public void setPdcCode(String pdcCode) {
		this.pdcCode = pdcCode;
	}

	/**
	 * @return the catalogItemName
	 */
	public String getCatalogItemName() {
		return catalogItemName;
	}

	/**
	 * @param catalogItemName the catalogItemName to set
	 */
	public void setCatalogItemName(String catalogItemName) {
		this.catalogItemName = catalogItemName;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the removedFlag
	 */
	public Boolean getRemovedFlag() {
		return removedFlag;
	}

	/**
	 * @param removedFlag the removedFlag to set
	 */
	public void setRemovedFlag(Boolean removedFlag) {
		this.removedFlag = removedFlag;
	}

	/**
	 * @return the vendorOwnedFlag
	 */
	public Boolean getVendorOwnedFlag() {
		return vendorOwnedFlag;
	}

	/**
	 * @param vendorOwnedFlag the vendorOwnedFlag to set
	 */
	public void setVendorOwnedFlag(Boolean vendorOwnedFlag) {
		this.vendorOwnedFlag = vendorOwnedFlag;
	}

	/**
	 * @return the person
	 */
	public Boolean getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Boolean person) {
		this.person = person;
	}

	/**
	 * @return the resourceName2
	 */
	public String getResourceName2() {
		return resourceName2;
	}

	/**
	 * @param resourceName2 the resourceName2 to set
	 */
	public void setResourceName2(String resourceName2) {
		this.resourceName2 = resourceName2;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the resProvUnitCode
	 */
	public String getResProvUnitCode() {
		return resProvUnitCode;
	}

	/**
	 * @param resProvUnitCode the resProvUnitCode to set
	 */
	public void setResProvUnitCode(String resProvUnitCode) {
		this.resProvUnitCode = resProvUnitCode;
	}

	public Boolean hasValidPersonInformation(){
		// make sure we have first and last names, or resource includes first and last name
		String tmpFirst="";
		String tmpLast="";
		
		if(StringUtility.hasValue(resourceName) && resourceName.indexOf(",")>0){
			StringTokenizer tokens = new StringTokenizer(resourceName,",");
			int i=0;
			while(tokens.hasMoreTokens()){
				String token = (String)tokens.nextToken();
				if(i==0){
					tmpLast=token;
				}else if(i==1){
					tmpFirst=token;
				}
				i++;
			}
		}
		
		// check firstname
		if(StringUtility.hasValue(firstName)){
			if(firstName.length()>35)
				firstName=firstName.substring(0, 34);
		}else if(StringUtility.hasValue(tmpFirst)){
			if(tmpFirst.length()>35)
				firstName=tmpFirst.substring(0, 34);
			else
				firstName=tmpFirst.trim();
		}
		
		// check lastname
		if(StringUtility.hasValue(lastName)){
			if(lastName.length()>35)
				lastName=lastName.substring(0, 34);
		}else if(StringUtility.hasValue(tmpLast)){
			if(tmpLast.length()>35)
				lastName=tmpLast.substring(0, 34);
			else
				lastName=tmpLast.trim();
		}
		
		
		if(StringUtility.hasValue(firstName) && StringUtility.hasValue(lastName))
			return true;
		else
			return false;
	}

	public Boolean hasValidNonPersonInformation(){
		// check resource
		if(StringUtility.hasValue(resourceName)){
			if(resourceName.length()>55)
				resourceName=resourceName.substring(0, 54);
		}
		
		if(StringUtility.hasValue(resourceName))
			return true;
		else
			return false;
	}

	/**
	 * @return the gaccUnitCode
	 */
	public String getGaccUnitCode() {
		return gaccUnitCode;
	}

	/**
	 * @param gaccUnitCode the gaccUnitCode to set
	 */
	public void setGaccUnitCode(String gaccUnitCode) {
		this.gaccUnitCode = gaccUnitCode;
	}

}
