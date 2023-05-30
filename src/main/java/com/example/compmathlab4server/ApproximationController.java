package com.example.compmathlab4server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class ApproximationController {
    private final ApproximationServiceImpl approximationService;

    @Autowired
    public ApproximationController(ApproximationServiceImpl approximationService) {
        this.approximationService = approximationService;
    }

    @PostMapping("/approximate")
    public ResponseEntity<ApproximationResponseDto[]> approximate(@RequestBody ApproximationRequestDto approximationRequestDto) {
        return ResponseEntity.ok(approximationService.approximate(approximationRequestDto));
    }

//    @ExceptionHandler
//    public ResponseEntity<String> handleExceptions(IllegalArgumentException exception) {
//        return ResponseEntity.badRequest().body(exception.getMessage());
//    }
}
