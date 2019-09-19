package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.Font;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentShift;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ_INCIDENT_SHIFT", sequenceName="SEQ_INCIDENT_SHIFT")
@Table(name = "isw_incident_shift")
public class IncidentShiftImpl extends PersistableImpl implements IncidentShift {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_SHIFT")
	private Long id = 0L;
	
	@Column(name = "SHIFT_NAME", nullable = false, length = 50)
	private String shiftName;
		
	@ManyToOne(targetEntity=IncidentImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name="INCIDENT_ID", insertable=true, updatable=true, nullable=false)
	private Incident incident;
	
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long incidentId;	
			
	@Column(name = "START_TIME", length = 4)
	private String startTime;
	
	@Column(name = "END_TIME", length = 4)
	private String endTime;
	
	@ManyToOne(targetEntity=FontImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name="FONT_ID", insertable=true, updatable=true, nullable=true)
	private Font font;
	
	@Column(name = "FONT_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long fontId;	
	
	@OneToMany(targetEntity=CostGroupImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentShift")
	private Collection<CostGroup> costGroups = new ArrayList<CostGroup>();
		
	@OneToMany(targetEntity=CostDataImpl.class,fetch = FetchType.LAZY,cascade=CascadeType.ALL, mappedBy="defaultIncidentShift")
	private Collection<CostData> costDatas;
	
	public IncidentShiftImpl() {
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
	 * @return the shiftName
	 */
	public String getShiftName() {
		return shiftName;
	}
	
	/**
	 * @param shiftName the shiftName to set
	 */
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	
	/**
	 * @return the incident
	 */
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
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

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * @param endTime the endTime to set
	 */
	public String getEndTime() {
		return endTime;
	}
		
	/**
	 * @return the endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}
	
	/**
	 * @return the fontId
	 */
	public Long getFontId() {
		return fontId;
	}
	
	/**
	 * @param fontId the fontId to set
	 */
	public void setFontId(Long fontId) {
		this.fontId = fontId;
	}
	
	/**
	 * @param costGroups the costGroups to set
	 */
	public void setCostGroups(Collection<CostGroup> costGroups) {
		this.costGroups = costGroups;
	}

	/**
	 * @return the costGroups
	 */
	public Collection<CostGroup> getCostGroups() {
		return costGroups;
	}

	/**
	 * @return the costDatas
	 */
	public Collection<CostData> getCostDatas() {
		return costDatas;
	}

	/**
	 * @param costDatas the costDatas to set
	 */
	public void setCostDatas(Collection<CostData> costDatas) {
		this.costDatas = costDatas;
	}
}