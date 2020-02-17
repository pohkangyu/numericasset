package test;

import static org.junit.Assert.assertEquals;
import java.time.LocalDateTime;
import org.junit.Test;
import engine.ConstantEngine;
import engine.ValueEngine;
import exception.engine.DateTimeNotInRangeException;

class ConstantEngineTest {

	@Test
	void test() {
		ConstantEngine testEngine = new ConstantEngine(0.01);
		
		LocalDateTime end = LocalDateTime.of(2015, 9, 10, 0, 0, 0);
		LocalDateTime start = LocalDateTime.of(2014, 9, 10, 0, 0, 0);
		
		double result = 0;
		
		try {
			result = testEngine.getValueFrom(start, end);
		} catch (DateTimeNotInRangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1.01, result);
	}

}
