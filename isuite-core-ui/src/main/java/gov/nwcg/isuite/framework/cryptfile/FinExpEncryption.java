package gov.nwcg.isuite.framework.cryptfile;

import gov.nwcg.isuite.framework.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FinExpEncryption {

	static void encryptFinExp(ISWFileEncryption encFexp,
			String clearTextFilPath, String encrFilPath) throws Exception {
		try {
			encFexp.tmpDir = FileUtil.iswCreateTmpPath();
			encFexp.aesKey = encFexp.genKey(null);
			encFexp.saveAESKey(encFexp.aesKey, encFexp.tmpDir,
					ISWFileEncryption.ISWPRIVKEY);
			encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, clearTextFilPath);
			encFexp.iswPackage(encFexp.tmpDir, encrFilPath);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (encFexp.tmpDir.exists()) {
					FileUtil.rDelete(encFexp.tmpDir);
				}
			} catch (Exception fe) {
				// smother
			}
		}
	}

	public static void decryptFinExp(ISWFileEncryption encFexp, String encFilPath,
			String outPath) throws Exception {
		
		int index = encFilPath.lastIndexOf(File.separator);
		String fileOnly = encFilPath.substring(index + 1);
		String[] fileAndExt = fileOnly.split(File.separator + ".(?=[^"
			 + File.separator + ".]+$)");

		try {
			encFexp.tmpDir = FileUtil.iswCreateTmpPath();
			encFexp.unArchive(encFexp.tmpDir, encFilPath);
			encFexp.loadKey(encFexp.tmpDir, ISWFileEncryption.ISWPUBKEY);
			encFexp.decrypt(encFexp.tmpDir, fileAndExt[0] + ".xml", outPath);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (encFexp.tmpDir.exists()) {
					FileUtil.rDelete(encFexp.tmpDir);
				}
			} catch (Exception fe) {
				// smother
			}
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			ISWFileEncryption encFexp = new ISWFileEncryption();
			/*
			encryptFinExp(encFexp, "c:\\tmp\\test\\fexptest.txt",
					"c:\\tmp\\test\\fexptest.fex");
			decryptFinExp(encFexp, "c:\\tmp\\test\\fexptest.fex",
					"c:\\tmp\\test\\out");
			*/
			decryptFinExp(encFexp, "c:\\development\\CA-TNF-001662-20150301-20150310-1351.fex",
							"c:\\development");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
