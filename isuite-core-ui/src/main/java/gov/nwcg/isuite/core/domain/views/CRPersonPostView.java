package gov.nwcg.isuite.core.domain.views;

import java.math.BigDecimal;
import java.util.Date;

public interface CRPersonPostView {
	
	public Long getIncidentId();

	public void setIncidentId(Long incidentId);
	
	public String getAccountCode();

	public void setAccountCode(String accountCode);

	public String getItemCode();

	public void setItemCode(String itemCode);

	public String getItemCodeDescription();

	public void setItemCodeDescription(String itemCodeDescription);

	public BigDecimal getNumberOfHours();

	public void setNumberOfHours(BigDecimal numberOfHours);

	public BigDecimal getRateAmount();

	public void setRateAmount(BigDecimal rateAmount);

	public String getRateClass();

	public void setRateClass(String rateClass);

	public String getRequestNumber();

	public void setRequestNumber(String requestNumber);

	public String getResourceName();

	public void setResourceName(String resourceName);

	public String getSectionCode();

	public void setSectionCode(String sectionCode);

	public String getSectionCodeDescription();

	public void setSectionCodeDescription(String sectionCodeDescription);

	public Date getStartDate();

	public void setStartDate(Date startDate);
	
	public Date getStartTime();

	public void setStartTime(Date startTime);

	public Date getStopDate();

	public void setStopDate(Date stopDate);
	
	public Date getStopTime();

	public void setStopTime(Date stopTime);

}