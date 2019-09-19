package gov.nwcg.isuite.core.reports.filter;

import java.util.Collection;

public class HomeUnitContactLabelsReportFilter {
	
	private Collection<Long> homeUnitContactIds;
	private Boolean exportToExcel;
	
	public HomeUnitContactLabelsReportFilter(){
	}

	/**
	 * @param homeUnitContactIds the homeUnitContactIds to set
	 */
	public void setHomeUnitContactIds(Collection<Long> homeUnitContactIds) {
		this.homeUnitContactIds = homeUnitContactIds;
	}

	/**
	 * @return the homeUnitContactIds
	 */
	public Collection<Long> getHomeUnitContactIds() {
		return homeUnitContactIds;
	}

	/**
	 * @param exportToExcel the exportToExcel to set
	 */
	public void setExportToExcel(Boolean exportToExcel) {
		this.exportToExcel = exportToExcel;
	}

	/**
	 * @return the exportToExcel
	 */
	public Boolean getExportToExcel() {
		return exportToExcel;
	}

}
