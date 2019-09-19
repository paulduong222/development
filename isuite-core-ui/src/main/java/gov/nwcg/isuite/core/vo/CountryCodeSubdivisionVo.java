package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CountryCode;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.impl.CountrySubdivisionImpl;
import gov.nwcg.isuite.core.filter.CountrySubdivisionFilter;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.filter.ObjectFilter;
import gov.nwcg.isuite.framework.util.VoValidator;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author kvelasquez
 */
public class CountryCodeSubdivisionVo extends AbstractVo implements PersistableVo {
   private boolean standard = false;
   private CountryCodeVo countryCodeVo;
   private Long countryCdId;
   private String countrySubAbbreviation;
   private String countrySubName;
   
   /**
    * 
    */
   public CountryCodeSubdivisionVo() {
      super();
   }
   
   /**
    * @param ccs
    */
   public CountryCodeSubdivisionVo(CountrySubdivision ccs) {
      super();
      if (ccs != null) {
         setCountrySubAbbreviation(ccs.getAbbreviation());
         setCountrySubName(ccs.getName());
         setCountryCdId(ccs.getCountryCodeId());
         setStandard(ccs.isStandard());
         setCountryCodeVo(new CountryCodeVo(ccs.getCountryCode()));
         setId(ccs.getId());
      }
   }

   /**
	 * Returns a CountryCodeSubdivisionVo instance from entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of CountryCodeSubdivisionVo
	 * @throws Exception
	 */
	public static CountryCodeSubdivisionVo getInstance(CountrySubdivision entity,boolean cascadable) throws Exception {
		CountryCodeSubdivisionVo vo = new CountryCodeSubdivisionVo();

		if(null == entity)
			throw new Exception("Unable to create CountryCodeSubdivisionVo from null entity.");

		vo.setId(entity.getId());

		if(cascadable){
			if(null != entity.getCountryCode()) {
			   vo.setCountryCodeVo(CountryCodeVo.getInstance(entity.getCountryCode(),true));
			}
			vo.setCountrySubAbbreviation(entity.getAbbreviation());
			vo.setCountrySubName(entity.getName());
			vo.setStandard(entity.isStandard());
			vo.setCountryCdId(entity.getCountryCodeId());
		}
		
		return vo;
	}
	
	public static Collection<CountryCodeSubdivisionVo> getInstances(Collection<CountrySubdivisionImpl> entities, boolean cascadable) throws Exception {
		Collection<CountryCodeSubdivisionVo> vos = new ArrayList<CountryCodeSubdivisionVo>();
		
		
		
		for(CountrySubdivisionImpl entity : entities){
			vos.add(CountryCodeSubdivisionVo.getInstance(entity, cascadable));
		}
		return vos;
	}
	

	   /**
	    * Returns a CountrySubdivision entity from a vo.
	    * 
	    * @param vo
	    * 			the source vo
	    * @param cascadable
	    * 			flag indicating whether the entity instance should created as a cascadable entity
	    * @return
	    * 			instance of CountrySubdivision entity
	    * @throws Exception
	    */
	   public static CountrySubdivision toEntity(CountryCodeSubdivisionVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		   CountrySubdivision entity=new CountrySubdivisionImpl();
			
		   entity.setId(vo.getId());
			
		   if(cascadable){
			   entity.setAbbreviation(vo.getCountrySubAbbreviation());
			   entity.setCountryCodeId(vo.getCountryCdId());
			   entity.setCountryCode(CountryCodeVo.toEntity(vo.getCountryCodeVo(), cascadable));
			   entity.setName(vo.getCountrySubName());
			   entity.setStandard(vo.isStandard());
		   }
		   
		   return entity;
	   }
	   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public CountrySubdivision toEntity(Persistable entity) throws Exception {
	   return populateEntity(this, ((CountrySubdivision)entity));
   }
   
   /**
    * Populates and returns an entity object with the values from the vo object.
    * 
    * @param vo
    * 			the source vo object
    * @param entity
    * 			the entity to populate and return
    * @return
    * 		the entity to return
    */
   private static CountrySubdivision populateEntity(CountryCodeSubdivisionVo vo, CountrySubdivision entity) {
	   if(null==entity){
		   entity = new CountrySubdivisionImpl();
	   }
	   
	   entity.setId(vo.getId());
	   
	   return entity;
   }

