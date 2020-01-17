package ru.asu.cleo.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MainListResponse<T> {

    @JsonProperty("response")
    private List<T> response;

    public MainListResponse(List<T> response) {
        this.response = response;
    }

    public List<T> getResponse() {
        return response;
    }

    public void setResponse(List<T> response) {
        this.response = response;
    }
}
