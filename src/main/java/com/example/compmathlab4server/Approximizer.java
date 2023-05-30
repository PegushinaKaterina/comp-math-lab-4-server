package com.example.compmathlab4server;

import java.util.*;
import java.util.function.Function;

public class Approximizer {
    private static MethodsResult getLinearApproximation (Point[] points) {
        double[] coefficientsFn = round(getCoefficientsLinearApproximation(points));
        String fn = coefficientsFn[0] + " + " +
                coefficientsFn[1] + " * x";
        Function<Double, Double> f = (x) -> (coefficientsFn[0] +
                coefficientsFn[1] * x);
        return new MethodsResult(fn, fn,  getStandardDeviation(f, points));
    }
    private static double[] getCoefficientsLinearApproximation (Point[] points) {
        Double sumX1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + point.getX(), Double::sum);
        Double sumX2 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 2), Double::sum);
        Double sumY1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + point.getY(), Double::sum);
        Double sumX1Y1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + point.getX() * point.getY(), Double::sum);
        double[][] matrix = new double[][]{{points.length, sumX1, sumY1},
                {sumX1, sumX2, sumX1Y1}};

        System.out.println(sumX1 + " " + sumX2 + " " + sumY1 + " " + sumX1Y1);
        return GaussSolver.solveGauss(matrix);
    }
    private static MethodsResult getSquareApproximation (Point[] points) {
        Double sumX1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + point.getX(), Double::sum);
        Double sumX2 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 2), Double::sum);
        Double sumX3 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 3), Double::sum);
        Double sumX4 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 4), Double::sum);
        Double sumY1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + point.getY(), Double::sum);
        Double sumX1Y1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + point.getX() * point.getY(), Double::sum);
        Double sumX2Y1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 2) * point.getY(), Double::sum);
        double[][] matrix = new double[][]{{points.length, sumX1, sumX2, sumY1},
                {sumX1, sumX2, sumX3, sumX1Y1},
                {sumX2, sumX3, sumX4, sumX2Y1}};
        double[] coefficientsFn = round(GaussSolver.solveGauss(matrix));
        String fn = coefficientsFn[0] + " + " +
                coefficientsFn[1] + " * x + " +
                coefficientsFn[2] + " * x^2";
        Function<Double, Double> f = (x) -> (coefficientsFn[0] +
                coefficientsFn[1] * x +
                coefficientsFn[2] * Math.pow(x, 2));
        return new MethodsResult(fn, fn, getStandardDeviation(f, points));
    }

    private static MethodsResult getCubicApproximation (Point[] points) {
        Double sumX1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + point.getX(), Double::sum);
        Double sumX2 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 2), Double::sum);
        Double sumX3 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 3), Double::sum);
        Double sumX4 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 4), Double::sum);
        Double sumX5 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 5), Double::sum);
        Double sumX6 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 6), Double::sum);
        Double sumY1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + point.getY(), Double::sum);
        Double sumX1Y1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + point.getX() * point.getY(), Double::sum);
        Double sumX2Y1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 2) * point.getY(), Double::sum);
        Double sumX3Y1 = Arrays.stream(points).reduce(0D, (sum, point) -> sum + Math.pow(point.getX(), 3) * point.getY(), Double::sum);

        double[][] matrix = new double[][]{{points.length, sumX1, sumX2, sumX3, sumY1},
                {sumX1, sumX2, sumX3, sumX4, sumX1Y1},
                {sumX2, sumX3, sumX4, sumX5, sumX2Y1},
                {sumX3, sumX4, sumX5, sumX6, sumX3Y1}};
        double[] coefficientsFn = round(GaussSolver.solveGauss(matrix));
        String fn = coefficientsFn[0] + " + " +
                coefficientsFn[1] + " * x + " +
                coefficientsFn[2] + " * x^2 + " +
                coefficientsFn[3] + " * x^3";
        Function<Double, Double> f = (x) -> (coefficientsFn[0] +
                coefficientsFn[1] * x +
                coefficientsFn[2] * Math.pow(x, 2) +
                coefficientsFn[3] * Math.pow(x, 3));
        return new MethodsResult(fn, fn, getStandardDeviation(f, points));
    }
    private static MethodsResult getExpApproximation (Point[] points) {
        if (Arrays.stream(points).anyMatch(point -> point.getY() <= 0)) {
            return new MethodsResult("0", "", Double.MAX_VALUE);
        }
        Point[] pointsChanged = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            pointsChanged[i] = new Point(point.getX(), Math.log(point.getY()));
        }

        double[] coefficientsFn = round(getCoefficientsLinearApproximation(pointsChanged));
        String fn = Math.round(Math.exp(coefficientsFn[0])*10000)/10000D + " * " +
                "exp(" + coefficientsFn[1] + " * x)";
