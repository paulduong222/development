package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.reports.data.OF286TimeInvoiceSubReportData;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.ContractorAgreementVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class OF286LineItemBuilder {
	private ApplicationContext context;
	
	public OF286LineItemBuilder(ApplicationContext ctx){
		this.context=ctx;
	}
	
	public Collection<OF286TimeInvoiceSubReportData> buildSubReportDataForDate(Date dt,IncidentResourceVo irVo,Collection<AssignmentTimePost> atps) {

		Collection<OF286TimeInvoiceSubReportData> list = new ArrayList<OF286TimeInvoiceSubReportData>();

		for (AssignmentTimePost atp : atps) {
			
			if(DateUtil.isSameDate(dt, atp.getPostStartDate())){
				OF286TimeInvoiceSubReportData td = new OF286TimeInvoiceSubReportData();
				OF286TimeInvoiceSubReportData td2 = new OF286TimeInvoiceSubReportData();
				
				AssignmentTimeVo at = null; 
				for (AssignmentVo a : irVo.getWorkPeriodVo().getAssignmentVos()) {
					if(atp.getAssignmentTimeId().compareTo(a.getAssignmentTimeVo().getId())==0){
						at = a.getAssignmentTimeVo();
						break;
					}
				}
		
				IncidentAccountCode iac = this.getPostingIac(atp);
				if(null != iac){
					Incident inc = iac.getIncident();
					if(null == inc){
						IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
						try{
							inc = dao.getById(iac.getIncidentId(),IncidentImpl.class);
						}catch(Exception e){}
					}
					
					td.setIncidentName(inc.getIncidentName());
					td.setIncidentNumber("TODO");
					td.setAccountingCode(iac.getAccountCode().getAccountCode());
					td2.setIncidentName(inc.getIncidentName());
					td2.setIncidentNumber("TODO");
					td2.setAccountingCode(iac.getAccountCode().getAccountCode());
					
				}
				
				td.setHalfRate(atp.getIsHalfRate());
				td2.setHalfRate(false);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(atp.getPostStartDate());
				td.setPostStartDay(cal.get(Calendar.DAY_OF_MONTH));
				td.setPostStartMonth(cal.get(Calendar.MONTH) + 1);
				td.setPostStartYear(cal.get(Calendar.YEAR));
				td2.setPostStartDay(cal.get(Calendar.DAY_OF_MONTH));
				td2.setPostStartMonth(cal.get(Calendar.MONTH) + 1);
				td2.setPostStartYear(cal.get(Calendar.YEAR));

				if(null != at){
					ContractorAgreementVo agree = (at.getContractorPaymentInfoVo() != null ? 
							at.getContractorPaymentInfoVo().getContractorAgreementVo() : null);
					if (agree != null) {
						td.setAgreementNumber(agree.getAgreementNumber());
						if(DateTransferVo.hasDateString(agree.getStartDateVo())){
							Date dte=DateTransferVo.getDate(agree.getStartDateVo());
							td.setBeginningDateOfAgreement(dte);
						}
						if(DateTransferVo.hasDateString(agree.getEndDateVo())){
							Date dte=DateTransferVo.getDate(agree.getEndDateVo());
							td.setEndingDateOfAgreement(dte);
						}
						Date hdt=null;
						if(DateTransferVo.hasDateString(at.getContractorPaymentInfoVo().getHiredDateVo())){
							try{
								hdt=DateTransferVo.getTransferDate(at.getContractorPaymentInfoVo().getHiredDateVo());
							}catch(Exception ee){}
						}			
						td.setDateOfHire(hdt);
						//td.setDateOfHire(at.getContractorPaymentInfoVo().getHiredDate());
						td.setPointOfHire(agree.getPointOfHire());
						td2.setAgreementNumber(agree.getAgreementNumber());
						if(DateTransferVo.hasDateString(agree.getStartDateVo())){
							Date dte=DateTransferVo.getDate(agree.getStartDateVo());
							td2.setBeginningDateOfAgreement(dte);
						}
						if(DateTransferVo.hasDateString(agree.getEndDateVo())){
							Date dte=DateTransferVo.getDate(agree.getEndDateVo());
							td2.setEndingDateOfAgreement(dte);
						}
						td2.setDateOfHire(hdt);
						//td2.setDateOfHire(at.getContractorPaymentInfoVo().getHiredDate());
						td2.setPointOfHire(agree.getPointOfHire());
					}
						
					if (at.getContractorPaymentInfoVo() != null) {
						td.setIsSupplies(at.getContractorPaymentInfoVo().getSupplies());
						td.setIsOperator(at.getContractorPaymentInfoVo().getOperation());
						td.setEquipmentWithdrawn(at.getContractorPaymentInfoVo().getWithdrawn());
						td2.setIsSupplies(at.getContractorPaymentInfoVo().getSupplies());
						td2.setIsOperator(at.getContractorPaymentInfoVo().getOperation());
						td2.setEquipmentWithdrawn(at.getContractorPaymentInfoVo().getWithdrawn());

						td.setPointOfHire("");
						td2.setPointOfHire("");
						if(StringUtility.hasValue(at.getContractorPaymentInfoVo().getPointOfHire())){
							td.setPointOfHire(at.getContractorPaymentInfoVo().getPointOfHire());
							td2.setPointOfHire(at.getContractorPaymentInfoVo().getPointOfHire());
						}
					}
				}
					
				Date releaseDate=DateTransferVo.getDate(irVo.getWorkPeriodVo().getDmReleaseDateVo());
				if(StringUtility.hasValue(irVo.getWorkPeriodVo().getDmReleaseDateVo().getTimeString())){
					try{
						releaseDate=DateUtil.addMilitaryTimeToDate(releaseDate, irVo.getWorkPeriodVo().getDmReleaseDateVo().getTimeString());
					}catch(Exception eee){}
				}
				if (releaseDate != null) {
					td.setEquipmentReleased(true);
					td.setEquipmentReleasedDateAndTime(releaseDate);
					td2.setEquipmentReleased(true);
					td2.setEquipmentReleasedDateAndTime(releaseDate);
				}
				
				if (atp.getContractorPostType().equals("GUARANTEE")){
					// guarantee posting
					if(BooleanUtility.isTrue(atp.getIsHalfRate())){
						td.setGuaranteeAmount(atp.getGuaranteeAmount() != null ? 
								(atp.getGuaranteeAmount().doubleValue() / 2) : null);
						td.setTotalAmount(td.getGuaranteeAmount());
						td.setWorkedTotalAmount(td.getGuaranteeAmount());
						td.setFinalAmount(td.getGuaranteeAmount());
					}else{
						td.setGuaranteeAmount(atp.getGuaranteeAmount() != null ? 
								atp.getGuaranteeAmount().doubleValue() : null);
						td.setTotalAmount(atp.getGuaranteeAmount().doubleValue());
						td.setWorkedTotalAmount(td.getTotalAmount());
						td.setFinalAmount(td.getTotalAmount());
					}
					td.setWorkedUnitType(atp.getUnitOfMeasure());
				}else if (atp.getContractorPostType().equals("SPECIAL")){
					AssignmentTimePost satp=atp;
					// special posting
					td.setSpecialUnits(satp.getQuantity() != null ? 
							satp.getQuantity().doubleValue() : 0.0);
					td.setSpecialUnitType(satp.getUnitOfMeasure());
					if(BooleanUtility.isTrue(satp.getIsHalfRate())){
						if(null != satp.getRefContractorRate() && DecimalUtil.hasValue(satp.getRefContractorRate().getGuaranteeAmount())){
							td.setSpecialRate(satp.getRateAmount() != null ? 
									satp.getRateAmount().doubleValue() : 0.0);
						}else{
							// only half if DAily
							if(satp.getRefContractorRate().getUnitOfMeasure().equalsIgnoreCase("DAILY")){
								td.setSpecialRate(satp.getRateAmount() != null ? 
										(satp.getRateAmount().doubleValue() / 2) : 0.0);
							}else{
								td.setSpecialRate(satp.getRateAmount() != null ? 
										(satp.getRateAmount().doubleValue()) : 0.0);
							}
							// only half the rate if no guarantee amount
							//td.setSpecialRate(satp.getRateAmount() != null ? 
							//		(satp.getRateAmount().doubleValue() / 2) : 0.0);
						}
						td.setSpecialTotalAmount(td.getSpecialUnits() * (td.getSpecialRate().doubleValue() ));
						td.setHalfRate(satp.getIsHalfRate());
					}else{
						td.setSpecialRate(satp.getRateAmount() != null ? 
								satp.getRateAmount().doubleValue() : 0.0);
						td.setSpecialTotalAmount(td.getSpecialUnits() * td.getSpecialRate());
					}
					if(null != satp.getRefContractorRate() && DecimalUtil.hasValue(satp.getRefContractorRate().getGuaranteeAmount())){
						td.setGuaranteeAmount(satp.getRefContractorRate().getGuaranteeAmount().doubleValue());
					}else{
						td.setGuaranteeAmount(satp.getGuaranteeAmount() != null ? 
								satp.getGuaranteeAmount().doubleValue() : null);
					}
					
					td.setTotalAmount(td.getSpecialTotalAmount());
					td.setFinalAmount(td.getSpecialTotalAmount());
					
					if(td.getGuaranteeAmount()!=null){
						if(td.getGuaranteeAmount().doubleValue() > td.getFinalAmount()){
							td.setFinalAmount(td.getGuaranteeAmount().doubleValue());
						}
					}
					
				}else if (atp.getContractorPostType().equals("PRIMARY")){
					// primary posting
					td.setWorkedUnits(atp.getQuantity() != null ? atp.getQuantity().doubleValue() : 0.0);
					td.setWorkedUnitType(atp.getUnitOfMeasure());
					if(BooleanUtility.isTrue(atp.getIsHalfRate())){
						// if half rate is checked and there IS NO guarantee amount, then half the rate
						// if half rate is checked and there IS A guarantee amount, then only half the guarantee amount
						// if half rate is checked and uom is daily or posting type is guarantee then half
						if(null != atp.getRefContractorRate() && DecimalUtil.hasValue(atp.getRefContractorRate().getGuaranteeAmount())){
							td.setWorkedRate(atp.getRateAmount() != null ? atp.getRateAmount().doubleValue() : 0.0);
						}else if(null != atp.getRefContractorRate() && atp.getUnitOfMeasure().equalsIgnoreCase("DAILY")){
							td.setWorkedRate(atp.getRateAmount() != null ? (atp.getRateAmount().doubleValue()/2) : 0.0);
						}else{
							td.setWorkedRate(atp.getRateAmount() != null ? (atp.getRateAmount().doubleValue()) : 0.0);
						}
					}else
						td.setWorkedRate(atp.getRateAmount() != null ? atp.getRateAmount().doubleValue() : 0.0);
					td.setWorkedTotalAmount(td.getWorkedUnits() * td.getWorkedRate());

					Boolean guarPosting=(DecimalUtil.hasValue(atp.getRefContractorRate().getGuaranteeAmount()) ? true : false);
					if(atp.getGuaranteePosting()==true || guarPosting==true){
						if(null != atp.getRefContractorRate() && DecimalUtil.hasValue(atp.getRefContractorRate().getGuaranteeAmount())){
							if(BooleanUtility.isTrue(atp.getIsHalfRate()))
								td.setGuaranteeAmount(atp.getRefContractorRate().getGuaranteeAmount().doubleValue()/2);
							else
								td.setGuaranteeAmount(atp.getRefContractorRate().getGuaranteeAmount().doubleValue());
						}else{
							td.setGuaranteeAmount(atp.getGuaranteeAmount() != null ? 
									atp.getGuaranteeAmount().doubleValue() : null);
						}
					}else{
						td.setGuaranteeAmount(null);
					}
					
					td.setTotalAmount(td.getWorkedTotalAmount());
					td.setFinalAmount(td.getTotalAmount());
					
					if(td.getGuaranteeAmount()!=null){
						if(td.getGuaranteeAmount().doubleValue() > td.getFinalAmount()){
							td.setFinalAmount(td.getGuaranteeAmount().doubleValue());
						}
					}
				}
		
				td.setDevDate(dt);
				td.setDevPostingType(atp.getContractorPostType());
				td.setDevUom(atp.getUnitOfMeasure());
				td.setDevContractorRateId(String.valueOf(atp.getRefContractorRateId()));
				td.setDevTotalAmount(td.getTotalAmount());
				td.setDevGuaranteeAmount(null != td.getGuaranteeAmount()?td.getGuaranteeAmount().doubleValue():0.0);

				td2.setDevDate(dt);
				td2.setDevPostingType(atp.getContractorPostType());
				td2.setDevTotalAmount(td.getTotalAmount());
				td2.setDevGuaranteeAmount(null != td.getGuaranteeAmount()?td.getGuaranteeAmount().doubleValue():0.0);
				if(StringUtility.hasValue(td2.getDevContractorRateId())){
					if(td2.getDevPostingType().equals("SPECIAL") || td2.getDevPostingType().equals("BOTH")){
						td2.setDevContractorRateId(String.valueOf(atp.getRefContractorRateId()));
						td2.setDevUom(atp.getUnitOfMeasure());
					}
				}
				
				list.add(td);
				if(atp.getContractorPostType().equals("BOTH"))
					list.add(td2);
			}
		}
		
		return list;
		
	}

	private IncidentAccountCode getPostingIac(AssignmentTimePost atp){
		IncidentAccountCode iac = null;
		
		if(null != atp.getIncidentAccountCode())
			iac=atp.getIncidentAccountCode();
		else if(null != atp.getAssignmentTimePost() && null != atp.getAssignmentTimePost().getIncidentAccountCode())
			iac=atp.getAssignmentTimePost().getIncidentAccountCode();
		
		return iac;
	}

}
