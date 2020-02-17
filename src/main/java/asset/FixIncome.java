package asset;

import java.time.LocalDateTime;

import engine.FixIncomeEngine;
import engine.ValueEngine;
import exception.engine.AssetCurrentAndDateException;
import exception.engine.BondExpiredException;
import exception.engine.DateDoesNotMatchException;
import exception.engine.DateTimeNotInRangeException;

public class FixIncome extends Asset {
	private double currentCost;
	private double faceValue;
	private double currentValue;
	private double periodInYear;
	private double couponRate;


	private LocalDateTime creationDate;
	private LocalDateTime maturityDate;
	private LocalDateTime currentDate;
	private FixIncomeEngine FIengine;

	
	public FixIncome(
					double currentCost,
					double faceValue, 
					double periodInYear,
					double couponRate,
					LocalDateTime creationDate, 
					LocalDateTime currentDate, 
					LocalDateTime maturityDate, 
					FixIncomeEngine FIengine) {
		
		//cost at which the asset is purchased
		this.currentCost = currentCost;
		
		//face value of the fixed income, the amount received once maturity hits
		this.faceValue = faceValue;
		
		//Period of coupon payments in a year
		//0.5 == 1 payment in 2 years, 2 == 2 payment in 1 year
		this.periodInYear = periodInYear;
		
		//Coupon rate that is paid per payment 
		this.couponRate = couponRate;
		
		//Date that this fix income is created or started
		this.creationDate = creationDate;
		
		//Current date, to keep track of the value of the fix income
		//Each time this currentDate is reassigned, the value of this fix income must be recalculated
		this.currentDate = currentDate;
		
		//Date of maturity of this fix income
		this.maturityDate = maturityDate;
		
		//The engine that determines how to calculate the value of this fix income
		this.FIengine = FIengine;
		
		//to calculate the current value the moment this bond is created
		//this keep tracks of the value of the fix income sync with the current date
		this.currentValue = FIengine.getInitialValue(this);
		
	}

	@Override
	public Asset cloneAsset() {
		return new FixIncome(currentCost, faceValue, periodInYear,couponRate, creationDate, currentDate, maturityDate, FIengine);
	}


	@Override
	public void runCycle(LocalDateTime date) throws DateTimeNotInRangeException, BondExpiredException, AssetCurrentAndDateException {

		if (date.isAfter(this.maturityDate)) {
			throw new BondExpiredException();
		}
		if (currentDate.isAfter(date))
		{
			throw new AssetCurrentAndDateException();
		}
		
		this.currentValue = FIengine.getValueFrom(this, currentDate);
		this.currentDate = date;
	}
	
	
	//getter methods
	public double getCurrentCost(LocalDateTime date) {
		return this.currentCost;
	}
	
	public double getCurrentValue(LocalDateTime date) throws DateDoesNotMatchException {
		if (date.isEqual(this.currentDate)) {
			return this.currentValue;
		}
		else
		{
			throw new DateDoesNotMatchException();
		}
	}

	public LocalDateTime getCreationDate() {
		return this.creationDate;
	}
	
	public LocalDateTime getCurrentDate() {
		return this.creationDate;
	}
	
	public LocalDateTime getMaturityDate() {
		return this.maturityDate;
	}
	
	public double getperiodInYear() {
		return this.periodInYear;
	}
	
	public double getcouponRate() {
		return this.couponRate;
	}
	
	public ValueEngine getValueEngine() {
		return this.FIengine;
	}

	public double getFaceValue() {
		return this.faceValue;
	}

}
