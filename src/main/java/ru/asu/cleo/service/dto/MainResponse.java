package ru.asu.cleo.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MainResponse<T> {

    @JsonProperty("response")
    private T response;

    public MainResponse(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
