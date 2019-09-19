package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.math.BigDecimal;

public interface CostAccrualExtractRsc extends Persistable{

    
    
//    
//    CREATED_BY     VARCHAR2(50 BYTE),
//    CREATED_DATE TIMESTAMP (6) DEFAULT SYSDATE,
//    CREATED_BY_ID    NUMBER,
//    LAST_MODIFIED_BY VARCHAR2(50 BYTE),
//    LAST_MODIFIED_DATE TIMESTAMP (6) DEFAULT SYSDATE,
//    LAST_MODIFIED_BY_ID NUMBER

	
	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the costAccrualExtractId the id CostAccrualExtract
	 */
	public Long getCostAccrualExtractId();
	
	/**
	 * @param costAccrualExtractId the costAccrualExtractId to set
	 */
	public void setCostAccrualExtractId(Long costAccrualExtractId) ;
	
	/**
	 * @return
	 */
	public IncidentResource getIncidentResource();

	/**
	 * @param incidentResource
	 */
	public void setIncidentResource(IncidentResource incidentResource);
	
	/**
	 * @return the incidentResourceId the incidentResource
	 */
	public Long getIncidentResourceId();
	
	/**
	 * @param incidentResource the incidentResource to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) ;
	
	
	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() ;

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) ;
	
	/**
	 * @return the changeAmount
	 */
	public BigDecimal getChangeAmount() ;

	/**
	 * @param changeAmount the changeAmount to set
	 */
	public void setChangeAmount(BigDecimal changeAmount) ;	

	/**
	 * @return the costAccrualCode
	 */
	public String getCostAccrualCode() ;

	/**
	 * @param costAccrualCode the costAccrualCode to set
	 */
	public void setCostAccrualCode(String costAccrualCode) ;
	
	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode);

	/**
	 * @return the accountCode
	 */
	public String getAccountCode();

	/**
	 * @param fiscalYear the fiscalYear to set
	 */
	public void setFiscalYear(String fiscalYear);

	/**
	 * @return the fiscalYear
	 */
	public String getFiscalYear();

	/**
	 * @param drawDown the drawDown to set
	 */
	public void setDrawDown(StringBooleanEnum drawDown);

	/**
	 * @return the drawDown
	 */
	public StringBooleanEnum getDrawDown();
	
	public IncidentResourceOther getIncidentResourceOther();
	public void setIncidentResourceOther(IncidentResourceOther incidentResourceOther);
	public Long getIncidentResourceOtherId();
	public void setIncidentResourceOtherId(Long incidentResourceOtherId);
}
