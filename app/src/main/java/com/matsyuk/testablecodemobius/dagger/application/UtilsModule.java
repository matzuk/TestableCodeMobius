package com.matsyuk.testablecodemobius.dagger.application;

import com.matsyuk.testablecodemobius.utils.rx.RxSchedulers;
import com.matsyuk.testablecodemobius.utils.rx.RxSchedulersAbs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author e.matsyuk
 */
@Module
public class UtilsModule {

    @Provides
    @Singleton
    RxSchedulersAbs provideRxSchedulersAbs() {
        return new RxSchedulers();
    }

}
