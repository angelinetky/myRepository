package com.ideagen.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Calculator {

	public static void main(String[] args) throws Exception{

		System.out.println("Calculations:");
		System.out.println("1 + 1 = "+calculate("1 + 1"));
		System.out.println("2 * 2 = "+calculate("2 * 2"));
		System.out.println("1 + 2 + 3 = "+calculate("1 + 2 + 3"));
		System.out.println("6 / 2 = "+calculate("6 / 2"));
		System.out.println("11 + 23 = "+calculate("11 + 23"));
		System.out.println("1 + 1 * 3 = "+calculate("1 + 1 * 3"));
		System.out.println("( 11.5 + 15.4 ) + 10.1 = "+calculate("( 11.5 + 15.4 ) + 10.1"));
		System.out.println("23 - ( 29.3 - 12.5 ) = "+calculate("23 - ( 29.3 - 12.5 )"));

	        	
	}
	
	public static double calculate(String sum) throws Exception {
		/*
		 * 1.Check operator precedence
		 * 2.perform calculation
		 */
		String checkFirst = checkOpPreced(sum);
		double result = doMath(checkFirst);
				
		return result;
		
	}

	/**
	 * Math calculation
	 * @param calString
	 * @return double
	 */
	private static double doMath(String calString) {

		BigDecimal calResult = new BigDecimal("0.00");
		String calBracket = "";
		String newCalcString = "";

		for(int i=0; i<calString.length(); i++) {
			if(calString.charAt(i)=='(') {
				calBracket = calString.substring(i+2, calString.indexOf(")",i)-1);
				newCalcString = newCalcString + doMath(calBracket);
				i = calString.indexOf(")");
			} else {
				newCalcString = newCalcString + calString.charAt(i);
			}
		}
		
		String[] sumString = newCalcString.split("\\s");
		for (int i = 0; i < sumString.length; i++) {
			if (isNumeric(sumString[i])) {
				calResult = new BigDecimal(Double.parseDouble(sumString[i]));
			} else {
				calResult = MathOperation(calResult, new BigDecimal(Double.parseDouble(sumString[i + 1])), sumString[i]);
				i++;
			}
			
		}
		
		return calResult.doubleValue();

	}
	
	/**
	 * Check the Operator precedence
	 * The method will reconstruct a new sum string by adding brackets to precedence combination
	 * @param sum
	 * @return String
	 */
	private static String checkOpPreced(String inputStr) {
		String[] inputString = inputStr.split("\\s");
		List<String> strList = new ArrayList<String>();
		String newSumString="";

		//Convert to string list
		for(String num:inputString) {
			strList.add(num); 
		}
		
		//Identify operator * & /, add bracket before and after
		if(strList.contains("*")) {
			int operatorIndex = strList.indexOf("*");
			strList.add(operatorIndex-1, "(");
			strList.add(operatorIndex+3, ")");
			
		}else if(strList.contains("/")) {
			int operatorIndex = strList.indexOf("/");
			strList.add(operatorIndex-1, "(");
			strList.add(operatorIndex+3, ")");
		}
		
		//Convert back to string
		for (String str : strList)
		{
			newSumString += str+ " ";
		}

		if(newSumString.length()>0) {
			return newSumString;
		}else
			return inputStr;
		
	}

	/**
	 * Method to determine type of Math Operation, based on operator
	 * @param number
	 * @param operator
	 * @return BigDecimal
	 */
	private static BigDecimal MathOperation(BigDecimal firstNum, BigDecimal secondNum, String operator) {
		BigDecimal result = new BigDecimal("0.00");
		
		switch (operator) {
		case "+":
			result = firstNum.add(secondNum);
			break;
		case "-":
			result = firstNum.subtract(secondNum);
			break;
		case "*":
			result = firstNum.multiply(secondNum);
			break;
		case "/":
			result = firstNum.divide(secondNum);
			break;
		}
		return result;
	}

	/**
	 * Check if the number is numeric using regex
	 * 
	 * @param strNum
	 * @return boolean
	 */
	private static boolean isNumeric(String strNum) {
		Pattern pattern = Pattern.compile("(\\d+\\.?)+");
		if (strNum == null) {
			return false;
		}
		return pattern.matcher(strNum).matches();
	}

}
