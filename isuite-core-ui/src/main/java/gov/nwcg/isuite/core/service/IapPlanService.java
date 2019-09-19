package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.IapAircraftTaskVo;
import gov.nwcg.isuite.core.vo.IapAircraftVo;
import gov.nwcg.isuite.core.vo.IapAreaLocationCapabilityVo;
import gov.nwcg.isuite.core.vo.IapAttachmentVo;
import gov.nwcg.isuite.core.vo.IapBranchCommSummaryVo;
import gov.nwcg.isuite.core.vo.IapBranchPersonnelVo;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.core.vo.IapForm202Vo;
import gov.nwcg.isuite.core.vo.IapForm203Vo;
import gov.nwcg.isuite.core.vo.IapForm204Vo;
import gov.nwcg.isuite.core.vo.IapForm205Vo;
import gov.nwcg.isuite.core.vo.IapForm206Vo;
import gov.nwcg.isuite.core.vo.IapForm220Vo;
import gov.nwcg.isuite.core.vo.IapFrequencyVo;
import gov.nwcg.isuite.core.vo.IapHospitalVo;
import gov.nwcg.isuite.core.vo.IapMedicalAidVo;
import gov.nwcg.isuite.core.vo.IapPersonnelVo;
import gov.nwcg.isuite.core.vo.IapPlanVo;
import gov.nwcg.isuite.core.vo.IapRemoteCampLocationsVo;
import gov.nwcg.isuite.core.vo.IapPlanPrintOrderVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface IapPlanService extends TransactionService {
	
	public DialogueVo getIapPlan(Long iapPlanId, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * Returns a list of all plans and forms for the incident or incident group id supplied.
	 * 
	 * @param incidentId
	 * @param incidentGroupId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getIapPlanGrid(Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * Save the iap plan record.
	 * 
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIapPlan(IapPlanVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Returns the iap form by id and type.
	 * 
	 * @param id
	 * @param type
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getIapForm(Long id, String formType, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * Lock or Unlock a plan.
	 * 
	 * @param iapPlanId
	 * @param action
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo lockUnlockIapPlan(Long iapPlanId, String action, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param formId
	 * @param type
	 * @param action
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo lockUnlockIapForm(Long formId, String formType, String action, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param iapPlanId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteIapPlan(Long iapPlanId, DialogueVo dialogueVo) throws ServiceException ;
	
	/**
	 * @param formId
	 * @param type
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteIapForm(Long formId, String formType, DialogueVo dialogueVo) throws ServiceException ;
	
	public DialogueVo saveIapForm202(IapForm202Vo vo, DialogueVo dialogueVo) throws ServiceException ;
	public DialogueVo saveIapForm203(IapForm203Vo vo, DialogueVo dialogueVo) throws ServiceException ;
	public DialogueVo saveIapForm204(IapForm204Vo vo, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo saveIapForm205(IapForm205Vo vo, DialogueVo dialogueVo) throws ServiceException ;
	public DialogueVo saveIapForm205Frequency(Long iapForm205Id, IapFrequencyVo frequencyVo,DialogueVo dialogueVo) throws ServiceException; 
	public DialogueVo verifyFrequencies(Long iapPlanId, Long iapForm205Id, IapFrequencyVo frequencyVo,DialogueVo dialogueVo) throws ServiceException; 
	public DialogueVo addIapForm205Frequencies(Long iapForm205Id, Collection<IapFrequencyVo> frequencyVos,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIapForm205Frequency(IapFrequencyVo frequencyVo,DialogueVo dialogueVo) throws ServiceException; 
	public DialogueVo saveIapForm206Hospital(Long iapForm206Id, IapHospitalVo hospitalVo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIapForm206Hospital(IapHospitalVo hospitalVo,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm206Ambulance(Long iapForm206Id, IapMedicalAidVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo saveIapForm206AirAmbulance(Long iapForm206Id, IapMedicalAidVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIapForm206Ambulance(IapMedicalAidVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIapForm206AirAmbulance(IapMedicalAidVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo saveIapForm220Helicopter(Long iapForm220Id, IapAircraftVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIapForm220Helicopter(IapAircraftVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo saveIapForm220Task(Long iapForm220Id, IapAircraftTaskVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIapForm220Task(IapAircraftTaskVo vo,DialogueVo dialogueVo) throws ServiceException ;
	public DialogueVo saveIapForm204OpPersonnel(Long iapForm204Id, IapBranchPersonnelVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIapForm204OpPersonnel(IapBranchPersonnelVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo saveIapForm204BranchComm(Long iapForm204Id, IapBranchCommSummaryVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIapForm204BranchComm(IapBranchCommSummaryVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo saveIapForm204BranchRscAssign(Long iapForm204Id, IapBranchRscAssignVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo addIapForm204RscAssign(Long iapForm204Id, String personNameOrder, Collection<IapBranchRscAssignVo> vos,DialogueVo dialogueVo) throws ServiceException ;	
	public DialogueVo deleteIapForm204BranchRscAssign(IapBranchRscAssignVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo saveIapForm203Position(Long iapForm203Id,IapPersonnelVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIapForm203Position(IapPersonnelVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo addIapForm204BranchComms(Long iapForm204Id, Collection<IapBranchCommSummaryVo> vos,DialogueVo dialogueVo) throws ServiceException ;
	public DialogueVo saveIapForm206AreaLocationCap(Long iapForm206Id, IapAreaLocationCapabilityVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIapForm206AreaLocationCap(IapAreaLocationCapabilityVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo saveIapForm206RemoteCampLocations(Long iapForm206Id, IapRemoteCampLocationsVo vo,DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIapForm206RemoteCampLocations(IapRemoteCampLocationsVo vo,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm206(IapForm206Vo vo, DialogueVo dialogueVo) throws ServiceException ;
	public DialogueVo saveIapForm220(IapForm220Vo vo, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param vo
	 * @param pdfByteArray
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveAttachment(IapAttachmentVo vo, byte[] pdfByteArray, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteAttachment(IapAttachmentVo vo, DialogueVo dialogueVo) throws ServiceException;

	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo validate220Helicopter(IapAircraftVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm203Positions(Collection<IapPersonnelVo> vos,DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo deleteAndResetToNoBranch(Long form203Id, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo resetToBranch(Long form203Id, String type, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo autoFill(Long iapForm204Id, IapBranchRscAssignVo vo,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getRscAssignGrid(Long iapForm204Id, IapBranchRscAssignVo vo,DialogueVo dialogueVo) throws ServiceException;	
	
	public DialogueVo saveIapForm204Position(Long iapForm204Id,IapBranchRscAssignVo vo,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm204Positions(Collection<IapBranchPersonnelVo> vos,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm204ResourceAssignPositions(Collection<IapBranchRscAssignVo> vos,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm204CommunicationPositions(Collection<IapBranchCommSummaryVo> vos,DialogueVo dialogueVo) throws ServiceException;
		
	public DialogueVo saveIapForm220AircraftPositions(Collection<IapAircraftVo> vos,DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo saveIapForm220TaskPositions(Collection<IapAircraftTaskVo> vos,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm205FrequencyPositions(Collection<IapFrequencyVo> vos,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm206AirAmbulancePositions(Collection<IapMedicalAidVo> vos,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm206AmbulancePositions(Collection<IapMedicalAidVo> vos,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm206HospitalPositions(Collection<IapHospitalVo> vos,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm206AlcPositions(Collection<IapAreaLocationCapabilityVo> vos,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIapForm206RclPositions(Collection<IapRemoteCampLocationsVo> vos,DialogueVo dialogueVo) throws ServiceException;
	
}
