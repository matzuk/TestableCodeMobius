package com.matsyuk.testablecodemobius.dagger.profile;


import com.matsyuk.testablecodemobius.business.profile.IProfileInteractor;
import com.matsyuk.testablecodemobius.business.profile.ProfileInteractor;
import com.matsyuk.testablecodemobius.data.repositories.profile.IProfileRepository;
import com.matsyuk.testablecodemobius.data.repositories.profile.ProfileRepositoryDemo;
import com.matsyuk.testablecodemobius.ui.profile.presenter.IProfilePresenter;
import com.matsyuk.testablecodemobius.ui.profile.presenter.ProfilePresenter;
import com.matsyuk.testablecodemobius.ui.profile.presenter.ProfilePresenterCache;
import com.matsyuk.testablecodemobius.utils.rx.RxSchedulersAbs;

import dagger.Module;
import dagger.Provides;

/**
 * @author e.matsyuk
 */
@Module
public class ProfileModule {

    @Provides
    @ProfileScope
    IProfileRepository provideIProfileRepository() {
        return new ProfileRepositoryDemo();
    }

    @Provides
    @ProfileScope
    IProfileInteractor provideIProfileInteractor(IProfileRepository iProfileRepository) {
        return new ProfileInteractor(iProfileRepository);
    }

    @Provides
    @ProfileScope
    ProfilePresenterCache provideProfilePresenterCache() {
        return new ProfilePresenterCache();
    }

    @Provides
    @ProfileScope
    IProfilePresenter provideIProfilePresenter(IProfileInteractor iProfileInteractor,
                                               RxSchedulersAbs rxSchedulersAbs,
                                               ProfilePresenterCache profilePresenterCache) {
        return new ProfilePresenter(iProfileInteractor, rxSchedulersAbs, profilePresenterCache);
//        return new FakeProfilePresenter(rxSchedulersAbs);
    }

}
