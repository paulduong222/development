package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.FuelType;
import gov.nwcg.isuite.core.domain.impl.FuelTypeImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class FuelTypeVo extends AbstractVo implements PersistableVo {
	private String code;
	private String description;
	
	/**
	* Default Constructor
	*/
	public FuelTypeVo() {
		super();
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static FuelTypeVo getInstance(FuelType entity,boolean cascadable) throws Exception {
		FuelTypeVo vo = new FuelTypeVo();
		
		if(null == entity)
			throw new Exception("Unable to create FuelTypeVo from null FuelType entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setCode(entity.getCode());
			vo.setDescription(entity.getDescription());
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<FuelTypeVo> getInstances(Collection<FuelType> entities, boolean cascadable) throws Exception {
		Collection<FuelTypeVo> vos = new ArrayList<FuelTypeVo>();

		for(FuelType entity : entities){
			vos.add(FuelTypeVo.getInstance(entity, cascadable));
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
	public static FuelType toEntity(FuelType entity, FuelTypeVo vo, boolean cascadable,Persistable...persistables) throws Exception {
		if (null == entity) {
			entity = new FuelTypeImpl();
		}
		
		entity.setId(vo.getId());

		if(cascadable){
			entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
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
	public static Collection<FuelType> toEntityList(Collection<FuelTypeVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<FuelType> entities = new ArrayList<FuelType>();
		
		for(FuelTypeVo vo : vos){
			entities.add(FuelTypeVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	
	
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
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

}
