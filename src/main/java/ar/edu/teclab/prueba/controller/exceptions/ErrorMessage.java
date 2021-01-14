package ar.edu.teclab.prueba.controller.exceptions;

import java.util.Objects;

public class ErrorMessage {
    private int statusCode;
    private String path;
    private String method;
    private String message;

    public static ErrorMessageBuilder anErrorMessage() {
        return new ErrorMessageBuilder();
    }
    public ErrorMessage(ErrorMessageBuilder builder) {
        this.statusCode = builder.statusCode;
        this.path = builder.path;
        this.method = builder.method;
        this.message = builder.message;
    }

    private ErrorMessage() {
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ErrorMessageBuilder {
        private int statusCode;
        private String path;
        private String method;
        private String message;

        public ErrorMessageBuilder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ErrorMessageBuilder setPath(String path) {
            this.path = path;
            return this;
        }

        public ErrorMessageBuilder setMethod(String method) {
            this.method = method;
            return this;
        }

        public ErrorMessageBuilder  setMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorMessage build() {
            return new ErrorMessage(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorMessage)) return false;
        ErrorMessage that = (ErrorMessage) o;
        return getStatusCode() == that.getStatusCode() && getPath().equals(that.getPath()) && getMethod().equals(that.getMethod()) && getMessage()
                .equals(that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatusCode(), getPath(), getMethod(), getMessage());
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "statusCode=" + statusCode +
                ", path='" + path + '\'' +
                ", method='" + method + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
