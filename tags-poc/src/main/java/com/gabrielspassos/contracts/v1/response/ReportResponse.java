package com.gabrielspassos.contracts.v1.response;

import java.util.Objects;

public class ReportResponse {

    private String externalId1;
    private String content;

    public String getExternalId1() {
        return externalId1;
    }

    public void setExternalId1(String externalId1) {
        this.externalId1 = externalId1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReportResponse that = (ReportResponse) o;
        return Objects.equals(externalId1, that.externalId1) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalId1, content);
    }

    @Override
    public String toString() {
        return "ReportResponse{" +
                "externalId1='" + externalId1 + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
