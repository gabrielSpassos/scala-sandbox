package com.gabrielspassos.contracts.v1.response;

import java.util.Objects;

public class TagsResponse {
    
    private String id;
    private Boolean isEnabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TagsResponse that = (TagsResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(isEnabled, that.isEnabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isEnabled);
    }

    @Override
    public String toString() {
        return "TagsResponse{" +
                "id='" + id + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
