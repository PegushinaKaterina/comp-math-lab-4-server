package com.example.compmathlab4server;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ApproximationServiceImpl implements ApproximationService{
    @Override
    public ApproximationResponseDto[] approximate(ApproximationRequestDto approximationRequestDto) {
        Point[] points = new Point[approximationRequestDto.getPoints().length];
        for (int i = 0; i < approximationRequestDto.getPoints().length; i++) {
            Double[] point = approximationRequestDto.getPoints()[i];
            points[i] = new Point(point[0], point[1]);
        }
        return Approximizer.getApproximation(points);
    }
}
