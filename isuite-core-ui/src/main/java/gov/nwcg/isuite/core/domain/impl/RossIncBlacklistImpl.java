package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.RossIncBlacklist;
import gov.nwcg.isuite.core.domain.RossIncDataBlacklist;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ_ROSS_INC_BLACKLIST", sequenceName="SEQ_ROSS_INC_BLACKLIST")
@Table(name = "isw_ross_inc_blacklist")
public class RossIncBlacklistImpl extends PersistableImpl implements RossIncBlacklist {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ROSS_INC_BLACKLIST")
	private Long id = 0L;

	@Column(name = "ROSS_INC_ID", nullable = false)
	private Long rossIncId;
	
	@OneToMany(targetEntity=RossIncDataBlacklistImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rossIncBlacklist")
	private Collection<RossIncDataBlacklist> rossIncDataBlacklists = new ArrayList<RossIncDataBlacklist>();
	
	public RossIncBlacklistImpl(){

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
	 * @return the rossIncId
	 */
	public Long getRossIncId() {
		return rossIncId;
	}


	/**
	 * @param rossIncId the rossIncId to set
	 */
	public void setRossIncId(Long rossIncId) {
		this.rossIncId = rossIncId;
	}


	/**
	 * @return the rossIncDataBlacklists
	 */
	public Collection<RossIncDataBlacklist> getRossIncDataBlacklists() {
		return rossIncDataBlacklists;
	}


	/**
	 * @param rossIncDataBlacklists the rossIncDataBlacklists to set
	 */
	public void setRossIncDataBlacklists(Collection<RossIncDataBlacklist> rossIncDataBlacklists) {
		this.rossIncDataBlacklists = rossIncDataBlacklists;
	}


}
