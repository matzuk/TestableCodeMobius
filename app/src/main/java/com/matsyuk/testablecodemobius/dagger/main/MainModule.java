package com.matsyuk.testablecodemobius.dagger.main;

import com.matsyuk.testablecodemobius.ui.main.presenter.IMainPresenter;
import com.matsyuk.testablecodemobius.ui.main.presenter.MainPresenter;

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
