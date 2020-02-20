package com.android.basics.di;

import com.android.basics.core.navigation.BundleFactory;
import com.android.basics.core.navigation.IntentFactory;
import com.android.basics.core.navigation.Navigator;
import com.android.basics.di.internal.InstanceContainer;
import com.android.basics.domain.repository.TodoRepository;
import com.android.basics.domain.repository.UserRepository;

public class ApplicationComponent {

    private final InstanceContainer container = new InstanceContainer();

    private ApplicationModule applicationModule;

    private PresentationModule presentationModule;

    public ApplicationModule getApplicationModule() {
        return applicationModule;
    }

    public void setApplicationModule(ApplicationModule applicationModule) {
        this.applicationModule = applicationModule;
    }

    public PresentationModule getPresentationModule() {
        return presentationModule;
    }

    public void setPresentationModule(PresentationModule presentationModule) {
        this.presentationModule = presentationModule;
    }

    public IntentFactory intentFactory() {
        if (!container.has(IntentFactory.class)) {
            container.register(IntentFactory.class, applicationModule.provideIntentFactory());
        }
        return container.get(IntentFactory.class);
    }

    public BundleFactory bundleFactory() {
        if (!container.has(BundleFactory.class)) {
            container.register(BundleFactory.class, applicationModule.provideBundleFactory());
        }
        return container.get(BundleFactory.class);
    }

    public Navigator navigator() {
        return applicationModule.provideNavigator(intentFactory(), bundleFactory());
    }

    public UserRepository userRepository() {
        if (!container.has(UserRepository.class)) {
            container.register(UserRepository.class, applicationModule.provideUserRepository(applicationModule.provideDaoExecutor(), applicationModule.provideUserDao(applicationModule.provideTodoDatabase(applicationModule.getApplication())), applicationModule.provideUserMapper()));
        }
        return container.get(UserRepository.class);
    }

    public TodoRepository todoRepository() {
        if (!container.has(TodoRepository.class)) {
            container.register(TodoRepository.class, applicationModule.provideTodoRepository(applicationModule.provideDaoExecutor(), applicationModule.provideTodoDao(applicationModule.provideTodoDatabase(applicationModule.getApplication())), applicationModule.provideTodoListMapper(), applicationModule.provideTodoMapper()));
        }
        return container.get(TodoRepository.class);
    }

    public InstanceContainer getContainer() {
        return container;
    }


}
