/***********************************************************************************************************************
 * Program Name:        Project 1 - Matrix Multiplication Analysis
 * Programmer:          Gabriel Ramos, Caleb Hammond, Logan Wilson, and Matthew Beeler
 * Class:               CSCI - 3230-001
 * Date:                8/19/2022
 * Last updated:        9/27/2022
 * Purpose:             The purpose of this program is to implement three methods of a square
 *                      matrix multiplication. The naive method, divide and conquer method, and the
 *                      Strassen's algorithm method are implemented.
 *                      References:  https://rextester.com/discussion/QVW94237/Strassen-java
 *
 * Implementations:     Matrix multiplication for square matrix using the following algorithms:
 *                          - Naive matrix multiplication
 *                          - Divide-and-conquer matrix multiplication
 *                          - Strassen's algorithm for matrix multiplication
 *
 *                      Above and beyond implementations:
 *                          - multiplication of rectangular matrices using the three algorithms
 *                          - implementation of floating-point square matrix multiplication
 *                          - implementation floating-point rectangular matrix multiplication
 **********************************************************************************************************************/

import java.util.Random;
import java.util.Scanner;

public class MatrixMultiplication {

    public static void main(String[] args) {

        System.out.println("This is a Matrix Multiplication Application!\n");
        System.out.println("  *   *       *    *****  *****   *  *  *\n *  *  *     ***     *    ****    *   *\n*       *   *   *    *    *  ***  *  *  *");

        int ans;
        String answer;
        Scanner myObj = new Scanner(System.in);

        do {
            System.out.println("What kind of matrix multiplication you would like to do?\n\n" +
                    "1 - Square Matrix Multiplication\n" +
                    "2 - Rectangular Matrix Multiplication\n" +
                    "3 - Floating-point Square Matrix Multiplication\n" +
                    "4 - Floating-point Rectangular Matrix Multiplication\n" +
                    "5 - Exit\n" +
                    " Type the number" +
                    " respective to the type of matrix multiplication: ");
            ans = myObj.nextInt();
            int size;
            int row;
            int column1;
            int column2;
            switch (ans) {
                case 1:
                    System.out.println("What is the size of the matrix? ");
                    size = myObj.nextInt();
                    squareMatrixMultiplication(size);
                    System.out.println("Do you want to do another multiplication? (yes/no): ");
                    answer = myObj.next();
                    if (answer.equals("no")) ans = 5;
                    break;
                case 2:
                    System.out.println("What is the number of rows of the first matrix? ");
                    row = myObj.nextInt();
                    System.out.println("What is the number of columns of the first matrix? ");
                    column1 = myObj.nextInt();
                    System.out.println("What is the number of columns of the second matrix? " +
                            "(the number of rows of the second matrix is the same as the number" +
                            "of columns of the first matrix) ");
                    column2 = myObj.nextInt();
                    rectangularMatrixMultiplication(row, column1, column2);
                    System.out.println("Do you want to do another multiplication? (yes/no): ");
                    answer = myObj.next();
                    if (answer.equals("no")) ans = 5;
                    break;
                case 3:
                    System.out.println("What is the size of the matrix? ");
                    size = myObj.nextInt();
                    floatMatrixMultiplication(size);
                    System.out.println("Do you want to do another multiplication? (yes/no): ");
                    answer = myObj.next();
                    if (answer.equals("no")) ans = 5;
                    break;
                case 4:
                    System.out.println("What is the number of rows of the first matrix? ");
                    row = myObj.nextInt();
                    System.out.println("What is the number of columns of the first matrix? ");
                    column1 = myObj.nextInt();
                    System.out.println("What is the number of columns of the second matrix? " +
                            "(the number of rows of the second matrix is the same as the number" +
                            "of columns of the first matrix) ");
                    column2 = myObj.nextInt();
                    floatRectangularMatrixMultiplication(row, column1, column2);
                    System.out.println("Do you want to do another multiplication? (yes/no): ");
                    answer = myObj.next();
                    if (answer.equals("no")) ans = 5;
                    break;
            }
        } while(ans != 5);
    }

