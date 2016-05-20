package com.matsyuk.testablecodemobius.dagger.application;

import com.matsyuk.testablecodemobius.dagger.main.MainComponent;
import com.matsyuk.testablecodemobius.dagger.main.MainModule;
import com.matsyuk.testablecodemobius.dagger.profile.ProfileComponent;
import com.matsyuk.testablecodemobius.dagger.profile.ProfileModule;
import com.matsyuk.testablecodemobius.dagger.transfer.TransferComponent;
import com.matsyuk.testablecodemobius.dagger.transfer.TransferModule;

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
