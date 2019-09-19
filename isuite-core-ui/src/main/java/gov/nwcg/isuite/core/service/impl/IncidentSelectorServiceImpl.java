package gov.nwcg.isuite.core.service.impl;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentSelectorDao;
import gov.nwcg.isuite.core.service.IncidentSelectorService;
import gov.nwcg.isuite.core.vo.IncidentSelector2Vo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IncidentSelectorServiceImpl extends BaseService implements IncidentSelectorService {
	private IncidentSelectorDao dao = null;
	private Long incidentId=null;
	private Long incidentGroupId=null;
	private Boolean incidentsOnly=false;
	private Boolean filterExcluded=true;
	
	public IncidentSelectorServiceImpl() {
		super();
	}

    public void initialization(){
    	dao = (IncidentSelectorDao)super.context.getBean("incidentSelectorDao");
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentSelectorService#getGrid(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(Long userId, DialogueVo dialogueVo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			Collection<IncidentSelector2Vo> vos = new ArrayList<IncidentSelector2Vo>();

			if(!LongUtility.hasValue(incidentGroupId)){
				// get incidents user can access
				Collection<IncidentSelector2Vo> incidentVos = new ArrayList<IncidentSelector2Vo>();
				incidentVos = dao.getIncidents(userId,incidentId,this.filterExcluded);

				for(IncidentSelector2Vo ivo : incidentVos){
					ivo.setType("INCIDENT");
					vos.add(ivo);
				}
			}
			
			if(!LongUtility.hasValue(incidentId) && !this.incidentsOnly){
				// get incident groups user can access
				Collection<IncidentSelector2Vo> incidentGroupVos = new ArrayList<IncidentSelector2Vo>();
				Collection<IncidentSelector2Vo> incidentGroupVos2 = new ArrayList<IncidentSelector2Vo>();
				incidentGroupVos = dao.getIncidentGroups(userId,incidentGroupId, this.filterExcluded);
				
				for (IncidentSelector2Vo vo : incidentGroupVos){
					Long groupId = vo.getId();

					IncidentSelector2Vo group = vo;
					
					IncidentGroup ig = igDao.getById(groupId, IncidentGroupImpl.class);
					if(null != ig){
						group.setIsSiteManaged(ig.getIsSiteManaged().getValue());
						
						for(Incident incident : ig.getIncidents()){
							IncidentSelector2Vo child = IncidentSelector2Vo.getInstance(incident);
							child.setIncidentId(incident.getId());
							child.setType("INCIDENT");
							group.getChildren().add(child);
						}
					}
					group.setType("INCIDENTGROUP");
					incidentGroupVos2.add(group);				
				}
				
				vos.addAll(incidentGroupVos2);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_SELECTOR_VOS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentSelectorService#getCustomUserIncDataView(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getCustomUserIncDataView(Long userId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
	
			Collection<IncidentSelector2Vo> vos = new ArrayList<IncidentSelector2Vo>();

			Collection<IncidentSelector2Vo> incVos = new ArrayList<IncidentSelector2Vo>();
			incVos = dao.getIncidents(userId,incidentId,true);
			for(IncidentSelector2Vo ivo : incVos){
				ivo.setType("INCIDENT");
				vos.add(ivo);
			}
			
			Collection<IncidentSelector2Vo> incidentGroupVos = new ArrayList<IncidentSelector2Vo>();
			incidentGroupVos = dao.getIncidentGroups(userId,incidentGroupId, true);
			for (IncidentSelector2Vo ivo : incidentGroupVos){
				ivo.setType("INCIDENTGROUP");
				vos.add(ivo);
			}
			
			Collection<IncidentSelector2Vo> excludedVos = new ArrayList<IncidentSelector2Vo>();
			Collection<IncidentSelector2Vo> excludedIncVos = new ArrayList<IncidentSelector2Vo>();
			excludedIncVos = dao.getExcludedIncidents(userId, null);
			for(IncidentSelector2Vo ivo : excludedIncVos){
				ivo.setType("INCIDENT");
				excludedVos.add(ivo);
			}
			
			
			Collection<IncidentSelector2Vo> excludedIncGrpVos = new ArrayList<IncidentSelector2Vo>();
			excludedIncGrpVos = dao.getExcludedIncidentGroups(userId, null);
			for (IncidentSelector2Vo ivo : excludedIncGrpVos){
				ivo.setType("INCIDENTGROUP");
				excludedVos.add(ivo);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_CUSTOM_USER_INC_VIEW");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			dialogueVo.setResultObjectAlternate(excludedVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo saveCustomView(Long userId, Collection<IncidentSelector2Vo> excludedVos, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
	
		try{
			
			// remove any excluded items that are now included
			Collection<Long> incidentIds = IncidentSelector2Vo.toIncidentIds(excludedVos);
			Collection<Long> incidentGroupids = IncidentSelector2Vo.toIncidentGroupIds(excludedVos);

			int cnt=dao.removeExcludedItems(userId, incidentIds, incidentGroupids);			
			
			// add new excluded items
			cnt+=dao.createExcludedItems(userId, incidentIds, incidentGroupids);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_CUSTOM_INC_VIEW");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		    coaVo.setMessageVo(
					   new MessageVo(
							   "text.incident", 
							   "info.generic" , new String[]{"The custom data view was saved"}, 
							   MessageTypeEnum.INFO));
			
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(cnt);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
	public void setIncidentsOnly(Boolean val) {
		this.incidentsOnly=val;
	}

	public Boolean getFilterExcluded() {
		return filterExcluded;
	}

	public void setFilterExcluded(Boolean filterExcluded) {
		this.filterExcluded = filterExcluded;
	}

}