    public static void squareMatrixMultiplication(int size) {
        System.out.println("Multiplication between square matrices using three different algorithms\n");
        int[][] A = createMatrix(size);
        int[][] B = createMatrix(size);

        System.out.println("\n\nUsing the naive algorithm:\n");
        int[][] C = naiveMatrixMultiplication(A,B,A.length);
        displayResult(C);

        System.out.println("\n\nUsing the divide-and-conquer algorithm:\n");
        int[][] D = matrixMultiply(A,B);
        displayResult(D);

        System.out.println("\n\nUsing the Strassen' algorithm:\n");
        int[][] E = strassenMatrixMultiplication(A,B, A.length);
        displayResult(E);

    }

    public static void rectangularMatrixMultiplication(int row, int column1row2, int column2) {
        System.out.println("Multiplication between rectangular matrices using three different algorithms\n");
        int[][] A = createRectangularMatrix(row,column1row2);
        int[][] B = createRectangularMatrix(column1row2,column2);

        System.out.println("\n\nUsing the naive algorithm:\n");
        int[][] C = naiveRectangularMatrixMultiplication(A,B);
        displayResult(C);

        // transform the rectangular matrices into square to perform the operations
        A = completeRectangularMatrix(A,B);
        B = completeRectangularMatrix(B,A);

        System.out.println("\n\nUsing the divide-and-conquer algorithm:\n");
        int[][] D = matrixMultiply(A,B);
        D = removeZeros(D, row, column2);
        displayResult(D);


        System.out.println("\n\nUsing the Strassen' algorithm:\n");
        int[][] E = strassenMatrixMultiplication(A, B, A.length);
        E = removeZeros(E, row, column2);
        displayResult(E);
    }

    public static void floatMatrixMultiplication(int size) {
        System.out.println("Multiplication between floating-point square matrices using three different algorithms\n");
        float[][] A = createFloatMatrix(size);
        float[][] B = createFloatMatrix(size);

        System.out.println("\n\nUsing the naive algorithm:\n");
        float[][] C = naiveFloatMatrixMultiplication(A,B, A.length);
        displayFloatResult(C);


        System.out.println("\n\nUsing the divide-and-conquer algorithm:\n");
        float[][] D = floatMatrixMultiply(A,B);
        displayFloatResult(D);



        System.out.println("\n\nUsing the Strassen' algorithm:\n");
        float[][] E = strassenFloatMatrixMultiplication(A,B, A.length);
        displayFloatResult(E);

    }

    public static void floatRectangularMatrixMultiplication(int row, int column1row2, int column2) {
        System.out.println("Multiplication between floating-point rectangular matrices using three different algorithms\n");
        float[][] A = createFloatRectangularMatrix(row, column1row2);
        float[][] B = createFloatRectangularMatrix(column1row2, column2);

        System.out.println("\n\nUsing the naive algorithm:\n");
        float[][] C = naiveFloatRectangularMatrixMultiplication(A,B);
        displayFloatResult(C);

        A = completeFloatRectangularMatrix(A,B);
        B = completeFloatRectangularMatrix(B,A);

        System.out.println("\n\nUsing the divide-and-conquer algorithm:\n");
        float[][] D = floatMatrixMultiply(A,B);
        D = removeFloatZeros(D, row, column2);
        displayFloatResult(D);

        System.out.println("\n\nUsing the Strassen' algorithm:\n");
        float[][] E = strassenFloatMatrixMultiplication(A,B, A.length);
        E = removeFloatZeros(E, row, column2);
        displayFloatResult(E);
    }

