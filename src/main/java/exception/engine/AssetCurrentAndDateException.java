package exception.engine;

public class AssetCurrentAndDateException extends Exception {
	public AssetCurrentAndDateException() {
		super("Date to be calculated is after current date of Bond");
	}
}
