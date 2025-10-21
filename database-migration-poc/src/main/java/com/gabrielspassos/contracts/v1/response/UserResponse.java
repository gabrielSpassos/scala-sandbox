package com.gabrielspassos.contracts.v1.response;

import java.util.Objects;

public class UserResponse {

    private String id;
    private String userId;
    private String cpf;
    private String externalId1;
    private String externalId2;
    private String status;
    private String createdAt;
    private String updatedAt;

    public UserResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(id, that.id)
                && Objects.equals(userId, that.userId)
                && Objects.equals(cpf, that.cpf)
                && Objects.equals(externalId1, that.externalId1)
                && Objects.equals(externalId2, that.externalId2)
                && Objects.equals(status, that.status)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, cpf, externalId1, externalId2, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", cpf='" + cpf + '\'' +
                ", externalId1='" + externalId1 + '\'' +
                ", externalId2='" + externalId2 + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
