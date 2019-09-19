package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.RossXmlFileData;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileDataImpl;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.RossInternalMetadata;
import gov.nwcg.isuite.framework.types.RossMetadataTypeEnum;
import gov.nwcg.isuite.framework.types.ShortUtil;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;
import gov.nwcg.isuite.xml.ross.MetadataType;
import gov.nwcg.isuite.xml.ross.Row;
import gov.nwcg.isuite.xml.ross.Row.Value;
import gov.nwcg.isuite.xml.ross2.DataTable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;


public class RossXmlFileDataVo extends AbstractVo {
	private String rossIncId;
	private String incNumber;
	private String incName;
	private String incType;
	private String incState;
	private Date initialDate;
	private String incAgencyAbbrev;
	private String incDisOrgUnitCode;
	private String complexFlag;
	private String complexMemberFlag;
	private String complexIncName;
	private String complexIncNumber;
	private String mergedIncFlag;
	private String previousIncNumber;
	private Date transferDate;
	private Date transferDateGmt;
	private String transferDateTzCode;
	private String transferFromOrgName;
	private String transferFromOrgUnitCode;
	private String transferToOrgName;
	private String transferToOrgUnitCode;
	private String transferredFlag;
	private Long resId;
	private BigDecimal reqId; 
	private String rootReqFlag;
	private String reqNumberPrefix;
	private String reqNumber;
	private String resName;
	private String assignmentName;
	private String resProvAgencyAbbrev;
	private String filledCatalogItemCode;
	private String filledCatalogItemName;
	private String employmentClass;
	private String catalogItemCode;
	private String catalogItemName;
	private String qualStatus;
	private String resProvUnitCode;
	private String jetPort;
	private Date mobEtd;
	private String mobEtdTzCode;
	private Date mobEta;
	private String mobEtaTzCode;
	private String mobEte;
	private Date demobEtd;
	private String demobEtdTzCode;
	private String demobEte;
	private String vendorOwnedFlag;
	private String vendorName;
	private String contractType;
	private String contractNumber;
	private String reqCatalogName;
	private String reqCategoryName;
	
	private Boolean rossAssignment=false;
	private String importStatus;
	
	private String firstName;
	private String lastName;
	private String middleName;
	
	public RossXmlFileDataVo(){
		
	}

