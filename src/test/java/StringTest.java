public class StringTest {

	@org.junit.jupiter.api.Test
	public void 이름_아스타테스트() {
		String str = "김천기";
		StringBuilder sb = new StringBuilder();
		sb.append(str.charAt(0));
		if (str.length() < 3) {
			sb.append("*");
			System.out.println(sb);
			return;
		}
		sb.append(str.substring(1, str.length()-1).replaceAll("[가-힣a-zA-Z0-9]{1}", "*"));
		sb.append(str.substring(str.length()-1));

		System.out.println(sb);
	}
}
