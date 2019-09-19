package gov.nwcg.isuite.core.domain.impl;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Font;
import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "iswl_font")
@SequenceGenerator(name="SEQ_FONT", sequenceName="SEQ_FONT")
public class FontImpl extends PersistableImpl implements Font {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_FONT")
	private Long id = 0L;
	
	
	@Column(name = "ENABLED", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum enabled;
	
	@Column(name = "FONT_NAME", length = 50)
	private String fontName;
	
	@Column(name = "FONT_SIZE")
	private Short fontSize;
	
	@Column(name = "FONT_WEIGHT")
	private Short fontWeight;
	
	@Column(name = "FONT_STYLE")
	private Short fontStyle;
	
	@OneToMany(targetEntity=IncidentShiftImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "font")
	private Collection<IncidentShift> incidentShifts = new ArrayList<IncidentShift>();
		
	/**
	 * Default constructor.
	 */
	public FontImpl() {
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
	 * @return enabled
	 */
	public StringBooleanEnum getEnabled() {
		return enabled;
	}

	/**
	 * @return fontName
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * @return the fontSize
	 */
	public Short getFontSize() {
		return fontSize;
	}

	/**
	 * @return the fontWeight
	 */
	public Short getFontWeight() {
		return fontWeight;
	}

	/**
	 * @return the fontStyle
	 */
	public Short getFontStyle() {
		return fontStyle;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(StringBooleanEnum enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param fontName the fontName to set
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(Short fontSize) {
		this.fontSize = fontSize;
	}
	
	/**
	 * @param fontWeight the fontWeight to set
	 */
	public void setFontWeight(Short fontWeight) {
		this.fontWeight = fontWeight;
	}
	
	/**
	 * @param fontStyle the fontStyle to set
	 */
	public void setFontStyle(Short fontStyle) {
		this.fontStyle = fontStyle;
	}

	/**
	 * @param incidentShifts the incidentShifts to set
	 */
	public void setIncidentShifts(Collection<IncidentShift> incidentShifts) {
		this.incidentShifts = incidentShifts;
	}

	/**
	 * @return the incidentShifts
	 */
	public Collection<IncidentShift> getIncidentShifts() {
		return incidentShifts;
	}
}