	/**
	 * @param metadata
	 * @param row
	 * @return
	 * @throws Exception
	 */
	private static RossXmlFileDataVo getInstance(MetadataType metadata,Row row) throws Exception {
		RossXmlFileDataVo vo = new RossXmlFileDataVo();
		
		/*
		 * We map the values using our internal rossdatatypeenum indexes, instead of
		 * relying on the order of the fields in the row set.
		 */
		List<Value> rowValues = row.getValue();
		
		int counter=0;
		
		for(Value v : rowValues){

			int internalIndex = RossMetadataTypeEnum.getInternalIndex(metadata,counter);
			
			String val = ((null != v && null != v.getContent()) ? v.getContent() : null);
			
			if(null != val)val=val.toUpperCase();
			
			switch(internalIndex)
			{
				case RossInternalMetadata.INC_NUMBER:
					vo.setIncNumber(val);
					break;
				case RossInternalMetadata.INC_NAME:
					vo.setIncName(val);
					break;
				case RossInternalMetadata.INC_TYPE:
					vo.setIncType(val);
					break;
				case RossInternalMetadata.INC_STATE:
					vo.setIncState(val);
					break;
				case RossInternalMetadata.INITIAL_DATE:
					vo.setInitialDate((null != val ? DateUtil.toDate(val, DateUtil.YYYYMMDD_T_HH_MI_SS): null ));
					break;
				case RossInternalMetadata.INC_AGENCY_ABBREV:
					vo.setIncAgencyAbbrev(val);
					break;
				case RossInternalMetadata.INC_DISP_ORG_UNIT_CODE:
					vo.setIncDisOrgUnitCode(val);
					break;
				case RossInternalMetadata.COMPLEX_FLAG:
					vo.setComplexFlag(val);
					break;
				case RossInternalMetadata.COMPLEX_MEMBER_FLAG:
					vo.setComplexMemberFlag(val);
					break;
				case RossInternalMetadata.COMPLEX_INC_NAME:
					vo.setComplexIncName(val);
					break;
				case RossInternalMetadata.COMPLEX_INC_NUMBER:
					vo.setComplexIncNumber(val);
					break;
				case RossInternalMetadata.MERGED_INC_FLAG:
					vo.setMergedIncFlag(val);
					break;
				case RossInternalMetadata.PREVIOUS_INC_NUMBER:
					vo.setPreviousIncNumber(val);
					break;
				case RossInternalMetadata.TRANSFER_DATE:
					vo.setTransferDate((null != val ? DateUtil.toDate(val, DateUtil.YYYYMMDD_T_HH_MI_SS): null ));
					break;
				case RossInternalMetadata.TRANSFER_DATE_GMT:
					vo.setTransferDateGmt((null != val ? DateUtil.toDate(val, DateUtil.YYYYMMDD_T_HH_MI_SS): null ));
					break;
				case RossInternalMetadata.TRANSFER_DATE_TZ_CODE:
					vo.setTransferDateTzCode(val);
					break;
				case RossInternalMetadata.TRANSFER_FROM_ORG_NAME:
					vo.setTransferFromOrgName(val);
					break;
				case RossInternalMetadata.TRANSFER_FROM_ORG_UNIT_CODE:
					vo.setTransferFromOrgUnitCode(val);
					break;
				case RossInternalMetadata.TRANSFER_TO_ORG_NAME:
					vo.setTransferToOrgName(val);
					break;
				case RossInternalMetadata.TRANSFER_TO_ORG_UNIT_CODE:
					vo.setTransferToOrgUnitCode(val);
					break;
				case RossInternalMetadata.TRANSFERRED_FLAG:
					vo.setTransferredFlag(val);
					break;
				case RossInternalMetadata.RES_ID:
					vo.setResId(TypeConverter.convertToLong(val));
					break;
				case RossInternalMetadata.REQ_ID:
					vo.setReqId(TypeConverter.convertToBigDecimal(val));
					break;
				case RossInternalMetadata.ROOT_REQ_FLAG:
					vo.setRootReqFlag(val);
					break;
				case RossInternalMetadata.REQ_NUMBER_PREFIX:
					vo.setReqNumberPrefix(val);
					break;
				case RossInternalMetadata.REQ_NUMBER:
//					vo.setReqNumber(val);
					vo.setReqNumber(StringUtility.hasValue(val) ? val : "");
					break;
				case RossInternalMetadata.RES_NAME:
					vo.setResName(val);
					break;
				case RossInternalMetadata.ASSIGNMENT_NAME:
					vo.setAssignmentName(val);
					break;
				case RossInternalMetadata.RES_PROV_AGENCY_ABBREV:
					vo.setResProvAgencyAbbrev(val);
					break;
				case RossInternalMetadata.FILLED_CATALOG_ITEM_CODE:
					vo.setFilledCatalogItemCode(val);
					break;
				case RossInternalMetadata.FILLED_CATALOG_ITEM_NAME:
					vo.setFilledCatalogItemName(val);
					break;
				case RossInternalMetadata.EMPLOYMENT_CLASS:
					vo.setEmploymentClass(val);
					break;
				case RossInternalMetadata.CATALOG_ITEM_CODE:
					vo.setCatalogItemCode(val);
					break;
				case RossInternalMetadata.CATALOG_ITEM_NAME:
					vo.setCatalogItemName(val);
					break;
				case RossInternalMetadata.QUAL_STATUS:
					vo.setQualStatus(val);
					break;
				case RossInternalMetadata.RES_PROV_UNIT_CODE:
					vo.setResProvUnitCode(val);
					break;
				case RossInternalMetadata.JET_PORT:
					vo.setJetPort(val);
					break;
				case RossInternalMetadata.MOB_ETD:
					vo.setMobEtd((null != val ? DateUtil.toDate(val, DateUtil.YYYYMMDD_T_HH_MI_SS): null ));
					break;
				case RossInternalMetadata.MOB_ETD_TZ_CODE:
					vo.setMobEtdTzCode(val);
					break;
				case RossInternalMetadata.MOB_ETA:
					vo.setMobEta((null != val ? DateUtil.toDate(val, DateUtil.YYYYMMDD_T_HH_MI_SS): null ));
					break;
				case RossInternalMetadata.MOB_ETA_TZ_CODE:
					vo.setMobEtaTzCode(val);
					break;
				case RossInternalMetadata.MOB_ETE:
					vo.setMobEte(val);
					break;
				case RossInternalMetadata.DEMOB_ETD:
					vo.setDemobEtd((null != val ? DateUtil.toDate(val, DateUtil.YYYYMMDD_T_HH_MI_SS): null ));
					break;
				case RossInternalMetadata.DEMOB_ETD_TZ_CODE:
					break;
				case RossInternalMetadata.DEMOB_ETE:
					vo.setDemobEte(val);
					break;
				case RossInternalMetadata.VENDOR_OWNED_FLAG:
					vo.setVendorOwnedFlag(val);
					break;
				case RossInternalMetadata.VENDOR_NAME:
					vo.setVendorName(val);
					break;
				case RossInternalMetadata.CONTRACT_TYPE:
					vo.setContractType(val);
					break;
				case RossInternalMetadata.CONTRACT_NUMBER:
					vo.setContractNumber(val);
					break;
				case RossInternalMetadata.REQ_CATALOG_NAME:
					vo.setReqCatalogName(val);
					break;
				case RossInternalMetadata.REQ_CATEGORY_NAME:
					vo.setReqCategoryName(val);
					break;
				case RossInternalMetadata.ROSS_INC_ID:
					vo.setRossIncId(val);
					break;
				case RossInternalMetadata.ROSS_LAST_NAME:
					vo.setLastName(val);
					break;
				case RossInternalMetadata.ROSS_FIRST_NAME:
					vo.setFirstName(val);
					break;
				case RossInternalMetadata.ROSS_MIDDLE_NAME:
					vo.setMiddleName(val);
					break;
			}
			
			counter++;
		}

		/*
		if( (StringUtility.hasValue(vo.getCatalogItemCode()) && 
				vo.getCatalogItemCode().equals(vo.getFilledCatalogItemCode())) 
			|| (StringUtility.hasValue(vo.getCatalogItemName()) && vo.getCatalogItemName().equals(vo.getFilledCatalogItemName())) ){
			vo.setRossAssignment(Boolean.TRUE);
		}else
			vo.setRossAssignment(Boolean.FALSE);
		*/
		
		if(StringUtility.hasValue(vo.getFilledCatalogItemName())){
			vo.setRossAssignment(Boolean.TRUE);
		}else
			vo.setRossAssignment(Boolean.FALSE);
		
		return vo;
	}
	
