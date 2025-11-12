package com.gabrielspassos.contracts.v3.response;

import java.util.Objects;

public class TagsResponse {
    
    private String id;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TagsResponse that = (TagsResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "TagsResponse{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
