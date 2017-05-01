package com.matsyuk.testablecodemobius.di.application;

import com.matsyuk.testablecodemobius.di.main.MainComponent;
import com.matsyuk.testablecodemobius.di.main.MainModule;
import com.matsyuk.testablecodemobius.di.profile.ProfileComponent;
import com.matsyuk.testablecodemobius.di.profile.ProfileModule;
import com.matsyuk.testablecodemobius.di.transfer.TransferComponent;
import com.matsyuk.testablecodemobius.di.transfer.TransferModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author e.matsyuk
 */
@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    MainComponent plus(MainModule mainModule);
    ProfileComponent plus(ProfileModule profileModule);
    TransferComponent plus(TransferModule transferModule);

}
