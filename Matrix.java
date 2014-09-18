/** @author  <Shubham Thakkar> shubhamthakkar14@gmail.com
 * @version <10/18/13>
 * 
 * Primes class: The sqrt method was made by Faruk Akgul and posted online on the website: 
 * http://faruk.akgul.org/blog/javas-missing-algorithm-biginteger-sqrt/
 */

import java.text.DecimalFormat;

/*TOO?
 * spans
 * cross product
 * is orthogonal
 * http://stackoverflow.com/questions/19940740/gaussian-elimination-java
 * */

public class Matrix {

	double[][] container;

	public Matrix(int row, int col, double val) {
		if (row <= 0 || col <= 0)
			throw new IllegalArgumentException();
		container = new double[row][col];
		if (val != 0)
			for (int r = 0; r < row; r++)
				for (int c = 0; c < col; c++)
					container[r][c] = val;
	}

	public Matrix(double[][] input) {
		if (input == null || input.length == 0 || input[0].length == 0)
			throw new IllegalArgumentException();
		container = new double[input.length][input[0].length];
		for (int r = 0; r < input.length; r++)
			for (int c = 0; c < input[r].length; c++)
				container[r][c] = input[r][c];
	}

	public void changeElement(int row, int col, double val) {
		if (row < 0 || col < 0 || row >= container.length
				|| col >= container[0].length)
			throw new IllegalArgumentException();
		container[row][col] = val;
	}

	public double getVal(int row, int col) {
		if (row < 0 || col < 0 || row >= container.length
				|| col >= container[0].length)
			throw new IllegalArgumentException();
		return container[row][col];
	}

	public int numRows() {
		return container.length;
	}

	public int numCols() {
		return container[0].length;
	}

	public Matrix add(Matrix other) {
		if (other == null || other.numRows() != numRows()
				|| other.numCols() != numCols())
			throw new IllegalArgumentException("Violation of precondition: "
					+ "MathMatrix rightHandSide constructor");
		return additionOrSubtraction(other, 1);
	}

	public Matrix subtract(Matrix rightMatrix) {
		if (rightMatrix == null || rightMatrix.numRows() != numRows()
				|| rightMatrix.numCols() != numCols())
			throw new IllegalArgumentException("Violation of precondition: "
					+ "MathMatrix rightHandSide constructor");
		return additionOrSubtraction(rightMatrix, -1);
	}

	private Matrix additionOrSubtraction(Matrix other, int offset) {
		Matrix toReturn = new Matrix(container.length, container[0].length, 0);
		for (int r = 0; r < container.length; r++)
			for (int c = 0; c < container[r].length; c++)
				toReturn.changeElement(r, c,
						container[r][c] + offset * other.getVal(r, c));
		return toReturn;
	}

