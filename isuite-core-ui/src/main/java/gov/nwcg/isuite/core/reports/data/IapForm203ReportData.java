package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.domain.IapPersonnelRes;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class IapForm203ReportData {
	private String incidentName;
	private String fromDate;
	private String toDate;
	private String fromTime;
	private String toTime;
	private String preparedBy;
	private String preparedByPosition;
	private String preparedDateTime;
	private String draftFinal;
	private String operationalPeriod;
	private String fromDayOfWeek;
	private String toDayOfWeek;
	private ArrayList<IapForm203PositionData> positionData = new ArrayList<IapForm203PositionData>();
	private JRBeanCollectionDataSource positions;
	
	public IapForm203ReportData() {
	}

	public static IapForm203ReportData getInstance(IapPlan iapPlan, IapForm203 entity) throws Exception {
		IapForm203ReportData reportData = new IapForm203ReportData();

		if(BooleanUtility.isTrue(entity.getIsFormLocked().getValue()))
			reportData.setDraftFinal("FINAL");
		else
			reportData.setDraftFinal("DRAFT");

		reportData.setOperationalPeriod(iapPlan.getOperationPeriod());
		
		// Block 1 Incident Name
		reportData.setIncidentName(iapPlan.getIncidentName());
		
		// Block 2 Operational Period
		if(DateUtil.hasValue(iapPlan.getFromDate())){
			reportData.setFromDate(DateUtil.toDateString(iapPlan.getFromDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
			reportData.setFromTime(DateUtil.toMilitaryTime(iapPlan.getFromDate()));
			reportData.setFromDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan.getFromDate()));
		}
		if(DateUtil.hasValue(iapPlan.getToDate())){
			reportData.setToDate(DateUtil.toDateString(iapPlan.getToDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
			reportData.setToTime(DateUtil.toMilitaryTime(iapPlan.getToDate()));
			if(reportData.getToTime().equals("2359"))
				reportData.setToTime("2400");
			
			reportData.setToDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan.getToDate()));
		}
		
	
		// Block 3 Positions
		int commanderCount=0;
		int agencyCount=0;
		int planningCount=0;
		int logisticsCount=0;
		int financeCount=0;
		int operationsCount=0;
		if(CollectionUtility.hasValue(entity.getIapPersonnels())){
			// load incident commanders
			int x=0;
			for(IapPersonnel p : entity.getIapPersonnels()){
				if(p.getSection().equals("INCIDENT_COMMANDER")){
					commanderCount++;
					if(x==0){
						// create the header record
						IapForm203PositionData data1 = new IapForm203PositionData();
						data1.setIsBlockTitle(true);
						data1.setBlockTitle("3. Incident Commander(s) and Command Staff:");
						data1.setIsBranch(false);
						reportData.getPositionData().add(data1);
					}
					IapForm203PositionData data = new IapForm203PositionData();
					data.setIsBlockTitle(false);
					data.setIsBranch(false);
					if(BooleanUtility.isFalse(p.getIsBlankLine().getValue())){
						data.setPosition(p.getRole());
						
						String resourceNames="";
						if(CollectionUtility.hasValue(p.getIapPersonnelResources())){
							for(IapPersonnelRes ipr : p.getIapPersonnelResources()){
								if(StringUtility.hasValue(ipr.getName())){
									resourceNames=resourceNames+(StringUtility.hasValue(resourceNames)?"\n":"")+ipr.getName()+(ipr.getIsTrainee().getValue()==true?" (T)":"");
								}
							}
						}
						data.setPositionName(resourceNames);
					}
					reportData.getPositionData().add(data);
					x++;
				}
			}
			
			// load Agency Orgs
			x=0;
			for(IapPersonnel p : entity.getIapPersonnels()){
				if(p.getSection().equals("AGENCY_SECTION")){
					agencyCount++;
					if(x==0){
						// create the header record
						IapForm203PositionData data1 = new IapForm203PositionData();
						data1.setIsBlockTitle(true);
						data1.setBlockTitle("4. Agency/Organization Representative(s):");
						data1.setIsBranch(false);
						reportData.getPositionData().add(data1);
						// create the sub header
						IapForm203PositionData data2 = new IapForm203PositionData();
						data2.setIsBlockTitle(false);
						data2.setIsAgencyLabel(true);
						data2.setIsBranch(false);
						reportData.getPositionData().add(data2);
					}
					IapForm203PositionData data = new IapForm203PositionData();
					data.setIsBlockTitle(false);
					data.setIsBranch(false);
					data.setIsAgencyData(true);
					if(BooleanUtility.isFalse(p.getIsBlankLine().getValue())){
						data.setIsAgencyData(true);
						
						String resourceNames="";
						if(CollectionUtility.hasValue(p.getIapPersonnelResources())){
							for(IapPersonnelRes ipr : p.getIapPersonnelResources()){
								if(StringUtility.hasValue(ipr.getName())){
									resourceNames=resourceNames+(StringUtility.hasValue(resourceNames)?"\n":"")+ipr.getName()+(ipr.getIsTrainee().getValue()==true?" (T)":"");
								}
							}
						}
						data.setPositionName(resourceNames);

						data.setAgencyName(p.getRole());
					}
					reportData.getPositionData().add(data);
					x++;
				}
			}

			// ensure at least 1 agency blank line
			if(agencyCount<1){
				// create the header record
				IapForm203PositionData data1 = new IapForm203PositionData();
				data1.setIsBlockTitle(true);
				data1.setBlockTitle("4. Agency/Organization Representatives:");
				data1.setIsBranch(false);
				reportData.getPositionData().add(data1);
				// create the sub header
				IapForm203PositionData data2 = new IapForm203PositionData();
				data2.setIsBlockTitle(false);
				data2.setIsAgencyLabel(true);
				data2.setIsBranch(false);
				reportData.getPositionData().add(data2);
			}
			
			// load Planning Section
			x=0;
			for(IapPersonnel p : entity.getIapPersonnels()){
				if(p.getSection().equals("PLANNING_SECTION")){
					planningCount++;
					if(x==0){
						// create the header record
						IapForm203PositionData data1 = new IapForm203PositionData();
						data1.setIsBlockTitle(true);
						data1.setBlockTitle("5. Planning Section:");
						data1.setIsBranch(false);
						reportData.getPositionData().add(data1);
					}
					IapForm203PositionData data = new IapForm203PositionData();
					data.setIsBlockTitle(false);
					data.setIsBranch(false);
					if(BooleanUtility.isFalse(p.getIsBlankLine().getValue())){
						data.setPosition(p.getRole());

						String resourceNames="";
						if(CollectionUtility.hasValue(p.getIapPersonnelResources())){
							for(IapPersonnelRes ipr : p.getIapPersonnelResources()){
								if(StringUtility.hasValue(ipr.getName())){
									resourceNames=resourceNames+(StringUtility.hasValue(resourceNames)?"\n":"")+ipr.getName()+(ipr.getIsTrainee().getValue()==true?" (T)":"");
								}
							}
						}
						data.setPositionName(resourceNames);
					}
					reportData.getPositionData().add(data);
					x++;
				}
			}

			// load Logistics Section
			x=0;
			for(IapPersonnel p : entity.getIapPersonnels()){
				if(p.getSection().equals("LOGISTICS_SECTION")){
					logisticsCount++;
					if(x==0){
						// create the header record
						IapForm203PositionData data1 = new IapForm203PositionData();
						data1.setIsBlockTitle(true);
						data1.setBlockTitle("6. Logistics Section:");
						data1.setIsBranch(false);
						reportData.getPositionData().add(data1);
					}
					IapForm203PositionData data = new IapForm203PositionData();
					data.setIsBlockTitle(false);
					data.setIsBranch(false);
					if(BooleanUtility.isFalse(p.getIsBlankLine().getValue())){
						data.setPosition(p.getRole());
						
						String resourceNames="";
						if(CollectionUtility.hasValue(p.getIapPersonnelResources())){
							for(IapPersonnelRes ipr : p.getIapPersonnelResources()){
								if(StringUtility.hasValue(ipr.getName())){
									resourceNames=resourceNames+(StringUtility.hasValue(resourceNames)?"\n":"")+ipr.getName()+(ipr.getIsTrainee().getValue()==true?" (T)":"");
								}
							}
						}
						data.setPositionName(resourceNames);
					}
					reportData.getPositionData().add(data);
					x++;
				}
			}

			// load Operations Section
			x=0;
			for(IapPersonnel p : entity.getIapPersonnels()){
				if(p.getSection().equals("OPERATIONS_SECTION")){
					operationsCount++;
					if(x==0){
						// create the header record
						IapForm203PositionData data1 = new IapForm203PositionData();
						data1.setIsBlockTitle(true);
						data1.setBlockTitle("7. Operations Section:");
						data1.setIsBranch(false);
						reportData.getPositionData().add(data1);
					}
					IapForm203PositionData data = new IapForm203PositionData();
					data.setIsBlockTitle(false);
					data.setIsBranch(false);
					if(BooleanUtility.isFalse(p.getIsBlankLine().getValue())){
						data.setPosition(p.getRole());
						
						String resourceNames="";
						if(CollectionUtility.hasValue(p.getIapPersonnelResources())){
							for(IapPersonnelRes ipr : p.getIapPersonnelResources()){
								if(StringUtility.hasValue(ipr.getName())){
									resourceNames=resourceNames+(StringUtility.hasValue(resourceNames)?"\n":"")+ipr.getName()+(ipr.getIsTrainee().getValue()==true?" (T)":"");
								}
							}
						}
						data.setPositionName(resourceNames);
					}
					reportData.getPositionData().add(data);
					x++;
				}
			}

			// load branch section
			x=0;
			Collection<String> distinctBranches = new ArrayList<String>();
			HashMap<String,Long> branchHash = new HashMap<String,Long>();
			
			for(IapPersonnel p : entity.getIapPersonnels()){
				if(p.getSection().equals("BRANCH_SECTION") && BooleanUtility.isTrue(p.getIsBranchName().getValue())){
					Boolean bfound=false;
					for(String s : distinctBranches){
						String name="";
						if(StringUtility.hasValue(s))
							name=s;
						else
							name="[Blank Branch]";
						
						if(name.equals(p.getName())){
							bfound=true;
							break;
						}
					}
					if(bfound==false){
						String name="";
						if(StringUtility.hasValue(p.getName()))
							name=p.getName();
						else
							name="[Blank Branch]";

						distinctBranches.add(name);
						branchHash.put(name, p.getId());
					}
				}
			}
			for(String branchName : distinctBranches){
				// create the header record
				IapForm203PositionData data1 = new IapForm203PositionData();
				data1.setIsBlockTitle(false);
				data1.setBranchName(branchName);
				data1.setIsBranch(true);
				
				if(entity.getIsNoBranch().getValue()==true)
					data1.setBranchLabel("");
				else{
					if(!branchName.equals("[Blank Branch]"))
						data1.setBranchLabel("Branch");
					else
						data1.setBranchLabel("");
				}
					
				reportData.getPositionData().add(data1);
				Long branchId=branchHash.get(branchName);
				
				for(IapPersonnel p : entity.getIapPersonnels()){
					if(p.getSection().equals("BRANCH_SECTION")){
						if(LongUtility.hasValue(p.getIapBranchPersonnelParentId())){
							if(p.getIapBranchPersonnelParentId().compareTo(branchId)==0){
								IapForm203PositionData data = new IapForm203PositionData();
								data.setIsBlockTitle(false);
								data.setIsBranch(false);
								if(BooleanUtility.isFalse(p.getIsBlankLine().getValue())){
									if(p.getRole().equals("DIVISION/GROUP"))
										data.setIsDivGroupPosition(true);

									data.setDivisionGroup(p.getDivisionGroupName());
									data.setPosition(p.getRole());

									String resourceNames="";
									if(CollectionUtility.hasValue(p.getIapPersonnelResources())){
										for(IapPersonnelRes ipr : p.getIapPersonnelResources()){
											if(StringUtility.hasValue(ipr.getName())){
												resourceNames=resourceNames+(StringUtility.hasValue(resourceNames)?"\n":"")+ipr.getName()+(ipr.getIsTrainee().getValue()==true?" (T)":"");
											}
										}
									}
									data.setPositionName(resourceNames);
								}
								reportData.getPositionData().add(data);
							}
						}
					}
				}
			}

			// load Air Ops Section
			x=0;
			for(IapPersonnel p : entity.getIapPersonnels()){
				if(p.getSection().equals("AIR_OPERATIONS")){
					financeCount++;
					if(x==0){
						// create the header record
						IapForm203PositionData data1 = new IapForm203PositionData();
						data1.setIsBlockTitle(true);
						data1.setBlockTitle("7b. Air Operations Branch:");
						data1.setIsBranch(false);
						reportData.getPositionData().add(data1);
					}
					IapForm203PositionData data = new IapForm203PositionData();
					data.setIsBlockTitle(false);
					data.setIsBranch(false);
					if(BooleanUtility.isFalse(p.getIsBlankLine().getValue())){
						data.setPosition(p.getRole());
						
						String resourceNames="";
						if(CollectionUtility.hasValue(p.getIapPersonnelResources())){
							for(IapPersonnelRes ipr : p.getIapPersonnelResources()){
								if(StringUtility.hasValue(ipr.getName())){
									resourceNames=resourceNames+(StringUtility.hasValue(resourceNames)?"\n":"")+ipr.getName()+(ipr.getIsTrainee().getValue()==true?" (T)":"");
								}
							}
						}
						data.setPositionName(resourceNames);
					}
					reportData.getPositionData().add(data);
					x++;
				}
			}
			
			// load Finance Section
			x=0;
			for(IapPersonnel p : entity.getIapPersonnels()){
				if(p.getSection().equals("FINANCE_SECTION")){
					financeCount++;
					if(x==0){
						// create the header record
						IapForm203PositionData data1 = new IapForm203PositionData();
						data1.setIsBlockTitle(true);
						data1.setBlockTitle("8. Finance/Administration Section:");
						data1.setIsBranch(false);
						reportData.getPositionData().add(data1);
					}
					IapForm203PositionData data = new IapForm203PositionData();
					data.setIsBlockTitle(false);
					data.setIsBranch(false);
					if(BooleanUtility.isFalse(p.getIsBlankLine().getValue())){
						data.setPosition(p.getRole());
						
						String resourceNames="";
						if(CollectionUtility.hasValue(p.getIapPersonnelResources())){
							for(IapPersonnelRes ipr : p.getIapPersonnelResources()){
								if(StringUtility.hasValue(ipr.getName())){
									resourceNames=resourceNames+(StringUtility.hasValue(resourceNames)?"\n":"")+ipr.getName()+(ipr.getIsTrainee().getValue()==true?" (T)":"");
								}
							}
						}
						data.setPositionName(resourceNames);
					}
					reportData.getPositionData().add(data);
					x++;
				}
			}
			
		}
		/*
		else{
			if(commanderCount<1){
				// create the header record
				IapForm203PositionData data1 = new IapForm203PositionData();
				data1.setIsBlockTitle(true);
				data1.setBlockTitle("3. Incident Commander(s):");
				data1.setIsBranch(false);
				reportData.getPositionData().add(data1);
				// create the header record
				IapForm203PositionData data2 = new IapForm203PositionData();
				data2.setIsBlockTitle(false);
				data2.setBlockTitle("");
				data2.setIsBranch(false);
				data2.setPositionName("");
				data2.setPosition("");
				reportData.getPositionData().add(data2);
			}
			if(agencyCount<1){
				// create the header record
				IapForm203PositionData data1 = new IapForm203PositionData();
				data1.setIsBlockTitle(true);
				data1.setBlockTitle("4. Agency/Org Representatives");
				data1.setIsBranch(false);
				reportData.getPositionData().add(data1);
				// create the sub header
				IapForm203PositionData data2 = new IapForm203PositionData();
				data2.setIsBlockTitle(false);
				data2.setIsAgencyLabel(true);
				data2.setIsBranch(false);
				reportData.getPositionData().add(data2);
			}
			if(planningCount<1){
				// create the header record
				IapForm203PositionData data1 = new IapForm203PositionData();
				data1.setIsBlockTitle(true);
				data1.setBlockTitle("5. Planning Section:");
				data1.setIsBranch(false);
				reportData.getPositionData().add(data1);
				IapForm203PositionData data2 = new IapForm203PositionData();
				data2.setIsBlockTitle(false);
				data2.setBlockTitle("");
				data2.setIsBranch(false);
				data2.setPositionName("");
				data2.setPosition("");
				reportData.getPositionData().add(data2);
			}
			if(logisticsCount<1){
				// create the header record
				IapForm203PositionData data1 = new IapForm203PositionData();
				data1.setIsBlockTitle(true);
				data1.setBlockTitle("6. Logistics Section:");
				data1.setIsBranch(false);
				reportData.getPositionData().add(data1);
				IapForm203PositionData data2 = new IapForm203PositionData();
				data2.setIsBlockTitle(false);
				data2.setBlockTitle("");
				data2.setIsBranch(false);
				data2.setPositionName("");
				data2.setPosition("");
				reportData.getPositionData().add(data2);
			}
			if(operationsCount<1){
				// create the header record
				IapForm203PositionData data1 = new IapForm203PositionData();
				data1.setIsBlockTitle(true);
				data1.setBlockTitle("7. Operations Section:");
				data1.setIsBranch(false);
				reportData.getPositionData().add(data1);
				IapForm203PositionData data2 = new IapForm203PositionData();
				data2.setIsBlockTitle(false);
				data2.setBlockTitle("");
				data2.setIsBranch(false);
				data2.setPositionName("");
				data2.setPosition("");
				reportData.getPositionData().add(data2);
			}
			if(financeCount<1){
				IapForm203PositionData data1 = new IapForm203PositionData();
				data1.setIsBlockTitle(true);
				data1.setBlockTitle("8. Finance/Admin Section:");
				data1.setIsBranch(false);
				reportData.getPositionData().add(data1);
				IapForm203PositionData data2 = new IapForm203PositionData();
				data2.setIsBlockTitle(false);
				data2.setBlockTitle("");
				data2.setIsBranch(false);
				data2.setPositionName("");
				data2.setPosition("");
				reportData.getPositionData().add(data2);
			}
			
		}
		*/
		
		// ensure position data is multiple of 68 for up to 10 pages
		int posDataSize=reportData.getPositionData().size();
		//int addSize=getAddSize(posDataSize);
		int addSize=0;
		/*
		for(int a=1;a<10;a++){
			if( (posDataSize > ((a * 68) - 68 )) && (posDataSize < (a * 68)) ){
				addSize=( (a * 68) - posDataSize);
				break;
			}
		}
		for(int i = 0;i<addSize;i++){
			IapForm203PositionData data1 = new IapForm203PositionData();
			data1.setIsBlockTitle(false);
			data1.setBlockTitle("");
			data1.setIsBranch(false);
			data1.setPosition("");
			data1.setPositionName("");
			reportData.getPositionData().add(data1);
		}
		*/
		
		// Block 9 Prepared By
		reportData.setPreparedBy(entity.getPreparedBy());
		reportData.setPreparedByPosition(entity.getPreparedByPosition());
		if(DateUtil.hasValue(entity.getPreparedDate())){
			String sdate=DateUtil.toDateString(entity.getPreparedDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String stime=DateUtil.toMilitaryTime(entity.getPreparedDate());
			reportData.setPreparedDateTime(sdate+" " + stime);
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
	 * @param incidentName the incidentName to set
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
	 * @param fromDate the fromDate to set
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
	 * @param toDate the toDate to set
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
	 * @param fromTime the fromTime to set
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
	 * @param toTime the toTime to set
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
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
	 * @param preparedByPosition the preparedByPosition to set
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
	 * @param preparedDateTime the preparedDateTime to set
	 */
	public void setPreparedDateTime(String preparedDateTime) {
		this.preparedDateTime = preparedDateTime;
	}

	/**
	 * @return the positionData
	 */
	public ArrayList<IapForm203PositionData> getPositionData() {
		return positionData;
	}

	/**
	 * @param positionData the positionData to set
	 */
	public void setPositionData(ArrayList<IapForm203PositionData> positionData) {
		this.positionData = positionData;
	}

	/**
	 * @return the positions
	 */
	public JRBeanCollectionDataSource getPositions() {
		return new JRBeanCollectionDataSource(this.positionData);
	}

	/**
	 * @param positions the positions to set
	 */
	public void setPositions(JRBeanCollectionDataSource positions) {
		this.positions = positions;
	}

	/**
	 * @return the draftFinal
	 */
	public String getDraftFinal() {
		return draftFinal;
	}

	/**
	 * @param draftFinal the draftFinal to set
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
	 * @param operationalPeriod the operationalPeriod to set
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
	 * @param fromDayOfWeek the fromDayOfWeek to set
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
	 * @param toDayOfWeek the toDayOfWeek to set
	 */
	public void setToDayOfWeek(String toDayOfWeek) {
		this.toDayOfWeek = toDayOfWeek;
	}
	
	
}
