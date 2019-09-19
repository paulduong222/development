package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Font;
import gov.nwcg.isuite.core.domain.impl.FontImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public class FontVo extends AbstractVo implements PersistableVo {
	private Boolean enabled;
	private String fontName;
	private Short fontSize;
	private Short fontWeight;
	private Short fontStyle;
		
	/**
	 * Constructor
	 */
	public FontVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static FontVo getInstance(Font entity, boolean cascadable) throws Exception {
		FontVo vo = new FontVo();
		
		if(null == entity)
			throw new Exception("Unable to create FontVo from null Font entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
						
			vo.setEnabled(entity.getEnabled().getValue());
			vo.setFontName(entity.getFontName());
			vo.setFontSize(entity.getFontSize());
			vo.setFontWeight(entity.getFontWeight());
			vo.setFontStyle(entity.getFontStyle());
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<FontVo> getInstances(Collection<Font> entities, boolean cascadable) throws Exception {
		Collection<FontVo> vos = new ArrayList<FontVo>();
		
		for(Font entity : entities) {
			vos.add(FontVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Font toEntity(Font entity, FontVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		
		if(null == entity) entity = new FontImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
						
			entity.setEnabled(StringBooleanEnum.toEnumValue(vo.getEnabled()));
			entity.setFontName(vo.getFontName());
			entity.setFontSize(vo.getFontSize());
			entity.setFontWeight(vo.getFontWeight());
			entity.setFontStyle(vo.getFontStyle());
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<Font> toEntityList(Collection<FontVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<Font> entities = new ArrayList<Font>();
		
		for(FontVo vo : vos) {
			entities.add(FontVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
		
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the fontName
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * @param fontName the fontName to set
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	/**
	 * @return the fontSize
	 */
	public Short getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(Short fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the fontWeight
	 */
	public Short getFontWeight() {
		return fontWeight;
	}

	/**
	 * @param fontWeight the fontWeight to set
	 */
	public void setFontWeight(Short fontWeight) {
		this.fontWeight = fontWeight;
	}

	/**
	 * @return the fontStyle
	 */
	public Short getFontStyle() {
		return fontStyle;
	}

	/**
	 * @param fontStyle the fontStyle to set
	 */
	public void setFontStyle(Short fontStyle) {
		this.fontStyle = fontStyle;
	}
}