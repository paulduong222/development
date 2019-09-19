package gov.nwcg.isuite.core.vo;

public class RateTypeVo {
	
	private String code;
	
	public RateTypeVo() {
		super();
	}
	
	public RateTypeVo(String code) {
		super();
		setCode(code);
	}
	
	/**
	    * Returns the Code.
	    * 
	    * @return
	    * 		the code to return
	    */
	   public String getCode() {
		   return code;
	   }

	   /**
	    * Sets the code.
	    * 
	    * @param code
	    * 			the code to set
	    */
	   public void setCode(String code) {
		   this.code=code;
	   }

}
