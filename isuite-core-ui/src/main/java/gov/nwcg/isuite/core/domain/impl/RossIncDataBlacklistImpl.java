package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.RossIncBlacklist;
import gov.nwcg.isuite.core.domain.RossIncDataBlacklist;
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

@Entity
@SequenceGenerator(name="SEQ_ISW_ROSS_INC_DATA_BL", sequenceName="SEQ_ISW_ROSS_INC_DATA_BL")
@Table(name = "isw_ross_inc_data_blacklist")
public class RossIncDataBlacklistImpl extends PersistableImpl implements RossIncDataBlacklist {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ISW_ROSS_INC_DATA_BL")
	private Long id=0L;

	@ManyToOne(targetEntity=RossIncBlacklistImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "ROSS_INC_BLACKLIST_ID")
	private RossIncBlacklist rossIncBlacklist;
	
	@Column(name = "RES_ID", nullable = false)
	private Long resId;

	@Column(name = "ROSS_RES_REQ_ID", nullable = true)
	private Long rossResReqId;
	
	@Column(name = "ROSS_INC_ID", length=20, nullable = true)
	private String rossIncId;

	@Column(name = "IMPORT_STATUS", length = 30)
	private String importStatus;
	
	public RossIncDataBlacklistImpl(){
		
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
	 * @return the rossIncBlacklist
	 */
	public RossIncBlacklist getRossIncBlacklist() {
		return rossIncBlacklist;
	}

	/**
	 * @param rossIncBlacklist the rossIncBlacklist to set
	 */
	public void setRossIncBlacklist(RossIncBlacklist rossIncBlacklist) {
		this.rossIncBlacklist = rossIncBlacklist;
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

	/**
	 * @return the rossResReqId
	 */
	public Long getRossResReqId() {
		return rossResReqId;
	}

	/**
	 * @param rossResReqId the rossResReqId to set
	 */
	public void setRossResReqId(Long rossResReqId) {
		this.rossResReqId = rossResReqId;
	}
	
	
}
