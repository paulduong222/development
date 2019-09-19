package gov.nwcg.isuite.framework.cryptfile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ISWFileEncryption {
	/**
	 * 
	 */

	private static final int AESKEYSIZE = 128;
	private static final String AESKEYFIL = "metadata.0";

	public static final String ISWPUBKEY = "isw0.dat";
	public static final String ISWPRIVKEY = "isw1.dat";

	public byte[] aesKey = new byte[AESKEYSIZE / 8];
	public File tmpDir;

	public ISWFileEncryption() {

	}

	public byte[] genKey(String pPhrase) throws NoSuchAlgorithmException,
			NoSuchProviderException, UnsupportedEncodingException {

		if (null == pPhrase) { // No pass phrase generate random
			KeyGenerator kgen = KeyGenerator.getInstance("AES", "IBMJCE");
			kgen.init(AESKEYSIZE);
			SecretKey SK = kgen.generateKey();
			return SK.getEncoded();
		} else { // create key from phrase
			byte[] bytesOfPhrase = pPhrase.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(bytesOfPhrase);
		}
	}

	public void encrypt(byte[] aesKey, File tmpDir, String fileName)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException {

		SecretKeySpec sKS = new SecretKeySpec(aesKey, "AES");
		Cipher aesCipher = Cipher.getInstance("AES", "IBMJCE");
		aesCipher.init(Cipher.ENCRYPT_MODE, sKS);

		FileInputStream is = new FileInputStream(fileName);
		int index = fileName.lastIndexOf(File.separator);
		if(index==-1)
			index = fileName.lastIndexOf("/");
		String fileAndExt = fileName.substring(index + 1);
		String path = tmpDir.getAbsolutePath();

		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(
				path + File.separator + fileAndExt), aesCipher);
		cp(is, os);
		os.flush();
		os.close();
		is.close();
	}

	public void decrypt(File tmpDir, String filName, String outPath)
			throws IOException, GeneralSecurityException,
			NoSuchPaddingException {

		SecretKeySpec sKS = new SecretKeySpec(aesKey, "AES");
		Cipher aesCipher = Cipher.getInstance("AES", "IBMJCE");
		aesCipher.init(Cipher.DECRYPT_MODE, sKS);

		CipherInputStream is = new CipherInputStream(new FileInputStream(tmpDir
				+ File.separator + filName), aesCipher);
		FileOutputStream os = new FileOutputStream(outPath + File.separator
				+ filName);

		cp(is, os);
		is.close();
		os.flush();
		os.close();
	}

	private void cp(InputStream is, OutputStream os) throws IOException {
		int x;
		byte[] b = new byte[4096];
		while ((x = is.read(b)) != -1) {
			os.write(b, 0, x);
		}
	}

	public void iswPackage(File f, String outFilPath)
			throws FileNotFoundException, IOException {

		final int BUFFER = 4096;
		boolean isEntry = false;
		ArrayList<String> directoryList = new ArrayList<String>();
		if (f.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream(outFilPath);
				ZipOutputStream zos = new ZipOutputStream(
						new BufferedOutputStream(fos));
				byte data[] = new byte[BUFFER];

				if (f.isDirectory()) {
					// This handle is a directory
					do {
						String directoryName = "";
						if (directoryList.size() > 0) {
							directoryName = directoryList.get(0);
							//System.out.println("Directory Name At 0 :"
							//		+ directoryName);
						}
						String fullPath = f.getAbsolutePath() + directoryName
								+ File.separator;
						File fileList = null;
						if (directoryList.size() == 0) {
							// root directory
							fileList = f;
						} else {
							// child directory
							fileList = new File(fullPath);
						}
						String[] filesName = fileList.list();

						int totalFiles = filesName.length;
						for (int i = 0; i < totalFiles; i++) {
							String name = filesName[i];
							File filesOrDir = new File(fullPath + name);
							if (filesOrDir.isDirectory()) {
								//System.out.println("New Directory Entry :"
								//		+ directoryName + name + "/");
								ZipEntry entry = new ZipEntry(directoryName
										+ name);
								zos.putNextEntry(entry);
								isEntry = true;
								directoryList.add(directoryName + name);
							} else {
								//System.out.println("New File Entry :"
								//		+ directoryName + name);
								//ZipEntry entry = new ZipEntry(directoryName
								//		+ File.separator + name);
								//ZipEntry entry = new ZipEntry(directoryName
								//		+ "/" + name);
								ZipEntry entry = new ZipEntry(name);
								zos.putNextEntry(entry);
								isEntry = true;
								FileInputStream fileInputStream = new FileInputStream(
										filesOrDir);
								BufferedInputStream bufferedInputStream = new BufferedInputStream(
										fileInputStream, BUFFER);
								int size = -1;
								while ((size = bufferedInputStream.read(data,
										0, BUFFER)) != -1) {
									zos.write(data, 0, size);
								}
								bufferedInputStream.close();
							}
						}
						if (directoryList.size() > 0
								&& directoryName.trim().length() > 0) {
							//System.out.println("Directory removed :"
							//		+ directoryName);
							directoryList.remove(0);
						}

					} while (directoryList.size() > 0);
				} else {
					// handle is a file, just package it
					//System.out.println("Zip this file :" + f.getPath());
					FileInputStream fis = new FileInputStream(f);
					BufferedInputStream bis = new BufferedInputStream(fis,
							BUFFER);
					ZipEntry entry = new ZipEntry(f.getName());
					zos.putNextEntry(entry);
					isEntry = true;
					int size = -1;
					while ((size = bis.read(data, 0, BUFFER)) != -1) {
						zos.write(data, 0, size);
					}
					bis.close();
				}

				// Did we add anything to the package ?
				if (isEntry) {
					zos.close();
				} else {
					zos = null;
					System.out.println("Nothing Found to package.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File or Directory not found");
		}
	}

	public void unArchive(File tmpDir, String inFilName) throws IOException {

		byte[] buffer = new byte[1024];

		ZipInputStream zis = new ZipInputStream(new FileInputStream(inFilName));

		ZipEntry ze = zis.getNextEntry();

		while (ze != null) {

			String fileName = ze.getName();
			File newFile = new File(tmpDir.getAbsolutePath() + File.separator
					+ fileName);

			//System.out.println("file unzip : " + newFile.getAbsoluteFile());

			new File(newFile.getParent()).mkdirs();

			FileOutputStream fos = new FileOutputStream(newFile);

			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

			fos.close();
			ze = zis.getNextEntry();
		}

		zis.closeEntry();
		zis.close();
	}

	static PublicKey readPubKeyFromFile(String keyFileName) throws IOException,
			ClassNotFoundException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchProviderException {

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream instr = classLoader.getResourceAsStream(keyFileName);

		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(
				instr));
		BigInteger m = (BigInteger) oin.readObject();
		BigInteger e = (BigInteger) oin.readObject();
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
		KeyFactory fact = KeyFactory.getInstance("RSA", "IBMJCE");
		PublicKey pubKey = fact.generatePublic(keySpec);

		oin.close();
		return pubKey;
	}

	static PrivateKey readPrivKeyFromFile(String keyFileName)
			throws IOException, ClassNotFoundException,
			NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchProviderException {

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream instr = classLoader.getResourceAsStream(keyFileName);

		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(
				instr));

		BigInteger m = (BigInteger) oin.readObject();
		BigInteger e = (BigInteger) oin.readObject();
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
		KeyFactory fact = KeyFactory.getInstance("RSA", "IBMJCE");
		PrivateKey privKey = fact.generatePrivate(keySpec);

		oin.close();
		return privKey;
	}

	public void saveAESKey(byte[] aesKey, File tmpDir, String pubKeyFil)
			throws IOException, GeneralSecurityException,
			ClassNotFoundException {

		PublicKey pk = readPubKeyFromFile(pubKeyFil);

		Cipher pkCipher = Cipher.getInstance("RSA", "IBMJCE");

		pkCipher.init(Cipher.ENCRYPT_MODE, pk);

		FileOutputStream os = new FileOutputStream(new File(
				tmpDir.getAbsolutePath() + File.separator + AESKEYFIL));

		CipherOutputStream cipherOutputStream = new CipherOutputStream(os,
				pkCipher);
		cipherOutputStream.write(aesKey);
		cipherOutputStream.flush();
		cipherOutputStream.close();

	}

	public void loadKey(File tmpDir, String PrivateKeyFil)
			throws ClassNotFoundException, GeneralSecurityException,
			IOException {

		PrivateKey pk = readPrivKeyFromFile(PrivateKeyFil);
		FileInputStream fis = new FileInputStream(new File(
				tmpDir.getAbsolutePath() + File.separator + AESKEYFIL));

		Cipher pkCipher = Cipher.getInstance("RSA", "IBMJCE");
		pkCipher.init(Cipher.DECRYPT_MODE, pk);
		CipherInputStream is = new CipherInputStream(fis, pkCipher);
		is.read(aesKey);
		is.close();
	}

}
