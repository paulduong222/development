/**
 * 
 */
package gov.nwcg.isuite.framework.security.impl;

import gov.nwcg.isuite.framework.exceptions.DomainException;
import gov.nwcg.isuite.framework.security.Encryptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author mpoll
 *
 */
public class SecretKeyEncryptorImpl implements Encryptor {
	private static final String PROVIDER = "BC";

	private static final String ALGORITHM = "ARC4";

	private static final String ALGORITHM_WITH_PADDING = "ARC4";

//	private static final String FORMAT_STRING = "HH:mm:ss:SSS";

//	private static final SimpleDateFormat sdf = new SimpleDateFormat(
//			FORMAT_STRING);

	private byte[] keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
			0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };

	private String propFileName = "";

	private Cipher cipher = null;

	private SecretKeySpec key = null;
	
	private static Properties props = null;

	public SecretKeyEncryptorImpl(String propFileName) throws DomainException {
//		System.out.println("Constructor Start:"
//				+ sdf.format(Calendar.getInstance().getTime()));

		Security.addProvider(new BouncyCastleProvider());
		this.propFileName = propFileName;

//		System.out.println("Constructor End:"
//				+ sdf.format(Calendar.getInstance().getTime()));
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.security.Encryptor#encrypt(java.lang.String)
	 */
	public String encrypt(String data) throws DomainException {
		String encryptedString = "";
		try {
//			System.out.println("Encrypt Start:"
//					+ sdf.format(Calendar.getInstance().getTime()));

			loadKey();

			byte[] cipherText = new byte[data.length()];
			getCipher().init(Cipher.ENCRYPT_MODE, key);
			int ctLength = getCipher().update(data.getBytes(), 0, data.length(),
					cipherText, 0);
			ctLength += getCipher().doFinal(cipherText, ctLength);

			encryptedString = base64Encode(cipherText);

//			System.out.println("Encrypt End:"
//					+ sdf.format(Calendar.getInstance().getTime()));
		///CLOVER:OFF
		} catch (InvalidKeyException e) {
			throw new DomainException("incorrect key provided", e);
		} catch (ShortBufferException e) {
			throw new DomainException("buffer has less data than expected", e);
		} catch (IllegalBlockSizeException e) {
			throw new DomainException("incorrect block size provided", e);
		} catch (BadPaddingException e) {
			throw new DomainException("incorrect padding format provided", e);
		}
		///CLOVER:ON
		return encryptedString;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.security.Encryptor#decrypt(java.lang.String)
	 */
	public String decrypt(String data) throws DomainException {
		String decryptedString = "";
		try {
//			System.out.println("Decrypt Start:"
//					+ sdf.format(Calendar.getInstance().getTime()));

			loadKey();

			byte[] b64DecryptedText = base64Decode(data);

			byte[] plainText = new byte[data.length()];
			getCipher().init(Cipher.DECRYPT_MODE, key);
			int ptLength = getCipher().update(b64DecryptedText, 0,
					b64DecryptedText.length, plainText, 0);
			ptLength += getCipher().doFinal(plainText, ptLength);
			decryptedString = new String(plainText);

//			System.out.println("Decrypt End:"
//					+ sdf.format(Calendar.getInstance().getTime()));
		///CLOVER:OFF
		} catch (InvalidKeyException e) {
			throw new DomainException("incorrect key provided", e);
		} catch (ShortBufferException e) {
			throw new DomainException("buffer has less data than expected", e);
		} catch (IllegalBlockSizeException e) {
			throw new DomainException("incorrect block size provided", e);
		} catch (BadPaddingException e) {
			throw new DomainException("incorrect padding format provided", e);
		}
		///CLOVER:ON
		return decryptedString.trim();
	}

	void loadKey() throws DomainException {
		if (key == null) {
			try {
				InputStream is = loadInputStream();
				if (is != null) {
					getProperties().load(is);
					String keyProp = props.getProperty("keyProp");

					ReverseEncryptorImpl rei = new ReverseEncryptorImpl();
					keyProp = rei.decrypt(keyProp);

					byte[] b64DecryptedText = base64Decode(keyProp);

					key = new SecretKeySpec(b64DecryptedText, ALGORITHM);
				} else {
					URL url = SecretKeyEncryptorImpl.class.getResource("/");
					String filePath = url.getPath() + propFileName;

					File file = new File(filePath);
					file.createNewFile();

					key = new SecretKeySpec(keyBytes, ALGORITHM);

					String b64EncodedString = base64Encode(key.getEncoded());

					ReverseEncryptorImpl rei = new ReverseEncryptorImpl();
					String keyProp = rei.encrypt(b64EncodedString);

					getProperties().setProperty("keyProp", keyProp);
					getProperties().store(new FileOutputStream(file), null);
				}
			///CLOVER:OFF
			} catch (FileNotFoundException e) {
				throw new DomainException("cannot find the specified file", e);
			} catch (IOException e) {
				throw new DomainException("cannot read and-or write file", e);
			}
			///CLOVER:ON
		}
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
		///CLOVER:OFF
		} catch (IOException e) {
			throw new DomainException("cannot decode string", e);
		}
		///CLOVER:ON
		return b64DecryptedText;
	}

	Properties getProperties() {
		if (props == null) {
			props = new Properties();
		}
		return props;
	}
	
	InputStream loadInputStream() {
		return SecretKeyEncryptorImpl.class.getClassLoader()
		.getResourceAsStream(propFileName);
	}

	SecretKeySpec getKey() throws DomainException {
		if (key == null) {
			try {
				loadKey();
			///CLOVER:OFF
			} catch (DomainException e) {
				throw new DomainException("cannot load key", e);
			}
			///CLOVER:ON
		}
		return key;
	}
	
	void setKey(SecretKeySpec key) {
		this.key = key;
	}
	
	Cipher getCipher() throws DomainException {
		if (cipher == null) {
			try {
				cipher = Cipher.getInstance(ALGORITHM_WITH_PADDING, PROVIDER);
			///CLOVER:OFF
			} catch (NoSuchAlgorithmException e) {
				throw new DomainException("cannot find the provided algorithm to utilize", e);
			} catch (NoSuchProviderException e) {
				throw new DomainException("cannot find the provider to utilize", e);
			} catch (NoSuchPaddingException e) {
				throw new DomainException("incorrect padding format provided", e);
			}
			///CLOVER:ON
		}
		return cipher;
	}

//	public static void main(String[] args) {
//
//		String inputData = "1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890" +
//		"1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890" +
//		"1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890" +
//		"1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890" +
//		"1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890" +
//		"1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890" +
//		"1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890" +
//		"1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890" +
//		"1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890" +
//		"1234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890" +
//		"1234567891123456789212345678931234567894123456";
//
//		try {
//			SecretKeyEncryptorImpl ei = new SecretKeyEncryptorImpl("testProperties.properties");
//			String encryptedString = ei.encrypt(inputData);
//			String decryptedString = ei.decrypt(encryptedString);
//			System.out.println("Decrypted String:" + decryptedString);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
