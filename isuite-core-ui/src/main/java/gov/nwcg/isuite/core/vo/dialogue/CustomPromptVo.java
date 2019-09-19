package gov.nwcg.isuite.core.vo.dialogue;

import java.util.Collection;

public class CustomPromptVo extends PromptVo {

	private Collection<? extends Object> selectionItems=null;
	
	private Collection<? extends Object> selectedItems=null;
	
	public CustomPromptVo(){
		
	}

	public CustomPromptVo(String cpname, String cpTitleProperty, String cpMessageProperty, Collection<? extends Object> records){
		super();
		super.promptName=cpname;
		super.titleProperty = cpTitleProperty;
		super.messageProperty = cpMessageProperty;
		this.selectionItems=records;
	}
	
	/**
	 * @return the selectionItems
	 */
	public Collection<? extends Object> getSelectionItems() {
		return selectionItems;
	}

	/**
	 * @param selectionItems the selectionItems to set
	 */
	public void setSelectionItems(Collection<? extends Object> selectionItems) {
		this.selectionItems = selectionItems;
	}

	/**
	 * @return the selectedRecords
	 */
	public Collection<? extends Object> getSelectedItems() {
		return selectedItems;
	}

	/**
	 * @param selectedItems the selectedRecords to set
	 */
	public void setSelectedItems(Collection<? extends Object> selectedItems) {
		this.selectedItems = selectedItems;
	}
	
}
