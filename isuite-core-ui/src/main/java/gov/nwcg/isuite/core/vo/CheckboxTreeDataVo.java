package gov.nwcg.isuite.core.vo;

import java.util.Collection;

public abstract class CheckboxTreeDataVo{
	
	private String itemLabel;
	private final Integer itemType;
	private String itemValue;
	private Boolean selected = false;
	private Collection<CheckboxTreeDataVo> children;
	
	public CheckboxTreeDataVo(Integer itemType) {
		this.itemType = itemType;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemLabel == null) ? 0 : itemLabel.hashCode());
		result = prime * result
				+ ((itemType == null) ? 0 : itemType.hashCode());
		result = prime * result
				+ ((itemValue == null) ? 0 : itemValue.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CheckboxTreeDataVo))
			return false;
		CheckboxTreeDataVo other = (CheckboxTreeDataVo) obj;
		if (itemType == null) {
			if (other.itemType != null)
				return false;
		} else if (!itemType.equals(other.itemType))
			return false;
		if (itemLabel == null) {
			if (other.itemLabel != null)
				return false;
		} else if (!itemLabel.equals(other.itemLabel))
			return false;
		if (itemValue == null) {
			if (other.itemValue != null)
				return false;
		} else if (!itemValue.equals(other.itemValue))
			return false;
		return true;
	}
	
//	public String toString() {
//		StringBuffer str = new StringBuffer();
//		String newline = "\n";
//		String tab = "\t";
//		
//		str.append(this.getItemLabel());
//		str.append(newline);
//		if(this.getChildren()!=null) {
//			for(CheckboxTreeDataVo child: this.getChildren()) {
//				str.append(tab);
//				str.append(child.toString());
//				
//			}
//		}
//		return str.toString();
//	}

	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Collection<CheckboxTreeDataVo> getChildren() {
		return children;
	}

	public void setChildren(Collection<CheckboxTreeDataVo> children) {
		this.children = children;
	}

	public Integer getItemType() {
		return itemType;
	}
}
