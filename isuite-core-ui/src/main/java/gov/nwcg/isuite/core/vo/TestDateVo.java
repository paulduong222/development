package gov.nwcg.isuite.core.vo;

import java.util.Date;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class TestDateVo extends AbstractVo 
{
	private Date gmtDate;
	private Date utcDate;
	
	public TestDateVo(){
		
	}

	public Date getGmtDate() {
		return gmtDate;
	}

	public void setGmtDate(Date gmtDate) {
		this.gmtDate = gmtDate;
	}

	public Date getUtcDate() {
		return utcDate;
	}

	public void setUtcDate(Date utcDate) {
		this.utcDate = utcDate;
	}
	
}
