package edu.ser222.m01_02;

/**
 * An implementation of the Matrix ADT. Provides four basic operations over an immutable type.
 * 
 * Last updated 7/31/2021.
 * 
 * @author Pin-Yang Wang, Ruben Acuna
 * @version 1.0
 */

public class CompletedMatrix implements Matrix {

    private final int[][] data;
   
    public CompletedMatrix(int[][] matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Input matrix can't be null.");
        }

        int rows = matrix.length;
        int cols = rows > 0 ? matrix[0].length : 0;

        data = new int[rows][cols];

        // check each row's number of elements
        for (int r = 0; r < rows; r++) {
            if (matrix[r].length != cols) {
                throw new IllegalArgumentException("Matrix rows have inconsistent length.");
            }
            // ensure matrix's immutability
            for (int c = 0; c < cols; c++) {
                data[r][c] = matrix[r][c];
            }
        }
    }

    @Override
    public int getElement(int x, int y) {
        if (x < 0 || y < 0 || x >= getRows() || y >= getColumns()) {
            throw new IndexOutOfBoundsException("Invalid indices.");
        }
        return data[x][y];
    }

    @Override
    public int getRows() {
        return data.length;
    }

    @Override
    public int getColumns() {
        return (data.length == 0) ? 0 : data[0].length;
    }

    @Override
    public Matrix scale(int scalar) {
        int rows_total = getRows();
        int cols_total = getColumns();
        int[][] result = new int[rows_total][cols_total];

        for (int r = 0; r < rows_total; r++)
            for (int c = 0; c < cols_total; c++)
                result[r][c] = data[r][c] * scalar;

        return new CompletedMatrix(result);
    }

    @Override
    public Matrix plus(Matrix other) {
        if (other == null || getRows() != other.getRows() || getColumns() != other.getColumns()) {
            throw new IllegalArgumentException("Matrix dimensions do not match.");
        }

        int rows_total = getRows();
        int cols_total = getColumns();
        int[][] result = new int[rows_total][cols_total];

        for (int r = 0; r < rows_total; r++)
            for (int c = 0; c < cols_total; c++)
                result[r][c] = data[r][c] + other.getElement(r, c);

        return new CompletedMatrix(result);
    }

    @Override
    public Matrix minus(Matrix other) {
        if (other == null || getRows() != other.getRows() || getColumns() != other.getColumns()) {
            throw new IllegalArgumentException("Matrix dimensions do not match.");
        }

        int rows_total = getRows();
        int cols_total = getColumns();
        int[][] result = new int[rows_total][cols_total];

        for (int r = 0; r < rows_total; r++)
            for (int c = 0; c < cols_total; c++)
                result[r][c] = data[r][c] - other.getElement(r, c);

        return new CompletedMatrix(result);
    }

    @Override
    public Matrix multiply(Matrix other) {
        if (other == null || getColumns() != other.getRows()) {
            throw new IllegalArgumentException("Invalid matrix sizes for multiplication.");
        }

        int rows_result = getRows();
        int cols_result = other.getColumns();
        int col1 = getColumns();

        int[][] result = new int[rows_result][cols_result];

        for (int r = 0; r < rows_result; r++) {
            for (int c = 0; c < cols_result; c++) {
                int sum = 0;
                for (int i = 0; i < col1; i++) {
                    sum += data[r][i] * other.getElement(i, c);
                }
                result[r][c] = sum;
            }
        }

        return new CompletedMatrix(result);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Matrix)) return false;

        Matrix m_other = (Matrix) other;

        if (getRows() != m_other.getRows() || getColumns() != m_other.getColumns()) return false;

        for (int r = 0; r < getRows(); r++)
            for (int c = 0; c < getColumns(); c++)
                if (data[r][c] != m_other.getElement(r, c))
                    return false;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int r = 0; r < getRows(); r++) {
            for (int c = 0; c < getColumns(); c++) {
                sb.append(data[r][c]);
                if (c < getColumns() - 1) sb.append(" ");
            }
            if (r < getRows() - 1) sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Entry point for matrix testing.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //These tests show sample usage of the matrix, and some basic ideas for testing. They are not comprehensive.

        int[][] data1 = new int[0][0];
        int[][] data2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] data3 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        int[][] data4 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        int[][] data5 = {{1, 4, 7}, {2, 5, 8}};

        Matrix m1 = new CompletedMatrix(data1);
        Matrix m2 = new CompletedMatrix(data2);
        Matrix m3 = new CompletedMatrix(data3);
        Matrix m4 = new CompletedMatrix(data4);
        Matrix m5 = new CompletedMatrix(data5);

        System.out.println("m1 --> Rows: " + m1.getRows() + " Columns: " + m1.getColumns());
        System.out.println("m2 --> Rows: " + m2.getRows() + " Columns: " + m2.getColumns());
        System.out.println("m3 --> Rows: " + m3.getRows() + " Columns: " + m3.getColumns());

        //check for reference issues
        System.out.println("m2 -->\n" + m2);
        data2[1][1] = 101;
        System.out.println("m2 -->\n" + m2);

        //test equals
        System.out.println("m2==null: " + m2.equals(null));             //false
        System.out.println("m3==\"MATRIX\": " + m2.equals("MATRIX"));   //false
        System.out.println("m2==m1: " + m2.equals(m1));                 //false
        System.out.println("m2==m2: " + m2.equals(m2));                 //true
        System.out.println("m2==m3: " + m2.equals(m3));                 //false
        System.out.println("m3==m4: " + m3.equals(m4));                 //true

        //test operations (valid)
        System.out.println("m1 + m1:\n" + m1.plus(m1));
        System.out.println("m1 + m1:\n" + m1.plus(m1));
        System.out.println("2 * m2:\n" + m2.scale(2));
        System.out.println("m2 + m3:\n" + m2.plus(m3));
        System.out.println("m2 - m3:\n" + m2.minus(m3));
        System.out.println("3 * m5:\n" + m5.scale(3));

        //not tested... multiply(). you know what to do.

        //test operations (invalid)
        //System.out.println("m1 + m2" + m1.plus(m2));
        //System.out.println("m1 + m5" + m1.plus(m5));
        //System.out.println("m1 - m2" + m1.minus(m2));
    }
}