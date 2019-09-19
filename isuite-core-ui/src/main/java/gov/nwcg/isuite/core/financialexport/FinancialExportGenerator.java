package gov.nwcg.isuite.core.financialexport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;

import gov.nwcg.isuite.core.domain.AdPaymentInfo;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.ContractorPaymentInfo;
import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AdPaymentInfoImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorPaymentInfoImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.core.persistence.CostAccrualExtractDao;
import gov.nwcg.isuite.core.persistence.FinancialExportDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.FinancialExportResourceDataVo;
import gov.nwcg.isuite.core.vo.FinancialExportVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.xml.financialexport.AccountingCodeSummaryType;
import gov.nwcg.isuite.xml.financialexport.AccrualDetailType;
import gov.nwcg.isuite.xml.financialexport.AccrualsType;
import gov.nwcg.isuite.xml.financialexport.AcctCodeSummary286Type;
import gov.nwcg.isuite.xml.financialexport.AdjustmentDetail286Type;
import gov.nwcg.isuite.xml.financialexport.AdjustmentSummary288Type;
import gov.nwcg.isuite.xml.financialexport.DBIncidentsType;
import gov.nwcg.isuite.xml.financialexport.ISuiteExport;
import gov.nwcg.isuite.xml.financialexport.ISuiteInvoice286Type;
import gov.nwcg.isuite.xml.financialexport.IncidentsType;
import gov.nwcg.isuite.xml.financialexport.Invoice286Type;
import gov.nwcg.isuite.xml.financialexport.Invoice288Type;
import gov.nwcg.isuite.xml.financialexport.PostingDetail286Type;
import gov.nwcg.isuite.xml.financialexport.PostingDetail288Type;
import gov.nwcg.isuite.xml.financialexport.PostingSummary286Type;
import gov.nwcg.isuite.xml.financialexport.PostingSummary288Type;
import gov.nwcg.isuite.xml.financialexport.RCLineSummaryType;

import org.springframework.context.ApplicationContext;


public class FinancialExportGenerator {
	private ApplicationContext context = null;
	private Long timeInvoiceId;
	private Collection<IncidentAcctCode> incidentAcctCodes = new ArrayList<IncidentAcctCode>();
	
	public FinancialExportGenerator(ApplicationContext ctx){
		this.context=ctx;
	}
	
	public ISuiteExport generateFinancialExport(FinancialExportVo fvo) throws Exception {
		
		ISuiteExport ie = new ISuiteExport();
		Collection<Incident> incidents = getIncidents(fvo);
		
		// djp - -09/05/2017 begin
		if(CollectionUtility.hasValue(incidents)){
			for(Incident i : incidents){
				boolean bIncluded=false;
				for(IncidentAcctCode iac : this.incidentAcctCodes){
					if(iac.getIncidentId().compareTo(i.getId())==0)
						bIncluded=true;
				}
				if(!bIncluded){
					IncidentAcctCode newIac = new IncidentAcctCode();
					newIac.setIncidentId(i.getId());
					newIac.setIncidentName(i.getIncidentName());
					newIac.setIncidentNumber(i.getIncidentNumber());
					if(CollectionUtility.hasValue(i.getIncidentAccountCodes())){
						for(IncidentAccountCode a : i.getIncidentAccountCodes()){
							newIac.getIncidentAccountCodes().add(a.getAccountCode().getAccountCode());
						}
					}
					this.incidentAcctCodes.add(newIac);
				}
			}
		}
		// djp - end
		
		Collection<Long> incidentIds = getIncidentIds(incidents);
		FinancialExportResourceDataVo rdVo = new FinancialExportResourceDataVo();
		
		if(null == fvo.getIncidentVo()){
			ie.setIncidentNumber(this.getIncidentNumber(fvo.getIncidentReferenceVo()));
			Date startDate=null;
			if(DateTransferVo.hasDateString(fvo.getIncidentReferenceVo().getIncStartDateTransferVo())){
				startDate=DateTransferVo.getDate(fvo.getIncidentReferenceVo().getIncStartDateTransferVo());
				ie.setIncidentStartDate(DateUtil.toDateString(startDate, DateUtil.YYYY_SLASH_MM_SLASH_DD));
			}
		}else{
			ie.setIncidentNumber(this.getIncidentNumber(fvo.getIncidentVo()));
			Date startDate=null;
			if(DateTransferVo.hasDateString(fvo.getIncidentVo().getIncStartDateTransferVo())){
				startDate=DateTransferVo.getDate(fvo.getIncidentVo().getIncStartDateTransferVo());
				ie.setIncidentStartDate(DateUtil.toDateString(startDate, DateUtil.YYYY_SLASH_MM_SLASH_DD));
			}else if(null != fvo.getIncidentReferenceVo() 
					&& DateTransferVo.hasDateString(fvo.getIncidentReferenceVo().getIncStartDateTransferVo())){
				startDate=DateTransferVo.getDate(fvo.getIncidentReferenceVo().getIncStartDateTransferVo());
				ie.setIncidentStartDate(DateUtil.toDateString(startDate, DateUtil.YYYY_SLASH_MM_SLASH_DD));
			}
		}
		
		
		ie.setExportDateTime(DateUtil.toDateString(new Date(), DateUtil.YYYY_SLASH_MM_SLASH_DD) + " " + DateUtil.toMilitaryTime(new Date()));
		
		try {
			//<DBIncidents>
			//ie.getDBIncidents().addAll(this.getDBIncidents(incidents));
			Collection<? extends DBIncidentsType> dbIncidentsType = this.getDBIncidents(incidents);
			//ie.getDBIncidents().addAll(dbIncidentsType);
			
			//<Accruals>
			//ie.getAccruals().addAll(this.getAccrualTypes(incidents, fvo, incidentIds));
			Collection<AccrualsType> accrualsType=this.getAccrualTypes(incidents, fvo, incidentIds);
			ie.getAccruals().addAll(accrualsType);
			
			//<ISuiteInvoice286>
			//ie.getISuiteInvoice286().addAll(this.getISuiteInvoice286Types(incidentIds));
			Collection<ISuiteInvoice286Type> invoice286Types = this.getISuiteInvoice286Types(incidentIds);
			ie.getISuiteInvoice286().addAll(invoice286Types);
			
			//<Invoice288>
			//ie.getInvoice288().addAll(this.getInvoice288Types(incidentIds));
			Collection<Invoice288Type> invoice288 = this.getInvoice288Types(incidentIds);
			ie.getInvoice288().addAll(invoice288);

			// add incidents that have data
			for(DBIncidentsType dit : dbIncidentsType){
				boolean hasData=false;
				
				for(AccrualsType at : accrualsType){
					for(IncidentsType it : at.getIncidents()){
						if(it.getIncidentNumber().equalsIgnoreCase(dit.getNumber())){
							hasData=true;
							break;
						}
					}
				}
				
				if(!hasData){
					for(ISuiteInvoice286Type inv286Type : invoice286Types){
						if(inv286Type.getInvoice286().getIncidentNumber().equalsIgnoreCase(dit.getNumber())){
							hasData=true;
							break;
						}
					}
				}
				
				if(!hasData){
					for(Invoice288Type inv288Type : invoice288){
						for(PostingSummary288Type pst : inv288Type.getPostingSummary288()){
							if(pst.getIncidentNumber().equalsIgnoreCase(dit.getNumber())){
								hasData=true;
								break;
							}
						}
						
					}
				}

				if(hasData==true)
					ie.getDBIncidents().add(dit);
					
			}
			
		}catch(Exception e){
			
			if(LongUtility.hasValue(timeInvoiceId)){
				FinancialExportDao feDao = (FinancialExportDao)context.getBean("financialExportDao");
				rdVo = feDao.getFinancialExportResourceData(timeInvoiceId);
				
				throw new Exception("Resource: " + FinancialExportResourceDataVo.getResourceInfo(rdVo) + " Exception: " + e.getMessage());
			}else{
				throw new Exception("Exception: " + e.getMessage());
			}
		}
		
		return ie;
	}
	
	public String getIncidentNumber(IncidentVo iVo) {
		String dash = "-";
		StringBuffer b = new StringBuffer();
		
		if(iVo.getHomeUnitVo() != null && iVo.getCountryCodeSubdivisionVo() != null) {
			b.append(iVo.getCountryCodeSubdivisionVo().getCountrySubAbbreviation()).append(dash)
			.append(iVo.getHomeUnitVo().getUnitCode().substring(3)).append(dash)
			.append(iVo.getIncidentNumber());   
		} else {
			b.append("N/A");
		}

		return b.toString();
	}
	
