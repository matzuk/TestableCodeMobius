package com.matsyuk.testablecodemobius.business.transfer.models;

import android.support.annotation.NonNull;

import com.matsyuk.testablecodemobius.data.network.models.request.transfer.TransferRequestModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.ValidateErrorModel;

import java.util.List;

/**
 * @author e.matsyuk
 */
public class ServerValidateResultModel {

    private TransferRequestModel transferRequestModel;
    private List<ValidateErrorModel> validateErrorModels;

    public ServerValidateResultModel(@NonNull TransferRequestModel transferRequestModel, @NonNull List<ValidateErrorModel> validateErrorModels) {
        this.transferRequestModel = transferRequestModel;
        this.validateErrorModels = validateErrorModels;
    }

    @NonNull
    public TransferRequestModel getTransferRequestModel() {
        return transferRequestModel;
    }

    @NonNull
    public List<ValidateErrorModel> getValidateErrors() {
        return validateErrorModels;
    }

    @Override
    public String toString() {
        return "ServerValidateResultModel{" +
                "transferRequestModel=" + transferRequestModel +
                ", validateErrorModels=" + validateErrorModels +
                '}';
    }
    
}
