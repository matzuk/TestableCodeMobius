package com.matsyuk.testablecodemobius.ui.transfer.presenter;

import android.support.annotation.NonNull;

import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.matsyuk.testablecodemobius.ui.transfer.models.TransferFilledDataModel;
import com.matsyuk.testablecodemobius.ui.transfer.view.ITransferView;

import rx.Observable;

/**
 * @author e.matsyuk
 */
public interface ITransferPresenter {

    void bindView(@NonNull ITransferView iTransferView);
    void unbindView();

    void listenFields(@NonNull Observable<TextViewTextChangeEvent> orgNameFieldListener, @NonNull Observable<TextViewTextChangeEvent> amountFieldListener);
    void sendTransfer(@NonNull TransferFilledDataModel transferFilledDataModel);

}
