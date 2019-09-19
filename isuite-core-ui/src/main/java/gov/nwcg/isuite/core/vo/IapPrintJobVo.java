package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;

public class IapPrintJobVo extends AbstractVo {
	private Long iapPlanId;
	private Collection<IapPrintFormVo> formsToPrint = new ArrayList<IapPrintFormVo>();
	private Collection<IapPlanPrintOrderVo>  ordersToPrint = new ArrayList<IapPlanPrintOrderVo>();
	private Boolean lockPlan;
	
	public IapPrintJobVo(){
		
	}

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId() {
		return iapPlanId;
	}

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId) {
		this.iapPlanId = iapPlanId;
	}

	/**
	 * @return the ordersToPrint
	 */
	public Collection<IapPlanPrintOrderVo> getOrdersToPrint() {
		return ordersToPrint;
	}

	/**
	 * @param ordersToPrint the ordersToPrint to set
	 */
	public void setOrdersToPrint(Collection<IapPlanPrintOrderVo> ordersToPrint) {
		this.ordersToPrint = ordersToPrint;
	}
	
	/**
	 * @return the formsToPrint
	 */
	public Collection<IapPrintFormVo> getFormsToPrint() {
		return formsToPrint;
	}

	/**
	 * @param formsToPrint the formsToPrint to set
	 */
	public void setFormsToPrint(Collection<IapPrintFormVo> formsToPrint) {
		this.formsToPrint = formsToPrint;
	}

	/**
	 * @return the lockPlan
	 */
	public Boolean getLockPlan() {
		return lockPlan;
	}

	/**
	 * @param lockPlan the lockPlan to set
	 */
	public void setLockPlan(Boolean lockPlan) {
		this.lockPlan = lockPlan;
	}

}
