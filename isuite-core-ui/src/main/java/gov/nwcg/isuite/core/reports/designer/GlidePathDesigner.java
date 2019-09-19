package gov.nwcg.isuite.core.reports.designer;

import gov.nwcg.isuite.core.reports.data.CustomReportData;
import gov.nwcg.isuite.core.reports.filter.GlidePathReportFilter;
import gov.nwcg.isuite.core.vo.CustomReportColumnVo;
import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.framework.report.IReport;

import java.awt.Color;
import java.util.Collection;

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.LineSpacingEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.type.PositionTypeEnum;
import net.sf.jasperreports.engine.type.SplitTypeEnum;
import net.sf.jasperreports.engine.type.VerticalAlignEnum;

public class GlidePathDesigner {
	
	private static final Color CELL_BG_COLOR_BLACK = new Color(0,0,0);
	private static final Color CELL_BG_COLOR_RED = new Color(255,0,0);
	private static final Color CELL_BG_COLOR_ORANGE = new Color(255,128,0);
	private static final Color CELL_BG_COLOR_YELLOW = new Color(255,255,0);
	private static final Color CELL_BG_COLOR_GREEN = new Color(0,255,0);
	private static final Color CELL_BG_COLOR_GRAY = new Color(179,182,184);
	
	private static final int PAGE_LENGTH_LONG_SIDE = 842;
	private static final int PAGE_LENGTH_SHORT_SIDE = 595;
	
	//private static final int PAGE_MARGIN_TOP = 20;
	//private static final int PAGE_MARGIN_BOTTOM = 20;
	private static final int PAGE_MARGIN_LEFT = 20;
	private static final int PAGE_MARGIN_RIGHT = 20;
	
	public static final int COLUMN_PADDING_INITIAL = 3; // Only used for PDFs; Public property: Used by rule engine
	public static final int COLUMN_PADDING_RIGHT = 3; // Only used for PDFs; Public property: Used by rule engine
	
	private static final int LINE_SPACING_HEIGHT_SINGLE = 10;
	private static final int LINE_SPACING_HEIGHT_ONE_AND_HALF = 15;
	private static final int LINE_SPACING_HEIGHT_DOUBLE = 20;
	
	private static final Color HEADER_BACKGROUND_COLOR = new Color(204, 204, 204);
	
	private static final JRDesignStyle STYLENORMAL = new JRDesignStyle();
	private static final JRDesignStyle STYLEBOLD = new JRDesignStyle();
	private static final JRDesignStyle STYLEITALIC = new JRDesignStyle();
	
	static {
		STYLENORMAL.setName("SansSerif_Normal");
		STYLEBOLD.setName("SansSerif_Bold");
		STYLEITALIC.setName("SansSerif_Italic");
	}
	
	/**
	 * Modifies and returns a JasperDesign object for Custom Reports as per the user inputs for that Custom Report
	 * @param customReport
	 * @param customReportVo
	 * @param jasperDesign
	 * @return
	 * @throws JRException
	 * @throws Exception
	 */
	public static JasperDesign modifyGlidePathReportDesign(IReport glidePathReport, GlidePathReportFilter filter, JasperDesign jasperDesign) throws JRException, Exception{
		// Validate that column information from IReport data matches that in column information from the user/filter.
		//validateColumnInformation(customReport, customReportVo);
		
/*		
		
		// Create Report Fields that will hold the result record values.
		createReportFields(jasperDesign, customReportVo.getCustomReportColumnVos());
				
		// Page Header: Create the column headers
		JRDesignBand pageHeader = createPageHeader(customReport, customReportVo);
		jasperDesign.setPageHeader(pageHeader);
	    
		// Page contents: Create column fields in the detail band
		JRDesignBand detailBand = createResultDetailBand(customReport, customReportVo);
		((JRDesignSection)jasperDesign.getDetailSection()).addBand(detailBand);
		
		// Finalize Design
		finalizeDesign(customReport, customReportVo, jasperDesign);
		
		
		
		*/
		return testReport(glidePathReport, filter, jasperDesign);
		//return jasperDesign;
	}
	
