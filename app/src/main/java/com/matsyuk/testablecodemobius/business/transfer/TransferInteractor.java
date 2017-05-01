package com.matsyuk.testablecodemobius.business.transfer;

import android.support.annotation.NonNull;

import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.matsyuk.testablecodemobius.business.transfer.validation.LocalTransferValidation;
import com.matsyuk.testablecodemobius.business.transfer.validation.ServerTransferValidateParser;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.TransferResultModel;
import com.matsyuk.testablecodemobius.data.repositories.transfer.ITransferRepository;
import com.matsyuk.testablecodemobius.presentation.transfer.models.TransferFilledDataModel;

import rx.Observable;
import rx.Single;

/**
 * @author e.matsyuk
 */
public class TransferInteractor implements ITransferInteractor {

    private static final String EMPTY_STRING = "";

    private ITransferRepository iTransferRepository;
    private LocalTransferValidation localTransferValidation;
    private ServerTransferValidateParser serverTransferValidateParser;

    public TransferInteractor(ITransferRepository iTransferRepository, LocalTransferValidation localTransferValidation, ServerTransferValidateParser serverTransferValidateParser) {
        this.iTransferRepository = iTransferRepository;
        this.localTransferValidation = localTransferValidation;
        this.serverTransferValidateParser = serverTransferValidateParser;
    }

    @Override
    public Single<TransferResultModel> sendTransfer(
            @NonNull TransferFilledDataModel transferFilledDataModel) {
        return localTransferValidation.getLocalValidateSingle(transferFilledDataModel)
                .flatMap(iTransferRepository::validateTransfer)
                .flatMap(serverTransferValidateParser::getServerValidateParseSingle)
                .flatMap(iTransferRepository::sendTransferToServer);
    }

    @Override
    public Observable<Boolean> controlSendButton(
            @NonNull Observable<String> orgNameFieldListener,
            @NonNull Observable<String> amountFieldListener) {
        return Observable.combineLatest(
                orgNameFieldListener.startWith(EMPTY_STRING),
                amountFieldListener.startWith(EMPTY_STRING),
                (orgName, amount) -> !orgName.isEmpty() && !amount.isEmpty()
            )
                .distinctUntilChanged();
    }

}
