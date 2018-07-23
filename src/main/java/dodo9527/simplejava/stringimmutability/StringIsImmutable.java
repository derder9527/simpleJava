package dodo9527.simplejava.stringimmutability;


public class StringIsImmutable {

	public static void main(String[] args) {
		System.out.println("stringReferenceCheck ");
		stringReferenceCheck();
		System.out.println("=======================");
		System.out.println("stringConstantCheck  ");
		stringConstantCheck();
		System.out.println("========================");
		System.out.println("stringInternCheck");
		stringInternCheck();
		
		String c = new String("abcd").intern();
		String d = new String("abcd").intern();
		System.out.println(c == d); // Now true
		System.out.println(c.equals(d)); // True
	}
	
	/**
	 * use == to check if multiple strings are using same object 
	 */
	private static void stringReferenceCheck(){
		String s1="abcd";
		String s2="abcd";
		String s3="ab"+"cd";
		String s4=new String("abcd");
		String s5=new String("ab")+"cd";
		String s6="ab";
		String s7="cd";
		String s8=s6+s7;
		String s9=new StringBuilder().append("a").append("b").append("c").append("d").toString();
		System.out.println("s1==s2 ?"+(s1==s2));//true
		System.out.println("s1==s3 ?"+(s1==s3));//true
		System.out.println("s1==s4 ?"+(s1==s4));//false
		System.out.println("s1==s5 ?"+(s1==s5));//false
		System.out.println("s1==s8 ?"+(s1==s8));//false
		System.out.println("s1==s9 ?"+(s1==s9));//false
		
		String ss1=new String("abcd");
		String ss2=new String("abcd");
		System.out.println("ss1==ss2 ?"+(ss1==ss2) ); //false
	}
	
	
	/**
	 * 用兩個class來保存相等的字串常數，並作reference check
	 */
	private static void stringConstantCheck(){
		StringContext c1=new StringContext();
		StringContextTwo c2=new StringContextTwo();
		System.out.println("c1.constant == c2.constant ?"+(c1.getConstant()== c2.getConstant())); //true
	}
	
	/**
	 * String的intern方法測試
	 */
	private static void stringInternCheck(){
		String i1="macbook";
		String i2=new String("macbook");
		String i3=i2.intern();
		String i4=(new String("mac").intern())+(new String("book").intern());
		String i5=new String("macbookPro").intern();
		String i6=new String("macbookPro").intern();
		System.out.println("i1==i2 ?"+(i1==i2));//false
		System.out.println("i1==i3 ?"+(i1==i3));//true
		System.out.println("i1==i4 ?"+(i1==i4));//false
		System.out.println("i5==i6 ?"+(i5==i6));//true
	}

}
