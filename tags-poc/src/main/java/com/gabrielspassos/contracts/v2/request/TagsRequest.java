package com.gabrielspassos.contracts.v2.request;

import java.util.Objects;

public class TagsRequest {

    private Boolean isEnabled;

    public TagsRequest() {
    }

    public TagsRequest(Boolean isEnabled) {
        this.isEnabled = isEnabled;
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
        TagsRequest that = (TagsRequest) o;
        return Objects.equals(isEnabled, that.isEnabled);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isEnabled);
    }

    @Override
    public String toString() {
        return "TagsRequest{" +
                "isEnabled=" + isEnabled +
                '}';
    }
}
