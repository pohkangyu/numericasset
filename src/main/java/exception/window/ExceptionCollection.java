package exception.window;

import java.util.ArrayList;
import java.util.List;

public class ExceptionCollection {
	
	public static List<Exception> lstExcept = new ArrayList<Exception>();

	public static void addException(Exception e){
		lstExcept.add(e);
	}
}
