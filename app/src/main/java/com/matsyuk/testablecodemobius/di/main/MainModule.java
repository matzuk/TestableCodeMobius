package com.matsyuk.testablecodemobius.di.main;

import com.matsyuk.testablecodemobius.presentation.main.presenter.IMainPresenter;
import com.matsyuk.testablecodemobius.presentation.main.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author e.matsyuk
 */
@Module
public class MainModule {

    @Provides
    @MainScope
    IMainPresenter provideIMainPresenter() {
        return new MainPresenter();
    }

}
