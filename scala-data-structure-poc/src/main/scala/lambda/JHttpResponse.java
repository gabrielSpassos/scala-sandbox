package lambda;

import java.util.Objects;

public class JHttpResponse {
    
    private int statusCode;
    private String responseBody;
    
    public JHttpResponse() {}
    
    public JHttpResponse(int statusCode, String responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    
    public String getResponseBody() {
        return responseBody;
    }
    
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JHttpResponse that = (JHttpResponse) o;
        return statusCode == that.statusCode && Objects.equals(responseBody, that.responseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, responseBody);
    }

    @Override
    public String toString() {
        return "JHttpResponse{" +
                "statusCode=" + statusCode +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
