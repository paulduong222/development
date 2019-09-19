package gov.nwcg.isuite.core.vo.dialogue;

public class NavigateVo {
	private String destination;
	
	public NavigateVo(){
		
	}

	public NavigateVo(String destination) {
		this.destination = destination;
	}
	
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

}
