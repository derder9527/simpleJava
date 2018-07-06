## Java is pass by value

**Java是傳值不是傳址** ，剛開始學java的時候，書上的這句話一直很困擾我，一直到最近才突然開竅

原來是我想的傳值跟址跟定義的不太一樣！

摘錄自[Java is Pass-by-Value, Dammit!](http://www.javadude.com/articles/passbyvalue.htm)

```wiki "Java is Pass-by-Value, Dammit!" http://www.javadude.com/articles/passbyvalue.htm

Pass-by-value
    The actual parameter (or argument expression) is fully evaluated and the resulting value is copied into a location being used to hold the formal parameter's value during method/function execution. That location is typically a chunk of memory on the runtime stack for the application (which is how Java handles it), but other languages could choose parameter storage differently.
    
Pass-by-reference
    The formal parameter merely acts as an alias for the actual parameter. Anytime the method/function uses the formal parameter (for reading or writing), it is actually using the actual parameter. 
```

當你invoke某個方法，並把參數傳送進方法時⋯⋯

- 傳值：你是把該物件本身(Object itself)轉給新的方法

- 傳址：你是把物件的指標(Object pointer)轉給新的方法

  

**到這裡應該會有種，我他媽的到底看了三小的感覺**

---



### 在JAVA，我們是怎麼宣告變數的？

```java
Car x; //宣告一個pointer
x=new Car(); //創造真正的Car物件實體，並把x的pointer指向新創建的Car實體
x.setLogo("BMW");//透過pointer為指向的物件更改它的instance variable 
```

|               Car x               |       =        | new Car()             |
| :-------------------------------: | :------------: | --------------------- |
| 宣告一個Car  的 pointer 並取名為x | 把pointer 指向 | 真正創建一個Car的物件 |

要宣告變數，我們要先幫他取一個**名字**，可能你會以為這個名字是**直接**代表那個物件，但實際上並不是；這個名字是代表一個pointer，這個pointer裡面可以存放真正的物件所在位址，透過`=`號就可以把這個pointer指向真正的物件位址，pointer有了真正的物件位址以後，你所invoke的方法才不會發生nullpointerExecepion 。

讓我們撰寫一個新的`changeCar(Car passInCar)` 方法內部更換傳入的參數`passInCar`

```java
public static void changeCar(Car passInCar){
    //更換 passInCar pointer 所指向的物件
    passInCar = new Car();
    //透過pointer為指向的物件更改它的instance variable 
    passInCar.setLogo("Benz");
}
```

接續之前的`x` 宣告pointer範例，將`x`做為`changeCar(Car passInCar)` 的輸入參數 ，且我們在方法內部將Car物件換一個全新的，並設定它的logo為Benz，最後將呼叫完畢後的 `x` Car物件print 出它的Logo⋯⋯

```java
changeCar(x);//將x 傳入method中
System.out.println("Car logo will be "+x.getLogo());//print結果
```



### 如果⋯⋯

- Java是傳值

  

在執行`changeCar(x)` 時，因為傳值是把該物件本身(Object itself)轉給新的方法，所以在changeCar方法內部的參數名稱`passInCar` 就會是一個新的pointer，其所指向的物件實體是從`x`的pointer所傳入的物件-也就是**值**(最後一個值麻煩重點畫線謝謝，這就是傳值！)。

回到最後一行印出`x.getLogo()`的結果，不管changeCar方法內部怎麼使用`=`號將`passInCar`的pointer指向別的物件，因為傳值的關係，方法外的`x`的pointer還是指向最初的BMW car物件！



- ~~Java是傳址~~

  

~~在執行`changeCar(x)` 時，因為傳址是把物件的指標(Object pointer)轉給新的方法，所以在changeCar方法內部的參數名稱`passInCar` 其所代表的pointer 就完全等於`x` ，passInCar的變更也會同步生效到x上。~~

~~回到最後一行印出`x.getLogo()`的結果，因為方法內部已經將`passInCar`這個pointer指向了別的物件，又因為傳址，所以`x`也會跟著一起指向新的物件，故最後x所指向的是Benz car物件~~



- - output

```bash
Car logo will be BMW
```



---

## the other demo 

```java "**JavaIsPassByValue.java** " https://github.com/derder9527/simpleJava/blob/master/src/main/java/dodo9527/simplejava/passbyvalue/JavaIsPassByValue.java
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

```



- output

  ```java
  測試基本型別是否passByValue
  Inside of primitiveSwap
  swapX = 20
  swapY = 10
  primitiveTest result:
  x = 10
  y = 20
  測試物件型別是否passByValue
  Inside of objectSwap
  swapX = Benz
  swapY = BMW
  primitiveTest result:
  x = BMW
  y = Benz
  ```

  

## 結論

- 不管是不是基本型別，Java always pass by value ! 
- 為了預防這種參數在方法之間傳來傳去一不小心就搞錯使用方式的問題，建議方法簽章自動加上final修飾子，讓參數無法透過`=`號更換實體物件

[Source code at GitHub](https://github.com/derder9527/simpleJava/tree/master/src/main/java/dodo9527/simplejava/passbyvalue)



REF:

[Java Pass By Value Stack Heap Memory Explanation](https://www.youtube.com/watch?v=_y7k_0edvuY)

[Heap with Reference  Instance Variables](https://www.youtube.com/watch?v=UcPuWY0wn3w)







