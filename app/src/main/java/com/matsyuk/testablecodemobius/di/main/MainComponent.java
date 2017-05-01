package com.matsyuk.testablecodemobius.di.main;

import com.matsyuk.testablecodemobius.presentation.main.views.MainFragment;

import dagger.Subcomponent;

/**
 * @author e.matsyuk
 */
@Subcomponent(modules = {MainModule.class})
@MainScope
public interface MainComponent {

    void inject(MainFragment mainFragment);

}
