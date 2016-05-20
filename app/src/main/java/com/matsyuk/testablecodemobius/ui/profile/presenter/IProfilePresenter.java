package com.matsyuk.testablecodemobius.ui.profile.presenter;

import com.matsyuk.testablecodemobius.ui.profile.view.IProfileView;

/**
 * @author e.matsyuk
 */
public interface IProfilePresenter {

    void bindView(IProfileView iProfileView);
    void unbindView();

    void loadPersonalInfo();
    void updateBalance();

}
