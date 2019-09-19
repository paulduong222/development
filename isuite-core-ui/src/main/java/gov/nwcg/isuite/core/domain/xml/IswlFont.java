package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlFont", table = "iswl_font")
public class IswlFont {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_FONT", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "Enabled", sqlname = "ENABLED", type="BOOLEAN")
	private String enabled;

	@XmlTransferField(name = "FontName", sqlname = "FONT_NAME", type="STRING")
	private String fontName;

	@XmlTransferField(name = "FontSize", sqlname = "FONT_SIZE", type="SHORT")
	private Short fontSize;

	@XmlTransferField(name = "FontWeight", sqlname = "FONT_WEIGHT", type="SHORT")
	private Short fontWeight;

	@XmlTransferField(name = "FONT_STYLE", type="SHORT")
	private Short fontStyle;

	/**
	 * Default constructor.
	 */
	public IswlFont() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return enabled
	 */
	public String getEnabled() {
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
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param fontName
	 *            the fontName to set
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(Short fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @param fontWeight
	 *            the fontWeight to set
	 */
	public void setFontWeight(Short fontWeight) {
		this.fontWeight = fontWeight;
	}

	/**
	 * @param fontStyle
	 *            the fontStyle to set
	 */
	public void setFontStyle(Short fontStyle) {
		this.fontStyle = fontStyle;
	}

}