	private Collection<Incident> getIncidents(FinancialExportVo fvo) throws Exception {
//		if(null == fvo.getIncidentVo()){
//			throw new IllegalArgumentException("Incident cannot be null");
//		}
		
		Collection<Incident> incidents = new ArrayList<Incident>();
		
		try {
			if(null != fvo.getIncidentGroupVo()) {
				IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				
				IncidentGroup ig = igDao.getById(fvo.getIncidentGroupVo().getId(), IncidentGroupImpl.class);
				
				if(null != ig) {
					incidents.addAll(ig.getIncidents());
				}
			}else {
				IncidentDao iDao = (IncidentDao)context.getBean("incidentDao");
				
				incidents.add(iDao.getById(fvo.getIncidentVo().getId(), IncidentImpl.class));
			}
			
		}catch(Exception e){
			throw e;
		}
		
		return incidents;
	}
	
	private Collection<Long> getIncidentIds(Collection<Incident> incidents) throws Exception {
		Collection<Long> incidentIds = new ArrayList<Long>();
		
		for(Incident incident : incidents) {
			incidentIds.add(incident.getId());
		}
		
		return incidentIds;
	}
	
	private Collection<DBIncidentsType> getDBIncidents(Collection<Incident> incidents) throws Exception {
		
		Collection<DBIncidentsType> list = new ArrayList<DBIncidentsType>();
			
		for(Incident incident : incidents) {
			DBIncidentsType dbIncidentType = new DBIncidentsType();
			
			dbIncidentType.setName(StringUtility.fixString(incident.getIncidentName(), 20));
			dbIncidentType.setNumber(StringUtility.fixString(incident.getIncidentNumber().substring(3), 13));
			dbIncidentType.setStartDate(DateUtil.toDateString(incident.getIncidentStartDate(), DateUtil.YYYY_SLASH_MM_SLASH_DD));
			
			list.add(dbIncidentType);
		}
		
		return list;
	}
	
	private Collection<ISuiteInvoice286Type> getISuiteInvoice286Types(Collection<Long> incidentIds) throws Exception{
		Collection<ISuiteInvoice286Type> iSuiteInvoice286Types = new ArrayList<ISuiteInvoice286Type>();
		
		try{
			TimeInvoiceDao timeInvoiceDao = (TimeInvoiceDao)context.getBean("timeInvoiceDao");
			Collection<TimeInvoice> inv286List = timeInvoiceDao.getUnexportedInvoices(incidentIds, Boolean.TRUE);

			/* The 286 invoice code is creating duplicate join records in 
			 * the assign_time_post_invoice and time_assign_adj_invoice tables.
			 * 
			 */
			for(TimeInvoice ti : inv286List){
				//System.out.println(ti.getId());
				timeInvoiceDao.cleanDuplicateInvoicePostings(ti.getId());
				timeInvoiceDao.cleanDuplicateInvoiceAdjustments(ti.getId());
				timeInvoiceDao.flushAndEvict(ti);
			}
			
			// reload list
			inv286List = timeInvoiceDao.getUnexportedInvoices(incidentIds, Boolean.TRUE);
			
			ContractorPaymentInfo conInfo = new ContractorPaymentInfoImpl();
			WorkPeriod wp = new WorkPeriodImpl();
			
			for (TimeInvoice inv286 : inv286List){
				Long irId=0L;
				timeInvoiceId = inv286.getId();
				
				for(AssignmentTimePost post : inv286.getAssignmentTimePosts()) {
					conInfo = post.getAssignmentTime().getContractorPaymentInfo();
					
					for(WorkPeriod workPeriod : post.getAssignmentTime().getAssignment().getWorkPeriods()){
						irId=workPeriod.getIncidentResourceId();

						if(workPeriod.getDMReleaseDate() == null){
							wp = workPeriod;
							break;
						}
					}
					
					break;
				}
				
				ISuiteInvoice286Type iInv286Type = new ISuiteInvoice286Type();
				
				// get resNumId from isw_incident_resource
				String resnum="0000";
				if(LongUtility.hasValue(irId)){
					Long resNumId=timeInvoiceDao.getResNumId(irId);
					if(LongUtility.hasValue(resNumId)){
						if(LongUtility.hasValue(resNumId)){
							String strNum=String.valueOf(resNumId);
							resnum=StringUtility.leftPad(strNum, "0", 4);
						}
					}
				}
				
				iInv286Type.setInvoiceID(resnum);

				//iInv286Type.setInvoiceID(inv286.getId().toString());
				iInv286Type.setBeginDate(DateUtil.toDateString(inv286.getFirstIncludeDate(), DateUtil.YYYY_SLASH_MM_SLASH_DD));
				iInv286Type.setEndDate(DateUtil.toDateString(inv286.getLastIncludeDate(), DateUtil.YYYY_SLASH_MM_SLASH_DD));
				iInv286Type.setOfficialNum("1");
				iInv286Type.setInvoiceReason("Original");
				if(null != conInfo.getContractor()){
					iInv286Type.setContractorName(StringUtility.fixString(conInfo.getContractor().getName(), 50));
					// djp 08/22/2017 only allow 30 chars max, adding 1 since the fixstring method is subtracting 1
					iInv286Type.setContractorAddress1(StringUtility.fixString(conInfo.getContractor().getAddress().getAddressLine1(), 31));
					iInv286Type.setContractorAddress2(StringUtility.fixString(conInfo.getContractor().getAddress().getAddressLine2(), 31));
					iInv286Type.setContractorCity(StringUtility.fixString(conInfo.getContractor().getAddress().getCity(), 30));
					if(null!= conInfo.getContractor().getAddress().getCountrySubdivision()) {
						iInv286Type.setContractorState(StringUtility.fixString(conInfo.getContractor().getAddress().getCountrySubdivision().getAbbreviation(), 4));
					}
					iInv286Type.setContractorZip(StringUtility.fixString(conInfo.getContractor().getAddress().getPostalCode(), 10));
				}else{
					throw new Exception("Contractor");
				}
				
				iInv286Type.setAgreementNum(StringUtility.fixString(conInfo.getContractorAgreement().getAgreementNumber(), 30));
				iInv286Type.setAgreementBegDt(DateUtil.toDateString(conInfo.getContractorAgreement().getStartDate(), DateUtil.YYYY_SLASH_MM_SLASH_DD));
				iInv286Type.setAgreementExpDt(DateUtil.toDateString(conInfo.getContractorAgreement().getEndDate(), DateUtil.YYYY_SLASH_MM_SLASH_DD));
				iInv286Type.setResourceKindDescription(StringUtility.fixString(conInfo.getAssignmentTime().getAssignment().getKind().getDescription(), 100));
				iInv286Type.setUniqueName(StringUtility.fixString(conInfo.getVinName(), 90));
				iInv286Type.setMake(StringUtility.fixString((StringUtility.hasValue(conInfo.getDesc1()) ? conInfo.getDesc1() : ""), 90));
				iInv286Type.setModel(StringUtility.fixString((StringUtility.hasValue(conInfo.getDesc2()) ? conInfo.getDesc2() : ""), 90));
				iInv286Type.setPointOfHire(StringUtility.fixString(conInfo.getContractorAgreement().getPointOfHire(), 30));
				
				if(null != wp){
					iInv286Type.setHireDate(DateUtil.toDateString(wp.getCICheckInDate(), DateUtil.YYYY_SLASH_MM_SLASH_DD));
					iInv286Type.setHireTime("0000");
				}else{
					iInv286Type.setHireDate("");
					iInv286Type.setHireTime("0000");
				}
				
				if(null != conInfo.getContractorAgreement().getAdminOffice()) {
					//iInv286Type.setAdminOffice(conInfo.getContractorAgreement().getAdminOffice().getOfficeName().substring(0, 29));
//					String officeName=conInfo.getContractorAgreement().getAdminOffice().getOfficeName();
//					if(StringUtility.hasValue(officeName)
//								&& officeName.length()>30){
//						officeName=StringUtility.leftTrim(officeName, 30);
//					}
					iInv286Type.setAdminOffice(StringUtility.fixString(conInfo.getContractorAgreement().getAdminOffice().getOfficeName(), 30));
					if(null != conInfo.getContractorAgreement().getAdminOffice().getAddress()) {
						iInv286Type.setAdminOfficeAddress1(StringUtility.fixString(conInfo.getContractorAgreement().getAdminOffice().getAddress().getAddressLine1(), 35));
						iInv286Type.setAdminOfficeAddress2(StringUtility.fixString(conInfo.getContractorAgreement().getAdminOffice().getAddress().getAddressLine2(), 35));
						iInv286Type.setAdminOfficeCity(StringUtility.fixString(conInfo.getContractorAgreement().getAdminOffice().getAddress().getCity(), 30));
						if(null != conInfo.getContractorAgreement().getAdminOffice().getAddress().getCountrySubdivision()) {
							iInv286Type.setAdminOfficeState(StringUtility.fixString(conInfo.getContractorAgreement().getAdminOffice().getAddress().getCountrySubdivision().getAbbreviation(), 4));
						}
						iInv286Type.setAdminOfficeZip(StringUtility.fixString(conInfo.getContractorAgreement().getAdminOffice().getAddress().getPostalCode(), 10));
					}
				}
				
				iInv286Type.setGovtSuppliesYN(conInfo.getSupplies() ? "1" : "0");
				iInv286Type.setGovtOperatorYN(conInfo.getOperation() ? "1" : "0");
				iInv286Type.setRequestNumber(StringUtility.fixString(conInfo.getAssignmentTime().getAssignment().getRequestNumber(), 20));
				
				if(null != this.getReleaseDate(conInfo.getAssignmentTime().getAssignment().getWorkPeriods())) {
					iInv286Type.setReleaseDate(DateUtil.toDateString(this.getReleaseDate(conInfo.getAssignmentTime().getAssignment().getWorkPeriods()), DateUtil.YYYY_SLASH_MM_SLASH_DD));
					iInv286Type.setReleaseTime(DateUtil.toMilitaryTime(this.getReleaseDate(conInfo.getAssignmentTime().getAssignment().getWorkPeriods())));
				}
				
				iInv286Type.setWithdrawn(conInfo.getWithdrawn() ? "1" : "0");
				
				if(null == conInfo.getAssignmentTime().getOfRemarks()){
					iInv286Type.setRemarks("");
				}else{
					iInv286Type.setRemarks(StringUtility.fixString(conInfo.getAssignmentTime().getOfRemarks(), 256));
				}
				
				iInv286Type.setTotalDeductions(get286TotalDeduction(inv286));
				iInv286Type.setTotalAdditions(get286TotalAddition(inv286));
				
				iInv286Type.getAdjustmentDetail286().addAll(this.getAdjustmentDetail286Types(inv286.getTimeAssignmentAdjusts()));
				
				iInv286Type.setInvoice286(this.getInvoice286Type(inv286));
				
				iSuiteInvoice286Types.add(iInv286Type);
				
				timeInvoiceId = 0L;
			}
			
		}catch(Exception e){
			throw e;
		}
		
		return iSuiteInvoice286Types;
	}
	
