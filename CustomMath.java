/** @author  <Shubham Thakkar> shubhamthakkar14@gmail.com
 * @version <10/18/13>
 * 
 * Primes class: The sqrt method was made by Faruk Akgul and posted online on the website: 
 * http://faruk.akgul.org/blog/javas-missing-algorithm-biginteger-sqrt/
 */

import java.math.BigInteger;
import java.util.ArrayList;

public class CustomMath {

	// Checks whether or not a number is prime or not by checking if its
	// divisible by every number up to its square root
	// pre: number!=null
	// post: returns a boolean of whether or not a number is prime
	public static boolean basicIsPrime(BigInteger number) {
		if (number == null)
			throw new IllegalArgumentException(
					"Your number cannot be equal to null.");
		if (!valid(number))
			return false;
		else {
			BigInteger current = new BigInteger("2");
			BigInteger root = squareRoot(number);
			while (current.compareTo(root) <= 0) {
				if (number.divideAndRemainder(current)[1]
						.equals(BigInteger.ZERO))
					return false;
				current = current.add(BigInteger.ONE);
			}
			return true;
		}
	}

	// Determines if a number is a possible prime number using Fermat's Little
	// Theorem
	// pre: number!=null number>1
	// post: returns a boolean is a possible prime
	public static boolean fermatsLittleTheorem(BigInteger number) {
		if (number == null || !valid(number))
			throw new IllegalArgumentException("That is not a valid input.");
		BigInteger current = BigInteger.ONE;
		while (current.compareTo(number) <= 0) {
			if (!(power(current, number).subtract(current)).remainder(number)
					.equals(BigInteger.ZERO))
				return false;
			current = current.add(BigInteger.ONE);
		}
		return true;
	}

	// This determines the value of one number ^ other number
	// pre: number!=null, power!=null, power>-1
	// post: returns a bigInteger with base^power
	public static BigInteger power(BigInteger base, BigInteger power) {
		if (base == null || power == null
				|| power.compareTo(BigInteger.ZERO) < 0)
			throw new IllegalArgumentException("That is not a valid input.");
		if (power.equals(BigInteger.ZERO))
			return BigInteger.ONE;
		BigInteger index = BigInteger.ZERO;
		BigInteger count = base;
		while (index.compareTo(power.subtract(BigInteger.ONE)) < 0) {
			count = count.multiply(base);
			index = index.add(BigInteger.ONE);
		}
		return count;
	}

	// This will return all the numbers that are primes below your number and
	// your number if its a prime.
	// pre: number!= null
	// post: returns an arrayList of prime numbers below the given limit.
	public static ArrayList<BigInteger> primesBelow(BigInteger number) {
		if (number == null)
			throw new IllegalArgumentException(
					"The number or the power cannot be equal to null.");
		else {
			ArrayList<BigInteger> list = new ArrayList<BigInteger>();
			BigInteger current = new BigInteger("2");
			while (current.compareTo(number) <= 0) {
				if (basicIsPrime(current))
					list.add(current);
				current = current.add(BigInteger.ONE);
			}
			return list;
		}
	}

	// This determines all of the factors of your number
	// pre: number!=null, numfactors <=2,147,483,647, number>1
	// post: returns an arrayList of factors for that number
	public static ArrayList<BigInteger> factors(BigInteger number) {
		if (number == null || !valid(number))
			throw new IllegalArgumentException("That is not a valid input.");
		ArrayList<BigInteger> list = new ArrayList<BigInteger>();
		BigInteger current = BigInteger.ONE;
		int i = 0;
		while (current.compareTo(squareRoot(number)) <= 0) {
			if (number.divideAndRemainder(current)[1].equals(BigInteger.ZERO)) {
				list.add(i, current);
				list.add(i + 1, number.divideAndRemainder(current)[0]);
				i++;
			}
			current = current.add(BigInteger.ONE);
		}
		return list;
	}

