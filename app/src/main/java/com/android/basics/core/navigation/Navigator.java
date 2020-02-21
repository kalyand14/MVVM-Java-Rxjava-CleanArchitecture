package com.android.basics.core.navigation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class Navigator implements BaseNavigator {

    private WeakReference<AppCompatActivity> contextWeakRef;

    IntentFactory intentFactory;
    BundleFactory bundleFactory;

    public Navigator(IntentFactory intentFactory, BundleFactory bundleFactory) {
        this.intentFactory = intentFactory;
        this.bundleFactory = bundleFactory;
    }

    public void setActivity(AppCompatActivity activity) {
        this.contextWeakRef = new WeakReference<>(activity);
    }

    public void clear() {
        contextWeakRef.clear();
        contextWeakRef = null;
    }

    @Override
    public void launchActivity(Intent intent) {
        contextWeakRef.get().startActivity(intent);
    }

    @Override
    public void finishActivity() {
        contextWeakRef.get().finish();
    }


    @Override
    public void closeApplication() {
        //TODO: need to change it
        contextWeakRef.get().finishAffinity();
    }

    @Override
    public Intent createIntent(Class<? extends AppCompatActivity> clazz) {
        return intentFactory.create(contextWeakRef.get(), clazz);
    }

    @Override
    public Bundle createBundle() {
        return bundleFactory.create();
    }

}
