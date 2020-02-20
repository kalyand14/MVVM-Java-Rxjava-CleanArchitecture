package com.android.basics.di;

import com.android.basics.di.internal.BaseScope;
import com.android.basics.di.internal.InstanceContainer;

public class TodoComponent implements BaseScope {
    private final InstanceContainer container = new InstanceContainer();

    private TodoComponent() {
    }

    public static TodoComponent getInstance() {
        if (!UserComponent.getInstance().getContainer().has(TodoComponent.class)) {
            UserComponent.getInstance().getContainer().register(TodoComponent.class, new TodoComponent());
        }
        return UserComponent.getInstance().getContainer().get(TodoComponent.class);
    }

    public InstanceContainer getContainer() {
        return container;
    }

    @Override
    public void end() {
        container.end();
    }
}
