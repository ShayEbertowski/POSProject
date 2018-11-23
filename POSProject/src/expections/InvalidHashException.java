package expections;

import util.Manager;

public class InvalidHashException extends Exception {
	
	public InvalidHashException(String string) {
		Manager.log(this, string);
	}

	public InvalidHashException(String string, NumberFormatException ex) {
		Manager.log(this, string + "\n" + ex);
	}

	public InvalidHashException(String string, IllegalArgumentException ex) {
		Manager.log(this, string + "\n" + ex);
	}


}
