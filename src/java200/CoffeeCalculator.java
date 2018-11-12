package java200;

public class CoffeeCalculator {
	public double total(int price, int person) {
		return price * person;
	}
	
	public double total(double totalPrice) {
		if(totalPrice >= 10000) { 
			return totalPrice - 2000;
		}else {
			return totalPrice;
		}
	}
	
	public String total(int person) {
		if(person >= 10) { 
			return "대량 주문으로 처리합니다.";
		}else {
			return "기본 주문으로 처리합니다.";
		}
	}
	
	public int total(String coffee, int person) {
		if(coffee.equals("라떼")) { 
			return 4000 * person;
		}else if(coffee.equals("카푸치노")) { 
			return 5000 * person;
		}else if(coffee.equals("바닐라라떼")) { 
			return 3000 * person;
		}else {
			System.out.println("올바른 커피 종류를 주문해주세요.");
			return 0;
		}
	}
}

