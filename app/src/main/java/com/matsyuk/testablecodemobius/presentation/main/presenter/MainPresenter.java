package com.matsyuk.testablecodemobius.presentation.main.presenter;

import com.matsyuk.testablecodemobius.presentation.main.views.IMainView;

/**
 * @author e.matsyuk
 */
public class MainPresenter implements IMainPresenter {

    private IMainView iMainView;

    @Override
    public void bindView(IMainView iMainView) {
        this.iMainView = iMainView;
    }

    @Override
    public void unbindView() {
        iMainView = null;
    }

    @Override
    public void clickToProfileButton() {
        iMainView.showProfileScreen();
    }

    @Override
    public void clickToTransferButton() {
        iMainView.showTransferScreen();
    }

}
