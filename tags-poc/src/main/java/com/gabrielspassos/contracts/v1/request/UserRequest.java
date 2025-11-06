package com.gabrielspassos.contracts.v1.request;

import java.util.Objects;

public class UserRequest {

    private String externalId1;
    private String externalId2;

    public UserRequest() {
    }

    public UserRequest(String externalId1, String externalId2) {
        this.externalId1 = externalId1;
        this.externalId2 = externalId2;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRequest that = (UserRequest) o;
        return Objects.equals(externalId1, that.externalId1) && Objects.equals(externalId2, that.externalId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalId1, externalId2);
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "externalId1='" + externalId1 + '\'' +
                ", externalId2='" + externalId2 + '\'' +
                '}';
    }
}