	private static JasperDesign testReport(IReport glidePathReport, GlidePathReportFilter filter, JasperDesign jasperDesign) throws JRException, Exception{
		
		JRElement subReportElement = jasperDesign.getDetailSection().getBands()[0].getElementByKey("subreport0");
		JRDesignSubreport sub0 = (JRDesignSubreport) subReportElement;
		
//		cell4.setBackcolor(CELL_BG_COLOR_RED);
		
		return jasperDesign;
	}
	
	
	
	private static boolean validateColumnInformation(IReport customReport, CustomReportVo customReportVo) throws Exception {
		Collection<CustomReportData> customReportDataList = (Collection<CustomReportData>)customReport.getReportData();
		
		if(customReportVo == null 
				|| customReportVo.getCustomReportColumnVos() == null 
				|| customReportDataList == null
				|| customReportDataList.size() == 0) {
			throw new Exception("Missing column information."); // Modify this to use error object in service class
		}
		
		return true;
	}
	
	private static JRDesignField createColumnField(String fieldName, CustomReportColumnVo vo) {
		return createColumnField(fieldName, java.lang.Object.class);
	}
	
	private static void createReportFields(JasperDesign jasperDesign, Collection<CustomReportColumnVo> colVos) throws JRException{
		if(colVos.size() < 1 || colVos.size() > CustomReportData.MAX_NUMBER_OF_COLUMNS) {
			throw new JRException(
					"Number of 'column' fields on the report must be between 1 and " 
					+ CustomReportData.MAX_NUMBER_OF_COLUMNS);
		}
		
		int i=0;
		for(CustomReportColumnVo vo : colVos){
			jasperDesign.addField(createColumnField("column" +(i+1), vo));
			i++;
		}
		
	}

	private static JRDesignTextField createColumnHeaderTextField(String headerText) {
		JRDesignTextField textField = new JRDesignTextField();
		JRDesignExpression expression = new JRDesignExpression("new java.lang.String(\"" + headerText + "\")");
	    textField.setExpression(expression);
//	    textField.setX(0); 				// Caller should reset
//		textField.setY(0); 				// Caller should reset
//	    textField.setWidth(40);			// Caller should reset
//	    textField.setHeight(15);		// Caller should reset
	    textField.setForecolor(Color.BLACK);
	    textField.setBackcolor(HEADER_BACKGROUND_COLOR);
	    textField.setMode(ModeEnum.OPAQUE);
	    textField.setHorizontalAlignment(HorizontalAlignEnum.LEFT);
	    textField.setVerticalAlignment(VerticalAlignEnum.TOP);
	    textField.setStretchWithOverflow(true);
	    textField.setStyle(STYLEBOLD);
	    textField.setPositionType(PositionTypeEnum.FIX_RELATIVE_TO_TOP);
    	textField.setBlankWhenNull(true);
    	return textField;
	}
	
	private static JRDesignTextField createColumnTextField(int columnNumber, CustomReportColumnVo vo) {
		JRDesignTextField textField = new JRDesignTextField();
		JRDesignExpression expression = new JRDesignExpression("$F{column"+ columnNumber + "}");
	    textField.setExpression(expression);
	    
//		textField.setX(0); 				// Caller should reset
//		textField.setY(0); 				// Caller should reset
//	    textField.setWidth(40);			// Caller should reset
//	    textField.setHeight(15);		// Caller should reset
	    textField.setForecolor(Color.BLACK);
	    //textField.setBackcolor(Color.WHITE);
	    textField.setMode(ModeEnum.TRANSPARENT);
	    textField.setHorizontalAlignment(HorizontalAlignEnum.LEFT);
	    textField.setVerticalAlignment(VerticalAlignEnum.TOP);
	    textField.setStretchWithOverflow(true);
	    textField.setStyle(STYLENORMAL);
	    textField.setPositionType(PositionTypeEnum.FIX_RELATIVE_TO_TOP);
    	textField.setBlankWhenNull(true);
	    return textField;
	}
	
	private static JRDesignField createColumnField(String fieldName, Class fieldClass) {
		JRDesignField field = new JRDesignField();
	    field.setName(fieldName);
	    field.setValueClass(fieldClass);
	    return field;
	}
	
