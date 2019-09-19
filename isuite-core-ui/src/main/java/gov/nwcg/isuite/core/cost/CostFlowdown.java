package gov.nwcg.isuite.core.cost;

import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.vo.DailyCostVo;
import gov.nwcg.isuite.framework.exceptions.DailyCostException;
import gov.nwcg.isuite.framework.types.CostLevelEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.ChangeUtil;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CostFlowdown {
	private ApplicationContext context;
	public Long incidentResourceId;
	public Long incidentResourceOtherId;
	public Collection<DailyCostVo> dailyCostVos = new ArrayList<DailyCostVo>();
	public IncidentResourceDailyCostDao irdcDao=null;
	
	public CostFlowdown(ApplicationContext ctx){
		this.context=ctx;
	}
	
	public void userFlowdown(DailyCostVo preSaveVo, DailyCostVo flowdownSourceVo) throws DailyCostException{

		try{
			Collection<String> updateSqls=new ArrayList<String>();
			
			for(DailyCostVo dcvo : dailyCostVos ) {
				if(dcvo.getId().compareTo(flowdownSourceVo.getId())!=0){
					String costLevel=(StringUtility.hasValue(dcvo.getCostLevel())?dcvo.getCostLevel():"");
					String costLevelSource=(StringUtility.hasValue(flowdownSourceVo.getCostLevel())?flowdownSourceVo.getCostLevel():"");
					Boolean doFlowdown=true;

					// M records only flowdown to other M records
					if(costLevelSource.equals("M") && !costLevel.equals("M")){
						doFlowdown=false;
					}

					if(!costLevelSource.equals("M") && costLevel.equals("M")){
						doFlowdown=false;
					}
					
					// only flowdown matching acct code ids - per defect 3810
					//System.out.println(flowdownSourceVo.getIncidentAccountCodeId());
					//System.out.println(dcvo.getIncidentAccountCodeId());
					if(flowdownSourceVo.getIncidentAccountCodeId().compareTo(dcvo.getIncidentAccountCodeId())!=0){
						doFlowdown=false;
					}

					if(BooleanUtility.isFalse(dcvo.getIsLocked()) && doFlowdown==true){
						if(costLevelSource.equals("A")){
							dcvo.setCostLevel("F");
						}else{
							// only update costlevel if source is "U"
							if(costLevelSource.equals("U")){
								dcvo.setCostLevel("U");
							}
						}

						dcvo.setIsFlowdown(false);
						dcvo.setIsLocked(flowdownSourceVo.getIsLocked());
						
						if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(flowdownSourceVo.getAircraftCostAmount(), preSaveVo.getAircraftCostAmount())))
							dcvo.setAircraftCostAmount(flowdownSourceVo.getAircraftCostAmount());
					
						if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(flowdownSourceVo.getUnitCostAmount(), preSaveVo.getUnitCostAmount())))
							dcvo.setUnitCostAmount(flowdownSourceVo.getUnitCostAmount());
						
						if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(flowdownSourceVo.getUnits(), preSaveVo.getUnits())))
							dcvo.setUnits(flowdownSourceVo.getUnits());
						
						//if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(flowdownSourceVo.getPrimaryTotalAmount(), preSaveVo.getPrimaryTotalAmount())))
						//	entity.setPrimaryTotalAmount(flowdownSourceVo.getPrimaryTotalAmount());
						BigDecimal amt = dcvo.getUnits().multiply(dcvo.getUnitCostAmount());
						dcvo.setPrimaryTotalAmount(amt);
						
						if(DecimalUtil.hasValue(dcvo.getSubordinateTotalAmount())){
							BigDecimal total = dcvo.getPrimaryTotalAmount().add(dcvo.getSubordinateTotalAmount());
							dcvo.setTotalCostAmount(total);
						}else
							dcvo.setTotalCostAmount(dcvo.getPrimaryTotalAmount());
						
						if(BooleanUtility.isTrue(ChangeUtil.isLongChanged(flowdownSourceVo.getAccrualCodeId(), preSaveVo.getAccrualCodeId())))
							dcvo.setAccrualCodeId(flowdownSourceVo.getAccrualCodeId());
						
						if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(flowdownSourceVo.getCargoPounds(), preSaveVo.getCargoPounds())))
							dcvo.setCargoPounds(flowdownSourceVo.getCargoPounds());
						
						if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(flowdownSourceVo.getFlightHours(), preSaveVo.getFlightHours())))
							dcvo.setFlightHours(flowdownSourceVo.getFlightHours());
						
						if(BooleanUtility.isTrue(ChangeUtil.isIntegerChanged(flowdownSourceVo.getNumberOfLoads(), preSaveVo.getNumberOfLoads())))
							dcvo.setNumberOfLoads(flowdownSourceVo.getNumberOfLoads());
						
						if(BooleanUtility.isTrue(ChangeUtil.isIntegerChanged(flowdownSourceVo.getNumberOfPassengers(), preSaveVo.getNumberOfPassengers())))
							dcvo.setNumberOfPassengers(flowdownSourceVo.getNumberOfPassengers());

						if(BooleanUtility.isTrue(ChangeUtil.isIntegerChanged(flowdownSourceVo.getNumberOfTrips(), preSaveVo.getNumberOfTrips())))
							dcvo.setNumberOfTrips(flowdownSourceVo.getNumberOfTrips());
						
						if(BooleanUtility.isTrue(ChangeUtil.isStringChanged(flowdownSourceVo.getRateType(), preSaveVo.getRateType())))
							dcvo.setRateType(flowdownSourceVo.getRateType());
						
						if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(flowdownSourceVo.getRetardantGallons(), preSaveVo.getRetardantGallons())))
							dcvo.setRetardantGallons(flowdownSourceVo.getRetardantGallons());
						
						if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(flowdownSourceVo.getWaterGallons(), preSaveVo.getWaterGallons())))
							dcvo.setWaterGallons(flowdownSourceVo.getWaterGallons());

						if(BooleanUtility.isTrue(ChangeUtil.isLongChanged(flowdownSourceVo.getCostGroupId(), preSaveVo.getCostGroupId()))){
							dcvo.setCostGroupId(flowdownSourceVo.getCostGroupId());
							dcvo.setIncidentShiftId(flowdownSourceVo.getIncidentShiftId());
						}
						
						if(BooleanUtility.isTrue(ChangeUtil.isLongChanged(flowdownSourceVo.getIncidentAccountCodeId(), preSaveVo.getIncidentAccountCodeId()))){
							dcvo.setIncidentAccountCodeId(flowdownSourceVo.getIncidentAccountCodeId());
						}

						String sql = dcvo.toSql(irdcDao.isOracleDialect());
						updateSqls.add(sql);				
						
						String sqlLock="update isw_inc_res_daily_cost ";
						if(BooleanUtility.isTrue(dcvo.getIsLocked())){
							 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 1 : true) + " ";
						}else{
							 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 0 : false) + " ";
						}
						sqlLock=sqlLock+"where to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') = " +
						 "  to_date('"+DateUtil.toDateString(dcvo.getActivityDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
						 "and incident_resource_id = " + dcvo.getIncidentResourceId() + " "+
						 "";
						updateSqls.add(sqlLock);
					}
				}
			}
			
			if(CollectionUtility.hasValue(updateSqls))
				irdcDao.persistSqls(updateSqls);
			
		}catch(Exception e){
			DailyCostException dce = new DailyCostException(e.getMessage());
			
			throw dce;
		}
	}

	private Boolean isChanged(Long preSaveId, Long postSaveId){
		if(!LongUtility.hasValue(preSaveId) && LongUtility.hasValue(postSaveId))
			return true;
		
		if(LongUtility.hasValue(preSaveId) && !LongUtility.hasValue(postSaveId))
			return true;
		
		if(LongUtility.hasValue(preSaveId) && LongUtility.hasValue(postSaveId)){
			if(preSaveId.compareTo(postSaveId)!=0)
				return true;
		}
		
		return false;
	}
	
	
}
