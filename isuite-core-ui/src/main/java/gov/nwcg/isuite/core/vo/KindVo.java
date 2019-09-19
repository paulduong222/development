/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.filter.KindFilter;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.filter.ObjectFilter;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.Validator;
import gov.nwcg.isuite.framework.util.VoValidator;

/**
 *
 */
public class KindVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard = false;
   private Long requestCategoryId;
   private RequestCategoryVo requestCategoryVo=null;
   private DepartmentVo sectionCodeVo;
   private DailyFormVo dailyFormVo;
   private GroupCategoryVo groupCategoryVo;
   private SubGroupCategoryVo subGroupCategoryVo;
   private Sit209Vo _209CodeVo;
   private Boolean direct=false;
   private Integer units;
   private Integer people;
   private Boolean lineOverhead=false;
   private Boolean subordinate=false;
   private Boolean strikeTeam=false;
   private Boolean aircraft=false;
   private Boolean active;
   private IncidentVo incidentVo;
   private IncidentGroupVo incidentGroupVo;
   private BigDecimal standardCost;
   
   public KindVo() {
      super();
   }

   public KindVo(Kind kind) {
      super();
      if(kind != null) {
         setCode(kind.getCode());
         setDescription(kind.getDescription());
         setStandard(kind.isStandard());
         setId(kind.getId());
      }
   }

   /**
    * Returns a KindVo from a Kind entity.
    * 
    * @param entity
    * 			the source Kind entity
    * @param cascadable
    * 			flag indicating whether the vo instance should created as a cascadable vo
    * @return
    * 			instance of Kind vo
    * @throws Exception
    */
   public static KindVo getInstance(Kind entity,boolean cascadable) throws Exception{
	   KindVo vo = new KindVo();
	
	   if(null == entity)
		   throw new Exception("Unable to create KindVo from null Kind entity.");
	
	   vo.setId(entity.getId());

	   /*
	    * Only populate fields outside of the entity Id if needed
	    */
	   if(cascadable){
		   
		   vo.setCode(entity.getCode());
		   vo.setDescription(entity.getDescription());
		   vo.setStandard(entity.isStandard());
		   if(null != entity.getAircraft())
			   vo.setAircraft(entity.getAircraft().getValue());
		   else
			   vo.setAircraft(false);
		   
	   	   vo.setRequestCategoryId(entity.getRequestCategoryId());

	   	   if( (null != entity.getRequestCategoryId()) && (entity.getRequestCategoryId()>0)){
	   		   vo.setRequestCategoryVo(RequestCategoryVo.getInstance(entity.getRequestCategory(), true));
	   	   }
	   	   
	   	   if(null != entity.getDepartment()) {
	   		   vo.setSectionCodeVo(DepartmentVo.getInstance(entity.getDepartment(), true));
	   	   }
	   	   
	   	   if(null != entity.getSit209()) {
	   		   vo.set_209CodeVo(Sit209Vo.getInstance(entity.getSit209(), true));
	   	   }
	   	   
	   	   vo.setDirect(entity.getDirect());
	   	   
	   	   if(null != entity.getDailyForm()) {
	   		   vo.setDailyFormVo(DailyFormVo.getInstance(entity.getDailyForm(), true));
	   	   }
	   	   
	   	   vo.setUnits(entity.getUnits());
	   	   vo.setPeople(entity.getPeople());
	   	   
	   	   if(null != entity.getSubGroupCategory()) {
	   		   vo.setSubGroupCategoryVo(SubGroupCategoryVo.getInstance(entity.getSubGroupCategory(), true));
	   	   }
	   	   
	   	   if(null != entity.getGroupCategory()) {
	   		   vo.setGroupCategoryVo(GroupCategoryVo.getInstance(entity.getGroupCategory(), true));
	   	   }
	   	   
	   	   vo.setLineOverhead(entity.getLineOverhead());
	   	   vo.setSubordinate(entity.getSubordinate());
	   	   vo.setStrikeTeam(entity.getStrikeTeam());
           vo.setActive(StringBooleanEnum.toBooleanValue(entity.isActive()));
        
	   	   if(null != entity.getIncident()) {
	   		   vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), true));
	   	   }
        
	   	   if(null != entity.getIncidentGroup()) {
	   		   vo.setIncidentGroupVo(IncidentGroupVo.getInstance(entity.getIncidentGroup(), true));
	   	   }
	   	   
			if ( DecimalUtil.hasValue(entity.getStandardCost()))
				vo.setStandardCost(entity.getStandardCost());
			else
				vo.setStandardCost(new BigDecimal(0));
	   	   // vo.setStandardCost(entity.getStandardCost());
	   }
	   
	   return vo;
   }

   /**
    * Returns a collection of KindVos from a collection of Kind entities.
    * 
    * @param entities
    * 			the source collection of Kind entities
    * @param cascadable
    * 			flag indicating whether the vo instances should created as a cascadable vos
    * @return
    * 			collection of Kind vos
    * @throws Exception
    */
   public static Collection<KindVo> getInstances(Collection<Kind> entities, boolean cascadable) throws Exception {
	   Collection<KindVo> vos = new ArrayList<KindVo>();
	   
	   for(Kind entity : entities){
		   vos.add(KindVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /**
    * Returns a Kind entity from a Kind vo.
    * 
    * @param vo
    * 			the source Kind vo
    * @param cascadable
    * 			flag indicating whether the entity instance should created as a cascadable entity
    * @return
    * 			instance of Kind entity
    * @throws Exception
    */
   public static Kind toEntity(Kind entity, KindVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
	   if (null == entity) {
		   entity = new KindImpl();
	   }
	   
		entity.setId(vo.getId());
		
		if(cascadable){
			
    		entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
			entity.setStandard(vo.getStandard());
			entity.setAircraft(StringBooleanEnum.toEnumValue(vo.getAircraft()));

        	/*
        	 * Kind --> RequestCategory (one way directional relationship)
        	 * Only need to setRequestCategory with just the entity id (set cascadable false).
        	 */
			if(null!=vo.getRequestCategoryVo()){
				entity.setRequestCategory(RequestCategoryVo.toEntity(vo.getRequestCategoryVo(),true));
			}else{
				if(null!=vo.getRequestCategoryId()){
					RequestCategoryVo reqCatVo = new RequestCategoryVo();
					reqCatVo.setId(vo.getRequestCategoryId());
					entity.setRequestCategory(RequestCategoryVo.toEntity(reqCatVo,true));
				}else
					throw new Exception("Unable to create Kind entity with unknown RequestCategory association.");
			}
			
			if(null != vo.getSectionCodeVo()) {
				entity.setDepartment(DepartmentVo.toEntity(vo.getSectionCodeVo(), false));
			}
			
			if(null != vo.get_209CodeVo() && LongUtility.hasValue(vo.get_209CodeVo().getId())) {
				entity.setSit209(Sit209Vo.toEntity(null, vo.get_209CodeVo(), false));
			}else{
				entity.setSit209(null);
			}
			
			entity.setDirect(vo.getDirect());
			
			if(null != vo.getDailyFormVo()) {
				entity.setDailyForm(DailyFormVo.toEntity(null, vo.getDailyFormVo(), false));
			}
			
			if ( IntegerUtility.hasValue(vo.getPeople()) && vo.getPeople().intValue() > 1000 ) {
				entity.setPeople(1000);
			} else {
				entity.setPeople(vo.getPeople());
			}

			if ( IntegerUtility.hasValue(vo.getUnits()) && vo.getUnits().intValue() > 200 ) {
				entity.setUnits(200);
			} else {
				entity.setUnits(vo.getUnits());
			}
			
			if(null != vo.getSubGroupCategoryVo()) {
				entity.setSubGroupCategory(SubGroupCategoryVo.toEntity(null, vo.getSubGroupCategoryVo(), false));
			}
			
			if(null != vo.getGroupCategoryVo()) {
				entity.setGroupCategory(GroupCategoryVo.toEntity(null, vo.getGroupCategoryVo(), false));
			}
			
			entity.setLineOverhead(vo.getLineOverhead());
			entity.setSubordinate(vo.getSubordinate());
			entity.setStrikeTeam(vo.getStrikeTeam());
			entity.setActive(StringBooleanEnum.toEnumValue(vo.isActive()));
	         
			if (null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())) {
				entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));
			}
         
			if (null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())) {
				entity.setIncidentGroup(IncidentGroupVo.toEntity(null, vo.getIncidentGroupVo(), false));
			}
			
			if ( DecimalUtil.hasValue(vo.getStandardCost()))
				entity.setStandardCost(vo.getStandardCost());
			else
				entity.setStandardCost(new BigDecimal(0));
			
			/*
			 * Validate the entity
			 */
    		validateEntity(entity);

		}
		
		return entity;
   }
   
   /**
    * Returns a collection of Kind entities from a collection of Kind vos.
    * 
    * @param vos
    * 			the source collection of Kind vos
    * @param cascadable
    * 			flag indicating whether the entity instances should created as a cascadable entities
    * @return
    * 			collection of Kind entities
    * @throws Exception
    */
   public static Collection<Kind> toEntityList(Collection<KindVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
	   Collection<Kind> entities = new ArrayList<Kind>();
	   
	   for(KindVo vo : vos){
		   entities.add(KindVo.toEntity(null, vo, cascadable, persistables));
	   }
	   
	   return entities;
   }
   
   /**
    * Perform some validation on the kind field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source kind entity
    * @throws ValidationException
    */
   private static void validateEntity(Kind entity) throws ValidationException {
	   Validator.validateStringField("code", entity.getCode(), 10, true);
	   Validator.validateStringField("description", entity.getDescription(), 75,true);
	   Validator.validateBooleanField("isStandard", entity.isStandard(), true);
	   Validator.validateEntityField("requestCategory", entity.getRequestCategory(), true);
   }
   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public Kind toEntity(Persistable entity) throws Exception {
	   // use KindVo.toEntity()
	   return populateEntity(this, ((Kind)entity));
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
   private static Kind populateEntity(KindVo vo, Kind entity) {
	   if(null==entity){
		   entity = new KindImpl();
	   }
	   
	   entity.setId(vo.getId());
	   
	   return entity;
   }

	public static KindVo getById(Long id, Collection<KindVo> vos){
		for(KindVo kindVo : vos){
			if(kindVo.getId().compareTo(id)==0)
				return kindVo;
		}
		
		return null;
	}

	public static KindVo getByCode(String code, Collection<KindVo> vos){
		for(KindVo kindVo : vos){
			if(kindVo.getCode().equalsIgnoreCase(code))
				return kindVo;
		}
		
		return null;
	}
	
   /**
    * Sets the code.
    * 
    * @param code 
    * 		the code to set
    */
   public void setCode(String code) {
      this.code = code;
   }
   
   /**
    * Returns the code.
    * 
    * @return 
    * 		the code
    */
   public String getCode() {
	   if(null != this.code) {
		   this.code = this.code.toUpperCase();
	   }
      return this.code;
   }

   /**
    * Sets the description.
    * 
    * @param description 
    * 		the description to set
    */
   public void setDescription(String description) {
      this.description = description;
   }
   
   /**
    * Returns the description.
    * 
    * @return 
    * 		the description to return
    */
   public String getDescription() {
	   if(null != this.description) {
		   this.description = this.description.toUpperCase();
	   }
      return this.description;
   }   
   
   /**
    * Returns whether or not this is a standard kind.
    * 
    * @return 
    * 		the standard status to return
    */
	@JsonIgnore
   public Boolean isStandard() {
      return this.standard;
   }

   /**
    * Returns whether or not this is a standard kind.
    * 
    * @return 
    * 		the standard status to return
    */
	@JsonProperty("standard")
   public Boolean getStandard() {
	   return isStandard();
   }
   
   /**
    * Sets whether or not this is a standard kind.
    * 
    * @param standard 
    * 		the standard status to set
    */
	@JsonProperty("standard")
   public void setStandard(Boolean standard) {
      this.standard = standard;
   }

	public Long getRequestCategoryId() {
		return requestCategoryId;
	}
	
	public void setRequestCategoryId(Long requestCategoryId) {
		this.requestCategoryId = requestCategoryId;
	}
	
	public RequestCategoryVo getRequestCategoryVo() {
		if(null==requestCategoryVo)
			requestCategoryVo = new RequestCategoryVo();
		
		return requestCategoryVo;
	}
	
	public void setRequestCategoryVo(RequestCategoryVo requestCategoryVo) {
		this.requestCategoryVo = requestCategoryVo;
	}          

	/**
	 * @param filter
	 * @param sourceVos
	 * @return
	 * @throws Exception
	 */
	public static Collection<KindVo> getVosByFilter(KindFilter filter, Collection<KindVo> sourceVos) throws Exception {
		Collection<KindVo> vos = new ArrayList<KindVo>();
		ObjectFilter oFilter = new ObjectFilter(KindVo.class);

		Collection<FilterCriteria> criteria = getVoFilterCriteria(filter);

		if(!VoValidator.hasOneFilter(criteria)){
			return sourceVos;
		}

		for(KindVo vo : sourceVos){
			KindVo fvo = (KindVo)oFilter.filterVo(vo, criteria);

			if(null != fvo)
				vos.add(fvo);
		}

		return vos;
	}

	public static Collection<FilterCriteria> getVoFilterCriteria(KindFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		criteria.add( null != filter.getCode() && !filter.getCode().isEmpty() ? new FilterCriteria("code",filter.getCode(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getDescription() && !filter.getDescription().isEmpty() ? new FilterCriteria("description",filter.getDescription(),FilterCriteria.TYPE_ILIKE) : null);

		return criteria;
	}

	public Sit209Vo get_209CodeVo() {
		return _209CodeVo;
	}

	public void set_209CodeVo(Sit209Vo codeVo) {
		_209CodeVo = codeVo;
	}

	public Boolean getDirect() {
		return direct;
	}

	public void setDirect(Boolean direct) {
		this.direct = direct;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public Integer getPeople() {
		return people;
	}

	public void setPeople(Integer people) {
		this.people = people;
	}

	public Boolean getLineOverhead() {
		return lineOverhead;
	}

	public void setLineOverhead(Boolean lineOverhead) {
		this.lineOverhead = lineOverhead;
	}

	public Boolean getSubordinate() {
		return subordinate;
	}

	public void setSubordinate(Boolean subordinate) {
		this.subordinate = subordinate;
	}

	public Boolean getStrikeTeam() {
		return strikeTeam;
	}

	public void setStrikeTeam(Boolean strikeTeam) {
		this.strikeTeam = strikeTeam;
	}

	/**
	 * @param sectionCodeVo the sectionCodeVo to set
	 */
	public void setSectionCodeVo(DepartmentVo sectionCodeVo) {
		this.sectionCodeVo = sectionCodeVo;
	}

	/**
	 * @return the sectionCodeVo
	 */
	public DepartmentVo getSectionCodeVo() {
		return sectionCodeVo;
	}

	/**
	 * @param dailyFormVo the dailyFormVo to set
	 */
	public void setDailyFormVo(DailyFormVo dailyFormVo) {
		this.dailyFormVo = dailyFormVo;
	}

	/**
	 * @return the dailyFormVo
	 */
	public DailyFormVo getDailyFormVo() {
		return dailyFormVo;
	}

	/**
	 * @param groupCategoryVo the groupCategoryVo to set
	 */
	public void setGroupCategoryVo(GroupCategoryVo groupCategoryVo) {
		this.groupCategoryVo = groupCategoryVo;
	}

	/**
	 * @return the groupCategoryVo
	 */
	public GroupCategoryVo getGroupCategoryVo() {
		return groupCategoryVo;
	}

	/**
	 * @param subGroupCategoryVo the subGroupCategoryVo to set
	 */
	public void setSubGroupCategoryVo(SubGroupCategoryVo subGroupCategoryVo) {
		this.subGroupCategoryVo = subGroupCategoryVo;
	}

	/**
	 * @return the subGroupCategoryVo
	 */
	public SubGroupCategoryVo getSubGroupCategoryVo() {
		return subGroupCategoryVo;
	}

	public Boolean getAircraft() {
		return aircraft;
	}

	public void setAircraft(Boolean aircraft) {
		this.aircraft = aircraft;
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
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}
	
	/**
	 * @return the incidentVo
	 */
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}
	
	/**
	 * @param incidentGroupVo the incidentGroupVo to set
	 */
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
	}
	
	/**
	 * @return the incidentGroupVo
	 */
	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}

	/**
	 * @param standardCost the standardCost to set
	 */
	public void setStandardCost(BigDecimal standardCost) {
		this.standardCost = standardCost;
	}

	/**
	 * @return the standardCost
	 */
	public BigDecimal getStandardCost() {
		return standardCost;
	}

}
