package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.WorkPeriodOvernightStayInfo;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodOvernightStayInfoImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class WorkPeriodOvernightStayInfoVo extends AbstractVo {
	private CountryCodeSubdivisionVo countrySubdivisionVo;
	private String city;
	private Date estimatedArrivalDate;
	private Long lengthOfStay=0L;
	private String remarks;

	public WorkPeriodOvernightStayInfoVo() {
		
	}
	
	public static WorkPeriodOvernightStayInfoVo getInstance(WorkPeriodOvernightStayInfo entity, Boolean cascadable, Persistable... persistables) throws Exception {
		WorkPeriodOvernightStayInfoVo vo = new WorkPeriodOvernightStayInfoVo();
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setEstimatedArrivalDate(entity.getEstimatedArrivalDate());
			vo.setCity(entity.getCity());
			vo.setLengthOfStay(entity.getLengthOfStay());
			vo.setRemarks(entity.getRemarks());
			vo.setCountrySubdivisionVo(CountryCodeSubdivisionVo.getInstance(entity.getCountrySubdivision(), true));
		}
		
		return vo;
	}
	
	public static Collection<WorkPeriodOvernightStayInfoVo> getInstances(Collection<WorkPeriodOvernightStayInfo> entities , Boolean cascadable, Persistable...persistables) throws Exception {
		Collection<WorkPeriodOvernightStayInfoVo> vos = new ArrayList<WorkPeriodOvernightStayInfoVo>();
	
		for(WorkPeriodOvernightStayInfo entity : entities) {
			vos.add(WorkPeriodOvernightStayInfoVo.getInstance(entity,cascadable,persistables));
		}
		
		return vos;
	}

	public static WorkPeriodOvernightStayInfo toEntity(WorkPeriodOvernightStayInfoVo vo, Boolean cascadable, Persistable...persistables) throws Exception {
		WorkPeriodOvernightStayInfo entity = new WorkPeriodOvernightStayInfoImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setCity(vo.getCity());
			entity.setCountrySubdivision(CountryCodeSubdivisionVo.toEntity(vo.getCountrySubdivisionVo(), false));
			entity.setEstimatedArrivalDate(vo.getEstimatedArrivalDate());
			entity.setLengthOfStay(vo.getLengthOfStay());
			entity.setRemarks(vo.getRemarks());
			
			WorkPeriod wpEntity=(WorkPeriod)AbstractVo.getPersistableObject(persistables, WorkPeriodImpl.class);
			if(null != wpEntity){
				entity.setWorkPeriod(wpEntity);
			}
			
		}
		
		return entity;
	}
	
	public static Collection<WorkPeriodOvernightStayInfo> toEntities(Collection<WorkPeriodOvernightStayInfoVo> vos, Boolean cascadable) throws Exception {
		Collection<WorkPeriodOvernightStayInfo> entities = new ArrayList<WorkPeriodOvernightStayInfo>();

		for(WorkPeriodOvernightStayInfoVo vo : vos) {
			entities.add(WorkPeriodOvernightStayInfoVo.toEntity(vo,cascadable));
		}
		
		return entities;
	}

	/**
	 * Returns the countrySubdivisionVo.
	 *
	 * @return 
	 *		the countrySubdivisionVo to return
	 */
	public CountryCodeSubdivisionVo getCountrySubdivisionVo() {
		return countrySubdivisionVo;
	}

	/**
	 * Sets the countrySubdivisionVo.
	 *
	 * @param countrySubdivisionVo 
	 *			the countrySubdivisionVo to set
	 */
	public void setCountrySubdivisionVo(
			CountryCodeSubdivisionVo countrySubdivisionVo) {
		this.countrySubdivisionVo = countrySubdivisionVo;
	}

	/**
	 * Returns the city.
	 *
	 * @return 
	 *		the city to return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city 
	 *			the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Returns the estimatedArrivalDate.
	 *
	 * @return 
	 *		the estimatedArrivalDate to return
	 */
	public Date getEstimatedArrivalDate() {
		return estimatedArrivalDate;
	}

	/**
	 * Sets the estimatedArrivalDate.
	 *
	 * @param estimatedArrivalDate 
	 *			the estimatedArrivalDate to set
	 */
	public void setEstimatedArrivalDate(Date estimatedArrivalDate) {
		this.estimatedArrivalDate = estimatedArrivalDate;
	}

	/**
	 * Returns the lengthOfStay.
	 *
	 * @return 
	 *		the lengthOfStay to return
	 */
	public Long getLengthOfStay() {
		return lengthOfStay;
	}

	/**
	 * Sets the lengthOfStay.
	 *
	 * @param lengthOfStay 
	 *			the lengthOfStay to set
	 */
	public void setLengthOfStay(Long lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}

	/**
	 * Returns the remarks.
	 *
	 * @return 
	 *		the remarks to return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Sets the remarks.
	 *
	 * @param remarks 
	 *			the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

	
	
}