	// This determines the factorial of a number.
	// pre: number>-1, number!=null
	// post: returns a bigInteger with the value of the factorial
	public static BigInteger factorial(BigInteger number) {
		if (number.compareTo(BigInteger.ZERO) < 0 || number == null)
			throw new IllegalArgumentException("That is not a valid input.");
		if (number.compareTo(BigInteger.ZERO) == 0)
			return BigInteger.ONE;
		else {
			BigInteger limit = number;
			while (limit.compareTo(BigInteger.ONE) > 0) {
				limit = limit.subtract(BigInteger.ONE);
				number = number.multiply(limit);
			}
			return number;
		}
	}

	// This determines all of the prime factors of your number
	// pre: number!=null, number>1
	// post: returns an arrayList of factors for that number
	public static ArrayList<BigInteger> primeFactors(BigInteger number) {
		if (number == null || !valid(number))
			throw new IllegalArgumentException("That is not a valid input.");
		ArrayList<BigInteger> list = new ArrayList<BigInteger>();
		BigInteger tempNumber = number;
		while (!basicIsPrime(tempNumber)) {
			BigInteger i = new BigInteger("2");
			boolean repeat = true;
			while (i.compareTo(squareRoot(number)) <= 0 && repeat) {
				if (tempNumber.divideAndRemainder(i)[1] == BigInteger.ZERO) {
					list.add(i);
					repeat = false;
					tempNumber = tempNumber.divide(i);
				}
				i = i.add(BigInteger.ONE);
			}
		}
		list.add(tempNumber);
		return list;
	}

	// This determines amountOfNumbers fibonnaci numbers by calculating each one
	// pre: amountOfNumbers>-1, you cannot ask for more than 2,147,483,647
	// numbers
	// post: returns an arrayList of fibonnaci numbers
	public static BigInteger[] basicFibonacci(int amountOfNumbers) {
		if (amountOfNumbers < 0)
			throw new IllegalArgumentException(
					"Your number mustbe greater than 0");
		if (amountOfNumbers == 0)
			return new BigInteger[0];
		BigInteger firstValue = BigInteger.ZERO;
		BigInteger secondValue = BigInteger.ONE;
		BigInteger[] array = new BigInteger[amountOfNumbers];
		array[0] = firstValue;
		for (int i = 1; i < amountOfNumbers; i++) {
			array[i] = secondValue;
			BigInteger temp = secondValue;
			secondValue = secondValue.add(firstValue);
			firstValue = temp;
		}
		return array;
	}

	// This determines the nth fibonnaci number. If your number is a decimal, it
	// will round down
	// It is a zero based indexing.
	// pre: value<~8.988466e307
	// post: it returns a rounded double with the nth fibonnaci number
	public static double nthFibonacci(double n) {
		if (n < 0)
			throw new IllegalArgumentException(
					"Your number mustbe greater than 0");
		return Math
				.round((Math.pow((1 + Math.sqrt(5)) / 2, Math.floor(n)) - Math
						.pow((1 - Math.sqrt(5)) / 2, Math.floor(n)))
						/ Math.sqrt(5));
	}

	// This determines the nth row in pascalls triangle
	// pre: row>0, row!=null
	// post: returns an array of BigIntegers with the row of the triangle
	public static ArrayList<BigInteger> pascallRow(BigInteger row) {
		if (row.compareTo(BigInteger.ZERO) < 0 || row == null)
			throw new IllegalArgumentException("Invalid input");
		ArrayList<BigInteger> list = new ArrayList<BigInteger>();
		BigInteger i = BigInteger.ZERO;
		while (i.compareTo(row) <= 0) {
			list.add(specificPascall(row, i));
			i = i.add(BigInteger.ONE);
		}
		return list;
	}

	// This determines the pascalls triangle up the the power row.
	// pre: power>0, power!=null
	// post: returns an 2D array of BigIntegers.
	public static ArrayList<ArrayList<BigInteger>> PascallsTriangle(
			BigInteger power) {
		if (power.compareTo(BigInteger.ZERO) < 0 || power == null)
			throw new IllegalArgumentException("Invalid input");
		ArrayList<ArrayList<BigInteger>> list = new ArrayList<ArrayList<BigInteger>>();
		BigInteger i = BigInteger.ZERO;
		while (i.compareTo(power.add(BigInteger.ONE)) < 0) {
			list.add(pascallRow(i));
			i = i.add(BigInteger.ONE);
		}
		return list;
	}

