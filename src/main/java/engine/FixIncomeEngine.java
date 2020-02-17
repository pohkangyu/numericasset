package engine;

import java.time.LocalDateTime;

import asset.FixIncome;
import exception.engine.DateTimeNotInRangeException;

public abstract class FixIncomeEngine extends ValueEngine {
	public abstract double getValueFrom(FixIncome fixIncome, LocalDateTime currentDate) throws DateTimeNotInRangeException;
	public abstract double getInitialValue(FixIncome fixIncome);
}
