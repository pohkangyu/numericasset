package engine;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import exception.engine.DateTimeNotInRangeException;

public class ConstantEngine extends EquityEngine{
	
	private double constant;
	
	public ConstantEngine(double constant){
		this.constant = constant;
	}
	
	public double getValueFrom(LocalDateTime start, LocalDateTime end) throws DateTimeNotInRangeException {
		
		if (end.isBefore(start)) {
			throw new DateTimeNotInRangeException();
		}
		else {
			double seconds = (double) ChronoUnit.SECONDS.between(start, end) /(double) 31536000;
			return (double) (1 + seconds * this.constant);
		}
	}

	@Override
	public ValueEngine clone() {
		return new ConstantEngine(this.constant);
	}

}
