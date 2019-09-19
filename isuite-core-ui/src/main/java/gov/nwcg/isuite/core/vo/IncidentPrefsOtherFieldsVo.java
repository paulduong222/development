package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentPrefsOtherFields;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentPrefsOtherFieldsImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

public class IncidentPrefsOtherFieldsVo extends AbstractVo implements PersistableVo {
//	private IncidentVo incidentVo;
	private Long incidentId;
	private Long incidentGroupId;
	private String other1Label;
	private String other2Label;
	private String other3Label;

	/**
	 * Default Constructor
	 */
	public IncidentPrefsOtherFieldsVo() {
	}

	/**
	 * Returns a IncidentPrefsOtherFieldsVo instance from a IncidentPrefsOtherFields entity.
	 * 
	 * @param entity
	 *          the source IncidentPrefsOtherFields entity
	 * @param cascadable
	 *          flag indicating whether the instance should created as a cascadable vo
	 * @return
	 *       instance of IncidentPrefsOtherFieldsVo
	 * @throws Exception
	 */
	public static IncidentPrefsOtherFieldsVo getInstance(IncidentPrefsOtherFields entity, boolean cascadable) throws Exception {
		IncidentPrefsOtherFieldsVo vo = new IncidentPrefsOtherFieldsVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentPrefsOtherFieldsVo from null IncidentPrefsOtherFields entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			if(LongUtility.hasValue(entity.getIncidentId()) || (null != entity.getIncident() && LongUtility.hasValue(entity.getIncident().getId()) ))
				vo.setIncidentId(entity.getIncidentId());
			if(LongUtility.hasValue(entity.getIncidentGroupId()) || (null != entity.getIncidentGroup() && LongUtility.hasValue(entity.getIncidentGroup().getId()) ))
				vo.setIncidentGroupId(entity.getIncidentGroupId());
			
			vo.setOther1Label(StringUtility.toUpper(entity.getOther1Label()));
			vo.setOther2Label(StringUtility.toUpper(entity.getOther2Label()));
			vo.setOther3Label(StringUtility.toUpper(entity.getOther3Label()));
		}

		return vo;
	}

	/**
	 * Returns a IncidentPrefsOtherFields entity from a IncidentPrefsOtherFieldsVo.
	 * 
	 * @param vo
	 *          the source IncidentPrefsOtherFieldsVo
	 * @param cascadable
	 *          flag indicating whether the entity instance should created as a cascadable entity
	 * @param persistables
	 *          Optional array of referenced persistable entities 
	 * @return
	 *          IncidentPrefsOtherFields entity
	 * @throws Exception
	 */
	public static IncidentPrefsOtherFields toEntity(IncidentPrefsOtherFields entity, IncidentPrefsOtherFieldsVo vo,boolean cascadable,Persistable...persistables) throws Exception {
		if (null == entity) {
			entity = new IncidentPrefsOtherFieldsImpl();
		}

		if(!LongUtility.hasValue(entity.getId())){
			entity.setId(vo.getId());
		}

		if(cascadable){
			entity.setOther1Label(vo.getOther1Label());
			entity.setOther2Label(vo.getOther2Label());
			entity.setOther3Label(vo.getOther3Label());
			
			Incident incEntity = (Incident)getPersistableObject(persistables, IncidentImpl.class);
			if(null != incEntity){
				entity.setIncident(incEntity);
			} else {
				if(LongUtility.hasValue(vo.getIncidentId())) {
					incEntity = new IncidentImpl();
					incEntity.setId(vo.getIncidentId());
					entity.setIncident(incEntity);
				}
			}
			
			IncidentGroup incGroupEntity = (IncidentGroup)getPersistableObject(persistables, IncidentGroupImpl.class);
			if(null != incGroupEntity){
				entity.setIncidentGroup(incGroupEntity);
			} else {
				if(LongUtility.hasValue(vo.getIncidentGroupId())) {
					incGroupEntity = new IncidentGroupImpl();
					incGroupEntity.setId(vo.getIncidentGroupId());
					entity.setIncidentGroup(incGroupEntity);
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
	 * Perform some validation on the IncidentPrefsOtherFields field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentPrefsOtherFields entity) throws ValidationException {
		Validator.validateStringField("other1Label", entity.getOther1Label(), 60, false);
		Validator.validateStringField("other2Label", entity.getOther2Label(), 60, false);
		Validator.validateStringField("other3Label", entity.getOther3Label(), 60, false);
	}

//	/**
//	 * @return the incidentVo
//	 */
//	public IncidentVo getIncidentVo() {
//		return incidentVo;
//	}
//
//	/**
//	 * @param incidentVo the incidentVo to set
//	 */
//	public void setIncidentVo(IncidentVo incidentVo) {
//		this.incidentVo = incidentVo;
//	}

	/**
	 * @return the other1Label
	 */
	public String getOther1Label() {
		return other1Label;
	}

	/**
	 * @param other1Label the other1Label to set
	 */
	public void setOther1Label(String other1Label) {
		this.other1Label = other1Label;
	}

	/**
	 * @return the other2Label
	 */
	public String getOther2Label() {
		return other2Label;
	}

	/**
	 * @param other2Label the other2Label to set
	 */
	public void setOther2Label(String other2Label) {
		this.other2Label = other2Label;
	}

	/**
	 * @return the other3Label
	 */
	public String getOther3Label() {
		return other3Label;
	}

	/**
	 * @param other3Label the other3Label to set
	 */
	public void setOther3Label(String other3Label) {
		this.other3Label = other3Label;
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

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}


}