    /**
     * Creates a variable size square matrix with random values
     * @param n - the size of matrix to be created
     * @return - returns the square matrix created
     */
    public static int[][] createMatrix(int n){
        Random rand = new Random(); // instance of Random class
        int [][] matrixCreated = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int rand_int1 = rand.nextInt(5);
                matrixCreated[i][j] = rand_int1;
            }
        }
        return matrixCreated;
    }

    /**
     * Creates a variable size rectangular matrix with random values
     * @param row - number of rows in the matrix
     * @param column - number of columns in the matrix
     * @return - returns the rectangular matrix
     */
    public static int[][] createRectangularMatrix(int row, int column) {
        Random rand = new Random(); //instance of Random class
        int[][] matrixCreated = new int[row][column];
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < column; j++) {
                int rand_int1 = rand.nextInt(5);
                matrixCreated[i][j] = rand_int1;
            }
        }
        return matrixCreated;
    }

    /**
     * Method to transform the multiplied square matrix back to its rectangular format, eliminating
     * the extra zeros
     * @param A - matrix A
     * @param row - number of rows of the new matrix
     * @param column - number of columns of the new matrix
     * @return - returns the new matrix
     */
    public static int[][] removeZeros (int[][] A, int row, int column) {
        int[][] newMatrix = new int[row][column];

        for(int i = 0; i < row; i++) {
            for(int j = 0; j < column; j++) {
                newMatrix[i][j] = A[i][j];
            }
        }
        return newMatrix;
    }

    /**
     * Method to transform the multiplied square floating-point matrix back to its rectangular format, eliminating
     * the extra zeros
     * @param A - matrix A
     * @param row - number of rows of the new matrix
     * @param column - number of columns of the new matrix
     * @return - returns the new matrix
     */
    public static float[][] removeFloatZeros (float[][] A, int row, int column) {
        float[][] newMatrix = new float[row][column];

        for(int i = 0; i < row; i++) {
            for(int j = 0; j < column; j++) {
                newMatrix[i][j] = A[i][j];
            }
        }
        return newMatrix;
    }

    /**
     * Creates a variable size floating-point rectangular matrix with random values
     * @param row - number of rows
     * @param column - number of columns
     * @return - returns the rectangular matrix
     */
    public static float[][] createFloatRectangularMatrix(int row, int column) {
        Random rand = new Random(); //instance of Random class
        float[][] matrixCreated = new float[row][column];
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < column; j++) {
                float rand_float1 = rand.nextFloat();
                matrixCreated[i][j] = rand_float1;
            }
        }
        return matrixCreated;
    }

    /**
     * Creates a variable size float square matrix with random values
     * @param n - the size of the matrix
     * @return - returns the floating-point square matrix
     */
    public static float[][] createFloatMatrix(int n) {
        Random rand = new Random();
        float[][] matrixCreated = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                float rand_float1 = rand.nextFloat();
                matrixCreated[i][j] = rand_float1;
            }
        }
        return matrixCreated;
    }

    /**
     * Method to determine if a number is a power of 2 (ex. 2, 4, 8, 16, etc)
     * @param n - the number to analyze
     * @return - returns true if the number is a power of 2
     */
    static boolean isPowerOfTwo(int n)
    {
        return (int)(Math.ceil((Math.log(n) / Math.log(2))))
                == (int)(Math.floor(((Math.log(n) / Math.log(2)))));
    }

    /**
     * This method identifies if the size of the matrix is a multiple of 2
     * if not then it will change the size of the matrix to the next
     * multiple of 2 and complete the available spots with 0s
     * @param matrix - matrix to be completed with 0s
     * @return - returns the new matrix completed with 0s
     */
    public static int[][] createZeroMatrix(int[][] matrix){
        Random rand = new Random(); // instance of Random class

        int size = matrix.length;
        do {
            size++;
        } while (!isPowerOfTwo(size));
        int [][] matrixCreated = new int[size][size];
        if (isPowerOfTwo(matrix.length)) {
            return matrix;
        }

        else {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    matrixCreated[i][j] = matrix[i][j];
                }
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrixCreated[i][size-1] = 0;
                    matrixCreated[j][size-1] = 0;
                }
            }
        }
        return matrixCreated;
    }

    /**
     * Compares the size of two multidimensional matrices and returns a matrix
     * with the size of the max length of the matrices and complete the empty
     * spaces with zeros
     * @param A - matrix A
     * @param B - matrix B
     * @return - returns the new matrix
     */
    public static int[][] completeRectangularMatrix(int[][] A, int[][] B) {
        int size = 0;
        if ( A.length >= B[0].length) {
            size = A.length;
            if (A.length == A[0].length) return A;
            if (A[0].length > A.length) size = A[0].length;
        }
        else {
            size = B.length;
            if (B.length <= B[0].length) size = B[0].length;
        }

        int[][] newMatrix = new int[size][size];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                newMatrix[i][j] = A[i][j];
            }
        }
        return newMatrix;
    }

    /**
     * Compares the size of two multidimensional floating-point matrices and returns a matrix
     * with the size of the max length of the matrices and complete the empty
     * spaces with zeros
     * @param A - matrix A
     * @param B - matrix B
     * @return - returns the new matrix
     */
    public static float[][] completeFloatRectangularMatrix(float[][] A, float[][] B) {
        int size = 0;
        if ( A.length >= B[0].length) {
            size = A.length;
            if (A.length == A[0].length) return A;
            if (A[0].length > A.length) size = A[0].length;
        }
        else {
            size = B.length;
            if (B.length <= B[0].length) size = B[0].length;
        }

        float[][] newMatrix = new float[size][size];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                newMatrix[i][j] = A[i][j];
            }
        }

        return newMatrix;
    }

    /**
     * This method identifies if the size of the matrix is a multiple of 2
     * if not then it will change the size of the matrix to the next
     * multiple of 2 and complete the available spots with 0s
     * @param matrix
     * @return - returns the new matrix completed with 0s
     */
    public static float[][] createZeroFloatMatrix(float[][] matrix) {
        Random rand = new Random();

        int size = matrix.length;
        do {
            size++;
        } while (!isPowerOfTwo(size));

        float[][] matrixCreated = new float[size][size];

        if (isPowerOfTwo(matrix.length)) {
            return matrix;
        }

        else {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    matrixCreated[i][j] = matrix[i][j];
                }
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrixCreated[i][size-1] = 0;
                    matrixCreated[j][size-1] = 0;
                }
            }
        }
        return matrixCreated;
    }

    /**
     * Performs a matrix multiplication between square matrices using the naive algorithm
     * @param A - matrix A to be multiplied
     * @param B - matrix B to be multiplied
     * @param n - size of matrix
     * @return - returns the multiplied matrix
     */
    public static int[][] naiveMatrixMultiplication(int[][] A, int[][] B, int n) {
            int[][] C = new int[n][n];
            for(int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        return C;
    }

    /**
     * Performs a matrix multiplication between rectangular matrices using the naive algorithm
     * @param A - matrix A to be multiplied
     * @param B - matrix B to be multiplied
     * @return - returns the multiplied matrix
     */
    public static int[][] naiveRectangularMatrixMultiplication(int[][] A, int[][] B) {
        int[][] C = new int[A.length][B[0].length];
        for(int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < B.length; k++) {
                    sum += A[i][k] * B[k][j];
                }
                C[i][j] = sum;
            }
        }
        return C;
    }

    /**
     * Performs a matrix multiplication between floating-point square matrices using the naive algorithm
     * @param A - matrix A to be multiplied
     * @param B - matrix B to be multiplied
     * @param n - size of matrix
     * @return - returns the multiplied matrix
     */
    public static float[][] naiveFloatMatrixMultiplication(float[][] A, float[][] B, int n) {
        float[][] C = new float[n][n];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    /**
     * Performs a matrix multiplication between floating-point rectangular matrices using the naive algorithm
     * @param A - matrix A to be multiplied
     * @param B - matrix B to be multiplied
     * @return - returns the multiplied matrix
     */
    public static float[][] naiveFloatRectangularMatrixMultiplication(float[][] A, float[][] B) {
        float[][] C = new float[A.length][B[0].length];
        for(int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                float sum = 0;
                for (int k = 0; k < B.length; k++) {
                    sum += A[i][k] * B[k][j];
                }

                C[i][j] = sum;
            }
        }
        return C;
    }

    /**
     * Performs a matrix multiplication between square matrix using the divide-and-conquer algorithm
     * @param A - matrix A to be multiplied
     * @param B - matrix B to be multiplied
     * @return - returns the multiplied matrix
     */
    public static int[][] matrixMultiply(int[][] A, int[][] B)
    {
        int n = A.length;
        int[][] C = new int[n][n];
        int initialLength = n;

        if (n == 1) {
            C[0][0] = C[0][0] + A[0][0] * B[0][0];

        } else {

            A = createZeroMatrix(A);
            B = createZeroMatrix(B);
            C = createZeroMatrix(C);
            n = A.length;

            int[][] A11 = new int[n / 2][n / 2];
            int[][] A12 = new int[n / 2][n / 2];
            int[][] A21 = new int[n / 2][n / 2];
            int[][] A22 = new int[n / 2][n / 2];

            int[][] B11 = new int[n / 2][n / 2];
            int[][] B12 = new int[n / 2][n / 2];
            int[][] B21 = new int[n / 2][n / 2];
            int[][] B22 = new int[n / 2][n / 2];

            //splits a matrix
            split(A, A11, 0, 0);
            split(A, A12, 0, n / 2);
            split(A, A21, n / 2, 0);
            split(A, A22, n / 2, n / 2);

            //split b into sub-matrices
            split(B, B11, 0, 0);
            split(B, B12, 0, n / 2);
            split(B, B21, n / 2, 0);
            split(B, B22, n / 2, n/2);

            // M1 = A11 * B11
            int[][] M1 = matrixMultiply(A11,B11);
            int[][] M2 = matrixMultiply(A12, B21);

            int[][] M3 = matrixMultiply(A11, B12);
            int[][] M4 = matrixMultiply(A12, B22);

            int[][] M5 = matrixMultiply(A21, B11);
            int[][] M6 = matrixMultiply(A22, B21);

            int[][] M7 = matrixMultiply(A21, B12);
            int[][] M8 = matrixMultiply(A22, B22);

            //sets C11 = M1*M2
            int[][] C11 = addMatrix(M1, M2);
            int[][] C12 = addMatrix(M3, M4);
            int[][] C21 = addMatrix(M5, M6);
            int[][] C22 = addMatrix(M7, M8);

            //joins C11 into C should show the matrix multiplied
            join(C11, C, 0, 0);
            join(C12, C, 0, n/2);
            join(C21, C, n/2, 0);
            join(C22, C, n/2, n/2);

            int[][] D = new int[initialLength][initialLength];
            for (int i = 0; i < initialLength; i++) {
                for(int j = 0; j < initialLength; j++) {
                    D[i][j] = C[i][j];
                }
            }
            return D;
        }
        return C;
    }

    /**
     * Implementation of Strassen's algorithm for square matrix multiplication
     * @param A - first matrix to be multiplied
     * @param B - second matrix to be multiplied
     * @param n - matrix size (has to be a power of 2 - i.e. 2,4,8,...)
     * @return - returns the multiplied A*B  matrix
     */
    public static int[][] strassenMatrixMultiplication (int[][] A, int [][] B, int n) {
        int[][] C = new int[n][n];

        int initialLength = n;

        // base case
        if (n == 1) {
            C[0][0] = A[0][0] * B[0][0];

            return C;
        } else {

            A = createZeroMatrix(A);
            B = createZeroMatrix(B);
            C = createZeroMatrix(C);
            n = A.length;

            //A11, A12, A21, A22
            //A11, A12, A13, A21, A22, A23, A31, A32, A33

            // Step 1: partition of input matrices into 4 n/2 x n/2 submatrices
            int[][] A11 = new int[n / 2][n / 2];
            int[][] A12 = new int[n / 2][n / 2];
            int[][] A21 = new int[n / 2][n / 2];
            int[][] A22 = new int[n / 2][n / 2];
            int[][] B11 = new int[n / 2][n / 2];
            int[][] B12 = new int[n / 2][n / 2];
            int[][] B21 = new int[n / 2][n / 2];
            int[][] B22 = new int[n / 2][n / 2];

            split(A, A11, 0, 0);
            split(A, A12, 0, n / 2);
            split(A, A21, n / 2, 0);
            split(A, A22, n / 2, n / 2);
            split(B, B11, 0, 0);
            split(B, B12, 0, n / 2);
            split(B, B21, n / 2, 0);
            split(B, B22, n / 2, n / 2);

            // Step 2 and 3: creating 7 n/2 x n/2 matrices to hold the products
            // Computing the products P1,...,P7 recursively

            // P1 = (A11 + A22)(B11 + B22)
            int[][] P1 = strassenMatrixMultiplication(addMatrix(A11, A22), addMatrix(B11, B22), n / 2);

            // P2 = (A21 + A22) B11
            int[][] P2 = strassenMatrixMultiplication(addMatrix(A21, A22), B11, n / 2);

            // P3 = A11 (B12 - B22)
            int[][] P3 = strassenMatrixMultiplication(A11, subMatrix(B12, B22), n / 2);

            // P4 = A22 (B21 - B11)
            int[][] P4 = strassenMatrixMultiplication(A22, subMatrix(B21, B11), n / 2);

            // P5 = (A11 + A12) B22
            int[][] P5 = strassenMatrixMultiplication(addMatrix(A11, A12), B22, n / 2);

            // P6 = (A21 - A11) (B11 + B12)
            int[][] P6 = strassenMatrixMultiplication(subMatrix(A21, A11), addMatrix(B11, B12), n / 2);

            // P7 = (A12 - A22) (B21 + B22)
            int[][] P7 = strassenMatrixMultiplication(subMatrix(A12, A22), addMatrix(B21, B22), n / 2);

            // Step 4: update the four submatrices C11, C12, C21, C22 of the result matrix C by
            // adding or subtracting various Pi matrices

            // C11 = P1 + P4 - P5 + P7;
            int[][] C11 = addMatrix(subMatrix(addMatrix(P1, P4), P5), P7);

            // C12 = P3 + P5
            int[][] C12 = addMatrix(P3, P5);

            // C21 = P2 + P4
            int[][] C21 = addMatrix(P2, P4);

            // C22 = P1 - P2 + P3 + P6
            int[][] C22 = addMatrix(subMatrix(addMatrix(P1, P3), P2), P6);

            join(C11, C, 0, 0);
            join(C12, C, 0, n / 2);
            join(C21, C, n / 2, 0);
            join(C22, C, n / 2, n / 2);

            int[][] D = new int[initialLength][initialLength];
            for (int i = 0; i < initialLength; i++) {
                for (int j = 0; j < initialLength; j++) {
                    D[i][j] = C[i][j];
                }
            }
            return D;
        }
    }

    /**
     * Performs a matrix multiplication between floating-point square matrix using the divide-and-conquer algorithm
     * @param A - matrix A to be multiplied
     * @param B - matrix B to be multiplied
     * @return - returns the multiplied matrix
     */
    public static float[][] floatMatrixMultiply(float[][] A, float[][] B)
    {
        int n = A.length;
        float[][] C = new float[n][n];
        int initialLength = n;

        if (n == 1) {
            C[0][0] = C[0][0] + A[0][0] * B[0][0];

        } else {

            A = createZeroFloatMatrix(A);
            B = createZeroFloatMatrix(B);
            C = createZeroFloatMatrix(C);
            n = A.length;

            float[][] A11 = new float[n / 2][n / 2];
            float[][] A12 = new float[n / 2][n / 2];
            float[][] A21 = new float[n / 2][n / 2];
            float[][] A22 = new float[n / 2][n / 2];

            float[][] B11 = new float[n / 2][n / 2];
            float[][] B12 = new float[n / 2][n / 2];
            float[][] B21 = new float[n / 2][n / 2];
            float[][] B22 = new float[n / 2][n / 2];

            //splits a matrix
            splitFloat(A, A11, 0, 0);
            splitFloat(A, A12, 0, n / 2);
            splitFloat(A, A21, n / 2, 0);
            splitFloat(A, A22, n / 2, n / 2);

            //split b into sub-matrices
            splitFloat(B, B11, 0, 0);
            splitFloat(B, B12, 0, n / 2);
            splitFloat(B, B21, n / 2, 0);
            splitFloat(B, B22, n / 2, n/2);

            // M1 = A11 * B11
            float[][] M1 = floatMatrixMultiply(A11,B11);
            float[][] M2 = floatMatrixMultiply(A12, B21);

            float[][] M3 = floatMatrixMultiply(A11, B12);
            float[][] M4 = floatMatrixMultiply(A12, B22);

            float[][] M5 = floatMatrixMultiply(A21, B11);
            float[][] M6 = floatMatrixMultiply(A22, B21);

            float[][] M7 = floatMatrixMultiply(A21, B12);
            float[][] M8 = floatMatrixMultiply(A22, B22);

            //sets C11 = M1*M2
            float[][] C11 = addFloatMatrix(M1, M2);
            float[][] C12 = addFloatMatrix(M3, M4);
            float[][] C21 = addFloatMatrix(M5, M6);
            float[][] C22 = addFloatMatrix(M7, M8);

            //joins C11 into C should show the matrix multiplied
            joinFloat(C11, C, 0, 0);
            joinFloat(C12, C, 0, n/2);
            joinFloat(C21, C, n/2, 0);
            joinFloat(C22, C, n/2, n/2);

            float[][] D = new float[initialLength][initialLength];
            for (int i = 0; i < initialLength; i++) {
                for(int j = 0; j < initialLength; j++) {
                    D[i][j] = C[i][j];
                }
            }
            return D;

        }
        return C;
    }

    /**
     * Performs a matrix multiplication between square floating-point matrices using the Strassen's algorithm
     * @param A - matrix A
     * @param B - matrix B
     * @param n - size of matrix
     * @return - returns the multiplied matrix
     */
    public static float[][] strassenFloatMatrixMultiplication (float[][] A, float [][] B, int n) {
        float[][] C = new float[n][n];

        int initialLength = n;

        // base case
        if (n == 1) {
            C[0][0] = A[0][0]*B[0][0];

            return C;
        }

        else {
            A = createZeroFloatMatrix(A);
            B = createZeroFloatMatrix(B);
            C = createZeroFloatMatrix(C);
            n = A.length;

            //A11, A12, A21, A22
            //A11, A12, A13, A21, A22, A23, A31, A32, A33

            // Step 1: partition of input matrices into 4 n/2 x n/2 submatrices
            float[][] A11 = new float[n / 2][n / 2];
            float[][] A12 = new float[n / 2][n / 2];
            float[][] A21 = new float[n / 2][n / 2];
            float[][] A22 = new float[n / 2][n / 2];
            float[][] B11 = new float[n / 2][n / 2];
            float[][] B12 = new float[n / 2][n / 2];
            float[][] B21 = new float[n / 2][n / 2];
            float[][] B22 = new float[n / 2][n / 2];

            splitFloat(A, A11, 0, 0);
            splitFloat(A, A12, 0, n / 2);
            splitFloat(A, A21, n / 2, 0);
            splitFloat(A, A22, n / 2, n / 2);
            splitFloat(B, B11, 0, 0);
            splitFloat(B, B12, 0, n / 2);
            splitFloat(B, B21, n / 2, 0);
            splitFloat(B, B22, n / 2, n / 2);

            // Step 2 and 3: creating 7 n/2 x n/2 matrices to hold the products
            // Computing the products P1,...,P7 recursively

            // P1 = (A11 + A22)(B11 + B22)
            float[][] P1 = strassenFloatMatrixMultiplication(addFloatMatrix(A11,A22), addFloatMatrix(B11, B22), n / 2);

            // P2 = (A21 + A22) B11
            float[][] P2 = strassenFloatMatrixMultiplication(addFloatMatrix(A21,A22), B11, n / 2);

            // P3 = A11 (B12 - B22)
            float[][] P3 = strassenFloatMatrixMultiplication(A11, subFloatMatrix(B12,B22), n / 2);

            // P4 = A22 (B21 - B11)
            float[][] P4 = strassenFloatMatrixMultiplication(A22, subFloatMatrix(B21, B11), n / 2);

            // P5 = (A11 + A12) B22
            float[][] P5 = strassenFloatMatrixMultiplication(addFloatMatrix(A11, A12), B22, n / 2);

            // P6 = (A21 - A11) (B11 + B12)
            float[][] P6 = strassenFloatMatrixMultiplication(subFloatMatrix(A21, A11), addFloatMatrix(B11, B12), n / 2);

            // P7 = (A12 - A22) (B21 + B22)
            float[][] P7 = strassenFloatMatrixMultiplication(subFloatMatrix(A12, A22), addFloatMatrix(B21, B22), n / 2);

            // Step 4: update the four submatrices C11, C12, C21, C22 of the result matrix C by
            // adding or subtracting various Pi matrices

            // C11 = P1 + P4 - P5 + P7;
            float[][] C11 =  addFloatMatrix(subFloatMatrix(addFloatMatrix(P1, P4), P5), P7);

            // C12 = P3 + P5
            float[][]C12 =  addFloatMatrix(P3, P5);

            // C21 = P2 + P4
            float[][]C21 = addFloatMatrix(P2, P4);

            // C22 = P1 - P2 + P3 + P6
            float[][] C22 = addFloatMatrix(subFloatMatrix(addFloatMatrix(P1, P3), P2), P6);

            joinFloat(C11, C, 0, 0);
            joinFloat(C12, C, 0, n / 2);
            joinFloat(C21, C, n / 2, 0);
            joinFloat(C22, C, n / 2, n / 2);

            float[][] D = new float[initialLength][initialLength];
            for (int i = 0; i < initialLength; i++) {
                for(int j = 0; j < initialLength; j++) {
                    D[i][j] = C[i][j];
                }
            }
            return D;
        }
    }

    /**
     * Method to add 2 matrices
     * @param A - matrix to be added
     * @param B - matrix to be added
     * @return - returns the matrix A+B
     */
    public static int[][] addMatrix (int[][] A, int[][] B) {
        int[][] C = new int[A.length][A.length];

        for (int i = 0; i < A.length; i ++){
            for (int j = 0; j < A.length; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    /**
     * Adds 2 floating-point matrices
     * @param A - matrix A
     * @param B - matrix B
     * @return - returns matrix A+B
     */
    public static float[][] addFloatMatrix (float[][] A, float[][] B) {
        float[][] C = new float[A.length][A.length];

        for (int i = 0; i < A.length; i ++){
            for (int j = 0; j < A.length; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    /**
     * Method to subtract 2 matrices
     * @param A - matrix to be added
     * @param B - matrix to be added
     * @return - returns the matrix A+B
     */
    public static int[][] subMatrix (int[][] A, int[][] B) {
        int[][] C = new int[A.length][A.length];

        for (int i = 0; i < A.length; i ++){
            for (int j = 0; j < A.length; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    /**
     * Subtracts 2 floating-point matrices
     * @param A - matrix A
     * @param B - matrix B
     * @return - returns matrix A-B
     */
    public static float[][] subFloatMatrix (float[][] A, float[][] B) {
        float[][] C = new float[A.length][A.length];

        for (int i = 0; i < A.length; i ++){
            for (int j = 0; j < A.length; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }


    /**
     * Method to split Matrix A (parent) into Matrix B (child)
     * @param A - parent matrix
     * @param B - child matrix
     * @param iB - matrix row
     * @param jB - matrix column
     */
    public static void split(int[][] A, int[][] B, int iB, int jB) {
        // Outer loop for rows
        for (int i1 = 0, i2 = iB; i1 < B.length; i1++, i2++)
            // Inner loop for columns
            for (int j1 = 0, j2 = jB; j1 < B.length; j1++, j2++)
                B[i1][j1] = A[i2][j2];
    }

    /**
     * Splits floating-point matrix A (parent) into matrix B (child)
     * @param A - parent matrix
     * @param B - child matrix
     * @param iB - matrix row
     * @param jB - matrix column
     */
    public static void splitFloat(float[][] A, float[][] B, int iB, int jB) {

        // Outer loop for rows
        for (int i1 = 0, i2 = iB; i1 < B.length; i1++, i2++)
            // Inner loop for columns
            for (int j1 = 0, j2 = jB; j1 < B.length; j1++, j2++)
                B[i1][j1] = A[i2][j2];
    }

    /**
     * Method to join Matrix A (child) into Matrix B (parent)
     * @param A - child matrix
     * @param B - parent matrix
     * @param iB - matrix row
     * @param jB - matrix column
     */
    public static void join(int[][] A, int[][] B, int iB, int jB) {
        // Outer loop for rows
        for (int i1 = 0, i2 = iB; i1 < A.length; i1++, i2++)
            // Inner loop for columns
            for (int j1 = 0, j2 = jB; j1 < A.length; j1++, j2++)
                B[i2][j2] = A[i1][j1];
    }

    /**
     * Method to join floating-point Matrix A (child) into Matrix B (parent)
     * @param A - child matrix
     * @param B - parent matrix
     * @param iB - matrix row
     * @param jB - matrix column
     */
    public static void joinFloat(float[][] A, float[][] B, int iB, int jB) {

        // Outer loop for rows
        for (int i1 = 0, i2 = iB; i1 < A.length; i1++, i2++)

            // Inner loop for columns
            for (int j1 = 0, j2 = jB; j1 < A.length;
                 j1++, j2++)

                B[i2][j2] = A[i1][j1];
    }

    /**
     * Method to display a matrix in a user-friendly format
     * @param result - matrix to be displayed
     */
    public static void displayResult(int[][] result) {
        System.out.println("Product of two matrices is: ");
        for(int[] row : result) {
            for (int column : row) {
                System.out.print(column + "\t\t");
            }
            System.out.println();
        }
    }

    /**
     * Method to display a floating-point matrix in a user-friendly format
     * @param result - matrix to be displayed
     */
    public static void displayFloatResult(float[][] result) {
        System.out.println("Product of two matrices is: ");
        for(float[] row : result) {
            for (float column : row) {
                System.out.print(column + "\t\t");
            }
            System.out.println();
        }
    }
}
