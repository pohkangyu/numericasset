package test;


import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
import asset.FixIncome;
import engine.ConstantEngine;
import engine.PVFixIncomeEngine;
import exception.engine.AssetCurrentAndDateException;
import exception.engine.BondExpiredException;
import exception.engine.DateDoesNotMatchException;
import exception.engine.DateTimeNotInRangeException;

public class PVFixIncomeTest {
	@Test
	void test() {
		PVFixIncomeEngine testEngine = new PVFixIncomeEngine(0.01);
		
		LocalDateTime start = LocalDateTime.of(2014, 9, 10, 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(2015, 9, 10, 0, 0, 0);
		LocalDateTime current = LocalDateTime.of(2015, 9, 10, 0, 0, 0);
		
		FixIncome result = new FixIncome(10000, 10000, 2, 0.05, start, start, end, testEngine);
		
		
		try {
			System.out.println(result.getCurrentValue(start));

			result.runCycle(current);

		} catch (DateTimeNotInRangeException | BondExpiredException | AssetCurrentAndDateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DateDoesNotMatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(result.getCurrentValue(current));
		} catch (DateDoesNotMatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		assertEquals(1.01, result);
	}
}
