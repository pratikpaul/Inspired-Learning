package learn.implement.innovate.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

public class AESEncryption {

	public static String encrypt(String password, SecretKey key) {
		byte[] cipherText = encryptLogic(password, key);
		return readableHex(cipherText);
	}

	public static String decrypt(byte[] cipherText, SecretKey key) {
		String decryptedText = decryptLogic(cipherText, key);
		return decryptedText;
	}

	public static byte[] encryptLogic(String plainText, SecretKey key) {
		byte[] byteCipher = null;
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteCipher = cipher.doFinal(plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return byteCipher;
	}

	public static String decryptLogic(byte[] byteCipher, SecretKey key) {
		byte[] bytePlaintext = null;

		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			bytePlaintext = cipher.doFinal(byteCipher);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new String(bytePlaintext);
	}

	private static String readableHex(byte[] hash) {
		return DatatypeConverter.printHexBinary(hash);
	}
}
