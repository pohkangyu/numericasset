package data;

import com.sun.javafx.property.PropertyReference;

import exception.window.ExceptionCollection;
import exception.window.ReadTableException;
import exception.window.ReadTableException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class DynamicPropertyValueFactory<S extends DataStructure,T> implements Callback<CellDataFeatures<S,T>, ObservableValue<T>> {
    
	private final String property;
    private Class<?> columnClass;
    private String previousProperty;
    private PropertyReference<T> propertyRef;
    
	public DynamicPropertyValueFactory(String property) {
        this.property = property;
		// TODO Auto-generated constructor stub
	}
	
    private ObservableValue<T> getCellDataReflectively(S rowData) {
        if (getProperty() == null || getProperty().isEmpty() || rowData == null) return null;
        
        // we attempt to cache the property reference here, as otherwise
        // performance suffers when working in large data models. For
        // a bit of reference, refer to RT-13937.
        T value = (T) rowData.getItem(getProperty());
        
        ExceptionCollection.addException(new ReadTableException(rowData));
        
        return new ReadOnlyObjectWrapper<T>(value);
        
    }
    
    public final String getProperty() { return property; }

    
    @Override public ObservableValue<T> call(CellDataFeatures<S,T> param) {
        return getCellDataReflectively(param.getValue());
    }
}
