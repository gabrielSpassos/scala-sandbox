package com.gabrielspassos.contracts.v1.response;

public class BankResponse {

    private String code;
    private String name;

    public BankResponse() {
        this.code = null;
        this.name = null;
    }

    public BankResponse(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
