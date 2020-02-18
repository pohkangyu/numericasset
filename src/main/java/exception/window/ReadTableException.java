package exception.window;

public class ReadTableException extends Exception {
	
	private Object problem;

	public ReadTableException(Object obj){
		super("Error reading this object["+ obj.getClass().toString() +"] and converting to table");
		this.problem = obj;
	}

}