	/**
	 * @param metadata
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	public static Collection<RossXmlFileDataVo> getInstances(MetadataType metadata, List<Row> rows) throws Exception {
		Collection<RossXmlFileDataVo> vos = new ArrayList<RossXmlFileDataVo>();
		
		for(Row row : rows){
			vos.add(getInstance(metadata,row));
		}
		
		return vos;
	}

	public static Collection<RossXmlFileDataVo> getInstances2(gov.nwcg.isuite.xml.ross2.DataSet rootNode) throws IsuiteException,Exception {
		Collection<RossXmlFileDataVo> vos = new ArrayList<RossXmlFileDataVo>();
		
		List<DataTable> dataTables = rootNode.getDataTable();
		gov.nwcg.isuite.xml.ross2.Row incidentRow=null;
		String incIdNode="";
		String incNumberNode="";
		String incStateNode="";
		String initialDateNode="";
		String incDispOrgUnitCodeNode="";
		String mergedIncFlagNode="";
		String previousIncNumberNode="";
		String incTypeNode="";
		String incAgencyAbbrevNode="";
		
		int cnt=0;
		for(DataTable dt : dataTables){
			cnt++;
			String dataTableId=dt.getId();
			if(cnt==2 && null==dataTableId){
				ErrorObject error = new ErrorObject(ErrorEnum._GENERIC,"No Resource Data for Incident");
				throw new IsuiteException(error);
			}
			if(dataTableId.equals("IncidentHeader")){
				List<gov.nwcg.isuite.xml.ross2.Row> rows=dt.getRow();
				for(gov.nwcg.isuite.xml.ross2.Row r : rows){
					incidentRow=r;
					break;
				}
			}else{
				List<gov.nwcg.isuite.xml.ross2.Row> rows=dt.getRow();
				for(gov.nwcg.isuite.xml.ross2.Row r : rows){
					
					System.out.println(r.getResName());
					if(r.getResName().equals("BECKER, CHRISTY A")){
						System.out.println("");
					}
						
					RossXmlFileDataVo rxfdVo = new RossXmlFileDataVo();
					String val="";
					
					rxfdVo.setRossIncId(incidentRow.getIncID());
					rxfdVo.setIncNumber(incidentRow.getIncNumber());
					rxfdVo.setIncName(incidentRow.getIncName());
					rxfdVo.setIncState(incidentRow.getIncState());

					val=incidentRow.getInitialDate();
					rxfdVo.setInitialDate((StringUtility.hasValue(val) ? DateUtil.toDate(val, DateUtil.YYYYMMDD_T_HH_MI_SS): null ));
					
					rxfdVo.setIncDisOrgUnitCode(incidentRow.getIncDispOrgUnitCode());
					rxfdVo.setMergedIncFlag(incidentRow.getMergedIncFlag());
					rxfdVo.setPreviousIncNumber(incidentRow.getPreviousIncNumber());
					rxfdVo.setIncType(incidentRow.getIncType());
					rxfdVo.setIncAgencyAbbrev(incidentRow.getIncAgencyAbbrev());

					rxfdVo.setAssignmentName(r.getAssignmentName());

					val=r.getReqID();
					if(!StringUtility.hasValue(val))
						val=null;
					rxfdVo.setReqId(TypeConverter.convertToBigDecimal(val));
					
					// r.getReqStatusCode() ?
					rxfdVo.setRootReqFlag(r.getRootReqFlag());
					rxfdVo.setReqNumberPrefix(r.getReqNumberPrefix());
					rxfdVo.setReqNumber(r.getReqNumber());
					// r.getReqStatus()  ?
					rxfdVo.setReqCatalogName(r.getReqCatalogName());
					rxfdVo.setReqCategoryName(r.getReqCategoryName());
					rxfdVo.setFilledCatalogItemCode(r.getFilledCatalogItemCode());
					rxfdVo.setFilledCatalogItemName(r.getFilledCatalogItemName());
					rxfdVo.setContractType(r.getContractType());
					rxfdVo.setContractNumber(r.getContractNumber());
					
					val=r.getMobETD();
					rxfdVo.setMobEtd((StringUtility.hasValue(val) ? DateUtil.toDate(val, DateUtil.YYYYMMDD_T_HH_MI_SS): null ));
					
					val=r.getResID();
					if(!StringUtility.hasValue(val))
						val=null;
					rxfdVo.setResId(TypeConverter.convertToLong(val));
					
					rxfdVo.setResName(r.getResName());
					rxfdVo.setLastName(r.getLastName());
					rxfdVo.setFirstName(r.getFirstName());
					rxfdVo.setMiddleName(r.getMiddleName());
					rxfdVo.setResProvUnitCode(r.getResProvUnitCode());
					rxfdVo.setVendorOwnedFlag(r.getVendorOwnedFlag());
					rxfdVo.setVendorName(r.getVendorName());
					rxfdVo.setJetPort(r.getJetPort());
					rxfdVo.setResProvAgencyAbbrev(r.getResProvAgencyAbbrev());
					rxfdVo.setEmploymentClass(r.getEmploymentClass());
					
					if(StringUtility.hasValue(r.getFilledCatalogItemName())){
						rxfdVo.setRossAssignment(Boolean.TRUE);
					}else
						rxfdVo.setRossAssignment(Boolean.FALSE);
					
					vos.add(rxfdVo);
				}
			}
		}
		
		return vos;
	}

	
	/**
	 * @param vos
	 * @return
	 */
	public static Collection<String> getDistinctIncidentNames(Collection<RossXmlFileDataVo> vos){
		Collection<String> names = new ArrayList<String>();
		
		if(null != vos && vos.size()>0){
			for(RossXmlFileDataVo vo : vos){
				if(StringUtility.hasValue(vo.getIncName())){
					if(!names.contains(vo.getIncName())){
						names.add(vo.getIncName());
					}
				}
			}
		}
		
		return names;
	}
	
