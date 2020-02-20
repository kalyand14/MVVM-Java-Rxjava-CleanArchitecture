package com.android.basics.di;

import com.android.basics.di.internal.BaseScope;
import com.android.basics.di.internal.InstanceContainer;

public class UserComponent implements BaseScope {
    private final InstanceContainer container = new InstanceContainer();

    private UserComponent() {
    }

    public static UserComponent getInstance() {
        if (!ApplicationComponent.getInstance().getContainer().has(UserComponent.class)) {
            ApplicationComponent.getInstance().getContainer().register(UserComponent.class, new UserComponent());
        }
        return ApplicationComponent.getInstance().getContainer().get(UserComponent.class);
    }

    @Override
    public void end() {
        container.end();
    }

    public InstanceContainer getContainer() {
        return container;
    }


}
