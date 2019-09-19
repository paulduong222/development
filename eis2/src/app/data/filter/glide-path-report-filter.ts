import { KindVo } from "../kind-vo";
import { ReportSectionCategorySelectVo } from "../report-section-category-select-vo";

export interface GlidePathReportFilter {
  	incidentId: any;
	incidentGroupId: any;
	incidentTag: string;
    incidentNumber: string;
	incidentName: string;

	numberOfDays: any;

	optionAll: boolean;
	optionSections: boolean;
	optionSummaryOnly: boolean;
	sectionCategories: ReportSectionCategorySelectVo[];

	optionIncludeSTComponents: boolean;
	sortByItemCodeDemobDate: boolean;
	sortByItemCodeRequestNumber: boolean;
	sortByDemobDateRequestNumber: boolean;
	sortByRequestNumberDemobDate: boolean;

	sortByDemobDateResourceName: boolean;

	sortByDemobDateRequestNumber2: boolean;
	sortByDemobDateResourceName2: boolean;
	sortByRequestNumberDemobDate2: boolean;
	startDateVo: any;
}
