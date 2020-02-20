package com.android.basics.core.presentation;

public class Resource<T> {

    private final ResourceState state;
    private final T data;
    private final String message;


    public Resource(ResourceState state, T data, String message) {
        this.state = state;
        this.data = data;
        this.message = message;
    }

    public ResourceState getState() {
        return state;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(ResourceState.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String message) {
        return new Resource<>(ResourceState.ERROR, null, message);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(ResourceState.LOADING, null, null);
    }
}
