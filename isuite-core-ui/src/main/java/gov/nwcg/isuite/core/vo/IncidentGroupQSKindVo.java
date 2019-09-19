package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupQSKind;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupQSKindImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.VoValidator;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("unchecked")
public class IncidentGroupQSKindVo extends AbstractVo implements PersistableVo {
	private Long incidentGroupId;
	private KindVo kindVo;

	/**
	 * Default Constructor
	 */
	public IncidentGroupQSKindVo() {
	}

	/**
	 * Returns a IncidentGroupQSKindVo instance from a IncidentGroupQSKind entity.
	 * 
	 * @param entity
	 *          the source IncidentGroupQSKind entity
	 * @param cascadable
	 *          flag indicating whether the instance should created as a cascadable vo
	 * @return
	 *       instance of IncidentGroupQSKindVo
	 * @throws Exception
	 */
	public static IncidentGroupQSKindVo getInstance(IncidentGroupQSKind entity, boolean cascadable) throws Exception {
		IncidentGroupQSKindVo vo = new IncidentGroupQSKindVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentGroupQSKindVo from null IncidentGroupQSKind entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			vo.setIncidentGroupId(entity.getIncidentGroupId());
			vo.setKindVo(KindVo.getInstance(entity.getKind(), true));
		}

		return vo;
	}

	/**
	 * Returns a Collection of IncidentGroupQSKindVo instances from a Collection of IncidentGroupQSKind entities.
	 * 
	 * @param entities
	 *          the source entities
	 * @param cascadable
	 *          flag indicating whether the instances should created as a cascadable Collection of vo's
	 * @return
	 *       Collection of IncidentGroupQSKindVo objects
	 * @throws Exception
	 */
	public static Collection<IncidentGroupQSKindVo> getInstances(Collection<IncidentGroupQSKind> entities, boolean cascadable) throws Exception {
		Collection<IncidentGroupQSKindVo> vos = new ArrayList<IncidentGroupQSKindVo>();

		for(IncidentGroupQSKind entity : entities){
			vos.add(IncidentGroupQSKindVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * Returns a IncidentGroupQSKind entity from a IncidentGroupQSKindVo.
	 * 
	 * @param vo
	 *          the source IncidentGroupQSKindVo
	 * @param cascadable
	 *          flag indicating whether the entity instance should created as a cascadable entity
	 * @param persistables
	 *          Optional array of referenced persistable entities 
	 * @return
	 *          IncidentGroupQSKind entity
	 * @throws Exception
	 */
	public static IncidentGroupQSKind toEntity(IncidentGroupQSKind entity, IncidentGroupQSKindVo vo,boolean cascadable,Persistable...persistables) throws Exception {
		if (null == entity) 
			entity = new IncidentGroupQSKindImpl();

		entity.setId(vo.getId());

		if(cascadable){

			IncidentGroup igEntity = (IncidentGroup)getPersistableObject(persistables,IncidentGroupImpl.class);
			if(null != igEntity){
				entity.setIncidentGroup(igEntity);
			}else{
				if(LongUtility.hasValue(vo.getIncidentGroupId())){
					IncidentGroup ig2Entity = new IncidentGroupImpl();
					ig2Entity.setId(vo.getIncidentGroupId());
					entity.setIncidentGroup(ig2Entity);
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

	public static Collection<IncidentGroupQSKind> toEntityList(Collection<IncidentGroupQSKindVo> vos, Boolean cascadable, Persistable...persistables) throws Exception {
		Collection<IncidentGroupQSKind> entities = new ArrayList<IncidentGroupQSKind>();

		for(IncidentGroupQSKindVo vo : vos){
			entities.add(IncidentGroupQSKindVo.toEntity(null,vo,true,persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the IncidentGroupQSKind field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentGroupQSKind entity) throws ValidationException {
	}

	/**
	 * Returns a collection of IncidentGroupQSKinds that need to be
	 * removed based on the kindVos collection.
	 * 
	 * @param kindVos
	 * @param incidentGroupQSKinds
	 * @return
	 */
	public static Collection<IncidentGroupQSKind> toRemoveList(Collection<KindVo> kindVos, Collection<IncidentGroupQSKind> incidentGroupQSKinds) throws Exception{
		Collection<IncidentGroupQSKind> removeList = new ArrayList<IncidentGroupQSKind>();

		/*
		 * Return empty list if entity.list is empty,
		 * there is nothing to remove
		 */
		if(!CollectionUtility.hasValue(incidentGroupQSKinds))
			return removeList;

		for(IncidentGroupQSKind igQsKind : incidentGroupQSKinds){
			Boolean found=false;

			for(KindVo kind : kindVos){
				if(igQsKind.getKindId().compareTo(kind.getId())==0){
					found=true;
					break;
				};
			}
			if(!found){
				removeList.add(igQsKind);
			}
		}
		
		return removeList;
	}

	/**
	 * @param newList
	 * @param incidentGroupQSKinds
	 * @return
	 */
	public static Collection<IncidentGroupQSKind> toEntityRemoveList(Collection<IncidentGroupQSKindVo> newList, Collection<IncidentGroupQSKind> incidentGroupQSKinds){
		Collection<IncidentGroupQSKind> removeList = new ArrayList<IncidentGroupQSKind>();

		if(!CollectionUtility.hasValue(incidentGroupQSKinds))
			return removeList;

		for(IncidentGroupQSKind igQsKind : incidentGroupQSKinds){
			Boolean found=false;

			for(IncidentGroupQSKindVo newQsKindVo : newList){
				if(igQsKind.getKindId().compareTo(newQsKindVo.getKindVo().getId())==0){
					found=true;
					break;
				};
			}
			if(!found){
				removeList.add(igQsKind);
			}
		}

		return removeList;
	}

	/**
	 * Returns a collection of IncidentGroupQSKinds that need to be
	 * added based on the kindVos collection.
	 * 
	 * 
	 * @param kindVos
	 * @param incidentGroupQSKinds
	 * @return
	 */
	public static Collection<IncidentGroupQSKind> toAddList(Collection<KindVo> kindVos
			, Collection<IncidentGroupQSKind> incidentGroupQSKinds
			, IncidentGroup entity) throws Exception {
		Collection<IncidentGroupQSKind> addList = new ArrayList<IncidentGroupQSKind>();

		if(!CollectionUtility.hasValue(incidentGroupQSKinds)){
			/*
			 * Add all
			 */
			for(KindVo kind : kindVos){
				IncidentGroupQSKind igQsKind = new IncidentGroupQSKindImpl();
				igQsKind.setIncidentGroup(entity);
				igQsKind.setKind(KindVo.toEntity(null,kind,false));
				addList.add(igQsKind);
			}
			return addList;
		}

		for(KindVo kind : kindVos){
			Boolean found=false;

			for(IncidentGroupQSKind igQsKind : incidentGroupQSKinds){
				if(igQsKind.getKindId().compareTo(kind.getId())==0){
					found=true;
					break;
				};
			}

			if(!found){
				IncidentGroupQSKind igQsKind = new IncidentGroupQSKindImpl();
				igQsKind.setIncidentGroup(entity);
				igQsKind.setKind(KindVo.toEntity(null,kind,false));
				addList.add(igQsKind);
			}
		}

		return addList;
	}

	/**
	 * @param igQsKindVos
	 * @param incidentGroupQSKinds
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static Collection<IncidentGroupQSKind> toEntityAddList(Collection<IncidentGroupQSKindVo> igQsKindVos, Collection<IncidentGroupQSKind> incidentGroupQSKinds, IncidentGroup entity) throws Exception {
		Collection<IncidentGroupQSKind> addList = new ArrayList<IncidentGroupQSKind>();

		if(!CollectionUtility.hasValue(incidentGroupQSKinds)){
			/*
			 * Add all
			 */
			for(IncidentGroupQSKindVo igQsKindVo : igQsKindVos){
				IncidentGroupQSKind igQsKind = new IncidentGroupQSKindImpl();
				igQsKind.setIncidentGroup(entity);
				igQsKind.setKind(KindVo.toEntity(null,igQsKindVo.getKindVo(),false));
				addList.add(igQsKind);
			}
			return addList;
		}

		for(IncidentGroupQSKindVo igQsKindVo : igQsKindVos){
			Boolean found=false;

			for(IncidentGroupQSKind igQsKind : incidentGroupQSKinds){
				if(igQsKind.getKindId().compareTo(igQsKindVo.getKindVo().getId())==0){
					found=true;
					break;
				};
			}

			if(!found){
				IncidentGroupQSKind igQsKind = new IncidentGroupQSKindImpl();
				igQsKind.setIncidentGroup(entity);
				igQsKind.setKind(KindVo.toEntity(null,igQsKindVo.getKindVo(),false));
				addList.add(igQsKind);
			}
		}

		return addList;
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
