package exceptions;

@SuppressWarnings("serial")
public class ArvoreVaziaException extends NoInexistenteException {

	public ArvoreVaziaException() {
	}

	public ArvoreVaziaException(String message) {
		super(message);
	}

	public ArvoreVaziaException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ArvoreVaziaException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ArvoreVaziaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
