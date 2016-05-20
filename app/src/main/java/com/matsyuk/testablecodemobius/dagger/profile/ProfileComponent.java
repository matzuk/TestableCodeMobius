package com.matsyuk.testablecodemobius.dagger.profile;

import com.matsyuk.testablecodemobius.ui.profile.view.ProfileFragment;

import dagger.Subcomponent;

/**
 * @author e.matsyuk
 */
@Subcomponent(modules = {ProfileModule.class})
@ProfileScope
public interface ProfileComponent {

    void inject(ProfileFragment profileFragment);

}
