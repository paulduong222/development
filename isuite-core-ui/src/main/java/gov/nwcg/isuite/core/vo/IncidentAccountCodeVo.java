package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IncidentAccountCodeVo extends AbstractVo implements PersistableVo {
	private AccountCodeVo accountCodeVo = new AccountCodeVo();
	private Long incidentId;
	private IncidentVo incidentVo;
	private AccountCodeVo overrideAccountCodeVo;
	private Boolean defaultFlag;
	private Boolean deletable;
	private String accrualAccountCode;
	private IncidentAccountCodeVo accrualIncidentAccountCodeVo;
	
	/**
	 * Constructor
	 */
	public IncidentAccountCodeVo() {}

	/**
	 * Returns a IncidentAccountCodeVo instance from an IncidentAccountCode entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of IncidentAccountCodeVo
	 * @throws Exception
	 */
	public static IncidentAccountCodeVo getInstance(IncidentAccountCode entity,boolean cascadable) throws Exception {
		IncidentAccountCodeVo vo = new IncidentAccountCodeVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentAccountCodeVo from null IncidentAccountCode entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setDefaultFlag(entity.getDefaultFlag());
		
			if(null != entity.getAccountCode())
				vo.setAccountCodeVo(AccountCodeVo.getInstance(entity.getAccountCode(), true));
			
			if(null != entity.getOverrideAccountCode())
				vo.setOverrideAccountCodeVo(AccountCodeVo.getInstance(entity.getOverrideAccountCode(),true));

			if(StringUtility.hasValue(entity.getAccrualAccountCode())){
				if(null != entity.getIncident()){
					try{
						if(CollectionUtility.hasValue(entity.getIncident().getIncidentAccountCodes())){
	
							for(IncidentAccountCode iac : entity.getIncident().getIncidentAccountCodes()){
								if(iac.getAccountCode().getAccountCode().equals(entity.getAccrualAccountCode())){
									// build manually to avoid neverending loop
									IncidentAccountCodeVo iacVo = new IncidentAccountCodeVo();
									iacVo.setId(iac.getId());
									iacVo.setAccountCodeVo(AccountCodeVo.getInstance(iac.getAccountCode(), true));
									vo.setAccrualIncidentAccountCodeVo(iacVo);
									break;
								}
							}
						}
					}catch(Exception ee){
						// for some reason line 64 will error when building this vo for a incident resource
						// smother the error
						//System.out.println(ee.getMessage());
					}
				}
			}else{
			}
			
			vo.setIncidentId(entity.getIncidentId());
			if(null != entity.getIncident()){
				IncidentVo ivo = new IncidentVo();
				ivo.setId(entity.getIncident().getId());
				ivo.setIncidentName(entity.getIncident().getIncidentName());
				ivo.setIncidentNumber(entity.getIncident().getIncidentNumber());
				if(null != entity.getIncident() && null != entity.getIncident().getHomeUnit())
					ivo.setHomeUnitVo(OrganizationVo.getInstance(entity.getIncident().getHomeUnit(), true));
				vo.setIncidentVo(ivo);
			}
		}else{
			// build for convenience
			vo.setIncidentId(entity.getIncidentId());
			if(null != entity.getIncident()){
				IncidentVo ivo = new IncidentVo();
				ivo.setId(entity.getIncident().getId());
				ivo.setIncidentName(entity.getIncident().getIncidentName());
				vo.setIncidentVo(ivo);
			}
		}
		
		return vo;
	}

	public static Collection<IncidentAccountCodeVo> getInstances(Collection<IncidentAccountCode> entities, boolean cascadable) throws Exception {
		Collection<IncidentAccountCodeVo> vos = new ArrayList<IncidentAccountCodeVo>();

		for(IncidentAccountCode entity : entities){
			vos.add(IncidentAccountCodeVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}

	/**
	 * Returns a IncidentAccountCode entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of IncidentAccountCode entity
	 * @throws Exception
	 */
	public static IncidentAccountCode toEntity(IncidentAccountCode entity, IncidentAccountCodeVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity)
			entity=new IncidentAccountCodeImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setDefaultFlag(vo.getDefaultFlag());
			
			if( (null != vo.getAccountCodeVo()) && (LongUtility.hasValue(vo.getAccountCodeVo().getId())) )
				entity.setAccountCode(AccountCodeVo.toEntity(null, vo.getAccountCodeVo(), true));
			else if( (null != vo.getAccountCodeVo()) )
				entity.setAccountCode(AccountCodeVo.toEntity(null, vo.getAccountCodeVo(), true,entity));
				
			if(null != vo.getOverrideAccountCodeVo())
				entity.setOverrideAccountCode(AccountCodeVo.toEntity(null, vo.getOverrideAccountCodeVo(), true));

			if(null != vo.getAccrualIncidentAccountCodeVo()){
				entity.setAccrualAccountCode(vo.getAccrualIncidentAccountCodeVo().getAccountCodeVo().getAccountCode());
			}else
				entity.setAccrualAccountCode(vo.getAccountCodeVo().getAccountCode());
			
			/*
			 * Get incident in the persistables array if available.
			 */
			Incident incidentEntity = (Incident)getPersistableObject(persistables,IncidentImpl.class);
			if(null != incidentEntity)
				entity.setIncident(incidentEntity);
			else{
				if(LongUtility.hasValue(vo.getIncidentId())){
					Incident incident = new IncidentImpl();
					incident.setId(vo.getIncidentId());
					entity.setIncident(incident);
				}
			}
			
			/*
			 * Validate the entity
			 */
			 validateEntity(entity);

		}

		return entity;
	}

    /**
     * Returns a collection of IncidentAccountCode entities from a collection of IncidentAccountCode vos.
     * 
     * @param vo
     * 			the source collection of vos
     * @param cascadable
     * 			flag indicating whether the entity instances should created as a cascadable entities
     * @param persistables
     * 			Optional array of referenced persistable entities 
     * @return
     * 			collection of IncidentAccountCode entities
     * @throws Exception
     */
	public static Collection<IncidentAccountCode> toEntityList(Collection<IncidentAccountCodeVo> vos, boolean cascadable, Persistable... persistables) throws Exception {
		Collection<IncidentAccountCode> entities = new ArrayList<IncidentAccountCode>();
		
		for(IncidentAccountCodeVo vo : vos){
			entities.add(IncidentAccountCodeVo.toEntity(null,vo,cascadable,persistables));
		}
		
		return entities;
	}
	
	/**
	 * Perform some validation on the incident entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source IncidentAccountCode entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentAccountCode entity) throws ValidationException {
	}

	
	/**
	 * Returns the accountCodeVo.
	 *
	 * @return 
	 *		the accountCodeVo to return
	 */
	public AccountCodeVo getAccountCodeVo() {
		return accountCodeVo;
	}

	/**
	 * Sets the accountCodeVo.
	 *
	 * @param accountCodeVo 
	 *			the accountCodeVo to set
	 */
	public void setAccountCodeVo(AccountCodeVo accountCodeVo) {
		this.accountCodeVo = accountCodeVo;
	}

	/**
	 * Returns the incidentVo.
	 *
	 * @return 
	 *		the incidentVo to return
	 */
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}

	/**
	 * Sets the incidentVo.
	 *
	 * @param incidentVo 
	 *			the incidentVo to set
	 */
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}

	/**
	 * Returns the overrideAccountCodeVo.
	 *
	 * @return 
	 *		the overrideAccountCodeVo to return
	 */
	public AccountCodeVo getOverrideAccountCodeVo() {
		return overrideAccountCodeVo;
	}

	/**
	 * Sets the overrideAccountCodeVo.
	 *
	 * @param overrideAccountCodeVo 
	 *			the overrideAccountCodeVo to set
	 */
	public void setOverrideAccountCodeVo(AccountCodeVo overrideAccountCodeVo) {
		this.overrideAccountCodeVo = overrideAccountCodeVo;
	}

	/**
	 * Returns the defaultFlag.
	 *
	 * @return 
	 *		the defaultFlag to return
	 */
	public Boolean getDefaultFlag() {
		return defaultFlag;
	}

	/**
	 * Sets the defaultFlag.
	 *
	 * @param defaultFlag 
	 *			the defaultFlag to set
	 */
	public void setDefaultFlag(Boolean defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

   /**
    * @return the deletable
    */
   public Boolean getDeletable() {
      return deletable;
   }

   /**
    * @param deletable the deletable to set
    */
   public void setDeletable(Boolean deletable) {
      this.deletable = deletable;
   }

/**
 * @return the incidentId
 */
public Long getIncidentId() {
	return incidentId;
}

/**
 * @param incidentId the incidentId to set
 */
public void setIncidentId(Long incidentId) {
	this.incidentId = incidentId;
}

public IncidentAccountCodeVo getAccrualIncidentAccountCodeVo() {
	return accrualIncidentAccountCodeVo;
}

public void setAccrualIncidentAccountCodeVo(
		IncidentAccountCodeVo accrualIncidentAccountCodeVo) {
	this.accrualIncidentAccountCodeVo = accrualIncidentAccountCodeVo;
}

public String getAccrualAccountCode() {
	return accrualAccountCode;
}

public void setAccrualAccountCode(String accrualAccountCode) {
	this.accrualAccountCode = accrualAccountCode;
}

}
