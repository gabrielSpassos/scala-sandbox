package com.gabrielspassos.contracts.v1.response;

import java.util.Objects;

public class UserResponse {
    
    private String externalId1;
    private String externalId2;
    private String status;

    public UserResponse() {
    }

    public String getExternalId1() {
        return externalId1;
    }

    public void setExternalId1(String externalId1) {
        this.externalId1 = externalId1;
    }

    public String getExternalId2() {
        return externalId2;
    }

    public void setExternalId2(String externalId2) {
        this.externalId2 = externalId2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(externalId1, that.externalId1)
                && Objects.equals(externalId2, that.externalId2)
                && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalId1, externalId2, status);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                ", externalId1='" + externalId1 + '\'' +
                ", externalId2='" + externalId2 + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
