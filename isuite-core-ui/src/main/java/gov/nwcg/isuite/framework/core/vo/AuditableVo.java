package gov.nwcg.isuite.framework.core.vo;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class AuditableVo {
   
	private String createdBy;
	private Date createdDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;
	
   public AuditableVo() {}
   
   public AuditableVo(Persistable entity) {
      super(); 
      if (null != entity) {
         this.createdBy = entity.getCreatedBy();
         this.createdDate = entity.getCreatedDate();
         this.lastModifiedBy = entity.getLastModifiedBy();
         this.lastModifiedDate = entity.getLastModifiedDate();
      }
   }
   
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	   
}
