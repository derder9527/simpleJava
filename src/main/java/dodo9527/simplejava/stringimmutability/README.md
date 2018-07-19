# String Immutability 

節錄自[String API](https://docs.oracle.com/javase/7/docs/api/java/lang/String.html)

```wiki
The String class represents character strings. All string literals in Java programs, such as "abc", are implemented as instances of this class.

Strings are constant; their values cannot be changed after they are created... Because String objects are immutable they can be shared. 
```

## 什麼是String ?

String 這個class 代表字元**串**，在java中任何`字串符號`都是這個class的實作物件。

接著 請記得 **Java  String is Immutable ** 

---

## 什麼是Immutable？

先來看看String 的 source code

```java
 
 * @author  Lee Boynton
 * @author  Arthur van Hoff
 * @author  Martin Buchholz
 * @author  Ulf Zibis
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    /** The value is used for character storage. */
    private final char value[];

    /** Cache the hash code for the string */
    private int hash; // Default to 0
    ⋯⋯⋯⋯
    }
```

`String ` class中有兩個私有實體變數(重要的)

- `char value[]`  String由字元所串成，這個陣列用來存放真實的字元。
- `int hash` 這個變數用於cache instance的hashcode 

作者在撰寫`String`時，除了建構式以及`hashcode()` 等public方法有辦法變動到這兩個數值以外，**沒有辦法變更這兩個實體變數** ，這種從設計上就讓這個class所初始化的任何物件(instance)的內部數值在建構後皆不可變動的形容詞就叫做 Immutable

---

## 為什麼String 要 Immutable ？

- 為了字串池(**String Pool**)的需要 

`All literal strings and string-valued constant expressions are interned.` -節錄自[String.intern()](https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#intern()) 

> 在java中有個概念叫[Constant Pool](http://blog.51cto.com/chenying/120900)，在編譯期就會把常數們放在`.class`文件裡的常數中，而String pool 是這個常數池的其中一種類型，注意只有用"內容"的字串才算常數喔！

~~~java
String x="abc";
String y="abc";
~~~

上述`String x,y` 皆指向字串的常數，而且他們的內容是相等的，如果沒有字串池的概念的話⋯⋯則String x 所指向的instance需要一段記憶體空間來存放String instance，而String y也同樣需要一段空間，但他們兩個的instance的實際內容卻是一樣的，為了節省空間，所以`String` 引入了字串池的概念。

只要是常數的String，而且內容一樣，**編譯器**就會幫妳自動指向字串(常數)池中的同一個instance，所以這個instance已經被兩個變數所共享，這時候字串的Immutable特性就顯得很重要，因為不可變，所以不管這個instance分享給誰，都會是安全的，**因為沒有人可以改變它**。



- 保存hashcode的快取 

  節錄自`String.hashCode()`

  ```java
     public int hashCode() {
          int h = hash;
          if (h == 0 && value.length > 0) {
              char val[] = value;
  
              for (int i = 0; i < value.length; i++) {
                  h = 31 * h + val[i];
              }
              hash = h;
          }
          return h;
      }
  ```

  

  因為String的不可變動性，只要執行過hashCode()方法後，string的hashcode 就會被cache在hash這個變數中，可以加快在用String來當key的各種集合物件中(HashMap,HashSet)的存取速度。

  

- 安全性

  `String` 很常拿來當作其他class的參數，如果String 的instance可以被改變，那可能就會造成很多安全性的問題(密碼被竄改，連線位址被竄改等等)。

- 執行緒安全性(thread-safe)

  因為value不可變，所以不管是哪個thread存取這個instance的結果都會是一樣的，故`String`是天生的執行緒安全。

---

## String.intern()補充

首先要分清楚`String`的常數，還有變數

```java
String con="abc";//常數instance
String var=new String("abc"); //變數instance
```

- 只要用引號所宣告出來的字串都會在字串池中
- 非常數的instance可以靠String的intern方法去引入在字串池中的字串實體(相同內容的)

> 在runtime時期的字串就算內容相同也是不同的物件實體喔！
>
> ```java
> String ss1=new String("abcd");
> String ss2=new String("abcd");
> System.out.println("ss1==ss2 ?"+(ss1==ss2) ); //false
> ```
>
> 

```java 
String i5=new String("macbookPro").intern();
String i6=new String("macbookPro").intern();
System.out.println("i5==i6 ?"+(i5==i6));//true
```

如果你有一個常見的字串很常在run-time出現，就可以透過intern方法把這個字串通通指向位於字串池中的實體，而不是創造一堆的字串實體來放相同的內容！



---

## Lab

```java
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

```



- output

```java
stringReferenceCheck 
s1==s2 ?true
s1==s3 ?true
s1==s4 ?false
s1==s5 ?false
s1==s8 ?false
s1==s9 ?false
ss1==ss2 ?false
=======================
stringConstantCheck  
c1.constant == c2.constant ?true
========================
stringInternCheck
i1==i2 ?false
i1==i3 ?true
i1==i4 ?false
i5==i6 ?true

```



[Source code at GitHub](https://github.com/derder9527/simpleJava/tree/master/src/main/java/dodo9527/simplejava/stringimmutability)



REF:

[Why String is immutable in Java? ](https://www.programcreek.com/2013/04/why-string-is-immutable-in-java/)

[Where does Java's String constant pool live, the heap or the stack?](https://stackoverflow.com/questions/4918399/where-does-javas-string-constant-pool-live-the-heap-or-the-stack/4918466#comment5484408_4918466)

[What is Java String interning?](https://stackoverflow.com/questions/10578984/what-is-java-string-interning)

[深入解析String#intern](https://tech.meituan.com/in_depth_understanding_string_intern.html)

[Java Performance Tuning Guide](http://java-performance.info/string-intern-in-java-6-7-8/)





