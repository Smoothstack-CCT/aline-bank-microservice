package com.aline.bankmicroservice.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    /**
     * Health Check endpoint for microservice
     * @return 200 Response if service is healthy, otherwise 404 is returned
     */
    @GetMapping("/health")
    @ApiOperation("Health Endpoint")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Service is healthy."),
            @ApiResponse(code= 404, message = "Service is unavailable")
    })
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
