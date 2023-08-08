package com.moraes.microservices.statefulanyapi.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moraes.microservices.statefulanyapi.api.dto.AnyResponse;
import com.moraes.microservices.statefulanyapi.api.service.AnyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/resource")
public class AnyController {

    private final AnyService service;

    @GetMapping
    public ResponseEntity<AnyResponse> getData(@RequestHeader String accessToken) {
        return ResponseEntity.ok(service.getData(accessToken));
    }
}
