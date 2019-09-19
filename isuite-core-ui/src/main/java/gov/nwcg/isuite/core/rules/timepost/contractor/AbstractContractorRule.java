package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class AbstractContractorRule extends AbstractRule {
	protected AssignmentTimePostVo vo=null;
	protected AssignmentTimePostVo specialVo=null;
	protected AssignmentTime assignmentTimeEntity=null;
	protected IncidentResource incidentResourceEntity=null;
	protected TimePostDao tpDao = null;
	protected TimePostQueryFilterImpl tpqFilter=null;
	protected String postType="";
	
	public AbstractContractorRule(ApplicationContext ctx){
		context=ctx;
	}

	public AbstractContractorRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(TimePostContractorRuleFactory.ObjectTypeEnum.ASSIGNMENT_TIME_POST_VO.name()))
			vo = (AssignmentTimePostVo)object;

		if(objectName.equals(TimePostContractorRuleFactory.ObjectTypeEnum.ASSIGNMENT_TIME_POST_SPECIAL_VO.name()))
			specialVo = (AssignmentTimePostVo)object;
		
		if(objectName.equals(TimePostContractorRuleFactory.ObjectTypeEnum.ASSIGNMENT_TIME_ENTITY.name()))
			assignmentTimeEntity = (AssignmentTime)object;
		
		if(objectName.equals(TimePostContractorRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_ENTITY.name()))
			incidentResourceEntity = (IncidentResource)object;

		if(objectName.equals(TimePostContractorRuleFactory.ObjectTypeEnum.TIME_POST_DAO.name()))
			tpDao = (TimePostDao)object;
	
		if(objectName.equals(TimePostContractorRuleFactory.ObjectTypeEnum.POST_TYPE.name()))
			postType = (String)object;
	}

	/**
	 * @param dupDates
	 * @return
	 */
	protected String toDateDelimitedString(Collection<String> dupDates){
		StringBuffer str=new StringBuffer();
		for(String s : dupDates){
			if(str.length()>0)
				str.append(" , ");
			str.append(s);
		}
		return str.toString();
	}
	
	/**
	 * Returns whether or not the time posting is a primary posting.
	 * @param vo
	 * @return
	 */
	protected Boolean isExclusivePrimaryContractorPost(){
		if(vo.getPrimaryPosting() && !vo.getSpecialPosting()){
			if(null != vo && null != vo.getRefContractorRateVo()){
				if(LongUtility.hasValue(vo.getRefContractorRateVo().getId())){
					if(StringUtility.hasValue(vo.getRefContractorRateVo().getUnitOfMeasure()))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns the Primary Posting Unit of Measure if available.
	 * 
	 * @return
	 */
	protected String getPrimaryUnitOfMeasure(){
		if(null != vo.getRefContractorRateVo()){
			if(LongUtility.hasValue(vo.getRefContractorRateVo().getId())){
				if(StringUtility.hasValue(vo.getRefContractorRateVo().getUnitOfMeasure()))
					return vo.getRefContractorRateVo().getUnitOfMeasure();
			}
		}
		return "";
	}

	/**
	 * Returns whether or not the time posting has an exclusive guarantee posting.
	 * 
	 * @param vo
	 * @return
	 */
	protected Boolean isExclusiveGuaranteeContractorPost(){
		if(vo.getGuaranteePosting()){
			if(null != vo && null != vo.getRefContractorRateVo()){
				if(LongUtility.hasValue(vo.getRefContractorRateVo().getId())){
					if(StringUtility.hasValue(vo.getRefContractorRateVo().getUnitOfMeasure()))
						return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the Exclusive Special Posting Unit of Measure if available.
	 * 
	 * @return
	 */
	protected String getExclusiveSpecialUnitOfMeasure(){
		if(null != vo.getRefContractorRateVo()){
			if(LongUtility.hasValue(vo.getRefContractorRateVo().getId())){
				if(StringUtility.hasValue(vo.getRefContractorRateVo().getUnitOfMeasure()))
					return vo.getRefContractorRateVo().getUnitOfMeasure();
			}
		}
		return "";
	}

	/**
	 * Returns the Special Posting Unit of Measure if available.
	 * 
	 * @return
	 */
	protected String getSpecialUnitOfMeasure(){
		if(null != specialVo && specialVo.getContractorPostType().equals("SPECIAL")){
			return specialVo.getUnitOfMeasure();
		}
		
		return "";
	}

	/**
	 * Returns the Special Posting Accounting Code if available.
	 * 
	 * @return
	 */
	protected String getSpecialAccountingCode(){
		if(null != specialVo){
			if(null != specialVo.getIncidentAccountCodeVo()){
				if(null != specialVo.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode()){
					return specialVo.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode();
				}
			}
		}
		return "";
	}

	/**
	 * Returns the Primary Posting Accounting Code if available.
	 * 
	 * @return
	 */
	protected String getPrimaryAccountingCode(){
		if(null != vo.getIncidentAccountCodeVo()){
			if(null != vo.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode()){
				return vo.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode();
			}
		}
		return "";
	}
	
}
