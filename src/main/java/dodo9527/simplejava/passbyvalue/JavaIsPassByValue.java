package dodo9527.simplejava.passbyvalue;

public class JavaIsPassByValue {

	public static void main(String[] args) {
		System.out.println("測試基本型別是否passByValue");
		primitiveTest();
		System.out.println("測試物件型別是否passByValue");
		objectTest();
	}
	
	/**
	 * 測試primitive type 是否 passByValue
	 */
	private static void primitiveTest(){
		int x =10;
		int y =20;
		//invoke一個新的方法，並在方法內交換object instance
		primitiveSwap(x, y);
		System.out.println("primitiveTest result:");
		System.out.println("x = "+x);
		System.out.println("y = "+y);
		
	}
	
	/**
	 * pass primitive parameter for test
	 * @param a
	 * @param b
	 */
	private static void primitiveSwap(int swapX ,int swapY){
		int temp=swapX;
		swapX=swapY;
		swapY=temp;
		System.out.println("Inside of primitiveSwap");
		System.out.println("swapX = "+swapX);
		System.out.println("swapY = "+swapY);
	}
	
	/**
	 * 測試Object type 是否 passByValue
	 */
	private static void objectTest(){
		Car x = new Car();
		x.setLogo("BMW");
		Car y=new Car();
		y.setLogo("Benz");
		objectSwap(x, y);
		System.out.println("primitiveTest result:");
		System.out.println("x = "+x.getLogo());
		System.out.println("y = "+y.getLogo());
		
	}
	
	/**
	 * pass primitive parameter for test
	 * @param swapX
	 * @param swapY
	 */
	private static void objectSwap(Car swapX ,Car swapY){
		Car temp=swapX;
		swapX=swapY;
		swapY=temp;
		System.out.println("Inside of objectSwap");
		System.out.println("swapX = "+swapX.getLogo());
		System.out.println("swapY = "+swapY.getLogo());
	}
	

	

}
