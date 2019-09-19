package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Message;
import gov.nwcg.isuite.core.domain.impl.MessageImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.DateUtil;

public class MessageBoardVo extends AbstractVo implements PersistableVo {
	private String type;
	private DateTransferVo effectiveDateTransferVo = new DateTransferVo();
	private DateTransferVo expireDateTransferVo = new DateTransferVo();
	private String status;
	private String messageText;
	private String updatedBy;
	private DateTransferVo updatedDateTransferVo = new DateTransferVo();
	
	public MessageBoardVo(){
		
	}
	
	/**
	 * Returns a MessageBoardVo from a Message entity.
	 * 
	 * @param entity
	 * 			the source Message entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of MessageBoardVo
	 * @throws Exception
	 */
	public static MessageBoardVo getInstance(Message entity, boolean cascadable) throws Exception {
		MessageBoardVo vo = new MessageBoardVo();
		
		if(null != entity) {
			vo.setId(entity.getId());
			
			if(cascadable){
				vo.setType(entity.getType());
				
				if(DateUtil.hasValue(entity.getEffectiveDate()))
					DateTransferVo.populateDate(vo.getEffectiveDateTransferVo(), entity.getEffectiveDate());
				
				if(DateUtil.hasValue(entity.getExpireDate()))
					DateTransferVo.populateDate(vo.getExpireDateTransferVo(), entity.getExpireDate());
				
				vo.setStatus(entity.getStatus());
				vo.setMessageText(entity.getMessageText());
				
				vo.setCreatedBy(entity.getCreatedBy());
				vo.setCreatedDate(entity.getCreatedDate());
				
				if(null != entity.getLastModifiedBy()){
					vo.setUpdatedBy(entity.getLastModifiedBy());
				}else {
					vo.setUpdatedBy("SYSTEM");
				}
				
				if(DateUtil.hasValue(entity.getLastModifiedDate()))
					DateTransferVo.populateDate(vo.getUpdatedDateTransferVo() , entity.getLastModifiedDate());
				
			}
		}
		
		return vo;
	}
	
	/**
	 * Returns a collection of MessageBoardVos from a collection of Message entities.
	 * 
	 * @param entities
	 * 			the source collection of Message entities
	 * @param cascadable
	 * 			flag indication whether the vo instances should be created as cascadable vos
	 * @return
	 * 			collection of MessageBoard vos
	 * @throws Exception
	 */
	public static Collection<MessageBoardVo> getInstances(Collection<Message> entities, boolean cascadable) throws Exception {
		Collection<MessageBoardVo> vos = new ArrayList<MessageBoardVo>();
		
		for(Message entity : entities){
			vos.add(MessageBoardVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	/**
	 * Returns a MessageBoard entity from a MessageBoard vo.
	 * 
	 * @param entity
	 * @param vo
	 * 			the source MessageBoard vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should be ceated as a cascadable entity
	 * @param persistables
	 * @return
	 * 			instance of MessageBoard entity
	 * @throws Exception
	 */
	public static Message toEntity(Message entity, MessageBoardVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		
		if(null == entity) entity = new MessageImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setType(vo.getType());
			
			if(DateTransferVo.hasDateString(vo.getEffectiveDateTransferVo())){
				entity.setEffectiveDate(DateTransferVo.getTransferDate(vo.getEffectiveDateTransferVo()));
			}
			
			if(DateTransferVo.hasDateString(vo.getExpireDateTransferVo())){
				entity.setExpireDate(DateTransferVo.getTransferDate(vo.getExpireDateTransferVo()));
			}
			
			entity.setStatus(vo.getStatus());
			entity.setMessageText(vo.getMessageText());
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<Message> toEntityList(Collection<MessageBoardVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<Message> entities = new ArrayList<Message>();
		
		for(MessageBoardVo vo : vos){
			entities.add(MessageBoardVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param effectiveDateTransferVo the effectiveDateTransferVo to set
	 */
	public void setEffectiveDateTransferVo(DateTransferVo effectiveDateTransferVo) {
		this.effectiveDateTransferVo = effectiveDateTransferVo;
	}

	/**
	 * @return the effectiveDateTransferVo
	 */
	public DateTransferVo getEffectiveDateTransferVo() {
		return effectiveDateTransferVo;
	}

	/**
	 * @param expireDateTransferVo the expireDateTransferVo to set
	 */
	public void setExpireDateTransferVo(DateTransferVo expireDateTransferVo) {
		this.expireDateTransferVo = expireDateTransferVo;
	}

	/**
	 * @return the expireDateTransferVo
	 */
	public DateTransferVo getExpireDateTransferVo() {
		return expireDateTransferVo;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param messageText the messageText to set
	 */
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	/**
	 * @return the messageText
	 */
	public String getMessageText() {
		return messageText;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedDateTransferVo the updatedDateTransferVo to set
	 */
	public void setUpdatedDateTransferVo(DateTransferVo updatedDateTransferVo) {
		this.updatedDateTransferVo = updatedDateTransferVo;
	}

	/**
	 * @return the updatedDateTransferVo
	 */
	public DateTransferVo getUpdatedDateTransferVo() {
		return updatedDateTransferVo;
	}

	

	
}
