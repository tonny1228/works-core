/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-2-28 下午3:49:48
 * @author tonny
 */
package works.tonny;

import java.util.Calendar;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class Loan {

	@Test
	public void test() {
		float x = 11700;
		float y = 13440;
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < 237; i++) {
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 10);
			x += 570;
			calendar.set(Calendar.DAY_OF_MONTH, 17);
			System.out.print(DateFormatUtils.format(calendar, "yyyy-MM-dd"));
			if (x > 2602) {
				x -= 2602;
				System.out.println("孟婷卡还款后余额" + x);
			} else if (y > 2602) {
				y -= 2602;
				System.out.println("我的卡还款后余额" + y);
			} else {
				x += 2602;
				System.out.println("钱不够了");
			}
			calendar.set(Calendar.DAY_OF_MONTH, 28);
			y += 1920;
		}
	}
}
