package expections;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import util.Manager;

public class CannotPerformOperationException extends Exception {
	
	public CannotPerformOperationException(String string) {
		Manager.log(this, string);
	}

	public CannotPerformOperationException(String string, NoSuchAlgorithmException ex) {
		Manager.log(this, string + "\n" + ex);
	}

	public CannotPerformOperationException(String string, InvalidKeySpecException ex) {
		Manager.log(this, string + "\n" + ex);
	}

}