   /**
	 * Accessor for countrySubAbbreviation.
	 * @see #setCountrySubAbbreviation(String)
	 * @return the countrySubAbbreviation, will not be null
	 */
	public String getCountrySubAbbreviation() {
	   return countrySubAbbreviation;
	}

	/**
	 * Accessor for countrySubAbbreviation.
	 * @param countrySubAbbreviation the countrySubAbbreviation to set, will not be null
	 * @see #setCountrySubAbbreviation(String)
	 */
	public void setCountrySubAbbreviation(String abbreviation) {
	   this.countrySubAbbreviation = abbreviation;
	}

	/**
	 * Accessor for countrySubName.
	 * @return the countrySubName, will not be null
	 * @see #setCountrySubName(String)
	 */
	public String getCountrySubName() {
	   return countrySubName;
	}

	/**
	 * Accessor for countrySubName.
	 * @param countrySubName the countrySubName to set, can not be null
	 * @see #getCountrySubName()
	 */
	public void setCountrySubName(String name) {
	   this.countrySubName = name;
	}

   /**
    * Accessor for standard.
    * @return the standard flag, will not be null
    * @see #setStandard(boolean)
    */
	@JsonIgnore
   public boolean isStandard() {
      return standard;
   }

   public boolean getStandard() {
      return standard;
   }
	
   /**
    * Accessor for standard.
    * @param standard the flag to set if the item comes standard with the base application or not, can not be null
    * @see #isStandard()
    */
   public void setStandard(boolean standard) {
      this.standard = standard;
   }

   /**
    * Accessor countryCdId
    * 
    * @see #setCountryCdId(Long)
    * @return countryCdId
    */
   public Long getCountryCdId() {
      return countryCdId;
   }

   /**
    * Accessor countryCdId
    * 
    * @see #getCountryCdId()
    * @param countryCdId id of country code object
    */
   public void setCountryCdId(Long id) {
      this.countryCdId = id;
   }

   /**
    * Accessor countryCode
    * 
    * @see #setCountryCode(CountryCode)
    * @return countryCode
    */
   public CountryCodeVo getCountryCodeVo() {
      if (countryCodeVo == null) {
         setCountryCodeVo(new CountryCodeVo());
      }
      return countryCodeVo;
   }

   /**
    * Accessor countryCode
    * 
    * @see #getCountryCode()
    * @param CountryCode country code object
    */
   public void setCountryCodeVo(CountryCodeVo countryCodeVo) {
      this.countryCodeVo = countryCodeVo;
   }

   /**
    * @param filter
    * @param sourceVos
    * @return
    * @throws Exception
    */
   public static Collection<CountryCodeSubdivisionVo> getVosByFilter(CountrySubdivisionFilter filter, Collection<CountryCodeSubdivisionVo> sourceVos) throws Exception {
	   Collection<CountryCodeSubdivisionVo> vos = new ArrayList<CountryCodeSubdivisionVo>();
	   ObjectFilter oFilter = new ObjectFilter(CountryCodeSubdivisionVo.class);
	   
	   Collection<FilterCriteria> criteria = getVoFilterCriteria(filter);
	   
	   if(!VoValidator.hasOneFilter(criteria)){
		   return sourceVos;
	   }
	   
	   for(CountryCodeSubdivisionVo vo : sourceVos){
		   CountryCodeSubdivisionVo ccVo = (CountryCodeSubdivisionVo)oFilter.filterVo(vo, criteria);
		   
		   if(null != ccVo)
			   vos.add(ccVo);
	   }
	   
	   return vos;
   }

	public static Collection<FilterCriteria> getVoFilterCriteria(CountrySubdivisionFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
		
		criteria.add( null != filter.getCountryAbbreviation() && !filter.getCountryAbbreviation().isEmpty() ? new FilterCriteria("countryAbbreviation",filter.getCountryAbbreviation(),FilterCriteria.TYPE_ILIKE, FilterCriteria.FIELD_STRING) : null);
		
		criteria.add( null != filter.getCountryCodeId() ? new FilterCriteria("countryCdId",filter.getCountryCodeId(),FilterCriteria.TYPE_EQUAL, FilterCriteria.FIELD_LONG) : null);

		return criteria;
	}
   
}