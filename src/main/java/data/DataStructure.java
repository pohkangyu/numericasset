package data;
import java.util.HashMap;

public class DataStructure {
	private HashMap<String, Object> map = new HashMap<String, Object>();
	
	public DataStructure(){
	
	}
	
	public DataStructure(String name, Object obj){
		addItem(name, obj);
	}
	
	public  Object addItem(String name, Object obj){
		 return map.put(name, obj);
	}
	public Object getItem(String item){
		return map.get(item);
	}
}