	private Collection<AdjustmentDetail286Type> getAdjustmentDetail286Types(Collection<TimeAssignAdjust> adjustments) throws Exception {
		Collection<AdjustmentDetail286Type> adjustmentDetail286Types = new ArrayList<AdjustmentDetail286Type>();
		
		for(TimeAssignAdjust adj : adjustments) {
			AdjustmentDetail286Type adjDetType = new AdjustmentDetail286Type();
			if(null != adj.getActivityDate()){
				adjDetType.setAdjustmentDate(DateUtil.toDateString(adj.getActivityDate(),DateUtil.YYYY_SLASH_MM_SLASH_DD));
			}else{
				adjDetType.setAdjustmentDate("");
			}
			adjDetType.setDescription(StringUtility.fixString(adj.getCommodity(), 75));
			if(AdjustmentTypeEnum.DEDUCTION == adj.getAdjustmentType()){
				adjDetType.setDeductionAmount(adj.getAdjustmentAmount().setScale(2,2));
				adjDetType.setAdditionAmount(new BigDecimal(0));
			}else{
				adjDetType.setAdditionAmount(adj.getAdjustmentAmount().setScale(2,2));
				adjDetType.setDeductionAmount(new BigDecimal(0));
			}
			adjustmentDetail286Types.add(adjDetType);
		}
		
		return adjustmentDetail286Types;
	}

	private Invoice286Type getInvoice286Type(TimeInvoice inv286) throws Exception {
		Invoice286Type invoice286Type = new Invoice286Type();
		AssignmentTimePost timePost = null;
		
		invoice286Type.setInvoiceNumber(StringUtility.fixString(inv286.getInvoiceNumber(), 35));
		
		for(AssignmentTimePost tp : inv286.getAssignmentTimePosts()) {
			timePost = tp;
			break;
		}
		
		if(null != timePost) {
			invoice286Type.setIncidentName(StringUtility.fixString(timePost.getIncidentAccountCode().getIncident().getIncidentName(), 20));
			invoice286Type.setIncidentNumber(timePost.getIncidentAccountCode().getIncident().getIncidentNumber().substring(3));
		}
		
		invoice286Type.getAcctCodeSummary286().add(this.getAcctCodeSummary286Type(inv286.getAssignmentTimePosts()));
		
		return invoice286Type;
	}
	
	private AcctCodeSummary286Type getAcctCodeSummary286Type(Collection<AssignmentTimePost> assignmentTimePosts) throws Exception {
		AcctCodeSummary286Type acctCodeSummary286Type = new AcctCodeSummary286Type();
		AssignmentTimePost post = new AssignmentTimePostImpl();
		
		for(AssignmentTimePost atp : assignmentTimePosts) {
			post = atp;
			break;
		}
		
		acctCodeSummary286Type.setAccountingCode(StringUtility.fixString(post.getIncidentAccountCode().getAccountCode().getAccountCode(), 50));
		Collection<PostingSummary286Type> postingSummary286Types = this.getPostingSummary286Types(assignmentTimePosts);
		
		acctCodeSummary286Type.setAccountingCodeTotals(this.getAccountingCodeTotals(postingSummary286Types));
		
		acctCodeSummary286Type.getPostingSummary286().addAll(postingSummary286Types);
		
		return acctCodeSummary286Type;
	}
	