	// This method determines a specific bionomial expansion of the nth row and
	// kth term (n choose k)
	// It is 0 based indexing.
	// pre: n>-1, k>-1
	// post: returns a bigInteger with a specific value.
	public static BigInteger specificPascall(BigInteger n, BigInteger k) {
		if (n.compareTo(BigInteger.ZERO) < 0
				|| k.compareTo(BigInteger.ZERO) < 0)
			throw new IllegalArgumentException(
					"Row or Term cannot be less than 0");
		return CustomMath.factorial(n).divide(
				CustomMath.factorial(k).multiply(
						CustomMath.factorial(n.subtract(k))));
	}

	// This method returns a list of numbers generated when you apply the
	// Collatz conjecture operations.
	// pre: n>0, n!=null
	// post: returns an arrayList of BigIntegers of the numbers in the steps of
	// the Collatz conjecture.
	public static ArrayList<BigInteger> CollatzList(BigInteger n) {
		if (n.compareTo(BigInteger.ZERO) <= 0 || n == null)
			throw new IllegalArgumentException(
					"Your number must be greater than 0");
		else {
			ArrayList<BigInteger> list = new ArrayList<BigInteger>();
			while (n.compareTo(BigInteger.ONE) > 0) {
				list.add(n);
				if (n.divideAndRemainder(BigInteger.valueOf(2))[1]
						.compareTo(BigInteger.ZERO) == 0)
					n = n.divide(BigInteger.valueOf(2));
				else
					n = n.multiply(BigInteger.valueOf(3)).add(BigInteger.ONE);
			}
			list.add(n);
			return list;
		}
	}

	// This method returns the number of steps required to get to 1 applying the
	// Collatz operations.
	// pre: n>0, n!=null
	// post: returns an arrayList of BigIntegers of the numbers in the steps of
	// the Collatz conjecture.
	public static int CollatzNumber(BigInteger n) {
		if (n.compareTo(BigInteger.ZERO) <= 0 || n == null)
			throw new IllegalArgumentException(
					"Your number must be greater than 0");
		else
			return CollatzList(n).size() - 1;
	}

	// This method is to find the squareroot of a BigInteger. This is not my
	// method.
	// pre: n>0, n!=null
	// post: returns an arrayList of BigIntegers of the numbers in the steps of
	// the Collatz conjecture.
	public static BigInteger squareRoot(BigInteger n) {
		if (n.compareTo(BigInteger.ZERO) < 0 || n == null)
			throw new IllegalArgumentException(
					"Your number must be greater than 0");
		else {
			BigInteger a = BigInteger.ONE;
			BigInteger b = new BigInteger(n.shiftRight(5)
					.add(new BigInteger("8")).toString());
			while (b.compareTo(a) >= 0) {
				BigInteger mid = new BigInteger(a.add(b).shiftRight(1)
						.toString());
				if (mid.multiply(mid).compareTo(n) > 0)
					b = mid.subtract(BigInteger.ONE);
				else
					a = mid.add(BigInteger.ONE);
			}
			return a.subtract(BigInteger.ONE);
		}
	}

	// This method runs the initial values into the ackermann function and spits
	// out the result.
	// pre: n>0, m>0, n!=null, m!=null
	// post: returns an arrayList of BigIntegers of the numbers in the steps of
	// the Collatz conjecture.
	public static BigInteger ackermannFunction(BigInteger m, BigInteger n) {
		if (m.compareTo(BigInteger.ZERO) < 0
				|| n.compareTo(BigInteger.ZERO) < 0 || m == null || n == null)
			throw new IllegalArgumentException("Invalid input");
		if (m.compareTo(BigInteger.ZERO) == 0)
			return n.add(BigInteger.ONE);
		else if (m.compareTo(BigInteger.ZERO) > 0
				&& n.compareTo(BigInteger.ZERO) == 0)
			return ackermannFunction(m.subtract(BigInteger.ONE), BigInteger.ONE);
		else
			return ackermannFunction(m.subtract(BigInteger.ONE),
					ackermannFunction(m, n.subtract(BigInteger.ONE)));
	}

	// This method is to determine if your BigInteger is a valid number.
	private static boolean valid(BigInteger number) {
		return number.compareTo(BigInteger.valueOf(2)) >= 0;
	}

}