	/**
	 * @param vos
	 * @return
	 */
	public static String getRossIncId(Collection<RossXmlFileDataVo> vos) {
		if(null != vos && vos.size()>0){
			for(RossXmlFileDataVo vo : vos){
				if(StringUtility.hasValue(vo.getRossIncId())){
					return vo.getRossIncId();
				}
			}
		}
		return "";
	}

	/**
	 * @param vos
	 * @return
	 */
	public static String getIncidentName(Collection<RossXmlFileDataVo> vos) {
		if(null != vos && vos.size()>0){
			for(RossXmlFileDataVo vo : vos){
				if(StringUtility.hasValue(vo.getIncName())){
					return vo.getIncName();
				}
			}
		}
		return "";
	}

	public static Collection<RossXmlFileDataVo> getByInc(String incident,Collection<RossXmlFileDataVo> vos){
		Collection<RossXmlFileDataVo> rtnVos = new ArrayList<RossXmlFileDataVo>();
		
		for(RossXmlFileDataVo rxfdVo : vos){
			if(rxfdVo.getRossIncId().equals(incident)){
				// exclude s request numbers with numeric filled catalog item code
				Boolean excluded = false;
				
				if(StringUtility.hasValue(rxfdVo.getReqNumber())){
					if(rxfdVo.getReqNumber().toUpperCase().startsWith("S")){
						if(StringUtility.hasValue(rxfdVo.getFilledCatalogItemCode())){
							String code=StringUtility.removeNonNumeric(rxfdVo.getFilledCatalogItemCode());
							try{
								if(BooleanUtility.isTrue(NumberUtils.isDigits(code)))
									excluded=true;
							}catch(Exception ee){}
						}
					}
				}
				if(!excluded)
					rtnVos.add(rxfdVo);
			}
		}

		return rtnVos;
	}

	public static Collection<RossXmlFileVo> getRossXmlFileVos(Collection<RossXmlFileDataVo> vos){
		HashMap<String,RossXmlFileVo> map = new HashMap<String,RossXmlFileVo>();
		
		for(RossXmlFileDataVo rxfdVo : vos){
			if(!map.containsKey(rxfdVo.getIncName())){
				RossXmlFileVo rxfVo = new RossXmlFileVo();
				rxfVo.setRossIncidentId(rxfdVo.getRossIncId());
				//System.out.println(rxfdVo.getRossIncId());
				rxfVo.setIncidentName(rxfdVo.getIncName());
				rxfVo.setIncidentEventType(rxfdVo.getIncType());
				rxfVo.setIncidentNumber(rxfdVo.getIncNumber());
				rxfVo.setIncidentStartDate(rxfdVo.getInitialDate());
				
				map.put(rxfVo.getIncidentName(), rxfVo);
			}
		}
		
		return map.values();
	}
	
	public static Collection<String> getIncidentNames(Collection<RossXmlFileDataVo> vos) {
		Collection<String> names = new ArrayList<String>();
		
		if(null != vos && vos.size()>0){
			for(RossXmlFileDataVo vo : vos){
				if(StringUtility.hasValue(vo.getIncName())){
					if(!names.contains(vo.getIncName())){
						names.add(vo.getIncName());
					}
				}
			}
		}
		return names;
	}
	
	/**
	 * @param vos
	 * @return
	 */
	public static String getIncidentNumber(Collection<RossXmlFileDataVo> vos) {
		if(null != vos && vos.size()>0){
			for(RossXmlFileDataVo vo : vos){
				if(StringUtility.hasValue(vo.getIncNumber())){
					return vo.getIncNumber();
				}
			}
		}
		return "";
	}

	/**
	 * @param vos
	 * @return
	 */
	public static String getIncidentEventType(Collection<RossXmlFileDataVo> vos) {
		if(null != vos && vos.size()>0){
			for(RossXmlFileDataVo vo : vos){
				if(StringUtility.hasValue(vo.getIncType())){
					return vo.getIncType();
				}
			}
		}
		return "";
	}