	private Collection<PostingSummary286Type> getPostingSummary286Types(Collection<AssignmentTimePost> assignmentTimePosts) throws Exception {
		Collection<PostingSummary286Type> postingSummary286Types = this.getPostingSummary286TypesCollection(assignmentTimePosts);
		
		for(PostingSummary286Type ps286Type : postingSummary286Types){
			
			BigDecimal guaranteeAmount = new BigDecimal(0.00);
			BigDecimal totalAmountEarned = new BigDecimal(0.00);
			
			for(AssignmentTimePost atp : assignmentTimePosts) {
				BigDecimal ga = new BigDecimal(0.00);
				
				if(null != atp.getRefContractorRate().getGuaranteeAmount()){
					if(atp.getGuaranteePosting() && atp.getIsHalfRate()) {
						ga = ga.add(atp.getRefContractorRate().getGuaranteeAmount().multiply(new BigDecimal(0.50)));
					}else{
						ga =  atp.getRefContractorRate().getGuaranteeAmount();
					}
				}
				
				if(ga.compareTo(guaranteeAmount) > 0){
					guaranteeAmount = ga;
				}
			}
			
			Collection<PostingDetail286Type> detail286Types = new ArrayList<PostingDetail286Type>();
			
			/*
			for(AssignmentTimePost atp : assignmentTimePosts) {
				if(ps286Type.getActivityDate().equalsIgnoreCase(DateUtil.toDateString(atp.getPostStartDate(),DateUtil.YYYY_SLASH_MM_SLASH_DD))){
					
					PostingDetail286Type pdType = this.getPostingDetail286Type(atp);
					detail286Types.add(pdType);
					ps286Type.getPostingDetail286().add(this.getPostingDetail286Type(atp));
					
					if(atp.getGuaranteePosting()){
						if(atp.getIsHalfRate()){
							totalAmountEarned = totalAmountEarned.add(atp.getRefContractorRate().getGuaranteeAmount().multiply(new BigDecimal(0.50)));
						}else{
							totalAmountEarned = totalAmountEarned.add(atp.getRefContractorRate().getGuaranteeAmount());
						}
					}else{
						if(atp.getContractorPostType().equalsIgnoreCase("PRIMARY")){
							totalAmountEarned = totalAmountEarned.add(pdType.getAmount());
						}else{
							totalAmountEarned = totalAmountEarned.add(pdType.getSAmount());
						}
					}
				}
			} 
			*/
			
			
			Collection<Long> uniqueContrRateIds = new ArrayList<Long>();
			for(AssignmentTimePost atp : assignmentTimePosts) {
				if(ps286Type.getActivityDate().equalsIgnoreCase(DateUtil.toDateString(atp.getPostStartDate(),DateUtil.YYYY_SLASH_MM_SLASH_DD))){
					if(!uniqueContrRateIds.contains(atp.getRefContractorRateId()))
						uniqueContrRateIds.add(atp.getRefContractorRateId());
				}
			}
			
			for(Long rateId : uniqueContrRateIds){
				PostingDetail286Type pdTypeCombined = null;
				String postType="";
				Boolean isHalfRate = false;
				Boolean isDaily=false;
				BigDecimal guar = new BigDecimal(0);
				
				for(AssignmentTimePost atp : assignmentTimePosts) {
					if(ps286Type.getActivityDate().equalsIgnoreCase(DateUtil.toDateString(atp.getPostStartDate(),DateUtil.YYYY_SLASH_MM_SLASH_DD))
							&& rateId.compareTo(atp.getRefContractorRateId())==0){

						guar=atp.getRefContractorRate().getGuaranteeAmount();
						postType=atp.getContractorPostType();
						PostingDetail286Type pdType = this.getPostingDetail286Type(atp);
						if(pdTypeCombined==null)
							pdTypeCombined=pdType;
						else{
							if(postType.equalsIgnoreCase("PRIMARY")){
								double v = Double.valueOf(pdTypeCombined.getNumberOfUnits());
								v=v+Double.valueOf(pdType.getNumberOfUnits());
								pdTypeCombined.setNumberOfUnits(String.valueOf(v));
							}else if(postType.equalsIgnoreCase("SPECIAL")){
								double v = Double.valueOf(pdTypeCombined.getSNumberOfUnits());
								v=v+Double.valueOf(pdType.getSNumberOfUnits());
								pdTypeCombined.setSNumberOfUnits(String.valueOf(v));
							}
						}
						if(BooleanUtility.isTrue(atp.getIsHalfRate()) && DecimalUtil.hasValue(guar)){
							isHalfRate=true;
						}
						if(BooleanUtility.isTrue(atp.getIsHalfRate()) && atp.getUnitOfMeasure().equalsIgnoreCase("DAILY")){
							isHalfRate=true;
							isDaily=true;
						}
					}
				} 
				
				if(isHalfRate && isDaily){
					if(postType.equalsIgnoreCase("SPECIAL")){
						BigDecimal r = pdTypeCombined.getSRate().multiply(new BigDecimal(0.50));
						pdTypeCombined.setSRate(r);
					}else{
						BigDecimal r = pdTypeCombined.getRate().multiply(new BigDecimal(0.50));
						pdTypeCombined.setRate(r);
					}
					//double v = Double.valueOf(pdTypeCombined.getNumberOfUnits());
					//pdTypeCombined.setAmount(r.multiply(new BigDecimal(v)).setScale(2,2));
				}else if(isHalfRate){
					if(postType.equalsIgnoreCase("SPECIAL")){
					}else{
						guar=guar.multiply(new BigDecimal(0.50)).setScale(2,2);
						pdTypeCombined.setGuarantee(guar);
					}
				}
				
				if(postType.equalsIgnoreCase("PRIMARY")){
					if(!StringUtility.hasValue(pdTypeCombined.getNumberOfUnits()))
						pdTypeCombined.setNumberOfUnits("0");
					if(!DecimalUtil.hasValue(pdTypeCombined.getRate()))
						pdTypeCombined.setRate(new BigDecimal(0));
					
					Double d = Double.valueOf(pdTypeCombined.getNumberOfUnits());
					pdTypeCombined.setAmount(pdTypeCombined.getRate().multiply(BigDecimal.valueOf(d)).setScale(2,2));
				}else if(postType.equalsIgnoreCase("SPECIAL")){
					Double d = Double.valueOf(pdTypeCombined.getSNumberOfUnits());
					pdTypeCombined.setAmount(pdTypeCombined.getSRate().multiply(BigDecimal.valueOf(d)).setScale(2,2));
					pdTypeCombined.setSAmount(pdTypeCombined.getSRate().multiply(BigDecimal.valueOf(d)).setScale(2,2));
				}else{
					System.out.println("");
				}
				ps286Type.getPostingDetail286().add(pdTypeCombined);
				
			}

			for(PostingDetail286Type pd : ps286Type.getPostingDetail286()){
				if(pd.getAmount().doubleValue() > pd.getGuarantee().doubleValue()){
					totalAmountEarned = totalAmountEarned.add(pd.getAmount());
				}else{
					totalAmountEarned = totalAmountEarned.add(pd.getGuarantee());
				}
			}
			
			/*
			for(Long rateId : uniqueContrRateIds){
				BigDecimal total = new BigDecimal(0);
				Boolean isHalfRate = false;
				BigDecimal guar = new BigDecimal(0);
				
				for(AssignmentTimePost atp : assignmentTimePosts) {
					if(ps286Type.getActivityDate().equalsIgnoreCase(DateUtil.toDateString(atp.getPostStartDate(),DateUtil.YYYY_SLASH_MM_SLASH_DD))
							&& rateId.compareTo(atp.getRefContractorRateId())==0){

						
						guar=atp.getRefContractorRate().getGuaranteeAmount();
						total = total.add(atp.getQuantity().multiply(atp.getRateAmount()).setScale(2,2));

						if(BooleanUtility.isTrue(atp.getIsHalfRate()) && DecimalUtil.hasValue(guar)){
							isHalfRate=true;
						}
					}
				}				
				
				if(isHalfRate && DecimalUtil.hasValue(guar)){
					guar=guar.multiply(new BigDecimal(0.50)).setScale(2,2);
				}
				
				if(total.doubleValue()>guar.doubleValue()){
					totalAmountEarned = totalAmountEarned.add(total);
				}else{
					totalAmountEarned = totalAmountEarned.add(guar);
				}
			}
			*/
			
			ps286Type.setTotalAmountEarned(totalAmountEarned.setScale(2,2));
			
			//ps286Type.setGuarantee(guaranteeAmount.setScale(2,2));
			ps286Type.setGuarantee(new BigDecimal(0).setScale(2,2));
			ps286Type.setPostTotal(ps286Type.getTotalAmountEarned());

			/*
			if (ps286Type.getTotalAmountEarned().compareTo(guaranteeAmount) > 0){
				ps286Type.setPostTotal(ps286Type.getTotalAmountEarned());
			}else{
				ps286Type.setPostTotal(guaranteeAmount.setScale(2,2));
			}
			*/
			
		}
		
		return postingSummary286Types;
	}
	
//	private Collection<PostingSummary286Type> getPostingSummary286Types(Collection<AssignmentTimePost> assignmentTimePosts) throws Exception {
//		Collection<PostingSummary286Type> postingSummary286Types = this.getPostingSummary286TypesCollection(assignmentTimePosts);
//		BigDecimal guaranteeAmount = null;
//		
//		for(PostingSummary286Type ps286Type : postingSummary286Types){
//			
//			for(AssignmentTimePost atp : assignmentTimePosts) {
//				if(ps286Type.getActivityDate().equalsIgnoreCase(DateUtil.toDateString(atp.getPostStartDate(),DateUtil.YYYY_SLASH_MM_SLASH_DD))){
//					if(null == atp.getGuaranteeAmount()){
//						ps286Type.getPostingDetail286().add(this.getPostingDetail286Type(atp));
//					}else{
//						guaranteeAmount = atp.getGuaranteeAmount().setScale(2);
//					}
//					
//					if(null != atp.getRefContractorRate().getGuaranteeAmount()){
//						ps286Type.setGuarantee(atp.getRefContractorRate().getGuaranteeAmount().setScale(2));
//					}
//				}
//			}
//			if(null != guaranteeAmount){
//				ps286Type.setTotalAmountEarned(guaranteeAmount);
//				ps286Type.setPostTotal(guaranteeAmount);
//			}else{
//				ps286Type.setTotalAmountEarned(get286PostAmts(ps286Type));
//				ps286Type.setPostTotal(get286PostAmts(ps286Type));
//			}
//			guaranteeAmount = null;
//		}
//		
//		return postingSummary286Types;
//	}
	
//	private BigDecimal get286PostAmts(PostingSummary286Type psType){
//		
//		BigDecimal bd = new BigDecimal(0);
//		
//		for(PostingDetail286Type pdType: psType.getPostingDetail286()){
//			if(null != pdType.getAmount()){
//				bd = bd.add(pdType.getAmount());
//			}
//			if(null != pdType.getSAmount()){
//				bd = bd.add(pdType.getSAmount());
//			}
//		}
//		
//		return bd.setScale(2,2);
//	}
	
