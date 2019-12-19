package ru.asu.cleo.service.dto;

public class MainResponseDTO<T> {

    private T response;

    public MainResponseDTO(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
