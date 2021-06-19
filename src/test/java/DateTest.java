import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateTest {

	@Test
		// @checkstyle:off
	void 시분초() {
		// @checkstyle:on
		Date date = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append(date.getHours());
		sb.append(":");
		sb.append(date.getMinutes());
		sb.append(":");
		sb.append(date.getSeconds());

		System.out.println(sb);
	}
}
