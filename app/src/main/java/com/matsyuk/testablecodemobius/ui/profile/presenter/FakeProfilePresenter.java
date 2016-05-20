package com.matsyuk.testablecodemobius.ui.profile.presenter;

import com.matsyuk.testablecodemobius.ui.profile.view.IProfileView;
import com.matsyuk.testablecodemobius.utils.rx.RxSchedulersAbs;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * @author e.matsyuk
 */
public class FakeProfilePresenter implements IProfilePresenter {

    private RxSchedulersAbs rxSchedulersAbs;
    private IProfileView iProfileView;

    public FakeProfilePresenter(RxSchedulersAbs rxSchedulersAbs) {
        this.rxSchedulersAbs = rxSchedulersAbs;
    }

    @Override
    public void bindView(IProfileView iProfileView) {
        this.iProfileView = iProfileView;
    }

    @Override
    public void unbindView() {
        iProfileView = null;
    }

    @Override
    public void loadPersonalInfo() {
        iProfileView.showProgress();
        Observable.timer(2, TimeUnit.SECONDS)
                .compose(rxSchedulersAbs.getIOToMainTransformer())
                .subscribe(aLong -> {
                    iProfileView.hideProgress();
                    iProfileView.showError();
                }, throwable -> {});
    }

    @Override
    public void updateBalance() {

    }

}
