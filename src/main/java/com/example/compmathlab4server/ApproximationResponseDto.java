package com.example.compmathlab4server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApproximationResponseDto {
    private String fn;
    private String fnText;
    private Double standardDeviation;
    private String message;
}
