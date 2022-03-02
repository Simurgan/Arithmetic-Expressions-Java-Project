import java.util.*;

public class Arithmetic {

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in); //console is the Scanner that we will use in the program to take inputs fom user
		
		String first = clean(console.nextLine()); // String first is the first line from user that has no spaces.
		String second = clean(console.nextLine()); // String second is the second line from user that has no spaces.
		String third = clean(console.nextLine()); // String third is the third line from user that has no spaces.
		String expression = clean(console.nextLine()); // String expression is the fourth line from user that has no spaces.
		
		expression = expression.replace(name(first), value(first));
		expression = expression.replace(name(second), value(second));
		expression = expression.replace(name(third), value(third));
		
		expression = "(" + expression.substring(0, expression.length() - 1) + ")";
		
		while(expression.contains("(")) {
			expression = process(expression);
		}
		
		System.out.println(expression);
		
		console.close();
	}
	public static String clean(String line) { //this method changes its parameter with it's without white space version
		String clear = ""; //This string will be filled with the parameter line's characters without white space
		for(int i = 0; i < line.length(); i++) {
			if(line.charAt(i) != ' ') {
				clear += line.charAt(i);
			}
		}
		return clear;
	}
	public static String name(String line) { // this method returns the variable names in its parameter
		int startIndex, endIndex = 0; // These are start index and end index which we will use in substring method.
		if(line.charAt(0) == 'i') {
			startIndex = 3;
		} else {
			startIndex = 6;
		}
		
		for(int i = startIndex + 1; i < line.length(); i++) {
			if(line.charAt(i) == '=') {
				endIndex = i;
			}
		}
		
		return line.substring(startIndex, endIndex);
	}
	public static String value(String line) { // this method returns the value in its parameter
		String number; // String number is the value in the parameter and will be returned
		int index = 0; // integer index is our index that we will use in substring method
		
		for(int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == '=') {
				index = i + 1;
			}
		}
		number = line.substring(index, line.length() - 1);
		
		if(!(line.charAt(0) == 'i') && !line.contains(".")) {
			number += ".0";
		}
		
		return number;
	}
	public static String process(String expression) { //this method finds the last paranthesis which has no paranthesis in it in its parameter and replace it with its value
		int beginIndex = expression.lastIndexOf('('); // this integer is the beginning of the paranthesis statement
		int endIndex = beginIndex + expression.substring(beginIndex).indexOf(')') + 1; //this integer is the end of the paranthesis statement
		String paranthesis = expression.substring(beginIndex, endIndex); // this string is the paranthesis statement
		paranthesis = calculate(paranthesis);
		expression = expression.substring(0, beginIndex) + paranthesis + expression.substring(endIndex);
		return expression;
	}
	public static String calculate(String paranthesis) { // this method calculates its parameter's value and returns it
		String result = ""; //this is the value of operations in the paranthesis
		int starter = 1, finisher = paranthesis.length() - 2; // these are indexes that are the beginning and the end of the operations
		for(int i = 0; i < paranthesis.length(); i++) {
			if(paranthesis.charAt(i) == '/' || paranthesis.charAt(i) == '*') {
				for(int j = i - 1; j >= 0; j--) {
					if(paranthesis.charAt(j) == '+' || paranthesis.charAt(j) == '-' || paranthesis.charAt(j) == '(') {
						starter = j + 1;
						break;
					}
				}
				for(int j = i + 1; j < paranthesis.length(); j++) {
					if(paranthesis.charAt(j) == '+' || paranthesis.charAt(j) == '-' || paranthesis.charAt(j) == ')' || paranthesis.charAt(j) == '*' || paranthesis.charAt(j) == '/') {
						finisher = j - 1;
						break;
					}
				}
				result = arithmetic(paranthesis.substring(starter, i), paranthesis.substring(i + 1, finisher + 1), paranthesis.charAt(i));
				i = (paranthesis.substring(0, starter) + result).length() - 1;
				paranthesis = paranthesis.substring(0, starter) + result + paranthesis.substring(finisher + 1);
			}
		}
		for(int i = 0; i < paranthesis.length(); i++) {
			if(paranthesis.charAt(i) == '+' || paranthesis.charAt(i) == '-') {
				for(int j = i-1; j >= 0; j--) {
					if(paranthesis.charAt(j) == '(') {
						starter = j + 1;
						break;
					}
				}
				for(int j = i + 1; j < paranthesis.length(); j++) {
					if(paranthesis.charAt(j) == '+' || paranthesis.charAt(j) == '-' || paranthesis.charAt(j) == ')') {
						finisher = j - 1;
						break;
					}
				}
				result = arithmetic(paranthesis.substring(starter, i), paranthesis.substring(i + 1, finisher + 1), paranthesis.charAt(i));
				i = (paranthesis.substring(0, starter) + result).length() - 1;
				paranthesis = paranthesis.substring(0, starter) + result + paranthesis.substring(finisher + 1);
			}
		}
		paranthesis = paranthesis.substring(1, paranthesis.length() - 1);
		return paranthesis;
	}
	public static String arithmetic(String fp, String sp, char operator) { // this method calculates arithmetical operations and returns their value
		String result; // this string is the value of the operation
		double fpd = Double.parseDouble(fp), spd = Double.parseDouble(sp); // these are the double versions of the parameter numbers
		
		if(fp.contains(".") && sp.contains(".")) {
			if(operator == '/') {
				result = Double.toString(fpd / spd);
			} else if (operator == '*') {
				result = Double.toString(fpd * spd);
			} else if (operator == '+') {
				result = Double.toString(fpd + spd);
			} else {
				result = Double.toString(fpd - spd);
			}
		} else if (!fp.contains(".") && !sp.contains(".")) {
			if(operator == '/') {
				result = Integer.toString((int)fpd / (int)spd);
			} else if (operator == '*') {
				result = Integer.toString((int)fpd * (int)spd);
			} else if (operator == '+') {
				result = Integer.toString((int)fpd + (int)spd);
			} else {
				result = Integer.toString((int)fpd - (int)spd);
			}
		} else if (fp.contains(".") && !sp.contains(".")) {
			if(operator == '/') {
				result = Double.toString(fpd / (int)spd);
			} else if (operator == '*') {
				result = Double.toString(fpd * (int)spd);
			} else if (operator == '+') {
				result = Double.toString(fpd + (int)spd);
			} else {
				result = Double.toString(fpd - (int)spd);
			}
		} else {
			if(operator == '/') {
				result = Double.toString((int)fpd / spd);
			} else if (operator == '*') {
				result = Double.toString((int)fpd * spd);
			} else if (operator == '+') {
				result = Double.toString((int)fpd + spd);
			} else {
				result = Double.toString((int)fpd - spd);
			}
		}
		
		return result;
	}
}
	
