package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface Recommendation extends Persistable {
	
	public Long getId();

	public void setId(Long id);

	public void setCode(String code);

	public String getCode();

	public void setDescription(String description);
	
	public String getDescription();


}
