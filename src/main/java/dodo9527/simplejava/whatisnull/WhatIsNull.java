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
