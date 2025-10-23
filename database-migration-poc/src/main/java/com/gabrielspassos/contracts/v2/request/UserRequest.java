package com.gabrielspassos.contracts.v2.request;

import java.util.Objects;

public class UserRequest {

    private String cpf;

    public UserRequest() {
    }

    public UserRequest(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRequest that = (UserRequest) o;
        return Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }

    @Override
    public String toString() {
        var maskedCPF = null != cpf && cpf.length() >= 4 ? cpf.substring(4) : "****";
        
        return "UserRequest{" +
                "cpf='" + maskedCPF + '\'' +
                '}';
    }
}
