/**
 * 
 */
package gov.nwcg.isuite.framework.security.impl;

import gov.nwcg.isuite.framework.exceptions.DomainException;
import gov.nwcg.isuite.framework.security.Encryptor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author mpoll
 * 
 * NOTES:
 * ********************************************************************************
 * This Class should generally not be accessed directly, but should be used via
 * a subclass (such as S2ESiteEncryptor or S2EEnterpriseEncryptor).  This allows
 * only exposing those parts of the encryptor that are necessary, depending on
 * the role the calling object must perform.
 * ********************************************************************************
 * 
 * An RSA Algorithm with 1024 bit encryption will handle 116 character
 * strings only 2048 bit enctryption will handle 245 character strings only
 */

public class PublicPrivateKeyEncryptorImpl implements Encryptor {
	private static final String ALGORITHM = "RSA";
	
	private static final String KEYSTORE_TYPE = "jks";

	// private static final String FORMAT_STRING = "HH:mm:ss:SSS";

	// private static final SimpleDateFormat sdf = new SimpleDateFormat(
	// FORMAT_STRING);

	private String publicFilePath = null;

	private String privateFilePath = null;
	
	private String keystoreAlias = null;

	private String keystorePassword = null;

	private PublicKey pubKey = null;

	private PrivateKey privKey = null;

	public PublicPrivateKeyEncryptorImpl(String publicKeyFilePath,
			String keystoreFileFilePath, String keystoreAlias,
			String keystorePassword)
			throws DomainException {
		// System.out.println("Constructor Start:"
		// + sdf.format(Calendar.getInstance().getTime()));

//		URL url = SecretKeyEncryptorImpl.class.getResource("/");
//		this.publicFilePath = url.getPath() + publicKeyFilename;
//		this.privateFilePath = url.getPath() + keystoreFileFilename;
		this.publicFilePath = publicKeyFilePath;
		this.privateFilePath = keystoreFileFilePath;
//		System.out.println("Public File Path: " + publicFilePath);
//		System.out.println("Private File Path: " + privateFilePath);

		this.keystoreAlias = keystoreAlias;
		this.keystorePassword = keystorePassword;

		if (publicFilePath != null) {
			byte[] encodedPublicKey = extractPublicKey();
			this.pubKey = generatePublicKey(encodedPublicKey);
		}
		
		if (privateFilePath != null) {
			this.privKey = extractPrivateKey();
		}

		// System.out.println("Constructor End:"
		// + sdf.format(Calendar.getInstance().getTime()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.security.Encryptor#encrypt(java.lang.String)
	 */
	public String encrypt(String data) throws DomainException {
		String encryptedString = "";
		try {
			// System.out.println("Encrypt Start:"
			// + sdf.format(Calendar.getInstance().getTime()));
			
			if (pubKey == null) {
				throw new IllegalStateException();
			}

			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] encryptedBytes = cipher.doFinal(data.getBytes());
			encryptedString = base64Encode(encryptedBytes);

			// System.out.println("Encrypt End:"
			// + sdf.format(Calendar.getInstance().getTime()));
///CLOVER:OFF
		} catch (NoSuchAlgorithmException e) {
			throw new DomainException("no such algorithm given", e);
		} catch (NoSuchPaddingException e) {
			throw new DomainException("no such padding", e);
		} catch (InvalidKeyException e) {
			throw new DomainException("invalid key provided", e);
		} catch (IllegalBlockSizeException e) {
			throw new DomainException("illegal block size", e);
		} catch (BadPaddingException e) {
			throw new DomainException("incorrect padding format provided", e);
		}
///CLOVER:ON
		return encryptedString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.security.Encryptor#decrypt(java.lang.String)
	 */
	public String decrypt(String data) throws DomainException {
		String decryptedString = "";
		try {
			// System.out.println("Decrypt Start:"
			// + sdf.format(Calendar.getInstance().getTime()));

			if (privKey == null) {
				throw new IllegalStateException();
			}

			byte[] b64DecryptedText = base64Decode(data);

			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] decryptedBytes = cipher.doFinal(b64DecryptedText);
			decryptedString = new String(decryptedBytes);

			// System.out.println("Decrypt End:"
			// + sdf.format(Calendar.getInstance().getTime()));
///CLOVER:OFF
		} catch (NoSuchAlgorithmException e) {
			throw new DomainException("no such algorithm given", e);
		} catch (NoSuchPaddingException e) {
			throw new DomainException("no such padding", e);
		} catch (InvalidKeyException e) {
			throw new DomainException("invalid key provided", e);
		} catch (IllegalBlockSizeException e) {
			throw new DomainException("illegal block size", e);
		} catch (BadPaddingException e) {
			throw new DomainException("incorrect padding format provided", e);
		}
///CLOVER:ON
		return decryptedString;
	}

