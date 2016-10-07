package compiler.exception;

public class InvalidFunctionCallException extends Exception {

	private static final long serialVersionUID = 1L;
	public InvalidFunctionCallException(String msg){
		super(msg);
	}
}
