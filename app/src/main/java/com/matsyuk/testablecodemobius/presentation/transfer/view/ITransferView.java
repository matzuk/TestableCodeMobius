package com.matsyuk.testablecodemobius.presentation.transfer.view;

/**
 * @author e.matsyuk
 */
public interface ITransferView {

    void showProgress();
    void hideProgress();

    void showOrgNameError(String error);
    void showBIKError(String error);
    void showINNError(String error);
    void showAccountNumberError(String error);
    void showAmountError(String error);
    void showCommonError();

    void showSuccess();

    void setEnableSendTransferButton(boolean enable);

}
