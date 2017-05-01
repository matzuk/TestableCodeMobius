package com.matsyuk.testablecodemobius.presentation.transfer.presenter;

import android.support.annotation.NonNull;

import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.matsyuk.testablecodemobius.business.transfer.ITransferInteractor;
import com.matsyuk.testablecodemobius.business.transfer.validation.TransferValidateException;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.TransferResultModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.TransferFilledDataModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.ValidateErrorModel;
import com.matsyuk.testablecodemobius.presentation.transfer.view.ITransferView;
import com.matsyuk.testablecodemobius.utils.rx.RxSchedulersAbs;

import java.util.List;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * @author e.matsyuk
 */
public class TransferPresenter implements ITransferPresenter {

    private ITransferInteractor iTransferInteractor;
    private RxSchedulersAbs rxSchedulersAbs;

    private ITransferView iTransferView;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public TransferPresenter(ITransferInteractor iTransferInteractor, RxSchedulersAbs rxSchedulersAbs) {
        this.iTransferInteractor = iTransferInteractor;
        this.rxSchedulersAbs = rxSchedulersAbs;
    }

    @Override
    public void bindView(@NonNull ITransferView iTransferView) {
        this.iTransferView = iTransferView;
    }

    @Override
    public void unbindView() {
        compositeSubscription.clear();
        iTransferView = null;
    }

    @Override
    public void listenFields(@NonNull Observable<TextViewTextChangeEvent> orgNameFieldListener,
                             @NonNull Observable<TextViewTextChangeEvent> amountFieldListener) {
        iTransferInteractor.controlSendButton(orgNameFieldListener, amountFieldListener)
                .subscribe(iTransferView::setEnableSendTransferButton, throwable -> {});
    }

    @Override
    public void sendTransfer(@NonNull TransferFilledDataModel transferFilledDataModel) {
        iTransferView.showProgress();
        iTransferInteractor.sendTransfer(transferFilledDataModel)
                .compose(rxSchedulersAbs.getIOToMainTransformerSingle())
                .subscribe(this::handleSendTransferSuccess, this::handleSendTransferError);
    }

    private void handleSendTransferSuccess(TransferResultModel transferResultModel) {
        iTransferView.hideProgress();
        iTransferView.showSuccess();
    }

    private void handleSendTransferError(Throwable throwable) {
        iTransferView.hideProgress();
        if (throwable instanceof TransferValidateException) {
            TransferValidateException transferValidateException = (TransferValidateException)throwable;
            handleValidateErrors(transferValidateException.getValidateErrorList());
        } else {
            iTransferView.showCommonError();
        }
    }

    private void handleValidateErrors(@NonNull List<ValidateErrorModel> validateErrorList) {
        for (ValidateErrorModel validateErrorModel : validateErrorList) {
            handleConcreteValidateError(validateErrorModel);
        }
    }

    private void handleConcreteValidateError(@NonNull ValidateErrorModel validateErrorModel) {
        switch (validateErrorModel.getField()) {
            case ORG_NAME: {
                iTransferView.showOrgNameError(validateErrorModel.getDescription());
                break;
            }
            case BIK: {
                iTransferView.showBIKError(validateErrorModel.getDescription());
                break;
            }
            case INN: {
                iTransferView.showINNError(validateErrorModel.getDescription());
                break;
            }
            case ACCOUNT_NUMBER: {
                iTransferView.showAccountNumberError(validateErrorModel.getDescription());
                break;
            }
            case AMOUNT: {
                iTransferView.showAmountError(validateErrorModel.getDescription());
                break;
            }
            default: // do nothing
        }
    }

}