	/**
	 * @param vos
	 * @return
	 */
	public static Date getIncidentStartDate(Collection<RossXmlFileDataVo> vos) {
		if(null != vos && vos.size()>0){
			for(RossXmlFileDataVo vo : vos){
				if(null != vo.getInitialDate())
					return vo.getInitialDate();
			}
		}
		return null;
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RossXmlFileDataVo getInstance(RossXmlFileData entity, Boolean cascadable) throws Exception {
		RossXmlFileDataVo vo = new RossXmlFileDataVo();
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setAssignmentName(entity.getAssignmentName());
			vo.setCatalogItemCode(entity.getCatalogItemCode());
			vo.setCatalogItemName(entity.getCatalogItemName());
			vo.setComplexFlag(entity.getComplexFlag());
			vo.setComplexIncName(entity.getComplexIncName());
			vo.setComplexIncNumber(entity.getComplexIncNumber());
			vo.setComplexMemberFlag(entity.getComplexMemberFlag());
			vo.setContractNumber(entity.getContractNumber());
			vo.setContractType(entity.getContractType());
			vo.setDemobEtd(entity.getDemobEtd());
			vo.setDemobEtdTzCode(entity.getDemobEtdTzCode());
			vo.setDemobEte(entity.getDemobEte());
			vo.setEmploymentClass(entity.getEmploymentClass());
			vo.setFilledCatalogItemCode(entity.getFilledCatalogItemCode());
			vo.setFilledCatalogItemName(entity.getFilledCatalogItemName());
			vo.setIncAgencyAbbrev(entity.getIncAgencyAbbrev());
			vo.setIncDisOrgUnitCode(entity.getIncDisOrgUnitCode());
			vo.setIncName(entity.getIncName());
			vo.setIncNumber(entity.getIncNumber());
			vo.setIncState(entity.getIncState());
			vo.setIncType(entity.getIncType());
			vo.setInitialDate(entity.getInitialDate());
			vo.setJetPort(entity.getJetPort());
			vo.setMergedIncFlag(entity.getMergedIncFlag());
			vo.setMobEta(entity.getMobEta());
			vo.setMobEtaTzCode(entity.getMobEtaTzCode());
			vo.setMobEtd(entity.getMobEtd());
			vo.setMobEtdTzCode(entity.getMobEtdTzCode());
			vo.setMobEte(entity.getMobEte());
			vo.setPreviousIncNumber(entity.getPreviousIncNumber());
			vo.setQualStatus(entity.getQualStatus());
			vo.setReqCatalogName(entity.getReqCatalogName());
			vo.setReqCategoryName(entity.getReqCategoryName());
			vo.setReqId(entity.getReqId());
			vo.setReqNumber(entity.getReqNumber());
			vo.setReqNumberPrefix(entity.getReqNumberPrefix());
			vo.setResId(entity.getResId());
			vo.setResName(entity.getResName());
			vo.setResProvAgencyAbbrev(entity.getResProvAgencyAbbrev());
			vo.setResProvUnitCode(entity.getResProvUnitCode());
			vo.setRootReqFlag(entity.getRootReqFlag());
			vo.setTransferDate(entity.getTransferDate());
			vo.setTransferDateGmt(entity.getTransferDateGmt());
			vo.setTransferDateTzCode(entity.getTransferDateTzCode());
			vo.setTransferFromOrgName(entity.getTransferFromOrgName());
			vo.setTransferFromOrgUnitCode(entity.getTransferFromOrgUnitCode());
			vo.setTransferredFlag(entity.getTransferredFlag());
			vo.setTransferToOrgName(entity.getTransferToOrgName());
			vo.setTransferToOrgUnitCode(entity.getTransferToOrgUnitCode());
			vo.setVendorName(entity.getVendorName());
			vo.setVendorOwnedFlag(entity.getVendorOwnedFlag());

			vo.setRossAssignment(ShortUtil.toBoolean(entity.getRossAssignment()));
			vo.setImportStatus(entity.getImportStatus());

			vo.setLastName(entity.getLastName());
			vo.setFirstName(entity.getFirstName());
			vo.setMiddleName(entity.getMiddleName());
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<RossXmlFileDataVo> getInstances(Collection<RossXmlFileData> entities, Boolean cascadable) throws Exception {
		Collection<RossXmlFileDataVo> vos = new ArrayList<RossXmlFileDataVo>();
		
		for(RossXmlFileData entity : entities){
			vos.add(RossXmlFileDataVo.getInstance(entity,cascadable));
		}
		
		return vos;
	}


	/**
	 * @param vo
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RossXmlFileData toEntity(RossXmlFileData entity, RossXmlFileDataVo vo, Boolean cascadable, Persistable... persistables) throws Exception {
		if(null == entity)
			entity = new RossXmlFileDataImpl();

		entity.setId(vo.getId());
		
		if(cascadable){
			
			RossXmlFile rxfEntity = (RossXmlFile)getPersistableObject(persistables, RossXmlFileImpl.class);
			if(null != rxfEntity)
				entity.setRossXmlFile(rxfEntity);
			
			entity.setAssignmentName(vo.getAssignmentName());
			entity.setCatalogItemCode(vo.getCatalogItemCode());
			entity.setCatalogItemName(vo.getCatalogItemName());
			entity.setComplexFlag(vo.getComplexFlag());
			entity.setComplexIncName(vo.getComplexIncName());
			entity.setComplexIncNumber(vo.getComplexIncNumber());
			entity.setComplexMemberFlag(vo.getComplexMemberFlag());
			entity.setContractNumber(vo.getContractNumber());
			entity.setContractType(vo.getContractType());
			entity.setDemobEtd(vo.getDemobEtd());
			entity.setDemobEtdTzCode(vo.getDemobEtdTzCode());
			entity.setDemobEte(vo.getDemobEte());
			entity.setEmploymentClass(vo.getEmploymentClass());
			entity.setFilledCatalogItemCode(vo.getFilledCatalogItemCode());
			entity.setFilledCatalogItemName(vo.getFilledCatalogItemName());
			entity.setIncAgencyAbbrev(vo.getIncAgencyAbbrev());
			entity.setIncDisOrgUnitCode(vo.getIncDisOrgUnitCode());
			entity.setIncName(vo.getIncName());
			entity.setIncNumber(vo.getIncNumber());
			entity.setIncState(vo.getIncState());
			entity.setIncType(vo.getIncType());
			entity.setInitialDate(vo.getInitialDate());
			entity.setJetPort(vo.getJetPort());
			entity.setMergedIncFlag(vo.getMergedIncFlag());
			entity.setMobEta(vo.getMobEta());
			entity.setMobEtaTzCode(vo.getMobEtaTzCode());
			entity.setMobEtd(vo.getMobEtd());
			entity.setMobEtdTzCode(vo.getMobEtdTzCode());
			entity.setMobEte(vo.getMobEte());
			entity.setPreviousIncNumber(vo.getPreviousIncNumber());
			entity.setQualStatus(vo.getQualStatus());
			entity.setReqCatalogName(vo.getReqCatalogName());
			entity.setReqCategoryName(vo.getReqCategoryName());
			entity.setReqId(vo.getReqId());
			entity.setReqNumber(vo.getReqNumber());
			entity.setReqNumberPrefix(vo.getReqNumberPrefix());
			entity.setResId(vo.getResId());
			entity.setResName(vo.getResName());
			entity.setResProvAgencyAbbrev(vo.getResProvAgencyAbbrev());
			entity.setResProvUnitCode(vo.getResProvUnitCode());
			entity.setRootReqFlag(vo.getRootReqFlag());
			entity.setTransferDate(vo.getTransferDate());
			entity.setTransferDateGmt(vo.getTransferDateGmt());
			entity.setTransferDateTzCode(vo.getTransferDateTzCode());
			entity.setTransferFromOrgName(vo.getTransferFromOrgName());
			entity.setTransferFromOrgUnitCode(vo.getTransferFromOrgUnitCode());
			entity.setTransferredFlag(vo.getTransferredFlag());
			entity.setTransferToOrgName(vo.getTransferToOrgName());
			entity.setTransferToOrgUnitCode(vo.getTransferToOrgUnitCode());
			entity.setVendorName(vo.getVendorName());
			entity.setVendorOwnedFlag(vo.getVendorOwnedFlag());

			entity.setRossAssignment(ShortUtil.toShort(vo.getRossAssignment()));
			entity.setImportStatus(vo.getImportStatus());
			
			entity.setLastName(vo.getLastName());
			entity.setFirstName(vo.getFirstName());
			entity.setMiddleName(vo.getMiddleName());
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<RossXmlFileData> toEntities(Collection<RossXmlFileDataVo> vos, Boolean cascadable, Persistable... persistables) throws Exception {
		Collection<RossXmlFileData> entities = new ArrayList<RossXmlFileData>();
		
		for(RossXmlFileDataVo vo : vos){
			entities.add(RossXmlFileDataVo.toEntity(null,vo,cascadable, persistables));
		}
		
		return entities;
	}

	/**
	 * @param entity
	 * @throws ValidationException
	 */
	private static void validateEntity(RossXmlFileData entity) throws ValidationException {
		
	}
	
	/**
	 * @return the incNumber
	 */
	public String getIncNumber() {
		return incNumber;
	}

	/**
	 * @param incNumber the incNumber to set
	 */
	public void setIncNumber(String incNumber) {
		this.incNumber = incNumber;
	}

	/**
	 * @return the incName
	 */
	public String getIncName() {
		return incName;
	}

	/**
	 * @param incName the incName to set
	 */
	public void setIncName(String incName) {
		this.incName = incName;
	}

	/**
	 * @return the incType
	 */
	public String getIncType() {
		return incType;
	}

	/**
	 * @param incType the incType to set
	 */
	public void setIncType(String incType) {
		this.incType = incType;
	}

	/**
	 * @return the incState
	 */
	public String getIncState() {
		return incState;
	}

	/**
	 * @param incState the incState to set
	 */
	public void setIncState(String incState) {
		this.incState = incState;
	}

	/**
	 * @return the initialDate
	 */
	public Date getInitialDate() {
		return initialDate;
	}

	/**
	 * @param initialDate the initialDate to set
	 */
	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	/**
	 * @return the incAgencyAbbrev
	 */
	public String getIncAgencyAbbrev() {
		return incAgencyAbbrev;
	}

	/**
	 * @param incAgencyAbbrev the incAgencyAbbrev to set
	 */
	public void setIncAgencyAbbrev(String incAgencyAbbrev) {
		this.incAgencyAbbrev = incAgencyAbbrev;
	}

	/**
	 * @return the incDisOrgUnitCode
	 */
	public String getIncDisOrgUnitCode() {
		return incDisOrgUnitCode;
	}

	/**
	 * @param incDisOrgUnitCode the incDisOrgUnitCode to set
	 */
	public void setIncDisOrgUnitCode(String incDisOrgUnitCode) {
		this.incDisOrgUnitCode = incDisOrgUnitCode;
	}

	/**
	 * @return the complexFlag
	 */
	public String getComplexFlag() {
		return complexFlag;
	}

	/**
	 * @param complexFlag the complexFlag to set
	 */
	public void setComplexFlag(String complexFlag) {
		this.complexFlag = complexFlag;
	}

	/**
	 * @return the complexMemberFlag
	 */
	public String getComplexMemberFlag() {
		return complexMemberFlag;
	}

	/**
	 * @param complexMemberFlag the complexMemberFlag to set
	 */
	public void setComplexMemberFlag(String complexMemberFlag) {
		this.complexMemberFlag = complexMemberFlag;
	}

	/**
	 * @return the complexIncName
	 */
	public String getComplexIncName() {
		return complexIncName;
	}

	/**
	 * @param complexIncName the complexIncName to set
	 */
	public void setComplexIncName(String complexIncName) {
		this.complexIncName = complexIncName;
	}

	/**
	 * @return the complexIncNumber
	 */
	public String getComplexIncNumber() {
		return complexIncNumber;
	}

	/**
	 * @param complexIncNumber the complexIncNumber to set
	 */
	public void setComplexIncNumber(String complexIncNumber) {
		this.complexIncNumber = complexIncNumber;
	}

	/**
	 * @return the mergedIncFlag
	 */
	public String getMergedIncFlag() {
		return mergedIncFlag;
	}

	/**
	 * @param mergedIncFlag the mergedIncFlag to set
	 */
	public void setMergedIncFlag(String mergedIncFlag) {
		this.mergedIncFlag = mergedIncFlag;
	}

	/**
	 * @return the previousIncNumber
	 */
	public String getPreviousIncNumber() {
		return previousIncNumber;
	}

	/**
	 * @param previousIncNumber the previousIncNumber to set
	 */
	public void setPreviousIncNumber(String previousIncNumber) {
		this.previousIncNumber = previousIncNumber;
	}

	/**
	 * @return the transferDate
	 */
	public Date getTransferDate() {
		return transferDate;
	}

	/**
	 * @param transferDate the transferDate to set
	 */
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	/**
	 * @return the transferDateGmt
	 */
	public Date getTransferDateGmt() {
		return transferDateGmt;
	}

	/**
	 * @param transferDateGmt the transferDateGmt to set
	 */
	public void setTransferDateGmt(Date transferDateGmt) {
		this.transferDateGmt = transferDateGmt;
	}

	/**
	 * @return the transferDateTzCode
	 */
	public String getTransferDateTzCode() {
		return transferDateTzCode;
	}

	/**
	 * @param transferDateTzCode the transferDateTzCode to set
	 */
	public void setTransferDateTzCode(String transferDateTzCode) {
		this.transferDateTzCode = transferDateTzCode;
	}

	/**
	 * @return the transferFromOrgName
	 */
	public String getTransferFromOrgName() {
		return transferFromOrgName;
	}

	/**
	 * @param transferFromOrgName the transferFromOrgName to set
	 */
	public void setTransferFromOrgName(String transferFromOrgName) {
		this.transferFromOrgName = transferFromOrgName;
	}

	/**
	 * @return the transferFromOrgUnitCode
	 */
	public String getTransferFromOrgUnitCode() {
		return transferFromOrgUnitCode;
	}

	/**
	 * @param transferFromOrgUnitCode the transferFromOrgUnitCode to set
	 */
	public void setTransferFromOrgUnitCode(String transferFromOrgUnitCode) {
		this.transferFromOrgUnitCode = transferFromOrgUnitCode;
	}

	/**
	 * @return the transferToOrgName
	 */
	public String getTransferToOrgName() {
		return transferToOrgName;
	}

	/**
	 * @param transferToOrgName the transferToOrgName to set
	 */
	public void setTransferToOrgName(String transferToOrgName) {
		this.transferToOrgName = transferToOrgName;
	}

	/**
	 * @return the transferToOrgUnitCode
	 */
	public String getTransferToOrgUnitCode() {
		return transferToOrgUnitCode;
	}

	/**
	 * @param transferToOrgUnitCode the transferToOrgUnitCode to set
	 */
	public void setTransferToOrgUnitCode(String transferToOrgUnitCode) {
		this.transferToOrgUnitCode = transferToOrgUnitCode;
	}

	/**
	 * @return the transferredFlag
	 */
	public String getTransferredFlag() {
		return transferredFlag;
	}

	/**
	 * @param transferredFlag the transferredFlag to set
	 */
	public void setTransferredFlag(String transferredFlag) {
		this.transferredFlag = transferredFlag;
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
	 * @return the reqId
	 */
	public BigDecimal getReqId() {
		return reqId;
	}

	/**
	 * @param reqId the reqId to set
	 */
	public void setReqId(BigDecimal reqId) {
		this.reqId = reqId;
	}

	/**
	 * @return the rootReqFlag
	 */
	public String getRootReqFlag() {
		return rootReqFlag;
	}

	/**
	 * @param rootReqFlag the rootReqFlag to set
	 */
	public void setRootReqFlag(String rootReqFlag) {
		this.rootReqFlag = rootReqFlag;
	}

	/**
	 * @return the reqNumberPrefix
	 */
	public String getReqNumberPrefix() {
		return reqNumberPrefix;
	}

	/**
	 * @param reqNumberPrefix the reqNumberPrefix to set
	 */
	public void setReqNumberPrefix(String reqNumberPrefix) {
		this.reqNumberPrefix = reqNumberPrefix;
	}

	/**
	 * @return the reqNumber
	 */
	public String getReqNumber() {
		return reqNumber;
	}

	/**
	 * @param reqNumber the reqNumber to set
	 */
	public void setReqNumber(String reqNumber) {
		this.reqNumber = reqNumber;
	}

	/**
	 * @return the resName
	 */
	public String getResName() {
		return resName;
	}

	/**
	 * @param resName the resName to set
	 */
	public void setResName(String resName) {
		this.resName = resName;
	}

	/**
	 * @return the assignmentName
	 */
	public String getAssignmentName() {
		return assignmentName;
	}

	/**
	 * @param assignmentName the assignmentName to set
	 */
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	/**
	 * @return the resProvAgencyAbbrev
	 */
	public String getResProvAgencyAbbrev() {
		return resProvAgencyAbbrev;
	}

	/**
	 * @param resProvAgencyAbbrev the resProvAgencyAbbrev to set
	 */
	public void setResProvAgencyAbbrev(String resProvAgencyAbbrev) {
		this.resProvAgencyAbbrev = resProvAgencyAbbrev;
	}

	/**
	 * @return the filledCatalogItemCode
	 */
	public String getFilledCatalogItemCode() {
		return filledCatalogItemCode;
	}

	/**
	 * @param filledCatalogItemCode the filledCatalogItemCode to set
	 */
	public void setFilledCatalogItemCode(String filledCatalogItemCode) {
		this.filledCatalogItemCode = filledCatalogItemCode;
	}

	/**
	 * @return the filledCatalogItemName
	 */
	public String getFilledCatalogItemName() {
		return filledCatalogItemName;
	}

	/**
	 * @param filledCatalogItemName the filledCatalogItemName to set
	 */
	public void setFilledCatalogItemName(String filledCatalogItemName) {
		this.filledCatalogItemName = filledCatalogItemName;
	}

	/**
	 * @return the employmentClass
	 */
	public String getEmploymentClass() {
		return employmentClass;
	}

	/**
	 * @param employmentClass the employmentClass to set
	 */
	public void setEmploymentClass(String employmentClass) {
		this.employmentClass = employmentClass;
	}

	/**
	 * @return the catalogItemCode
	 */
	public String getCatalogItemCode() {
		return catalogItemCode;
	}

	/**
	 * @param catalogItemCode the catalogItemCode to set
	 */
	public void setCatalogItemCode(String catalogItemCode) {
		this.catalogItemCode = catalogItemCode;
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
	 * @return the qualStatus
	 */
	public String getQualStatus() {
		return qualStatus;
	}

	/**
	 * @param qualStatus the qualStatus to set
	 */
	public void setQualStatus(String qualStatus) {
		this.qualStatus = qualStatus;
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

	/**
	 * @return the jetPort
	 */
	public String getJetPort() {
		return jetPort;
	}

	/**
	 * @param jetPort the jetPort to set
	 */
	public void setJetPort(String jetPort) {
		this.jetPort = jetPort;
	}

	/**
	 * @return the mobEtd
	 */
	public Date getMobEtd() {
		return mobEtd;
	}

	/**
	 * @param mobEtd the mobEtd to set
	 */
	public void setMobEtd(Date mobEtd) {
		this.mobEtd = mobEtd;
	}

	/**
	 * @return the mobEtdTzCode
	 */
	public String getMobEtdTzCode() {
		return mobEtdTzCode;
	}

	/**
	 * @param mobEtdTzCode the mobEtdTzCode to set
	 */
	public void setMobEtdTzCode(String mobEtdTzCode) {
		this.mobEtdTzCode = mobEtdTzCode;
	}

	/**
	 * @return the mobEta
	 */
	public Date getMobEta() {
		return mobEta;
	}

	/**
	 * @param mobEta the mobEta to set
	 */
	public void setMobEta(Date mobEta) {
		this.mobEta = mobEta;
	}

	/**
	 * @return the mobEtaTzCode
	 */
	public String getMobEtaTzCode() {
		return mobEtaTzCode;
	}

	/**
	 * @param mobEtaTzCode the mobEtaTzCode to set
	 */
	public void setMobEtaTzCode(String mobEtaTzCode) {
		this.mobEtaTzCode = mobEtaTzCode;
	}

	/**
	 * @return the mobEte
	 */
	public String getMobEte() {
		return mobEte;
	}

	/**
	 * @param mobEte the mobEte to set
	 */
	public void setMobEte(String mobEte) {
		this.mobEte = mobEte;
	}

	/**
	 * @return the demobEtd
	 */
	public Date getDemobEtd() {
		return demobEtd;
	}

	/**
	 * @param demobEtd the demobEtd to set
	 */
	public void setDemobEtd(Date demobEtd) {
		this.demobEtd = demobEtd;
	}

	/**
	 * @return the demobEtdTzCode
	 */
	public String getDemobEtdTzCode() {
		return demobEtdTzCode;
	}

	/**
	 * @param demobEtdTzCode the demobEtdTzCode to set
	 */
	public void setDemobEtdTzCode(String demobEtdTzCode) {
		this.demobEtdTzCode = demobEtdTzCode;
	}

	/**
	 * @return the demobEte
	 */
	public String getDemobEte() {
		return demobEte;
	}

	/**
	 * @param demobEte the demobEte to set
	 */
	public void setDemobEte(String demobEte) {
		this.demobEte = demobEte;
	}

	/**
	 * @return the vendorOwnedFlag
	 */
	public String getVendorOwnedFlag() {
		return vendorOwnedFlag;
	}

	/**
	 * @param vendorOwnedFlag the vendorOwnedFlag to set
	 */
	public void setVendorOwnedFlag(String vendorOwnedFlag) {
		this.vendorOwnedFlag = vendorOwnedFlag;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @return the contractType
	 */
	public String getContractType() {
		return contractType;
	}

	/**
	 * @param contractType the contractType to set
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * @return the reqCatalogName
	 */
	public String getReqCatalogName() {
		return reqCatalogName;
	}

	/**
	 * @param reqCatalogName the reqCatalogName to set
	 */
	public void setReqCatalogName(String reqCatalogName) {
		this.reqCatalogName = reqCatalogName;
	}

	/**
	 * @return the reqCategoryName
	 */
	public String getReqCategoryName() {
		return reqCategoryName;
	}

	/**
	 * @param reqCategoryName the reqCategoryName to set
	 */
	public void setReqCategoryName(String reqCategoryName) {
		this.reqCategoryName = reqCategoryName;
	}

	/**
	 * @return the rossIncId
	 */
	public String getRossIncId() {
		return rossIncId;
	}

	/**
	 * @param rossIncId the rossIncId to set
	 */
	public void setRossIncId(String rossIncId) {
		this.rossIncId = rossIncId;
	}

	/**
	 * @return the rossAssignment
	 */
	public Boolean getRossAssignment() {
		return rossAssignment;
	}

	/**
	 * @param rossAssignment the rossAssignment to set
	 */
	public void setRossAssignment(Boolean rossAssignment) {
		this.rossAssignment = rossAssignment;
	}

	/**
	 * @return the importStatus
	 */
	public String getImportStatus() {
		return importStatus;
	}

	/**
	 * @param importStatus the importStatus to set
	 */
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

}
