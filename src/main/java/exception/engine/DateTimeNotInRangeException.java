package exception.engine;

public class DateTimeNotInRangeException extends Exception{
	public DateTimeNotInRangeException() {
		super("Date is not within current date and maturity range");
	}
}
