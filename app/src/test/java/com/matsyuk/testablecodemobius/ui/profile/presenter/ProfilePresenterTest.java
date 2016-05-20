package com.matsyuk.testablecodemobius.ui.profile.presenter;

import com.matsyuk.testablecodemobius.business.profile.IProfileInteractor;
import com.matsyuk.testablecodemobius.ui.profile.models.PersonalFullDataModel;
import com.matsyuk.testablecodemobius.ui.profile.view.IProfileView;
import com.matsyuk.testablecodemobius.utils.rx.RxSchedulersAbs;
import com.matsyuk.testablecodemobius.utils.rx.RxSchedulersTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.math.BigDecimal;

import rx.Single;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author e.matsyuk
 */
public class ProfilePresenterTest {

    private ProfilePresenter profilePresenter;
    private IProfileInteractor iProfileInteractor;
    private ProfilePresenterCache profilePresenterCache;

    private IProfileView iProfileView;

    private PersonalFullDataModel personalFullDataModel;
    private String startBalance = "100";
    private String nearestDepartment = "Moscow";

    @Before
    public void beforeEachTest() {
        iProfileInteractor = mock(IProfileInteractor.class);
        RxSchedulersAbs rxSchedulersAbs = new RxSchedulersTest();
        profilePresenterCache = mock(ProfilePresenterCache.class);
        profilePresenter = new ProfilePresenter(iProfileInteractor, rxSchedulersAbs, profilePresenterCache);
        iProfileView = mock(IProfileView.class);

        personalFullDataModel = new PersonalFullDataModel(
                "Ivanov S.P.",
                "1234567",
                "4321",
                startBalance,
                nearestDepartment
        );
    }

    @Test
    public void loadPersonalInfo_notNullCache() {
        // mock profilePresenterCache
        when(profilePresenterCache.isCacheExist()).thenReturn(true);
        when(profilePresenterCache.getPersonalFullDataModel()).thenReturn(personalFullDataModel);
        // presenter bindView
        profilePresenter.bindView(iProfileView);
        // action loadPersonalInfo
        profilePresenter.loadPersonalInfo();
        // verify interactor was not called
        verify(iProfileInteractor, never()).getPersonalData();
        // verify progressBar was not called
        verify(iProfileView, never()).showProgress();
        verify(iProfileView, never()).hideProgress();
        // verify showError was not called
        verify(iProfileView, never()).showError();
        // verify calls of view methods
        InOrder inOrder = inOrder(iProfileView);
        inOrder.verify(iProfileView).setName("Ivanov S.P.");
        inOrder.verify(iProfileView).setAccountNumber("1234567");
        inOrder.verify(iProfileView).setCardNumber("4321");
        inOrder.verify(iProfileView).setBalance(startBalance);
        inOrder.verify(iProfileView).setNearestDepartment(nearestDepartment);
    }

    @Test
    public void loadPersonalInfo_nullCache_successLoad() {
        // mock interactor
        when(iProfileInteractor.getPersonalData()).thenReturn(Single.fromCallable(() -> personalFullDataModel));
        // mock cache
        when(profilePresenterCache.isCacheExist()).thenReturn(false);
        // presenter bindView
        profilePresenter.bindView(iProfileView);
        // action loadPersonalInfo
        profilePresenter.loadPersonalInfo();
        // verify interactor was called
        verify(iProfileInteractor).getPersonalData();
        // verify cache was updated
        verify(profilePresenterCache).updateData(personalFullDataModel);
        // verify calls of view methods
        InOrder inOrder = inOrder(iProfileView);
        inOrder.verify(iProfileView).showProgress();
        inOrder.verify(iProfileView).setName("Ivanov S.P.");
        inOrder.verify(iProfileView).setAccountNumber("1234567");
        inOrder.verify(iProfileView).setCardNumber("4321");
        inOrder.verify(iProfileView).setBalance(startBalance);
        inOrder.verify(iProfileView).setNearestDepartment(nearestDepartment);
        inOrder.verify(iProfileView).hideProgress();
        // verify showError was not called
        verify(iProfileView, never()).showError();
    }

    @Test
    public void loadPersonalInfo_nullCache_errorLoad() {
        // mock interactor
        when(iProfileInteractor.getPersonalData()).thenReturn(Single.error(new RuntimeException()));
        // mock cache
        when(profilePresenterCache.isCacheExist()).thenReturn(false);
        // presenter bindView
        profilePresenter.bindView(iProfileView);
        // action loadPersonalInfo
        profilePresenter.loadPersonalInfo();
        // verify interactor was called
        verify(iProfileInteractor).getPersonalData();
        // verify cache was not updated
        verify(profilePresenterCache, never()).updateData(any(PersonalFullDataModel.class));
        // verify calls of view methods (progress and showError)
        InOrder inOrder = inOrder(iProfileView);
        inOrder.verify(iProfileView).showProgress();
        inOrder.verify(iProfileView).hideProgress();
        inOrder.verify(iProfileView).showError();
        // verify info was not showed to view
        verify(iProfileView, never()).setName(anyString());
        verify(iProfileView, never()).setAccountNumber(anyString());
        verify(iProfileView, never()).setCardNumber(anyString());
        verify(iProfileView, never()).setBalance(anyString());
        verify(iProfileView, never()).setNearestDepartment(anyString());
    }

    @Test
    public void updateBalance_success() {
        // mock profilePresenterCache
        when(profilePresenterCache.isCacheExist()).thenReturn(false);
        // new balance
        BigDecimal balance = new BigDecimal(200);
        // mock interactor
        when(iProfileInteractor.updateBalance()).thenReturn(Single.fromCallable(() -> balance));
        // presenter bindView
        profilePresenter.bindView(iProfileView);
        // action updateBalance
        profilePresenter.updateBalance();
        // verify view was updated
        verify(iProfileView).setBalance("200");
        // verify cache was updated
        verify(profilePresenterCache).updateBalance(balance);
    }

    @Test
    public void updateBalance_error() {
        // mock profilePresenterCache
        when(profilePresenterCache.isCacheExist()).thenReturn(false);
        // mock interactor
        when(iProfileInteractor.updateBalance()).thenReturn(Single.error(new RuntimeException()));
        // presenter bindView
        profilePresenter.bindView(iProfileView);
        // action updateBalance
        profilePresenter.updateBalance();
        // verify view was not updated
        verify(iProfileView, never()).setBalance(anyString());
        // verify cache was not updated
        verify(profilePresenterCache, never()).updateBalance(any(BigDecimal.class));
    }

}