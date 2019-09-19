package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.core.domain.impl.AdPaymentInfoImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.math.BigDecimal;
import java.util.Collection;

public interface RateClass extends Persistable {

	public void setRateClassName(String name);
	
	public String getRateClassName();
	
	public void setRateYear(Integer val);
	
	public Integer getRateYear();
	
	public void setStandard(Boolean val);
	
	public Boolean isStandard();

	public Collection<RateClassRate> getRateClassRates();

	public void setRateClassRates(Collection<RateClassRate> rateClassRates);
	
	
}