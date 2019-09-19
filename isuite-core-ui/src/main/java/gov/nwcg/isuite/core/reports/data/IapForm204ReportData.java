package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapBranchCommSummary;
import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.core.domain.IapBranchPersonnelRes;
import gov.nwcg.isuite.core.domain.IapBranchRscAssign;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.ReportTextUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class IapForm204ReportData {
	private String incidentName;
	private String fromDate;
	private String toDate;
	private String fromTime;
	private String toTime;
	private String branchName;
	private String divisionName;
	private String groupName;
	private String stagingArea;
	private String specialInstructions;
	private String workAssignments;
	private String preparedBy;
	private String preparedByPosition;
	private String preparedDateTime;
	private String preparedDateString;
	private String preparedTime;
	private String approvedBy;
	private String draftFinal;
	private String operationalPeriod;
	private String fromDayOfWeek;
	private String toDayOfWeek;
	private String sfunction1;
	private String rx1;
	private String tx1;
	private String rxTone1;
	private String txTone1;
	private String channel1;
	private String mode1;
	private String sfunction2;
	private String rx2;
	private String tx2;
	private String rxTone2;
	private String txTone2;
	private String channel2;
	private String mode2;
	private String sfunction3;
	private String rx3;
	private String tx3;
	private String rxTone3;
	private String txTone3;
	private String channel3;
	private String mode3;
	private String sfunction4;
	private String rx4;
	private String tx4;
	private String rxTone4;
	private String txTone4;
	private String channel4;
	private String mode4;
	private String sfunction5;
	private String rx5;
	private String tx5;
	private String rxTone5;
	private String txTone5;
	private String channel5;
	private String mode5;
	private String sfunction6;
	private String rx6;
	private String tx6;
	private String rxTone6;
	private String txTone6;
	private String channel6;
	private String mode6;
	private String sfunction7;
	private String rx7;
	private String tx7;
	private String rxTone7;
	private String txTone7;
	private String channel7;
	private String mode7;
	private String sfunction8;
	private String rx8;
	private String tx8;
	private String rxTone8;
	private String txTone8;
	private String channel8;
	private String mode8;
	private String role1;
	private String role2;
	private String role3;
	private String role4;
	private String role5;
	private String role6;
	private String name1;
	private String name1b;
	private String name2;
	private String name2b;
	private String name3;
	private String name3b;
	private String name4;
	private String name4b;
	private String name5;
	private String name5b;
	private String name6;
	private String name6b;
	private Boolean includeThirdRow=false;
	private ArrayList<IapForm204ResourceData> resourceData = new ArrayList<IapForm204ResourceData>();
	private JRBeanCollectionDataSource resourcesAssigned;
	private ArrayList<IapForm204CommData> commData = new ArrayList<IapForm204CommData>();
	private JRBeanCollectionDataSource communications;

	public IapForm204ReportData() {
	}

	public static IapForm204ReportData getInstance(IapPlan iapPlan,
			IapBranch entity) throws Exception {
		IapForm204ReportData reportData = new IapForm204ReportData();

		if (BooleanUtility.isTrue(entity.getIsForm204Locked().getValue()))
			reportData.setDraftFinal("FINAL");
		else
			reportData.setDraftFinal("DRAFT");

		reportData.setOperationalPeriod(iapPlan.getOperationPeriod());
		
		// Block 1 Incident Name
		reportData.setIncidentName(iapPlan.getIncidentName());

		// Block 2 Operational Period
		if (DateUtil.hasValue(iapPlan.getFromDate())) {
			reportData.setFromDate(DateUtil.toDateString(iapPlan.getFromDate(),
					DateUtil.MM_SLASH_DD_SLASH_YYYY));
			reportData.setFromTime(DateUtil.toMilitaryTime(iapPlan
					.getFromDate()));
			reportData.setFromDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan
					.getFromDate()));
		}
		if (DateUtil.hasValue(iapPlan.getToDate())) {
			reportData.setToDate(DateUtil.toDateString(iapPlan.getToDate(),
					DateUtil.MM_SLASH_DD_SLASH_YYYY));
			reportData.setToTime(DateUtil.toMilitaryTime(iapPlan.getToDate()));
			if(reportData.getToTime().equals("2359"))
				reportData.setToTime("2400");
			reportData.setToDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan
					.getToDate()));
		}
		
		// Block 3 Branch/Division/Group/Staging Area
		reportData.setBranchName(entity.getBranchName());
		reportData.setDivisionName(entity.getDivisionName());
		reportData.setGroupName(entity.getGroupName());
		reportData.setStagingArea(entity.getStagingArea());


		String name1="";
		String name2="";
		String name3="";
		String name4="";
		
		// Block 4 Operations Personnel
		if (CollectionUtility.hasValue(entity.getIapBranchPersonnels())) {
			
			if(entity.getIapBranchPersonnels().size()>4)
				reportData.setIncludeThirdRow(true);

			int oppDataCnt=1;

			String n1="";
			String n2="";
			
			for (IapBranchPersonnel bp : entity.getIapBranchPersonnels()) {
				n1="";
				n2="";
				
				String resourceNames="";
				if(CollectionUtility.hasValue(bp.getIapBranchPersonnelResources())){
					int namecnt=1;
					for(IapBranchPersonnelRes ipr : bp.getIapBranchPersonnelResources()){
						if(StringUtility.hasValue(ipr.getName())){
							if(namecnt==1){
								n1=ipr.getName()+(ipr.getIsTrainee().getValue()==true?" (T)":"");
								namecnt++;
							}else
								n2=ipr.getName() +(ipr.getIsTrainee().getValue()==true?" (T)":"");
							resourceNames=resourceNames+(StringUtility.hasValue(resourceNames)?"\n":"")+ipr.getName()+(ipr.getIsTrainee().getValue()==true?" (T)":"");
						}
					}
				}
				
				switch(oppDataCnt)
				{
					case 1:
						reportData.setRole1(bp.getRole());
						reportData.setName1(n1);
						reportData.setName1b(n2);
						break;
					case 2:
						reportData.setRole2(bp.getRole());
						reportData.setName2(n1);
						reportData.setName2b(n2);
						break;
					case 3:
						reportData.setRole3(bp.getRole());
						reportData.setName3(n1);
						reportData.setName3b(n2);
						break;
					case 4:
						reportData.setRole4(bp.getRole());
						reportData.setName4(n1);
						reportData.setName4b(n2);
						break;
					case 5:
						reportData.setRole5(bp.getRole());
						reportData.setName5(n1);
						reportData.setName5b(n2);
						break;
					case 6:
						reportData.setRole6(bp.getRole());
						reportData.setName6(n1);
						reportData.setName6b(n2);
						break;
				}
				
				oppDataCnt++;
			}
			
		}
		
		// Block 5 Resources Assigned
		if (CollectionUtility.hasValue(entity.getIapBranchRscAssigns())) {
			for (IapBranchRscAssign r : entity.getIapBranchRscAssigns()) {
				IapForm204ResourceData rdata = new IapForm204ResourceData();
				rdata.setResourceIdentifier(r.getResourceName());
				rdata.setLeader(r.getLeaderName());
				rdata.setEmt("");
				if(DateUtil.hasValue(r.getLastDayToWorkDate())){
					String lwd=DateUtil.toDateString(r.getLastDayToWorkDate(), DateUtil.MM_SLASH_DD);
					rdata.setLastWorkDay(lwd);
				}else
					rdata.setLastWorkDay("");

				if (StringUtility.hasValue(r.getDropOffPoint())) {
					rdata.setDropOff(r.getDropOffPoint() + "/");
				} else
					rdata.setDropOff("/");

				if (StringUtility.hasValue(r.getDropOffTime())) {
					rdata.setDropOff(rdata.getDropOff() + r.getDropOffTime());
				}

				if (StringUtility.hasValue(r.getPickUpPoint())) {
					rdata.setPickUp(r.getPickUpPoint() + "/");
				} else
					rdata.setPickUp("/");

				if (StringUtility.hasValue(r.getPickUpTime())) {
					rdata.setPickUp(rdata.getPickUp() + r.getPickUpTime());
				}

				if (IntegerUtility.hasValue(r.getNbrOfPersonnel())) {
					if(BooleanUtility.isTrue(r.getIsBlankLine().getValue()))
						rdata.setNumPersonnel("");
					else{
						rdata.setNumPersonnel(
								String.valueOf(
										r.getNbrOfPersonnel()));
					}
				} else {
					if(BooleanUtility.isTrue(r.getIsBlankLine().getValue()))
						rdata.setNumPersonnel("");
					else{
						//rdata.setNumPersonnel("0");
						rdata.setNumPersonnel("");
					}
				}
				reportData.getResourceData().add(rdata);
			}
		}

		// ensure at least 1 resource assigned record
		if (!CollectionUtility.hasValue(reportData.getResourceData())) {
			IapForm204ResourceData rdata = new IapForm204ResourceData();
			reportData.getResourceData().add(rdata);
		}

		// Block 6 Work Assignments
		if (StringUtility.hasValue(entity.getWorkAssignment())) {
			// Convert fontsizes 14,16,18 to 1,2,3
			String str=ReportTextUtil.formatText(entity.getWorkAssignment());
			reportData.setWorkAssignments(str);
		}

		// Block 7 Special Instructions
		if (StringUtility.hasValue(entity.getSpecialInstructions())) {
			// Convert fontsizes 14,16,18 to 1,2,3
			String str=ReportTextUtil.formatText(entity.getSpecialInstructions());
			reportData.setSpecialInstructions(str);
		}

		// Block 8 Communications
		if (CollectionUtility.hasValue(entity.getIapBranchCommSummaries())) {
			int x=1;
			for (IapBranchCommSummary comm : entity.getIapBranchCommSummaries()) {
				switch(x){
					case 1:
						reportData.setSfunction1(comm.getFunction());
						reportData.setRx1(comm.getRx());
						reportData.setTx1(comm.getTx());
						reportData.setRxTone1(comm.getRxTone());
						reportData.setTxTone1(comm.getTxTone());
						reportData.setChannel1(comm.getChannel1());
						reportData.setMode1(comm.getMode());
						break;
					case 2:
						reportData.setSfunction2(comm.getFunction());
						reportData.setRx2(comm.getRx());
						reportData.setTx2(comm.getTx());
						reportData.setRxTone2(comm.getRxTone());
						reportData.setTxTone2(comm.getTxTone());
						reportData.setChannel2(comm.getChannel1());
						reportData.setMode2(comm.getMode());
						break;
					case 3:
						reportData.setSfunction3(comm.getFunction());
						reportData.setRx3(comm.getRx());
						reportData.setTx3(comm.getTx());
						reportData.setRxTone3(comm.getRxTone());
						reportData.setTxTone3(comm.getTxTone());
						reportData.setChannel3(comm.getChannel1());
						reportData.setMode3(comm.getMode());
						break;
					case 4:
						reportData.setSfunction4(comm.getFunction());
						reportData.setRx4(comm.getRx());
						reportData.setTx4(comm.getTx());
						reportData.setRxTone4(comm.getRxTone());
						reportData.setTxTone4(comm.getTxTone());
						reportData.setChannel4(comm.getChannel1());
						reportData.setMode4(comm.getMode());
						break;
					case 5:
						reportData.setSfunction5(comm.getFunction());
						reportData.setRx5(comm.getRx());
						reportData.setTx5(comm.getTx());
						reportData.setRxTone5(comm.getRxTone());
						reportData.setTxTone5(comm.getTxTone());
						reportData.setChannel5(comm.getChannel1());
						reportData.setMode5(comm.getMode());
						break;
					case 6:
						reportData.setSfunction6(comm.getFunction());
						reportData.setRx6(comm.getRx());
						reportData.setTx6(comm.getTx());
						reportData.setRxTone6(comm.getRxTone());
						reportData.setTxTone6(comm.getTxTone());
						reportData.setChannel6(comm.getChannel1());
						reportData.setMode6(comm.getMode());
						break;
					case 7:
						reportData.setSfunction7(comm.getFunction());
						reportData.setRx7(comm.getRx());
						reportData.setTx7(comm.getTx());
						reportData.setRxTone7(comm.getRxTone());
						reportData.setTxTone7(comm.getTxTone());
						reportData.setChannel7(comm.getChannel1());
						reportData.setMode7(comm.getMode());
						break;
					case 8:
						reportData.setSfunction8(comm.getFunction());
						reportData.setRx8(comm.getRx());
						reportData.setTx8(comm.getTx());
						reportData.setRxTone8(comm.getRxTone());
						reportData.setTxTone8(comm.getTxTone());
						reportData.setChannel8(comm.getChannel1());
						reportData.setMode8(comm.getMode());
						break;
				}
				x++;
				/*
				IapForm204CommData data = new IapForm204CommData();
				data.setSfunction(comm.getFunction());
				data.setRx(comm.getRx());
				data.setTx(comm.getTx());
				data.setRxTone(comm.getRxTone());
				data.setTxTone(comm.getTxTone());
				data.setChannel(comm.getChannel1());
				data.setMode(comm.getMode());
				reportData.getCommData().add(data);
				*/
			}
		}

		// if no comm data, render 4 defatuls on form
		if (!CollectionUtility.hasValue(entity.getIapBranchCommSummaries())) {
			reportData.setSfunction1("COMMAND");
			reportData.setSfunction2("TACTICAL");
			reportData.setSfunction3("LOGISTICS");
			reportData.setSfunction4("AIR TO GROUND");
		}

		// Block 9 Prepared By
		reportData.setPreparedBy(entity.getPreparedBy());
		reportData.setPreparedByPosition(entity.getPreparedByPosition());
		reportData.setApprovedBy(entity.getApprovedBy());
		if (DateUtil.hasValue(entity.getPreparedDate())) {
			String sdate = DateUtil.toDateString(entity.getPreparedDate(),
					DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String stime = DateUtil.toMilitaryTime(entity.getPreparedDate());
			reportData.setPreparedDateTime(sdate + " " + stime);
			reportData.setPreparedDateString(sdate);
			reportData.setPreparedTime(stime);
		}

		return reportData;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentName
	 *            the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the fromTime
	 */
	public String getFromTime() {
		return fromTime;
	}

	/**
	 * @param fromTime
	 *            the fromTime to set
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * @return the toTime
	 */
	public String getToTime() {
		return toTime;
	}

	/**
	 * @param toTime
	 *            the toTime to set
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the divisionName
	 */
	public String getDivisionName() {
		return divisionName;
	}

	/**
	 * @param divisionName
	 *            the divisionName to set
	 */
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the stagingArea
	 */
	public String getStagingArea() {
		return stagingArea;
	}

	/**
	 * @param stagingArea
	 *            the stagingArea to set
	 */
	public void setStagingArea(String stagingArea) {
		this.stagingArea = stagingArea;
	}

	/**
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	/**
	 * @param specialInstructions
	 *            the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	/**
	 * @return the workAssignments
	 */
	public String getWorkAssignments() {
		return workAssignments;
	}

	/**
	 * @param workAssignments
	 *            the workAssignments to set
	 */
	public void setWorkAssignments(String workAssignments) {
		this.workAssignments = workAssignments;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy
	 *            the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition() {
		return preparedByPosition;
	}

	/**
	 * @param preparedByPosition
	 *            the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition) {
		this.preparedByPosition = preparedByPosition;
	}

	/**
	 * @return the preparedDateTime
	 */
	public String getPreparedDateTime() {
		return preparedDateTime;
	}

	/**
	 * @param preparedDateTime
	 *            the preparedDateTime to set
	 */
	public void setPreparedDateTime(String preparedDateTime) {
		this.preparedDateTime = preparedDateTime;
	}

	/**
	 * @return the resourceData
	 */
	public ArrayList<IapForm204ResourceData> getResourceData() {
		return resourceData;
	}

	/**
	 * @param resourceData
	 *            the resourceData to set
	 */
	public void setResourceData(ArrayList<IapForm204ResourceData> resourceData) {
		this.resourceData = resourceData;
	}

	/**
	 * @return the resourcesAssigned
	 */
	public JRBeanCollectionDataSource getResourcesAssigned() {
		return new JRBeanCollectionDataSource(this.resourceData);
	}

	/**
	 * @param resourcesAssigned
	 *            the resourcesAssigned to set
	 */
	public void setResourcesAssigned(
			JRBeanCollectionDataSource resourcesAssigned) {
		this.resourcesAssigned = resourcesAssigned;
	}

	/**
	 * @return the commData
	 */
	public ArrayList<IapForm204CommData> getCommData() {
		return commData;
	}

	/**
	 * @param commData
	 *            the commData to set
	 */
	public void setCommData(ArrayList<IapForm204CommData> commData) {
		this.commData = commData;
	}

	/**
	 * @return the communications
	 */
	public JRBeanCollectionDataSource getCommunications() {
		return new JRBeanCollectionDataSource(this.commData);
	}

	/**
	 * @param communications
	 *            the communications to set
	 */
	public void setCommunications(JRBeanCollectionDataSource communications) {
		this.communications = communications;
	}

	/**
	 * @return the preparedDateString
	 */
	public String getPreparedDateString() {
		return preparedDateString;
	}

	/**
	 * @param preparedDateString
	 *            the preparedDateString to set
	 */
	public void setPreparedDateString(String preparedDateString) {
		this.preparedDateString = preparedDateString;
	}

	/**
	 * @return the preparedTime
	 */
	public String getPreparedTime() {
		return preparedTime;
	}

	/**
	 * @param preparedTime
	 *            the preparedTime to set
	 */
	public void setPreparedTime(String preparedTime) {
		this.preparedTime = preparedTime;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy
	 *            the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the draftFinal
	 */
	public String getDraftFinal() {
		return draftFinal;
	}

	/**
	 * @param draftFinal
	 *            the draftFinal to set
	 */
	public void setDraftFinal(String draftFinal) {
		this.draftFinal = draftFinal;
	}

	/**
	 * @return the operationalPeriod
	 */
	public String getOperationalPeriod() {
		return operationalPeriod;
	}

	/**
	 * @param operationalPeriod
	 *            the operationalPeriod to set
	 */
	public void setOperationalPeriod(String operationalPeriod) {
		this.operationalPeriod = operationalPeriod;
	}

	/**
	 * @return the fromDayOfWeek
	 */
	public String getFromDayOfWeek() {
		return fromDayOfWeek;
	}

	/**
	 * @param fromDayOfWeek
	 *            the fromDayOfWeek to set
	 */
	public void setFromDayOfWeek(String fromDayOfWeek) {
		this.fromDayOfWeek = fromDayOfWeek;
	}

	/**
	 * @return the toDayOfWeek
	 */
	public String getToDayOfWeek() {
		return toDayOfWeek;
	}

	/**
	 * @param toDayOfWeek
	 *            the toDayOfWeek to set
	 */
	public void setToDayOfWeek(String toDayOfWeek) {
		this.toDayOfWeek = toDayOfWeek;
	}

	/**
	 * @return the sfunction1
	 */
	public String getSfunction1() {
		return sfunction1;
	}

	/**
	 * @param sfunction1 the sfunction1 to set
	 */
	public void setSfunction1(String sfunction1) {
		this.sfunction1 = sfunction1;
	}

	/**
	 * @return the rx1
	 */
	public String getRx1() {
		return rx1;
	}

	/**
	 * @param rx1 the rx1 to set
	 */
	public void setRx1(String rx1) {
		this.rx1 = rx1;
	}

	/**
	 * @return the tx1
	 */
	public String getTx1() {
		return tx1;
	}

	/**
	 * @param tx1 the tx1 to set
	 */
	public void setTx1(String tx1) {
		this.tx1 = tx1;
	}

	/**
	 * @return the rxTone1
	 */
	public String getRxTone1() {
		return rxTone1;
	}

	/**
	 * @param rxTone1 the rxTone1 to set
	 */
	public void setRxTone1(String rxTone1) {
		this.rxTone1 = rxTone1;
	}

	/**
	 * @return the txTone1
	 */
	public String getTxTone1() {
		return txTone1;
	}

	/**
	 * @param txTone1 the txTone1 to set
	 */
	public void setTxTone1(String txTone1) {
		this.txTone1 = txTone1;
	}

	/**
	 * @return the channel1
	 */
	public String getChannel1() {
		return channel1;
	}

	/**
	 * @param channel1 the channel1 to set
	 */
	public void setChannel1(String channel1) {
		this.channel1 = channel1;
	}

	/**
	 * @return the mode1
	 */
	public String getMode1() {
		return mode1;
	}

	/**
	 * @param mode1 the mode1 to set
	 */
	public void setMode1(String mode1) {
		this.mode1 = mode1;
	}

	/**
	 * @return the sfunction2
	 */
	public String getSfunction2() {
		return sfunction2;
	}

	/**
	 * @param sfunction2 the sfunction2 to set
	 */
	public void setSfunction2(String sfunction2) {
		this.sfunction2 = sfunction2;
	}

	/**
	 * @return the rx2
	 */
	public String getRx2() {
		return rx2;
	}

	/**
	 * @param rx2 the rx2 to set
	 */
	public void setRx2(String rx2) {
		this.rx2 = rx2;
	}

	/**
	 * @return the tx2
	 */
	public String getTx2() {
		return tx2;
	}

	/**
	 * @param tx2 the tx2 to set
	 */
	public void setTx2(String tx2) {
		this.tx2 = tx2;
	}

	/**
	 * @return the rxTone2
	 */
	public String getRxTone2() {
		return rxTone2;
	}

	/**
	 * @param rxTone2 the rxTone2 to set
	 */
	public void setRxTone2(String rxTone2) {
		this.rxTone2 = rxTone2;
	}

	/**
	 * @return the txTone2
	 */
	public String getTxTone2() {
		return txTone2;
	}

	/**
	 * @param txTone2 the txTone2 to set
	 */
	public void setTxTone2(String txTone2) {
		this.txTone2 = txTone2;
	}

	/**
	 * @return the channel2
	 */
	public String getChannel2() {
		return channel2;
	}

	/**
	 * @param channel2 the channel2 to set
	 */
	public void setChannel2(String channel2) {
		this.channel2 = channel2;
	}

	/**
	 * @return the mode2
	 */
	public String getMode2() {
		return mode2;
	}

	/**
	 * @param mode2 the mode2 to set
	 */
	public void setMode2(String mode2) {
		this.mode2 = mode2;
	}

	/**
	 * @return the sfunction3
	 */
	public String getSfunction3() {
		return sfunction3;
	}

	/**
	 * @param sfunction3 the sfunction3 to set
	 */
	public void setSfunction3(String sfunction3) {
		this.sfunction3 = sfunction3;
	}

	/**
	 * @return the rx3
	 */
	public String getRx3() {
		return rx3;
	}

	/**
	 * @param rx3 the rx3 to set
	 */
	public void setRx3(String rx3) {
		this.rx3 = rx3;
	}

	/**
	 * @return the tx3
	 */
	public String getTx3() {
		return tx3;
	}

	/**
	 * @param tx3 the tx3 to set
	 */
	public void setTx3(String tx3) {
		this.tx3 = tx3;
	}

	/**
	 * @return the rxTone3
	 */
	public String getRxTone3() {
		return rxTone3;
	}

	/**
	 * @param rxTone3 the rxTone3 to set
	 */
	public void setRxTone3(String rxTone3) {
		this.rxTone3 = rxTone3;
	}

	/**
	 * @return the txTone3
	 */
	public String getTxTone3() {
		return txTone3;
	}

	/**
	 * @param txTone3 the txTone3 to set
	 */
	public void setTxTone3(String txTone3) {
		this.txTone3 = txTone3;
	}

	/**
	 * @return the channel3
	 */
	public String getChannel3() {
		return channel3;
	}

	/**
	 * @param channel3 the channel3 to set
	 */
	public void setChannel3(String channel3) {
		this.channel3 = channel3;
	}

	/**
	 * @return the mode3
	 */
	public String getMode3() {
		return mode3;
	}

	/**
	 * @param mode3 the mode3 to set
	 */
	public void setMode3(String mode3) {
		this.mode3 = mode3;
	}

	/**
	 * @return the sfunction4
	 */
	public String getSfunction4() {
		return sfunction4;
	}

	/**
	 * @param sfunction4 the sfunction4 to set
	 */
	public void setSfunction4(String sfunction4) {
		this.sfunction4 = sfunction4;
	}

	/**
	 * @return the rx4
	 */
	public String getRx4() {
		return rx4;
	}

	/**
	 * @param rx4 the rx4 to set
	 */
	public void setRx4(String rx4) {
		this.rx4 = rx4;
	}

	/**
	 * @return the tx4
	 */
	public String getTx4() {
		return tx4;
	}

	/**
	 * @param tx4 the tx4 to set
	 */
	public void setTx4(String tx4) {
		this.tx4 = tx4;
	}

	/**
	 * @return the rxTone4
	 */
	public String getRxTone4() {
		return rxTone4;
	}

	/**
	 * @param rxTone4 the rxTone4 to set
	 */
	public void setRxTone4(String rxTone4) {
		this.rxTone4 = rxTone4;
	}

	/**
	 * @return the txTone4
	 */
	public String getTxTone4() {
		return txTone4;
	}

	/**
	 * @param txTone4 the txTone4 to set
	 */
	public void setTxTone4(String txTone4) {
		this.txTone4 = txTone4;
	}

	/**
	 * @return the channel4
	 */
	public String getChannel4() {
		return channel4;
	}

	/**
	 * @param channel4 the channel4 to set
	 */
	public void setChannel4(String channel4) {
		this.channel4 = channel4;
	}

	/**
	 * @return the mode4
	 */
	public String getMode4() {
		return mode4;
	}

	/**
	 * @param mode4 the mode4 to set
	 */
	public void setMode4(String mode4) {
		this.mode4 = mode4;
	}

	/**
	 * @return the sfunction5
	 */
	public String getSfunction5() {
		return sfunction5;
	}

	/**
	 * @param sfunction5 the sfunction5 to set
	 */
	public void setSfunction5(String sfunction5) {
		this.sfunction5 = sfunction5;
	}

	/**
	 * @return the rx5
	 */
	public String getRx5() {
		return rx5;
	}

	/**
	 * @param rx5 the rx5 to set
	 */
	public void setRx5(String rx5) {
		this.rx5 = rx5;
	}

	/**
	 * @return the tx5
	 */
	public String getTx5() {
		return tx5;
	}

	/**
	 * @param tx5 the tx5 to set
	 */
	public void setTx5(String tx5) {
		this.tx5 = tx5;
	}

	/**
	 * @return the rxTone5
	 */
	public String getRxTone5() {
		return rxTone5;
	}

	/**
	 * @param rxTone5 the rxTone5 to set
	 */
	public void setRxTone5(String rxTone5) {
		this.rxTone5 = rxTone5;
	}

	/**
	 * @return the txTone5
	 */
	public String getTxTone5() {
		return txTone5;
	}

	/**
	 * @param txTone5 the txTone5 to set
	 */
	public void setTxTone5(String txTone5) {
		this.txTone5 = txTone5;
	}

	/**
	 * @return the channel5
	 */
	public String getChannel5() {
		return channel5;
	}

	/**
	 * @param channel5 the channel5 to set
	 */
	public void setChannel5(String channel5) {
		this.channel5 = channel5;
	}

	/**
	 * @return the mode5
	 */
	public String getMode5() {
		return mode5;
	}

	/**
	 * @param mode5 the mode5 to set
	 */
	public void setMode5(String mode5) {
		this.mode5 = mode5;
	}

	/**
	 * @return the sfunction6
	 */
	public String getSfunction6() {
		return sfunction6;
	}

	/**
	 * @param sfunction6 the sfunction6 to set
	 */
	public void setSfunction6(String sfunction6) {
		this.sfunction6 = sfunction6;
	}

	/**
	 * @return the rx6
	 */
	public String getRx6() {
		return rx6;
	}

	/**
	 * @param rx6 the rx6 to set
	 */
	public void setRx6(String rx6) {
		this.rx6 = rx6;
	}

	/**
	 * @return the tx6
	 */
	public String getTx6() {
		return tx6;
	}

	/**
	 * @param tx6 the tx6 to set
	 */
	public void setTx6(String tx6) {
		this.tx6 = tx6;
	}

	/**
	 * @return the rxTone6
	 */
	public String getRxTone6() {
		return rxTone6;
	}

	/**
	 * @param rxTone6 the rxTone6 to set
	 */
	public void setRxTone6(String rxTone6) {
		this.rxTone6 = rxTone6;
	}

	/**
	 * @return the txTone6
	 */
	public String getTxTone6() {
		return txTone6;
	}

	/**
	 * @param txTone6 the txTone6 to set
	 */
	public void setTxTone6(String txTone6) {
		this.txTone6 = txTone6;
	}

	/**
	 * @return the channel6
	 */
	public String getChannel6() {
		return channel6;
	}

	/**
	 * @param channel6 the channel6 to set
	 */
	public void setChannel6(String channel6) {
		this.channel6 = channel6;
	}

	/**
	 * @return the mode6
	 */
	public String getMode6() {
		return mode6;
	}

	/**
	 * @param mode6 the mode6 to set
	 */
	public void setMode6(String mode6) {
		this.mode6 = mode6;
	}

	/**
	 * @return the sfunction7
	 */
	public String getSfunction7() {
		return sfunction7;
	}

	/**
	 * @param sfunction7 the sfunction7 to set
	 */
	public void setSfunction7(String sfunction7) {
		this.sfunction7 = sfunction7;
	}

	/**
	 * @return the rx7
	 */
	public String getRx7() {
		return rx7;
	}

	/**
	 * @param rx7 the rx7 to set
	 */
	public void setRx7(String rx7) {
		this.rx7 = rx7;
	}

	/**
	 * @return the tx7
	 */
	public String getTx7() {
		return tx7;
	}

	/**
	 * @param tx7 the tx7 to set
	 */
	public void setTx7(String tx7) {
		this.tx7 = tx7;
	}

	/**
	 * @return the rxTone7
	 */
	public String getRxTone7() {
		return rxTone7;
	}

	/**
	 * @param rxTone7 the rxTone7 to set
	 */
	public void setRxTone7(String rxTone7) {
		this.rxTone7 = rxTone7;
	}

	/**
	 * @return the txTone7
	 */
	public String getTxTone7() {
		return txTone7;
	}

	/**
	 * @param txTone7 the txTone7 to set
	 */
	public void setTxTone7(String txTone7) {
		this.txTone7 = txTone7;
	}

	/**
	 * @return the channel7
	 */
	public String getChannel7() {
		return channel7;
	}

	/**
	 * @param channel7 the channel7 to set
	 */
	public void setChannel7(String channel7) {
		this.channel7 = channel7;
	}

	/**
	 * @return the mode7
	 */
	public String getMode7() {
		return mode7;
	}

	/**
	 * @param mode7 the mode7 to set
	 */
	public void setMode7(String mode7) {
		this.mode7 = mode7;
	}

	/**
	 * @return the sfunction8
	 */
	public String getSfunction8() {
		return sfunction8;
	}

	/**
	 * @param sfunction8 the sfunction8 to set
	 */
	public void setSfunction8(String sfunction8) {
		this.sfunction8 = sfunction8;
	}

	/**
	 * @return the rx8
	 */
	public String getRx8() {
		return rx8;
	}

	/**
	 * @param rx8 the rx8 to set
	 */
	public void setRx8(String rx8) {
		this.rx8 = rx8;
	}

	/**
	 * @return the tx8
	 */
	public String getTx8() {
		return tx8;
	}

	/**
	 * @param tx8 the tx8 to set
	 */
	public void setTx8(String tx8) {
		this.tx8 = tx8;
	}

	/**
	 * @return the rxTone8
	 */
	public String getRxTone8() {
		return rxTone8;
	}

	/**
	 * @param rxTone8 the rxTone8 to set
	 */
	public void setRxTone8(String rxTone8) {
		this.rxTone8 = rxTone8;
	}

	/**
	 * @return the txTone8
	 */
	public String getTxTone8() {
		return txTone8;
	}

	/**
	 * @param txTone8 the txTone8 to set
	 */
	public void setTxTone8(String txTone8) {
		this.txTone8 = txTone8;
	}

	/**
	 * @return the channel8
	 */
	public String getChannel8() {
		return channel8;
	}

	/**
	 * @param channel8 the channel8 to set
	 */
	public void setChannel8(String channel8) {
		this.channel8 = channel8;
	}

	/**
	 * @return the mode8
	 */
	public String getMode8() {
		return mode8;
	}

	/**
	 * @param mode8 the mode8 to set
	 */
	public void setMode8(String mode8) {
		this.mode8 = mode8;
	}

	/**
	 * @return the role1
	 */
	public String getRole1() {
		return role1;
	}

	/**
	 * @param role1 the role1 to set
	 */
	public void setRole1(String role1) {
		this.role1 = role1;
	}

	/**
	 * @return the role2
	 */
	public String getRole2() {
		return role2;
	}

	/**
	 * @param role2 the role2 to set
	 */
	public void setRole2(String role2) {
		this.role2 = role2;
	}

	/**
	 * @return the role3
	 */
	public String getRole3() {
		return role3;
	}

	/**
	 * @param role3 the role3 to set
	 */
	public void setRole3(String role3) {
		this.role3 = role3;
	}

	/**
	 * @return the role4
	 */
	public String getRole4() {
		return role4;
	}

	/**
	 * @param role4 the role4 to set
	 */
	public void setRole4(String role4) {
		this.role4 = role4;
	}

	/**
	 * @return the role5
	 */
	public String getRole5() {
		return role5;
	}

	/**
	 * @param role5 the role5 to set
	 */
	public void setRole5(String role5) {
		this.role5 = role5;
	}

	/**
	 * @return the role6
	 */
	public String getRole6() {
		return role6;
	}

	/**
	 * @param role6 the role6 to set
	 */
	public void setRole6(String role6) {
		this.role6 = role6;
	}

	/**
	 * @return the name1
	 */
	public String getName1() {
		return name1;
	}

	/**
	 * @param name1 the name1 to set
	 */
	public void setName1(String name1) {
		this.name1 = name1;
	}

	/**
	 * @return the name2
	 */
	public String getName2() {
		return name2;
	}

	/**
	 * @param name2 the name2 to set
	 */
	public void setName2(String name2) {
		this.name2 = name2;
	}

	/**
	 * @return the name3
	 */
	public String getName3() {
		return name3;
	}

	/**
	 * @param name3 the name3 to set
	 */
	public void setName3(String name3) {
		this.name3 = name3;
	}

	/**
	 * @return the name4
	 */
	public String getName4() {
		return name4;
	}

	/**
	 * @param name4 the name4 to set
	 */
	public void setName4(String name4) {
		this.name4 = name4;
	}

	/**
	 * @return the name5
	 */
	public String getName5() {
		return name5;
	}

	/**
	 * @param name5 the name5 to set
	 */
	public void setName5(String name5) {
		this.name5 = name5;
	}

	/**
	 * @return the name6
	 */
	public String getName6() {
		return name6;
	}

	/**
	 * @param name6 the name6 to set
	 */
	public void setName6(String name6) {
		this.name6 = name6;
	}

	/**
	 * @return the includeThirdRow
	 */
	public Boolean getIncludeThirdRow() {
		return includeThirdRow;
	}

	/**
	 * @param includeThirdRow the includeThirdRow to set
	 */
	public void setIncludeThirdRow(Boolean includeThirdRow) {
		this.includeThirdRow = includeThirdRow;
	}

	/**
	 * @return the name1b
	 */
	public String getName1b() {
		return name1b;
	}

	/**
	 * @param name1b the name1b to set
	 */
	public void setName1b(String name1b) {
		this.name1b = name1b;
	}

	/**
	 * @return the name2b
	 */
	public String getName2b() {
		return name2b;
	}

	/**
	 * @param name2b the name2b to set
	 */
	public void setName2b(String name2b) {
		this.name2b = name2b;
	}

	/**
	 * @return the name3b
	 */
	public String getName3b() {
		return name3b;
	}

	/**
	 * @param name3b the name3b to set
	 */
	public void setName3b(String name3b) {
		this.name3b = name3b;
	}

	/**
	 * @return the name4b
	 */
	public String getName4b() {
		return name4b;
	}

	/**
	 * @param name4b the name4b to set
	 */
	public void setName4b(String name4b) {
		this.name4b = name4b;
	}

	/**
	 * @return the name5b
	 */
	public String getName5b() {
		return name5b;
	}

	/**
	 * @param name5b the name5b to set
	 */
	public void setName5b(String name5b) {
		this.name5b = name5b;
	}

	/**
	 * @return the name6b
	 */
	public String getName6b() {
		return name6b;
	}

	/**
	 * @param name6b the name6b to set
	 */
	public void setName6b(String name6b) {
		this.name6b = name6b;
	}


}
