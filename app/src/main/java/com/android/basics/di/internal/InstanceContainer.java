package com.android.basics.di.internal;

import java.util.HashMap;
import java.util.Map;

public class InstanceContainer implements InstanceScope {

    private final Map<Class, Object> instances = new HashMap<>();

    @Override
    public <T> T register(Class<T> clazz, T instance) {
        instances.put(clazz, instance);
        return instance;
    }

    @Override
    public <T> T get(Class<T> clazz) {
        return (T) instances.get(clazz);
    }

    @Override
    public <T> boolean has(Class<T> clazz) {
        return instances.containsKey(clazz);
    }

    @Override
    public <T> void destroy(Class<T> clazz) {
        if (has(clazz)) {
            instances.remove(clazz);
        }
    }

    @Override
    public void end() {
        for (Map.Entry<Class, Object> entry : instances.entrySet()) {
            if (entry.getValue() instanceof ScopeObserver) {
                ((ScopeObserver) entry.getValue()).onScopeEnded();
            }
        }
        instances.clear();
    }
}
