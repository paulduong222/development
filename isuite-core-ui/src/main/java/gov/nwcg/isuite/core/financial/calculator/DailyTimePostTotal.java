package gov.nwcg.isuite.core.financial.calculator;

import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import java.math.BigDecimal;

public class DailyTimePostTotal {

	private BigDecimal totalPrimaryAmount;
	private BigDecimal totalSpecialAmount;
	private BigDecimal guaranteeAmount;
	private Long rateClassId;


	public static BigDecimal getTotalPrimaryAmount(AssignmentTimePostVo atp) {
		BigDecimal tpa = null;
		if(atp.getContractorPostType().equals("SPECIAL")){
			
			// special posting, no primary value
			tpa = new BigDecimal(0.0);
			
		} else if(atp.getContractorPostType().equals("BOTH")){

			// both primary and special
			tpa = atp.getQuantity().multiply(atp.getRateAmount());
			
		} else if(atp.getContractorPostType().equals("PRIMARY")){
			
			// primary only
			if(BooleanUtility.isTrue(atp.getIsHalfRate()))
				tpa = atp.getQuantity().multiply((new BigDecimal(atp.getRateAmount().doubleValue() / 2)));
			else
				tpa = atp.getQuantity().multiply(atp.getRateAmount());
		} else if(atp.getContractorPostType().equals("GUARANTEE")){
			if(BooleanUtility.isTrue(atp.getIsHalfRate()))
				tpa = atp.getQuantity().multiply((new BigDecimal(atp.getRateAmount().doubleValue() / 2)));
			else
				tpa = atp.getQuantity().multiply(atp.getRateAmount());
		} else {
			tpa = new BigDecimal(0.0);
		}
		/*
		if(atp.getPrimaryPosting() && atp.getSpecialPosting()) {
			// special posting, no primary value
			tpa = new BigDecimal(0.0);
		} else if(atp.getPrimaryPosting() && !atp.getSpecialPosting() 
				&& atp.getSpecialRateAssignmentTimePostVo() != null) {
			// both primary and special
			tpa = atp.getQuantity().multiply(atp.getRateAmount());
		} else if(atp.getPrimaryPosting() && !atp.getGuaranteePosting()) {
			// primary only
			if(BooleanUtility.isTrue(atp.getIsHalfRate()))
				tpa = atp.getQuantity().multiply((new BigDecimal(atp.getRateAmount().doubleValue() / 2)));
			else
				tpa = atp.getQuantity().multiply(atp.getRateAmount());
		} else {
			tpa = new BigDecimal(0.0);
		}
		*/
		return tpa;
	}



	public BigDecimal getTotalPrimaryAmount() {
		return totalPrimaryAmount;
	}

	public void setTotalPrimaryAmount(BigDecimal totalPrimaryAmount) {
		this.totalPrimaryAmount = totalPrimaryAmount;
	}

	public BigDecimal getTotalSpecialAmount() {
		return totalSpecialAmount;
	}

	public void setTotalSpecialAmount(BigDecimal totalSpecialAmount) {
		this.totalSpecialAmount = totalSpecialAmount;
	}

	public BigDecimal getGuaranteeAmount() {
		return guaranteeAmount;
	}

	public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	public Long getRateClassId() {
		return rateClassId;
	}

	public void setRateClassId(Long rateClassId) {
		this.rateClassId = rateClassId;
	}



}
