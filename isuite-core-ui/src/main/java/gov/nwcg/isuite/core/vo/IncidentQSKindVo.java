package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentQSKind;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQSKindImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.VoValidator;

import java.util.ArrayList;
import java.util.Collection;

public class IncidentQSKindVo extends AbstractVo implements PersistableVo {
	private Long incidentId;
	private KindVo kindVo;
	
	/**
	 * Default Constructor
	 */
	public IncidentQSKindVo() {
	}

	/**
	 * Returns a IncidentQSKindVo instance from a IncidentQSKind entity.
	 * 
	 * @param entity
	 *          the source IncidentQSKind entity
	 * @param cascadable
	 *          flag indicating whether the instance should created as a cascadable vo
	 * @return
	 *       instance of IncidentQSKindVo
	 * @throws Exception
	 */
	public static IncidentQSKindVo getInstance(IncidentQSKind entity, boolean cascadable) throws Exception {
		IncidentQSKindVo vo = new IncidentQSKindVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentQSKindVo from null IncidentQSKind entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			vo.setIncidentId(entity.getIncidentId());
			vo.setKindVo(KindVo.getInstance(entity.getKind(), true));
		}

		return vo;
	}

	/**
	 * Returns a Collection of IncidentQSKindVo instances from a Collection of IncidentQSKind entities.
	 * 
	 * @param entities
	 *          the source entities
	 * @param cascadable
	 *          flag indicating whether the instances should created as a cascadable Collection of vo's
	 * @return
	 *       Collection of IncidentQSKindVo objects
	 * @throws Exception
	 */
	public static Collection<IncidentQSKindVo> getInstances(Collection<IncidentQSKind> entities, boolean cascadable) throws Exception {
		Collection<IncidentQSKindVo> vos = new ArrayList<IncidentQSKindVo>();

		for(IncidentQSKind entity : entities){
			vos.add(IncidentQSKindVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * Returns a IncidentQSKind entity from a IncidentQSKindVo.
	 * 
	 * @param vo
	 *          the source IncidentQSKindVo
	 * @param cascadable
	 *          flag indicating whether the entity instance should created as a cascadable entity
	 * @param persistables
	 *          Optional array of referenced persistable entities 
	 * @return
	 *          IncidentQSKind entity
	 * @throws Exception
	 */
	public static IncidentQSKind toEntity(IncidentQSKind entity, IncidentQSKindVo vo,boolean cascadable,Persistable...persistables) throws Exception {
		if (null == entity) 
			entity = new IncidentQSKindImpl();

		entity.setId(vo.getId());

		if(cascadable){
			
			Incident incEntity = (Incident)getPersistableObject(persistables,IncidentImpl.class);
			if(null != incEntity){
				entity.setIncident(incEntity);
			}else{
				if(LongUtility.hasValue(vo.getIncidentId())){
					Incident iEntity = new IncidentImpl();
					iEntity.setId(vo.getIncidentId());
					entity.setIncident(iEntity);
				}
			}

			if(VoValidator.isValidAbstractVo(vo.getKindVo()))
				entity.setKind(KindVo.toEntity(null, vo.getKindVo(), false));
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	public static Collection<IncidentQSKind> toEntityList(Collection<IncidentQSKindVo> vos, Boolean cascadable, Persistable...persistables) throws Exception {
		Collection<IncidentQSKind> entities = new ArrayList<IncidentQSKind>();
		
		for(IncidentQSKindVo vo : vos){
			entities.add(IncidentQSKindVo.toEntity(null,vo,true,persistables));
		}
		
		return entities;
	}

	/**
	 * Perform some validation on the IncidentQSKind field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentQSKind entity) throws ValidationException {
	}

	/**
	 * Returns a collection of IncidentQSKinds that need to be
	 * removed based on the kindVos collection.
	 * 
	 * @param kindVos
	 * @param incidentQSKinds
	 * @return
	 */
	public static Collection<IncidentQSKind> toRemoveList(Collection<KindVo> kindVos, Collection<IncidentQSKind> incidentQSKinds) throws Exception{
		Collection<IncidentQSKind> removeList = new ArrayList<IncidentQSKind>();

		/*
		 * Return empty list if entity.list is empty,
		 * there is nothing to remove
		 */
		if(!CollectionUtility.hasValue(incidentQSKinds))
			return removeList;

		for(IncidentQSKind iQsKind : incidentQSKinds){
			Boolean found=false;

			for(KindVo kind : kindVos){
				if(iQsKind.getKindId().compareTo(kind.getId())==0){
					found=true;
					break;
				};
			}
			if(!found){
				removeList.add(iQsKind);
			}
		}
		
		return removeList;
	}

	/**
	 * @param newList
	 * @param incidentQSKinds
	 * @return
	 */
	public static Collection<IncidentQSKind> toEntityRemoveList(Collection<IncidentQSKindVo> newList, Collection<IncidentQSKind> incidentQSKinds){
		Collection<IncidentQSKind> removeList = new ArrayList<IncidentQSKind>();

		if(!CollectionUtility.hasValue(incidentQSKinds))
			return removeList;

		for(IncidentQSKind iQsKind : incidentQSKinds){
			Boolean found=false;

			for(IncidentQSKindVo newQsKindVo : newList){
				if(iQsKind.getKindId().compareTo(newQsKindVo.getKindVo().getId())==0){
					found=true;
					break;
				};
			}
			if(!found){
				removeList.add(iQsKind);
			}
		}

		return removeList;
	}

	/**
	 * Returns a collection of IncidentQSKinds that need to be
	 * added based on the kindVos collection.
	 * 
	 * 
	 * @param kindVos
	 * @param incidentQSKinds
	 * @return
	 */
	public static Collection<IncidentQSKind> toAddList(Collection<KindVo> kindVos
			, Collection<IncidentQSKind> incidentQSKinds
			, Incident entity) throws Exception {
		Collection<IncidentQSKind> addList = new ArrayList<IncidentQSKind>();

		if(!CollectionUtility.hasValue(incidentQSKinds)){
			/*
			 * Add all
			 */
			for(KindVo kind : kindVos){
				IncidentQSKind iQsKind = new IncidentQSKindImpl();
				iQsKind.setIncident(entity);
				iQsKind.setKind(KindVo.toEntity(null,kind,true));
				addList.add(iQsKind);
			}
			return addList;
		}

		for(KindVo kind : kindVos){
			Boolean found=false;

			for(IncidentQSKind iQsKind : incidentQSKinds){
				if(iQsKind.getKindId().compareTo(kind.getId())==0){
					found=true;
					break;
				};
			}

			if(!found){
				IncidentQSKind iQsKind = new IncidentQSKindImpl();
				iQsKind.setIncident(entity);
				iQsKind.setKind(KindVo.toEntity(null,kind,true));
				addList.add(iQsKind);
			}
		}

		return addList;
	}

	/**
	 * @param iQsKindVos
	 * @param incidentQSKinds
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static Collection<IncidentQSKind> toEntityAddList(Collection<IncidentQSKindVo> iQsKindVos, Collection<IncidentQSKind> incidentQSKinds, Incident entity) throws Exception {
		Collection<IncidentQSKind> addList = new ArrayList<IncidentQSKind>();

		if(!CollectionUtility.hasValue(incidentQSKinds)){
			/*
			 * Add all
			 */
			for(IncidentQSKindVo iQsKindVo : iQsKindVos){
				IncidentQSKind iQsKind = new IncidentQSKindImpl();
				iQsKind.setIncident(entity);
				iQsKind.setKind(KindVo.toEntity(null,iQsKindVo.getKindVo(),false));
				addList.add(iQsKind);
			}
			return addList;
		}

		for(IncidentQSKindVo iQsKindVo : iQsKindVos){
			Boolean found=false;

			for(IncidentQSKind iQsKind : incidentQSKinds){
				if(iQsKind.getKindId().compareTo(iQsKindVo.getKindVo().getId())==0){
					found=true;
					break;
				};
			}

			if(!found){
				IncidentQSKind iQsKind = new IncidentQSKindImpl();
				iQsKind.setIncident(entity);
				iQsKind.setKind(KindVo.toEntity(null,iQsKindVo.getKindVo(),false));
				addList.add(iQsKind);
			}
		}

		return addList;
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
	 * @return the kindVo
	 */
	public KindVo getKindVo() {
		return kindVo;
	}

	/**
	 * @param kindVo the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}

}
