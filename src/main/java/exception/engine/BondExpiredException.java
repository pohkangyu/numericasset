package exception.engine;

public class BondExpiredException extends Exception {
	public BondExpiredException() {
		super("Bond is expired!");
	}
}
