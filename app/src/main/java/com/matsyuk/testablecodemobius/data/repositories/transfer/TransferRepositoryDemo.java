package com.matsyuk.testablecodemobius.data.repositories.transfer;

import android.support.annotation.NonNull;

import com.matsyuk.testablecodemobius.data.network.models.request.transfer.TransferRequestModel;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.TransferResultModel;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.TransferValidateModel;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Single;

/**
 * @author e.matsyuk
 */
public class TransferRepositoryDemo implements ITransferRepository {

    @Override
    public Single<TransferValidateModel> validateTransfer(@NonNull TransferRequestModel transferRequestModel) {
        return Single.fromCallable(() -> new TransferValidateModel("success", transferRequestModel, null));
    }

    @Override
    public Single<TransferResultModel> sendTransferToServer(@NonNull TransferRequestModel transferRequestModel) {
        return Observable.timer(2, TimeUnit.SECONDS)
                .toSingle()
                .flatMap(aLong -> Single.fromCallable(() -> new TransferResultModel("success", "The transfer to Microsoft was successful")));
    }

}
