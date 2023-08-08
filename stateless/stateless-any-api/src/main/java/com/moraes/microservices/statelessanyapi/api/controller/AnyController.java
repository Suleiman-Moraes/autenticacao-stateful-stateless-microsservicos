package com.moraes.microservices.statelessanyapi.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moraes.microservices.statelessanyapi.api.dto.AnyResponse;
import com.moraes.microservices.statelessanyapi.api.service.AnyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/resource")
public class AnyController {

    private final AnyService service;

    @GetMapping
    public ResponseEntity<AnyResponse> getResource(@RequestHeader String accessToken) {
        return ResponseEntity.ok(service.getData(accessToken));
    }
}
