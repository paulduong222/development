package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

public class ReportAgencyFaxSelectVo {

	private String label;
	private Collection<ReportSelectVo> children;
	
	public ReportAgencyFaxSelectVo() {}

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Collection<ReportSelectVo> getChildren() {
    if(children == null )
      children = new ArrayList<ReportSelectVo>();
    return children;
  }

  public void setChildren(Collection<ReportSelectVo> children) {
    this.children = children;
  }



	
  
}

