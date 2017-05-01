package com.matsyuk.testablecodemobius.business.transfer;

import android.support.annotation.NonNull;

import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.TransferResultModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.TransferFilledDataModel;

import rx.Observable;
import rx.Single;

/**
 * @author e.matsyuk
 */
public interface ITransferInteractor {

    Single<TransferResultModel> sendTransfer(@NonNull TransferFilledDataModel transferFilledDataModel);
    Observable<Boolean> controlSendButton(@NonNull Observable<String> orgNameFieldListener, @NonNull Observable<String> amountFieldListener);

}
