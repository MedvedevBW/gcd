package com.bwsw.test.gcd.entities;

public final class GcdCalculationResponse {
    private long requestId;
    private long result;
    private String errorMessage;

    public GcdCalculationResponse(long requestId, long result, String errorMessage) {
        this.requestId = requestId;
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public long getResult() {
        return result;
    }

    public long getRequestId() {
        return requestId;
    }

    public String toString() {
        return "GcdCalculationResponse( requestId: " + requestId +
                ", result: " + result + ", errorMessage: " + errorMessage + ")";
    }
}
