package com.gabrielspassos.contracts.v1.response;

public interface BaseApiResponse<T> {

    T getBody();
    int getStatusCode();
    String getMessage();
    
}
