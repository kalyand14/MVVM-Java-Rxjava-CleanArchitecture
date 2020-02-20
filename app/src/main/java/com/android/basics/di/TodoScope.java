package com.android.basics.di;

import com.android.basics.di.internal.BaseScope;
import com.android.basics.di.internal.InstanceContainer;

public class TodoScope implements BaseScope {
    private final InstanceContainer container = new InstanceContainer();

    private TodoScope() {
    }

    public static TodoScope getInstance() {
        if (!UserComponent.getInstance().getContainer().has(TodoScope.class)) {
            UserComponent.getInstance().getContainer().register(TodoScope.class, new TodoScope());
        }
        return UserComponent.getInstance().getContainer().get(TodoScope.class);
    }

    public InstanceContainer getContainer() {
        return container;
    }

    @Override
    public void end() {
        container.end();
    }
}
