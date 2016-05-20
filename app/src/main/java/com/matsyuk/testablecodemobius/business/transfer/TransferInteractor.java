package com.matsyuk.testablecodemobius.business.transfer;

import android.support.annotation.NonNull;

import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.matsyuk.testablecodemobius.business.transfer.validation.LocalTransferValidation;
import com.matsyuk.testablecodemobius.business.transfer.validation.ServerTransferValidateParser;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.TransferResultModel;
import com.matsyuk.testablecodemobius.data.repositories.transfer.ITransferRepository;
import com.matsyuk.testablecodemobius.ui.transfer.models.TransferFilledDataModel;

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
            @NonNull Observable<TextViewTextChangeEvent> orgNameFieldListener,
            @NonNull Observable<TextViewTextChangeEvent> amountFieldListener) {
        return Observable.combineLatest(
                orgNameFieldListener.compose(getChangedTextTransformer()),
                amountFieldListener.compose(getChangedTextTransformer()),
                (orgName, amount) -> !orgName.isEmpty() && !amount.isEmpty()
            )
                .distinctUntilChanged();
    }

    private Observable.Transformer<TextViewTextChangeEvent, String> getChangedTextTransformer()  {
        return objectObservable -> (objectObservable
                .map(textViewTextChangeEvent -> textViewTextChangeEvent.text().toString()))
                .startWith(EMPTY_STRING);

    }


}
