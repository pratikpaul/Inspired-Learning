package learn.implement.innovate.encryption;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public enum SecretKeyGeneration {

	Instance;
	
	public SecretKey getEncryptionKey() {
		KeyGenerator generator = null;
		try {
			generator = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		generator.init(128);
		SecretKey key = generator.generateKey();
		return key;
	}
}
