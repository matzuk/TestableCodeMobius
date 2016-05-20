package com.matsyuk.testablecodemobius.ui.main.presenter;

import com.matsyuk.testablecodemobius.ui.main.views.IMainView;

/**
 * @author e.matsyuk
 */
public interface IMainPresenter {

    void bindView(IMainView iMainView);
    void unbindView();

    void clickToProfileButton();
    void clickToTransferButton();

}
