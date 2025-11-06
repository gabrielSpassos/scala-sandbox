package com.gabrielspassos.contracts.v2.response;

import java.util.Objects;

public class ReportResponse {

    private String content;

    public ReportResponse() {
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
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }

    @Override
    public String toString() {
        return "ReportContract{" +
                "content='" + content + '\'' +
                '}';
    }
}
