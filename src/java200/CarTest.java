package java200;

public class CarTest {

	public static void main(String[] args) {
		Car car = new Car();
		car.color = "빨강";
		car.gear = 2;
		car.speed = 300;
		
		car.speedUp(car);
		car.speedDown();
		
		System.out.println(car);
		
		
	}

}
