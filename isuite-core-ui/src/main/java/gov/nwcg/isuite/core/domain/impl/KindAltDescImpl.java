package gov.nwcg.isuite.core.domain.impl;

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

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.KindAltDesc;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_KIND_ALT_DESC", sequenceName="SEQ_KIND_ALT_DESC")
@Table(name="iswl_kind_alt_desc")
public class KindAltDescImpl extends PersistableImpl implements KindAltDesc {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_KIND_ALT_DESC")
	private Long id = 0L;

	@ManyToOne(targetEntity=KindImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "KIND_ID", insertable=true, updatable = true, nullable = false)
	private Kind kind;

	@Column(name = "KIND_ID", insertable=false, updatable = false, nullable = false)
	private Long kindId;
	
	@Column(name = "DESCRIPTION", nullable = false, length = 75)
	private String description;
	

	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/**
	 * @return the kind
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

}
