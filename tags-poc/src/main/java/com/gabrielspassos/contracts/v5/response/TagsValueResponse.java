package com.gabrielspassos.contracts.v5.response;

import java.util.Objects;

public class TagsValueResponse {

    private String value;
    private String createdAt;
    private String updatedAt;

    public TagsValueResponse() {
    }

    public TagsValueResponse(String value, String createdAt, String updatedAt) {
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        TagsValueResponse that = (TagsValueResponse) o;
        return Objects.equals(value, that.value) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "TagsValueResponse{" +
                "value='" + value + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
