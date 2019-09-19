package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.domain.impl.CostAccrualExtractImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.PhoneNumberUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CostAccrualExtractVo extends AbstractVo implements PersistableVo {
	private Date extractDate;
	private DateTransferVo extractDateVo;
	private Boolean finalized;
	private String preparedBy;
	private String preparedPhone;
	private Long incidentId;
	private Long incidentGroupId;
	private BigDecimal totalAmount;
	private BigDecimal changeAmount;
	private DateTransferVo finalizedDate;
	
	//private Collection<CostAccrualExtractResourceVo> costAccrualExtractResourceVos = new ArrayList<CostAccrualExtractResourceVo>();

	public CostAccrualExtractVo() {
	}

	public static CostAccrualExtractVo getInstance(CostAccrualExtract entity,boolean cascadable) throws Exception {
		CostAccrualExtractVo vo = new CostAccrualExtractVo();

		if(null == entity)
			throw new Exception("Unable to create CostAccrualExtractVo from null CostAccrualExtract entity.");

		vo.setId(entity.getId());
		
		
		if(cascadable){
			vo.setExtractDate(entity.getExtractDate());
			vo.setExtractDateVo(new DateTransferVo());
			if(DateUtil.hasValue(entity.getExtractDate()))
				DateTransferVo.populateDate(vo.getExtractDateVo(), entity.getExtractDate());
			
			vo.setFinalized(StringBooleanEnum.toBooleanValue(entity.isFinalized()));
			vo.setPreparedBy(entity.getPreparedBy());

			if(StringUtility.hasValue(entity.getPreparedPhone()))
				vo.setPreparedPhone(PhoneNumberUtil.formatNumber(entity.getPreparedPhone()));
			else
				vo.setPreparedPhone("");
			
			if(DateUtil.hasValue(entity.getFinalizedDate())){
				vo.setFinalizedDate(DateTransferVo.getInstance(entity.getFinalizedDate()));
			}

			vo.setIncidentId(entity.getIncidentId());
			vo.setIncidentGroupId(entity.getIncidentGroupId());
			
			BigDecimal totalAmt=new BigDecimal(0.0);
			BigDecimal changeAmt=new BigDecimal(0.0);
			
			vo.setTotalAmount(totalAmt);
			vo.setChangeAmount(changeAmt);
		}
		
		return vo;
	}

	public static Collection<CostAccrualExtractVo> getInstances(Collection<CostAccrualExtract> entities, boolean cascadable) throws Exception {
		Collection<CostAccrualExtractVo> vos = new ArrayList<CostAccrualExtractVo>();

		for(CostAccrualExtract entity : entities){
			vos.add(CostAccrualExtractVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}

	/**
	 * Returns a CostAccrualExtract entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of CostAccrualExtract entity
	 * @throws Exception
	 */
	public static CostAccrualExtract toEntity(CostAccrualExtract entity, CostAccrualExtractVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity){
			entity=new CostAccrualExtractImpl();
			entity.setId(vo.getId());
			entity.setIsExported(StringBooleanEnum.N);
		}
		
		
		if(cascadable){

		}

		return entity;
	}

	/**
	 * @return the extractDate
	 */
	public Date getExtractDate() {
		return extractDate;
	}

	/**
	 * @param extractDate the extractDate to set
	 */
	public void setExtractDate(Date extractDate) {
		this.extractDate = extractDate;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedPhone
	 */
	public String getPreparedPhone() {
		return preparedPhone;
	}

	/**
	 * @param preparedPhone the preparedPhone to set
	 */
	public void setPreparedPhone(String preparedPhone) {
		this.preparedPhone = preparedPhone;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the changeAmount
	 */
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	/**
	 * @param changeAmount the changeAmount to set
	 */
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}

	/**
	 * @return the finalized
	 */
	public Boolean getFinalized() {
		return finalized;
	}

	/**
	 * @param isFinalized the isFinalized to set
	 */
	public void setFinalized(Boolean isFinalized) {
		this.finalized = isFinalized;
	}

	/**
	 * @return the finalizedDate
	 */
	public DateTransferVo getFinalizedDate() {
		return finalizedDate;
	}

	/**
	 * @param finalizedDate the finalizedDate to set
	 */
	public void setFinalizedDate(DateTransferVo finalizedDate) {
		this.finalizedDate = finalizedDate;
	}

	/**
	 * @return the extractDateVo
	 */
	public DateTransferVo getExtractDateVo() {
		return extractDateVo;
	}

	/**
	 * @param extractDateVo the extractDateVo to set
	 */
	public void setExtractDateVo(DateTransferVo extractDateVo) {
		this.extractDateVo = extractDateVo;
	}

}
