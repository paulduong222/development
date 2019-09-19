package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.filter.AgencyFilter;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.filter.ObjectFilter;
import gov.nwcg.isuite.framework.types.AgencyTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.VoValidator;

/**
 * 
 */
@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class AgencyVo extends AbstractVo implements PersistableVo {
   private String agencyCd;
   private String agencyNm;
   private AgencyTypeEnum theAgencyType;
   private Date deletedDate;
   private Boolean standard = false;
   private Boolean state = false;
   private AgencyGroupVo agencyGroupVo;
   private RateGroupVo rateGroupVo;
   private Boolean active;
   private IncidentVo incidentVo;
   private IncidentGroupVo incidentGroupVo;
   
   
   /**
    * Constructor
    */
   public AgencyVo() {
      super();
   }

   /**
    * Constructor
    * 
    * @param agency
    * 			the entity to initialize vo from
    */
   public AgencyVo(Agency agency) {
      super();
      if (agency != null) {
         setAgencyCd(agency.getAgencyCode());
         setAgencyNm(agency.getAgencyName());
         setTheAgencyType(agency.getAgencyType());
         setId(agency.getId());
      }
      
   }

   public static AgencyVo getInstance(Agency entity, boolean cascadable) throws Exception {
	   AgencyVo vo = new AgencyVo();
	   
	   if(null == entity)
		   throw new Exception("Unable to build AgencyVo instance from null Agency entity");
	   
	   vo.setId(entity.getId());
	   
	   if(cascadable){
		   vo.setAgencyCd(entity.getAgencyCode());
		   vo.setAgencyNm(entity.getAgencyName());
		   vo.setStandard(entity.getStandard());
		   vo.setState(entity.getState());
		   
		   if(null != entity.getAgencyGroup()) {
			   vo.setAgencyGroupVo(AgencyGroupVo.getInstance(entity.getAgencyGroup(), false));
			   vo.getAgencyGroupVo().setCode(entity.getAgencyGroup().getCode());
		   }
		   
		   if(null != entity.getRateGroup()) {
			   vo.setRateGroupVo(RateGroupVo.getInstance(entity.getRateGroup(), true));
		   }
		   
	       vo.setActive(StringBooleanEnum.toBooleanValue(entity.isActive()));
	         
		   if(null != entity.getIncident()) {
			   vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), false));
			   vo.getIncidentVo().setIncidentName(entity.getIncident().getIncidentName());
		   }
         
		   if(null != entity.getIncidentGroup()) {
			   vo.setIncidentGroupVo(IncidentGroupVo.getInstance(entity.getIncidentGroup(), false));
			   vo.getIncidentGroupVo().setGroupName(entity.getIncidentGroup().getGroupName());
		   }
	   }
	   
	   return vo;
   }
   
   /**
    * @param entities
    * @param cascadable
    * @return
    * @throws Exception
    */
   public static Collection<AgencyVo> getInstances(Collection<Agency> entities, boolean cascadable) throws Exception {
	   Collection<AgencyVo> vos = new ArrayList<AgencyVo>();
	   
	   for(Agency entity : entities) {
		   vos.add(AgencyVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public Agency toEntity(Persistable entity) throws Exception {
	   return populateEntity(this, ((Agency)entity));
   }

   public static Agency toEntity(Agency entity, AgencyVo sourceVo,boolean cascadable,Persistable... persistables) throws Exception {
	   if (null == entity) {
		   entity = new AgencyImpl();
	   }
	   
	   entity.setId(sourceVo.getId());
	   
	   if(cascadable)
	   {
		   entity.setId(sourceVo.getId());
		   entity.setAgencyCode(sourceVo.getAgencyCd());
		   entity.setAgencyName(sourceVo.getAgencyNm());
		   entity.setStandard(sourceVo.getStandard());
		   entity.setState(sourceVo.getState());
		   
		   if(null!=sourceVo.getTheAgencyType()){
			   entity.setAgencyType(sourceVo.getTheAgencyType());
		   }
		   
		   if(null != sourceVo.getAgencyGroupVo()) {
			   entity.setAgencyGroup(AgencyGroupVo.toEntity(null, sourceVo.getAgencyGroupVo(), false));
			   if(null==entity.getAgencyType()){
				   if(sourceVo.getAgencyGroupVo().getCode().equalsIgnoreCase("F"))
					   entity.setAgencyType(AgencyTypeEnum.FEDERAL);
				   if(sourceVo.getAgencyGroupVo().getCode().equalsIgnoreCase("S"))
					   entity.setAgencyType(AgencyTypeEnum.STATE);
				   if(sourceVo.getAgencyGroupVo().getCode().equalsIgnoreCase("O"))
					   entity.setAgencyType(AgencyTypeEnum.OTHER);
			   }
		   }
		   
		   if(null != sourceVo.getRateGroupVo()) {
			   entity.setRateGroup(RateGroupVo.toEntity(null, sourceVo.getRateGroupVo(), false));
		   }
		   
		   entity.setActive(StringBooleanEnum.toEnumValue(sourceVo.isActive()));
	         
	       if (null != sourceVo.getIncidentVo() && LongUtility.hasValue(sourceVo.getIncidentVo().getId())) {
	    	   entity.setIncident(IncidentVo.toEntity(null, sourceVo.getIncidentVo(), false));
	       }
         
	       if (null != sourceVo.getIncidentGroupVo() && LongUtility.hasValue(sourceVo.getIncidentGroupVo().getId())) {
	    	   entity.setIncidentGroup(IncidentGroupVo.toEntity(null, sourceVo.getIncidentGroupVo(), false));
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
   private static Agency populateEntity(AgencyVo vo, Agency entity) {
	   if(null==entity){
		   entity = new AgencyImpl();
	   }
	   
	   entity.setId(vo.getId());
	   entity.setAgencyCode(vo.getAgencyCd());
	   entity.setAgencyName(vo.getAgencyNm());
	   if(null!=vo.getTheAgencyType()){
		   entity.setAgencyType(vo.getTheAgencyType());
	   }
	   
	   return entity;
   }
   
   
   /*
    * @param agencyCd the 4 digit agency code definition
    */
   public void setAgencyCd(String agencyCode) {
      this.agencyCd = agencyCode;
   }

	public static AgencyVo getById(Long id, Collection<AgencyVo> vos){
		for(AgencyVo agencyVo : vos){
			if(agencyVo.getId().compareTo(id)==0)
				return agencyVo;
		}
		
		return null;
	}
   
   /*
    * @return the agency code
    */
   public String getAgencyCd() {
      return agencyCd;
   }
   
   /**
    * @return the agencyNm
    */
   public String getAgencyNm() {
      return agencyNm;
   }

   /**
    * @param agencyNm the agencyNm to set
    */
   public void setAgencyNm(String agencyName) {
      this.agencyNm = agencyName;
   }

   /**
    * @return the theAgencyType
    */
   public AgencyTypeEnum getTheAgencyType() {
      return theAgencyType;
   }

   /**
    * @param theAgencyType the theAgencyType to set
    */
   public void setTheAgencyType(AgencyTypeEnum agencyType) {
      this.theAgencyType = agencyType;
   }

   /**
    * @param filter
    * @param sourceVos
    * @return
    * @throws Exception
    */
   public static Collection<AgencyVo> getVosByFilter(AgencyFilter filter, Collection<AgencyVo> sourceVos) throws Exception {
	   Collection<AgencyVo> vos = new ArrayList<AgencyVo>();
	   ObjectFilter oFilter = new ObjectFilter(AgencyVo.class);

	   Collection<FilterCriteria> criteria = getVoFilterCriteria(filter);

	   if(!VoValidator.hasOneFilter(criteria)){
		   return sourceVos;
	   }

	   for(AgencyVo vo : sourceVos){
		   AgencyVo ccVo = (AgencyVo)oFilter.filterVo(vo, criteria);

		   if(null != ccVo)
			   vos.add(ccVo);
	   }

	   return vos;
   }

	public static Collection<FilterCriteria> getVoFilterCriteria(AgencyFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
		
		criteria.add( null != filter.getAgencyCode() && !filter.getAgencyCode().isEmpty() ? new FilterCriteria("agencyCd",filter.getAgencyCode(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getAgencyName() && !filter.getAgencyName().isEmpty() ? new FilterCriteria("agencyNm",filter.getAgencyName(),FilterCriteria.TYPE_ILIKE) : null);

		return criteria;
	}

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Boolean getStandard() {
		return standard;
	}

	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @param agencyGroupVo the agencyGroupVo to set
	 */
	public void setAgencyGroupVo(AgencyGroupVo agencyGroupVo) {
		this.agencyGroupVo = agencyGroupVo;
	}

	/**
	 * @return the agencyGroupVo
	 */
	public AgencyGroupVo getAgencyGroupVo() {
		return agencyGroupVo;
	}

	/**
	 * @param rateGroupVo the rateGroupVo to set
	 */
	public void setRateGroupVo(RateGroupVo rateGroupVo) {
		this.rateGroupVo = rateGroupVo;
	}

	/**
	 * @return the rateGroupVo
	 */
	public RateGroupVo getRateGroupVo() {
		return rateGroupVo;
	}

	/**
	 * @return the state
	 */
	public Boolean getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Boolean state) {
		this.state = state;
	}
	
	 /**
	  * @return the active
	  */
	@JsonProperty("active")
	public Boolean getActive() {
		return active;
	}

	/**
	 * @return the active
	 */
	@JsonIgnore
	public Boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	@JsonProperty("active")
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param incidentVo the incidentVo to set
	 */
//	@JsonIgnore
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}
	
	/**
	 * @return the incidentVo
	 */
//	@JsonIgnore
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}
	
	/**
	 * @param incidentGroupVo the incidentGroupVo to set
	 */
//	@JsonIgnore
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
	}
	
	/**
	 * @return the incidentGroupVo
	 */
//	@JsonIgnore
	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}
   
}
