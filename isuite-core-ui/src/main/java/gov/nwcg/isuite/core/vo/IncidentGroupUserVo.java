package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupQSKind;
import gov.nwcg.isuite.core.domain.IncidentGroupUser;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupQSKindImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupUserImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;


public class IncidentGroupUserVo extends AbstractVo implements PersistableVo {
	private IncidentGroupVo incidentGroupVo;
	private UserVo userVo;
	
	/**
	 * Default constructor
	 */
	public IncidentGroupUserVo() {
	}

	public static IncidentGroupUserVo getInstance(IncidentGroupUser entity,boolean cascadable) throws Exception{
      IncidentGroupUserVo vo = new IncidentGroupUserVo();

      if(null == entity)
         throw new Exception("Unable to create IncidentGroupUserVo from null IncidentGroupUser entity.");

      vo.setId(entity.getId());

      /*
       * Only populate fields outside of the entity Id if needed
       */
      if(cascadable){
         vo.setUserLoginName(entity.getUser().getLoginName());
         vo.setUserVo(UserVo.getInstance(entity.getUser(), true));
      }

      return vo;
   }
	
	public static Collection<IncidentGroupUserVo> getInstances(Collection<IncidentGroupUser> entities, boolean cascadable) throws Exception {
      Collection<IncidentGroupUserVo> vos = new ArrayList<IncidentGroupUserVo>();

      for(IncidentGroupUser entity : entities){
         vos.add(IncidentGroupUserVo.getInstance(entity, cascadable));
      }

      return vos;
   }
	
	
	public static IncidentGroupUser toEntity(IncidentGroupUser entity,IncidentGroupUserVo vo, boolean cascadable, Persistable... persistables) throws Exception {
		if(null==entity)entity = new IncidentGroupUserImpl();
		
		entity.setId(vo.getId());

		if(cascadable){

			IncidentGroup igEntity = (IncidentGroup)AbstractVo.getPersistableObject(persistables,IncidentGroupImpl.class);
			if(null != igEntity)
				entity.setIncidentGroup(igEntity);
			
			if(null != vo.getUserVo()){
				entity.setUser(UserVo.toEntity(null, vo.getUserVo(), false));
			}
		}

		return entity;
	}

	public static Collection<IncidentGroupUser> toEntityList(Collection<IncidentGroupUserVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IncidentGroupUser> entities = new ArrayList<IncidentGroupUser>();
		
		for(IncidentGroupUserVo vo : vos){
			entities.add(IncidentGroupUserVo.toEntity(null, vo, cascadable,persistables));
		}
		
		return entities;
	}
	
	public static Collection<IncidentGroupUser> toEntityRemoveList(Collection<IncidentGroupUserVo> newList, Collection<IncidentGroupUser> incidentGroupUsers){
		Collection<IncidentGroupUser> removeList = new ArrayList<IncidentGroupUser>();

		if(!CollectionUtility.hasValue(incidentGroupUsers))
			return removeList;

		for(IncidentGroupUser igu : incidentGroupUsers){
			Boolean found=false;

			for(IncidentGroupUserVo newIguVo : newList){
				if(igu.getUserId().compareTo(newIguVo.getUserVo().getId())==0){
					found=true;
					break;
				};
			}
			if(!found){
				removeList.add(igu);
			}
		}

		return removeList;
	}

	public static Collection<IncidentGroupUser> toEntityAddList(Collection<IncidentGroupUserVo> iguVos
			, Collection<IncidentGroupUser> incidentGroupUsers
			, IncidentGroup entity) throws Exception {
		
		Collection<IncidentGroupUser> addList = new ArrayList<IncidentGroupUser>();

		if(!CollectionUtility.hasValue(incidentGroupUsers)){
			/*
			 * Add all
			 */
			for(IncidentGroupUserVo iguVo : iguVos){
				IncidentGroupUser igu = new IncidentGroupUserImpl();
				igu.setIncidentGroup(entity);
				igu.setUser(UserVo.toEntity(null,iguVo.getUserVo(),false));
				addList.add(igu);
			}
			return addList;
		}

		for(IncidentGroupUserVo iguVo : iguVos){
			Boolean found=false;

			for(IncidentGroupUser igu : incidentGroupUsers){
				if(igu.getUserId().compareTo(iguVo.getUserVo().getId())==0){
					found=true;
					break;
				};
			}

			if(!found){
				addList.add(IncidentGroupUserVo.toEntity(null, iguVo, true, entity));
			}
		}

		return addList;
	}
	
	/**
	 * Returns the incidentGroupVo.
	 *
	 * @return 
	 *		the incidentGroupVo to return
	 */
	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}

	/**
	 * Sets the incidentGroupVo.
	 *
	 * @param incidentGroupVo 
	 *			the incidentGroupVo to set
	 */
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
	}

	/**
	 * Returns the userVo.
	 *
	 * @return 
	 *		the userVo to return
	 */
	public UserVo getUserVo() {
		return userVo;
	}

	/**
	 * Sets the userVo.
	 *
	 * @param userVo 
	 *			the userVo to set
	 */
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
}
