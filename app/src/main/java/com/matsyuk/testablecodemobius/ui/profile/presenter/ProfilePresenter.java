package com.matsyuk.testablecodemobius.ui.profile.presenter;

import android.support.annotation.NonNull;

import com.matsyuk.testablecodemobius.business.profile.IProfileInteractor;
import com.matsyuk.testablecodemobius.ui.profile.models.PersonalFullDataModel;
import com.matsyuk.testablecodemobius.ui.profile.view.IProfileView;
import com.matsyuk.testablecodemobius.utils.rx.RxSchedulersAbs;

import java.math.BigDecimal;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author e.matsyuk
 */
public class ProfilePresenter implements IProfilePresenter {

    private IProfileInteractor iProfileInteractor;
    private RxSchedulersAbs rxSchedulersAbs;
    private ProfilePresenterCache profilePresenterCache;

    private IProfileView iProfileView;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public ProfilePresenter(IProfileInteractor iProfileInteractor, RxSchedulersAbs rxSchedulersAbs, ProfilePresenterCache profilePresenterCache) {
        this.iProfileInteractor = iProfileInteractor;
        this.rxSchedulersAbs = rxSchedulersAbs;
        this.profilePresenterCache = profilePresenterCache;
    }

    @Override
    public void bindView(IProfileView iProfileView) {
        this.iProfileView = iProfileView;
    }

    @Override
    public void unbindView() {
        compositeSubscription.clear();
        iProfileView = null;
    }

    @Override
    public void loadPersonalInfo() {
        if (profilePresenterCache.isCacheExist()) {
            setPersonalInfoToView(profilePresenterCache.getPersonalFullDataModel());
        } else {
            loadPersonalInfoFromData();
        }
    }

    private void setPersonalInfoToView(@NonNull PersonalFullDataModel personalFullDataModel) {
        iProfileView.setName(personalFullDataModel.getName());
        iProfileView.setAccountNumber(personalFullDataModel.getAccountNumber());
        iProfileView.setCardNumber(personalFullDataModel.getCardNumber());
        iProfileView.setBalance(personalFullDataModel.getBalance());
        iProfileView.setNearestDepartment(personalFullDataModel.getNearestDepartment());
    }

    private void loadPersonalInfoFromData() {
        iProfileView.showProgress();
        Subscription loadPersonalInfoSubscription = iProfileInteractor.getPersonalData()
                .compose(rxSchedulersAbs.getIOToMainTransformerSingle())
                .subscribe(this::handleSuccessLoadPersonalInfo, this::handleErrorLoadPersonalInfo);
        compositeSubscription.add(loadPersonalInfoSubscription);
    }

    private void handleSuccessLoadPersonalInfo(@NonNull PersonalFullDataModel personalFullDataModel) {
        // save cache
        profilePresenterCache.updateData(personalFullDataModel);
        // view actions
        setPersonalInfoToView(personalFullDataModel);
        // hide progress
        iProfileView.hideProgress();
    }

    private void handleErrorLoadPersonalInfo(Throwable throwable) {
        iProfileView.hideProgress();
        iProfileView.showError();
    }

    @Override
    public void updateBalance() {
        Subscription updateBalanceSubscription = iProfileInteractor.updateBalance()
                .compose(rxSchedulersAbs.getIOToMainTransformerSingle())
                .subscribe(this::handleSuccessUpdateBalance, throwable -> {});
        compositeSubscription.add(updateBalanceSubscription);
    }

    private void handleSuccessUpdateBalance(@NonNull BigDecimal balance) {
        // save in cache
        profilePresenterCache.updateBalance(balance);
        // call view
        iProfileView.setBalance(balance.toPlainString());
    }

}
