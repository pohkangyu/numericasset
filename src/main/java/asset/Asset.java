package asset;
import java.time.LocalDateTime;
import engine.ValueEngine;
import exception.engine.AssetCurrentAndDateException;
import exception.engine.BondExpiredException;
import exception.engine.DateDoesNotMatchException;
import exception.engine.DateTimeNotInRangeException;

abstract class Asset {
	
	public abstract double getCurrentCost(LocalDateTime date);
	
	public abstract double getCurrentValue(LocalDateTime date) throws DateDoesNotMatchException;
	
	//get the date at which the asset is created
	public abstract LocalDateTime getCreationDate();

	//clone the asset
	public abstract Asset cloneAsset();

	//get the value engine of the object
	public abstract ValueEngine getValueEngine();

	public abstract void runCycle(LocalDateTime date) throws Exception;
	
}
