package com.matsyuk.testablecodemobius.ui.main.presenter;

import com.matsyuk.testablecodemobius.ui.main.views.IMainView;

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
