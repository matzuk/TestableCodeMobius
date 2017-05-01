package com.matsyuk.testablecodemobius.business.transfer.models;

import android.support.annotation.NonNull;

import com.matsyuk.testablecodemobius.presentation.transfer.models.TransferFilledDataModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.ValidateErrorModel;

import java.util.List;

/**
 * @author e.matsyuk
 */
public class LocalValidateResultModel {

    private TransferFilledDataModel transferFilledDataModel;
    private List<ValidateErrorModel> validateErrorModels;

    public LocalValidateResultModel(@NonNull TransferFilledDataModel transferFilledDataModel, @NonNull List<ValidateErrorModel> validateErrorModels) {
        this.transferFilledDataModel = transferFilledDataModel;
        this.validateErrorModels = validateErrorModels;
    }

    @NonNull
    public TransferFilledDataModel getTransferFilledDataModel() {
        return transferFilledDataModel;
    }

    @NonNull
    public List<ValidateErrorModel> getValidateErrors() {
        return validateErrorModels;
    }

    @Override
    public String toString() {
        return "LocalValidateResultModel{" +
                "transferFilledDataModel=" + transferFilledDataModel +
                ", validateErrorModels=" + validateErrorModels +
                '}';
    }

}
