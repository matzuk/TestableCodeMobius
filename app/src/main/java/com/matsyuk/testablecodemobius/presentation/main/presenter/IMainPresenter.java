package com.matsyuk.testablecodemobius.presentation.main.presenter;

import com.matsyuk.testablecodemobius.presentation.main.views.IMainView;

/**
 * @author e.matsyuk
 */
public interface IMainPresenter {

    void bindView(IMainView iMainView);
    void unbindView();

    void clickToProfileButton();
    void clickToTransferButton();

}
