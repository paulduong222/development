package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapPlanPrintOrder;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl; 

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * IapPlanPrintOrder entity.
 */
@Entity
@Table(name = "isw_iap_plan_print_order")
@SequenceGenerator(name="SEQ_IAP_PLAN_PRINT_ORDER", sequenceName="SEQ_IAP_PLAN_PRINT_ORDER")

public class IapPlanPrintOrderImpl extends PersistableImpl implements IapPlanPrintOrder{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_PLAN_PRINT_ORDER")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapPlanImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_PLAN_ID", nullable = false)
	private IapPlan iapPlan;
	
	@Column(name = "IAP_PLAN_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapPlanId;

	@Column(name = "FORM_TYPE", length = 20)
	private String formType;
	
	@Column(name = "FORM_ID")
	private Long formId;

	@Column(name = "POSITION")
	private Integer position;
	
	/** 
	 * Default constructor 
	 */
	public IapPlanPrintOrderImpl() {
		super();
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

 
	/**
	 * @return the FormType
	 */
	public String getFormType() {
		return formType;
	}
	/**
	 * @param formType the formType to set
	 */
	public void setFormType(String formType){
		this.formType = formType;
	}
	
	/**
	 * @return the formId
	 */
	public Long getFormId(){
		return formId;
	}
	/**
	 * @param formId the formId to set
	 */
	public void setFormId(Long formId){
		this.formId = formId;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position){
		this.position = position;
	}
	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}
	/**
	 * @return the iapPlan
	 */
	public IapPlan getIapPlan() {
		return iapPlan;
	}

	/**
	 * @param iapPlan the iapPlan to set
	 */
	public void setIapPlan(IapPlan iapPlan) {
		this.iapPlan = iapPlan;
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

}

