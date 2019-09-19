package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.reports.data.Tnsp225ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp2ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp3ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp4ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp5ReportData;
import gov.nwcg.isuite.core.reports.data.TnspEvalRecordReportData;
import gov.nwcg.isuite.core.reports.data.TnspHUAvery5160ReportData;
import gov.nwcg.isuite.core.reports.data.TnspIncidentTraineeReportData;
import gov.nwcg.isuite.core.reports.filter.IncidentTrainingSummaryReportFilter;
import gov.nwcg.isuite.core.reports.filter.TrainingAssignmentsListReportFilter;
import gov.nwcg.isuite.core.vo.FuelTypeVo;
import gov.nwcg.isuite.core.vo.ResourceTrainingVo;
import gov.nwcg.isuite.core.vo.TrainingSpecialistVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface ResourceTrainingDao extends TransactionSupport, CrudDao<ResourceTraining> {
	
	public Collection<ResourceTrainingVo> getResourceTrainings(Long incidentResourceId) throws PersistenceException;
	
	public Collection<FuelTypeVo> getSelectedFuelTypes(Long resourceTrainingId) throws PersistenceException;
	
	public Collection<FuelTypeVo> getAvailableFuelTypes(Long resourceTrainingId) throws PersistenceException;
	
	public String getTraineeTotal(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public String getPriorityTrainees(Long incidentId, Long incidentGroupId) throws PersistenceException;

	
	public Collection<TnspIncidentTraineeReportData> getTraineeDataFormReportData(Long rtId, Long trainerId, Long tnspId) throws PersistenceException;

	public Collection<TnspEvalRecordReportData> getTnspEvalRecordReportData(Long rtId, Long rttId) throws PersistenceException;
	
	public Collection<Tnsp5ReportData> getTnspHomeUnitLetterReportData(Long rtId, Long tnspId) throws PersistenceException;
	
	public Collection<Tnsp4ReportData> getExitInterviewReportData(Long rtId,Long trainerId, Long tnspId) throws PersistenceException;

	public Collection<Tnsp225ReportData> getPerformanceEvalReportData(Long rtId, Long rttId) throws PersistenceException;

	public Collection<Tnsp3ReportData> getTrainingSummaryReportData(IncidentTrainingSummaryReportFilter filter) throws PersistenceException;
	
	public Collection<TrainingSpecialistVo> getTrainingSpecialistList(Long incidentId, Long groupId, Long rtId) throws PersistenceException;

	public String getEarliestStartDate(Long incidentId, Long groupId) throws PersistenceException;

	public Collection<Tnsp2ReportData> getTrainingAssnListReportData(TrainingAssignmentsListReportFilter filter) throws PersistenceException;

	public Collection<TnspHUAvery5160ReportData> getHomeUnitContactLabelData(Collection<Long> huIds) throws PersistenceException;
}
