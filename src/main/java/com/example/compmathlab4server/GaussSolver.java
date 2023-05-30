package com.example.compmathlab4server;

public class GaussSolver {
    public static double[] solveGauss(double[][] matrix) {
        int n = matrix.length;
        triangulateMatrix(matrix);

        for (int i = 1; i < n; i++) {
            for (int jBeforeI = 0; jBeforeI < i; jBeforeI++) {
                double i_head = matrix[jBeforeI][i];
                for (int k = i; k < n + 1; k++) {
                    matrix[jBeforeI][k] = matrix[jBeforeI][k] - matrix[i][k] * i_head;
                }
            }
        }

        double[] answer = new double[n];
        for (int i = 0; i < matrix.length; i++) {
            answer[i] = matrix[i][n];
        }
        return answer;
    }

    private static void triangulateMatrix(double[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            if (matrix[i][i] == 0) {
                swapLines(matrix, i);
            }

            double mainDiagonalElement = matrix[i][i];

            for (int jInILine = i; jInILine < n + 1; jInILine++) {
                matrix[i][jInILine] = matrix[i][jInILine] / mainDiagonalElement;
            }

            for (int jAfterI = i + 1; jAfterI < n; jAfterI++) {
                double jLineHead = matrix[jAfterI][i];

                for (int k = i; k < n + 1; k++) {
                    matrix[jAfterI][k] = matrix[jAfterI][k] - jLineHead * matrix[i][k];
                }
            }

        }
    }

    private static void swapLines(double[][] matrix, int i) {
        for (int j = i + 1; j < matrix.length; j++) {
            if (matrix[j][i] != 0) {
                double[] jMatrix = matrix[j];
                matrix[j] = matrix[i];
                matrix[i] = jMatrix;

                return;
            }
        }
    }
}
