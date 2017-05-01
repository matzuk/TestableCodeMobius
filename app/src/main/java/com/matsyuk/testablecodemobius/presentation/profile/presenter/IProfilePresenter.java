package com.matsyuk.testablecodemobius.presentation.profile.presenter;

import com.matsyuk.testablecodemobius.presentation.profile.view.IProfileView;

/**
 * @author e.matsyuk
 */
public interface IProfilePresenter {

    void bindView(IProfileView iProfileView);
    void unbindView();

    void loadPersonalInfo();
    void updateBalance();

}
