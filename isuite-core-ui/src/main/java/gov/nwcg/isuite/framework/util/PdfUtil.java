package gov.nwcg.isuite.framework.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

public class PdfUtil {

	public static String concatenatePdfs(Collection<String> pdfFiles, String outputFile) throws Exception {
		
		try{
	        Document document = new Document();
	        PdfCopy copy = new PdfCopy(document, new FileOutputStream(outputFile));
	        document.open();
	        
	        PdfReader reader;			
	        int n;
	        
	        // loop over the documents you want to concatenate
	        for(String s : pdfFiles){
	            reader = new PdfReader(s);
	        	
	            n = reader.getNumberOfPages();
	            for (int page = 0; page < n; ) {
	                copy.addPage(copy.getImportedPage(reader, ++page));
	            }
	            copy.freeReader(reader);
	            reader.close();
	        }
	        
	        // step 5
	        document.close();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return outputFile;
	}
	
	// Static method to determine @ of pages in a pdf file given its path
	public static int numberOfPagesInPdfFile(String pdfFilePath) throws IOException {
		PdfReader reader = new PdfReader(pdfFilePath);
    	int numberOfPages = reader.getNumberOfPages();
    	reader.close();
    	return numberOfPages;
	}
}