	public static int getMaximumAllowedContentWidth(CustomReportVo customReportVo){
		if(OrientationEnum.LANDSCAPE.equals(customReportVo.getCanonicalOrientation())){
	    	return (PAGE_LENGTH_LONG_SIDE - PAGE_MARGIN_LEFT - PAGE_MARGIN_RIGHT);
	    } else {
	    	return (PAGE_LENGTH_SHORT_SIDE - PAGE_MARGIN_LEFT - PAGE_MARGIN_RIGHT);
	    }
	}
	
	private static int getHeightForCanonicalLineSpacing(LineSpacingEnum lineSpacingEnum){
		 if((LineSpacingEnum.ONE_AND_HALF).equals(lineSpacingEnum)){
		    	return LINE_SPACING_HEIGHT_ONE_AND_HALF;
		    } else if((LineSpacingEnum.DOUBLE).equals(lineSpacingEnum)){
		    	return LINE_SPACING_HEIGHT_DOUBLE;
		    } else {
		    	return LINE_SPACING_HEIGHT_SINGLE;
		    }
	}
	
	private static int getResultTableHeight(IReport customReport, CustomReportVo customReportVo) {
		int rowHeight = getHeightForCanonicalLineSpacing(customReportVo.getCanonicalLineSpacingEnum());
		int totalResultTableHeight = rowHeight * customReport.getReportData().size();
		return totalResultTableHeight;
	}
	
	private static JRDesignFrame createFrameForHeaderRow(IReport customReport, CustomReportVo customReportVo){
		JRDesignFrame frame = new JRDesignFrame();
	    frame.setPositionType(PositionTypeEnum.FIX_RELATIVE_TO_TOP);
	    frame.setX(0);
	    frame.setY(0);
	    frame.setForecolor(Color.BLACK);
	    frame.setBackcolor(HEADER_BACKGROUND_COLOR);
	    frame.setMode(ModeEnum.OPAQUE);
	    frame.setPositionType(PositionTypeEnum.FIX_RELATIVE_TO_TOP);
	    
	    if(customReport.isExportToExcel()){
	    	frame.setWidth(customReportVo.getTotalWidthOfColumns());
	    } else {
		    frame.setWidth(getMaximumAllowedContentWidth(customReportVo));
	    }
	    
	    frame.setHeight(getHeightForCanonicalLineSpacing(customReportVo.getCanonicalLineSpacingEnum()));

	    // Following is required since header height was disproportionately large  
	    // as compared with other (content) rows set with same height.
	    frame.setHeight((int)Math.round(frame.getHeight() * 0.5)); 	 

	    return frame;
	}
	
	private static JRDesignBand createPageHeader(IReport customReport, CustomReportVo customReportVo){
		JRDesignBand headerBand = new JRDesignBand();
	    
		JRDesignFrame rowFrame = createFrameForHeaderRow(customReport, customReportVo);	  
		
	    Collection<CustomReportColumnVo> columns = customReportVo.getCustomReportColumnVos();
	    
	    int x_coord = 0;
	    
	    for(CustomReportColumnVo column : columns) {
	    	JRDesignTextField headerTextField = createColumnHeaderTextField(column.getHeader());
	    	headerTextField.setWidth(column.getWidth());
	    	headerTextField.setX(x_coord);
	    	if(!customReport.isExportToExcel()) { 
	    		x_coord += column.getWidth() + COLUMN_PADDING_RIGHT;
	    	} else {
	    		x_coord += column.getWidth();
	    	}
	    		rowFrame.addElement(headerTextField);
	    }
	    headerBand.setHeight(rowFrame.getHeight());
	    headerBand.addElement(rowFrame);
		return headerBand;
	}
	
	private static JRDesignBand createResultDetailBand(IReport customReport, CustomReportVo customReportVo) {
		int rowHeight = getHeightForCanonicalLineSpacing(customReportVo.getCanonicalLineSpacingEnum());
		
		JRDesignBand detailBand = new JRDesignBand();
	    detailBand.setHeight(rowHeight);
	    detailBand.setSplitType(SplitTypeEnum.IMMEDIATE);
	    
	    int x_coord = 0;
	    int columnNumber = 0;
	    
	    for(CustomReportColumnVo vo : customReportVo.getCustomReportColumnVos()){
	    	
	    	JRDesignTextField textField = createColumnTextField(columnNumber+1, vo);
	    	int thisColWidth = vo.getWidth();
	    	textField.setWidth(thisColWidth);
	    	textField.setHeight(rowHeight);
	    	textField.setX(x_coord);
	    	
	    	if(!customReport.isExportToExcel()) { 
	    		x_coord += thisColWidth + COLUMN_PADDING_RIGHT;
	    	} else {
	    		x_coord += thisColWidth;
	    	}
	    	detailBand.addElement(textField);
	    	columnNumber++;
	    }
	    
	    return detailBand;
	}
	