	public void scale(int factor) {
		if (factor == 0)
			container = new double[container.length][container.length];
		else
			for (int r = 0; r < container.length; r++)
				for (int c = 0; c < container[r].length; c++)
					container[r][c] = factor * container[r][c];
	}

	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass())
			return false;
		Matrix toCompare = (Matrix) other;
		if (container[0].length != toCompare.numCols()
				|| container.length != toCompare.numRows())
			return false;
		for (int r = 0; r < container.length; r++)
			for (int c = 0; c < container[0].length; c++)
				if (toCompare.getVal(r, c) != container[r][c])
					return false;
		return true;
	}

	//

	public Matrix transpose() {
		Matrix m = new Matrix(container[0].length, container.length, 0);
		for (int r = 0; r < container.length; r++)
			for (int c = 0; c < container[0].length; c++)
				m.changeElement(c, r, container[r][c]);
		return m;
	}

	public Matrix multiplication(Matrix rightHandSide) {
		if (rightHandSide == null
				|| container.length != rightHandSide.numCols())
			throw new IllegalArgumentException();
		Matrix m = new Matrix(container.length, rightHandSide.numCols(), 0);
		int count = 0;
		for (int r = 0; r < m.numRows(); r++)
			for (int c = 0; c < m.numCols(); c++) {
				for (int i = 0; i < this.numCols(); i++)
					count += this.getVal(r, i) * rightHandSide.getVal(i, c);
				m.changeElement(r, c, count);
				count = 0;
			}
		return m;
	}

	public boolean isUpperTriangle() {
		for (int r = 0; r < container.length; r++)
			for (int c = 0; c < container[0].length; c++)
				if ((c < r && container[r][c] != 0)
						|| (c >= r && container[r][c] == 0))
					return false;
		return true;
	}

	public boolean isLowerTriangle() {
		for (int r = 0; r < container.length; r++)
			for (int c = 0; c < container[0].length; c++)
				if ((c > r && container[r][c] != 0)
						|| (c <= r && container[r][c] == 0))
					return false;
		return true;
	}

	public boolean isDiagonalMatrix() {
		for (int r = 0; r < container.length; r++)
			for (int c = 0; c < container[0].length; c++)
				if ((c != r && container[r][c] != 0)
						|| (c == r && container[r][c] == 0))
					return false;
		return true;
	}

	public boolean isSquareMatrix() {
		return container.length == container[0].length;
	}

	public static Matrix identityMatrix(int size) {
		if (size <= 0)
			throw new IllegalArgumentException();
		Matrix m = new Matrix(size, size, 0);
		for (int r = 0; r < size; r++)
			m.changeElement(r, r, 1);
		return m;
	}

	public String toString() {
		DecimalFormat format = new DecimalFormat();
		int[] values = new int[container[0].length];
		for (int c = 0; c < container[0].length; c++)
			for (int r = 0; r < container.length; r++) {
				container[r][c] += 0.0;
				if (format.format(container[r][c]).length() > values[c])
					values[c] = format.format(container[r][c]).length();
			}
		StringBuffer buffer = new StringBuffer();
		for (int r = 0; r < container.length; r++) {
			buffer.append("[");
			for (int c = 0; c < container[0].length; c++) {
				int numTimes;
				if (c == 0)
					numTimes = values[c]
							- format.format(container[r][c]).length();
				else
					numTimes = values[c]
							- format.format(container[r][c]).length() + 1;
				for (int i = 0; i < numTimes; i++)
					buffer.append(" ");
				buffer.append(format.format(container[r][c]));
			}
			buffer.append("]\n");
		}
		return buffer.toString();
	}

	public double determinant() {
		if (container.length != container[0].length)
			throw new IllegalArgumentException();
		if (container.length == 2)
			return container[0][0] * container[1][1] - container[0][1]
					* container[1][0];
		double count = 0;
		for (int r = 1; r <= container.length; r++)
			count += Math.pow(-1, r + 1) * container[r - 1][0]
					* minor(r, 1).determinant();
		return count;
	}

	public Matrix minor(int numRow, int numCol) {
		if (numRow < 1 || numCol < 1)
			throw new IllegalArgumentException();
		double[][] newOne = new double[container.length - 1][container.length - 1];
		int currentC;
		int currentR = 0;
		for (int newOneR = 0; newOneR < newOne.length; newOneR++) {
			currentC = 0;
			for (int newOneC = 0; newOneC < newOne.length; newOneC++) {
				if (currentR == numRow - 1)
					currentR++;
				if (currentC == numCol - 1)
					currentC++;
				newOne[newOneR][newOneC] = container[currentR][currentC];
				currentC++;
			}
			currentR++;
		}
		return new Matrix(newOne);
	}

	public Matrix RREF() {
		Matrix m = new Matrix(container);
		int q = 0;
		for (int i = 0; i < m.numRows() && q < m.numCols(); i++) {
			boolean canBeReduced = m.getVal(i, q) != 0;
			if (!canBeReduced) {
				int k = i + 1;
				while (k < m.numRows() && !canBeReduced) {
					if (m.getVal(k, q) != 0) {
						m.swap(k, i);
						canBeReduced = true;
					}
					k++;
				}
			}
			if (canBeReduced) {
				double pivot = m.getVal(i, q);
				for (int c = 0; c < m.numCols(); c++)
					m.changeElement(i, c, m.getVal(i, c) * 1 / pivot);
				for (int r = 0; r < m.numRows(); r++)
					if (r != i) {
						double val = m.getVal(r, q);
						for (int c = 0; c < m.numCols(); c++)
							m.changeElement(r, c,
									m.getVal(r, c) - val * m.getVal(i, c));
					}
			} else
				i--;
			q++;
		}
		return m;
	}

	public void swap(int row1, int row2) {
		if (row1 < 0 || row2 < 0)
			throw new IllegalArgumentException();
		double[] temp = new double[container[0].length];
		for (int i = 0; i < container[0].length; i++)
			temp[i] = container[row1][i];
		for (int i = 0; i < container[0].length; i++)
			container[row1][i] = container[row2][i];
		for (int i = 0; i < container[0].length; i++)
			container[row2][i] = temp[i];
	}

	/*
	 * public Matrix REF() { Matrix m = new Matrix(container); int q = 0; for
	 * (int i = 0; i < m.numRows() && q < m.numCols(); i++) { boolean
	 * canBeReduced = m.getVal(i, q) != 0; if (!canBeReduced) { int k = i + 1;
	 * while (k < m.numRows() && !canBeReduced) { if (m.getVal(k, q) != 0) {
	 * m.swap(k, i); canBeReduced = true; } k++; } } if (canBeReduced) { double
	 * pivot = m.getVal(i, q); for (int r = 0; r < m.numRows(); r++) if (r != i)
	 * { double val = m.getVal(r, q); for (int c = r; c < m.numCols(); c++)
	 * m.changeElement(r, c, m.getVal(r, c) - val / pivot m.getVal(i, c)); } }
	 * else i--; q++; } return m; }
	 */

	public String solution() {
		DecimalFormat format = new DecimalFormat();
		StringBuffer sb = new StringBuffer();
		Matrix m = RREF();
		int c = 0;
		int r = 0;
		int numFreeVars = 0;

		for (int row = 0; row < container.length; row++) {
			if (m.getVal(row, c) == 0) {
				sb.append("x" + c + " is free.\n");
				numFreeVars++;
				row--;
			} else {
				boolean hasOne = false;
				sb.append("x" + c + " = ");
				for (int i = c + 1; i < container[0].length
						&& m.getVal(row, i) != 0; i++) {
					if (-1 * m.getVal(row, i) > 0 && hasOne)
						sb.append("+");
					sb.append(format.format(-1 * m.getVal(row, i)) + "x" + i
							+ " ");
					hasOne = true;
				}
				if (!hasOne)
					sb.append("0");
				sb.append("\n");
			}
			c++;
			r = row;
		}
		for (int i = c; i < container[r].length; i++) {
			sb.append("x" + i + " is free.");
			numFreeVars++;
		}
		sb.append("\nNumber of free Variables: " + numFreeVars
				+ "\nThis system has ");
		if (numFreeVars == 0)
			sb.append("one solution.\n");
		else
			sb.append("infinately many solutions.\n");
		return sb.toString();
	}

	public Matrix inverse() {
		if (!isInvertible())
			throw new IllegalArgumentException();
		Matrix m = new Matrix(container.length, container.length * 2, 0);
		for (int r = 0; r < container.length; r++)
			for (int c = 0; c < container[r].length; c++)
				m.changeElement(r, c, container[r][c]);
		int row = 0;
		for (int col = container.length; col < m.numCols(); col++) {
			m.changeElement(row, col, 1);
			row++;
		}
		int otherR = 0;
		int otherC = 0;
		Matrix temp = m.RREF();
		m = new Matrix(container.length, container.length, 0);
		for (int c = container.length; c < temp.numCols(); c++) {
			for (int r = 0; r < container[0].length; r++) {
				m.changeElement(otherR, otherC, temp.getVal(r, c));
				otherR++;
			}
			otherC++;
			otherR = 0;
		}
		return m;
	}

	public boolean isInvertible() {
		return container[0].length == container.length && determinant() != 0;
	}

}
