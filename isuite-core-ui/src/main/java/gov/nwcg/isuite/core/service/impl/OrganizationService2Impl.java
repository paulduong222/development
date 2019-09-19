package gov.nwcg.isuite.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentOrg;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.impl.IncidentOrgImpl;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentOrgDao;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.rules.ReferenceDataDeleteUnitIdRulesHandler;
import gov.nwcg.isuite.core.rules.ReferenceDataSaveUnitIdRulesHandler;
import gov.nwcg.isuite.core.service.OrganizationService2;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.IncidentOrgVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

public class OrganizationService2Impl extends BaseService implements OrganizationService2 {
	private OrganizationDao dao;

	public OrganizationService2Impl(){

	}

	public void initialization(){
		dao = (OrganizationDao)super.context.getBean("organizationDao");
	}

	public DialogueVo deleteOrganization(OrganizationVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			
			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown Organization.");
			
			Organization entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), OrganizationImpl.class);
			
			ReferenceDataDeleteUnitIdRulesHandler ruleHandler = new ReferenceDataDeleteUnitIdRulesHandler(context);
			if(ruleHandler.execute(vo, entity,dialogueVo)==AbstractRule._OK){
				
				dao.delete(entity);
				
				//Remove from GlobalCacheVo
				GlobalCacheVo gvo = ((GlobalCacheVo)context.getBean("globalCacheVo"));
				Collection<OrganizationVo> newList = new ArrayList<OrganizationVo>();
				
				for(OrganizationVo ovo : gvo.getOrganizationVos()) {
					if(ovo.getId().compareTo(vo.getId())==0) {
						//skip this one since we are removing it
					}else
						newList.add(ovo);
				}
				
				gvo.setOrganizationVos(newList);
				((GlobalCacheVo)context.getBean("globalCacheVo")).setOrganizationVos(newList);				
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_ORGANIZATION");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.unitId", "info.0028" , new String[]{"Unit Id"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setResultObject(vo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo save(OrganizationVo vo, DialogueVo dialogueVo, IncidentVo ivo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{		
			Organization entity = null;
			Boolean bEdit=false;
			if(LongUtility.hasValue(vo.getId())){
				entity = dao.getById(vo.getId(), OrganizationImpl.class);
				bEdit=true;
			}
			
			ReferenceDataSaveUnitIdRulesHandler ruleHandler = new ReferenceDataSaveUnitIdRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){

				entity = OrganizationVo.toEntity(entity, vo, true);
				
				if (!entity.isStandard()) {
					// dan 6/6/2019 commenting out below
					// need to research further and determine if this is needed
					// entity.setIncident(IncidentVo.toEntity(entity.getIncident(), ivo, false));
				}
				
				if (entity.isStandard()) {
					if(ivo != null) {
						
						IncidentOrg incOrg=null;
						
						for(IncidentOrg io : entity.getIncidentOrgs()) {
							if(io.getIncident().getId().compareTo(ivo.getId())==0) {
								incOrg=io;
								break;
							}
						}
						
						if(!entity.isLocal()) {
							if(null != incOrg) {
								entity.getIncidentOrgs().remove(incOrg);
							}
							
							IncidentOrgDao incOrgDao = (IncidentOrgDao)super.context.getBean("incidentOrgDao");
							IncidentOrg incOrgToDelete = null;
							incOrgToDelete = incOrgDao.getById(incOrg.getId(), IncidentOrgImpl.class);
							incOrgDao.delete(incOrgToDelete);
						}
						
						if(entity.isLocal()) {
							if(incOrg == null) {
								IncidentOrg newIncOrg = new IncidentOrgImpl();
								newIncOrg.setIncident(IncidentVo.toEntity(null, ivo, false));
								newIncOrg.setOrganization(entity);
								newIncOrg.setType("L");
								entity.getIncidentOrgs().add(newIncOrg);
							}
						}
					}
				}
				
				dao.save(entity);
				dao.flushAndEvict(entity);
				entity = dao.getById(entity.getId(), OrganizationImpl.class);
				
				vo=OrganizationVo.getInstance(entity, true);
				vo.setIncidentOrgVos(IncidentOrgVo.getInstances(entity.getIncidentOrgs(), true));
				
				//update GlobalCacheVo
				Collection<OrganizationVo> organizationVos = ((GlobalCacheVo)context.getBean("globalCacheVo")).getOrganizationVos();
				
				if(bEdit){
					OrganizationVo organizationVo = null;
					
					for(OrganizationVo ovo : organizationVos) {
						if(ovo.getId().compareTo(vo.getId())==0) {
							organizationVo = ovo;
							break;
						}
					}
					
					organizationVos.remove(organizationVo);
					organizationVos.add(vo);
					((GlobalCacheVo)context.getBean("globalCacheVo")).setOrganizationVos(organizationVos);
				}else{
					organizationVos.add(vo);
					((GlobalCacheVo)context.getBean("globalCacheVo")).setOrganizationVos(organizationVos);	
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_ORGANIZATION");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.unitId", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
	
		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Collection<OrganizationVo> vos = new ArrayList<OrganizationVo>();
			Collection<OrganizationVo> pdcOrgs = new ArrayList<OrganizationVo>();
			Collection<OrganizationVo> allVos = new ArrayList<OrganizationVo>();
			
			Collection<Organization> orgs = dao.getAll(null);
			
			for(Organization org : orgs){
				if(!org.isDispatchCenter()){
					OrganizationVo vo = OrganizationVo.getInstance(org, true);
					vo.setIncidentOrgVos(IncidentOrgVo.getInstances(org.getIncidentOrgs(), true));
					vos.add(vo);
					allVos.add(vo);
				}else{
					OrganizationVo pdcVo = OrganizationVo.getInstance(org, true);
					pdcOrgs.add(pdcVo);
					allVos.add(pdcVo);
				}
			}
			
			((GlobalCacheVo)context.getBean("globalCacheVo")).setOrganizationVos(allVos);	
			((GlobalCacheVo)context.getBean("globalCacheVo")).setNonPDCOrgs(vos);	
			((GlobalCacheVo)context.getBean("globalCacheVo")).setPdcVos(pdcOrgs);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_ORGANIZATIONS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo getGridIncidentorGroup(Long incidentId, Long incidentGroupId, Boolean incidentOnly, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Collection<Long> incidentIds = new ArrayList<Long>();
			if(LongUtility.hasValue(incidentId) && BooleanUtility.isTrue(incidentOnly)){
				incidentIds.add(incidentId);
			}
			if(LongUtility.hasValue(incidentId) && BooleanUtility.isFalse(incidentOnly)){
				// determine if incident is part of a group
				IncidentDao idao = (IncidentDao)context.getBean("incidentDao");
				Long incGroupId=idao.getIncidentGroupId(incidentId);
				if(LongUtility.hasValue(incGroupId)){
					incidentId=null;
					incidentGroupId=incGroupId;
				}
			}
			if(LongUtility.hasValue(incidentGroupId)){
				// get all incidentids for group
				IncidentGroupDao igdao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				incidentIds=igdao.getIncidentIdsInGroup(incidentGroupId);
			}
			
			Collection<OrganizationVo> vos = this.dao.getStandardAndNonStandard(incidentIds, incidentGroupId);
			Collection<OrganizationVo> newvos = new ArrayList<OrganizationVo>();
			for(OrganizationVo v : vos){
				if(BooleanUtility.isTrue(v.getActive())){
					newvos.add(v);
				}
			}

			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_ORGANIZATIONS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(newvos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	public DialogueVo getGridIncidentorGroup2(Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			/*
			Collection<Long> incidentIds = new ArrayList<Long>();
			if(LongUtility.hasValue(incidentId)){
				// determine if incident is part of a group
				IncidentDao idao = (IncidentDao)context.getBean("incidentDao");
				Long incGroupId=idao.getIncidentGroupId(incidentId);
				if(LongUtility.hasValue(incGroupId)){
					incidentId=null;
					incidentGroupId=incGroupId;
				}
			}
			if(LongUtility.hasValue(incidentGroupId)){
				// get all incidentids for group
				IncidentGroupDao igdao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				incidentIds=igdao.getIncidentIdsInGroup(incidentGroupId);
			}
			*/
			Collection<OrganizationVo> vos = new ArrayList<OrganizationVo>();
			
			// add all standard orgs and all non-standard for the inc/group
			for(OrganizationVo v : super.getGlobalCacheVo().getNonPDCOrgs()){
				if(BooleanUtility.isTrue(v.getActive())){
					if(BooleanUtility.isTrue(v.getStandard())){
						vos.add(v);
					}else{
						/*
						System.out.println(v.getUnitCode());
						if(LongUtility.hasValue(incidentId) && null != v.getIncidentVo()){
							if(incidentId.compareTo(v.getIncidentVo().getId())==0)
								vos.add(v);
						}

						if(LongUtility.hasValue(incidentGroupId) && null != v.getIncidentVo()){
							for(Long incid : incidentIds){
								if(incid.compareTo(v.getIncidentVo().getId())==0)
									vos.add(v);
							}
						}
						*/
						
						if(LongUtility.hasValue(incidentGroupId) && null != v.getIncidentGroupVo()){
							if(incidentGroupId.compareTo(v.getIncidentGroupVo().getId())==0)
								vos.add(v);
						}
					}
				}
			}
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_ORGANIZATIONS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

}
