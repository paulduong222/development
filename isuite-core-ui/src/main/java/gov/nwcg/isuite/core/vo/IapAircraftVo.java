package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapAircraft;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.impl.IapAircraftImpl;
import gov.nwcg.isuite.core.domain.impl.IapForm220Impl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IapAircraftVo extends AbstractVo implements PersistableVo {
	private Long iapForm220Id;
	private String wingType;
	private String aircraft;
	private Integer nbrAvailable;
	private String type;
	private String makeModel;
	private String faaNbr;
	private String base;
	private String baseFax;
	private String available;
	private DateTransferVo availableDateVo;
	private String startTime;
	private String remarks;
	private Boolean isBlankLine=false;
	private Integer positionNum;
	
	/**
	 * Constructor
	 */
	public IapAircraftVo() {
	}
	
	public static Collection<IapAircraftVo> buildDefaultFixedWingVos(IapForm220Vo vo) throws Exception {
		Collection<IapAircraftVo> fixedWingVos = new ArrayList<IapAircraftVo>();
		for(int i=1;i<13;i++){
			IapAircraftVo vo1=IapAircraftVo.buildEmptyVo("", "FIXED",vo);
			vo1.setPositionNum(Integer.valueOf(i));
			fixedWingVos.add(vo1);
		}
		
		return fixedWingVos;
	}
	
	public static IapAircraftVo buildEmptyVo(String name, String wingtype, IapForm220Vo form220Vo) throws Exception {
		IapAircraftVo vo = new IapAircraftVo();
		
		vo.setAircraft(name);
		vo.setWingType(wingtype);
		vo.setIsBlankLine(false);
		
		if(form220Vo != null){
			vo.setIapForm220Id(form220Vo.getId());
		}
		
		return vo;
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapAircraftVo getInstance(IapAircraft entity, boolean cascadable) throws Exception {
		IapAircraftVo vo = new IapAircraftVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapAircraftVo from null IapAircraft entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIapForm220Id(entity.getIapForm220Id());
			if ( null != entity.getIapForm220()) {
				vo.setIapForm220Id(entity.getIapForm220().getId());
			}
			vo.setWingType(entity.getWingType());
			vo.setAircraft(entity.getAircraft());
			vo.setNbrAvailable(entity.getNbrAvailable());
			vo.setType(entity.getType());
			vo.setMakeModel(entity.getMakeModel());
			vo.setFaaNbr(entity.getFaaNbr());
			vo.setBase(entity.getBase());
			vo.setBaseFax(entity.getBaseFax());
			vo.setAvailable(entity.getAvailable());
			
			vo.setAvailableDateVo(new DateTransferVo());
			if(DateUtil.hasValue(entity.getAvailableDate()))
				DateTransferVo.populateDate(vo.getAvailableDateVo(), entity.getAvailableDate());
			
			vo.setStartTime(entity.getStartTime());
			vo.setRemarks(entity.getRemarks());
			
			if(IntegerUtility.hasValue(entity.getPositionNum()))
				vo.setPositionNum(entity.getPositionNum());
			else
				vo.setPositionNum(new Integer(0));

			vo.setIsBlankLine(entity.getIsBlankLine().getValue());
			
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapAircraftVo> getInstances(Collection<IapAircraft> entities, boolean cascadable) throws Exception {
		Collection<IapAircraftVo> vos = new ArrayList<IapAircraftVo>();
		
		for(IapAircraft entity : entities) {
			vos.add(IapAircraftVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static IapAircraft toEntity(IapAircraft entity, IapAircraftVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapAircraftImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setWingType(vo.getWingType());
			entity.setAircraft(StringUtility.toUpper(vo.getAircraft()));
			entity.setNbrAvailable(vo.getNbrAvailable());
			entity.setType(StringUtility.toUpper(vo.getType()));
			entity.setMakeModel(StringUtility.toUpper(vo.getMakeModel()));
			entity.setFaaNbr(StringUtility.toUpper(vo.getFaaNbr()));
			entity.setBase(StringUtility.toUpper(vo.getBase()));
			entity.setBaseFax(StringUtility.toUpper(vo.getBaseFax()));
			entity.setAvailable(StringUtility.toUpper(vo.getAvailable()));
			entity.setStartTime(vo.getStartTime());
			entity.setRemarks(vo.getRemarks());

			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			entity.setIsBlankLine(StringBooleanEnum.toEnumValue(vo.getIsBlankLine()));
			
			if(DateTransferVo.hasDateString(vo.getAvailableDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getAvailableDateVo());
				entity.setAvailableDate(dt);
			}

			
			IapForm220 iapForm220 = (IapForm220)AbstractVo.getPersistableObject(persistables, IapForm220Impl.class);
			if(null != iapForm220){
				entity.setIapForm220(iapForm220);
			}
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapAircraft> toEntityList(Collection<IapAircraftVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapAircraft> entities = new ArrayList<IapAircraft>();
		
		for(IapAircraftVo vo : vos) {
			entities.add(IapAircraftVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	
	/**
	 * @param wingType the wingType to set
	 */
	public void setWingType(String wingType) {
		this.wingType = wingType;
	}

	/**
	 * @return the wingType
	 */
	public String getWingType() {
		return wingType;
	}

	/**
	 * @param aircraft the aircraft to set
	 */
	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}

	/**
	 * @return the aircraft
	 */
	public String getAircraft() {
		return aircraft;
	}

	/**
	 * @param nbrAvailable the nbrAvailable to set
	 */
	public void setNbrAvailable(Integer nbrAvailable) {
		this.nbrAvailable = nbrAvailable;
	}

	/**
	 * @return the nbrAvailable
	 */
	public Integer getNbrAvailable() {
		return nbrAvailable;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param makeModel the makeModel to set
	 */
	public void setMakeModel(String makeModel) {
		this.makeModel = makeModel;
	}

	/**
	 * @return the makeModel
	 */
	public String getMakeModel() {
		return makeModel;
	}

	/**
	 * @param faaNbr the faaNbr to set
	 */
	public void setFaaNbr(String faaNbr) {
		this.faaNbr = faaNbr;
	}

	/**
	 * @return the faaNbr
	 */
	public String getFaaNbr() {
		return faaNbr;
	}

	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}

	/**
	 * @param baseFax the baseFax to set
	 */
	public void setBaseFax(String baseFax) {
		this.baseFax = baseFax;
	}

	/**
	 * @return the baseFax
	 */
	public String getBaseFax() {
		return baseFax;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(String available) {
		this.available = available;
	}

	/**
	 * @return the available
	 */
	public String getAvailable() {
		return available;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the iapForm220Id
	 */
	public Long getIapForm220Id() {
		return iapForm220Id;
	}

	/**
	 * @param iapForm220Id the iapForm220Id to set
	 */
	public void setIapForm220Id(Long iapForm220Id) {
		this.iapForm220Id = iapForm220Id;
	}

	/**
	 * @return the availableDateVo
	 */
	public DateTransferVo getAvailableDateVo() {
		return availableDateVo;
	}

	/**
	 * @param availableDateVo the availableDateVo to set
	 */
	public void setAvailableDateVo(DateTransferVo availableDateVo) {
		this.availableDateVo = availableDateVo;
	}

	/**
	 * @return the isBlankLine
	 */
	public Boolean getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(Boolean isBlankLine) {
		this.isBlankLine = isBlankLine;
	}

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}
	

}
