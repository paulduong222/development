package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

public class ReportSectionCategorySelectVo {
	private String label;
	private String value;
	private String state;
	private Collection<ReportSectionCategorySelectVo> children = new ArrayList<ReportSectionCategorySelectVo>();
	
	public ReportSectionCategorySelectVo() {

	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the children
	 */
	public Collection<ReportSectionCategorySelectVo> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Collection<ReportSectionCategorySelectVo> children) {
		this.children = children;
	}

}

