package engine;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import asset.FixIncome;
import exception.engine.DateDoesNotMatchException;
import exception.engine.DateTimeNotInRangeException;

public class PVFixIncomeEngine extends FixIncomeEngine{
	
	private double discountRatePerYear;

	public PVFixIncomeEngine(double discountRatePerYear){
		this.discountRatePerYear = discountRatePerYear;
	}
	
	public double getValueFrom(FixIncome fixIn, LocalDateTime currentDate) throws DateTimeNotInRangeException {
		
		//to use the initial value
		//Time given must be after fixIn date creation and before maturity of the fixIn
		if (currentDate.isBefore(fixIn.getCurrentDate()) || currentDate.isAfter(fixIn.getMaturityDate())){
			throw new DateTimeNotInRangeException();
		}

		double years = ChronoUnit.YEARS.between(fixIn.getCurrentDate(), currentDate);
		
		double noPayments = years * fixIn.getperiodInYear();
		
		double discountRate = 1 + discountRatePerYear / fixIn.getperiodInYear();
		
		double value = 0;
		try {
			value = fixIn.getCurrentValue(currentDate);
		} catch (DateDoesNotMatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0;i<= noPayments;i++)
		{
			value *= discountRate;
		}
		
		return value;
	}

	@Override
	public ValueEngine clone() {
		return new PVFixIncomeEngine(this.discountRatePerYear);
	}

	@Override
	public double getInitialValue(FixIncome fixIn) {
		//Initial value of the FI starts with face value and we slowly discount it
		double value = fixIn.getFaceValue();
		
		//Coupon amount, the amount that is paid each time
		double couponAmt = fixIn.getcouponRate() * fixIn.getFaceValue() / fixIn.getperiodInYear();
		
		//Get the amount of years between the creation period and maturity of the FI
		double years = ChronoUnit.YEARS.between(fixIn.getCreationDate(), fixIn.getMaturityDate());
		
		//Total number of payments that should happen
		double noPayments = years * fixIn.getperiodInYear();
		
		//Discount rate that is multiplied  after every payment
		double discountRate = 1 + discountRatePerYear / fixIn.getperiodInYear();
		
		//start with 0 not 1 since first payment is not done at time period 0
		for (int i=0;i<= noPayments;i++)
		{
			value /= discountRate;
			value += couponAmt;
		}
		return value;
	}

}