	PrivateKey extractPrivateKey() throws DomainException {
		PrivateKey key = null;
		try {
			KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
			ks.load(new FileInputStream(privateFilePath), keystorePassword
					.toCharArray());
			key = (PrivateKey) ks.getKey(keystoreAlias, keystorePassword
					.toCharArray());
		} catch (Exception e) {
			throw new DomainException(
					"exception thrown while extracting private key", e);
		}
		return key;
	}

	byte[] extractPublicKey() throws DomainException {
		byte[] encodedKey = null;
		try {
			InputStream certFileIs = new FileInputStream(publicFilePath);
			CertificateFactory cf = CertificateFactory.getInstance("X509");
			X509Certificate cert = (X509Certificate) cf
					.generateCertificate(certFileIs);
			encodedKey = cert.getPublicKey().getEncoded();
		} catch (Exception e) {
			throw new DomainException(
					"exception thrown while extracting public key", e);
		}
		return encodedKey;
	}

	PublicKey generatePublicKey(byte[] encodedKey) throws DomainException {
		String errorString = "";
		X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(encodedKey);
		KeyFactory factory;
		PublicKey retKey = null;

		try {
			factory = KeyFactory.getInstance(ALGORITHM);
			retKey = factory.generatePublic(pubSpec);
///CLOVER:OFF
		} catch (InvalidKeySpecException e) {
			errorString += "\ncould not create public key of type: "
					+ ALGORITHM;
		} catch (NoSuchAlgorithmException e) {
			errorString += "\ncould not find algorithm type: " + ALGORITHM;
		}
///CLOVER:ON

		return retKey;
	}

	String base64Encode(byte[] text) {
		BASE64Encoder b64e = new BASE64Encoder();
		return b64e.encode(text);
	}

	byte[] base64Decode(String text) throws DomainException {
		byte[] b64DecryptedText = null;
		try {
			BASE64Decoder b64d = new BASE64Decoder();
			b64DecryptedText = b64d.decodeBuffer(text);
			// /CLOVER:OFF
		} catch (IOException e) {
			throw new DomainException("cannot decode string", e);
		}
		// /CLOVER:ON
		return b64DecryptedText;
	}

//	 public static void main(String[] args) {
//
//		String inputData = "1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890"
//				+ "1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890"
//				+ "123456789112345678921234567893123456789412345";
//		try {
//			PublicPrivateKeyEncryptorImpl ei = new PublicPrivateKeyEncryptorImpl(
//					"C:\\Apps\\Projects\\trunk\\isuite-domain\\src\\main\\resources\\pubKey.txt", 
//					"C:\\Apps\\Projects\\trunk\\isuite-domain\\src\\main\\resources\\publicPrivate.keystore", 
//					"forests", 
//					"forests");
//			String encryptedString = ei.encrypt(inputData);
//			System.out.println("Encrypted String:" + encryptedString);
//			String decryptedString = ei.decrypt(encryptedString);
//			System.out.println("Decrypted String:" + decryptedString);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
