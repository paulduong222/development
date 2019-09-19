package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapAttachment;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * IapAttachment entity.
 */
@Entity
@Table(name = "isw_iap_attachment")
@SequenceGenerator(name="SEQ_IAP_ATTACHMENT", sequenceName="SEQ_IAP_ATTACHMENT")
public class IapAttachmentImpl extends PersistableImpl implements IapAttachment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_ATTACHMENT")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapPlanImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_PLAN_ID", nullable = false)
	private IapPlan iapPlan;
	
	@Column(name = "IAP_PLAN_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapPlanId;

	@Column(name = "ATTACHMENT_NAME", length = 50)
	private String attachmentName;
	
	@Column(name = "FILENAME", length = 200)
	private String filename;

	@Column(name = "ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attached;
	
	/** 
	 * Default constructor 
	 */
	public IapAttachmentImpl() {
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
	 * @param attachmentName the attachmentName to set
	 */
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	/**
	 * @return the attachmentName
	 */
	public String getAttachmentName() {
		return attachmentName;
	}

	/**
	 * @param attached the attached to set
	 */
	public void setAttached(StringBooleanEnum attached) {
		this.attached = attached;
	}

	/**
	 * @return the attached
	 */
	public StringBooleanEnum getAttached() {
		return attached;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
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
