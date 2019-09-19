package gov.nwcg.isuite.core.service.impl;


import gov.nwcg.isuite.core.cost.CostAccrualExtractGenerator;
import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.impl.CostAccrualExtractImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.persistence.CostAccrualExtractDao;
import gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.reports.CostAccrualAllDetailReport;
import gov.nwcg.isuite.core.reports.CostAccrualDetailReport;
import gov.nwcg.isuite.core.reports.CostAccrualSummaryReport;
import gov.nwcg.isuite.core.reports.data.CostAccrualAllDetailReportData;
import gov.nwcg.isuite.core.reports.data.CostAccrualDetailReportData;
import gov.nwcg.isuite.core.reports.data.CostAccrualSummaryReportData;
import gov.nwcg.isuite.core.rules.CostAccrualFinalizeExtractRulesHandler;
import gov.nwcg.isuite.core.rules.CostAccrualRunExtractRulesHandler;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.service.CostAccrualService;
import gov.nwcg.isuite.core.vo.CostAccrualExtractVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.service.BaseReportService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.ReportBuilder2;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.ShortUtil;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.PhoneNumberUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;


public class CostAccrualServiceImpl extends BaseReportService implements CostAccrualService {
	private CostAccrualExtractDao dao = null;
	private CostAccrualExtractGenerator generator = null;
	
	public CostAccrualServiceImpl(){
	}

	public void initialization() {
		this.dao=(CostAccrualExtractDao)super.context.getBean("costAccrualExtractDao");
	}