//        Function<Double, Double> f = (x) -> (coefficientsFn[0] +
//                coefficientsFn[1] * x);
        Function<Double, Double> f = x -> Math.exp(coefficientsFn[0]) * Math.exp(coefficientsFn[1] * x);
        return new MethodsResult(fn, fn, getStandardDeviation(f, points));
    }

    private static MethodsResult getLogApproximation (Point[] points) {
        if (Arrays.stream(points).anyMatch(point -> point.getX() <= 0)) {
            return new MethodsResult("0", "", Double.MAX_VALUE);
        }
        Point[] pointsChanged = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            pointsChanged[i] = new Point(Math.log(point.getX()), point.getY());
        }
        double[] coefficientsFn = round(getCoefficientsLinearApproximation(pointsChanged));
        String fn = coefficientsFn[0] + " + " +
                coefficientsFn[1] + " * ln(x)";
        Function<Double, Double> f = (x) -> (coefficientsFn[0] +
                coefficientsFn[1] * Math.log(x));
        return new MethodsResult(fn, fn, getStandardDeviation(f, points));
    }
    private static MethodsResult getPowerApproximation (Point[] points) {
        if (Arrays.stream(points).anyMatch(point -> point.getX() <= 0 || point.getY() <= 0)) {
            return new MethodsResult("0", "", Double.MAX_VALUE);
        }
        Point[] pointsChanged = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            pointsChanged[i] =  new Point(Math.log(point.getX()), Math.log(point.getY()));
        }
        double[] coefficientsFn = round(getCoefficientsLinearApproximation(pointsChanged));
        String fn = Math.round(Math.exp(coefficientsFn[0])*10000)/10000D + " * nthRoot(x, 10000)^" +
                Math.round(coefficientsFn[1]*10000);
        String fnText = Math.round(Math.exp(coefficientsFn[0])*10000)/10000D + " * x^" +
                Math.round(coefficientsFn[1]*10000)/10000D;
//        Function<Double, Double> f = (x) -> (coefficientsFn[0] +
//            x * coefficientsFn[1]);
        Function<Double, Double> f = x -> Math.exp(coefficientsFn[0])  * Math.pow(x, coefficientsFn[1]);

        return new MethodsResult(fn, fnText, getStandardDeviation(f, points));
    }

    private static double getStandardDeviation(Function<Double, Double> fn, Point[] points) {
        Function<Point, Double> getElemSum = point -> Math.pow(fn.apply(point.getX()) - point.getY(), 2); ;
        double s = Arrays.stream(points).reduce(0D, (sum, point) -> sum + getElemSum.apply(point), Double::sum);
        return Math.pow(s / points.length,  0.5);
    }

    public static ApproximationResponseDto[] getApproximation (Point[] points) {
        List<MethodsResult> results = new ArrayList<>();
        results.add(getLinearApproximation(points));
        results.add(getSquareApproximation(points));
        results.add(getCubicApproximation(points));
        results.add(getExpApproximation(points));
        results.add(getLogApproximation(points));
        results.add(getPowerApproximation(points));
        MethodsResult bestResult = results.stream().min(Comparator.comparingDouble(res -> res.standardDeviation)).get();
        Function<MethodsResult, String> getMassage = res -> res == bestResult ? "Это наилучшая аппроксимация" : "0".equals(res.fn) ? "Невозможно построить аппроксимацию данного типа" : "";
        ApproximationResponseDto[] approximationResponseDtos = new ApproximationResponseDto[results.size()];
        for (int i = 0; i < results.size(); i++) {
            MethodsResult result = results.get(i);
            approximationResponseDtos[i] =  new ApproximationResponseDto(result.fn,
                    result.fnText,
                    Math.round(result.standardDeviation*10000)/10000D,
                    getMassage.apply(result));
        }
        return approximationResponseDtos;
    }
    private record MethodsResult(String fn, String fnText, Double standardDeviation) {}

    private static double[] round(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.round(arr[i] * 10000) / 10000D;
        }
        return arr;
    }
}
