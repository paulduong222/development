package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.AccountCode;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.impl.AccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.builder.EqualsBuilder;

public class AccountCodeVo extends AbstractVo implements PersistableVo {
	private String accountCode;
	private AgencyVo agencyVo = new AgencyVo();
	private RegionCodeVo regionUnitVo = new RegionCodeVo();

	public AccountCodeVo() {
	}

	/**
	 * Returns a AccountCodeVo instance from an AccountCode entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of AccountCodeVo
	 * @throws Exception
	 */
	public static AccountCodeVo getInstance(AccountCode entity,boolean cascadable) throws Exception {
		AccountCodeVo vo = new AccountCodeVo();

		if(null == entity)
			throw new Exception("Unable to create AccountCodeVo from null AccountCode entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setAccountCode(entity.getAccountCode());
			
			if(null != entity.getAgency())
				vo.setAgencyVo(AgencyVo.getInstance(entity.getAgency(), true));
			
			if(null != entity.getRegionUnit())
				vo.setRegionUnitVo(RegionCodeVo.getInstance(entity.getRegionUnit(),true));
			
		}

		return vo;
	}

	public static Collection<AccountCodeVo> getInstances(Collection<AccountCode> entities, boolean cascadable) throws Exception {
		Collection<AccountCodeVo> vos = new ArrayList<AccountCodeVo>();

		for(AccountCode entity : entities){
			vos.add(AccountCodeVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * Returns a AccountCode entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of AccountCode entity
	 * @throws Exception
	 */
	public static AccountCode toEntity(AccountCode entity, AccountCodeVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity){
			entity=new AccountCodeImpl();
			entity.setId(vo.getId());
		}
		
		
		if(cascadable){
			/*
			 * Get IncidentAccountCodes in the persistables array if available.
			 */
			IncidentAccountCode iacEntity = (IncidentAccountCode)getPersistableObject(persistables,IncidentAccountCodeImpl.class);
			if(null != iacEntity){
				if(null != entity.getIncidentAccountCodes()){
					entity.getIncidentAccountCodes().add(iacEntity);
				}else{
					entity.setIncidentAccountCodes(new ArrayList<IncidentAccountCode>());
					entity.getIncidentAccountCodes().add(iacEntity);
				}
			}
			
			entity.setAccountCode(vo.getAccountCode().toUpperCase());
			
			if(null != vo.getAgencyVo())
				entity.setAgency(AgencyVo.toEntity(null, vo.getAgencyVo(), false));
			
			if( (null != vo.getRegionUnitVo() && null != vo.getRegionUnitVo().getId()) && (vo.getRegionUnitVo().getId()>0))
				entity.setRegionUnit(RegionCodeVo.toEntity(null, vo.getRegionUnitVo(), false));
			
			/*
			 * Validate the entity
			 */
			 validateEntity(entity);

		}

		return entity;
	}

	/**
	 * Perform some validation on the incident entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source AccountCode entity
	 * @throws ValidationException
	 */
	private static void validateEntity(AccountCode entity) throws ValidationException {
		Validator.validateStringField("accountCode", entity.getAccountCode(), 50, true);
		Validator.validateEntityField("agency", entity.getAgency(), true);
	}

	/**
	 * Returns the accountCode.
	 *
	 * @return 
	 *		the accountCode to return
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * Sets the accountCode.
	 *
	 * @param accountCode 
	 *			the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * Returns the agencyVo.
	 *
	 * @return 
	 *		the agencyVo to return
	 */
	public AgencyVo getAgencyVo() {
		return agencyVo;
	}

	/**
	 * Sets the agencyVo.
	 *
	 * @param agencyVo 
	 *			the agencyVo to set
	 */
	public void setAgencyVo(AgencyVo agencyVo) {
		this.agencyVo = agencyVo;
	}

	/**
	 * Returns the regionUnitVo.
	 *
	 * @return 
	 *		the regionUnitVo to return
	 */
	public RegionCodeVo getRegionUnitVo() {
		return regionUnitVo;
	}

	/**
	 * Sets the regionUnitVo.
	 *
	 * @param regionUnitVo 
	 *			the regionUnitVo to set
	 */
	public void setRegionUnitVo(RegionCodeVo regionUnitVo) {
		this.regionUnitVo = regionUnitVo;
	}

	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		AccountCodeVo o = (AccountCodeVo)obj;
		return new EqualsBuilder()
	      	.append(new Object[]{id,accountCode,agencyVo.getId(),(null!=getRegionUnitVo()?getRegionUnitVo().getId():null)},
	      			new Object[]{o.id,o.accountCode,o.getAgencyVo().getId(),(null!=o.getRegionUnitVo()?o.getRegionUnitVo().getId():null)})
	      	.isEquals();
    }   


}
