package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.AdPaymentInfo;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.impl.AdPaymentInfoImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.RateAreaEnum;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.VoValidator;

import java.util.Date;

import com.ibm.xml.crypto.util.Base64;

public class AdPaymentInfoVo extends AbstractVo {
	private RateAreaVo rateAreaVo;
	private RateClassRateVo rateClassRateVo;
	private Boolean initialEmp;
	private Boolean returnTravel;
	private String pointOfHire;
	private OrganizationVo pointOfHireOrgVo;
	private String ssn;
	private String ssnVerify;
	private String encryptedSSN;
	private String eci;
	private Date deletedDate;
	private RateAreaEnum rateAreaName;
	private Integer rateYear;
	
	// placeholder for rateYear
	private RateClassVo rateClassVo;
	
	public AdPaymentInfoVo() {
	}

	public static AdPaymentInfoVo getInstance(AdPaymentInfo entity,boolean cascadable) throws Exception {
		AdPaymentInfoVo vo = new AdPaymentInfoVo();
		
		if(null == entity)
			throw new Exception("Unable to create vo from null AdPaymentInfo entity");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setInitialEmp(entity.getInitialEmp());
			vo.setReturnTravel(entity.getReturnTravel());
			vo.setEci(entity.getEci());
			
			if(null != entity.getPointOfHireOrg()){
				vo.setPointOfHireOrgVo(OrganizationVo.getInstance(entity.getPointOfHireOrg(), true));
			}
			
			vo.setRateAreaName(entity.getRateAreaName());

			if(null != entity.getRateAreaName()){
				RateAreaVo ravo = new RateAreaVo();
				String area=entity.getRateAreaName().name();
				if(area.equals("ALASKA")){
					ravo.setId(1L);
				}else if(area.equals("CONUS")){
					ravo.setId(2L);
				}else if(area.equals("HAWAII")){
					ravo.setId(3L);
				}
				ravo.setCode(area);
				vo.setRateAreaVo(ravo);
			}
			
			if(null != entity.getRateClassRate()){
				vo.setRateClassRateVo(RateClassRateVo.getInstance(entity.getRateClassRate(), true));
			}
			
			vo.setEncryptedSsn(entity.getSsn());
			//vo.setSsn(entity.getSsn());
			vo.setRateYear(entity.getRateYear());

			
		}
		