	private Collection<PostingSummary286Type> getPostingSummary286TypesCollection(Collection<AssignmentTimePost> assignmentTimePosts) throws Exception {
		Collection<PostingSummary286Type> postingSummary286Types = new ArrayList<PostingSummary286Type>();
		String summaryActivityDate = null;
		PostingSummary286Type psType = new PostingSummary286Type();
		
		for(AssignmentTimePost atp : assignmentTimePosts) {
			String postActivityDate = DateUtil.toDateString(atp.getPostStartDate(),DateUtil.YYYY_SLASH_MM_SLASH_DD);
			
			if(!postActivityDate.equalsIgnoreCase(summaryActivityDate)){
				psType = new PostingSummary286Type();
				psType.setActivityDate(postActivityDate);
				summaryActivityDate = DateUtil.toDateString(atp.getPostStartDate(),DateUtil.YYYY_SLASH_MM_SLASH_DD);
				psType.setGuarantee(new BigDecimal(0.00).setScale(2,2));
				postingSummary286Types.add(psType);
			}
		}
		
		return postingSummary286Types;
	}
	
	private Collection<PostingSummary288Type> getPostingSummary288TypesCollection(Collection<AssignmentTimePost> assignmentTimePosts) throws Exception {
		Collection<PostingSummary288Type> postingSummary288Types = new ArrayList<PostingSummary288Type>();
		
		//String summaryActivityDate = null;
		PostingSummary288Type postSummary;
		
		for(AssignmentTimePost atp : assignmentTimePosts) {
			postSummary = new PostingSummary288Type();
			
			postSummary.setIncidentNumber(atp.getIncidentAccountCode().getIncident().getIncidentNumber().substring(3));
			postSummary.setAccountingCode(StringUtility.fixString(atp.getIncidentAccountCode().getAccountCode().getAccountCode(), 50));
			postSummary.setClassCode(atp.getRateClassRate().getRateClassName());
			postSummary.setClassRate(atp.getRateClassRate().getRate().setScale(2,2).toString());
			postSummary.setKindCode(StringUtility.fixString(atp.getKind().getCode(), 10));
			
			if(null != atp.getPostStartDate()) {
				postSummary.setPostingYear(DateUtil.toDateString(atp.getPostStartDate(), DateUtil.YYYY));
			}
			
			Boolean bMatch = false;
			
			if(postingSummary288Types.size() < 1){
				postingSummary288Types.add(postSummary);
			}else{
				for(PostingSummary288Type pst : postingSummary288Types){
					if(pst.getIncidentNumber().equalsIgnoreCase(postSummary.getIncidentNumber())
							&&
							pst.getAccountingCode().equalsIgnoreCase(postSummary.getAccountingCode())
							&&
							pst.getClassCode().equalsIgnoreCase(postSummary.getClassCode())
							&&
							pst.getClassRate().equalsIgnoreCase(postSummary.getClassRate())
							&&
							pst.getKindCode().equalsIgnoreCase(postSummary.getKindCode())
							&&
							pst.getPostingYear().equalsIgnoreCase(postSummary.getPostingYear())
					){
						bMatch=true;
					}
					/*
					if(!pst.getIncidentNumber(). equalsIgnoreCase(postSummary.getIncidentNumber())){
						bMatch = false;
					}
					if(!pst.getAccountingCode().equalsIgnoreCase(postSummary.getAccountingCode())){
						bMatch = false;
					}
					if(!pst.getClassCode().equalsIgnoreCase(postSummary.getClassCode())){
						bMatch = false;
					}
					if(!pst.getClassRate().equalsIgnoreCase(postSummary.getClassRate())){
						bMatch = false;
					}
					if(!pst.getKindCode().equalsIgnoreCase(postSummary.getKindCode())){
						bMatch = false;
					}
					if(!pst.getPostingYear().equalsIgnoreCase(postSummary.getPostingYear())){
						bMatch = false;
					}
					if(bMatch){
						break;
					}
					*/
				}
				
				if(!bMatch){
					postingSummary288Types.add(postSummary);
				}
				
			}
			
		}
		
		//System.out.println("postingSummary288Types Count: " + postingSummary288Types.size());
		return postingSummary288Types;
	}
	
//	private Collection<PostingSummary286Type> getPostingSummary286Types(Collection<AssignmentTimePost> assignmentTimePosts) throws Exception {
//		Collection<PostingSummary286Type> postingSummary286Types = new ArrayList<PostingSummary286Type>();
//		
//		for(AssignmentTimePost atp : assignmentTimePosts) {
//			
//			PostingSummary286Type postSumm286Type = new PostingSummary286Type();
//			
//			postSumm286Type.setTotalAmountEarned(get286PostAmt(atp));
//			postSumm286Type.setPostTotal(get286PostAmt(atp));
//			
//			
//			if(null == atp.getRefContractorRate().getGuaranteeAmount()){
//				postSumm286Type.setGuarantee(new BigDecimal(0.00).setScale(2));
//			}else{
//				postSumm286Type.setGuarantee(atp.getRefContractorRate().getGuaranteeAmount().setScale(2));
//			}
//			
//			if(null != atp.getPostStartDate()){
//				postSumm286Type.setActivityDate(DateUtil.toDateString(atp.getPostStartDate(),DateUtil.YYYY_SLASH_MM_SLASH_DD));
//			}
//			
//			if(null == atp.getGuaranteeAmount()){
//				postSumm286Type.getPostingDetail286().add(this.getPostingDetail286Type(atp));
//			}
//			
//			postingSummary286Types.add(postSumm286Type);
//		}
//		
//		return postingSummary286Types;
//	}
	
	private PostingDetail286Type getPostingDetail286Type(AssignmentTimePost atp) throws Exception {
		PostingDetail286Type postingDetail286Type = new PostingDetail286Type();
		
		if(null != atp.getRefContractorRate()
				&& DecimalUtil.hasValue(atp.getRefContractorRate().getGuaranteeAmount()))
			postingDetail286Type.setGuarantee(atp.getRefContractorRate().getGuaranteeAmount().setScale(2,2));
		else
			postingDetail286Type.setGuarantee(new BigDecimal(0).setScale(2,2));
		
		//System.out.println(atp.getRefContractorRate().getGuaranteeAmount());
		
		if(atp.getContractorPostType().equalsIgnoreCase("PRIMARY")){
			if(!atp.getGuaranteePosting()){
				if(atp.getUnitOfMeasure().equalsIgnoreCase("DAILY") || atp.getUnitOfMeasure().equalsIgnoreCase("HOURLY")){
					if(null == atp.getPostStartDate()) {
						postingDetail286Type.setStartTime("0000");
					}else{
						postingDetail286Type.setStartTime(DateUtil.toMilitaryTime(atp.getPostStartDate()));
					}
					
					if(null == atp.getPostStopDate()) {
						postingDetail286Type.setStopTime("0000");
					}else{
						postingDetail286Type.setStopTime(DateUtil.toMilitaryTime(atp.getPostStopDate()));
					}
				}
				
				postingDetail286Type.setNumberOfUnits(atp.getQuantity().toString());
				postingDetail286Type.setUnitOfMeasure(StringUtility.fixString(atp.getUnitOfMeasure(), 20));
				if(null != atp.getRateAmount()){
					//if(atp.getIsHalfRate()){
					//	postingDetail286Type.setRate(atp.getRateAmount().multiply(new BigDecimal(0.50)).setScale(3,3));
					//}else{
						postingDetail286Type.setRate(atp.getRateAmount().setScale(3,3));
				//	}
					
//					postingDetail286Type.setAmount(get286PostAmt(atp));
					postingDetail286Type.setAmount(atp.getQuantity().multiply(postingDetail286Type.getRate()).setScale(2,2));
				}
			}
		}else{
			if(atp.getUnitOfMeasure().equalsIgnoreCase("HOURLY")){
				if(null == atp.getPostStartDate()) {
//					postingDetail286Type.setSStartTime("0000");
				}else{
					String postStartDate = DateUtil.toMilitaryTime(atp.getPostStartDate());
					if(!postStartDate.equalsIgnoreCase("0000")){
						postingDetail286Type.setSStartTime(postStartDate);
					}
				}
				
				if(null == atp.getPostStopDate()) {
//					postingDetail286Type.setSStopTime("0000");
				}else{
					postingDetail286Type.setSStopTime(DateUtil.toMilitaryTime(atp.getPostStopDate()));
				}
			}
			
			postingDetail286Type.setSNumberOfUnits(atp.getQuantity().toString());
			postingDetail286Type.setSUnitOfMeasure(StringUtility.fixString(atp.getUnitOfMeasure(), 20));
			if(null != atp.getRateAmount()){
				//if(atp.getIsHalfRate()){
				//	postingDetail286Type.setSRate(atp.getRateAmount().multiply(new BigDecimal(0.50)).setScale(3,3));
				//}else{
					postingDetail286Type.setSRate(atp.getRateAmount().setScale(3,3));
				//}
				
//				postingDetail286Type.setSAmount(get286PostAmt(atp));
				postingDetail286Type.setSAmount(atp.getQuantity().multiply(postingDetail286Type.getSRate()).setScale(2,2));
			}
		}
		
		return postingDetail286Type;
	}
	
