package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.GridColumnUser;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.GridColumnUserImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GridColumnUserVo extends AbstractVo implements PersistableVo {
	private Long gridColumnId;
	private GridColumnVo gridColumnVo;
	private Integer position;
	private Long userId;
	private UserVo userVo;
	private Boolean visible;
	
	public GridColumnUserVo(){
		super();
	}

    /**
     * Returns a GridColumnUserVo instance from a GridColumnUser entity.
     * 
     * @param entity
     * 			the source GridColumnUser entity
     * @param cascadable
     * 			flag indicating whether the instance should created as a cascadable vo
     * @return
     * 		instance of GridColumnUserVo
     * @throws Exception
     */
	public static GridColumnUserVo getInstance(GridColumnUser entity,boolean cascadable) throws Exception {
		GridColumnUserVo vo = new GridColumnUserVo();
		
    	if(null == entity)
    		throw new Exception("Unable to create GridColumnUserVo from null GridColumnUser entity.");

    	vo.setId(entity.getId());
		
		if(cascadable){
			
    	    /*
    	     * GridColumnUser <--> User (bidirectional relationship)
    	     * The relationship from gridColumUser to user
    	     * should not cascade (cascadable = false).
    	     */
			vo.setUserId(entity.getUserId());
    	    if(null!=entity.getUser()){
    	    	UserVo userVo = UserVo.getInstance(entity.getUser(),false);
    	    	vo.setUserVo(userVo);
    	    }
			
    	    /*
    	     * GridColumnUser <--> GridColumn (one way directional relationship)
    	     * The relationship from gridColumUser to GridColumn
    	     * should not cascade, however for this vo set cascadable to true
    	     * when getting an instance of gridcolumnvo so that the column 
    	     * definitions are available. (cascadable = true).
    	     */
			vo.setGridColumnId(entity.getGridColumnId());
    	    if(null!=entity.getGridColumn()){
    	    	vo.setGridColumnVo(GridColumnVo.getInstance(entity.getGridColumn(),true));
    	    }
			vo.setPosition(entity.getPosition());
			vo.setVisible(entity.isVisible());
		}
		
		return vo;
	}

    /**
     * Returns a collection of GridColumnUserVo instances from a collection of GridColumnUser entities.
     * 
     * @param entities
     * 			collection of GridColumnUser entities
     * @param cascadable
     * 			flag indicating whether the instances should created as a cascadable vo
     * @return
     * 		collection of GridColumnUserVos
     * @throws Exception
     */
	public static Collection<GridColumnUserVo> getInstances(Collection<GridColumnUser> entities,boolean cascadable) throws Exception {
		
		List<GridColumnUserVo> vos = new ArrayList<GridColumnUserVo>();
		
		for(GridColumnUser entity : entities){
			vos.add(GridColumnUserVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}

    /**
     * Returns a GridColumnUser entity instance from a GridColumnUserVo.
     * 
     * @param vo
     * 			source GridColumnUserVo
     * @param cascadable
     * 			flag indicating whether the instances should created as a cascadable vo
     * @param persistables
     * 			Optional array of referenced persistable entities 
     * @return
     * 		gridColumnUser entity
     * @throws Exception
     */
	public static GridColumnUser toEntity(GridColumnUserVo vo, boolean cascadable, Persistable... persistables) throws Exception {
		GridColumnUser entity = new GridColumnUserImpl();
			
		entity.setId(vo.getId());
			
		if(cascadable){
		
    	    /*
    	     * GridColumnUser --> GridColumn (one way directional relationship)
    	     * Set cascadable to false
    	     */
			entity.setGridColumn(GridColumnVo.toEntity(vo.getGridColumnVo(),false));

    	    /*
    	     * GridColumnUser --> User (one way directional relationship)
    	     * Set cascadable to false
    	     */
			User userImpl = new UserImpl();
			userImpl.setId(vo.getUserVo().getId());
			entity.setUser(userImpl);
			
			entity.setPosition(vo.getPosition());
			
			entity.setVisible(vo.getVisible());
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}
			
		return entity;
	}
	
    /**
     * Returns a collection of GridColumnUser entity instances from a collection of GridColumnUserVos.
     * 
     * @param vos
     * 			collection of source GridColumnUserVos
     * @param cascadable
     * 			flag indicating whether the instances should created as a cascadable entity
     * @param persistables
     * 			Optional array of referenced persistable entities 
     * @return
     * 		collection of gridColumnUser entities
     * @throws Exception
     */
	public static Collection<GridColumnUser> toEntityList(Collection<GridColumnUserVo> vos, boolean cascadable, Persistable... persistables) throws Exception {
		Collection<GridColumnUser> entities = new ArrayList<GridColumnUser>();

		for(GridColumnUserVo vo : vos){
			entities.add(GridColumnUserVo.toEntity(vo,cascadable,persistables));
		}
		
		return entities;
	}
	
	/**
	 * Perform some validation on the GridColumnUser entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source GridColumnUser entity
	 * @throws ValidationException
	 */
	private static void validateEntity(GridColumnUser entity) throws ValidationException {
		Validator.validateEntityField("gridColumn", entity.getGridColumn(), true);
		Validator.validateEntityField("user", entity.getUser(), true);
		Validator.validateIntegerField("position", entity.getPosition(), true);
		Validator.validateBooleanField("visible", entity.isVisible(), true);
	}

	/**
	 * Returns the gridColumnId.
	 *
	 * @return 
	 *		the gridColumnId to return
	 */
	public Long getGridColumnId() {
		return gridColumnId;
	}

	/**
	 * Sets the gridColumnId.
	 *
	 * @param gridColumnId 
	 *			the gridColumnId to set
	 */
	public void setGridColumnId(Long gridColumnId) {
		this.gridColumnId = gridColumnId;
	}

	/**
	 * Returns the gridColumnVo.
	 *
	 * @return 
	 *		the gridColumnVo to return
	 */
	public GridColumnVo getGridColumnVo() {
		return gridColumnVo;
	}

	/**
	 * Sets the gridColumnVo.
	 *
	 * @param gridColumnVo 
	 *			the gridColumnVo to set
	 */
	public void setGridColumnVo(GridColumnVo gridColumnVo) {
		this.gridColumnVo = gridColumnVo;
	}

	/**
	 * Returns the position.
	 *
	 * @return 
	 *		the position to return
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position 
	 *			the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * Returns the userId.
	 *
	 * @return 
	 *		the userId to return
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the userId.
	 *
	 * @param userId 
	 *			the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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

	/**
	 * Returns the visible.
	 *
	 * @return 
	 *		the visible to return
	 */
	public Boolean getVisible() {
		return visible;
	}

	/**
	 * Sets the visible.
	 *
	 * @param visible 
	 *			the visible to set
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}


}
