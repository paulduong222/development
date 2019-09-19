package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CountryCode;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.impl.CountryCodeImpl;
import gov.nwcg.isuite.core.domain.impl.CountrySubdivisionImpl;
import gov.nwcg.isuite.core.filter.CountryCodeFilter;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.filter.ObjectFilter;
import gov.nwcg.isuite.framework.util.VoValidator;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 
 * @author kvelasquez
 */
public class CountryCodeVo extends AbstractVo implements PersistableVo {
   private String countryAbbreviation;
   private String countryName;
   
   public CountryCodeVo() {
      super();
   }
    
   public CountryCodeVo(CountryCode cc) {
      super();
      if (cc != null) {
         setCountryName(cc.getName());
         setCountryAbbreviation(cc.getAbbreviation());
         setId(cc.getId());
      }
   }

   public static CountryCodeVo getInstance(CountryCode entity, boolean cascadable) throws Exception {
	   CountryCodeVo vo = new CountryCodeVo();
	   
  	   if(null == entity)
			throw new Exception("Unable to create CountryCodeVo from null entity.");
		
	   vo.setId(entity.getId());
	   
	   if(cascadable){
		   vo.setCountryAbbreviation(entity.getAbbreviation());
		   vo.setCountryName(entity.getName());
	   }
	   
	   return vo;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public CountryCode toEntity(Persistable entity) throws Exception {
	   return populateEntity(this, ((CountryCode)entity));
   }
   
   
   /**
    * Returns a CountryCode entity from a vo.
    * 
    * @param vo
    * 			the source vo
    * @param cascadable
    * 			flag indicating whether the entity instance should created as a cascadable entity
    * @return
    * 			instance of CountrySubdivision entity
    * @throws Exception
    */
   public static CountryCode toEntity(CountryCodeVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
	   CountryCode entity=new CountryCodeImpl();
		
	   entity.setId(vo.getId());
		
	   if(cascadable)
	   {
		   if(vo.getCountryAbbreviation() != null && vo.getCountryName() != null)
		   {
			   entity.setAbbreviation(vo.getCountryAbbreviation());
			   entity.setName(vo.getCountryName());
		   }
	   }
	   
	   return entity;
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
   private static CountryCode populateEntity(CountryCodeVo vo, CountryCode entity) {
	   if(null==entity){
		   entity = new CountryCodeImpl();
	   }
	   
	   entity.setId(vo.getId());
	   
	   return entity;
   }

   /**
	 * Abbreviation of the country.
	 * @param countryAbbreviation cannot be null
	 * @see #getCountryAbbreviation()
	 */
   public void setCountryAbbreviation(String abbreviation) {
      this.countryAbbreviation = abbreviation;
   }

	/**
	 * Abbreviation of the country
	 * @return countryAbbreviation of the country, will not be null
	 * @see #setCountryAbbreviation(String)
	 */
   public String getCountryAbbreviation() {
      return countryAbbreviation;
   }

	/**
	 * Accessor for countryName of country code.
	 * @param countryName countryName of country, cannot be null
	 * @see #getCountryName()
	 */
   public void setCountryName(String name) {
      this.countryName = name;
   }

	/**
	 * Accessor for countryName of country code.
	 * @return country code countryName
	 * @see #setCountryName(String)
	 */
   public String getCountryName() {
      return countryName;
   }
 
   /**
    * @param filter
    * @param sourceVos
    * @return
    * @throws Exception
    */
   public static Collection<CountryCodeVo> getVosByFilter(CountryCodeFilter filter, Collection<CountryCodeVo> sourceVos) throws Exception {
	   Collection<CountryCodeVo> vos = new ArrayList<CountryCodeVo>();
	   ObjectFilter oFilter = new ObjectFilter(CountryCodeVo.class);
	   
	   Collection<FilterCriteria> criteria = getVoFilterCriteria(filter);
	   
	   if(!VoValidator.hasOneFilter(criteria)){
		   return sourceVos;
	   }
	   
	   for(CountryCodeVo vo : sourceVos){
		   CountryCodeVo ccVo = (CountryCodeVo)oFilter.filterVo(vo, criteria);
		   
		   if(null != ccVo)
			   vos.add(ccVo);
	   }
	   
	   return vos;
   }

	public static Collection<FilterCriteria> getVoFilterCriteria(CountryCodeFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
		
		criteria.add( null != filter.getCountryCodeAbbreviation() && !filter.getCountryCodeAbbreviation().isEmpty() ? new FilterCriteria("countryAbbreviation",filter.getCountryCodeAbbreviation(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getCountryCodeName() && !filter.getCountryCodeName().isEmpty() ? new FilterCriteria("countryName",filter.getCountryCodeName(),FilterCriteria.TYPE_ILIKE) : null);

		return criteria;
	}
	
}