	private Collection<Invoice288Type> getInvoice288Types(Collection<Long> incidentIds) throws Exception{
		Collection<Invoice288Type> invoice288Types = new ArrayList<Invoice288Type>();
		
		try{
			TimeInvoiceDao invDao = (TimeInvoiceDao)context.getBean("timeInvoiceDao");
			Collection<TimeInvoice> timeInvoices = invDao.getUnexportedInvoices(incidentIds, Boolean.FALSE);
			
			for(TimeInvoice timeInvoice : timeInvoices) {
				AdPaymentInfo adInfo = new AdPaymentInfoImpl();
				timeInvoiceId = timeInvoice.getId();
				
				// 2017/08/16 djp run test and see if order by id or by post date ( do some postings out of order to test)
				for(AssignmentTimePost post : timeInvoice.getAssignmentTimePosts()) {
					adInfo = post.getAssignmentTime().getAdPaymentInfo();
					break;
				}
				
				Invoice288Type inv288Type = new Invoice288Type();
				inv288Type.setInvoiceID(timeInvoice.getId().toString());
				inv288Type.setECI(StringUtility.fixString(adInfo.getEci(), 30));
				
				if(null != adInfo.getPointOfHire() && adInfo.getPointOfHire().length() > 3){
					inv288Type.setPointOfHire(StringUtility.fixString(adInfo.getPointOfHire().substring(0, 2).concat(adInfo.getPointOfHire().substring(3)), 90));
				}else{
					inv288Type.setPointOfHire(StringUtility.fixString(adInfo.getPointOfHire(), 90));
				}
				
				
				for(Resource resource : timeInvoice.getResources()) {
					inv288Type.setFirstName(StringUtility.fixString(resource.getFirstName(), 35));
					inv288Type.setLastName(StringUtility.fixString(resource.getLastName(), 35));
					break;
				}
				
				inv288Type.setInvoiceNumber(StringUtility.fixString(timeInvoice.getInvoiceNumber(), 28));
				
				inv288Type.getAdjustmentSummary288().addAll(this.getAdjustmentSummary288Types(timeInvoice.getTimeAssignmentAdjusts()));
				
				inv288Type.getPostingSummary288().addAll(this.getPostingSummary288Types(timeInvoice.getAssignmentTimePosts()));
				
				invoice288Types.add(inv288Type);
				
				timeInvoiceId = 0L;
			}	
		}catch(Exception e){
			throw e;
		}
		
		return invoice288Types;
	}
	
	private Collection<AdjustmentSummary288Type> getAdjustmentSummary288Types(Collection<TimeAssignAdjust> adjusts) throws Exception {
		Collection<AdjustmentSummary288Type> adjustmentSummary288Types = new ArrayList<AdjustmentSummary288Type>();
		
		for(TimeAssignAdjust adjust : adjusts){
			AdjustmentSummary288Type adjustSummary = new AdjustmentSummary288Type();
			
			adjustSummary.setAdjustmentAmountSummary(adjust.getAdjustmentAmount().setScale(2,2));
			
			if(null != adjust.getAdjustCategory()) {
				adjustSummary.setAdjustmentCategory(StringUtility.fixString(adjust.getAdjustCategory().getCode(), 10));
			}else{
				throw new Exception("Adjustment Category");
			}
			
			if(null != adjust.getActivityDate()) {
				adjustSummary.setAdjustmentDate(DateUtil.toDateString(adjust.getActivityDate(), DateUtil.YYYY_SLASH_MM_SLASH_DD));
			}
			
			adjustmentSummary288Types.add(adjustSummary);
		}
		
		return adjustmentSummary288Types;
	}
	
	private Collection<PostingSummary288Type> getPostingSummary288Types(Collection<AssignmentTimePost> assignmentTimePosts) throws Exception {
		Collection<PostingSummary288Type> postingSummary288Types = getPostingSummary288TypesCollection(assignmentTimePosts);
		Collection<AssignmentTimePostingDetail288Type> atp288Types = getAssignmentTimePostingDetail288Types(assignmentTimePosts);
		
		for(PostingSummary288Type ps288Type : postingSummary288Types) {
			for(AssignmentTimePostingDetail288Type atp : atp288Types) {
				Boolean bMatch = true;
				
				if(!ps288Type.getIncidentNumber(). equalsIgnoreCase(atp.getIncidentNumber())){
					bMatch = false;
				}
				if(!ps288Type.getAccountingCode().equalsIgnoreCase(atp.getAccountingCode())){
					bMatch = false;
				}
				if(!ps288Type.getClassCode().equalsIgnoreCase(atp.getClassCode())){
					bMatch = false;
				}
				if(!ps288Type.getClassRate().equalsIgnoreCase(atp.getClassRate())){
					bMatch = false;
				}
				if(!ps288Type.getKindCode().equalsIgnoreCase(atp.getKindCode())){
					bMatch = false;
				}
				if(!ps288Type.getPostingYear().equalsIgnoreCase(atp.getPostingYear())){
					bMatch = false;
				}
				
				if(bMatch){
					PostingDetail288Type postDetail = new PostingDetail288Type();
					
					postDetail.setActivityDate(atp.getActivityDate());
					postDetail.setStartTime(atp.getStartTime());
					postDetail.setStopTime(atp.getStopTime());
					postDetail.setSpecialPayCode(StringUtility.fixString(atp.getSpecialPayCode(), 10));
					postDetail.setReportOrder(atp.getReportOrder());
					postDetail.setColumnNumber(atp.getColumnNumber());
					
					ps288Type.getPostingDetail288().add(postDetail);
				}
			}
		}
		
		return postingSummary288Types;
	}
	
