package java200;

public class clockTest {

	public static void main(String[] args) {
		Clock clock = new Clock();
		clock.color = "골드";
		clock.shape = "네모";
		
		System.out.println(clock);
		clock.run(100);
		clock.stop("202호");
	}

}
