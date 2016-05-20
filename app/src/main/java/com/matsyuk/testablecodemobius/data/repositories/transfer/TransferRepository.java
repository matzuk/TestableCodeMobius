package com.matsyuk.testablecodemobius.data.repositories.transfer;

import android.support.annotation.NonNull;

import com.matsyuk.testablecodemobius.data.network.models.request.transfer.TransferRequestModel;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.TransferResultModel;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.TransferValidateModel;

import rx.Single;

/**
 * @author e.matsyuk
 */
public class TransferRepository implements ITransferRepository {

    @Override
    public Single<TransferValidateModel> validateTransfer(@NonNull TransferRequestModel transferRequestModel) {
        return null;
    }

    @Override
    public Single<TransferResultModel> sendTransferToServer(@NonNull TransferRequestModel transferRequestModel) {
        return null;
    }

}