	private Collection<AssignmentTimePostingDetail288Type> getAssignmentTimePostingDetail288Types(Collection<AssignmentTimePost> assignmentTimePosts) throws Exception {
		Collection<AssignmentTimePostingDetail288Type> atp288Types = new ArrayList<AssignmentTimePostingDetail288Type>();
		
		int groupCnt=0;
		int reportOrder=0;
		int columnNumber=0;
		String groupClassification="";
		String newGroupClassification="";
		
		for(AssignmentTimePost atp : assignmentTimePosts) {
			AssignmentTimePostingDetail288Type postDetail = new AssignmentTimePostingDetail288Type();
			
			postDetail.setIncidentNumber(atp.getIncidentAccountCode().getIncident().getIncidentNumber().substring(3));
			postDetail.setAccountingCode(atp.getIncidentAccountCode().getAccountCode().getAccountCode());
			postDetail.setClassCode(atp.getRateClassRate().getRateClassName());
			postDetail.setClassRate(atp.getRateClassRate().getRate().setScale(2,2).toString());
			postDetail.setKindCode(atp.getKind().getCode());
			postDetail.setPostingYear(DateUtil.toDateString(atp.getPostStartDate(), DateUtil.YYYY));
			
			if(null != atp.getPostStartDate()) {
				postDetail.setActivityDate(DateUtil.toDateString(atp.getPostStartDate(), DateUtil.YYYY_SLASH_MM_SLASH_DD));
				postDetail.setStartTime(DateUtil.toMilitaryTime(atp.getPostStartDate()));
			}
			if(null != atp.getPostStopDate()) {
				postDetail.setStopTime(DateUtil.toMilitaryTime(atp.getPostStopDate()));
			}
			
			if(null != atp.getSpecialPay()) {
				String specialPayCode = atp.getSpecialPay().getCode();
				postDetail.setSpecialPayCode(specialPayCode);
				
				if(specialPayCode.equalsIgnoreCase("GUAR") ||  specialPayCode.equalsIgnoreCase("COP") || specialPayCode.equalsIgnoreCase("DAY OFF")) {
					postDetail.setStartTime("0002");
					
					Integer quantity = atp.getQuantity().intValue();
					
					if(quantity.toString().length() < 2){
						postDetail.setStopTime("0" + quantity.toString() + "02");
					}else{
						postDetail.setStopTime(quantity.toString() + "02");
					}
				}
			}
			//add report order and column number
			newGroupClassification = atp.getIncidentAccountCode().getIncident().getIncidentName();
			newGroupClassification=newGroupClassification+"-"+atp.getIncidentAccountCode().getIncident().getHomeUnit().getUnitCode();
			newGroupClassification=newGroupClassification+"-"+atp.getIncidentAccountCode().getIncident().getIncidentNumber();
			
			newGroupClassification=newGroupClassification+"-"+DateUtil.getYearAsInt(atp.getPostStartDate());
			
			if(null != atp.getIncidentAccountCode().getAccountCode().getRegionUnit()
				&& StringUtility.hasValue(atp.getIncidentAccountCode().getAccountCode().getRegionUnit().getCode())) {
				newGroupClassification=newGroupClassification+"-"+atp.getIncidentAccountCode().getAccountCode().getRegionUnit().getCode()
				+ "/" + atp.getIncidentAccountCode().getAccountCode().getAccountCode();
			}else{
				newGroupClassification=newGroupClassification+"-"+atp.getIncidentAccountCode().getAccountCode().getAccountCode();
			}
			
			if(atp.getAssignmentTime().getEmploymentType()==EmploymentTypeEnum.OTHER){
				newGroupClassification=newGroupClassification+"-"+atp.getKind().getCode();
				if(atp.getOtherRate() == null){
					newGroupClassification=newGroupClassification+"-"+"0.0";
				}else{
					newGroupClassification=newGroupClassification+"-"+atp.getOtherRate().doubleValue();
				}
			}
			
			if(atp.getAssignmentTime().getEmploymentType()==EmploymentTypeEnum.AD){
				if(atp.getRateClassRate() == null){
					newGroupClassification=newGroupClassification+"-"+atp.getKind().getCode();
				}else{
					newGroupClassification=newGroupClassification+"-"+atp.getRateClassRate().getRateClassName();
				}
				if(atp.getRateAmount() == null){
					newGroupClassification=newGroupClassification+"-"+"0.0";
				}else{
					newGroupClassification=newGroupClassification+"-"+atp.getRateAmount().doubleValue();
				}
			}
			
			if(atp.getAssignmentTime().getEmploymentType()==EmploymentTypeEnum.FED){
				newGroupClassification=newGroupClassification+"-"+atp.getKind().getCode();
				if(atp.getRateAmount() == null) {
					newGroupClassification=newGroupClassification+"-"+"0.0";
				}else{
					newGroupClassification=newGroupClassification+"-"+atp.getRateAmount().doubleValue();
				}
			}
			
			if(StringUtility.hasValue(groupClassification)){
				if(groupClassification.equals(newGroupClassification)){
					if( (groupCnt + 1) == 8){
						columnNumber++;
						groupCnt=1;
					}else{
						groupCnt+=1;
					}
				}else{
					groupCnt=1;
					columnNumber++;

					groupClassification=newGroupClassification;
				}
			}else{
				groupClassification=newGroupClassification;
				groupCnt=1;
				columnNumber++;
			}

			postDetail.setColumnNumber(Integer.toString(columnNumber));
			postDetail.setReportOrder(Integer.toString(reportOrder++));
			
			atp288Types.add(postDetail);
		}
		
		return atp288Types;
	}
	
	private Collection<AccrualsType> getAccrualTypes(Collection<Incident> incidents, FinancialExportVo fvo, Collection<Long> incidentIds) throws Exception {
		Collection<AccrualsType> accrualsTypes = new ArrayList<AccrualsType>();
		
		try {
			Collection<CostAccrualExtract> accruals = null;
			CostAccrualExtractDao caeDao = (CostAccrualExtractDao)context.getBean("costAccrualExtractDao");
			
			accruals = caeDao.getUnexportedExtracts(incidentIds);
			
			for(CostAccrualExtract accrual : accruals) {
				AccrualsType accrualsType = new AccrualsType();
				
				accrualsType.setAccrualHeaderID(accrual.getId());
				
				if(null != accrual.getExtractDate()) {
					accrualsType.setCaptureDate(DateUtil.toDateString(accrual.getExtractDate(), DateUtil.YYYY_SLASH_MM_SLASH_DD));
					accrualsType.setCaptureTime(DateUtil.toMilitaryTime(accrual.getExtractDate()));
					accrualsType.setCaptureTime(DateUtil.toMilitaryTime(accrual.getExtractDate()));
				}
				
				accrualsType.setPreparedByName(StringUtility.fixString(accrual.getPreparedBy(),30));
				accrualsType.setPreparedByPhone(StringUtility.fixString(accrual.getPreparedPhone(), 10));
				
				// djp 09/05/2017 accrualsType.getIncidents().addAll(this.getIncidentsTypes(incidents));
				accrualsType.getAccountingCodeSummary().addAll(this.getAccountingCodeSummaryTypes(accrual.getId()));
				
				// djp 09/05/2017
				// loop through incident account code data and see if we have accrual data for the account code
				Collection<String> includeIncidentNumbers = new ArrayList<String>();
				for(IncidentAcctCode iac : this.incidentAcctCodes){
					for(AccountingCodeSummaryType at : accrualsType.getAccountingCodeSummary()){
						String atAcctCode=at.getAccountingCode().substring(0, (at.getAccountingCode().length()-2));
						for(String s : iac.getIncidentAccountCodes()){
							if(atAcctCode.equalsIgnoreCase(s)){
								if(!includeIncidentNumbers.contains(iac.getIncidentNumber())){
									includeIncidentNumbers.add(iac.getIncidentNumber());
								}
							}
							
						}
					}
				}
				Collection<IncidentsType> incidentTypes=this.getIncidentsTypes(incidents);
				for(IncidentsType it : incidentTypes){
					boolean binclude=false;
					for(String s : includeIncidentNumbers){
						if(s.equalsIgnoreCase("US-"+it.getIncidentNumber()))
								binclude=true;
					}
					if(binclude==true){
						accrualsType.getIncidents().add(it);
					}
				}
				//accrualsType.getIncidents().addAll(this.getIncidentsTypes(incidents));
				if(null != accrualsType
						&& CollectionUtility.hasValue(accrualsType.getAccountingCodeSummary())){
					accrualsTypes.add(accrualsType);
				}
					
			}
			
		}catch(Exception e){
			throw e;
		}
		
		return accrualsTypes;
	}
	
	private Collection<IncidentsType> getIncidentsTypes(Collection<Incident> incidents) throws Exception {
		Collection<IncidentsType> incidentsTypes = new ArrayList<IncidentsType>();
		
		for(Incident incident : incidents) {
			IncidentsType incidentType = new IncidentsType();
			
			incidentType.setIncidentName(StringUtility.fixString(incident.getIncidentName(), 20));
			incidentType.setIncidentNumber(incident.getIncidentNumber().substring(3));
			
			incidentsTypes.add(incidentType);
		}
		
		return incidentsTypes;
	}
	
	private Collection<AccountingCodeSummaryType> getAccountingCodeSummaryTypes(Long extractId) throws Exception {
		
		Collection<AccountingCodeSummaryType> acsTypes = new ArrayList<AccountingCodeSummaryType>();
		
		CostAccrualExtractDao caeDao = (CostAccrualExtractDao)context.getBean("costAccrualExtractDao");
		acsTypes = caeDao.getAccountingCodeSummaryTypes(extractId);

		for(AccountingCodeSummaryType acsType : acsTypes) {
			acsType.setAccountingCode(StringUtility.fixString(acsType.getAccountingCode(), 20));
			
			if(null != acsType.getSubChangeAmount()) {
				acsType.setSubChangeAmount(acsType.getSubChangeAmount().setScale(2,2));
			}
			
			if(null != acsType.getSubPrevAmount()) {
				acsType.setSubPrevAmount(acsType.getSubPrevAmount().setScale(2,2));
			}
			
			if(null != acsType.getSubTotalAmount()) {
				acsType.setSubTotalAmount(acsType.getSubTotalAmount().setScale(2,2));
			}
			
			acsType.getRCLineSummary().addAll(this.getRCLineSummaryTypes(extractId, acsType.getAccountingCode()));
		}
		
		return acsTypes;
	}
	
