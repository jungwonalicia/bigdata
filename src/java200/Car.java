package java200;

public class Car {
	int speed;
	int gear;
	String color;
	
	
	public void speedUp(Car car) {
		System.out.println("입력된 색은 " + car.color);
		System.out.println("속도를 Up");
	}
	
	public void speedDown() {
		System.out.println("속도를 Down");
	}

	// kgjava@daum.net => at(~에) 
	// annotation(표기,표시) 
	
	@Override //오버라이드: 재정의=>@
	public String toString() {
		return "Car [speed=" + speed + ", gear=" + gear + ", color=" + color + "]";
	}
	
	
	
	
	
	
	
	
	
}
