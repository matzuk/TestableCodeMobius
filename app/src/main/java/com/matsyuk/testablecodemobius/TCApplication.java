package com.matsyuk.testablecodemobius;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.matsyuk.testablecodemobius.di.application.AppComponent;
import com.matsyuk.testablecodemobius.di.application.AppModule;
import com.matsyuk.testablecodemobius.di.application.DaggerAppComponent;

/**
 * @author e.matsyuk
 */
public class TCApplication extends Application {

    // dagger2 appComponent
    @SuppressWarnings("NullableProblems")
    @NonNull
    private AppComponent appComponent;

    @NonNull
    public static TCApplication get(@NonNull Context context) {
        return (TCApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = prepareAppComponent().build();
    }

    @NonNull
    private DaggerAppComponent.Builder prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this));
    }

    @NonNull
    public AppComponent applicationComponent() {
        return appComponent;
    }

}
