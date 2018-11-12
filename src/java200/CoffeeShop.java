package java200;

import java.util.Scanner;

public class CoffeeShop {

	public static void main(String[] args) {
		CoffeeCalculator cal = new CoffeeCalculator();
		//1.
		Scanner	sc = new Scanner(System.in);
		System.out.println("커피값과 인원수를 입력하세요.");
		int price = sc.nextInt();
		int person = sc.nextInt();
		
		double total = cal.total(price, person);
		
		//2.
		double payment = cal.total(total);
		System.out.println("당신이 최종 지불한 금액은 " + (int)payment + "원");
		
		//3.
		String result = cal.total(person);
		System.out.println("인원수 " + person + "명 주문은 " + 
							result);
		
		//4. 
		System.out.print("먹고 싶은 커피종류를 정확하게 입력>> ");
		String coffee = sc.next();
		int finalPayment = cal.total(coffee, person);
		System.out.println("커피 종류에 따른 최종 지불한 금액은 " + finalPayment);
	}
}
