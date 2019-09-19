package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapBranchCommSummary;
import gov.nwcg.isuite.core.domain.impl.IapBranchCommSummaryImpl;
import gov.nwcg.isuite.core.domain.impl.IapBranchImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IapBranchCommSummaryVo extends AbstractVo implements PersistableVo {
	private Long iapBranchId;
	private String sfunction;
	private String rx;
	private String tx;
	private String tone;
	private String rxTone;
	private String txTone;
	private String mode;
	private String system1;
	private String channel1;
	private String system2;
	private String channel2;
	private String primaryContact;
	private String cellNbr;
	private String pagerNbr;
	private Long masterFreqId;
	private Boolean isBlankLine=false;
	private Integer positionNum;
	
	/**
	 * Constructor
	 */
	public IapBranchCommSummaryVo() {
	}
	
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapBranchCommSummaryVo getInstance(IapBranchCommSummary entity, boolean cascadable) throws Exception {
		IapBranchCommSummaryVo vo = new IapBranchCommSummaryVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapBranchCommSummaryVo from null IapBranchCommSummary entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIapBranchId(entity.getIapBranchId());
			vo.setSfunction(entity.getFunction());
			vo.setRx(entity.getRx());
			vo.setTx(entity.getTx());
			vo.setTone(entity.getTone());
			vo.setSystem1(entity.getSystem1());
			vo.setChannel1(entity.getChannel1());
			vo.setSystem2(entity.getSystem2());
			vo.setChannel2(entity.getChannel2());
			vo.setPrimaryContact(entity.getPrimaryContact());
			vo.setCellNbr(entity.getCellNbr());
			vo.setPagerNbr(entity.getPagerNbr());
			vo.setRxTone(entity.getRxTone());
			vo.setTxTone(entity.getTxTone());
			vo.setMode(entity.getMode());
			vo.setMasterFreqId(entity.getMasterFreqId());
			
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
	public static Collection<IapBranchCommSummaryVo> getInstances(Collection<IapBranchCommSummary> entities, boolean cascadable) throws Exception {
		Collection<IapBranchCommSummaryVo> vos = new ArrayList<IapBranchCommSummaryVo>();
		
		for(IapBranchCommSummary entity : entities) {
			vos.add(IapBranchCommSummaryVo.getInstance(entity, cascadable));
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
	public static IapBranchCommSummary toEntity(IapBranchCommSummary entity, IapBranchCommSummaryVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapBranchCommSummaryImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setFunction(StringUtility.toUpper(vo.getSfunction()));
			entity.setRx(StringUtility.toUpper(vo.getRx()));
			entity.setTx(StringUtility.toUpper(vo.getTx()));
			entity.setTone(StringUtility.toUpper(vo.getTone()));
			entity.setRxTone(StringUtility.toUpper(vo.getRxTone()));
			entity.setTxTone(StringUtility.toUpper(vo.getTxTone()));
			entity.setMode(StringUtility.toUpper(vo.getMode()));
			entity.setSystem1(StringUtility.toUpper(vo.getSystem1()));
			entity.setChannel1(StringUtility.toUpper(vo.getChannel1()));
			entity.setSystem2(StringUtility.toUpper(vo.getSystem2()));
			entity.setChannel2(StringUtility.toUpper(vo.getChannel2()));
			entity.setPrimaryContact(vo.getPrimaryContact());
			entity.setCellNbr(vo.getCellNbr());
			entity.setPagerNbr(vo.getPagerNbr());
			entity.setMasterFreqId(vo.getMasterFreqId());
			
			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			entity.setIsBlankLine(StringBooleanEnum.toEnumValue(vo.getIsBlankLine()));
			
			IapBranch branchEntity = (IapBranch)(AbstractVo.getPersistableObject(persistables, IapBranchImpl.class));
			if(null != branchEntity){
				entity.setIapBranch(branchEntity);
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
	public static Collection<IapBranchCommSummary> toEntityList(Collection<IapBranchCommSummaryVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapBranchCommSummary> entities = new ArrayList<IapBranchCommSummary>();
		
		for(IapBranchCommSummaryVo vo : vos) {
			entities.add(IapBranchCommSummaryVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}


	/**
	 * @param function the function to set
	 */
	public void setSfunction(String function) {
		this.sfunction = function;
	}


	/**
	 * @return the function
	 */
	public String getSfunction() {
		return sfunction;
	}


	/**
	 * @param rx the rx to set
	 */
	public void setRx(String rx) {
		this.rx = rx;
	}


	/**
	 * @return the rx
	 */
	public String getRx() {
		return rx;
	}


	/**
	 * @param tx the tx to set
	 */
	public void setTx(String tx) {
		this.tx = tx;
	}


	/**
	 * @return the tx
	 */
	public String getTx() {
		return tx;
	}


	/**
	 * @param tone the tone to set
	 */
	public void setTone(String tone) {
		this.tone = tone;
	}


	/**
	 * @return the tone
	 */
	public String getTone() {
		return tone;
	}


	/**
	 * @param system1 the system1 to set
	 */
	public void setSystem1(String system1) {
		this.system1 = system1;
	}


	/**
	 * @return the system1
	 */
	public String getSystem1() {
		return system1;
	}


	/**
	 * @param channel1 the channel1 to set
	 */
	public void setChannel1(String channel1) {
		this.channel1 = channel1;
	}


	/**
	 * @return the channel1
	 */
	public String getChannel1() {
		return channel1;
	}


	/**
	 * @param system2 the system2 to set
	 */
	public void setSystem2(String system2) {
		this.system2 = system2;
	}


	/**
	 * @return the system2
	 */
	public String getSystem2() {
		return system2;
	}


	/**
	 * @param channel2 the channel2 to set
	 */
	public void setChannel2(String channel2) {
		this.channel2 = channel2;
	}


	/**
	 * @return the channel2
	 */
	public String getChannel2() {
		return channel2;
	}


	/**
	 * @param primaryContact the primaryContact to set
	 */
	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}


	/**
	 * @return the primaryContact
	 */
	public String getPrimaryContact() {
		return primaryContact;
	}


	/**
	 * @param cellNbr the cellNbr to set
	 */
	public void setCellNbr(String cellNbr) {
		this.cellNbr = cellNbr;
	}


	/**
	 * @return the cellNbr
	 */
	public String getCellNbr() {
		return cellNbr;
	}


	/**
	 * @param pagerNbr the pagerNbr to set
	 */
	public void setPagerNbr(String pagerNbr) {
		this.pagerNbr = pagerNbr;
	}


	/**
	 * @return the pagerNbr
	 */
	public String getPagerNbr() {
		return pagerNbr;
	}


	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId() {
		return iapBranchId;
	}


	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId) {
		this.iapBranchId = iapBranchId;
	}


	/**
	 * @return the rxTone
	 */
	public String getRxTone() {
		return rxTone;
	}


	/**
	 * @param rxTone the rxTone to set
	 */
	public void setRxTone(String rxTone) {
		this.rxTone = rxTone;
	}


	/**
	 * @return the txTone
	 */
	public String getTxTone() {
		return txTone;
	}


	/**
	 * @param txTone the txTone to set
	 */
	public void setTxTone(String txTone) {
		this.txTone = txTone;
	}


	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}


	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}


	/**
	 * @return the masterFreqId
	 */
	public Long getMasterFreqId() {
		return masterFreqId;
	}


	/**
	 * @param masterFreqId the masterFreqId to set
	 */
	public void setMasterFreqId(Long masterFreqId) {
		this.masterFreqId = masterFreqId;
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
