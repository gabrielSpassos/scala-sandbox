package com.gabrielspassos.contracts.v2.response;

import java.util.Objects;

public class TagsResponse {
    
    private String id;
    private String enabledAt;
    private String disabledAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnabledAt() {
        return enabledAt;
    }

    public void setEnabledAt(String enabledAt) {
        this.enabledAt = enabledAt;
    }

    public String getDisabledAt() {
        return disabledAt;
    }

    public void setDisabledAt(String disabledAt) {
        this.disabledAt = disabledAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TagsResponse that = (TagsResponse) o;
        return Objects.equals(id, that.id)
                && Objects.equals(enabledAt, that.enabledAt)
                && Objects.equals(disabledAt, that.disabledAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enabledAt, disabledAt);
    }

    @Override
    public String toString() {
        return "TagsResponse{" +
                "id='" + id + '\'' +
                ", enabledAt='" + enabledAt + '\'' +
                ", disabledAt='" + disabledAt + '\'' +
                '}';
    }
}
