package com.android.basics.core.di;

public interface InstanceScope extends BaseScope {

    <T> T register(Class<T> clazz, T instance);

    <T> T get(Class<T> clazz);

    <T> boolean has(Class<T> clazz);

    <T> void destroy(Class<T> clazz);

}