	private String getDestinationFileName() throws Exception {
		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
		return "rpt" + timestamp + ".pdf";
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostAccrualService#finalizeAccrual(gov.nwcg.isuite.core.vo.CostAccrualExtractVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo runFinalize(CostAccrualExtractVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
		
		try{
			CostAccrualFinalizeExtractRulesHandler ruleHandler = new CostAccrualFinalizeExtractRulesHandler(context);
			if(ruleHandler.execute(vo,dialogueVo)==AbstractRule._OK){
				
				CostAccrualExtract entity = this.dao.getById(vo.getId(), CostAccrualExtractImpl.class);
				
				CostAccrualExtractVo updatedVo = null;

				Collection<Long> incidentIds = new ArrayList<Long>();
				
				if(null != entity){
					entity.setFinalized(StringBooleanEnum.Y);
					entity.setPreparedBy(vo.getPreparedBy());
					entity.setPreparedPhone(PhoneNumberUtil.digitOnlyPhoneNumberFormat(vo.getPreparedPhone()));
					entity.setFinalizedDate(DateTransferVo.getTransferDate(vo.getFinalizedDate()));
					
					// get next sequence number
					if(LongUtility.hasValue(vo.getIncidentId())){
						Incident inc = incidentDao.getById(vo.getIncidentId(),IncidentImpl.class);
						Integer nbr=(IntegerUtility.hasValue(inc.getAccrualExtractNumber()) ? inc.getAccrualExtractNumber() : 0 )+1;
						inc.setAccrualExtractNumber(nbr);
						incidentDao.save(inc);
						incidentDao.flushAndEvict(inc);
						entity.setSequenceNumber(ShortUtil.toShort(nbr));
					}else if(LongUtility.hasValue(vo.getIncidentGroupId())){
						IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
						IncidentGroup incGroup = incidentGroupDao.getById(vo.getIncidentGroupId(),IncidentGroupImpl.class);
						Integer nbr=(ShortUtil.hasValue(incGroup.getAccrualExtractNumber()) ? ShortUtil.toInteger(incGroup.getAccrualExtractNumber()) : 0 )+1;
						incGroup.setAccrualExtractNumber(ShortUtil.toShort(nbr));
						for(Incident i : incGroup.getIncidents()){
							incidentIds.add(i.getId());
							incidentGroupDao.flushAndEvict(i);
						}
						incidentGroupDao.save(incGroup);
						incidentGroupDao.flushAndEvict(incGroup);
						entity.setSequenceNumber(ShortUtil.toShort(nbr));
					}
					
					this.dao.save(entity);
					this.dao.flushAndEvict(entity);

					if(CollectionUtility.hasValue(incidentIds)){
						// for each incident in group, finalize extract
						for(Long incId : incidentIds){
							// get last non finalized extract for incident
							CostAccrualExtract incExtract=this.dao.getExtractByDate(incId, null, entity.getExtractDate());
							if(null != incExtract){
								Incident inc = incidentDao.getById(incId,IncidentImpl.class);
								Integer nbr=(IntegerUtility.hasValue(inc.getAccrualExtractNumber()) ? inc.getAccrualExtractNumber() : 0 )+1;
								inc.setAccrualExtractNumber(nbr);
								incidentDao.save(inc);
								incidentDao.flushAndEvict(inc);
								incExtract.setSequenceNumber(ShortUtil.toShort(nbr));
								incExtract.setFinalized(StringBooleanEnum.Y);
								incExtract.setPreparedBy(vo.getPreparedBy());
								incExtract.setPreparedPhone(PhoneNumberUtil.digitOnlyPhoneNumberFormat(vo.getPreparedPhone()));
								incExtract.setFinalizedDate(DateTransferVo.getTransferDate(vo.getFinalizedDate()));
								this.dao.save(incExtract);
								this.dao.flushAndEvict(incExtract);
								dao.flushAndEvict(inc);
							}
						}
					}
					
					entity = this.dao.getById(vo.getId(), CostAccrualExtractImpl.class);
				
					updatedVo = CostAccrualExtractVo.getInstance(entity, true);

					// get the totals
					BigDecimal totalAmt=dao.getTotalAmountByExtractId(vo.getId());
					BigDecimal changeAmt=dao.getTotalChangeAmountByExtractId(vo.getId());
					updatedVo.setTotalAmount(totalAmt);
					updatedVo.setChangeAmount(changeAmt);
					
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RUN_FINALIZE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.costAccruals","info.0030.02"
						, new String[]{"Cost Accrual data has been finalized for "+(DateUtil.toDateString(vo.getExtractDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY))+". The accrual data can no longer be updated."} 
				, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setResultObject(updatedVo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostAccrualService#getGrid(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			if(LongUtility.hasValue(incidentId)){
				// check if incident is part of group
				// if yes, always return incident group records
				IncidentDao incidentDao=(IncidentDao)context.getBean("incidentDao");
				Long groupId=incidentDao.getIncidentGroupId(incidentId);
				if(LongUtility.hasValue(groupId)){
					incidentGroupId=groupId;
					incidentId=null;
				}
			}
			
			Collection<CostAccrualExtractVo> vos = dao.getGrid(incidentId, incidentGroupId);
			
			/*
			 * for each vo in vos,
			 * set changeamount
			 */
			Collection<CostAccrualExtractVo> vos2 = new ArrayList<CostAccrualExtractVo>();
			for(CostAccrualExtractVo vo : vos){
				BigDecimal totalAmount=dao.getTotalAmountByExtractId(vo.getId());
				BigDecimal changeAmount=dao.getTotalChangeAmountByExtractId(vo.getId());

				vo.setTotalAmount(totalAmount);
				vo.setChangeAmount(changeAmount);
				
				vos2.add(vo);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("COST_ACCRUAL_GET_GRID");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setRecordset(vos2);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostAccrualService#runExtract(java.lang.String, java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo runExtract(String extractDate, Long incidentId,Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			this.generator = new CostAccrualExtractGenerator(this.dao,super.context);

			CostAccrualRunExtractRulesHandler ruleHandler = new CostAccrualRunExtractRulesHandler(context);
			if(ruleHandler.execute(extractDate, this.dao, incidentId, incidentGroupId,dialogueVo)==AbstractRule._OK){

				// collection of extractvo
				Collection<CostAccrualExtractVo> extractVos = new ArrayList<CostAccrualExtractVo>();

				Boolean isIncidentGroup=false;
				if(LongUtility.hasValue(incidentGroupId))
					isIncidentGroup=true;
				else{
					if(LongUtility.hasValue(incidentId)){
						// check if incident is part of group
						IncidentDao incidentDao=(IncidentDao)context.getBean("incidentDao");
						Long groupId=incidentDao.getIncidentGroupId(incidentId);
						if(LongUtility.hasValue(groupId)){
							incidentGroupId=groupId;
							isIncidentGroup=true;
						}
					}
				}
				
				// run for incident if there is an incidentId and no incidentgroupid
				//if(LongUtility.hasValue(incidentId) && !LongUtility.hasValue(incidentGroupId)){
				if(isIncidentGroup==false){
					CostAccrualExtractVo extractVo = generator.runExtractIncident(extractDate, incidentId);
					
					if(null != extractVo)
						extractVos.add(extractVo);
				}
				
				// run for incident group if there is an incidentGroupId
				if(LongUtility.hasValue(incidentGroupId)){
					// get incident group
					IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
					IncidentGroup igEntity = igDao.getById(incidentGroupId,IncidentGroupImpl.class);
					
					// for each incident in group, run extract
					for(Incident i : igEntity.getIncidents()){
						CostAccrualExtractGenerator generator2 = new CostAccrualExtractGenerator(this.dao,super.context);
						CostAccrualExtractVo extractVoInc = generator2.runExtractIncident(extractDate, i.getId());
					}
					
					// run for incident group 
					CostAccrualExtractVo extractVo = generator.runExtractIncidentGroup2(extractDate, incidentGroupId);
					if(null != extractVo)
						extractVos.add(extractVo);
					
					
					// for each incident in group, run extract
					/*
					for(Incident i : igEntity.getIncidents()){
						CostAccrualExtractGenerator generator2 = new CostAccrualExtractGenerator(this.dao,super.context);
						extractVo = generator2.runExtractIncident(extractDate, i.getId());
					}
					*/
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RUN_EXTRACT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.costAccruals","info.0030.01", new String[]{"Cost Accrual Extract for " + extractDate} , MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
						
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setRecordset(extractVos);
				dialogueVo.setResultObjectAlternate4(generator.deletedExtractIds);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveAccrualCode(IncidentAccountCodeVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			// get the IncidentAccount entity
			IncidentAccountCodeDao iacDao = (IncidentAccountCodeDao)context.getBean("incidentAccountCodeDao");
			IncidentAccountCode entity = iacDao.getById(vo.getId(), IncidentAccountCodeImpl.class);
			if(null != entity){
				Long incId=0L;
				if(null != entity.getIncident() && LongUtility.hasValue(entity.getIncident().getId())){
					incId=entity.getIncident().getId();
				}else if(LongUtility.hasValue(entity.getIncidentId())){
					incId=entity.getIncidentId();
				}
				
				for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
					IRule rule = SiteManagedRuleFactory.getInstance(re, context, incId, "INCIDENT");
					
					if(null != rule){
						if(AbstractRule._OK != rule.executeRules(dialogueVo)){
							iacDao.flushAndEvict(entity);
							return dialogueVo;
						}
					}
				}
				
				entity.setAccrualAccountCode(vo.getAccrualAccountCode());
				iacDao.save(entity);
				iacDao.flushAndEvict(entity);
				entity = iacDao.getById(vo.getId(), IncidentAccountCodeImpl.class);
				vo=IncidentAccountCodeVo.getInstance(entity, true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_ACCRUAL_CODE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.accrualCode","info.0030.01"
						, new String[]{"Accrual Code"} 
						, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setResultObject(vo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostAccrualService#generateAccrualSummaryReport(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateAccrualSummaryReport(Long extractId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			CostAccrualExtract entity = dao.getById(extractId, CostAccrualExtractImpl.class);
			if(null != entity){
				String header2="";
				String header4="";
//				Long prevExtractId = 0L;
				if(LongUtility.hasValue(entity.getIncidentId())){
					IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
					Incident incident = incidentDao.getById(entity.getIncidentId(),IncidentImpl.class);
					incidentDao.flushAndEvict(incident);
					header4=incident.getIncidentName()+" " + incident.getIncidentNumber();
//					prevExtractId = dao.getPreviousExtractId(extractId, entity.getIncidentId(),null);
				}else if(LongUtility.hasValue(entity.getIncidentGroupId())){
					IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
					IncidentGroup incidentGroup = incidentGroupDao.getById(entity.getIncidentGroupId(),IncidentGroupImpl.class);
					incidentGroupDao.flushAndEvict(incidentGroup);
					header4="(Incident Group) " + incidentGroup.getGroupName();
//					prevExtractId = dao.getPreviousExtractId(extractId, null,entity.getIncidentGroupId());
				}
				
				// get report data
				Collection<CostAccrualSummaryReportData> reportData = dao.getCostAccrualSummaryReportData(extractId);

				// init new report object
				IReport report = new CostAccrualSummaryReport(reportData);
				report.addCustomField("header1", DateUtil.toDateString(entity.getExtractDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
				String seqNumber=String.valueOf((ShortUtil.hasValue(entity.getSequenceNumber()) ? entity.getSequenceNumber() : 0));
				seqNumber=StringUtility.leftPad(seqNumber, "0", 3);
				report.addCustomField("header2",seqNumber);
				report.addCustomField("header3",(StringUtility.hasValue(entity.getPreparedBy()) ? (entity.getPreparedBy()+" "+PhoneNumberUtil.formatNumber(entity.getPreparedPhone()) ) : "") );
				report.addCustomField("header4", header4);
				ReportBuilder2 reportBuilder = new ReportBuilder2(servletContext);

				// Generate Report
				String reportName=getDestinationFileName();
				String outputFile=super.getOutputFile(reportName);
				reportBuilder.applicationContext=super.context;
				String reportFile=reportBuilder.createPdfReport(report, outputFile);
				String reportUrl=getOutputUrl(reportName);

				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("PRINT_ACCRUAL_SUMMARY");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(reportUrl);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostAccrualService#generateAccrualDetailReport(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateAccrualDetailReport(Long extractId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			CostAccrualExtract entity = dao.getById(extractId, CostAccrualExtractImpl.class);
			if(null != entity){
				String header2="";
				String header4="";
//				Long prevExtractId = 0L;
				if(LongUtility.hasValue(entity.getIncidentId())){
					IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
					Incident incident = incidentDao.getById(entity.getIncidentId(),IncidentImpl.class);
					incidentDao.flushAndEvict(incident);
					header4=incident.getIncidentName()+" " + incident.getIncidentNumber();
//					prevExtractId = dao.getPreviousExtractId(extractId, entity.getIncidentId(),null);
				}else if(LongUtility.hasValue(entity.getIncidentGroupId())){
					IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
					IncidentGroup incidentGroup = incidentGroupDao.getById(entity.getIncidentGroupId(),IncidentGroupImpl.class);
					incidentGroupDao.flushAndEvict(incidentGroup);
					header4="(Incident Group) " + incidentGroup.getGroupName();
//					prevExtractId = dao.getPreviousExtractId(extractId, null,entity.getIncidentGroupId());
				}
				
				// get report data
				Collection<CostAccrualDetailReportData> reportData = dao.getCostAccrualDetailReportData(extractId);
				
				// init new report object
				IReport report = new CostAccrualDetailReport(reportData);
				report.addCustomField("header1", DateUtil.toDateString(entity.getExtractDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
				String seqNumber=String.valueOf((ShortUtil.hasValue(entity.getSequenceNumber()) ? entity.getSequenceNumber() : 0));
				seqNumber=StringUtility.leftPad(seqNumber, "0", 3);
				report.addCustomField("header2",seqNumber);
				report.addCustomField("header3",(StringUtility.hasValue(entity.getPreparedBy()) ? (entity.getPreparedBy()+" "+PhoneNumberUtil.formatNumber(entity.getPreparedPhone()) ) : "") );
				report.addCustomField("header4", header4);
				ReportBuilder2 reportBuilder = new ReportBuilder2(servletContext);

				// Generate Report
				String reportName=getDestinationFileName();
				String outputFile=super.getOutputFile(reportName);
				reportBuilder.applicationContext=super.context;
				String reportFile=reportBuilder.createPdfReport(report, outputFile);
				String reportUrl=getOutputUrl(reportName);

				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("PRINT_ACCRUAL_DETAIL");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(reportUrl);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo generateAccrualAllDetailReport(Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Collection<CostAccrualExtractVo> vos = dao.getGrid(incidentId, incidentGroupId);
			Collection<CostAccrualAllDetailReportData> reportDataObjects = new ArrayList<CostAccrualAllDetailReportData>();
			
			// for each extract, build the report and sub report data
			for(CostAccrualExtractVo vo : vos ){
				String header4="";
				if(LongUtility.hasValue(vo.getIncidentId())){
					IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
					Incident incident = incidentDao.getById(vo.getIncidentId(),IncidentImpl.class);
					incidentDao.flushAndEvict(incident);
					header4=incident.getIncidentName()+" " + incident.getIncidentNumber();
				}else if(LongUtility.hasValue(vo.getIncidentGroupId())){
					IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
					IncidentGroup incidentGroup = incidentGroupDao.getById(vo.getIncidentGroupId(),IncidentGroupImpl.class);
					incidentGroupDao.flushAndEvict(incidentGroup);
					header4="(Incident Group) " + incidentGroup.getGroupName();
				}
				
				Collection<CostAccrualAllDetailReportData> reportDataList = dao.getCostAccrualAllDetailReportData(vo.getId());
				for(CostAccrualAllDetailReportData data : reportDataList) {
					data.setHeader1(DateUtil.toDateString(vo.getExtractDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
					data.setHeader2("");
					data.setHeader3((StringUtility.hasValue(vo.getPreparedBy()) ? (vo.getPreparedBy()+" "+PhoneNumberUtil.formatNumber(vo.getPreparedPhone())) : ""));
					data.setHeader4(header4);
				}
				
				reportDataObjects.addAll(reportDataList);
			}
			
//			for(CostAccrualExtractVo vo : vos ){
//			CostAccrualAllDetailReportData reportData = new CostAccrualAllDetailReportData();
//			String header4="";
////			Long prevExtractId = 0L;
//			if(LongUtility.hasValue(vo.getIncidentId())){
//				IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
//				Incident incident = incidentDao.getById(vo.getIncidentId(),IncidentImpl.class);
//				incidentDao.flushAndEvict(incident);
//				header4=incident.getIncidentName()+" " + incident.getIncidentNumber();
////				prevExtractId = dao.getPreviousExtractId(vo.getId(), vo.getIncidentId(),null);
//			}else if(LongUtility.hasValue(vo.getIncidentGroupId())){
//				IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
//				IncidentGroup incidentGroup = incidentGroupDao.getById(vo.getIncidentGroupId(),IncidentGroupImpl.class);
//				incidentGroupDao.flushAndEvict(incidentGroup);
//				header4="(Incident Group) " + incidentGroup.getGroupName();
////				prevExtractId = dao.getPreviousExtractId(vo.getId(), null,vo.getIncidentGroupId());
//			}
//			reportData.setHeader1(DateUtil.toDateString(vo.getExtractDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
//			reportData.setHeader2("");
//			reportData.setHeader3((StringUtility.hasValue(vo.getPreparedBy()) ? (vo.getPreparedBy()+" "+PhoneNumberUtil.formatNumber(vo.getPreparedPhone())) : ""));
//			reportData.setHeader4(header4);
//			
//			Collection<CostAccrualAllDetailSubReportData> subReportData = dao.getCostAccrualAllDetailSubReportData(vo.getId());
//			reportData.setSubReportData(subReportData);
//
//			//System.out.println(reportData.getHeader1());
//			reportDataObjects.add(reportData);
//		}
			
			
			// init new report object
			IReport report = new CostAccrualAllDetailReport(reportDataObjects);
			report.addCustomField("SUBREPORT_DIR", super.getSourcePath());
			ReportBuilder2 reportBuilder = new ReportBuilder2(servletContext);

			// Generate Report
			String reportName=getDestinationFileName();
			String outputFile=super.getOutputFile(reportName);
			reportBuilder.applicationContext=super.context;
			String reportFile=reportBuilder.createPdfReport(report, outputFile);
			String reportUrl=getOutputUrl(reportName);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("PRINT_ACCRUAL_ALL_DETAIL");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(reportUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

}