		return vo;
	}

	public static AdPaymentInfo toEntity(AdPaymentInfo entity, AdPaymentInfoVo vo,boolean cascadable,Persistable...persistables) throws Exception {
		if(null == entity){
			entity = new AdPaymentInfoImpl();
		}

		if(!LongUtility.hasValue(entity.getId()))
			entity.setId(vo.getId());

		if(cascadable){
			entity.setInitialEmp(vo.getInitialEmp());
			entity.setReturnTravel(vo.getReturnTravel());
			entity.setEci(vo.getEci());
			entity.setPointOfHire(StringUtility.toUpper(vo.getPointOfHire()));

			if(VoValidator.isValidAbstractVo(vo.getRateClassVo())){
				entity.setRateYear(vo.getRateClassVo().getRateYear());
			}
			
			AssignmentTime atEntity = (AssignmentTime)AbstractVo.getPersistableObject(persistables, AssignmentTimeImpl.class);
			if(null != atEntity)
				entity.setAssignmentTime(atEntity);
			
			if(AbstractVo.hasValue(vo.getPointOfHireOrgVo())){
				entity.setPointOfHireOrg(OrganizationVo.toEntity(null,vo.getPointOfHireOrgVo(), false));
			}else
				entity.setPointOfHireOrg(null);
			
			if(AbstractVo.hasValue(vo.getRateClassRateVo()) ){
				entity.setRateClassRate(RateClassRateVo.toEntity(null,vo.getRateClassRateVo(), false));
			}else
				entity.setRateClassRate(null);
			
			if(null != vo.getRateAreaName()){
				entity.setRateAreaName(vo.getRateAreaName());
			}else{
				if(AbstractVo.hasValue(vo.getRateAreaVo())){
					String area = vo.getRateAreaVo().getCode();
					if(area.equals("ALASKA")){
						entity.setRateAreaName(RateAreaEnum.ALASKA);
					} else if (area.equals("CONUS")){
						entity.setRateAreaName(RateAreaEnum.CONUS);
					} else if (area.equals("HAWAII")){
						entity.setRateAreaName(RateAreaEnum.HAWAII);
					}
				}
			}
			
			entity.setSsn(vo.getEncryptedSsn());
			//entity.setSsn(vo.getSsn());

		}
		
		return entity;
	}

	
	/**
	 * @return the rateAreaVo
	 */
	public RateAreaVo getRateAreaVo() {
		return rateAreaVo;
	}

	/**
	 * @param rateAreaVo the rateAreaVo to set
	 */
	public void setRateAreaVo(RateAreaVo rateAreaVo) {
		this.rateAreaVo = rateAreaVo;
	}

	/**
	 * @return the rateClassRateVo
	 */
	public RateClassRateVo getRateClassRateVo() {
		return rateClassRateVo;
	}

	/**
	 * @param rateClassRateVo the rateClassRateVo to set
	 */
	public void setRateClassRateVo(RateClassRateVo rateClassRateVo) {
		this.rateClassRateVo = rateClassRateVo;
	}

	/**
	 * @return the initialEmp
	 */
	public Boolean getInitialEmp() {
		return initialEmp;
	}

	/**
	 * @param initialEmp the initialEmp to set
	 */
	public void setInitialEmp(Boolean initialEmp) {
		this.initialEmp = initialEmp;
	}

	/**
	 * @return the returnTravel
	 */
	public Boolean getReturnTravel() {
		return returnTravel;
	}

	/**
	 * @param returnTravel the returnTravel to set
	 */
	public void setReturnTravel(Boolean returnTravel) {
		this.returnTravel = returnTravel;
	}

	/**
	 * @return the pointOfHire
	 */
	public String getPointOfHire() {
		return pointOfHire;
	}

	/**
	 * @param pointOfHire the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}

	
	/**
	 * @return the ssn decrypted
	 */
	public void setEncryptedSsn(String encryptedSsn) throws ServiceException {
		
		if(null != encryptedSsn && encryptedSsn != ""){
			try {
				FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
				byte[] bytes = Base64.decode(encryptedSsn);
				this.ssn = new String(enc.decrypt(bytes));	
				this.ssnVerify=new String(enc.decrypt(bytes));
			}
			catch(Exception e){
				throw new ServiceException(e);
			}
		}
		else {
			this.ssn = "";
		}
	}	
	
	/**
	 * @return the ssn encrypted
	 */
	public String getEncryptedSsn() throws ServiceException {
		
		// encrypt the ssn value if applicable
		if(null != this.ssn && this.ssn != ""){
			try {
				FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
				byte[] encryptedBytes = enc.encrypt(this.ssn.getBytes()); //store the encrypted ssn as a byte array, in order to successfully decrypt
				this.encryptedSSN = Base64.encode(encryptedBytes);
			}
			catch(Exception e){
				throw new ServiceException(e);
			}
		}
		return this.encryptedSSN;
	}

	
	
	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
			this.ssn = ssn;
	}
	/**
	 * @return the eci
	 */
	public String getEci() {
		return eci;
	}

	/**
	 * @param ssn the eci to set
	 */
	public void setEci(String eci) {
			this.eci = eci;
	}
	


	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public OrganizationVo getPointOfHireOrgVo() {
		return pointOfHireOrgVo;
	}

	public void setPointOfHireOrgVo(OrganizationVo pointOfHireOrgVo) {
		this.pointOfHireOrgVo = pointOfHireOrgVo;
	}

	public RateAreaEnum getRateAreaName() {
		return rateAreaName;
	}

	public void setRateAreaName(RateAreaEnum rateAreaName) {
		this.rateAreaName = rateAreaName;
	}

	public Integer getRateYear() {
		return rateYear;
	}

	public void setRateYear(Integer rateYear) {
		this.rateYear = rateYear;
	}

	public RateClassVo getRateClassVo() {
		return rateClassVo;
	}

	public void setRateClassVo(RateClassVo rateClassVo) {
		this.rateClassVo = rateClassVo;
	}

	public String getSsnVerify() {
		return ssnVerify;
	}

	public void setSsnVerify(String ssnVerify) {
		this.ssnVerify = ssnVerify;
	}


}