	private Collection<RCLineSummaryType> getRCLineSummaryTypes(Long extractId, String accountCodeYr) throws Exception {
		Collection<RCLineSummaryType> rlsTypes = new ArrayList<RCLineSummaryType>();
		
		CostAccrualExtractDao caeDao = (CostAccrualExtractDao)context.getBean("costAccrualExtractDao");
		rlsTypes = caeDao.getRCLineSummaryTypes(extractId, accountCodeYr);
		
		for(RCLineSummaryType rlsType : rlsTypes) {
			rlsType.setRCLineNumber(StringUtility.fixString(rlsType.getRCLineNumber(), 5));
			rlsType.setRCLineDescription(StringUtility.fixString(rlsType.getRCLineDescription(), 40));
			
			if(null != rlsType.getRCLineChangeAmount()) {
				rlsType.setRCLineChangeAmount(rlsType.getRCLineChangeAmount().setScale(2,2));
			}
			if(null != rlsType.getRCLinePrevAmount()) {
				rlsType.setRCLinePrevAmount(rlsType.getRCLinePrevAmount().setScale(2,2));
			}
			if(null != rlsType.getRCLineTotalAmount()) {
				rlsType.setRCLineTotalAmount(rlsType.getRCLineTotalAmount().setScale(2,2));
			}
			
			rlsType.setAccrualCode(StringUtility.fixString(rlsType.getAccrualCode(), 5));
			
			rlsType.getAccrualDetail().addAll(this.getAccrualDetailTypes(extractId, accountCodeYr, rlsType.getAccrualCode()));
		}
		
		return rlsTypes;
	}
	
	private Collection<AccrualDetailType> getAccrualDetailTypes(Long extractId, String accountCodeYr, String accrualCode) throws Exception {
		Collection<AccrualDetailType> adTypes = new ArrayList<AccrualDetailType>();
		
		CostAccrualExtractDao caeDao = (CostAccrualExtractDao)context.getBean("costAccrualExtractDao");
		adTypes = caeDao.getAccrualDetailTypes(extractId, accountCodeYr, accrualCode);
		
		for(AccrualDetailType adType : adTypes) {
			adType.setName(StringUtility.fixString(adType.getName(), 53));
			adType.setRequestNumber(StringUtility.fixString(adType.getRequestNumber(), 20));
			adType.setKindCode(StringUtility.fixString(adType.getKindCode(), 4));
			adType.setHomeUnitCode(StringUtility.fixString(adType.getHomeUnitCode(), 6));
			
			if(null != adType.getTotalAmount()) {
				adType.setTotalAmount(adType.getTotalAmount().setScale(2,2));
			}else{
				// dan-8/3/2017 asc wants node element as zero instead of null 
				adType.setTotalAmount(new BigDecimal(0).setScale(2,2));
			}
			if(null != adType.getChangeAmount()) {
				adType.setChangeAmount(adType.getChangeAmount().setScale(2,2));
			}else{
				// dan-8/3/2017 asc wants node element as zero instead of null 
				adType.setChangeAmount(new BigDecimal(0).setScale(2,2));
			}
			if(null != adType.getPrevAmount()) {
				adType.setPrevAmount(adType.getPrevAmount().setScale(2,2));
			}else{
				// dan-8/3/2017 asc wants node element as zero instead of null 
				adType.setPrevAmount(new BigDecimal(0).setScale(2,2));
			}
		}
		
		return adTypes;
	}
	
	private BigDecimal get286TotalDeduction(TimeInvoice inv){
		Collection<TimeAssignAdjust> adjustList = inv.getTimeAssignmentAdjusts();
		BigDecimal total = new BigDecimal(0);
		
		for(TimeAssignAdjust adj : adjustList){
			if(AdjustmentTypeEnum.DEDUCTION == adj.getAdjustmentType()){
				total = total.add(adj.getAdjustmentAmount());
			}
		}
		
		if(total.compareTo(new BigDecimal(0)) == 1) {
			return total.setScale(2,2);
		}
		return null;
	}
	

	private BigDecimal get286TotalAddition(TimeInvoice inv){
		Collection<TimeAssignAdjust> adjustList = inv.getTimeAssignmentAdjusts();
		BigDecimal total = new BigDecimal(0);
		
		for(TimeAssignAdjust adj : adjustList){
			if(AdjustmentTypeEnum.ADDITION == adj.getAdjustmentType()){
				total = total.add(adj.getAdjustmentAmount());
			}
		}
		
		if(total.compareTo(new BigDecimal(0)) == 1) {
			return total.setScale(2,2);
		}
		
		return null;
	}
	
//	private BigDecimal get286PostAmt(AssignmentTimePost post){
//		
//		BigDecimal bd = post.getRateAmount();		
//		bd = bd.multiply(post.getQuantity());
//		
//		return bd.setScale(2,2);
//	}
	
	private BigDecimal getAccountingCodeTotals(Collection<PostingSummary286Type> postingSummary286Types) throws Exception {
		BigDecimal total = new BigDecimal(0.00);
		
		for(PostingSummary286Type pst : postingSummary286Types) {
			if(null != pst.getPostTotal()){
				total = total.add(pst.getPostTotal());
			}
		}
		
		return total.setScale(2,2);
	}
	
//	private BigDecimal getAccountingCodeTotals(Collection<AssignmentTimePost> assignmentTimePosts) throws Exception {
//		BigDecimal total = new BigDecimal(0.00).setScale(2);
//		
//		for(AssignmentTimePost atp : assignmentTimePosts) {
//			if(null != atp.getGuaranteeAmount()){
//				if(atp.getIsHalfRate()){
//					total = total.add(atp.getGuaranteeAmount().multiply(new BigDecimal(0.50)));
//				}else{
//					total = total.add(atp.getGuaranteeAmount());
//				}
//				
//			}else{
//				total = total.add(atp.getRateAmount().multiply(atp.getQuantity()));
//			}
//			
//		}
//		
//		return total.setScale(2);
//	}

	
	public void updateRecordsToExported(FinancialExportVo fvo) throws Exception {
		Collection<Incident> incidents = getIncidents(fvo);
		Collection<Long> incidentIds = getIncidentIds(incidents);
		
		this.updateInvoices(incidentIds);
		this.updateAccrualExtract(incidentIds);
	}
	
	private void updateInvoices(Collection<Long> incidentIds) throws Exception{
		Collection<TimeInvoice> invList = new ArrayList<TimeInvoice>();
		TimeInvoiceDao invDao = (TimeInvoiceDao)context.getBean("timeInvoiceDao");
		try {
			Collection<TimeInvoice> conInv = invDao.getUnexportedInvoices(incidentIds, Boolean.TRUE);
			invList.addAll(conInv);
			Collection<TimeInvoice> adInv = invDao.getUnexportedInvoices(incidentIds, Boolean.FALSE);
			invList.addAll(adInv);
			
			for(TimeInvoice ti : invList){
				ti.setIsExported(StringBooleanEnum.Y);
				ti.setExportDate(new Date());
			}
			invDao.saveAll(invList);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	private void updateAccrualExtract(Collection<Long> incidentIds) throws Exception{
		
		CostAccrualExtractDao caeDao = (CostAccrualExtractDao)context.getBean("costAccrualExtractDao");
		
		try {
			caeDao.updateExportedAccruals(incidentIds);
		}catch (Exception e) {
			throw e;
		}
	}
	
	private Date getReleaseDate(Collection<WorkPeriod> workPeriods) throws Exception {
		Date releaseDate = null;
		
		for(WorkPeriod wp : workPeriods) {
			releaseDate = wp.getDMReleaseDate();
			break;
		}
		
		return releaseDate;
	}

	/**
	 * @param timeInvoiceId the timeInvoiceId to set
	 */
	public void setTimeInvoiceId(Long timeInvoiceId) {
		this.timeInvoiceId = timeInvoiceId;
	}

	/**
	 * @return the timeInvoiceId
	 */
	public Long getTimeInvoiceId() {
		return timeInvoiceId;
	}
	
	
}
