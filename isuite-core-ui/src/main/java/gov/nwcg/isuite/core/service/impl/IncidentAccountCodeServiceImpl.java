package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.AccountCode;
import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.impl.AccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.domain.impl.RegionCodeImpl;
import gov.nwcg.isuite.core.filter.IncidentAccountCodeFilter;
import gov.nwcg.isuite.core.persistence.AccountCodeDao;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.persistence.RegionCodeDao;
import gov.nwcg.isuite.core.service.IncidentAccountCodeService;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.AgencyTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Service layer area used for accessing accountcode functionality.
 * 
 * @author mpoll
 */
public class IncidentAccountCodeServiceImpl extends BaseService implements IncidentAccountCodeService {

   private IncidentAccountCodeDao incidentAccountCodeDao;
   private AccountCodeDao accountCodeDao;
   private AgencyDao agencyDao;
   private OrganizationDao organizationDao;

   public IncidentAccountCodeServiceImpl(){
	   
   }
   
   public void initialization(){
	   incidentAccountCodeDao = (IncidentAccountCodeDao)super.context.getBean("incidentAccountCodeDao");
	   agencyDao = (AgencyDao)super.context.getBean("agencyDao");
	   accountCodeDao = (AccountCodeDao)super.context.getBean("accountCodeDao");
	   organizationDao = (OrganizationDao)super.context.getBean("organizationDao");
	   
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#save(gov.nwcg.isuite.domain.incident.IncidentAccountCodeVo)
    */
   public void save(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException {
      if ( incidentAccountCodeVo == null ) {
         throw new ServiceException("incidentAccountCodeVo object cannot be null");
      }
      IncidentAccountCode incidentAccountCode = this.voToDo(incidentAccountCodeVo);
      if ( incidentAccountCode == null ) {
         throw new ServiceException("incidentAccountCode object cannot be null");
      }
      
      this.save(incidentAccountCode);
   }
   
   /** 
    * This will be used to "Warn" users when they're adding or editing and have 
    * picked an account code that is already associated with other incidents.
    * 
    * This may also be used to determine if a mass update is even possible.
    * 
    * @return
    */
   private Collection<IncidentAccountCodeVo> getExistingAccountCodeIncidentAssociations(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException{
	   /*
      Collection<IncidentAccountCodeVo> incidentAccountCodeVos = new ArrayList<IncidentAccountCodeVo>();
         if (incidentAccountCodeVo.getAccountCodeId() == null) {
            incidentAccountCodeVos = getExistingAccountCodeIncidentAssociations(incidentAccountCodeVo.getAccountFireCode());
         } else {
            incidentAccountCodeVos = getExistingAccountCodeIncidentAssociations(incidentAccountCodeVo.getAccountCodeId());
         }
      return incidentAccountCodeVos;
      */
	   return null;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#getIncidentsInvolvedInPotentialMassUpdate(gov.nwcg.isuite.domain.incident.IncidentAccountCodeVo)
    */
   public String getIncidentsInvolvedInPotentialMassUpdate(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException{
/*      StringBuffer existingIncidentsThatUseThisAccountCode = new StringBuffer();
      existingIncidentsThatUseThisAccountCode.append(incidentAccountCodeVo.getIncidentName());
      for (IncidentAccountCodeVo iacv : getExistingAccountCodeIncidentAssociations(incidentAccountCodeVo)) {
         if (!(iacv.getIncidentId().equals(incidentAccountCodeVo.getIncidentId()))) {
               existingIncidentsThatUseThisAccountCode.append(", ");
            existingIncidentsThatUseThisAccountCode.append(iacv.getIncidentName());
         } 
      }
      return existingIncidentsThatUseThisAccountCode.toString();
*/   
	   return null;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#shouldWarnUserAboutExistingRelationships(gov.nwcg.isuite.domain.incident.IncidentAccountCodeVo)
    */
   public boolean shouldWarnUserAboutExistingRelationships(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException {
      // determine if the account code already exists and ask the user if they'd like to continue.
      // 1. I inadvertently have added a relationship that already exists for the same incident. (no dialog)
      // 2. I have added an account that exists in other relationships with incidents. (dialog)
      // 3. Could have done both. (no dialog)
      // 4. Could have done neither. (no dialog)
      Collection<IncidentAccountCodeVo> iacVos = getExistingAccountCodeIncidentAssociations(incidentAccountCodeVo);
	  //If there is only 1 IAC in the database, there is no need for a dialog.
      if(iacVos.size() == 1) { 
		  return false;   
	  }
      /*
      for (IncidentAccountCodeVo iacv : iacVos) {
         if (!(iacv.getIncidentId().equals(incidentAccountCodeVo.getIncidentId()))) {
            return true;
         } 
      }
      */
      return false;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#getExistingAccountCodeIncidentAssociations(java.lang.Long)
    */
   public Collection<IncidentAccountCodeVo> getExistingAccountCodeIncidentAssociations(Long accountCodeId) throws ServiceException {
      Collection<IncidentAccountCodeVo> iacVos = new ArrayList<IncidentAccountCodeVo>();
      Collection<IncidentAccountCode> incidentAccountCodes;
      try {
         incidentAccountCodes = incidentAccountCodeDao.getIncidentAccountCodesByAccountCodeId(accountCodeId);
         /*
         for(IncidentAccountCode iac : incidentAccountCodes) {
        	 IncidentAccountCodeVo incidentAccountCodeVo = new IncidentAccountCodeVo(iac);
        	 iacVos.add(incidentAccountCodeVo);
         }
         */
      } catch (PersistenceException pe) {
         throw new ServiceException("error.accountCodeAssociation");
      }
      return iacVos;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#getExistingAccountCodeIncidentAssociations(java.lang.String)
    */
   public Collection<IncidentAccountCodeVo> getExistingAccountCodeIncidentAssociations(String accountFireCode) throws ServiceException {
      AccountCode accountCode;
      Long accountCodeId = 0L;
      try {
         accountCode = accountCodeDao.getAccountCodeByAccountCode(accountFireCode);
      }
      catch ( PersistenceException pe ) {
         throw new ServiceException(pe);
      }
      if (accountCode != null) {
        accountCodeId = accountCode.getId();
      }
      Collection<IncidentAccountCodeVo> incidentACVos = getExistingAccountCodeIncidentAssociations(accountCodeId);
//      return getExistingAccountCodeIncidentAssociations(accountCodeId);
      return incidentACVos;
   }

   // NOTE: This method is not to be called directly by other Service classes
   // but should go through the
   // save(IncidentAccountCodeVo incidentAccountCodeVo) method to make sure
   // validation is done.
   public void save(IncidentAccountCode persistable) throws ServiceException {
      try {
         incidentAccountCodeDao.save(persistable);
      }
      catch ( PersistenceException e ) {
         throw new ServiceException(e);
      } 
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#delete(java.lang.Long)
    */
   public void delete(Long incidentAccountCodeId) throws ServiceException {
      if ( (incidentAccountCodeId == null) || (incidentAccountCodeId.equals(0L)) ) {
         throw new ServiceException("incidentAccountCodeId cannot be null");
      }

      try {
         IncidentAccountCode iac = incidentAccountCodeDao.getById(incidentAccountCodeId, IncidentAccountCode.class);
         if ( (iac != null) && (iac.getId().compareTo(0L) > 0) ) {
            this.delete(iac);
         }
      }
      catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.PersistableService#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(IncidentAccountCode persistable) throws ServiceException {
	   throw new ServiceException("error");
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#getAll(gov.nwcg.isuite.domain.incident.IncidentAccountCodeFilter)
    */
   @Deprecated
   public Collection<IncidentAccountCodeVo> getAll(IncidentAccountCodeFilter filter) throws ServiceException {
	   throw new UnsupportedOperationException();
   }
  
   @Override
   public Collection<IncidentAccountCodeVo> getGrid(IncidentAccountCodeFilter filter) throws ServiceException {
	   Collection<IncidentAccountCode> iacs = null;//iacGridVos = null;
	   try {
		   iacs = incidentAccountCodeDao.getGrid(null, filter);//iacGridVos = incidentAccountCodeDao.getGrid(null, filter);
	   } catch (PersistenceException e) {
		   throw new ServiceException(e);
	   }
//	   return iacGridVos;
	   try {
		   return IncidentAccountCodeVo.getInstances(iacs, true);
	   } catch (Exception e) {
		   throw new ServiceException(e);
	   }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#voToDo(gov.nwcg.isuite.domain.incident.IncidentAccountCodeVo)
    */
   public IncidentAccountCode voToDo(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException {
      // TODO Validate if we have done an e-ISuite Enterprise sync and make sure
      // Enterprise info overwrites the data (may not need to be done here, but
      // somewhere else)
      // Also, make attributes 'read-only' if matched.
      // TODO If Agency = FEMA, (factor in for additional requirements TBD).
      // TODO Original Invoice generation checks need to be added at a later
      // date.
      // TODO Time/Cost Posting checks need to be added at a later date.

      IncidentAccountCode iac;
      Incident incident = null;
      Agency agency = null;
      AccountCode accountCode = null;
      Organization regionUnit;
     
      /*
      if (incidentAccountCodeVo.getIncidentName() == null
               || incidentAccountCodeVo.getIncidentName().trim().length() == 0) {
         throw new ServiceException("Incident Name and-or Incident Id can't be blank or null");
      }

      if ( (incidentAccountCodeVo.getAgencyCode() == null)
               || incidentAccountCodeVo.getAgencyCode().trim().length() == 0) {
         LOG.debug("Agency and-or Agency Id can't be blank or null");
         throw new ServiceException("Unexpected error.");
      }

      if ( (incidentAccountCodeVo.getAccountFireCode() == null)
               || incidentAccountCodeVo.getAccountFireCode().trim().length() == 0) {
         throw new ServiceException("Accounting Fire Code can't be blank");
      }

      if ( incidentAccountCodeVo.getDefaultValue() == null ) {
         throw new ServiceException("Default value can't be null");
      }
      
      try {
         incident = incidentDao.getByIncidentName(incidentAccountCodeVo.getIncidentName());
         agency = agencyDao.getByAgencyCode(incidentAccountCodeVo.getAgencyCode());
         accountCode = accountCodeDao.getAccountCodeByAccountCode(incidentAccountCodeVo.getAccountFireCode());
         regionUnit = organizationDao.getByOrganizationCode(incidentAccountCodeVo.getUnitCode());
      } catch (PersistenceException pe) {
         throw new ServiceException(pe);
      }
      if ( incidentAccountCodeVo.getAccountCodeId() == null ) {
         // we came from the ADD page.
         iac = new IncidentAccountCodeImpl();
         // Set the accrual override code information.
         iac.setOverrideAccountCodeId(incidentAccountCodeVo.getAccrualOverrideCodeId());
         if (incidentAccountCodeVo.getAccrualOverrideCodeId() != null && incidentAccountCodeVo.getAccrualOverrideCodeId() != 0L) {
            try {
               iac.setOverrideAccountCode(accountCodeDao.getById(incidentAccountCodeVo.getAccrualOverrideCodeId(), AccountCodeImpl.class));
            }
            catch ( PersistenceException pe ) {
               throw new ServiceException(pe);
            }
         }
         iac.setDefaultFlag(incidentAccountCodeVo.getDefaultValue());
         if ( accountCode == null ) {
            // 1. we are creating a new account code and associating it with an
            // incident.
            AccountCode theNewAccountCode = new AccountCodeImpl();
            if(agency != null) {
            	theNewAccountCode.setAgency(agency);
            	theNewAccountCode.setAgencyId(agency.getId());
            } else {
            	throw new ServiceException("error.nonExistentAgency");
            }
            theNewAccountCode.setAccountCode(incidentAccountCodeVo.getAccountFireCode());
            if(regionUnit != null) {
            	theNewAccountCode.setRegionUnitId(regionUnit.getId());
            	theNewAccountCode.setRegionUnit(regionUnit);
            }
            if(incident != null) {
            	iac.setIncident(incident);
            	iac.setIncidentId(incident.getId());
            } else {
            	throw new ServiceException("error.nonExistentIncident");
            }
            iac.setAccountCode(theNewAccountCode);
            iac.setOverrideAccountCodeId(incidentAccountCodeVo.getAccrualOverrideCodeId());
         }
         else {
            // 2. we are adding an existing account code to an incident.
            accountCode.setLastModifiedBy(incidentAccountCodeVo.getUserLoginName());
            accountCode.setLastModifiedDate(new Date());
            //Agency must be set here or the accountCode will have the wrong agency code.
            //see above --> accountCode = accountCodeDao.getAccountCodeByAccountCode(incidentAccountCodeVo.getAccountFireCode());
            accountCode.setAgency(agency);
            iac.setAccountCode(accountCode);
            iac.setIncident(incident);
         }
      }
      else {
         // we came from the EDIT page.
         try {
            if(null != accountCode) {
               // it's possible, however unlikely that they might find a weakness.  We are vulnerable.
               // They could be adding a record that already exists.
               IncidentAccountCode existingIAC = incidentAccountCodeDao.getIncidentAccountCodeByAccountCodeIdAndIncidentId(
                        accountCode.getId(), incidentAccountCodeVo.getIncidentId());
               if ((null != existingIAC) && (!existingIAC.getAccountCodeId().equals(incidentAccountCodeVo.getAccountCodeId()))) {
                  existingIAC = incidentAccountCodeDao.getById(existingIAC.getId(), IncidentAccountCodeImpl.class);
                  return existingIAC;
               }
            }
            iac = incidentAccountCodeDao.getById(incidentAccountCodeVo.getId(), 
                     IncidentAccountCodeImpl.class);
         }
         catch (PersistenceException pe) {
            throw new ServiceException("Unexpected Error.", pe);
         }
         // Set the accrual override code information.
         iac.setOverrideAccountCodeId(incidentAccountCodeVo.getAccrualOverrideCodeId());
         if (incidentAccountCodeVo.getAccrualOverrideCodeId() != null && incidentAccountCodeVo.getAccrualOverrideCodeId() != 0L) {
            try {
               iac.setOverrideAccountCode(accountCodeDao.getById(incidentAccountCodeVo.getAccrualOverrideCodeId(), AccountCodeImpl.class));
            }
            catch ( PersistenceException pe ) {
               throw new ServiceException(pe);
            }
         }
         
         iac.setDefaultFlag(incidentAccountCodeVo.getDefaultValue());
         if ( accountCode == null ) {
            // 1. 1 incident : 1 accounting Code
            // In this case, it's just a simple edit of the accountCodeImpl record
            // 2. Multiple incidents : 1 accounting Code
            // In this case, a dialog is required to get more info from the user.
            try {
               //If the account code id in the IAC is null, we are adding a new AC (the accountCode here will be null)
               accountCode = accountCodeDao.getById(incidentAccountCodeVo.getAccountCodeId(), AccountCodeImpl.class);
            }
            catch ( PersistenceException pe ) {
               throw new ServiceException("failed to get the existing account code with this id: "
                        + incidentAccountCodeVo.getAccountCodeId() + ".  Shouldn't happen.", pe);
            }
            if(incidentAccountCodeVo.getIsMassUpdate() || !shouldWarnUserAboutExistingRelationships(incidentAccountCodeVo)) {
               accountCode.setAccountCode(incidentAccountCodeVo.getAccountFireCode());
               accountCode.setAgencyId(incidentAccountCodeVo.getAgencyId());
               accountCode.setLastModifiedBy(incidentAccountCodeVo.getUserLoginName());
               accountCode.setLastModifiedDate(new Date());
               accountCode.setRegionUnitId(incidentAccountCodeVo.getUnitId());
            }
         } else {
            //TODO:  will any logic ever exist in this else statement?
            // In this case where it does already exist, the user
            // is not allowed to change any other data, so no sets are called on the account code.
         }
         iac.setAccountCode(accountCode);
         iac.setIncident(incident);
      }
      // Add Audit Info to all inserted/updated records.
      super.setAuditInfo(iac);
      return iac;
	*/
      return null;
   }
   
   /**
    * This method will fetch the association with the default AccountCode
    * as it pertains to the incidentId given.
    */
   IncidentAccountCode getDefaultIncidentAccountCode(Long incidentId) throws ServiceException{
      IncidentAccountCode defaultIncidentAccountCode;
      try {
         defaultIncidentAccountCode = incidentAccountCodeDao.getDefaultIncidentAccountCodeByIncidentId(incidentId);
      }
      catch (PersistenceException pe) {
         throw new ServiceException(pe);
      }
      return defaultIncidentAccountCode;
   }

   /**
    * Ensure that the field lengths are consistent with requirements
    * @param iac - A single {@link IncidentAccountCode}
    * @throws ServiceException
    */
   private void checkTheLengthsOfTheFieldTypesThatWerePicked(IncidentAccountCode iac) throws ServiceException {
      
      AccountCode ac = iac.getAccountCode();
      // If Incident = 'Wildland Fire Event Type (Fire - Wildfire (WF)',
      // Agency Code must be a FED type OR FEMA type OR Agency Type CANNOT be
      // a FEDERAL type
      if ( iac.getIncident().getEventType().getEventTypeCode().equalsIgnoreCase("WF") ) {
         if ( ( !ac.getAgency().getAgencyCode().equalsIgnoreCase("FED"))
                  && ( !ac.getAgency().getAgencyCode().equalsIgnoreCase("FEMA"))
                  && (ac.getAgency().getAgencyType().equals(AgencyTypeEnum.FEDERAL)) ) {
            throw new ServiceException("error.incorrectAgencySelection");
         }
      }
      else {
         // If Incident != 'Wildland Fire Event Type (Fire - Wildfire (WF)',
         // Agency must not be a FED type
         if ( ac.getAgency().getAgencyCode().equalsIgnoreCase("FED") ) {
            throw new ServiceException("error.incorrectAgencyType");
         }
      }

      // If Agency = "FED", user should enter a Fire Code (verify length is
      // exactly 4 characters)
      if ( ac.getAgency().getAgencyCode().equalsIgnoreCase("FED") ) {
         if ( ac.getAccountCode().length() != 4 ) {
            //TODO:  What is the error code for this message?  I can't find it in the Use Cases.
        	 throw new ServiceException("error.accountCodeLengthForFEDAgency");
         }
      }
      else if ( ac.getAgency().getAgencyCode().equalsIgnoreCase("FS") ) {
         // If Agency = "FS", verify length is <= 6 characters
         if ( ac.getAccountCode().length() > 6 ) {
        	 //TODO:  What is the error code for this message?  I can't find it in the Use Cases.
        	 throw new ServiceException("error.accountCodeLengthForFSAgency");
         }
      }
      else {
         // Otherwise, verify length is <= 50 characters
         if ( ac.getAccountCode().length() > 50 ) {
        	//TODO:  What is the error code for this message?  I can't find it in the Use Cases.
        	 throw new ServiceException("error.accountCodeLengthGreaterThan50");
         }
      }
   }
   
   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.service.TransliterableService#vosToDos(java.util.Collection)
    */
   public Collection<IncidentAccountCode> vosToDos(Collection<IncidentAccountCodeVo> theVos) throws ServiceException {
      Collection<IncidentAccountCode> doList = new ArrayList<IncidentAccountCode>();
      for ( IncidentAccountCodeVo incidentAccountCodeVo : theVos ) {
         doList.add(voToDo(incidentAccountCodeVo));
      }
      return doList;
   }
   
   /**
    * Save a single {@link AccountCode} to the DB
    * @param ac - A single {@link AccountCode}
    * @throws ServiceException
    */
   private void saveAccountCode(AccountCode ac) throws ServiceException{
      try {
         // set the agency, because one MUST exist
         if (ac.getAgency() == null || (ac.getAgencyId() != ac.getAgency().getId())) {
            if (ac.getAgencyId() != null && ac.getAgencyId() != 0L) {
               ac.setAgency(agencyDao.getById(ac.getAgencyId(), AgencyImpl.class));
            } else {
               throw new ServiceException("Agency id was null, and agency cannot be null.");
            }
         }
         
         RegionCodeDao rcDao = (RegionCodeDao)context.getBean("regionCodeDao");
         // set the fs region unit if the user entered one.
         if (ac.getRegionUnit() == null || (ac.getRegionUnit().getId() != ac.getRegionUnitId())) {
            if (ac.getRegionUnitId() != null && ac.getRegionUnitId() != 0L) {
               ac.setRegionUnit(rcDao.getById(ac.getRegionUnitId(), RegionCodeImpl.class));
            } 
         }
            accountCodeDao.save(ac);
         }
         catch ( PersistenceException pe ) {
            throw new ServiceException(pe);
         }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#addIncidentAccountCode(gov.nwcg.isuite.domain.incident.IncidentAccountCodeVo)
    */
   public void addIncidentAccountCode(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException {
	   IncidentAccountCode iac = null;
	   /*
	   try {
		   iac = incidentAccountCodeDao.getIACByAccountCodeAndIncidentId(
				   incidentAccountCodeVo.getAccountFireCode(), incidentAccountCodeVo.getIncidentId());
	   } catch (PersistenceException e) {
		   throw new ServiceException(e);
	   }
				   */
	   
	   //Check for duplicate Account Code add attempt
	   if(iac != null) {
		   throw new ServiceException("error.duplicateAccountCode");
	   }

	   IncidentAccountCode incidentAccountCodeToSave = voToDo(incidentAccountCodeVo);

	   /*
	   try {
		   // Last minute checks for consistency
		   checkTheLengthsOfTheFieldTypesThatWerePicked(incidentAccountCodeToSave);

		   //Save the AC so that it is non-transient.  If we don't save the AC here, when we try to save the
		   //IAC below we will get an error indicating that a persistent object cannot reference a transient object.
		   if (incidentAccountCodeToSave.getAccountCodeId() == null) {
			   saveAccountCode(incidentAccountCodeToSave.getAccountCode());
		   }
		   // They've selected the default option.  Let's make sure we de-select the current
		   // one that is the default (if one exists).
		   IncidentAccountCode defaultIAC = getDefaultIncidentAccountCode(incidentAccountCodeVo.getIncidentId());
		   if(defaultIAC == null) {
			   //This is the first Accounting code being assigned to the Incident, 
			   // hence there is no default incident account code.  So this new record will
			   // be the default by default.
			   incidentAccountCodeToSave.setDefaultFlag(true);
		   } else {
			   if(incidentAccountCodeToSave.getDefaultFlag()) {
				   defaultIAC.setDefaultFlag(false); 
				   try {
					   incidentAccountCodeDao.save(defaultIAC);
				   }
				   catch ( PersistenceException pe ) {
					   throw new ServiceException("Failed to deselect the old default IAC", pe);
				   }
			   }
		   }
		   incidentAccountCodeDao.save(incidentAccountCodeToSave);
	   }
	   catch ( PersistenceException pe ) {
		   throw new ServiceException(pe);
	   }
		   */
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#getAccountFireCodeById(java.lang.Long)
    */
   public String getAccountFireCodeById(Long accountCodeId) throws ServiceException {
      String accountFireCode = null;
      AccountCode accountCode = null;
      try {
         accountCode = accountCodeDao.getById(accountCodeId, AccountCodeImpl.class);
         if(null != accountCode) {
            accountFireCode = accountCode.getAccountCode();
         } 
      }
      catch ( PersistenceException pe ) {
         throw new ServiceException(pe);
      }
      return accountFireCode;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#updateIncidentAccountCode(gov.nwcg.isuite.domain.incident.IncidentAccountCodeVo)
    */
   public void updateIncidentAccountCode(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException {
	   /*
      IncidentAccountCode incidentAccountCodeToUpdate;
      // Note that we have to get the default IAC before we tweak any "Persistables", otherwise hibernate thinks that there
      // are multiple defaults (potentially)
      IncidentAccountCode defaultIAC = getDefaultIncidentAccountCode(incidentAccountCodeVo.getIncidentId());
      //      IncidentAccountCode incidentAccountCodeToUpdate = voToDo(incidentAccountCodeVo);
      if (!shouldWarnUserAboutExistingRelationships(incidentAccountCodeVo)) {
         incidentAccountCodeToUpdate = voToDo(incidentAccountCodeVo);
         // 1. 1 incident : 1 accounting Code
         // In this case, it's just a simple edit of the accountCodeImpl record
         
            AccountCode accountCode = incidentAccountCodeToUpdate.getAccountCode();
//            accountCode.setAgency(agency);
            accountCode.setAgencyId(incidentAccountCodeVo.getAgencyId());
            accountCode.setLastModifiedBy(incidentAccountCodeVo.getUserLoginName());
            accountCode.setLastModifiedDate(new Date());
            accountCode.setRegionUnitId(incidentAccountCodeVo.getUnitId());
            saveAccountCode(accountCode);
//            accountCode.setRegionUnit(regionUnit);
            incidentAccountCodeToUpdate.setDefaultFlag(incidentAccountCodeVo.getDefaultValue());
            if (incidentAccountCodeVo.getAccrualOverrideCodeId() != null && incidentAccountCodeVo.getAccrualOverrideCodeId() != 0L) {
               try {
                  incidentAccountCodeToUpdate.setOverrideAccountCode(accountCodeDao.getById(incidentAccountCodeVo.getAccrualOverrideCodeId(), AccountCodeImpl.class));
               }
               catch ( PersistenceException pe ) {
                  throw new ServiceException(pe);
               }
            }
            incidentAccountCodeToUpdate.setAccountCode(accountCode);
         
      } else {
         incidentAccountCodeToUpdate = voToDo(incidentAccountCodeVo);
         // 2. Multiple incidents : 1 accounting Code
         // In this case, a dialog is required to get more info from the user.
         if (incidentAccountCodeVo.getIsMassUpdate()) {
            //mass update ie. simple accountCodeEdit. (unless it already exists, then it's not so simple)
            if(incidentAccountCodeVo.getAccountCodeId().equals(incidentAccountCodeToUpdate.getAccountCode().getId())) {
               saveAccountCode(incidentAccountCodeToUpdate.getAccountCode());
            } else {
               try {
                  // update other associated incidents to point to the right(existing) Account Code.
                  for (IncidentAccountCode iac : incidentAccountCodeDao.getIncidentAccountCodesByAccountCodeId(incidentAccountCodeVo.getAccountCodeId())) {
                     // however, in the case where you're updating an account code to one that already
                     // exists for the same incident, the mass update will try and map two IAC records such that
                     // they have the same accountCode and Incident, which is of course a constraint violation.
                     // so we have to avoid that one.  you'll note our avoidance of it via the if statement.
                     // note also that this *could* potentially not be what the user wanted.  Oh well.
                     if (!iac.getId().equals(incidentAccountCodeVo.getId())) {
                        iac.setAccountCode(incidentAccountCodeToUpdate.getAccountCode());
                        incidentAccountCodeDao.save(iac);
                     }
                  }
               } catch (PersistenceException pe) {
                  throw new ServiceException(pe);
               }
               //TODO:  Do we want to delete an AC with no incident attachments?
            }
            
         } else {
            //they've chosen no to a mass update dialog.
            //single new record to add
            //The following if statement will check to see if the account code was changed to a non existing account code in 
            // the database, and if so, then it will create a brand new account code record.
            if(incidentAccountCodeVo.getAccountCodeId().equals(incidentAccountCodeToUpdate.getAccountCode().getId())) {
               //-------------- Note there is a special case, where all they've done is updated superfluous data, where account
               // code updates are not required.
               if (!incidentAccountCodeVo.getAccountFireCode().equals(incidentAccountCodeToUpdate.getAccountCode().getAccountCode())) {
                  AccountCode theNewAccountCode = new AccountCodeImpl();
                  theNewAccountCode.setAccountCode(incidentAccountCodeVo.getAccountFireCode());
                  theNewAccountCode.setAgencyId(incidentAccountCodeVo.getAgencyId());
                  theNewAccountCode.setCreatedBy(incidentAccountCodeVo.getUserLoginName());
                  theNewAccountCode.setCreatedDate(new Date());
                  theNewAccountCode.setRegionUnitId(incidentAccountCodeVo.getUnitId());
                  //save the transient object theNewAccountCode before setting it in the iACToUpdate
                  this.saveAccountCode(theNewAccountCode);
                  incidentAccountCodeToUpdate.setAccountCode(theNewAccountCode);
               } else {
                  incidentAccountCodeToUpdate.getAccountCode().setAgencyId(incidentAccountCodeVo.getAgencyId());
                  incidentAccountCodeToUpdate.getAccountCode().setRegionUnitId(incidentAccountCodeVo.getUnitId());
                  this.saveAccountCode(incidentAccountCodeToUpdate.getAccountCode());
               }
            } 
         }
      }
      
      if(defaultIAC == null) {
         throw new ServiceException("If incident_id is unique in the IAC table, the default flag for the corresponding IAC record should not be false.");
      }
      
      // If this record is being 'Updated' (Edit Mode) and it is set as a
      // default, the user must NOT be able to change it to a non-default state.
      // realistically this should be impossible assuming good UI design/checks.
      if (incidentAccountCodeToUpdate.getDefaultFlag() == false) {
         if (defaultIAC.getId().equals(incidentAccountCodeToUpdate.getId())) {
            // we've decided to silently fix the improbable instance.
            incidentAccountCodeToUpdate.setDefaultFlag(true);
         }
      } else {
         // They've selected the default option.  Let's make sure we de-select the current
         // one that is the default.
         if (!defaultIAC.getId().equals(incidentAccountCodeToUpdate.getId())) {
            defaultIAC.setDefaultFlag(false); 
            try {
               incidentAccountCodeDao.save(defaultIAC);
            }
            catch ( PersistenceException pe ) {
               throw new ServiceException("Failed to deselect the old default IAC", pe);
            }
         }
      }
      if(!incidentAccountCodeVo.getIsRegionUnitEnabled()) {
         incidentAccountCodeToUpdate.getAccountCode().setRegionUnit(null);
         incidentAccountCodeToUpdate.getAccountCode().setRegionUnitId(null);
      } 
      // Last minute checks for consistency
      checkTheLengthsOfTheFieldTypesThatWerePicked(incidentAccountCodeToUpdate);
      try {
         incidentAccountCodeDao.save(incidentAccountCodeToUpdate);
      }
      catch ( PersistenceException pe ) {
         throw new ServiceException(pe);
      }
      */
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#getEventTypeIdByIncidentId(java.lang.Long)
    */
   public Long getEventTypeIdByIncidentId(Long incidentId) throws ServiceException {
      try {
         return incidentAccountCodeDao.getEventTypeIdByIncidentId(incidentId);
      } catch (PersistenceException pe) {
         throw new ServiceException(pe);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.incident.IncidentAccountCodeService#getIacVoById(java.lang.Long)
    */
   public IncidentAccountCodeVo getIacVoById(Long id) throws ServiceException {
	   try {
		   return incidentAccountCodeDao.getIacVoById(id);
	   } catch (PersistenceException e) {
		   throw new ServiceException(e);
	   }
   }
}
