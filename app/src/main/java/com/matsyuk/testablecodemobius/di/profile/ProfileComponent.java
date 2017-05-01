package com.matsyuk.testablecodemobius.di.profile;

import com.matsyuk.testablecodemobius.presentation.profile.view.ProfileFragment;

import dagger.Subcomponent;

/**
 * @author e.matsyuk
 */
@Subcomponent(modules = {ProfileModule.class})
@ProfileScope
public interface ProfileComponent {

    void inject(ProfileFragment profileFragment);

}
