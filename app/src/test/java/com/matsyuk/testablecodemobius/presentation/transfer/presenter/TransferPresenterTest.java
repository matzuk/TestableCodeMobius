package com.matsyuk.testablecodemobius.presentation.transfer.presenter;

import com.matsyuk.testablecodemobius.business.transfer.ITransferInteractor;
import com.matsyuk.testablecodemobius.business.transfer.validation.TransferValidateException;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.TransferResultModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.TransferFilledDataModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.ValidateErrorModel;
import com.matsyuk.testablecodemobius.presentation.transfer.view.ITransferView;
import com.matsyuk.testablecodemobius.utils.rx.RxSchedulersAbs;
import com.matsyuk.testablecodemobius.utils.rx.RxSchedulersTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.List;

import rx.Observable;
import rx.Single;

import static java.util.Arrays.asList;
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
public class TransferPresenterTest {

    private ITransferInteractor iTransferInteractor;
    private ITransferView iTransferView;
    private TransferPresenter transferPresenter;

    @Before
    public void beforeEachTest() {
        iTransferInteractor = mock(ITransferInteractor.class);
        RxSchedulersAbs rxSchedulersAbs = new RxSchedulersTest();
        iTransferView = mock(ITransferView.class);
        transferPresenter = new TransferPresenter(iTransferInteractor, rxSchedulersAbs);
    }

    @Test
    public void sendTransfer_success() {
        // mock
        when(iTransferInteractor.sendTransfer(any(TransferFilledDataModel.class))).thenReturn(
                Single.fromCallable(() -> new TransferResultModel("success", "info")));
        // bindView
        transferPresenter.bindView(iTransferView);
        // sendTransfer
        transferPresenter.sendTransfer(mock(TransferFilledDataModel.class));
        // verify
        InOrder inOrder = inOrder(iTransferView);
        inOrder.verify(iTransferView).showProgress();
        inOrder.verify(iTransferView).hideProgress();
        inOrder.verify(iTransferView).showSuccess();
        //
        verify(iTransferView, never()).showAccountNumberError(anyString());
        verify(iTransferView, never()).showBIKError(anyString());
        verify(iTransferView, never()).showINNError(anyString());
        verify(iTransferView, never()).showOrgNameError(anyString());
        verify(iTransferView, never()).showAmountError(anyString());
        verify(iTransferView, never()).showCommonError();
    }

    @Test
    public void sendTransfer_validateError() {
        // mock
        when(iTransferInteractor.sendTransfer(any(TransferFilledDataModel.class))).thenReturn(
                Single.error(getTransferValidateException()));
        // bindView
        transferPresenter.bindView(iTransferView);
        // sendTransfer
        transferPresenter.sendTransfer(mock(TransferFilledDataModel.class));
        // verify
        InOrder inOrder = inOrder(iTransferView);
        inOrder.verify(iTransferView).showProgress();
        inOrder.verify(iTransferView).hideProgress();
        inOrder.verify(iTransferView).showOrgNameError("org name error");
        inOrder.verify(iTransferView).showBIKError("bik error");
        inOrder.verify(iTransferView).showINNError("inn error");
        inOrder.verify(iTransferView).showAccountNumberError("account number error");
        inOrder.verify(iTransferView).showAmountError("amount error");
        //
        verify(iTransferView, never()).showSuccess();
        verify(iTransferView, never()).showCommonError();
    }

    private TransferValidateException getTransferValidateException() {
        List<ValidateErrorModel> validateErrorList = asList(
                new ValidateErrorModel(ValidateErrorModel.Field.ORG_NAME, "org name error"),
                new ValidateErrorModel(ValidateErrorModel.Field.BIK, "bik error"),
                new ValidateErrorModel(ValidateErrorModel.Field.INN, "inn error"),
                new ValidateErrorModel(ValidateErrorModel.Field.ACCOUNT_NUMBER, "account number error"),
                new ValidateErrorModel(ValidateErrorModel.Field.AMOUNT, "amount error")
        );
        return new TransferValidateException(validateErrorList);
    }

    @Test
    public void sendTransfer_noValidateError() {
        // mock
        when(iTransferInteractor.sendTransfer(any(TransferFilledDataModel.class))).thenReturn(
                Single.error(new RuntimeException()));
        // bindView
        transferPresenter.bindView(iTransferView);
        // sendTransfer
        transferPresenter.sendTransfer(mock(TransferFilledDataModel.class));
        // verify
        InOrder inOrder = inOrder(iTransferView);
        inOrder.verify(iTransferView).showProgress();
        inOrder.verify(iTransferView).hideProgress();
        inOrder.verify(iTransferView).showCommonError();
        //
        verify(iTransferView, never()).showAccountNumberError(anyString());
        verify(iTransferView, never()).showBIKError(anyString());
        verify(iTransferView, never()).showINNError(anyString());
        verify(iTransferView, never()).showOrgNameError(anyString());
        verify(iTransferView, never()).showAmountError(anyString());
        verify(iTransferView, never()).showSuccess();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void listenFields_success() {
        // mock
        when(iTransferInteractor.controlSendButton(any(Observable.class), any(Observable.class))).thenReturn(Observable.fromCallable(() -> true));
        // bindView
        transferPresenter.bindView(iTransferView);
        // listen
        transferPresenter.listenFields(any(Observable.class), any(Observable.class));
        // verify
        verify(iTransferView).setEnableSendTransferButton(true);
    }

}