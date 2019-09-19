package gov.nwcg.isuite.core.reports.generator.of288;

import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.data.EmergencyFirefighterCommissaryReportData;
import gov.nwcg.isuite.core.reports.data.EmergencyFirefighterCommissarySubReportData;
import gov.nwcg.isuite.core.reports.data.OF288TimeDetail;
import gov.nwcg.isuite.core.reports.data.OF288TimeInvoice;
import gov.nwcg.isuite.core.reports.data.PersonDetail;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeAdustDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimePostDataVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.report.data.TimeInvoiceData;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.PhoneNumberUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class OF288ReportDataGenerator {
	public IncidentResourceTimeDataVo irTimeDataVo;
	public Collection<IncidentResourceTimePostDataVo> irTimePostDataVos;
	public Collection<IncidentResourceTimeAdustDataVo> irTimeAdjustDataVos;
	public TimeReportFilter filter;
	public ApplicationContext context;
	public Collection<IncidentResourceTimePostDataVo> irTimePostDataInvoiceAmountVos = 
		new ArrayList<IncidentResourceTimePostDataVo>();
	
	public Collection<OF288TimeInvoice> generateReportData() throws ServiceException {

		Collection<OF288TimeInvoice> timeInvoices = new ArrayList<OF288TimeInvoice>();
		OF288TimeInvoice ti;

		try {
			ti = new OF288TimeInvoice();
			ti.setDraftInvoice(filter.getPrintDraftInvoice());
			ti.setDuplicateOriginalInvoice(filter.getPrintDuplicateOriginalInvoice());
			ti.setFinalInvoice(filter.getFinalInvoice());
			ti.setReportPrintedDate(new Date());
			
			// populuate Point of Hire field
			this.setPointOfHire(ti);
 
			// populate hiringUnitName, Phone, and Fax
			this.setHiringInfo(ti);
			
			// populate invoice number info
			this.setInvoiceIdentity(ti);
			
			// populate resource info
			this.setResourceInfo(ti);

			// populate person detail 
			ti.setPersonDetail(this.getPersonDetail());

			//populate time details 
			this.setTimeDetails(ti);
			
			this.setAdjustmentDetail(ti);
			
			timeInvoices.add(ti);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		return timeInvoices;
	}

	public void setHiringInfo(OF288TimeInvoice ti){
		if(StringUtility.hasValue(this.irTimeDataVo.getHiringUnitName())){
			ti.setHiringUnitName(this.irTimeDataVo.getHiringUnitName().toUpperCase());
		}else{
			ti.setHiringUnitName("");
		}
		
		if(StringUtility.hasValue(this.irTimeDataVo.getHiringPhone())){
			String ph = PhoneNumberUtil.formatNumber(this.irTimeDataVo.getHiringPhone());
			ti.setHiringPhone(ph);
		}else
			ti.setHiringPhone("");
		
		if(StringUtility.hasValue(this.irTimeDataVo.getHiringFax())){
			String ph = PhoneNumberUtil.formatNumber(this.irTimeDataVo.getHiringFax());
			ti.setHiringFax(ph);
		}else
			ti.setHiringFax("");
		
	}
	
	private void setInvoiceIdentity(OF288TimeInvoice ti) throws Exception {
		String resnumid="0000";
		if(LongUtility.hasValue(this.irTimeDataVo.getResNumId()))
			resnumid=String.valueOf(this.irTimeDataVo.getResNumId());
		else{
			//Long lngResNumId=irdao.updateResNumId(this.irTimeDataVo.getIncidentResourceId());
			//if(LongUtility.hasValue(lngResNumId)){
			//	resnumid=String.valueOf(lngResNumId);
			//}
		}
		String invId=StringUtility.leftPad(resnumid, "0", 4);

		String invNumber="F-"+
		this.irTimeDataVo.getIncidentYear() + "-" +
		this.irTimeDataVo.getIncidentUnitCode()+"-" +
		this.irTimeDataVo.getIncidentNumber() + "-" +
		invId;
		ti.setIdentificationNumber(invNumber);			 
		
		ti.setIdentificationNumber(this.generateInvoiceIdNumber(ti));

		ti.setOfficialNumber(this.getTimeInvoiceNameCount(ti.getIdentificationNumber()));

		// get like named previous invoices
		String invoiceId = ti.getIdentificationNumber();
		if(StringUtility.hasValue(invoiceId)){
			int lngth=invoiceId.length();
			invoiceId=invoiceId.substring(0, lngth-1);
		}
		//int index = invoiceId.lastIndexOf('-');
		//invoiceId = invoiceId.substring(0, index + 1);
		ti.setPreviousInvoices(this.getPreviousTimeInvoicesNames(invoiceId));
		
	}
	
	private void setPointOfHire(OF288TimeInvoice ti){
		if(this.irTimeDataVo.getEmploymentType().equalsIgnoreCase("AD")){
			ti.setPointOfHire(this.irTimeDataVo.getPointOfHire());
		}else if(this.irTimeDataVo.getEmploymentType().equalsIgnoreCase("OTHER")
				 || this.irTimeDataVo.getEmploymentType().equalsIgnoreCase("FED")){
			ti.setPointOfHire(this.irTimeDataVo.getResourceUnitCode());	
		}
	}
	
	private void setResourceInfo(OF288TimeInvoice ti){
		ti.setResourceId(irTimeDataVo.getResourceId());
		ti.setRemarks(irTimeDataVo.getOfRemarks());
		ti.setRequestNumber(this.irTimeDataVo.getRequestNumber());
		ti.setEmploymentType(this.irTimeDataVo.getEmploymentType());

		if(BooleanUtility.isTrue(this.irTimeDataVo.getPerson())){
			ti.setResourceName(this.irTimeDataVo.getLastName() + ", " + this.irTimeDataVo.getFirstName());
		}else{
			ti.setResourceName(this.irTimeDataVo.getResourceName());
		}
		
	}
	
	private PersonDetail getPersonDetail() {
		PersonDetail pd = new PersonDetail();

		pd.setRequestNumber(this.irTimeDataVo.getRequestNumber());

		if(BooleanUtility.isTrue(this.irTimeDataVo.getPerson())){
			pd.setFirstName(this.irTimeDataVo.getFirstName());
			pd.setLastName(this.irTimeDataVo.getLastName());
		}else{
			pd.setFirstName("");
			pd.setLastName(this.irTimeDataVo.getResourceName());
		}

		pd.setSSN("");
		if(this.irTimeDataVo.getEmploymentType().equalsIgnoreCase("AD")){
			if(StringUtility.hasValue(this.irTimeDataVo.getEci())){
				String eci = this.irTimeDataVo.getEci();
				if(this.irTimeDataVo.getEci().length()<10)
					eci=StringUtility.leftPad(this.irTimeDataVo.getEci(), "0", 10);
				pd.setSSN(eci);
			}
		}

		pd.setStreetAddress1(this.irTimeDataVo.getAddressLine1() != null ? this.irTimeDataVo.getAddressLine1() : "");
		pd.setStreetAddress2(this.irTimeDataVo.getAddressLine2());
		pd.setCity(this.irTimeDataVo.getCity());
		pd.setState(this.irTimeDataVo.getState());
		pd.setZipCode(this.irTimeDataVo.getPostalCode());

		pd.setPhoneNumber(this.irTimeDataVo.getPhone());
		pd.setFaxNumber(this.irTimeDataVo.getFax());
		
		String iac="";
		for(IncidentResourceTimePostDataVo v : irTimePostDataVos){
			if(v.getIncidentResourceId().compareTo(this.irTimeDataVo.getIncidentResourceId())==0)
				iac=v.getAccountCode();
		}
		pd.setAccountingCode(iac);

		return pd;
	}
	
	private void setTimeDetails(OF288TimeInvoice ti ){
		int groupCnt=0;
		String groupClassification="";
		String groupIndex="a";
		String newGroupClassification="";
		
		for(IncidentResourceTimePostDataVo v : irTimePostDataVos){
			try{
			}catch(Exception e){
				// smother
			}
			
			String incidentTag = v.getIncidentUnitCode()
								+ "-" +v.getIncidentNumber();
			 
			//if(v.getIncidentResourceId().compareTo(this.irTimeDataVo.getIncidentResourceId())==0){
				OF288TimeDetail td = new OF288TimeDetail();
				td.setRequestNumber(this.irTimeDataVo.getRequestNumber());
				td.setIncidentNumber(incidentTag);
				
				td.setIncidentLocation("");
				td.setIncidentState("");
				if(StringUtility.hasValue(v.getIncidentUnitCode())){
					String cd = v.getIncidentUnitCode().substring(3, (v.getIncidentUnitCode().length()));
					td.setIncidentLocation(cd);
					
					String state = v.getIncidentUnitCode().substring(0, 2);
					td.setIncidentState(state);
				}
				td.setIncidentName(v.getIncidentName());
				//td.setIncidentNumber(v.getIncidentNumber());
				
				newGroupClassification=v.getIncidentName();
				newGroupClassification
					= newGroupClassification
					  + "-" 
					  + v.getIncidentUnitCode()
					  +"-"+v.getIncidentNumber();
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(v.getPostStartDate());
				td.setPostStartDay(cal.get(Calendar.DAY_OF_MONTH));
				td.setPostStartMonth(cal.get(Calendar.MONTH) + 1);
				td.setPostStartYear(cal.get(Calendar.YEAR));
				td.setPostStartHour(cal.get(Calendar.HOUR_OF_DAY));
				td.setPostStartMinute(cal.get(Calendar.MINUTE));
				td.setHours(0.0);

				newGroupClassification=newGroupClassification+"-"+DateUtil.getYearAsInt(v.getPostStartDate());

				td.setYear(DateUtil.getYearAsInt(v.getPostStartDate()) );

				if(null != v.getPostStopDate()){
					cal.setTime(v.getPostStopDate());
					Integer hour=cal.get(Calendar.HOUR_OF_DAY);
					Integer minute=cal.get(Calendar.MINUTE);
					if(hour==00 && minute==0){
						hour=24;
					}else if(hour==23 && minute==59){
						hour=24;
						minute=0;
					}
					
					td.setPostStopHour(hour);
					td.setPostStopMinute(minute);

					if( (td.getPostStopHour().intValue()==23 && td.getPostStopMinute().intValue()==59 )){
						td.setPostStopHour(Integer.valueOf(24));
						td.setPostStopMinute(Integer.valueOf(00));
					}
							
					td.setHours(v.getQuantity() != null ? v.getQuantity().doubleValue() : 0.0);
				}
				if (td.getHours() <= 0.0) {
					ti.setHasPendingTotals(true);
				}
				td.setStartTimeOnly(v.getReturnTravelStartOnly());
				
				td.setSpecialPayCode(v.getSpecialPayCode() != null ? v.getSpecialPayCode() : "");
				td.setSpecialPayId(v.getSpecialPayId() != null ? v.getSpecialPayId() : null);
				td.setShowStartStop(true);
				td.setShowHoursSpecial(false);
				td.setShowHours(true);
				
				if(StringUtility.hasValue(td.getSpecialPayCode())){
					String code=td.getSpecialPayCode();
					if(code.equals("COP") || code.equals("DAY OFF") || code.equals("GUAR")){
						if(code.equals("GUAR"))
							td.setSpecialPayCode("GUARANTEE");
						
						td.setShowStartStop(false);
						td.setShowHoursSpecial(false);
						td.setHours(v.getQuantity() != null ? v.getQuantity().doubleValue() : 0.0);
					}else{
						td.setShowHoursSpecial(true);
						td.setHours(v.getQuantity() != null ? v.getQuantity().doubleValue() : 0.0);
					}
				}
				
				if(null != v.getAccountCode()){ 

					if(StringUtility.hasValue(v.getRegionCode())){
						td.setAccountingCode(
								v.getRegionCode()
								+ "/" 
								+ v.getAccountCode());
						newGroupClassification=newGroupClassification+"-"+td.getAccountingCode();
					}else{
						td.setAccountingCode(v.getAccountCode());
						newGroupClassification=newGroupClassification+"-"+"-"+td.getAccountingCode();;
					}
				}else
					td.setAccountingCode("UNKNOWN");

				td.setHalfRate(v.getHalfRate());
				td.setKindCode("");
				td.setAdClass("");
				
				if(v.getEmploymentType().equals("OTHER")){
					td.setFireFighterClassification(v.getKindCode());
					td.setKindCode(v.getKindCode());
					//+"/"+ String.valueOf(atp.getOtherRate()));
					td.setRate(v.getOtherRate() != null ? v.getOtherRate().doubleValue() : 0.0);
					newGroupClassification=newGroupClassification+"-"+v.getKindCode()+"-"+td.getRate();
				}

				if(v.getEmploymentType().equals("AD")){
					td.setFireFighterClassification(
							(v.getRateClassName() != null ? v.getRateClassName() + " " : "")
							+ v.getKindCode());
					td.setRate(v.getRateAmount() != null ? v.getRateAmount().doubleValue() : 0.0);
					td.setKindCode(v.getKindCode());
					td.setAdClass(v.getRateClassName());
					newGroupClassification=newGroupClassification+"-"+td.getFireFighterClassification()+"-"+td.getRate();
				}
				
				if(v.getEmploymentType().equals("FED")){
					td.setFireFighterClassification(v.getKindCode());
					td.setKindCode(v.getKindCode());
					td.setRate(v.getRateAmount() != null ? v.getRateAmount().doubleValue() : 0.0);
					newGroupClassification=newGroupClassification+"-"+td.getFireFighterClassification()+"-"+td.getRate();
				}

				if(StringUtility.hasValue(groupClassification)){
					if(groupClassification.equals(newGroupClassification)){
						if( (groupCnt + 1) == 8){
							if(groupIndex.equals("a"))
								groupIndex="b";
							else
								groupIndex="a";

							groupCnt=1;
						}else{
							groupCnt+=1;
						}
					}else{
						groupCnt=1;
						if(groupIndex.equals("a"))
							groupIndex="b";
						else
							groupIndex="a";

						groupClassification=newGroupClassification;
					}
				}else{
					groupClassification=newGroupClassification;
					groupCnt=1;
					groupIndex="a";
				}

				td.setGroupIndex(groupIndex);

				if(DecimalUtil.hasDoubleValue(td.getRate())){
					if (td.getRate() <= 0.0) {
						ti.setHasPendingTotals(true);
					}
				}else
					ti.setHasPendingTotals(true);
				
				td.setEmployeeType(this.irTimeDataVo.getEmploymentType());

				ti.getOf288TimeDetails().add(td);
			//}
			
		}

		if(BooleanUtility.isTrue(filter.getFinalInvoice())){
			groupCnt=0;
			// add an additional grouping per defect 3495
			OF288TimeDetail td = new OF288TimeDetail();
			
			if(groupIndex.equals("a"))
				groupIndex="b";
			else
				groupIndex="a";
				
			td.setFireFighterClassification("Blank");
			td.setStartTimeOnly(false);
			td.setShowStartStop(false);
			td.setShowHoursSpecial(false);
			td.setShowHours(false);
			ti.setHasPendingTotals(false);
			td.setIncidentName("");
			td.setIncidentNumber("");
			td.setIncidentLocation("");
			td.setEmployeeType("");

			td.setGroupIndex(groupIndex);

			ti.getOf288TimeDetails().add(td);
		}
		
	}
	
	private void setAdjustmentDetail(OF288TimeInvoice ti) throws Exception {
		/* Add adjustment Details */
		EmergencyFirefighterCommissaryReportData adj = new EmergencyFirefighterCommissaryReportData();
		adj.setPersonDetail(ti.getPersonDetail());
		adj.setRegularGovEmployee("");
		adj.setCasualEmployee("");
		adj.setOtherEmployee("");
		
		if(this.irTimeDataVo.getEmploymentType().equals("FED")){
			adj.setRegularGovEmployee("X");
		}else if(this.irTimeDataVo.getEmploymentType().equals("AD")){
			adj.setCasualEmployee("X");
		}else if(this.irTimeDataVo.getEmploymentType().equals("OTHER")){
			adj.setOtherEmployee("X");
		}
		
		if(StringUtility.hasValue(this.irTimeDataVo.getHiringUnitName())){
			adj.setHiringUnitName(this.irTimeDataVo.getHiringUnitName().toUpperCase());
		}else{
			adj.setHiringUnitName("");
		}
		if(StringUtility.hasValue(this.irTimeDataVo.getHiringPhone())){
			String ph = PhoneNumberUtil.formatNumber(this.irTimeDataVo.getHiringPhone());
			adj.setHiringPhone(ph);
		}else
			adj.setHiringPhone("");
		
		if(StringUtility.hasValue(this.irTimeDataVo.getHiringFax())){
			String ph = PhoneNumberUtil.formatNumber(this.irTimeDataVo.getHiringFax());
			adj.setHiringFax(ph);
		}else
			adj.setHiringFax("");

		adj.setPointOfHire(ti.getPointOfHire());
		
		adj.setRequestNumber(ti.getRequestNumber());
		adj.setIdentificationNumber(ti.getIdentificationNumber());
		adj.setOfficialNumber(ti.getOfficialNumber());
		adj.setReportType(ti.getReportType());
		adj.setReportTypeHeader(ti.getReportTypeHeader());
		if(DateUtil.hasValue(ti.getPostStartDate())){
			adj.setStartDate(ti.getPostStartDate());
		}
		if(DateUtil.hasValue(ti.getPostStopDate())){
			adj.setEndDate(ti.getPostStopDate());
		}

		adj.setIncidentLocation("");
		adj.setIncidentState("");
		if(StringUtility.hasValue(this.irTimeDataVo.getIncidentUnitCode())){
			String cd = this.irTimeDataVo.getIncidentUnitCode().substring(3, (this.irTimeDataVo.getIncidentUnitCode().length()));
			adj.setIncidentLocation(cd);
			
			String state = this.irTimeDataVo.getIncidentUnitCode().substring(0, 2);
			adj.setIncidentState(state);
		}
		
		
		adj.setIncidentNumber(this.irTimeDataVo.getIncidentUnitCode()+"-"+this.irTimeDataVo.getIncidentNumber());
		adj.setIncidentName(this.irTimeDataVo.getIncidentName());
		adj.setUnitCode(this.irTimeDataVo.getIncidentUnitCode());
		

		Date firstAdjustmentDate=null;
		
		for (IncidentResourceTimeAdustDataVo v : this.irTimeAdjustDataVos) {
			String lastIncludeDate=DateUtil.toDateString(filter.getLastDateToIncludeOnReport(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
			
			
			String adjDate=DateUtil.toDateString(v.getActivityDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY);

			Date dteLastInclude=DateUtil.toDate(lastIncludeDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			dteLastInclude=DateUtil.addMilitaryTimeToDate(dteLastInclude, "2359");
			Date dteAdj=DateUtil.toDate(adjDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			
			if(null == firstAdjustmentDate)
				firstAdjustmentDate=dteAdj;
			else{
				if(dteAdj.before(firstAdjustmentDate))
					firstAdjustmentDate=dteAdj;
			}

			if (!dteAdj.after(dteLastInclude) ) {
				EmergencyFirefighterCommissarySubReportData sub = new EmergencyFirefighterCommissarySubReportData();

				sub.setPurchaseDate(dteAdj);
				sub.setAccountingCode(v.getAccountingCode());
				sub.setCategoryName(StringUtility.hasValue(v.getAdjustmentCategoryDesc()) ? 
						v.getAdjustmentCategoryDesc() : "");
				sub.setItem(v.getCommodity());
				if (v.getAdjustmentType().equalsIgnoreCase("ADDITION")) {
					sub.setAmount(v.getAdjustmentAmount() != null ? 
							v.getAdjustmentAmount().doubleValue() : 0.0);
				} else {
					sub.setAmount(-1 * (v.getAdjustmentAmount() != null ? 
							v.getAdjustmentAmount().doubleValue() : 0.0) );
				}

				adj.getSubReportData().add(sub);
			}
			if (adj.getSubReportData().size() > 0) {
				ti.setCommissaryDetails(adj);
				ti.setCommissaryReport(true);
			}
		}
		
		if(null == ti.getPostStartDate() && null != firstAdjustmentDate){
			ti.setPostStartDate(firstAdjustmentDate);
		}
		if(null == ti.getPostStopDate() && null != filter.getLastDateToIncludeOnReport()){
			ti.setPostStopDate(filter.getLastDateToIncludeOnReport());
		}
		
	}
	
	protected String generateInvoiceIdNumber(TimeInvoiceData ti) throws PersistenceException {
		String invoiceId = ti.getIdentificationNumber();

		ReportTimeDao reportTimeDao = (ReportTimeDao) context.getBean("reportTimeDao");
		int count = reportTimeDao.getTimeInvoiceLikeNameCount(invoiceId);

		String sufix = "";
		int wrapCounter = 0;
		while (count + 1 > 26) {
			wrapCounter++;
			count = count - 26;
		}
		if (wrapCounter > 0)
			sufix = sufix + (char) (wrapCounter + 64);
		sufix = sufix + (char) (count + 65);
		invoiceId = invoiceId + sufix;

		return invoiceId;
	}	
	
	protected String getTimeInvoiceNameCount(String invoiceNumber) throws ServiceException {
		ReportTimeDao reportTimeDao = (ReportTimeDao) context.getBean("reportTimeDao");
		String count = "";
		try {
			count = reportTimeDao.getTimeInvoiceNameCount(invoiceNumber);
		} catch (PersistenceException e) {
			throw new ServiceException(e);
		}
		return count;
	}
	
	protected Collection<String> getPreviousTimeInvoicesNames(String invoiceId) throws ServiceException {
		ReportTimeDao reportTimeDao = (ReportTimeDao) context.getBean("reportTimeDao");
		Collection<String> names = null;
		try {
			names = reportTimeDao.getPreviousTimeInvoicesNames(invoiceId);
		} catch (PersistenceException e) {
			throw new ServiceException(e);
		}
		return names;
	}
}