	private static void finalizeDesign(IReport customReport, CustomReportVo customReportVo, JasperDesign jasperDesign) throws JRException{
		// Main report level properties
		OrientationEnum orientationEnum = customReportVo.getCanonicalOrientation();
		jasperDesign.setOrientation(orientationEnum);
		
		// Title properties
		JRElement frameTitle = jasperDesign.getTitle().getElementByKey("frameTitle");
		//jasperDesign.getDetailSection().getBands()[0].getElementByKey("subreport").getElementGroup().g
		
		
		JRDesignTextField txtFieldHeaderTitle = (JRDesignTextField)jasperDesign.getTitle().getElementByKey("txtFieldHeaderTitle");
		JRElement lineUnderTitle = jasperDesign.getTitle().getElementByKey("lineUnderTitle");
		JRDesignTextField txtFieldHeaderSubTitle = (JRDesignTextField)jasperDesign.getTitle().getElementByKey("txtFieldHeaderSubTitle");
		
		int contentWidth = 0;
		if(customReport.isExportToExcel()){ // REPORT TYPE: EXCEL
			contentWidth = customReportVo.getTotalWidthOfColumnsIncludingPadding();
			
			jasperDesign.setPageWidth(contentWidth);
			jasperDesign.setPageHeight(getResultTableHeight(customReport, customReportVo) 
					+ jasperDesign.getPageHeader().getHeight() 
					+ 10000);// Buffer to ensure that rows are not split in b/w pages
			
			jasperDesign.setSummary(null);
			jasperDesign.setPageFooter(null);
			
			jasperDesign.setColumnWidth(contentWidth);
			
			jasperDesign.setTopMargin(0);
			jasperDesign.setBottomMargin(0);
			jasperDesign.setLeftMargin(0);
			jasperDesign.setRightMargin(0);
			
			jasperDesign.setTitle(null);
			setExcelExportSpecificProperties(jasperDesign);
		} else { // REPORT TYPE: PDF
			contentWidth = getMaximumAllowedContentWidth(customReportVo);
			
			if(orientationEnum.equals(OrientationEnum.LANDSCAPE)){
				jasperDesign.setPageWidth(PAGE_LENGTH_LONG_SIDE);
				jasperDesign.setPageHeight(PAGE_LENGTH_SHORT_SIDE);
			} else {
				jasperDesign.setPageWidth(PAGE_LENGTH_SHORT_SIDE);
				jasperDesign.setPageHeight(PAGE_LENGTH_LONG_SIDE);
			}
			jasperDesign.setColumnWidth(contentWidth);
			
			frameTitle.setWidth(contentWidth);
			txtFieldHeaderTitle.setWidth(contentWidth);
			lineUnderTitle.setWidth(contentWidth);
			txtFieldHeaderSubTitle.setWidth(contentWidth);
			
			jasperDesign.setProperty("net.sf.jasperreports.print.keep.full.text", "true");
		}
	}
	
	private static void setExcelExportSpecificProperties(JasperDesign jasperDesign) {
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.create.custom.palette", "false");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.one.page.per.sheet", "false");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.remove.empty.space.between.rows", "true");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.remove.empty.space.between.columns", "true");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.white.page.background", "false");
		
		
		
		
		
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.detect.cell.type", "true");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.size.fix.enabled", "true");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.ignore.graphics", "false");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.collapse.row.span", "true");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.ignore.cell.border", "false");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.ignore.cell.background", "false");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.max.rows.per.sheet", "0");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.use.timezone", "false");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.wrap.text", "true");
		jasperDesign.setProperty("net.sf.jasperreports.print.keep.full.text", "true");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.exclude.origin.keep.first.band.1", "pageHeader");
		jasperDesign.setProperty("net.sf.jasperreports.export.xls.exclude.origin.band.1", "pageHeader");
	}
}
