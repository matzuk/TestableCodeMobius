package com.matsyuk.testablecodemobius.dagger.main;

import com.matsyuk.testablecodemobius.ui.main.views.MainFragment;

import dagger.Subcomponent;

/**
 * @author e.matsyuk
 */
@Subcomponent(modules = {MainModule.class})
@MainScope
public interface MainComponent {

    void inject(MainFragment mainFragment);

}
