package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;

public class IncidentGroupConflictVo extends AbstractVo {
	private Collection<String> textLines = new ArrayList<String>();
	private int conflictCount=0;
	
	public IncidentGroupConflictVo(){
		
	}

	/**
	 * @return the textLines
	 */
	public Collection<String> getTextLines() {
		return textLines;
	}

	/**
	 * @param textLines the textLines to set
	 */
	public void setTextLines(Collection<String> textLines) {
		this.textLines = textLines;
	}

	/**
	 * @return the conflictCount
	 */
	public int getConflictCount() {
		return conflictCount;
	}

	/**
	 * @param conflictCount the conflictCount to set
	 */
	public void setConflictCount(int conflictCount) {
		this.conflictCount = conflictCount;
	}
}
