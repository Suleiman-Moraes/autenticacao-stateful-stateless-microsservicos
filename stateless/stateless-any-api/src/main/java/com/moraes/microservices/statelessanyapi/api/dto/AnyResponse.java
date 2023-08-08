package com.moraes.microservices.statelessanyapi.api.dto;

public record AnyResponse(String status, Integer code, AuthUserResponse authUser) {

}
