package gov.nwcg.isuite.framework.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtil {

	/**
	 * Writes a byte array output stream to file.
	 * 
	 * @param baos
	 * 			the ByteArrayOutputStream to write
	 * @param destFile
	 * 			the destination file to write the output stream to
	 * @throws Exception
	 */
	public static void writeFile(ByteArrayOutputStream baos, String destFile) throws Exception {
		
		try{
			java.io.File file = new java.io.File(destFile);

			baos.writeTo(new FileOutputStream(file,true));
			baos.flush();
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}finally{
			try{
				if(null != baos)
					baos.close();
			}catch(Exception smother){
				
			}
		}
	}

	public static void writeFile(byte[] bytes, String destFile) throws Exception {
		try{
			FileOutputStream fos = new FileOutputStream(destFile);
			fos.write(bytes);
			fos.close();
		}catch(Exception e){
			throw e;
		}
	}

	public static void writeFile2(byte[] bytes, String destFile) throws Exception {
		try{
		    InputStream stream = new ByteArrayInputStream(bytes);
		    OutputStream os = new FileOutputStream(destFile);
		    byte buf[] = new byte[512000];
		    int len;
		    while((len=stream.read(buf))>0){
			    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    	baos.write(buf,0,len);
				baos.writeTo(os);
				baos.close();
		    }
		    os.close();
		    stream.close();
			
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * Returns whether or the fileToCheck is an actual file.
	 * 
	 * @param fileToCheck
	 * 			the file name to check
	 * @return
	 * 		whether or not the file exists
	 * @throws Exception
	 */
	public static boolean fileExists(String fileToCheck) throws Exception {
		try{
			java.io.File file = new java.io.File(fileToCheck);
			return file.exists();
		}catch(Exception e){
			throw e;
		}
	}

	public static boolean isFolder(String pathToCheck) throws Exception {
		try{
			java.io.File file = new java.io.File(pathToCheck);
			
			if(file.exists() && file.isDirectory())
				return true;
			else
				return false;
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * Deletes the file.
	 * 
	 * @param fileToDelete
	 * 			the file to delete
	 * @throws Exception
	 */
	public static void deleteFile(String fileToDelete) throws Exception {
		try{
			java.io.File file = new java.io.File(fileToDelete);
			if(file.exists())
				file.delete();
		}catch(Exception e){
			throw e;
		}
	}

	public static Collection<String> getContentsByLine(String strFile) throws Exception {
		BufferedReader input = null;
		Collection<String> contents = new ArrayList<String>();

		try {
			File file = new File(strFile);
			if(file.exists()){
				input =  new BufferedReader(new FileReader(file));
				String line="";

				while (( line = input.readLine()) != null){
					contents.add(line);
				}
			}
		}catch(Exception e){
		}finally{
			if(null != input)
				input.close();
		}

		return contents;
	}

	/**
	 * @param origFile
	 * @param newFile
	 * @throws Exception
	 */
	public static void renameFile(String origFile, String newFile) throws Exception {
		File file = new File(origFile);
		file.renameTo(new File(newFile));
	}

	public static void main(String[] args){
		String strFile = "C:\\Isuitesayre-GoodCognosFile.txt";
		try{
			Collection<String> contents = FileUtil.getContentsByLine(strFile);

			System.out.println(contents.size());
			String line = contents.iterator().next();
			System.out.println(line);

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public static StringBuffer getFileContents(String strFile) throws Exception {
		BufferedReader input = null;
		StringBuffer buffer = new StringBuffer();

		try {
			File file = new File(strFile);
			if(file.exists()) {
				input = new BufferedReader(new FileReader(file));
				String line=""; 

				while (( line = input.readLine()) != null) {
					buffer.append(line);
				}
			}
		}catch(Exception e){

		}finally {
			if(null != input)
				input.close();
		}

		return buffer;
	}
	
	public static byte[] getFileContentsBytes(String strFile) throws Exception {
		FileInputStream fis = null;
		byte[] byteArray = null;
		
		try {
			fis = new FileInputStream(strFile);
			
			int i = fis.available();
			
			byteArray = new byte[i];
			
			fis.read(byteArray);
			
		}catch(Exception e) {
		}
		return byteArray;
	}

	/**
	 * Create a new temporary directory. Use rDelete(file) to clean this
	 * directory up since it isn't deleted automatically
	 * 
	 * @return the new directory file handle
	 * @throws IOException
	 */
	public static File iswCreateTmpPath() throws IOException {
		final File sysTmpDir = new File(System.getProperty("java.io.tmpdir"));
		File newTmpDir;
		final int maxAttempts = 100; // If in a race how many tries to create a
		// unique name
		int attempts = 0;
		do {
			attempts++;
			if (attempts > maxAttempts) {
				throw new IOException(
						"Failed to create a unique temporary directory after "
						+ maxAttempts + " tries.");
			}
			String dirName = "isw_" + UUID.randomUUID().toString();
			newTmpDir = new File(sysTmpDir, dirName);
		} while (newTmpDir.exists());

		if (newTmpDir.mkdirs()) {
			return newTmpDir;
		} else {
			throw new IOException("Failed to create temp dir named "
					+ newTmpDir.getAbsolutePath());
		}
	}

	/**
	 * Recursively delete file or directory. Handles symbolic links.
	 * 
	 * @param file
	 *            the file or directory handle to delete
	 * 
	 * */

	public static void rDelete(File f) throws IOException {
		if (f.isDirectory()) {
			for (File ff : f.listFiles())
				rDelete(ff);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}	

	public static void makeDir(String dir) throws Exception {
		File f=new File(dir);
		if(null != f && !f.exists())
			f.mkdirs();
	}

	public static Boolean isDir(String dir) throws Exception {
		Boolean exists=false;

		try{
			java.io.File file = new java.io.File(dir);
			exists=file.exists();
		}catch(Exception e){
		}

		return exists;
	}

	public static void copyFile(String source, String target) throws Exception {
		InputStream inStream = null;
		OutputStream outStream = null;

		try{

			inStream = new FileInputStream(new File(source));
			outStream = new FileOutputStream(new File(target));

			byte[] buffer = new byte[1024];

			int length;
			
			while ((length = inStream.read(buffer)) > 0){
				outStream.write(buffer, 0, length);
			}

		}catch(IOException e){
			throw e;
		}finally{
			try{
				if(null != inStream)
					inStream.close();
				if(null != outStream)
					outStream.close();
			}catch(Exception smother){}
		}
	}
	
	public static Collection<String> getDirFilenames(String dirPath) {
		Collection<String> files = new ArrayList<String>();
		
		try{
			File f=new File(dirPath);
			files=new ArrayList<String>(Arrays.asList(f.list()));
		}catch(Exception e){
			
		}
		
		return files;
	}
	
	public static void zipFile(String sourceFile, String targetFile) {
		try{
			File s = new File(sourceFile);
			
			FileOutputStream fos = new FileOutputStream(targetFile);
            ZipOutputStream zos = new ZipOutputStream(fos);		
             
            ZipEntry ze = new ZipEntry(s.getName());
            zos.putNextEntry(ze);
            
            //read the file and write to ZipOutputStream
            FileInputStream fis = new FileInputStream(s);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
             
            //Close the zip entry to write to zip file
            zos.closeEntry();
            
            //Close resources
            zos.close();
            fis.close();
            fos.close();             
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public static void unzipFile(String sourceFile, String targetFile) throws Exception {
		try{
			File s = new File(sourceFile);
			byte[] buffer = new byte[1024];
			
			FileInputStream fis=new FileInputStream(s);
			ZipInputStream zis=new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while(ze != null){
				String filenam=ze.getName();
				System.out.println(filenam);
				File t = new File(targetFile);
				FileOutputStream fos = new FileOutputStream(t);
				int len;
				while((len = zis.read(buffer))>0){
					fos.write(buffer,0,len);
				}
				fos.close();
				zis.closeEntry();
				ze=null;
			}
			fis.close();
			zis.close();
		}catch(Exception e){
			throw e;
		}
	}

	public static void createFile(String file) throws Exception {
		File f = new File(file);
		if(f.exists())
			f.delete();
		f.createNewFile();
	}

	public static BufferedWriter getFileAppender(String file) throws Exception {
		FileWriter fwriter = new FileWriter(file,true);
		BufferedWriter writer = new BufferedWriter(fwriter);
		return writer;
	}
	
}
