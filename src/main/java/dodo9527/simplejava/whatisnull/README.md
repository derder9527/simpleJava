

# What is Null in java ?

在java裡，除了**PrimitiveType**以及**ReferenceType**之外還有一種特殊的型別叫**null**。([JLS-4.1](https://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.1))

- 沒有正式的type名稱，所以你不能宣告它，也不能轉型成它

  ```java
  Null x; //not working !
  Object y=new Object();
  (Null) y ; //not working !
  ```

- Null 這個沒有名稱的 type 只會有一個 reference 值，那就是null (我不是在玩饒舌⋯⋯)

  ```java
  Object x=null;
  Object y=null;
  System.out.println(x==y);//true
  ```

- null 的reference的表示方式就是`null`，把pointer x 指到null 的reference ([JLS-3.10.7](https://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.7))([JLS-15.8.1](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.8.1))

  ```java
  Object x;
  x=null;//把x指向null 的reference
  ```

- `null`可以當任何ReferenceType的 reference value值 (PrimitiveType 不行喔！)

  ```java
  Clz1 x=null;
  Clz2 y=null;
  Clz3 z=null;
  
  // int k =null ; will cause Unresolved compilation problem: Type mismatch: cannot convert from null to int
  int k=null; 
  
  ```

  > 這裡有地方要注意，`null`可以是任何ReferenceType的 reference value值，但是`null instanceof T`會是恆定的**false**喔！也就是說只要對`null`做任何的`instanceof`檢查，結果都會是false。
  >
  > [JSL-15.20.2-1](https://docs.oracle.com/javase/specs/jls/se10/html/jls-15.html#jls-15.20.2)
  >
  > ```wiki
  > At run time, the result of the instanceof operator is true if the value of the RelationalExpression is not null and the reference could be cast to the ReferenceType without raising a ClassCastException. Otherwise the result is false. 
  > ```



---

## 結論

null是java裡一個特殊的Reference value ，它代表的是一個不存在的instance，這個不存在的instance在java裡有許多特殊的例外，它的存在可以讓許多事情變得簡單

- Method Return 

  ```java
  public static String getCar(int index){
  		String[] cars={"Benz","BMW","Audi"};
  		if(index>cars.length||index<0){
  			return null;
  		}
  		return Arrays.asList(cars).get(index);
  	}
  ```

  因為`null`可以是任何參照型別的值，同樣的方法的回傳值也能使用`null`(Reference type)，所以當方法內有任何不合理的輸入，或者是其他的錯誤狀態，也可以用`null`當成一種return。

  

- NullPointerException 

  `null`是一個不存在的instance，只要invoke到它就會導致這個runtime exception發生，某方面來說也算是一種即時的除錯機制⋯⋯



上述觀念是正向思考的結果⋯⋯

---



我真正的想法是

## **null 煩死人了**

連 Null References的發明人[Tony Hoare](https://en.wikipedia.org/wiki/Tony_Hoare)都在2009年的conference發表了：

[Null References: The Billion Dollar Mistake](https://www.infoq.com/presentations/Null-References-The-Billion-Dollar-Mistake-Tony-Hoare)

- Null references have historically been a bad idea
- Early compilers provided opt-out switches for run-time checks, at the expense of correctness
- ⋯⋯⋯⋯⋯⋯

短講中提到null是個不好的主意，許多java的method使用`null`當成return ，而呼叫該方法的stack又沒有做null檢查，就會導致NullPointerException，這個錯誤卻是在runtime時期才會報出，直接導致整個系統錯誤，**實在是很難處理**。

雖然在Java 8新增了Optional 來補救這個洞，這個解決方案也只是把null檢查後的各種行為放進類別中包覆，並沒有徹底解決null這個洞，設計上的問題還沒辦法透過設計解決，但各位碼農還是可以在程式碼中多做些null檢查來加減彌補這個設計瑕疵！



---

## Lab

```java
/**
 * 
 */
package dodo9527.simplejava.whatisnull;

import java.util.Arrays;

/**
 * @author derder
 *
 */
public class WhatIsNull {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		primitiveTypeInputNull(null); //解開會導致 compilation problem
		referenceTypeInputNulll(null);
		Object x=null;
		Object y=null;
		System.out.println(x==y);//true
		System.out.println(null==null);//true
		System.out.println(null instanceof Object);//false
		String myCar;
		myCar = getCar(0);
		System.out.println(myCar.length());//4
		myCar=getCar(-1);
		System.out.println(myCar.length());//java.lang.NullPointerException
		
	}

	
	public static void primitiveTypeInputNull(int input){
		System.out.println("input = "+input);
	}
	
	public static void referenceTypeInputNulll(Object input){
		System.out.println("input = "+input);
		
	}
	
	public static String getCar(int index){
		String[] cars={"Benz","BMW","Audi"};
		if(index>cars.length||index<0){
			return null;
		}
		return Arrays.asList(cars).get(index);
	}
}

```



[Source code at GitHub](https://github.com/derder9527/simpleJava/tree/master/src/main/java/dodo9527/simplejava/whatisnull)



REF:

[stack overflow](https://stackoverflow.com/questions/2950319/is-null-check-needed-before-calling-instanceof)

[What is null in Java?](https://stackoverflow.com/questions/2707322/what-is-null-in-java)

[ Null 造成了數十億的損失 Null Reference is the Billion Dollar Mistake ](http://blog.maxkit.com.tw/2015/08/null-null-reference-is-billion-dollar.html)

[補救null的策略](https://www.ithome.com.tw/node/79039)

