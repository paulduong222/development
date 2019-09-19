package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.domain.impl.ReportImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

/**
 * 
 * @author aroundy
 *
 */
public class ReportVo extends AbstractVo implements PersistableVo {

	private Long id;
	private Date dateGenerated;
	private Date dateRequested;
	private String errorDesc;
	private String fileName;
	private String reportName;
	private String resultCode;
	private Long userId;
	private UserVo userVo;
	
	public ReportVo() { 
		super(); 
	}

	/* 
	 * converters report <--> reportVo
	 */
	
	/**
     * Returns a ReportVo instance from a Report entity.
     * 
     * @param entity
     * 			the source Report entity
     * @param cascadable
     * 			flag indicating whether the vo instance should created as a cascadable vo
     * @return ReportVo
     * @throws Exception
     */
	public static ReportVo getInstance(Report entity,boolean cascadable) 
			throws Exception {
		ReportVo vo = new ReportVo();
		
		if(entity == null)
    		throw new Exception("Unable to create ReportVo from null Report entity.");
    	
    	vo.setId(entity.getId());

    	/*
    	 * Only populate fields outside of the entity Id if needed
    	 */
    	if(cascadable){
    		vo.setDateGenerated(entity.getDateGenerated());
    		vo.setDateRequested(entity.getDateRequested());
    		vo.setErrorDesc(entity.getErrorDesc());
    		vo.setFileName(entity.getFileName());
    		vo.setReportName(entity.getReportName());
    		vo.setResultCode(entity.getResultCode());
    		vo.setUserId(entity.getUserId());
    		if(entity.getUserId() != null) {
    			vo.setUserVo(
    					UserVo.getInstance(entity.getUser(), true));
    		}
    	}
    	return vo;
	}
	
	/**
	 * Return a collection of ReportVos
	 * 
	 * @param entities
	 * 			the source Report entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as cascadable vos
	 * @return
	 * 			Collection of ReportVos
	 * @throws Exception
	 */
	public static List<ReportVo> getInstances(Collection<Report> entities, boolean cascadable) 
			throws Exception {
		List<ReportVo> vos = new ArrayList<ReportVo>();
    	
    	for(Report r : entities){
    		vos.add(ReportVo.getInstance(r, cascadable));
    	}
    	return vos;
	}
	
	/**
	 * Creates and returns a Report entity from a report vo.
	 * 
	 * @param entity 
	 * 		a Report entity
	 * @param sourceVo
	 * 		the source ReportVo
	 * @param cascadable
	 * 		flag indicating whether the entity instance should created as a cascadable entity
     * @return
	 * 		the Report entity
	 * @throws Exception
	 */
	public static Report toEntity(Report entity,ReportVo sourceVo,boolean cascadable) 
			throws Exception {
		if(entity == null)
    		entity = new ReportImpl();
    	
    	entity.setId(sourceVo.getId());
    	
    	if(cascadable){
    		entity.setDateGenerated(sourceVo.getDateGenerated());
    		entity.setDateRequested(sourceVo.getDateRequested());
    		entity.setErrorDesc(sourceVo.getErrorDesc());
    		entity.setFileName(sourceVo.getFileName());
    		entity.setReportName(sourceVo.getReportName());
    		entity.setResultCode(sourceVo.getResultCode());
    		entity.setUserId(sourceVo.getUserId());
    		if(sourceVo.getUserVo() != null)
    			if (sourceVo.getUserVo().getId() > 0) {
    				entity.setUser(UserVo.toEntity(null, sourceVo.getUserVo(), false));
    			} else {
    				entity.setUser(null);
    			}
    		else{
    			if (sourceVo.getUserId() != null && sourceVo.getUserId() > 0) {
    				User user = new UserImpl();
        			user.setId(sourceVo.getUserId());
        			entity.setUser(user);
    			}
    		}
    	}
    	return entity;
	}
	
	/**
     * Creates and returns a collection of Report entities from a collection of reportVos.
     * 
     * @param sourceVos
     * 			the source collection of reportVos 
     * @param cascadable
     * 			flag indicating whether the entity instances should created as a cascadable entities
    * @return
     * 		collection of report entities
     * @throws Exception
     */
	public static Collection<Report> toEntityList(Collection<ReportVo> sourceVos, boolean cascadable) 
			throws Exception {
    	List<Report> entityList = new ArrayList<Report>();
    	
    	for(ReportVo sourceVo : sourceVos){
    		entityList.add(ReportVo.toEntity(null,sourceVo, cascadable));
    	}
    	return entityList;
    }
	
	/* 
	 * getters and setters
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateGenerated() {
		return dateGenerated;
	}

	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}

	public Date getDateRequested() {
		return dateRequested;
	}

	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

}
