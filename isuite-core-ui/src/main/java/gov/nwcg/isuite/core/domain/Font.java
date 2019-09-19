package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface Font extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id);
		
	/**
	 * @return fontName
	 */
	public String getFontName();
	
	/**
	 * @param fontName the fontName to set
	 */
	public void setFontName(String fontName);
	
	/**
	 * @return fontSize
	 */
	public Short getFontSize();
	
	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(Short fontSize);
	
	/**
	 * @return fontWeight
	 */
	public Short getFontWeight();
	
	/**
	 * @param fontWeight the fontWeight to set
	 */
	public void setFontWeight(Short fontWeight);
	
	/**
	 * @return fontStyle
	 */
	public Short getFontStyle();
	
	/**
	 * @param fontStyle the fontStyle to set
	 */
	public void setFontStyle(Short fontStyle);
	
	/**
	 * @return enabled
	 */
	public StringBooleanEnum getEnabled();
	
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(StringBooleanEnum enabled);
	
	/**
	 * @param incidentShifts the incidentShifts to set
	 */
	public void setIncidentShifts(Collection<IncidentShift> incidentShifts);

	/**
	 * @return the incidentShifts
	 */
	public Collection<IncidentShift> getIncidentShifts();
	
}