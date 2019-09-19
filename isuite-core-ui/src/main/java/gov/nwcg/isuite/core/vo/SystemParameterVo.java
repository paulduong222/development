package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.SystemParameterImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;


import java.util.ArrayList;
import java.util.Collection;

public class SystemParameterVo extends AbstractVo {
	private String parameterName;
	private String parameterValue;

	public SystemParameterVo() {
		
	}

	public static SystemParameterVo getInstance(SystemParameter entity, Boolean cascadable) throws Exception {
		SystemParameterVo vo = new SystemParameterVo();
		
		if(null==entity)
			throw new Exception("Unable to create vo from null systemrole entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setParameterName(entity.getParameterName());
			vo.setParameterValue(entity.getParameterValue());
		}
		
		return vo;
	}
	
	public static Collection<SystemParameterVo> getInstances(Collection<SystemParameter> entities, Boolean cascadable) throws Exception {
		Collection<SystemParameterVo> vos = new ArrayList<SystemParameterVo>();
		
		for(SystemParameter entity : entities){
			vos.add(SystemParameterVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static SystemParameter toEntity(SystemParameterVo vo, Boolean cascadable) throws Exception {
		SystemParameter entity = new SystemParameterImpl();
		
		if(null == vo)
			throw new Exception("Unable to create systemrole entity from null vo.");
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setParameterName(vo.getParameterName());
			entity.setParameterValue(vo.getParameterValue());
		}
		return entity;
	}
	
	public static Collection<SystemParameter> toEntityList(Collection<SystemParameterVo> vos, Boolean cascadable) throws Exception {
		Collection<SystemParameter> entities = new ArrayList<SystemParameter>();
		
		for(SystemParameterVo vo : vos){
			entities.add(SystemParameterVo.toEntity(vo, cascadable));
		}
		
		return entities;
	}

	/**
	 * @return the parameterName
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * @param parameterName the parameterName to set
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	/**
	 * @return the parameterValue
	 */
	public String getParameterValue() {
		return parameterValue;
	}

	/**
	 * @param parameterValue the parameterValue to set
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	

}
