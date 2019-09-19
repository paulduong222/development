package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IncidentGroupGridVo extends AbstractVo implements Comparable<IncidentGroupGridVo> {
	private String groupName;
	private String transferableIdentity;
	private Long workAreaId;
	private Boolean deletable;
	private Collection<IncidentGroupGridVo> children = new ArrayList<IncidentGroupGridVo>();
	private Collection<IncidentGroupUserVo> igUserVos = new ArrayList<IncidentGroupUserVo>();
	
	public IncidentGroupGridVo(){
		
	}

	@SuppressWarnings("unchecked")
   public static IncidentGroupGridVo getInstance(IncidentGroup entity, boolean cascadable) throws Exception {
	   IncidentGroupGridVo vo = new IncidentGroupGridVo();

	   if(null == entity)
	      throw new Exception("Unable to create IncidentGroupGridVo from null IncidentGroup entity.");

	   vo.setId(entity.getId());

	   /*
	    * Only populate fields outside of the entity Id if needed
	    */
	   if(cascadable){
	      vo.setGroupName(entity.getGroupName());
	      vo.setCreatedDate(entity.getCreatedDate());
	      Collection<IncidentGroupGridVo> incidents = new ArrayList<IncidentGroupGridVo>();
	      for(Incident incident : entity.getIncidents()) {
	         //Only add restricted incidents to the child list.
	         if(incident.getRestricted() == true) {
	            IncidentGroupGridVo iggvo = new IncidentGroupGridVo();
	            //The Incident Group Id must be set here.  Otherwise, the IncidentGroup.getById() call will fail.
	            iggvo.setId(entity.getId());
	            iggvo.setGroupName("Incident - " + incident.getIncidentName());
	            incidents.add(iggvo);
	         }
	      }
	      Collections.sort((List)incidents);
	      vo.setChildren(incidents);
	      vo.setIgUserVos(IncidentGroupUserVo.getInstances(entity.getIncidentGroupUsers(), true));
	   }
	   //TODO:  Set criteria for a deletable IncidentGroup. -dbudge
	   /*if(true) {
	      vo.setDeletable(true);//Set to true for now.  Still need to determine deletable criteria. -dbudge
	   } else {
	      vo.setDeletable(false);
	   }*/
	   
	   vo.setDeletable(true);//Set to true for now.  Still need to determine deletable criteria. -dbudge
	   
	   return vo;
	}
	
	public static Collection<IncidentGroupGridVo> getInstances(Collection<IncidentGroup> entities, boolean cascadable) throws Exception {
	   Collection<IncidentGroupGridVo> vos = new ArrayList<IncidentGroupGridVo>();

	   for(IncidentGroup entity : entities){
	      vos.add(IncidentGroupGridVo.getInstance(entity, cascadable));
	   }

	   return vos;
	}
	
	/**
	 * Returns the groupName.
	 *
	 * @return 
	 *		the groupName to return
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * Sets the groupName.
	 *
	 * @param groupName 
	 *			the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * Returns the workAreaId.
	 *
	 * @return 
	 *		the workAreaId to return
	 */
	public Long getWorkAreaId() {
		return workAreaId;
	}

	/**
	 * Sets the workAreaId.
	 *
	 * @param workAreaId 
	 *			the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
	}

   /**
    * @return the children
    */
   public Collection<IncidentGroupGridVo> getChildren() {
      return children;
   }

   /**
    * @param children the children to set
    */
   public void setChildren(Collection<IncidentGroupGridVo> children) {
      this.children = children;
   }

   /**
    * @return the igUserVos
    */
   public Collection<IncidentGroupUserVo> getIgUserVos() {
      return igUserVos;
   }

   /**
    * @param igUserVos the igUserVos to set
    */
   public void setIgUserVos(Collection<IncidentGroupUserVo> igUserVos) {
      this.igUserVos = igUserVos;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(IncidentGroupGridVo o) {
      return this.groupName.compareTo(o.groupName);
   }

   /**
    * @return the deletable
    */
   public Boolean getDeletable() {
      return deletable;
   }

   /**
    * @param deletable the deletable to set
    */
   public void setDeletable(Boolean deletable) {
      this.deletable = deletable;
   }

/**
 * @return the transferableIdentity
 */
public String getTransferableIdentity() {
	return transferableIdentity;
}

/**
 * @param transferableIdentity the transferableIdentity to set
 */
public void setTransferableIdentity(String transferableIdentity) {
	this.transferableIdentity = transferableIdentity;
}
}
