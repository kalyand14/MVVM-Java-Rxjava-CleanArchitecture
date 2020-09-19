package com.android.basics.core.presentation;

public class AuthResource<T> {

    private final AuthResourceState state;
    private final T data;
    private final String message;


    public AuthResource(AuthResourceState state, T data, String message) {
        this.state = state;
        this.data = data;
        this.message = message;
    }

    public AuthResourceState getState() {
        return state;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static <T> AuthResource<T> authenticated(T data) {
        return new AuthResource<>(AuthResourceState.AUTHENTICATED, data, null);
    }

    public static <T> AuthResource<T> error(String message) {
        return new AuthResource<>(AuthResourceState.ERROR, null, message);
    }

    public static <T> AuthResource<T> loading() {
        return new AuthResource<>(AuthResourceState.LOADING, null, null);
    }

    public static <T> AuthResource<T> logout() {
        return new AuthResource<>(AuthResourceState.UN_AUTHENTICATED, null, null);
    }

    public enum AuthResourceState {
        AUTHENTICATED, LOADING, ERROR, UN_AUTHENTICATED
    }
}
