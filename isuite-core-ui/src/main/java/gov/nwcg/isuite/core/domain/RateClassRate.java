package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.core.domain.impl.AdPaymentInfoImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.math.BigDecimal;
import java.util.Collection;

public interface RateClassRate extends Persistable {

	
	public void setRate(BigDecimal val);
	
	public BigDecimal getRate();
	
	public void setArea(String area);
	
	public String getArea();
	
	public void setRateYear(Integer val);
	
	public Integer getRateYear();
	
	public void setStandard(Boolean val);
	
	public Boolean isStandard();


	/**
	 * @return the assignmentTimePosts
	 */
	public Collection<AssignmentTimePost> getAssignmentTimePosts();


	/**
	 * @param assignmentTimePosts the assignmentTimePosts to set
	 */
	public void setAssignmentTimePosts(Collection<AssignmentTimePost> assignmentTimePosts) ;

	public RateClass getRateClass();

	public void setRateClass(RateClass rateClass);

	public Long getRateClassId();

	public void setRateClassId(Long rateClassId) ;

	public String getRateClassName();

	public void setRateClassName(String rateClassName);

	public String getRateClassCode() ;

	public void setRateClassCode(String rateClassCode);
	
	public RateClassRate getTrainingRateClassRate() ;


	public void setTrainingRateClassRate(RateClassRate trainingRateClassRate) ;


	public Long getTrainingRateClassRateId() ;


	public void setTrainingRateClassRateId(Long trainingRateClassRateId) ;
	
   /**
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active);
	
	/**
	 * @return the active
	 */
	public StringBooleanEnum isActive();
	
	/**
	 * @param adPaymentInfos the adPaymentInfos to set
	 */
	public void setAdPaymentInfos(Collection<AdPaymentInfo> adPaymentInfos);

	/**
	 * @return the adPaymentInfos
	 */
	public Collection<AdPaymentInfo> getAdPaymentInfos();
	
}