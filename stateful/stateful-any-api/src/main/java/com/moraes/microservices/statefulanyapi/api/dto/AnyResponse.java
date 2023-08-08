package com.moraes.microservices.statefulanyapi.api.dto;

public record AnyResponse(String status, Integer code, AuthUserResponse authUserResponse) {

}
