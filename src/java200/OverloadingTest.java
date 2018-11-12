package java200;

public class OverloadingTest {

	public static void main(String[] args) {
		Overload over = new Overload();
		over.add(1, 1); // ok
		//over.add(1, 0.2); //순서
		over.add(1.1, 2); // ok
		//over.add(2.3, 4.5); //데이터 타입
		//over.add(4.4); //갯수
	}
}


