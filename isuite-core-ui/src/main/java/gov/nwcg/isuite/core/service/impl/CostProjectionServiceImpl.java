package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.domain.ProjectionItemWorksheet;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.domain.impl.ProjectionImpl;
import gov.nwcg.isuite.core.domain.impl.ProjectionItemImpl;
import gov.nwcg.isuite.core.domain.impl.ProjectionItemWorksheetImpl;
import gov.nwcg.isuite.core.persistence.CostProjectionDao;
import gov.nwcg.isuite.core.persistence.CostProjectionItemDao;
import gov.nwcg.isuite.core.persistence.CostProjectionItemWorksheetDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.reports.filter.CostProjectionReportFilter;
import gov.nwcg.isuite.core.reports.generator.CostProjectionCategoryDetailReportGeneratorImpl;
import gov.nwcg.isuite.core.reports.generator.CostProjectionReportGeneratorImpl;
import gov.nwcg.isuite.core.reports.generator.CostProjectionTotalReportGeneratorImpl;
import gov.nwcg.isuite.core.rules.CostProjectionsCreateProjectionRulesHandler;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.service.CostProjectionService;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.ProjectionItemVo;
import gov.nwcg.isuite.core.vo.ProjectionVo;
import gov.nwcg.isuite.core.vo.ProjectionWorksheetGridVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class CostProjectionServiceImpl extends BaseService implements CostProjectionService {
	private CostProjectionDao dao = null;
	private CostProjectionItemDao pidao = null;
	private IncidentDao incidentDao;
	private IncidentGroupDao incidentGroupDao;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public CostProjectionServiceImpl(){}

	public void initialization() {
		this.dao=(CostProjectionDao)super.context.getBean("costProjectionDao");
		this.incidentDao = (IncidentDao)super.context.getBean("incidentDao");
		this.incidentGroupDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
		this.pidao = (CostProjectionItemDao)super.context.getBean("costProjectionItemDao");
	}
			
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostProjectionService#getGrid(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getProjectionGrid(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{			
			Collection<ProjectionVo> vos = dao.getProjectionGrid(incidentId, incidentGroupId);
			CourseOfActionVo coaVo = getCourseOfActionVo("GET_PROJECTIONS",CourseOfActionTypeEnum.HANDLE_RECORDSET,null);
			dialogueVo.setResultObject(vos);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
		
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostProjectionService#getGrid(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getIncidentSummaryGrid(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{			
			Collection<ProjectionItemVo> vos = dao.getIncidentSummaryGrid(projectionVo.getId());
			
			CourseOfActionVo coaVo = getCourseOfActionVo("GET_INCIDENT_SUMMARY",CourseOfActionTypeEnum.HANDLE_RECORDSET,null);
			dialogueVo.setResultObject(vos);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostProjectionService#getGrid(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getManuallyAddProjectionItemGrid(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{			
			Collection<ProjectionItemVo> vos = dao.getManuallyAddProjectionGrid(projectionVo.getId());
				
			CourseOfActionVo coaVo = getCourseOfActionVo("GET_MANUALLYADD_PROJECTIONITEM",CourseOfActionTypeEnum.HANDLE_RECORDSET,null);
			dialogueVo.setResultObject(vos);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostProjectionService#getGrid(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getSupportingCostGrid(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{			
			Collection<ProjectionItemVo> vos = dao.getSupportingCostGrid(projectionVo.getId());
				
			CourseOfActionVo coaVo = getCourseOfActionVo("GET_SUPPORTING_COST",CourseOfActionTypeEnum.HANDLE_RECORDSET,null);
			dialogueVo.setResultObject(vos);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostProjectionService#getGrid(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getProjectionWorkSheetGrid(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{			
			CostProjectionItemWorksheetDao cpiwDao = (CostProjectionItemWorksheetDao)context.getBean("costProjectionItemWorksheetDao");
			
			Collection<ProjectionWorksheetGridVo> vos = ProjectionWorksheetGridVo.getInstances(cpiwDao.getProjectionWorksheetGrid(projectionVo.getId()));

			CourseOfActionVo coaVo = getCourseOfActionVo("GET_PROJECTION_ITEM_WORKSHEET",CourseOfActionTypeEnum.HANDLE_RECORDSET,null);
			dialogueVo.setResultObject(vos);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostProjectionService#getGrid(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getProjectionWorkSheetStartDate(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{			
			CostProjectionItemWorksheetDao cpiwDao = (CostProjectionItemWorksheetDao)context.getBean("costProjectionItemWorksheetDao");
			
			Date date = cpiwDao.getProjectionWorksheetStartDay(projectionVo.getId());

			CourseOfActionVo coaVo = getCourseOfActionVo("GET_PROJECTION_WORKSHEET_STARTDATE",CourseOfActionTypeEnum.HANDLE_RECORDSET,null);
			dialogueVo.setResultObject(date);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostProjectionService#getGrid(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getSupportingCostWorkSheetGrid(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{			
			CostProjectionItemWorksheetDao cpiwDao = (CostProjectionItemWorksheetDao)context.getBean("costProjectionItemWorksheetDao");
			Collection<ProjectionWorksheetGridVo> vos = ProjectionWorksheetGridVo.getSupportingCostInstances(cpiwDao.getSupportingCostWorksheetGrid(projectionVo.getId()));

			CourseOfActionVo coaVo = getCourseOfActionVo("GET_SUPPORTINGCOST_WORKSHEET",CourseOfActionTypeEnum.HANDLE_RECORDSET,null);
			dialogueVo.setResultObject(vos);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostProjectionService#addProjection(gov.nwcg.isuite.core.vo.ProjectionVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo addProjection(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Incident incident = null;
			IncidentGroup incidentGroup = null;
			
			if(projectionVo.getIncidentId() != 0)
				incident = getIncident(projectionVo.getIncidentId());
			else if(projectionVo.getIncidentGroupId() != 0)
				incidentGroup = getIncidentGroup(projectionVo.getIncidentGroupId());
		
			CostProjectionsCreateProjectionRulesHandler ruleHandler = 
							new CostProjectionsCreateProjectionRulesHandler(context);
			
			String today = (String)dialogueVo.getResultObjectAlternate4();
			if(EISuiteCalendar.isTrainingMode==true
					&& EISuiteCalendar.hasDbCalendarDate(super.getUserSessionDbName())==true){
				today=EISuiteCalendar.getCalendarDateAsString(super.getUserSessionDbName());
			}
			
            //1. check add rules: startday can not before incident start day
			//                    numofdays <= 90
			//                    projectionName can not be dup in the same day
			if(ruleHandler.execute(projectionVo, this.dao, dialogueVo, incident, incidentGroup,today)== AbstractRule._OK){
				
				//1. prepare projection data 
				Projection entity = new ProjectionImpl();
	
				//2. for create a new projection, the projection data should be generated base on 
				//   all the actual daily cost data (all days daily cost)   
				//   this has been changed on 02/14:After talking with Donna, she said that average cost and total should be based 
				//   on the current day's daily cost amount, not on all of the days in the projection.
				//   Trudi Allison: If there are no costs for the current day, then there should be no projection generated.

				Collection<ProjectionItem> projectionItems = getProjectionItemData(entity,projectionVo,today);
				
				entity.getProjectionItems().addAll(projectionItems);
				if(incident != null)
					entity.setIncident(incident);
				else if(incidentGroup != null)
					entity.setIncidentGroup(incidentGroup);

				dao.save(ProjectionVo.toEntity(entity, projectionVo, true));
				dao.flushAndEvict(entity);
				
				CourseOfActionVo coaVo = getCourseOfActionVo("ADD_PROJECTION",CourseOfActionTypeEnum.SHOWMESSAGE,
								new MessageVo("text.extract","info.0030.01"
								, new String[]{" cost Projection (" + projectionVo.getProjectionName() + ") "} 
								, MessageTypeEnum.INFO));
				dialogueVo.setResultObject(projectionVo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			else {
				if(null != dialogueVo.getCourseOfActionVo() 
						&& dialogueVo.getCourseOfActionVo().getCoaName().equals("validationerror")){
					return dialogueVo;
				}else
					throw new Exception("Add projection failed. "+ (String)dialogueVo.getResultObjectAlternate4());
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostProjectionService#updateProjection(gov.nwcg.isuite.core.vo.ProjectionVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */  
	public DialogueVo updateProjection(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{	
			if(this.isLocked(projectionVo, dialogueVo)==true){
				return dialogueVo;
			}
			
			
				//1. get selected projection entity by id
				Projection entity = null;
				if(LongUtility.hasValue(projectionVo.getId())){
					entity = dao.getById(projectionVo.getId(), ProjectionImpl.class);
				}
				
				//3. get projection worksheet start date
				CostProjectionItemWorksheetDao cpiwDao = (CostProjectionItemWorksheetDao)context.getBean("costProjectionItemWorksheetDao");
				Date startDate = cpiwDao.getProjectionWorksheetStartDay(projectionVo.getId());
				
			
				
				//3. Rule: edit projection only allowed to update numofdays of the projection.
				//         since the projection numofdays changed, so the worksheet days range is changed
				Collection<ProjectionItem> pis = entity.getProjectionItems();
				for(ProjectionItem pi: pis) {
					Date d = ((List<ProjectionItemWorksheet>)pi.getProjectionItemWorksheets()).get(0).getProjectionDate();
					updateProjectionWorksheetSize(pi,projectionVo.getNumberOfDays(),startDate);
				}
				
				//2. Rule: user can only update number of days
				entity.setNumberOfDays(projectionVo.getNumberOfDays());
				
				dao.save(entity);
				dao.flushAndEvict(entity);
				
				entity = dao.getById(projectionVo.getId(), ProjectionImpl.class);
				projectionVo = ProjectionVo.getInstance(entity, true);
								
				CourseOfActionVo coaVo = getCourseOfActionVo("EDIT_PROJECTION",CourseOfActionTypeEnum.SHOWMESSAGE,
						new MessageVo("text.extract","info.0030.02"
						, new String[]{"Cost Projection ("+  projectionVo.getProjectionName() +") is updated."} 
						, MessageTypeEnum.INFO));
	
				dialogueVo.setResultObject(projectionVo);
				dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostProjectionService#deleteProjection(gov.nwcg.isuite.core.vo.ProjectionVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */  
	public DialogueVo deleteProjection(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{	
			if(this.isLocked(projectionVo, dialogueVo)==true){
				return dialogueVo;
			}
			
			//1. delete entiry 
			Projection entity = null;
			if(LongUtility.hasValue(projectionVo.getId())){
				entity = dao.getById(projectionVo.getId(), ProjectionImpl.class);
			}
				
			dao.delete(entity);
			dao.flushAndEvict(entity);
								
			CourseOfActionVo coaVo = getCourseOfActionVo("DELETE_PROJECTION",CourseOfActionTypeEnum.SHOWMESSAGE,
					new MessageVo("text.extract","info.0030.02"
							, new String[]{"Cost Projection ("+  projectionVo.getProjectionName() +") is deleted."} 
					, MessageTypeEnum.INFO));
			dialogueVo.setResultObject(null);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
		
	public DialogueVo addProjectionItem(ProjectionVo projectionVo, ProjectionItemVo projectionItemVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			if(this.isLocked(projectionVo, dialogueVo)==true){
				return dialogueVo;
			}
			
			Projection entity = null;
			if(LongUtility.hasValue(projectionVo.getId())){
				entity = dao.getById(projectionVo.getId(), ProjectionImpl.class);
			}
			
			ProjectionItem piEntity = new ProjectionItemImpl();
			piEntity.setIsItemCodeActive(StringBooleanEnum.Y);
			piEntity.setIsManuallyAdded(StringBooleanEnum.Y);
			piEntity.setIsSupportCost(StringBooleanEnum.N);
			piEntity.setNumberOfPersonnel(projectionItemVo.getKindVo().getPeople()*projectionItemVo.getKindVo().getUnits());
			piEntity.setCostName(projectionItemVo.getKindVo().getDescription());
			piEntity.setItemCodeGroup(projectionItemVo.getKindVo().getCode());
			piEntity.setItemId(projectionItemVo.getKindVo().getId());
			Kind kind = new KindImpl();
			kind.setId(projectionItemVo.getKindVo().getId());
			piEntity.setKind(kind);
			Projection projection = new ProjectionImpl();
			projection.setId(projectionVo.getId());
			piEntity.setProjection(projection);
			piEntity.setAverageCost(projectionItemVo.getAverageCost());
			piEntity.setQuantity(projectionItemVo.getKindVo().getUnits());
				
			//update projectionItems
			BigDecimal total; 
			total = projectionItemVo.getAverageCost().multiply(BigDecimal.valueOf(projectionItemVo.getKindVo().getUnits()));
			piEntity.setTotalCost(total);
			entity.addProjectionItem(piEntity);
			
			//add a manually projection item will change the personnel number of supporting cost
			entity.updateSupportingItem(piEntity);

			//add new projection item does not creat projeciton item worksheets
			//entity.getProjectionItemWorksheets().addAll(getProjectionItemWorkSheetData(entity,projectionVo.getNumberOfDays(),projectionVo.getStartDate())); 
						
			dao.save(entity);
			dao.flushAndEvict(entity);	
													
			CourseOfActionVo coaVo = getCourseOfActionVo("SAVE_PROJECTIONITEM",CourseOfActionTypeEnum.SHOWMESSAGE,
					new MessageVo("text.extract","info.0030.02"
					, new String[]{"The Projection item is saved."} 
					, MessageTypeEnum.INFO));
				
			dialogueVo.setResultObject(projectionItemVo);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
		
	public DialogueVo updateProjectionItem(ProjectionVo projectionVo, ProjectionItemVo projectionItemVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{	
			if(this.isLocked(projectionVo, dialogueVo)==true){
				return dialogueVo;
			}
			
			//1. get entity  
			ProjectionItem entity = null;
			entity = pidao.getById(projectionItemVo.getId(), ProjectionItemImpl.class);
				
			//update entity with the new ProjectionItem data
			BigDecimal total; 
	
			//update projection item only reset average cost and total
			total = projectionItemVo.getAverageCost().multiply(BigDecimal.valueOf(projectionItemVo.getQuantity()));
			entity.setTotalCost(total);
			entity.setAverageCost(projectionItemVo.getAverageCost());
						
			pidao.save(entity);
			pidao.flushAndEvict(entity);	
			
			CourseOfActionVo coaVo = getCourseOfActionVo("SAVE_PROJECTIONITEM",CourseOfActionTypeEnum.SHOWMESSAGE,
					new MessageVo("text.extract","info.0030.02"
				    , new String[]{"The change of average cost is saved."} 
					, MessageTypeEnum.INFO));
			
			dialogueVo.setResultObject(projectionItemVo);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
		
	public DialogueVo deleteProjectionItem(ProjectionVo projectionVo, ProjectionItemVo projectionItemVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{	
			if(this.isLocked(projectionVo, dialogueVo)==true){
				return dialogueVo;
			}
			
			Projection entity = null;
			if(LongUtility.hasValue(projectionVo.getId())){
				entity = dao.getById(projectionVo.getId(), ProjectionImpl.class);
			}
			
			ProjectionItem pi = new ProjectionItemImpl();
			
			pi.setId(projectionItemVo.getId());
			pi.setNumberOfPersonnel(projectionItemVo.getNumberOfPersonnel());
			entity.updateSupportingItem(pi);
			entity.removeProjectionItem(pi.getId());
			
		    dao.save(entity);
			dao.flushAndEvict(entity);
									
			CourseOfActionVo coaVo = getCourseOfActionVo("DELETE_PROJECTIONITEM",CourseOfActionTypeEnum.SHOWMESSAGE,
						new MessageVo("text.extract","info.0030.02"
						, new String[]{"The Projection item is deleted."} 
						, MessageTypeEnum.INFO));
				
			dialogueVo.setResultObject(projectionItemVo);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo updatePorjectionQuantity(ProjectionVo projectionVo, ProjectionWorksheetGridVo projectionWorksheetGridVo,DialogueVo dialogueVo) {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{	
			if(this.isLocked(projectionVo, dialogueVo)==true){
				return dialogueVo;
			}
			
			//1. get entity  
			Collection<ProjectionItemWorksheet> pjEntities = null;
			CostProjectionItemWorksheetDao cpiwDao = (CostProjectionItemWorksheetDao)context.getBean("costProjectionItemWorksheetDao");
			pjEntities = cpiwDao.getProjectionWorksheetByItemCode(projectionVo.getId(),  projectionWorksheetGridVo.getItemCode()); 
			
			//update entity with the new quantity
			//date is the user picked date
			Date date = (Date)dialogueVo.getResultObjectAlternate2();
			Integer quantity = ((Integer) dialogueVo.getResultObjectAlternate3()).intValue();
			boolean isUpdateAll = (Boolean) dialogueVo.getResultObjectAlternate4();
			
			int people = 0;		
			
			for(ProjectionItemWorksheet piwksheet: pjEntities) {
				if(projectionWorksheetGridVo.getItemCode().equals("OD") || projectionWorksheetGridVo.getItemCode().equals("OS")) 
					people = 1; //all items in OD OS have 1 quantity and 1 people 
				else 
					people = piwksheet.getProjectionItem().getKind().getPeople();
					
				//find beginning date to update
				String dateStr = dateFormat.format(date);
				String projectionDate = dateFormat.format(piwksheet.getProjectionDate());
				if(dateStr.equals(projectionDate)) {
					piwksheet.setQuantity(quantity);
					piwksheet.setNumberOfPersonnel(quantity*people);
					continue;
				}
				
				//if update all is checked then update the following date from "date"
				if(isUpdateAll && piwksheet.getProjectionDate().after(date)) {
					piwksheet.setQuantity(quantity);
					piwksheet.setNumberOfPersonnel(quantity*people);
				}
			}
			
			cpiwDao.saveAll(pjEntities);
			
			//update support worksheet
			Collection<ProjectionItemWorksheet> spEntities = null;
			spEntities = cpiwDao.getSupportingCostWorksheetGrid(projectionVo.getId()); 
			Collection<ProjectionItemWorksheet> personnels = cpiwDao.getProjectionWorksheetPersonnelTotal(projectionVo.getId()); 
			
			for(ProjectionItemWorksheet support: spEntities) {
				String projectionDate = dateFormat.format(support.getProjectionDate());
				for(ProjectionItemWorksheet personnel: personnels) {
					String personnelDate = dateFormat.format(personnel.getProjectionDate());
					if(personnelDate.equals(projectionDate)) { 
						support.setNumberOfPersonnel(personnel.getNumberOfPersonnel());
						break;
					}
					else
						continue;
				}
			}
			cpiwDao.saveAll(spEntities);
			
			CourseOfActionVo coaVo = getCourseOfActionVo("UPDATE_PROJECTION_WORKSHEET",CourseOfActionTypeEnum.SHOWMESSAGE,
					new MessageVo("text.extract","info.0030.02"
					, new String[]{"The projection quantity is updated."} 
					, MessageTypeEnum.INFO));
			dialogueVo.setResultObject(projectionVo);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo updateSupportingCostAverageCost(ProjectionVo projectionVo,DialogueVo dialogueVo){
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{	
			if(this.isLocked(projectionVo, dialogueVo)==true){
				return dialogueVo;
			}
			
			//1. get entity  
			Collection<ProjectionItemWorksheet> entities = null;
			CostProjectionItemWorksheetDao cpiwDao = (CostProjectionItemWorksheetDao)context.getBean("costProjectionItemWorksheetDao");
			entities = cpiwDao.getSupportingCostWorksheetGrid(projectionVo.getId());
				
			//update entity with the new quantity
			Date date = (Date)dialogueVo.getResultObjectAlternate2();
			BigDecimal averageCost  = null;
			if (dialogueVo.getResultObjectAlternate3() instanceof Double) 
				averageCost = new BigDecimal((Double)dialogueVo.getResultObjectAlternate3());
			
			if (dialogueVo.getResultObjectAlternate3() instanceof Integer) 
				averageCost = new BigDecimal((Integer)dialogueVo.getResultObjectAlternate3());
			
			boolean isUpdateAll = (Boolean) dialogueVo.getResultObjectAlternate4();
			
			for(ProjectionItemWorksheet piwksheet: entities) {
				//find beginning date to update
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateStr = dateFormat.format(date);
				String projectionDate = dateFormat.format(piwksheet.getProjectionDate());
				if(dateStr.equals(projectionDate)) {
					piwksheet.setAverageCost(averageCost);
					continue;
				}
				
				if(isUpdateAll && piwksheet.getProjectionDate().after(date))  
					piwksheet.setAverageCost(averageCost);
			}
	
			
			cpiwDao.saveAll(entities);
			
			CourseOfActionVo coaVo = getCourseOfActionVo("UPDATE_SUPPORTCOST_WORKSHEET",CourseOfActionTypeEnum.SHOWMESSAGE,
					new MessageVo("text.extract","info.0030.02"
					, new String[]{"The projection average cost is updated."} 
					, MessageTypeEnum.INFO));
			
			dialogueVo.setResultObject(projectionVo);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo refreshIncidentSummaryProjection(ProjectionVo projectionVo,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{	
			//1. get existing projection items for selected projection  
			Projection projection = dao.getById(projectionVo.getId(), ProjectionImpl.class);
			if(projection == null)
				throw new Exception("Can not find the selected projection.");
			
			Collection<ProjectionItem> oldProjectionItems = projection.getProjectionItems();
			
			//2. base on the current day daily cost to generate projection item data
			Collection<ProjectionItem> currentDayProjectionItems = getProjectionItemDataForCurrentDay(projectionVo,(String)dialogueVo.getResultObjectAlternate4(),false);
			
			//3. update the existing projection item list with the current day daily cost
			for(ProjectionItem currentDayPitem: currentDayProjectionItems) {
				
				final String itemCode = currentDayPitem.getItemCodeGroup();
				ProjectionItemImpl update = (ProjectionItemImpl)CollectionUtils.find(oldProjectionItems, new Predicate() {
						@Override
						public boolean evaluate(Object object) {
							ProjectionItem data = (ProjectionItem) object;
							return itemCode.equals(data.getItemCodeGroup());
						}
					});
				
				//it is in the existing list
				if(update != null) { 
					update.updateObject(currentDayPitem);
					if(update.getIsManuallyAdded().getValue())  //if it is in the list but is manually add move it to incident summary grid
						update.setIsManuallyAdded(StringBooleanEnum.N);
				} 
				//new to the existing list
				else { 
					ProjectionItemImpl projectionItem = (ProjectionItemImpl)currentDayPitem;
					oldProjectionItems.add(projectionItem);
				}
			}
			
			//4. set active=N for the items are in the existing projection item list but not in the current day projection item list 	
			for(ProjectionItem pi: oldProjectionItems) {
				
				final String itemCode = pi.getItemCodeGroup(); 
					
				ProjectionItemImpl update = (ProjectionItemImpl)CollectionUtils.find(currentDayProjectionItems, new Predicate() {
						@Override
						public boolean evaluate(Object object) {
							ProjectionItem data = (ProjectionItem) object;
							return itemCode.equals(data.getItemCodeGroup());
						}
					});
				
				if(update == null && pi.getIsManuallyAdded().equals(StringBooleanEnum.N))
					pi.setIsItemCodeActive(StringBooleanEnum.N);   //what else need to set????????
			}
			
			//update supporting item projection the total number of people
			ProjectionItemImpl update = (ProjectionItemImpl)CollectionUtils.find(oldProjectionItems, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					ProjectionItem data = (ProjectionItem) object;
					return data.getIsSupportCost().getValue();
				}
			});
			
			if(update != null)
				update.setNumberOfPersonnel(getSupportingCostPeople(oldProjectionItems));
			
			
			//not set here it should be in refresh worksheet,the projection start day should be consist with worksheet start day
			String today = (String)dialogueVo.getResultObjectAlternate4();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date startDate = dateFormat.parse(today);
			projection.setStartDate(getNextDate(startDate));
			
			dao.save(projection);
			dao.flushAndEvict(projection);	
			
			CourseOfActionVo coaVo = getCourseOfActionVo("REFRESH_INCIDENTSUMMARY_PROJECTION",CourseOfActionTypeEnum.SHOWMESSAGE,
					new MessageVo("text.extract","info.0030.02"
					, new String[]{"The resource Summary has been refreshed."} 
					, MessageTypeEnum.INFO));
			
			
			//dialogueVo.setResultObject(projection);
			dialogueVo.setCourseOfActionVo(coaVo);
								
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo refreshProjectionWorksheet(ProjectionVo projectionVo,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{	
			//1. get current projection items  
			Projection projection = dao.getById(projectionVo.getId(), ProjectionImpl.class);	
			if(projection == null)
				throw new Exception("Can not find the selected projection.");
			Collection<ProjectionItem> projectionItems = projection.getProjectionItems();
						
			//2. update projectionItem worksheet for all projection items
			for(ProjectionItem pi: projectionItems)	{
				updateProjectionWorksheet(pi,projection.getNumberOfDays(),projection.getStartDate());
			}
			dao.save(projection);
			dao.flushAndEvict(projection);
			
			
			//update support worksheet
			CostProjectionItemWorksheetDao cpiwDao = (CostProjectionItemWorksheetDao)context.getBean("costProjectionItemWorksheetDao");
			Collection<ProjectionItemWorksheet> spEntities = null;
			spEntities = cpiwDao.getSupportingCostWorksheetGrid(projectionVo.getId()); 
			Collection<ProjectionItemWorksheet> personnels = cpiwDao.getProjectionWorksheetPersonnelTotal(projectionVo.getId()); 
			
			for(ProjectionItemWorksheet support: spEntities) {
				String projectionDate = dateFormat.format(support.getProjectionDate());
				for(ProjectionItemWorksheet personnel: personnels) {
					String personnelDate = dateFormat.format(personnel.getProjectionDate());
					if(personnelDate.equals(projectionDate)) { 
						support.setNumberOfPersonnel(personnel.getNumberOfPersonnel());
						break;
					}
					else
						continue;
				}
			}
			cpiwDao.saveAll(spEntities);
			
			CourseOfActionVo coaVo = getCourseOfActionVo("REFRESH_PROJECTION_WORKSHEET",CourseOfActionTypeEnum.SHOWMESSAGE,
					new MessageVo("text.extract","info.0030.02"
					, new String[]{"The projection worksheet has been updated."} 
					, MessageTypeEnum.INFO));
			
//			dialogueVo.setResultObject(oldProjectionItems);
			dialogueVo.setCourseOfActionVo(coaVo);
								
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	private void groupODProjectionItems(Collection<ProjectionItem> vos) {
		Collection<ProjectionItem> ODList = new ArrayList<ProjectionItem>(vos);
		
		CollectionUtils.filter(ODList, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				ProjectionItem data = (ProjectionItem) object;
				boolean t = data.getItemCodeGroup().equals("OD");
				return t;
			}
		});
		
		if(ODList.isEmpty())
			return;
		
		BigDecimal averageCost = BigDecimal.ZERO;
		int quantity = 0;
		int people = 0;
		BigDecimal total = BigDecimal.ZERO;
		
		for(ProjectionItem pi:ODList){
			quantity = quantity + pi.getQuantity();
			people = people + (pi.getNumberOfPersonnel()== null? 0:pi.getNumberOfPersonnel());
			total = total.add(pi.getTotalCost());
			averageCost = averageCost.add(pi.getAverageCost());
		}
		averageCost = averageCost.divide(BigDecimal.valueOf(ODList.size()),2,RoundingMode.CEILING);
		total = averageCost.multiply(BigDecimal.valueOf(quantity));
		ProjectionItem odpi = new ProjectionItemImpl();
		odpi.setCostName("OD cost group");
		odpi.setItemCodeGroup("OD");
		odpi.setIsSupportCost(StringBooleanEnum.N);
		odpi.setItemId(null);
		odpi.setNumberOfPersonnel(people);
		odpi.setQuantity(quantity);
		odpi.setTotalCost(total);
		odpi.setAverageCost(averageCost);
		
		List<ProjectionItem> newList = new ArrayList<ProjectionItem>();
		Iterator<ProjectionItem> iter = vos.iterator();
		while (iter.hasNext()) {
			ProjectionItem pi = (ProjectionItem)iter.next();
		  if(!pi.getItemCodeGroup().equals("OD"))
			  newList.add(pi);
		}
		vos.clear();
		newList.add(odpi);
		vos.addAll(newList);
		
		return;
	}
	
	private void groupOSProjectionItems(Collection<ProjectionItem> vos) {
		Collection<ProjectionItem> OSList = new ArrayList<ProjectionItem>(vos);		
		CollectionUtils.filter(OSList, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				ProjectionItem data = (ProjectionItem) object;
				boolean t = data.getItemCodeGroup().equals("OS");
				return t;
			}
		});
		
		if(OSList.isEmpty())
			return;
		
		BigDecimal averageCost = BigDecimal.ZERO;
		int quantity = 0;
		int people = 0;
		BigDecimal total = BigDecimal.ZERO;
		
		for(ProjectionItem pi:OSList){
			quantity = quantity + pi.getQuantity();
			people = people + (pi.getNumberOfPersonnel()== null? 0:pi.getNumberOfPersonnel());
			total = total.add(pi.getTotalCost());
			averageCost = averageCost.add(pi.getAverageCost());
		}
	
		averageCost = averageCost.divide(BigDecimal.valueOf(OSList.size()),2,RoundingMode.CEILING);
		total = averageCost.multiply(BigDecimal.valueOf(quantity));
		ProjectionItem ospi = new ProjectionItemImpl();
		ospi.setCostName("OS cost group");
		ospi.setItemCodeGroup("OS");
		ospi.setIsSupportCost(StringBooleanEnum.N);
		ospi.setItemId(null);
		ospi.setNumberOfPersonnel(people);
		ospi.setQuantity(quantity);
		ospi.setTotalCost(total);
		ospi.setAverageCost(averageCost);
	
		List<ProjectionItem> newList = new ArrayList<ProjectionItem>();
		Iterator<ProjectionItem> iter = vos.iterator();
		while (iter.hasNext()) {
			ProjectionItem pi = (ProjectionItem)iter.next();
		  if(!pi.getItemCodeGroup().equals("OS"))
			  newList.add(pi);
		}
		vos.clear();
		newList.add(ospi);
		vos.addAll(newList);
		
		return;
	}
	
	private void groupSupportCostProjectionItems(Collection<ProjectionItem> vos) {
		Collection<ProjectionItem> supportCostList = new ArrayList<ProjectionItem>(vos);		
		CollectionUtils.filter(supportCostList, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				ProjectionItem data = (ProjectionItem) object;
				return data.getIsSupportCost().getValue();
			}
		});
		
		if(supportCostList.isEmpty())
			return;
		
		BigDecimal averageCost = BigDecimal.ZERO;
		int quantity = 0;
		int people = 0;
		BigDecimal total = BigDecimal.ZERO;
		
		for(ProjectionItem pi:supportCostList){
			quantity = quantity + pi.getQuantity();
			people = people + (pi.getNumberOfPersonnel()== null? 0:pi.getNumberOfPersonnel());
			total = total.add(pi.getTotalCost());
			averageCost = averageCost.add(pi.getAverageCost());
		}
	    
	    averageCost = averageCost.divide(BigDecimal.valueOf(2),2,RoundingMode.CEILING);
		total = averageCost.multiply(BigDecimal.valueOf(quantity));
		ProjectionItem ospi = new ProjectionItemImpl();
		ospi.setCostName("Support cost group");
		ospi.setItemCodeGroup("Other Support");
		ospi.setIsSupportCost(StringBooleanEnum.Y);
		ospi.setItemId(null);
		ospi.setNumberOfPersonnel(people);
		ospi.setQuantity(quantity);
		ospi.setTotalCost(total);
		ospi.setAverageCost(averageCost);
	
		List<ProjectionItem> newList = new ArrayList<ProjectionItem>();
		Iterator<ProjectionItem> iter = vos.iterator();
		while (iter.hasNext()) {
			ProjectionItem pi = (ProjectionItem)iter.next();
		  if(!pi.getIsSupportCost().getValue())
			  newList.add(pi);
		}
		vos.clear();
		newList.add(ospi);
		vos.addAll(newList);
		
		return;
	}

	
	private Incident getIncident(Long incidentId) {
		
		Incident entity = null;
		
		try {
			if(incidentId != 0) {
				entity = incidentDao.getById(incidentId);
			}
		} catch (Exception e){
			super.dialogueException(null, e);
		}
		
		return entity;
	}
		
	private IncidentGroup getIncidentGroup(Long incidentGroupId) {
		
		IncidentGroup entity = new IncidentGroupImpl();
		
		try {
			if(incidentGroupId != 0) {
				entity = incidentGroupDao.getById(incidentGroupId,IncidentGroupImpl.class);
			}
		} catch (Exception e){
			super.dialogueException(null, e);
		}
		
		return entity;
	}
	
	public DialogueVo getManuallyAddProjectionItemCode(ProjectionVo projectionVo,DialogueVo dialogueVo) throws ServiceException{
		Collection<KindVo> itemCodes = new ArrayList<KindVo>();
		
		try {
			itemCodes = dao.getManuallyAddProjectionItemCode(projectionVo.getIncidentId(), projectionVo.getIncidentGroupId());
			
			CourseOfActionVo coaVo = getCourseOfActionVo("GET_MANUALLYADD_PROJECTION_ITEM_CODE",CourseOfActionTypeEnum.HANDLE_RECORDSET,null);
			dialogueVo.setResultObject(itemCodes);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(null, e);
		}
		return dialogueVo;
	}
	
	private Collection<ProjectionItem> getProjectionItemData(Projection projection, ProjectionVo projectionVo, String today) throws ServiceException{
		Collection<ProjectionItem> projectionItems = new ArrayList<ProjectionItem>();
	
		try {
			//projectionItems = dao.getProjectionItemData(projectionVo.getIncidentId(), projectionVo.getIncidentGroupId());
			projectionItems = dao.getProjectionItemDataForCurrentDay(projectionVo.getIncidentId(), projectionVo.getIncidentGroupId(),today);
			
			Collections.sort((List<ProjectionItem>)projectionItems, new Comparator<ProjectionItem>() {
				public int compare(ProjectionItem o1, ProjectionItem o2) {
					return o1.getItemId().compareTo(o2.getItemId());
				}
			});
			
			//consolidated OD/OS/other support cost
			groupODProjectionItems(projectionItems);
			groupOSProjectionItems(projectionItems);
			groupSupportCostProjectionItems(projectionItems);
			
			//Rule: the start day of a projection item worksheet needs at least one day after the projection creating date.
			Date startDate = projectionVo.getStartDate();
			
			for(ProjectionItem pi : projectionItems) { 
				pi.setProjection(projection);
				Kind kind = new KindImpl();
				kind.setId(pi.getItemId());
				//for OD,OS,Ohter Support there are no itemId  
				if(pi.getItemId()!= null)
					pi.setKind(kind);
				pi.setIsManuallyAdded(StringBooleanEnum.N);
				pi.setIsItemCodeActive(StringBooleanEnum.Y);
				if(pi.getIsSupportCost().equals(StringBooleanEnum.Y)) { 
					pi.setNumberOfPersonnel(getSupportingCostPeople(projectionItems));
					pi.setQuantity(getSupportingCostQuantity(projectionItems));
				}
				pi.getProjectionItemWorksheets().addAll(getProjectionItemWorkSheetData(pi,projectionVo.getNumberOfDays(),startDate)); 
			}
		}catch(Exception e){
			super.dialogueException(null, e);
		}
		return projectionItems;
	}

	private int getSupportingCostPeople(Collection<ProjectionItem> projectionItems) {
		int people = 0;
		for(ProjectionItem pi : projectionItems) { 
			if(!pi.getIsSupportCost().getValue())
				people = people + pi.getNumberOfPersonnel();
		}
		
		return people;
	}
	
	private int getSupportingCostQuantity(Collection<ProjectionItem> projectionItems) {
		int quantity = 0;
		for(ProjectionItem pi : projectionItems) { 
			if(!pi.getIsSupportCost().getValue())
				quantity = quantity + pi.getQuantity();
		}
		
		return quantity;
	}
	
	private Collection<ProjectionItem> getProjectionItemDataForCurrentDay(ProjectionVo projectionVo, String today, boolean isTrue) throws Exception{
		Collection<ProjectionItem> projectionItems = new ArrayList<ProjectionItem>();
	
		try {
			projectionItems = dao.getProjectionItemDataForCurrentDay(projectionVo.getIncidentId(), projectionVo.getIncidentGroupId(),today);
			
			if(projectionItems.isEmpty())
				throw new Exception("There is no current day's cost data. You must run Daily Costs to update a Cost Projection.");
			
			Collections.sort((List<ProjectionItem>)projectionItems, new Comparator<ProjectionItem>() {
				public int compare(ProjectionItem o1, ProjectionItem o2) {
					return o1.getItemId().compareTo(o2.getItemId());
				}
			});
			
			//consolidated OD/OS/other supporting cost items
			groupODProjectionItems(projectionItems);
			groupOSProjectionItems(projectionItems);
			groupSupportCostProjectionItems(projectionItems);
			
			Projection entity = new ProjectionImpl();
			entity.setId(projectionVo.getId());
			
			for(ProjectionItem pi : projectionItems) { 
				pi.setProjection(entity);
				Kind kind = new KindImpl();
				kind.setId(pi.getItemId());
				if(pi.getItemId()!=null)
					pi.setKind(kind);
				if(pi.getIsSupportCost().equals(StringBooleanEnum.Y)) { 
					pi.setNumberOfPersonnel(getSupportingCostPeople(projectionItems)); 
					pi.setQuantity(getSupportingCostQuantity(projectionItems)); 
				}
				pi.setIsManuallyAdded(StringBooleanEnum.N);
				pi.setIsItemCodeActive(StringBooleanEnum.Y);
			}
		}catch(Exception e){
			throw e;
		}
		return projectionItems;
	}
		
	private Date getNextDate(Date d){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
		
	}
	
	private Collection<ProjectionItemWorksheet> getProjectionItemWorkSheetData(ProjectionItem pi, int numOfDay,Date startDate) {
		Collection<ProjectionItemWorksheet> projectionItemWorksheets = new ArrayList<ProjectionItemWorksheet>();
		
		try {
			for(int i=0; i<numOfDay; i++) {
				ProjectionItemWorksheet piwsht = new ProjectionItemWorksheetImpl();
				piwsht.setProjectionItem(pi);
				if(pi.getIsSupportCost().getValue()) {
					piwsht.setAverageCost(pi.getTotalCost().divide(BigDecimal.valueOf(pi.getNumberOfPersonnel()),3,RoundingMode.HALF_DOWN));
				} else 
					piwsht.setAverageCost(pi.getAverageCost());
				piwsht.setNumberOfPersonnel(pi.getNumberOfPersonnel());
				piwsht.setProjectionDate(startDate);
				startDate = getNextDate(startDate);
				piwsht.setQuantity(pi.getQuantity());
				projectionItemWorksheets.add(piwsht);
			}
		}catch(Exception e){
			super.dialogueException(null, e);
		}
		return projectionItemWorksheets;
	}
	
	private Collection<ProjectionItemWorksheet> getProjectionItemWorkSheetData(ProjectionItem pi, int numOfDay,Date startDate, int people, BigDecimal total) {
		Collection<ProjectionItemWorksheet> projectionItemWorksheets = new ArrayList<ProjectionItemWorksheet>();
		int average = total.intValue()/people;
		try {
			for(int i=0; i<numOfDay; i++) {
				ProjectionItemWorksheet piwsht = new ProjectionItemWorksheetImpl();
				piwsht.setProjectionItem(pi);
				piwsht.setAverageCost(new BigDecimal(average));
				piwsht.setNumberOfPersonnel(people);
				piwsht.setProjectionDate(startDate);
				startDate = getNextDate(startDate);
				piwsht.setQuantity(pi.getQuantity());
				projectionItemWorksheets.add(piwsht);
			}
		}catch(Exception e){
			super.dialogueException(null, e);
		}
		return projectionItemWorksheets;
	}
	
	private void updateProjectionWorksheet(ProjectionItem pi,int days,Date startDate) {
		int size = 0;
		
		List<ProjectionItemWorksheet> oldList = (List<ProjectionItemWorksheet>)pi.getProjectionItemWorksheets();
		if(oldList != null && !oldList.isEmpty()) 
			size = oldList.size();
	
		if(size == 0) { //no worksheet for the projection item
			pi.setProjectionItemWorksheets(getProjectionItemWorkSheetData(pi,days,startDate));
		} else { //no size change, the size change is done in updating projection
			setProjectionWorksheetData(pi,size,startDate);
		}
	}
	
	private void updateProjectionWorksheetSize(ProjectionItem pi,int days,Date startDate) {
		
		List<ProjectionItemWorksheet> oldList = (List<ProjectionItemWorksheet>)pi.getProjectionItemWorksheets();
		
		Collections.sort((List<ProjectionItemWorksheet>)oldList, new Comparator<ProjectionItemWorksheet>() {
			public int compare(ProjectionItemWorksheet o1, ProjectionItemWorksheet o2) {
				return o1.getProjectionDate().compareTo(o2.getProjectionDate());
			}
		});
		
		//startDate = oldList.get(0).getProjectionDate();
		if(oldList == null || oldList.isEmpty()) 
			return;
		
		int size = oldList.size();
		if (size < days) { // add more days
			startDate = oldList.get(size-1).getProjectionDate(); 
			pi.addAllProjectionItemWorksheet(getProjectionItemWorkSheetData(pi,(days-size),getNextDate(startDate)));
		} else if(size > days) { //remove some days
			List<ProjectionItemWorksheet> newList =  new ArrayList<ProjectionItemWorksheet>(oldList);
			oldList.clear();
			for(int i=0; i<days; i++)
				oldList.add(newList.get(i));
		}
	}
	
	private void setProjectionWorksheetData(ProjectionItem pi, int updateCounter, Date startDate) {
		List<ProjectionItemWorksheet> projectionItemWorksheets = (List<ProjectionItemWorksheet>)pi.getProjectionItemWorksheets();
		
		Collections.sort((List<ProjectionItemWorksheet>)projectionItemWorksheets, new Comparator<ProjectionItemWorksheet>() {
			public int compare(ProjectionItemWorksheet o1, ProjectionItemWorksheet o2) {
				return o1.getProjectionDate().compareTo(o2.getProjectionDate());
			}
		});
		
		try {
			//for non supporting item only change the average cost
			for(int i=0; i < updateCounter; i++) {
				ProjectionItemWorksheet piwksht = projectionItemWorksheets.get(i);
				piwksht.updateObject(pi,startDate);
				startDate = getNextDate(startDate);
			}
			
			//for supporting item only change the personnel, new manually add item
			
		}catch(Exception e){
			super.dialogueException(null, e);
		}
	}
	
	private CourseOfActionVo getCourseOfActionVo(String name, CourseOfActionTypeEnum type, MessageVo messageVo){
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(name);
		coaVo.setCoaType(type);
		coaVo.setMessageVo(messageVo);
		coaVo.setIsDialogueEnding(true);
		return coaVo;
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostProjectionService#generateCostProjectionSummaryReport(gov.nwcg.isuite.core.reports.filter.CostProjectionReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateCostProjectionReport(CostProjectionReportFilter filter, ProjectionVo projectionVo, DialogueVo dialogueVo) throws Exception {
		IncidentGroup incidentGroup;
		Incident incident;
		if(projectionVo.getIncidentId() != 0) {
			incident = this.incidentDao.getById(projectionVo.getIncidentId());
			filter.incidentId = projectionVo.getIncidentId();
			filter.incidentName = incident.getIncidentName();
			filter.incidentGroupId = projectionVo.getIncidentGroupId();
			filter.incidentGroupName = "";
			filter.incidentNumber = incident.getIncidentNumber();
		}
			
		if(projectionVo.getIncidentGroupId() != 0) {
			incidentGroup = this.incidentGroupDao.getById(projectionVo.getIncidentGroupId());
			filter.incidentGroupId = projectionVo.getIncidentGroupId();
			filter.incidentName = incidentGroup.getGroupName();
			filter.incidentId = projectionVo.getIncidentId();
			filter.incidentName = "";
			filter.incidentNumber = "";
		}
		
		filter.projectionId = projectionVo.getId();
		filter.projectionName = projectionVo.getProjectionName();
		filter.days = projectionVo.getNumberOfDays();
		filter.startDate = projectionVo.getStartDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(filter.startDate);
		calendar.add(Calendar.DAY_OF_YEAR, filter.days-1);
		filter.endDate = calendar.getTime();
		
		CostProjectionReportGeneratorImpl rg;
		
		if(filter.isByTotalReport)
			rg = new CostProjectionTotalReportGeneratorImpl();
		else
			rg = new CostProjectionCategoryDetailReportGeneratorImpl();
		
		rg.setApplicationContext(super.context);
		rg.setServletContext(super.servletContext);
		rg.setUserSessionVo(getUserSessionVo());
		return rg.generateReport(filter, dialogueVo);
	}
	
	private Boolean isLocked(ProjectionVo projectionVo , DialogueVo dialogueVo) throws Exception{
		Long id=0L;
		String type="";
		
		if(null != projectionVo && null != projectionVo.getIncidentVo() && LongUtility.hasValue(projectionVo.getIncidentVo().getId())){
			id=projectionVo.getIncidentVo().getId();
			type="INCIDENT";
		}else if(null != projectionVo && null != projectionVo.getIncidentGroupVo() && LongUtility.hasValue(projectionVo.getIncidentGroupVo().getId())){
			id=projectionVo.getIncidentGroupVo().getId();
			type="INCIDENTGROUP";
		}else if(null != projectionVo && LongUtility.hasValue(projectionVo.getIncidentId())){
			id=projectionVo.getIncidentId();
			type="INCIDENT";
		}else if(null != projectionVo && LongUtility.hasValue(projectionVo.getIncidentGroupId())){
			id=projectionVo.getIncidentGroupId();
			type="INCIDENTGROUP";
		}

		for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
			IRule rule = SiteManagedRuleFactory.getInstance(re, context, id, type);
			
			if(null != rule){
				if(AbstractRule._OK != rule.executeRules(dialogueVo)){
					return true;
				}
			}
		}
		
		return false;
	}
}

