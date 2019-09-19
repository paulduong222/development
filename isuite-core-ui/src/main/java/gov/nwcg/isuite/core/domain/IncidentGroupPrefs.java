package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.IncidentPrefsSectionNameEnum;

public interface IncidentGroupPrefs extends Persistable {

   public Long getIncidentGroupId();
   
   public void setIncidentGroupId(Long incidentGroupId);

   public IncidentGroup getIncidentGroup();
   
   public void setIncidentGroup(IncidentGroup incidentGroup);

   public IncidentPrefsSectionNameEnum getSectionName();
   
   public void setSectionName(IncidentPrefsSectionNameEnum sectionName);

   public String getFieldLabel();
   
   public void setFieldLabel(String fieldLabel);

   public Integer getPosition();
   
   public void setPosition(Integer position);

   public Boolean isSelected();

   public void setSelected(Boolean selected);

}
