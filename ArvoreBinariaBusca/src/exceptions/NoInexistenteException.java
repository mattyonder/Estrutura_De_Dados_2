package exceptions;

@SuppressWarnings("serial")
public class NoInexistenteException extends Exception {

	public NoInexistenteException() {
	}

	public NoInexistenteException(String message) {
		super(message);
	}

	public NoInexistenteException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NoInexistenteException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoInexistenteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
